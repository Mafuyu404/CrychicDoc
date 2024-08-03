package io.github.lightman314.lightmanscurrency.common.menus.wallet;

import io.github.lightman314.lightmanscurrency.LCText;
import io.github.lightman314.lightmanscurrency.LightmansCurrency;
import io.github.lightman314.lightmanscurrency.api.money.coins.CoinAPI;
import io.github.lightman314.lightmanscurrency.common.items.WalletItem;
import io.github.lightman314.lightmanscurrency.common.menus.providers.WalletBankMenuProvider;
import io.github.lightman314.lightmanscurrency.common.menus.providers.WalletMenuProvider;
import io.github.lightman314.lightmanscurrency.common.menus.slots.BlacklistSlot;
import io.github.lightman314.lightmanscurrency.common.menus.slots.CoinSlot;
import io.github.lightman314.lightmanscurrency.common.menus.slots.DisplaySlot;
import io.github.lightman314.lightmanscurrency.common.menus.validation.EasyMenu;
import io.github.lightman314.lightmanscurrency.util.InventoryUtil;
import io.github.lightman314.lightmanscurrency.util.MathUtil;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkHooks;

public abstract class WalletMenuBase extends EasyMenu {

    private static int maxWalletSlots = 0;

    protected final Container dummyInventory = new SimpleContainer(1);

    protected final int walletStackIndex;

    protected final Inventory inventory;

    private boolean autoConvert;

    protected final SimpleContainer coinInput;

    protected final WalletItem walletItem;

    public static int getMaxWalletSlots() {
        return maxWalletSlots;
    }

    public static void updateMaxWalletSlots(int slotCount) {
        maxWalletSlots = Math.max(maxWalletSlots, slotCount);
    }

    public final boolean isEquippedWallet() {
        return this.walletStackIndex < 0;
    }

    public final int getWalletStackIndex() {
        return this.walletStackIndex;
    }

    public final boolean hasWallet() {
        ItemStack wallet = this.getWallet();
        return !wallet.isEmpty() && wallet.getItem() instanceof WalletItem;
    }

    public final ItemStack getWallet() {
        return this.isEquippedWallet() ? CoinAPI.API.getEquippedWallet(this.inventory.player) : this.inventory.getItem(this.walletStackIndex);
    }

    public boolean canExchange() {
        return WalletItem.CanExchange(this.walletItem);
    }

    public boolean canPickup() {
        return WalletItem.CanPickup(this.walletItem);
    }

    public boolean hasBankAccess() {
        return WalletItem.HasBankAccess(this.walletItem);
    }

    public boolean getAutoExchange() {
        return this.autoConvert;
    }

    public void ToggleAutoExchange() {
        this.autoConvert = !this.autoConvert;
        this.saveWalletContents();
    }

    public Player getPlayer() {
        return this.player;
    }

    protected WalletMenuBase(MenuType<?> type, int windowID, Inventory inventory, int walletStackIndex) {
        super(type, windowID, inventory);
        this.inventory = inventory;
        this.walletStackIndex = walletStackIndex;
        if (this.getWallet().getItem() instanceof WalletItem wi) {
            this.walletItem = wi;
        } else {
            this.walletItem = null;
        }
        this.coinInput = new SimpleContainer(WalletItem.InventorySize(this.walletItem));
        this.reloadWalletContents();
        this.autoConvert = WalletItem.getAutoExchange(this.getWallet());
    }

    protected final void addInventorySlot(int x, int y, int index) {
        if (index == this.walletStackIndex) {
            this.m_38897_(new DisplaySlot(this.inventory, index, x, y));
        } else {
            this.m_38897_(new BlacklistSlot(this.inventory, index, x, y, this.inventory, this.walletStackIndex));
        }
    }

    protected final void addCoinSlots(int yPosition) {
        for (int y = 0; y * 9 < this.coinInput.getContainerSize(); y++) {
            for (int x = 0; x < 9 && x + y * 9 < this.coinInput.getContainerSize(); x++) {
                this.m_38897_(new CoinSlot(this.coinInput, x + y * 9, 8 + x * 18, yPosition + y * 18).addListener(this::saveWalletContents));
            }
        }
    }

    protected final void addDummySlots(int slotLimit) {
        while (this.f_38839_.size() < slotLimit) {
            this.m_38897_(new DisplaySlot(this.dummyInventory, 0, 1073741823, 1073741823));
        }
    }

