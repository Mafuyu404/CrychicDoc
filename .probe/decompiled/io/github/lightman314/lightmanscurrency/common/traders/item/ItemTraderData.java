package io.github.lightman314.lightmanscurrency.common.traders.item;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import io.github.lightman314.lightmanscurrency.LCText;
import io.github.lightman314.lightmanscurrency.LightmansCurrency;
import io.github.lightman314.lightmanscurrency.api.misc.player.PlayerReference;
import io.github.lightman314.lightmanscurrency.api.money.value.MoneyValue;
import io.github.lightman314.lightmanscurrency.api.stats.StatKeys;
import io.github.lightman314.lightmanscurrency.api.traders.TradeContext;
import io.github.lightman314.lightmanscurrency.api.traders.TradeResult;
import io.github.lightman314.lightmanscurrency.api.traders.TraderType;
import io.github.lightman314.lightmanscurrency.api.traders.menu.storage.ITraderStorageMenu;
import io.github.lightman314.lightmanscurrency.api.upgrades.UpgradeType;
import io.github.lightman314.lightmanscurrency.client.gui.widget.button.icon.IconData;
import io.github.lightman314.lightmanscurrency.client.util.IconAndButtonUtil;
import io.github.lightman314.lightmanscurrency.common.blockentity.handler.TraderItemHandler;
import io.github.lightman314.lightmanscurrency.common.items.UpgradeItem;
import io.github.lightman314.lightmanscurrency.common.menus.traderstorage.item.ItemStorageTab;
import io.github.lightman314.lightmanscurrency.common.menus.traderstorage.item.ItemTradeEditTab;
import io.github.lightman314.lightmanscurrency.common.notifications.types.settings.AddRemoveTradeNotification;
import io.github.lightman314.lightmanscurrency.common.notifications.types.trader.ItemTradeNotification;
import io.github.lightman314.lightmanscurrency.common.notifications.types.trader.OutOfStockNotification;
import io.github.lightman314.lightmanscurrency.common.player.LCAdminMode;
import io.github.lightman314.lightmanscurrency.common.traders.InputTraderData;
import io.github.lightman314.lightmanscurrency.common.traders.item.tradedata.ItemTradeData;
import io.github.lightman314.lightmanscurrency.common.traders.item.tradedata.restrictions.ItemTradeRestriction;
import io.github.lightman314.lightmanscurrency.common.traders.permissions.Permissions;
import io.github.lightman314.lightmanscurrency.common.traders.rules.TradeRule;
import io.github.lightman314.lightmanscurrency.common.upgrades.Upgrades;
import io.github.lightman314.lightmanscurrency.common.upgrades.types.capacity.CapacityUpgrade;
import io.github.lightman314.lightmanscurrency.util.FileUtil;
import io.github.lightman314.lightmanscurrency.util.MathUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.ResourceLocationException;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;

public class ItemTraderData extends InputTraderData implements TraderItemStorage.ITraderItemFilter {

    public static final List<UpgradeType> ALLOWED_UPGRADES = Lists.newArrayList(new UpgradeType[] { Upgrades.ITEM_CAPACITY });

    public static final int DEFAULT_STACK_LIMIT = 576;

    public static final TraderType<ItemTraderData> TYPE = new TraderType<>(new ResourceLocation("lightmanscurrency", "item_trader"), ItemTraderData::new);

    TraderItemHandler itemHandler = new TraderItemHandler(this);

    protected TraderItemStorage storage = new TraderItemStorage(this);

    protected List<ItemTradeData> trades;

    public IItemHandler getItemHandler(Direction relativeSide) {
        return this.itemHandler.getHandler(relativeSide);
    }

    public final TraderItemStorage getStorage() {
        return this.storage;
    }

    public void markStorageDirty() {
        this.markDirty(new Consumer[] { this::saveStorage });
    }

    @Override
    public boolean allowAdditionalUpgradeType(UpgradeType type) {
        return ALLOWED_UPGRADES.contains(type);
    }

    private ItemTraderData() {
        this(TYPE);
    }

    protected ItemTraderData(@Nonnull TraderType<?> type) {
        super(type);
        this.trades = ItemTradeData.listOfSize(1, true);
        this.validateTradeRestrictions();
    }

    public ItemTraderData(int tradeCount, @Nonnull Level level, @Nonnull BlockPos pos) {
        this(TYPE, tradeCount, level, pos);
    }

    protected ItemTraderData(@Nonnull TraderType<?> type, int tradeCount, @Nonnull Level level, @Nonnull BlockPos pos) {
        super(type, level, pos);
        this.trades = ItemTradeData.listOfSize(tradeCount, true);
        this.validateTradeRestrictions();
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        this.saveStorage(compound);
        this.saveTrades(compound);
    }

    protected final void saveStorage(CompoundTag compound) {
        this.storage.save(compound, "ItemStorage");
    }

    @Override
    protected final void saveTrades(CompoundTag compound) {
        ItemTradeData.saveAllData(compound, this.trades);
    }

    @Override
    public void loadAdditional(CompoundTag compound) {
        super.loadAdditional(compound);
        if (compound.contains("ItemStorage")) {
            this.storage.load(compound, "ItemStorage");
        }
        if (compound.contains("Trades")) {
            this.trades = ItemTradeData.loadAllData(compound, !this.isPersistent());
            this.validateTradeRestrictions();
        }
    }

    @Override
    public int getTradeCount() {
        return this.trades.size();
    }

    @Override
    public void addTrade(Player requestor) {
        if (!this.isClient()) {
            if (this.getTradeCount() < 100) {
                if (LCAdminMode.isAdminPlayer(requestor)) {
                    this.overrideTradeCount(this.getTradeCount() + 1);
                    this.pushLocalNotification(new AddRemoveTradeNotification(PlayerReference.of(requestor), true, this.getTradeCount()));
                } else {
                    Permissions.PermissionWarning(requestor, "add a trade slot", "LC_ADMIN_MODE");
                }
            }
        }
    }

    @Override
    public void removeTrade(Player requestor) {
        if (!this.isClient()) {
            if (this.getTradeCount() > 1) {
                if (LCAdminMode.isAdminPlayer(requestor)) {
                    this.overrideTradeCount(this.getTradeCount() - 1);
                    this.pushLocalNotification(new AddRemoveTradeNotification(PlayerReference.of(requestor), false, this.getTradeCount()));
                } else {
                    Permissions.PermissionWarning(requestor, "remove a trade slot", "LC_ADMIN_MODE");
                }
            }
        }
    }

    public void overrideTradeCount(int newTradeCount) {
        if (this.getTradeCount() != MathUtil.clamp(newTradeCount, 1, 100)) {
            int tradeCount = MathUtil.clamp(newTradeCount, 1, 100);
            List<ItemTradeData> oldTrades = this.trades;
            this.trades = ItemTradeData.listOfSize(tradeCount, !this.isPersistent());
            for (int i = 0; i < oldTrades.size() && i < this.trades.size(); i++) {
                this.trades.set(i, (ItemTradeData) oldTrades.get(i));
            }
            this.validateTradeRestrictions();
            if (this.isServer()) {
                this.markTradesDirty();
            }
        }
    }

    public final void validateTradeRestrictions() {
        for (int i = 0; i < this.trades.size(); i++) {
            ItemTradeData trade = (ItemTradeData) this.trades.get(i);
            trade.setRestriction(this.getTradeRestriction(i));
        }
    }

    protected ItemTradeRestriction getTradeRestriction(int tradeIndex) {
        return ItemTradeRestriction.NONE;
    }

    public ItemTradeData getTrade(int tradeSlot) {
        if (tradeSlot >= 0 && tradeSlot < this.trades.size()) {
            return (ItemTradeData) this.trades.get(tradeSlot);
        } else {
            LightmansCurrency.LogError("Cannot get trade in index " + tradeSlot + " from a trader with only " + this.trades.size() + " trades.");
            return new ItemTradeData(false);
        }
    }