    public final void reloadWalletContents() {
        Container walletInventory = WalletItem.getWalletInventory(this.getWallet());
        for (int i = 0; i < this.coinInput.getContainerSize() && i < walletInventory.getContainerSize(); i++) {
            this.coinInput.setItem(i, walletInventory.getItem(i));
        }
    }

    public final int getRowCount() {
        return 1 + (this.coinInput.getContainerSize() - 1) / 9;
    }

    public final int getSlotCount() {
        return this.coinInput.getContainerSize();
    }

    @Override
    protected void onValidationTick(@Nonnull Player player) {
        this.validateHasWallet();
    }

    public final boolean validateHasWallet() {
        if (!this.hasWallet()) {
            if (this.walletStackIndex < 0) {
                LightmansCurrency.LogWarning("Forcibly closing the wallet menu, as the player no longer has a wallet equipped!");
            } else {
                LightmansCurrency.LogWarning("Forcibly closing the wallet menu, as the player is no longer holding a wallet in slot " + this.walletStackIndex + "!");
            }
            this.player.closeContainer();
            return true;
        } else {
            return false;
        }
    }

    public final void saveWalletContents() {
        if (!this.validateHasWallet()) {
            WalletItem.putWalletInventory(this.getWallet(), this.coinInput);
            if (this.autoConvert != WalletItem.getAutoExchange(this.getWallet())) {
                WalletItem.toggleAutoExchange(this.getWallet());
            }
        }
    }

    public final void ExchangeCoints() {
        CoinAPI.API.CoinExchangeAllUp(this.coinInput);
        CoinAPI.API.SortCoinsByValue(this.coinInput);
        this.saveWalletContents();
    }

    public final ItemStack PickupCoins(ItemStack stack) {
        ItemStack returnValue = stack.copy();
        for (int i = 0; i < this.coinInput.getContainerSize() && !returnValue.isEmpty(); i++) {
            ItemStack thisStack = this.coinInput.getItem(i);
            if (thisStack.isEmpty()) {
                this.coinInput.setItem(i, returnValue.copy());
                returnValue = ItemStack.EMPTY;
            } else if (InventoryUtil.ItemMatches(thisStack, returnValue)) {
                int amountToAdd = MathUtil.clamp(returnValue.getCount(), 0, thisStack.getMaxStackSize() - thisStack.getCount());
                thisStack.setCount(thisStack.getCount() + amountToAdd);
                returnValue.setCount(returnValue.getCount() - amountToAdd);
            }
        }
        if (this.autoConvert) {
            this.ExchangeCoints();
        } else {
            this.saveWalletContents();
        }
        return returnValue;
    }

    public static void OnWalletUpdated(Entity entity) {
        if (entity instanceof Player player && player.containerMenu instanceof WalletMenuBase menu) {
            menu.reloadWalletContents();
        }
    }

    public static void SafeOpenWalletMenu(@Nonnull ServerPlayer player, int walletIndex) {
        SafeOpenWallet(player, walletIndex, new WalletMenuProvider(walletIndex));
    }

    public static void SafeOpenWalletBankMenu(@Nonnull ServerPlayer player, int walletIndex) {
        SafeOpenWallet(player, walletIndex, new WalletBankMenuProvider(walletIndex));
    }

    public static void SafeOpenWallet(@Nonnull ServerPlayer player, int walletIndex, @Nonnull MenuProvider menu) {
        SafeOpenWallet(player, walletIndex, menu, new WalletMenuBase.WalletDataWriter(walletIndex));
    }

    public static void SafeOpenWallet(@Nonnull ServerPlayer player, int walletIndex, @Nonnull MenuProvider menu, @Nonnull Consumer<FriendlyByteBuf> dataWriter) {
        if (walletIndex < 0) {
            if (!WalletItem.isWallet(CoinAPI.API.getEquippedWallet(player))) {
                player.sendSystemMessage(LCText.MESSAGE_WALLET_NONE_EQUIPPED.get());
                return;
            }
            NetworkHooks.openScreen(player, menu, dataWriter);
        } else {
            Inventory inventory = player.m_150109_();
            if (walletIndex >= inventory.getContainerSize()) {
                return;
            }
            if (!WalletItem.isWallet(inventory.getItem(walletIndex))) {
                return;
            }
            NetworkHooks.openScreen(player, menu, dataWriter);
        }
    }

    public static record WalletDataWriter(int walletIndex) implements Consumer<FriendlyByteBuf> {

        public void accept(FriendlyByteBuf buffer) {
            buffer.writeInt(this.walletIndex);
        }
    }
}