    @Nonnull
    @Override
    public List<ItemTradeData> getTradeData() {
        return this.trades;
    }

    @Override
    public int getTradeStock(int tradeSlot) {
        ItemTradeData trade = this.getTrade(tradeSlot);
        if (trade.sellItemsDefined()) {
            return this.isCreative() ? Integer.MAX_VALUE : trade.stockCount(this);
        } else {
            return 0;
        }
    }

    @Override
    public IconData inputSettingsTabIcon() {
        return IconData.of(Items.HOPPER);
    }

    @Override
    public MutableComponent inputSettingsTabTooltip() {
        return LCText.TOOLTIP_TRADER_SETTINGS_INPUT_ITEM.get();
    }

    @Override
    public IconData getIcon() {
        return IconAndButtonUtil.ICON_TRADER;
    }

    @Override
    protected void saveAdditionalToJson(JsonObject json) {
        JsonArray trades = new JsonArray();
        for (ItemTradeData trade : this.trades) {
            if (trade.isValid()) {
                JsonObject tradeData = new JsonObject();
                JsonArray ignoreNBTData = new JsonArray();
                tradeData.addProperty("TradeType", trade.getTradeDirection().name());
                if (trade.getSellItem(0).isEmpty()) {
                    tradeData.add("SellItem", FileUtil.convertItemStack(trade.getSellItem(1)));
                    if (trade.hasCustomName(1)) {
                        tradeData.addProperty("DisplayName", trade.getCustomName(1));
                    }
                    if (!trade.getEnforceNBT(1)) {
                        ignoreNBTData.add(0);
                    }
                } else {
                    tradeData.add("SellItem", FileUtil.convertItemStack(trade.getSellItem(0)));
                    if (trade.hasCustomName(0)) {
                        tradeData.addProperty("DisplayName", trade.getCustomName(0));
                    }
                    if (!trade.getEnforceNBT(0)) {
                        ignoreNBTData.add(0);
                    }
                    if (!trade.getSellItem(1).isEmpty()) {
                        tradeData.add("SellItem2", FileUtil.convertItemStack(trade.getSellItem(1)));
                        if (trade.hasCustomName(1)) {
                            tradeData.addProperty("DisplayName2", trade.getCustomName(1));
                        }
                        if (!trade.getEnforceNBT(1)) {
                            ignoreNBTData.add(1);
                        }
                    }
                }
                if (trade.isSale() || trade.isPurchase()) {
                    tradeData.add("Price", trade.getCost().toJson());
                }
                if (trade.isBarter()) {
                    if (trade.getBarterItem(0).isEmpty()) {
                        tradeData.add("BarterItem", FileUtil.convertItemStack(trade.getBarterItem(1)));
                        if (!trade.getEnforceNBT(3)) {
                            ignoreNBTData.add(2);
                        }
                    } else {
                        tradeData.add("BarterItem", FileUtil.convertItemStack(trade.getBarterItem(0)));
                        if (!trade.getEnforceNBT(2)) {
                            ignoreNBTData.add(2);
                        }
                        if (!trade.getBarterItem(1).isEmpty()) {
                            tradeData.add("BarterItem2", FileUtil.convertItemStack(trade.getBarterItem(1)));
                            if (!trade.getEnforceNBT(3)) {
                                ignoreNBTData.add(3);
                            }
                        }
                    }
                }
                if (!ignoreNBTData.isEmpty()) {
                    tradeData.add("IgnoreNBT", ignoreNBTData);
                }
                JsonArray ruleData = TradeRule.saveRulesToJson(trade.getRules());
                if (!ruleData.isEmpty()) {
                    tradeData.add("Rules", ruleData);
                }
                trades.add(tradeData);
            }
        }
        JsonArray storageData = new JsonArray();
        for (ItemStack item : this.storage.getContents()) {
            boolean shouldWrite = false;
            for (int i = 0; i < this.trades.size() && !shouldWrite; i++) {
                ItemTradeData tradex = (ItemTradeData) this.trades.get(i);
                if (tradex.isValid() && tradex.shouldStorageItemBeSaved(item)) {
                    shouldWrite = true;
                }
            }
            if (shouldWrite) {
                storageData.add(FileUtil.convertItemStack(item));
            }
        }
        if (!storageData.isEmpty()) {
            json.add("RelevantStorage", storageData);
        }
        json.add("Trades", trades);
    }

    @Override
    protected void loadAdditionalFromJson(JsonObject json) throws JsonSyntaxException, ResourceLocationException {
        JsonArray trades = GsonHelper.getAsJsonArray(json, "Trades");
        this.trades = new ArrayList();
        for (int i = 0; i < trades.size() && this.trades.size() < 100; i++) {
            try {
                JsonObject tradeData = trades.get(i).getAsJsonObject();
                ItemTradeData newTrade = new ItemTradeData(false);
                newTrade.setItem(FileUtil.parseItemStack(GsonHelper.getAsJsonObject(tradeData, "SellItem")), 0);
                if (tradeData.has("SellItem2")) {
                    newTrade.setItem(FileUtil.parseItemStack(GsonHelper.getAsJsonObject(tradeData, "SellItem2")), 1);
                }
                if (tradeData.has("TradeType")) {
                    newTrade.setTradeType(ItemTradeData.loadTradeType(GsonHelper.getAsString(tradeData, "TradeType")));
                }
                if (tradeData.has("Price")) {
                    if (newTrade.isBarter()) {
                        LightmansCurrency.LogWarning("Price is being defined for a barter trade. Price will be ignored.");
                    } else {
                        newTrade.setCost(MoneyValue.loadFromJson(tradeData.get("Price")));
                    }
                } else if (!newTrade.isBarter()) {
                    LightmansCurrency.LogWarning("Price is not defined on a non-barter trade. Price will be assumed to be free.");
                    newTrade.setCost(MoneyValue.free());
                }
                if (tradeData.has("BarterItem")) {
                    if (newTrade.isBarter()) {
                        newTrade.setItem(FileUtil.parseItemStack(GsonHelper.getAsJsonObject(tradeData, "BarterItem")), 2);
                        if (tradeData.has("BarterItem2")) {
                            newTrade.setItem(FileUtil.parseItemStack(GsonHelper.getAsJsonObject(tradeData, "BarterItem2")), 3);
                        }
                    } else {
                        LightmansCurrency.LogWarning("BarterItem is being defined for a non-barter trade. Barter item will be ignored.");
                    }
                }
                if (tradeData.has("DisplayName")) {
                    newTrade.setCustomName(0, GsonHelper.getAsString(tradeData, "DisplayName"));
                }
                if (tradeData.has("DisplayName2")) {
                    newTrade.setCustomName(1, GsonHelper.getAsString(tradeData, "DisplayName2"));
                }
                if (tradeData.has("Rules")) {
                    newTrade.setRules(TradeRule.Parse(GsonHelper.getAsJsonArray(tradeData, "Rules"), newTrade));
                }
                if (tradeData.has("IgnoreNBT")) {
                    JsonArray ignoreNBTData = GsonHelper.getAsJsonArray(tradeData, "IgnoreNBT");
                    for (int j = 0; j < ignoreNBTData.size(); j++) {
                        int slot = ignoreNBTData.get(j).getAsInt();
                        newTrade.setEnforceNBT(slot, false);
                    }
                }
                this.trades.add(newTrade);
            } catch (Exception var10) {
                LightmansCurrency.LogError("Error parsing item trade at index " + i, var10);
            }
        }
        if (this.trades.isEmpty()) {
            throw new JsonSyntaxException("Trader has no valid trades!");
        } else {
            List<ItemStack> storage = new ArrayList();
            if (json.has("RelevantStorage")) {
                JsonArray storageData = json.getAsJsonArray("RelevantStorage");
                for (int i = 0; i < storageData.size(); i++) {
                    try {
                        ItemStack item = FileUtil.parseItemStack(storageData.get(i).getAsJsonObject());
                        storage.add(item);
                    } catch (ResourceLocationException | JsonSyntaxException var9) {
                        LightmansCurrency.LogError("Error parsing storage item at index " + i, var9);
                    }
                }
            }
            this.storage = new TraderItemStorage.LockedTraderStorage(this, storage);
        }
    }

    @Override
    protected void saveAdditionalPersistentData(CompoundTag compound) {
        ListTag tradePersistentData = new ListTag();
        boolean tradesAreRelevant = false;
        for (ItemTradeData trade : this.trades) {
            CompoundTag ptTag = new CompoundTag();
            if (TradeRule.savePersistentData(ptTag, trade.getRules(), "RuleData")) {
                tradesAreRelevant = true;
            }
            tradePersistentData.add(ptTag);
        }
        if (tradesAreRelevant) {
            compound.put("PersistentTradeData", tradePersistentData);
        }
    }

    @Override
    protected void loadAdditionalPersistentData(CompoundTag compound) {
        if (compound.contains("PersistentTradeData")) {
            ListTag tradePersistentData = compound.getList("PersistentTradeData", 10);
            for (int i = 0; i < tradePersistentData.size() && i < this.trades.size(); i++) {
                ItemTradeData trade = (ItemTradeData) this.trades.get(i);
                CompoundTag ptTag = tradePersistentData.getCompound(i);
                TradeRule.loadPersistentData(ptTag, trade.getRules(), "RuleData");
            }
        }
    }

    @Override
    protected void getAdditionalContents(List<ItemStack> results) {
        results.addAll(this.storage.getSplitContents());
    }

    @Override
    protected TradeResult ExecuteTrade(TradeContext context, int tradeIndex) {
        ItemTradeData trade = this.getTrade(tradeIndex);
        if (trade == null) {
            LightmansCurrency.LogDebug("Trade at index " + tradeIndex + " is null. Cannot execute trade!");
            return TradeResult.FAIL_INVALID_TRADE;
        } else if (!trade.isValid()) {
            LightmansCurrency.LogDebug("Trade at index " + tradeIndex + " is not a valid trade. Cannot execute trade.");
            return TradeResult.FAIL_INVALID_TRADE;
        } else if (!context.hasPlayerReference()) {
            return TradeResult.FAIL_NULL;
        } else if (this.runPreTradeEvent(trade, context).isCanceled()) {
            return TradeResult.FAIL_TRADE_RULE_DENIAL;
        } else {
            MoneyValue price = trade.getCost(context);
            if (trade.isSale()) {
                if (trade.outOfStock(context) && !this.isCreative()) {
                    LightmansCurrency.LogDebug("Not enough items in storage to carry out the trade at index " + tradeIndex + ". Cannot execute trade.");
                    return TradeResult.FAIL_OUT_OF_STOCK;
                } else {
                    List<ItemStack> soldItems = trade.getRandomSellItems(this);
                    if (soldItems == null) {
                        LightmansCurrency.LogDebug("Not enough items in storage to carry out the trade at index " + tradeIndex + ". Cannot execute trade.");
                        return TradeResult.FAIL_OUT_OF_STOCK;
                    } else if (!context.canFitItems(soldItems)) {
                        LightmansCurrency.LogDebug("Not enough room for the output item. Aborting trade!");
                        return TradeResult.FAIL_NO_OUTPUT_SPACE;
                    } else if (!context.getPayment(price)) {
                        LightmansCurrency.LogDebug("Not enough money is present for the trade at index " + tradeIndex + ". Cannot execute trade.\nPrice: " + price.getString("Null") + "\nAvailable Funds: " + context.getAvailableFunds().getString());
                        return TradeResult.FAIL_CANNOT_AFFORD;
                    } else {
                        for (int i = 0; i < soldItems.size(); i++) {
                            if (!context.putItem((ItemStack) soldItems.get(i))) {
                                LightmansCurrency.LogError("Not enough room for the output item. Giving refund & aborting Trade!");
                                for (int x = 0; x < i; x++) {
                                    context.collectItem((ItemStack) soldItems.get(x));
                                }
                                context.givePayment(price);
                                return TradeResult.FAIL_NO_OUTPUT_SPACE;
                            }
                        }
                        MoneyValue taxesPaid = MoneyValue.empty();
                        if (!this.isCreative()) {
                            trade.RemoveItemsFromStorage(this.getStorage(), soldItems);
                            this.markStorageDirty();
                            taxesPaid = this.addStoredMoney(price, true);
                            if (!trade.hasStock(this)) {
                                this.pushNotification(OutOfStockNotification.create(this.getNotificationCategory(), tradeIndex));
                            }
                        }
                        this.incrementStat(StatKeys.Traders.MONEY_EARNED, price);
                        if (!taxesPaid.isEmpty()) {
                            this.incrementStat(StatKeys.Taxables.TAXES_PAID, taxesPaid);
                        }
                        this.pushNotification(ItemTradeNotification.create(trade, price, context.getPlayerReference(), this.getNotificationCategory(), taxesPaid));
                        this.runPostTradeEvent(trade, context, price, taxesPaid);
                        return TradeResult.SUCCESS;
                    }
                }
            } else if (trade.isPurchase()) {
                List<ItemStack> collectableItems = context.getCollectableItems(trade.getItemRequirement(0), trade.getItemRequirement(1));
                if (!context.hasItems(collectableItems)) {
                    LightmansCurrency.LogDebug("Not enough items in the item slots to make the purchase.");
                    return TradeResult.FAIL_CANNOT_AFFORD;
                } else if (!trade.hasSpace(this, collectableItems) && !this.isCreative()) {
                    LightmansCurrency.LogDebug("Not enough room in storage to store the purchased items.");
                    return TradeResult.FAIL_NO_INPUT_SPACE;
                } else if (trade.outOfStock(context) && !this.isCreative()) {
                    LightmansCurrency.LogDebug("Not enough money in storage to pay for the purchased items.");
                    return TradeResult.FAIL_OUT_OF_STOCK;
                } else {
                    context.collectItems(collectableItems);
                    context.givePayment(price);
                    MoneyValue taxesPaidx = MoneyValue.empty();
                    if (!this.isCreative()) {
                        for (ItemStack item : collectableItems) {
                            this.getStorage().forceAddItem(item);
                        }
                        this.markStorageDirty();
                        taxesPaidx = this.removeStoredMoney(price, true);
                        if (!trade.hasStock(this)) {
                            this.pushNotification(OutOfStockNotification.create(this.getNotificationCategory(), tradeIndex));
                        }
                    }
                    this.incrementStat(StatKeys.Traders.MONEY_PAID, price);
                    if (!taxesPaidx.isEmpty()) {
                        this.incrementStat(StatKeys.Taxables.TAXES_PAID, taxesPaidx);
                    }
                    this.pushNotification(ItemTradeNotification.create(trade, price, context.getPlayerReference(), this.getNotificationCategory(), taxesPaidx));
                    this.runPostTradeEvent(trade, context, price, taxesPaidx);
                    return TradeResult.SUCCESS;
                }
            } else if (!trade.isBarter()) {
                return TradeResult.FAIL_INVALID_TRADE;
            } else {
                List<ItemStack> collectableItems = context.getCollectableItems(trade.getItemRequirement(2), trade.getItemRequirement(3));
                if (collectableItems == null) {
                    LightmansCurrency.LogDebug("Collectable items returned a null list!");
                    return TradeResult.FAIL_CANNOT_AFFORD;
                } else if (!trade.hasSpace(this, collectableItems) && !this.isCreative()) {
                    LightmansCurrency.LogDebug("Not enough room in storage to store the purchased items.");
                    return TradeResult.FAIL_NO_INPUT_SPACE;
                } else if (trade.outOfStock(context) && !this.isCreative()) {
                    LightmansCurrency.LogDebug("Not enough items in storage to carry out the trade at index " + tradeIndex + ". Cannot execute trade.");
                    return TradeResult.FAIL_OUT_OF_STOCK;
                } else {
                    List<ItemStack> soldItems = trade.getRandomSellItems(this);
                    if (soldItems == null) {
                        LightmansCurrency.LogDebug("Not enough items in storage to carry out the trade at index " + tradeIndex + ". Cannot execute trade.");
                        return TradeResult.FAIL_OUT_OF_STOCK;
                    } else if (!context.canFitItems(soldItems)) {
                        LightmansCurrency.LogDebug("Not enough space to store the purchased items.");
                        return TradeResult.FAIL_NO_OUTPUT_SPACE;
                    } else {
                        context.collectItems(collectableItems);
                        for (int ix = 0; ix < soldItems.size(); ix++) {
                            if (!context.putItem((ItemStack) soldItems.get(ix))) {
                                LightmansCurrency.LogError("Not enough room for the output item. Giving refund & aborting Trade!");
                                for (int x = 0; x < ix; x++) {
                                    context.collectItem((ItemStack) soldItems.get(x));
                                }
                                context.givePayment(price);
                                return TradeResult.FAIL_NO_OUTPUT_SPACE;
                            }
                        }
                        this.pushNotification(ItemTradeNotification.create(trade, price, context.getPlayerReference(), this.getNotificationCategory(), MoneyValue.empty()));
                        if (!this.isCreative()) {
                            for (ItemStack item : collectableItems) {
                                this.storage.forceAddItem(item);
                            }
                            trade.RemoveItemsFromStorage(this.getStorage(), soldItems);
                            this.markStorageDirty();
                            if (!trade.hasStock(this)) {
                                this.pushNotification(OutOfStockNotification.create(this.getNotificationCategory(), tradeIndex));
                            }
                        }
                        this.runPostTradeEvent(trade, context, price, MoneyValue.empty());
                        return TradeResult.SUCCESS;
                    }
                }
            }
        }
    }

    @Override
    public boolean canMakePersistent() {
        return true;
    }

    @Override
    public void initStorageTabs(@Nonnull ITraderStorageMenu menu) {
        menu.setTab(1, new ItemStorageTab(menu));
        menu.setTab(2, new ItemTradeEditTab(menu));
    }

    @Override
    public boolean isItemRelevant(ItemStack item) {
        for (ItemTradeData trade : this.trades) {
            if (trade.allowItemInStorage(item)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getStorageStackLimit() {
        int limit = 576;
        for (int i = 0; i < this.getUpgrades().getContainerSize(); i++) {
            ItemStack stack = this.getUpgrades().getItem(i);
            Item var5 = stack.getItem();
            if (var5 instanceof UpgradeItem) {
                UpgradeItem upgradeItem = (UpgradeItem) var5;
                if (this.allowUpgrade(upgradeItem) && upgradeItem.getUpgradeType() instanceof CapacityUpgrade) {
                    limit += UpgradeItem.getUpgradeData(stack).getIntValue(CapacityUpgrade.CAPACITY);
                }
            }
        }
        return limit;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction relativeSide) {
        return ForgeCapabilities.ITEM_HANDLER.orEmpty(cap, LazyOptional.of(() -> this.getItemHandler(relativeSide)));
    }

    @Override
    protected void appendTerminalInfo(@Nonnull List<Component> list, @Nullable Player player) {
        int tradeCount = 0;
        int outOfStock = 0;
        for (ItemTradeData trade : this.trades) {
            if (trade.isValid()) {
                tradeCount++;
                if (!this.isCreative() && !trade.hasStock(this)) {
                    outOfStock++;
                }
            }
        }
        list.add(LCText.TOOLTIP_NETWORK_TERMINAL_TRADE_COUNT.get(tradeCount));
        if (outOfStock > 0) {
            list.add(LCText.TOOLTIP_NETWORK_TERMINAL_OUT_OF_STOCK_COUNT.get(outOfStock));
        }
    }
}