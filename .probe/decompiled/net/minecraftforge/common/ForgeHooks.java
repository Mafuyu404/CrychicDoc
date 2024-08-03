package net.minecraftforge.common;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.Lifecycle;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import net.minecraft.ChatFormatting;
import net.minecraft.ResourceLocationException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.ChatDecorator;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagKey;
import net.minecraft.util.CrudeIncrementalIntIdentityHashBiMap;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.util.datafix.fixes.StructuresBecomeConfiguredFix;
import net.minecraft.world.Container;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackLinkedSet;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TippedArrowItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.WorldData;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifierManager;
import net.minecraftforge.common.loot.LootTableIdCondition;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.common.util.BrainBuilder;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.common.util.MavenVersionStringHelper;
import net.minecraftforge.common.util.MutableHashedLinkedMap;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.DifficultyChangeEvent;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.GrindstoneEvent;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.ItemStackedOnOtherEvent;
import net.minecraftforge.event.ModMismatchEvent;
import net.minecraftforge.event.RegisterStructureConversionsEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.VanillaGameEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.EnderManAngerEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingBreatheEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingDrownEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingGetProjectileEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.living.LivingMakeBrainEvent;
import net.minecraftforge.event.entity.living.LivingSwapItemsEvent;
import net.minecraftforge.event.entity.living.LivingUseTotemEvent;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.NoteBlockEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoader;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.RegistryManager;
import net.minecraftforge.resource.ResourcePackLoader;
import net.minecraftforge.server.permission.PermissionAPI;
import org.apache.commons.lang3.function.TriFunction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.ApiStatus.Internal;

public class ForgeHooks {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final Marker FORGEHOOKS = MarkerManager.getMarker("FORGEHOOKS");

    private static final Marker WORLDPERSISTENCE = MarkerManager.getMarker("WP");

    static final Pattern URL_PATTERN = Pattern.compile("((?:[a-z0-9]{2,}:\\/\\/)?(?:(?:[0-9]{1,3}\\.){3}[0-9]{1,3}|(?:[-\\w_]{1,}\\.[a-z]{2,}?))(?::[0-9]{1,5})?.*?(?=[!\"§ \n]|$))", 2);

    private static ThreadLocal<Player> craftingPlayer = new ThreadLocal();

    private static final ThreadLocal<Deque<ForgeHooks.LootTableContext>> lootContext = new ThreadLocal();

    private static final Map<Holder.Reference<Item>, Integer> VANILLA_BURNS = new HashMap();

    private static final Set<String> VANILLA_DIMS = Sets.newHashSet(new String[] { "minecraft:overworld", "minecraft:the_nether", "minecraft:the_end" });

    private static final String DIMENSIONS_KEY = "dimensions";

    private static final String SEED_KEY = "seed";

    private static final Map<EntityType<? extends LivingEntity>, AttributeSupplier> FORGE_ATTRIBUTES = new HashMap();

    private static final Lazy<Map<String, StructuresBecomeConfiguredFix.Conversion>> FORGE_CONVERSION_MAP = Lazy.concurrentOf(() -> {
        Map<String, StructuresBecomeConfiguredFix.Conversion> map = new HashMap();
        MinecraftForge.EVENT_BUS.post(new RegisterStructureConversionsEvent(map));
        return ImmutableMap.copyOf(map);
    });

    public static boolean canContinueUsing(@NotNull ItemStack from, @NotNull ItemStack to) {
        return !from.isEmpty() && !to.isEmpty() ? from.getItem().canContinueUsing(from, to) : false;
    }

    public static boolean isCorrectToolForDrops(@NotNull BlockState state, @NotNull Player player) {
        return !state.m_60834_() ? ForgeEventFactory.doPlayerHarvestCheck(player, state, true) : player.hasCorrectToolForDrops(state);
    }

    public static boolean onItemStackedOn(ItemStack carriedItem, ItemStack stackedOnItem, Slot slot, ClickAction action, Player player, SlotAccess carriedSlotAccess) {
        return MinecraftForge.EVENT_BUS.post(new ItemStackedOnOtherEvent(carriedItem, stackedOnItem, slot, action, player, carriedSlotAccess));
    }

    public static void onDifficultyChange(Difficulty difficulty, Difficulty oldDifficulty) {
        MinecraftForge.EVENT_BUS.post(new DifficultyChangeEvent(difficulty, oldDifficulty));
    }

    public static LivingChangeTargetEvent onLivingChangeTarget(LivingEntity entity, LivingEntity originalTarget, LivingChangeTargetEvent.ILivingTargetType targetType) {
        LivingChangeTargetEvent event = new LivingChangeTargetEvent(entity, originalTarget, targetType);
        MinecraftForge.EVENT_BUS.post(event);
        return event;
    }

    public static Brain<?> onLivingMakeBrain(LivingEntity entity, Brain<?> originalBrain, Dynamic<?> dynamic) {
        BrainBuilder<?> brainBuilder = originalBrain.createBuilder();
        LivingMakeBrainEvent event = new LivingMakeBrainEvent(entity, brainBuilder);
        MinecraftForge.EVENT_BUS.post(event);
        return brainBuilder.makeBrain(dynamic);
    }

    public static boolean onLivingTick(LivingEntity entity) {
        return MinecraftForge.EVENT_BUS.post(new LivingEvent.LivingTickEvent(entity));
    }

    public static boolean onLivingAttack(LivingEntity entity, DamageSource src, float amount) {
        return entity instanceof Player || !MinecraftForge.EVENT_BUS.post(new LivingAttackEvent(entity, src, amount));
    }

    public static boolean onPlayerAttack(LivingEntity entity, DamageSource src, float amount) {
        return !MinecraftForge.EVENT_BUS.post(new LivingAttackEvent(entity, src, amount));
    }

    public static LivingKnockBackEvent onLivingKnockBack(LivingEntity target, float strength, double ratioX, double ratioZ) {
        LivingKnockBackEvent event = new LivingKnockBackEvent(target, strength, ratioX, ratioZ);
        MinecraftForge.EVENT_BUS.post(event);
        return event;
    }

    public static boolean onLivingUseTotem(LivingEntity entity, DamageSource damageSource, ItemStack totem, InteractionHand hand) {
        return !MinecraftForge.EVENT_BUS.post(new LivingUseTotemEvent(entity, damageSource, totem, hand));
    }

    public static float onLivingHurt(LivingEntity entity, DamageSource src, float amount) {
        LivingHurtEvent event = new LivingHurtEvent(entity, src, amount);
        return MinecraftForge.EVENT_BUS.post(event) ? 0.0F : event.getAmount();
    }

    public static float onLivingDamage(LivingEntity entity, DamageSource src, float amount) {
        LivingDamageEvent event = new LivingDamageEvent(entity, src, amount);
        return MinecraftForge.EVENT_BUS.post(event) ? 0.0F : event.getAmount();
    }

    public static boolean onLivingDeath(LivingEntity entity, DamageSource src) {
        return MinecraftForge.EVENT_BUS.post(new LivingDeathEvent(entity, src));
    }

    public static boolean onLivingDrops(LivingEntity entity, DamageSource source, Collection<ItemEntity> drops, int lootingLevel, boolean recentlyHit) {
        return MinecraftForge.EVENT_BUS.post(new LivingDropsEvent(entity, source, drops, lootingLevel, recentlyHit));
    }

    @Nullable
    public static float[] onLivingFall(LivingEntity entity, float distance, float damageMultiplier) {
        LivingFallEvent event = new LivingFallEvent(entity, distance, damageMultiplier);
        return MinecraftForge.EVENT_BUS.post(event) ? null : new float[] { event.getDistance(), event.getDamageMultiplier() };
    }

    public static int getLootingLevel(Entity target, @Nullable Entity killer, @Nullable DamageSource cause) {
        int looting = 0;
        if (killer instanceof LivingEntity) {
            looting = EnchantmentHelper.getMobLooting((LivingEntity) killer);
        }
        if (target instanceof LivingEntity) {
            looting = getLootingLevel((LivingEntity) target, cause, looting);
        }
        return looting;
    }

    public static int getLootingLevel(LivingEntity target, @Nullable DamageSource cause, int level) {
        LootingLevelEvent event = new LootingLevelEvent(target, cause, level);
        MinecraftForge.EVENT_BUS.post(event);
        return event.getLootingLevel();
    }

    public static double getEntityVisibilityMultiplier(LivingEntity entity, Entity lookingEntity, double originalMultiplier) {
        LivingEvent.LivingVisibilityEvent event = new LivingEvent.LivingVisibilityEvent(entity, lookingEntity, originalMultiplier);
        MinecraftForge.EVENT_BUS.post(event);
        return Math.max(0.0, event.getVisibilityModifier());
    }

    public static Optional<BlockPos> isLivingOnLadder(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull LivingEntity entity) {
        boolean isSpectator = entity instanceof Player && entity.m_5833_();
        if (isSpectator) {
            return Optional.empty();
        } else if (!ForgeConfig.SERVER.fullBoundingBoxLadders.get()) {
            return state.isLadder(level, pos, entity) ? Optional.of(pos) : Optional.empty();
        } else {
            AABB bb = entity.m_20191_();
            int mX = Mth.floor(bb.minX);
            int mY = Mth.floor(bb.minY);
            int mZ = Mth.floor(bb.minZ);
            for (int y2 = mY; (double) y2 < bb.maxY; y2++) {
                for (int x2 = mX; (double) x2 < bb.maxX; x2++) {
                    for (int z2 = mZ; (double) z2 < bb.maxZ; z2++) {
                        BlockPos tmp = new BlockPos(x2, y2, z2);
                        state = level.getBlockState(tmp);
                        if (state.isLadder(level, tmp, entity)) {
                            return Optional.of(tmp);
                        }
                    }
                }
            }
            return Optional.empty();
        }
    }

    public static void onLivingJump(LivingEntity entity) {
        MinecraftForge.EVENT_BUS.post(new LivingEvent.LivingJumpEvent(entity));
    }

    @Nullable
    public static ItemEntity onPlayerTossEvent(@NotNull Player player, @NotNull ItemStack item, boolean includeName) {
        player.captureDrops(Lists.newArrayList());
        ItemEntity ret = player.drop(item, false, includeName);
        player.captureDrops(null);
        if (ret == null) {
            return null;
        } else {
            ItemTossEvent event = new ItemTossEvent(ret, player);
            if (MinecraftForge.EVENT_BUS.post(event)) {
                return null;
            } else {
                if (!player.m_9236_().isClientSide) {
                    player.m_20193_().m_7967_(event.getEntity());
                }
                return event.getEntity();
            }
        }
    }

    public static boolean onVanillaGameEvent(Level level, GameEvent vanillaEvent, Vec3 pos, GameEvent.Context context) {
        return !MinecraftForge.EVENT_BUS.post(new VanillaGameEvent(level, vanillaEvent, pos, context));
    }

    private static String getRawText(Component message) {
        return message.getContents() instanceof LiteralContents literalContents ? literalContents.text() : "";
    }

    @Nullable
    public static Component onServerChatSubmittedEvent(ServerPlayer player, String plain, Component decorated) {
        ServerChatEvent event = new ServerChatEvent(player, plain, decorated);
        return MinecraftForge.EVENT_BUS.post(event) ? null : event.getMessage();
    }

    @NotNull
    public static ChatDecorator getServerChatSubmittedDecorator() {
        return (sender, message) -> CompletableFuture.supplyAsync(() -> sender == null ? message : onServerChatSubmittedEvent(sender, getRawText(message), message));
    }

    public static Component newChatWithLinks(String string) {
        return newChatWithLinks(string, true);
    }

    public static Component newChatWithLinks(String string, boolean allowMissingHeader) {
        MutableComponent ichat = null;
        Matcher matcher = URL_PATTERN.matcher(string);
        int lastEnd = 0;
        while (true) {
            String url;
            MutableComponent link;
            while (true) {
                if (!matcher.find()) {
                    String end = string.substring(lastEnd);
                    if (ichat == null) {
                        ichat = Component.literal(end);
                    } else if (end.length() > 0) {
                        ichat.append(Component.literal(string.substring(lastEnd)));
                    }
                    return ichat;
                }
                int start = matcher.start();
                int end = matcher.end();
                String part = string.substring(lastEnd, start);
                if (part.length() > 0) {
                    if (ichat == null) {
                        ichat = Component.literal(part);
                    } else {
                        ichat.append(part);
                    }
                }
                lastEnd = end;
                url = string.substring(start, end);
                link = Component.literal(url);
                try {
                    if (new URI(url).getScheme() != null) {
                        break;
                    }
                    if (allowMissingHeader) {
                        url = "http://" + url;
                        break;
                    }
                    if (ichat == null) {
                        ichat = Component.literal(url);
                    } else {
                        ichat.append(url);
                    }
                } catch (URISyntaxException var11) {
                    if (ichat == null) {
                        ichat = Component.literal(url);
                    } else {
                        ichat.append(url);
                    }
                }
            }
            ClickEvent click = new ClickEvent(ClickEvent.Action.OPEN_URL, url);
            link.setStyle(link.getStyle().withClickEvent(click).withUnderlined(true).withColor(TextColor.fromLegacyFormat(ChatFormatting.BLUE)));
            if (ichat == null) {
                ichat = Component.literal("");
            }
            ichat.append(link);
        }
    }

    public static void dropXpForBlock(BlockState state, ServerLevel level, BlockPos pos, ItemStack stack) {
        int fortuneLevel = stack.getEnchantmentLevel(Enchantments.BLOCK_FORTUNE);
        int silkTouchLevel = stack.getEnchantmentLevel(Enchantments.SILK_TOUCH);
        int exp = state.getExpDrop(level, level.f_46441_, pos, fortuneLevel, silkTouchLevel);
        if (exp > 0) {
            state.m_60734_().popExperience(level, pos, exp);
        }
    }

    public static int onBlockBreakEvent(Level level, GameType gameType, ServerPlayer entityPlayer, BlockPos pos) {
        boolean preCancelEvent = false;
        ItemStack itemstack = entityPlayer.m_21205_();
        if (!itemstack.isEmpty() && !itemstack.getItem().canAttackBlock(level.getBlockState(pos), level, pos, entityPlayer)) {
            preCancelEvent = true;
        }
        if (gameType.isBlockPlacingRestricted()) {
            if (gameType == GameType.SPECTATOR) {
                preCancelEvent = true;
            }
            if (!entityPlayer.m_36326_() && (itemstack.isEmpty() || !itemstack.hasAdventureModeBreakTagForBlock(level.registryAccess().registryOrThrow(Registries.BLOCK), new BlockInWorld(level, pos, false)))) {
                preCancelEvent = true;
            }
        }
        if (level.getBlockEntity(pos) == null) {
            entityPlayer.connection.send(new ClientboundBlockUpdatePacket(pos, level.getFluidState(pos).createLegacyBlock()));
        }
        BlockState state = level.getBlockState(pos);
        BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(level, pos, state, entityPlayer);
        event.setCanceled(preCancelEvent);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled()) {
            entityPlayer.connection.send(new ClientboundBlockUpdatePacket(level, pos));
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity != null) {
                Packet<?> pkt = blockEntity.getUpdatePacket();
                if (pkt != null) {
                    entityPlayer.connection.send(pkt);
                }
            }
        }
        return event.isCanceled() ? -1 : event.getExpToDrop();
    }

    public static InteractionResult onPlaceItemIntoWorld(@NotNull UseOnContext context) {
        ItemStack itemstack = context.getItemInHand();
        Level level = context.getLevel();
        Player player = context.getPlayer();
        if (player != null && !player.getAbilities().mayBuild && !itemstack.hasAdventureModePlaceTagForBlock(level.registryAccess().registryOrThrow(Registries.BLOCK), new BlockInWorld(level, context.getClickedPos(), false))) {
            return InteractionResult.PASS;
        } else {
            Item item = itemstack.getItem();
            int size = itemstack.getCount();
            CompoundTag nbt = null;
            if (itemstack.getTag() != null) {
                nbt = itemstack.getTag().copy();
            }
            if (!(itemstack.getItem() instanceof BucketItem)) {
                level.captureBlockSnapshots = true;
            }
            ItemStack copy = itemstack.copy();
            InteractionResult ret = itemstack.getItem().useOn(context);
            if (itemstack.isEmpty()) {
                ForgeEventFactory.onPlayerDestroyItem(player, copy, context.getHand());
            }
            level.captureBlockSnapshots = false;
            if (ret.consumesAction()) {
                int newSize = itemstack.getCount();
                CompoundTag newNBT = null;
                if (itemstack.getTag() != null) {
                    newNBT = itemstack.getTag().copy();
                }
                List<BlockSnapshot> blockSnapshots = (List<BlockSnapshot>) level.capturedBlockSnapshots.clone();
                level.capturedBlockSnapshots.clear();
                itemstack.setCount(size);
                itemstack.setTag(nbt);
                Direction side = context.getClickedFace();
                boolean eventResult = false;
                if (blockSnapshots.size() > 1) {
                    eventResult = ForgeEventFactory.onMultiBlockPlace(player, blockSnapshots, side);
                } else if (blockSnapshots.size() == 1) {
                    eventResult = ForgeEventFactory.onBlockPlace(player, (BlockSnapshot) blockSnapshots.get(0), side);
                }
                if (eventResult) {
                    ret = InteractionResult.FAIL;
                    for (BlockSnapshot blocksnapshot : Lists.reverse(blockSnapshots)) {
                        level.restoringBlockSnapshots = true;
                        blocksnapshot.restore(true, false);
                        level.restoringBlockSnapshots = false;
                    }
                } else {
                    itemstack.setCount(newSize);
                    itemstack.setTag(newNBT);
                    for (BlockSnapshot snap : blockSnapshots) {
                        int updateFlag = snap.getFlag();
                        BlockState oldBlock = snap.getReplacedBlock();
                        BlockState newBlock = level.getBlockState(snap.getPos());
                        newBlock.m_60696_(level, snap.getPos(), oldBlock, false);
                        level.markAndNotifyBlock(snap.getPos(), level.getChunkAt(snap.getPos()), oldBlock, newBlock, updateFlag, 512);
                    }
                    if (player != null) {
                        player.awardStat(Stats.ITEM_USED.get(item));
                    }
                }
            }
            level.capturedBlockSnapshots.clear();
            return ret;
        }
    }

    public static boolean onAnvilChange(AnvilMenu container, @NotNull ItemStack left, @NotNull ItemStack right, Container outputSlot, String name, int baseCost, Player player) {
        AnvilUpdateEvent e = new AnvilUpdateEvent(left, right, name, baseCost, player);
        if (MinecraftForge.EVENT_BUS.post(e)) {
            return false;
        } else if (e.getOutput().isEmpty()) {
            return true;
        } else {
            outputSlot.setItem(0, e.getOutput());
            container.setMaximumCost(e.getCost());
            container.repairItemCountCost = e.getMaterialCost();
            return false;
        }
    }

    public static float onAnvilRepair(Player player, @NotNull ItemStack output, @NotNull ItemStack left, @NotNull ItemStack right) {
        AnvilRepairEvent e = new AnvilRepairEvent(player, left, right, output);
        MinecraftForge.EVENT_BUS.post(e);
        return e.getBreakChance();
    }

    public static int onGrindstoneChange(@NotNull ItemStack top, @NotNull ItemStack bottom, Container outputSlot, int xp) {
        GrindstoneEvent.OnPlaceItem e = new GrindstoneEvent.OnPlaceItem(top, bottom, xp);
        if (MinecraftForge.EVENT_BUS.post(e)) {
            outputSlot.setItem(0, ItemStack.EMPTY);
            return -1;
        } else if (e.getOutput().isEmpty()) {
            return Integer.MIN_VALUE;
        } else {
            outputSlot.setItem(0, e.getOutput());
            return e.getXp();
        }
    }

    public static boolean onGrindstoneTake(Container inputSlots, ContainerLevelAccess access, Function<Level, Integer> xpFunction) {
        access.execute((l, p) -> {
            int xp = (Integer) xpFunction.apply(l);
            GrindstoneEvent.OnTakeItem e = new GrindstoneEvent.OnTakeItem(inputSlots.getItem(0), inputSlots.getItem(1), xp);
            if (!MinecraftForge.EVENT_BUS.post(e)) {
                if (l instanceof ServerLevel) {
                    ExperienceOrb.award((ServerLevel) l, Vec3.atCenterOf(p), e.getXp());
                }
                l.m_46796_(1042, p, 0);
                inputSlots.setItem(0, e.getNewTopItem());
                inputSlots.setItem(1, e.getNewBottomItem());
                inputSlots.setChanged();
            }
        });
        return true;
    }

    public static void setCraftingPlayer(Player player) {
        craftingPlayer.set(player);
    }

    public static Player getCraftingPlayer() {
        return (Player) craftingPlayer.get();
    }

    @NotNull
    public static ItemStack getCraftingRemainingItem(@NotNull ItemStack stack) {
        if (stack.getItem().hasCraftingRemainingItem(stack)) {
            stack = stack.getItem().getCraftingRemainingItem(stack);
            if (!stack.isEmpty() && stack.isDamageableItem() && stack.getDamageValue() > stack.getMaxDamage()) {
                ForgeEventFactory.onPlayerDestroyItem((Player) craftingPlayer.get(), stack, null);
                return ItemStack.EMPTY;
            } else {
                return stack;
            }
        } else {
            return ItemStack.EMPTY;
        }
    }

    public static boolean onPlayerAttackTarget(Player player, Entity target) {
        if (MinecraftForge.EVENT_BUS.post(new AttackEntityEvent(player, target))) {
            return false;
        } else {
            ItemStack stack = player.m_21205_();
            return stack.isEmpty() || !stack.getItem().onLeftClickEntity(stack, player, target);
        }
    }

    public static boolean onTravelToDimension(Entity entity, ResourceKey<Level> dimension) {
        EntityTravelToDimensionEvent event = new EntityTravelToDimensionEvent(entity, dimension);
        MinecraftForge.EVENT_BUS.post(event);
        return !event.isCanceled();
    }

    public static InteractionResult onInteractEntityAt(Player player, Entity entity, HitResult ray, InteractionHand hand) {
        Vec3 vec3d = ray.getLocation().subtract(entity.position());
        return onInteractEntityAt(player, entity, vec3d, hand);
    }

    public static InteractionResult onInteractEntityAt(Player player, Entity entity, Vec3 vec3d, InteractionHand hand) {
        PlayerInteractEvent.EntityInteractSpecific evt = new PlayerInteractEvent.EntityInteractSpecific(player, hand, entity, vec3d);
        MinecraftForge.EVENT_BUS.post(evt);
        return evt.isCanceled() ? evt.getCancellationResult() : null;
    }

    public static InteractionResult onInteractEntity(Player player, Entity entity, InteractionHand hand) {
        PlayerInteractEvent.EntityInteract evt = new PlayerInteractEvent.EntityInteract(player, hand, entity);
        MinecraftForge.EVENT_BUS.post(evt);
        return evt.isCanceled() ? evt.getCancellationResult() : null;
    }

    public static InteractionResult onItemRightClick(Player player, InteractionHand hand) {
        PlayerInteractEvent.RightClickItem evt = new PlayerInteractEvent.RightClickItem(player, hand);
        MinecraftForge.EVENT_BUS.post(evt);
        return evt.isCanceled() ? evt.getCancellationResult() : null;
    }

    @Deprecated(since = "1.20.1", forRemoval = true)
    public static PlayerInteractEvent.LeftClickBlock onLeftClickBlock(Player player, BlockPos pos, Direction face) {
        return onLeftClickBlock(player, pos, face, ServerboundPlayerActionPacket.Action.START_DESTROY_BLOCK);
    }

    public static PlayerInteractEvent.LeftClickBlock onLeftClickBlock(Player player, BlockPos pos, Direction face, ServerboundPlayerActionPacket.Action action) {
        PlayerInteractEvent.LeftClickBlock evt = new PlayerInteractEvent.LeftClickBlock(player, pos, face, PlayerInteractEvent.LeftClickBlock.Action.convert(action));
        MinecraftForge.EVENT_BUS.post(evt);
        return evt;
    }

    public static PlayerInteractEvent.LeftClickBlock onClientMineHold(Player player, BlockPos pos, Direction face) {
        PlayerInteractEvent.LeftClickBlock evt = new PlayerInteractEvent.LeftClickBlock(player, pos, face, PlayerInteractEvent.LeftClickBlock.Action.CLIENT_HOLD);
        MinecraftForge.EVENT_BUS.post(evt);
        return evt;
    }

    public static PlayerInteractEvent.RightClickBlock onRightClickBlock(Player player, InteractionHand hand, BlockPos pos, BlockHitResult hitVec) {
        PlayerInteractEvent.RightClickBlock evt = new PlayerInteractEvent.RightClickBlock(player, hand, pos, hitVec);
        MinecraftForge.EVENT_BUS.post(evt);
        return evt;
    }

    public static void onEmptyClick(Player player, InteractionHand hand) {
        MinecraftForge.EVENT_BUS.post(new PlayerInteractEvent.RightClickEmpty(player, hand));
    }

    public static void onEmptyLeftClick(Player player) {
        MinecraftForge.EVENT_BUS.post(new PlayerInteractEvent.LeftClickEmpty(player));
    }

    @Nullable
    public static GameType onChangeGameType(Player player, GameType currentGameType, GameType newGameType) {
        if (currentGameType != newGameType) {
            PlayerEvent.PlayerChangeGameModeEvent evt = new PlayerEvent.PlayerChangeGameModeEvent(player, currentGameType, newGameType);
            MinecraftForge.EVENT_BUS.post(evt);
            return evt.isCanceled() ? null : evt.getNewGameMode();
        } else {
            return newGameType;
        }
    }

    private static ForgeHooks.LootTableContext getLootTableContext() {
        ForgeHooks.LootTableContext ctx = (ForgeHooks.LootTableContext) ((Deque) lootContext.get()).peek();
        if (ctx == null) {
            throw new JsonParseException("Invalid call stack, could not grab json context!");
        } else {
            return ctx;
        }
    }

    public static TriFunction<ResourceLocation, JsonElement, ResourceManager, Optional<LootTable>> getLootTableDeserializer(Gson gson, String directory) {
        return (location, data, resourceManager) -> {
            try {
                Resource resource = (Resource) resourceManager.m_213713_(location.withPath(directory + "/" + location.getPath() + ".json")).orElse(null);
                boolean custom = resource == null || !resource.isBuiltin();
                return Optional.ofNullable(loadLootTable(gson, location, data, custom));
            } catch (Exception var7) {
                LOGGER.error("Couldn't parse element {}:{}", directory, location, var7);
                return Optional.empty();
            }
        };
    }

    public static LootTable loadLootTable(Gson gson, ResourceLocation name, JsonElement data, boolean custom) {
        Deque<ForgeHooks.LootTableContext> que = (Deque<ForgeHooks.LootTableContext>) lootContext.get();
        if (que == null) {
            que = Queues.newArrayDeque();
            lootContext.set(que);
        }
        LootTable ret;
        try {
            que.push(new ForgeHooks.LootTableContext(name, custom));
            ret = (LootTable) gson.fromJson(data, LootTable.class);
            ret.setLootTableId(name);
        } catch (JsonParseException var10) {
            throw var10;
        } finally {
            que.pop();
        }
        if (!custom) {
            ret = ForgeEventFactory.loadLootTable(name, ret);
        }
        if (ret != null) {
            ret.freeze();
        }
        return ret;
    }

    public static String readPoolName(JsonObject json) {
        ForgeHooks.LootTableContext ctx = getLootTableContext();
        if (json.has("name")) {
            return GsonHelper.getAsString(json, "name");
        } else if (ctx.custom) {
            return "custom#" + json.hashCode();
        } else {
            ctx.poolCount++;
            return ctx.poolCount == 1 ? "main" : "pool" + (ctx.poolCount - 1);
        }
    }

    public static FluidType getVanillaFluidType(Fluid fluid) {
        if (fluid == Fluids.EMPTY) {
            return ForgeMod.EMPTY_TYPE.get();
        } else if (fluid == Fluids.WATER || fluid == Fluids.FLOWING_WATER) {
            return ForgeMod.WATER_TYPE.get();
        } else if (fluid != Fluids.LAVA && fluid != Fluids.FLOWING_LAVA) {
            if (!ForgeMod.MILK.filter(milk -> milk == fluid).isPresent() && !ForgeMod.FLOWING_MILK.filter(milk -> milk == fluid).isPresent()) {
                throw new RuntimeException("Mod fluids must override getFluidType.");
            } else {
                return ForgeMod.MILK_TYPE.get();
            }
        } else {
            return ForgeMod.LAVA_TYPE.get();
        }
    }

    public static TagKey<Block> getTagFromVanillaTier(Tiers tier) {
        return switch(tier) {
            case WOOD ->
                Tags.Blocks.NEEDS_WOOD_TOOL;
            case GOLD ->
                Tags.Blocks.NEEDS_GOLD_TOOL;
            case STONE ->
                BlockTags.NEEDS_STONE_TOOL;
            case IRON ->
                BlockTags.NEEDS_IRON_TOOL;
            case DIAMOND ->
                BlockTags.NEEDS_DIAMOND_TOOL;
            case NETHERITE ->
                Tags.Blocks.NEEDS_NETHERITE_TOOL;
        };
    }

    public static Collection<CreativeModeTab> onCheckCreativeTabs(CreativeModeTab... vanillaTabs) {
        List<CreativeModeTab> tabs = new ArrayList(Arrays.asList(vanillaTabs));
        return tabs;
    }

    public static boolean onCropsGrowPre(Level level, BlockPos pos, BlockState state, boolean def) {
        BlockEvent ev = new BlockEvent.CropGrowEvent.Pre(level, pos, state);
        MinecraftForge.EVENT_BUS.post(ev);
        return ev.getResult() == Result.ALLOW || ev.getResult() == Result.DEFAULT && def;
    }

    public static void onCropsGrowPost(Level level, BlockPos pos, BlockState state) {
        MinecraftForge.EVENT_BUS.post(new BlockEvent.CropGrowEvent.Post(level, pos, state, level.getBlockState(pos)));
    }

    @Nullable
    public static CriticalHitEvent getCriticalHit(Player player, Entity target, boolean vanillaCritical, float damageModifier) {
        CriticalHitEvent hitResult = new CriticalHitEvent(player, target, damageModifier, vanillaCritical);
        MinecraftForge.EVENT_BUS.post(hitResult);
        return hitResult.getResult() != Result.ALLOW && (!vanillaCritical || hitResult.getResult() != Result.DEFAULT) ? null : hitResult;
    }

    public static Multimap<Attribute, AttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot equipmentSlot, Multimap<Attribute, AttributeModifier> attributes) {
        ItemAttributeModifierEvent event = new ItemAttributeModifierEvent(stack, equipmentSlot, attributes);
        MinecraftForge.EVENT_BUS.post(event);
        return event.getModifiers();
    }

    public static ItemStack getProjectile(LivingEntity entity, ItemStack projectileWeaponItem, ItemStack projectile) {
        LivingGetProjectileEvent event = new LivingGetProjectileEvent(entity, projectileWeaponItem, projectile);
        MinecraftForge.EVENT_BUS.post(event);
        return event.getProjectileItemStack();
    }

    @Nullable
    public static String getDefaultCreatorModId(@NotNull ItemStack itemStack) {
        Item item = itemStack.getItem();
        ResourceLocation registryName = ForgeRegistries.ITEMS.getKey(item);
        String modId = registryName == null ? null : registryName.getNamespace();
        if ("minecraft".equals(modId)) {
            if (item instanceof EnchantedBookItem) {
                ListTag enchantmentsNbt = EnchantedBookItem.getEnchantments(itemStack);
                if (enchantmentsNbt.size() == 1) {
                    CompoundTag nbttagcompound = enchantmentsNbt.getCompound(0);
                    ResourceLocation resourceLocation = ResourceLocation.tryParse(nbttagcompound.getString("id"));
                    if (resourceLocation != null && ForgeRegistries.ENCHANTMENTS.containsKey(resourceLocation)) {
                        return resourceLocation.getNamespace();
                    }
                }
            } else if (item instanceof PotionItem || item instanceof TippedArrowItem) {
                Potion potionType = PotionUtils.getPotion(itemStack);
                ResourceLocation resourceLocation = ForgeRegistries.POTIONS.getKey(potionType);
                if (resourceLocation != null) {
                    return resourceLocation.getNamespace();
                }
            } else if (item instanceof SpawnEggItem) {
                ResourceLocation resourceLocation = ForgeRegistries.ENTITY_TYPES.getKey(((SpawnEggItem) item).getType(null));
                if (resourceLocation != null) {
                    return resourceLocation.getNamespace();
                }
            }
        }
        return modId;
    }

    public static boolean onFarmlandTrample(Level level, BlockPos pos, BlockState state, float fallDistance, Entity entity) {
        if (entity.canTrample(state, pos, fallDistance)) {
            BlockEvent.FarmlandTrampleEvent event = new BlockEvent.FarmlandTrampleEvent(level, pos, state, fallDistance, entity);
            MinecraftForge.EVENT_BUS.post(event);
            return !event.isCanceled();
        } else {
            return false;
        }
    }

    public static int onNoteChange(Level level, BlockPos pos, BlockState state, int old, int _new) {
        NoteBlockEvent.Change event = new NoteBlockEvent.Change(level, pos, state, old, _new);
        return MinecraftForge.EVENT_BUS.post(event) ? -1 : event.getVanillaNoteId();
    }

    public static boolean hasNoElements(Ingredient ingredient) {
        ItemStack[] items = ingredient.getItems();
        if (items.length == 0) {
            return true;
        } else if (items.length != 1) {
            return false;
        } else {
            ItemStack item = items[0];
            return item.getItem() == Items.BARRIER && item.getHoverName() instanceof MutableComponent hoverName && hoverName.getString().startsWith("Empty Tag: ");
        }
    }

    @Deprecated(forRemoval = true, since = "1.20.1")
    public static <T> void deserializeTagAdditions(List<TagEntry> list, JsonObject json, List<TagEntry> allList) {
    }

    @Nullable
    public static EntityDataSerializer<?> getSerializer(int id, CrudeIncrementalIntIdentityHashBiMap<EntityDataSerializer<?>> vanilla) {
        EntityDataSerializer<?> serializer = vanilla.byId(id);
        if (serializer == null) {
            ForgeRegistry<EntityDataSerializer<?>> registry = (ForgeRegistry<EntityDataSerializer<?>>) ForgeRegistries.ENTITY_DATA_SERIALIZERS.get();
            if (registry != null) {
                serializer = registry.getValue(id);
            }
        }
        return serializer;
    }

    public static int getSerializerId(EntityDataSerializer<?> serializer, CrudeIncrementalIntIdentityHashBiMap<EntityDataSerializer<?>> vanilla) {
        int id = vanilla.getId(serializer);
        if (id < 0) {
            ForgeRegistry<EntityDataSerializer<?>> registry = (ForgeRegistry<EntityDataSerializer<?>>) ForgeRegistries.ENTITY_DATA_SERIALIZERS.get();
            if (registry != null) {
                id = registry.getID(serializer);
            }
        }
        return id;
    }

    public static boolean canEntityDestroy(Level level, BlockPos pos, LivingEntity entity) {
        if (!level.isLoaded(pos)) {
            return false;
        } else {
            BlockState state = level.getBlockState(pos);
            return ForgeEventFactory.getMobGriefingEvent(level, entity) && state.canEntityDestroy(level, pos, entity) && ForgeEventFactory.onEntityDestroyBlock(entity, pos, state);
        }
    }

    public static int getBurnTime(ItemStack stack, @Nullable RecipeType<?> recipeType) {
        if (stack.isEmpty()) {
            return 0;
        } else {
            Item item = stack.getItem();
            int ret = stack.getBurnTime(recipeType);
            return ForgeEventFactory.getItemBurnTime(stack, ret == -1 ? (Integer) VANILLA_BURNS.getOrDefault(ForgeRegistries.ITEMS.getDelegateOrThrow(item), 0) : ret, recipeType);
        }
    }

    public static synchronized void updateBurns() {
        VANILLA_BURNS.clear();
        FurnaceBlockEntity.m_58423_().entrySet().forEach(e -> VANILLA_BURNS.put(ForgeRegistries.ITEMS.getDelegateOrThrow((Item) e.getKey()), (Integer) e.getValue()));
    }

    @Deprecated
    public static List<ItemStack> modifyLoot(List<ItemStack> list, LootContext context) {
        return modifyLoot(LootTableIdCondition.UNKNOWN_LOOT_TABLE, ObjectArrayList.wrap((ItemStack[]) list.toArray()), context);
    }

    public static ObjectArrayList<ItemStack> modifyLoot(ResourceLocation lootTableId, ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        context.setQueriedLootTableId(lootTableId);
        LootModifierManager man = ForgeInternalHandler.getLootModifierManager();
        for (IGlobalLootModifier mod : man.getAllLootMods()) {
            generatedLoot = mod.apply(generatedLoot, context);
        }
        return generatedLoot;
    }

    public static List<String> getModPacks() {
        List<String> modpacks = ResourcePackLoader.getPackNames();
        if (modpacks.isEmpty()) {
            throw new IllegalStateException("Attempted to retrieve mod packs before they were loaded in!");
        } else {
            return modpacks;
        }
    }

    public static List<String> getModPacksWithVanilla() {
        List<String> modpacks = getModPacks();
        modpacks.add("vanilla");
        return modpacks;
    }

    @Deprecated
    public static Map<EntityType<? extends LivingEntity>, AttributeSupplier> getAttributesView() {
        return Collections.unmodifiableMap(FORGE_ATTRIBUTES);
    }

    @Deprecated
    public static void modifyAttributes() {
        ModLoader.get().postEvent(new EntityAttributeCreationEvent(FORGE_ATTRIBUTES));
        Map<EntityType<? extends LivingEntity>, AttributeSupplier.Builder> finalMap = new HashMap();
        ModLoader.get().postEvent(new EntityAttributeModificationEvent(finalMap));
        finalMap.forEach((k, v) -> {
            AttributeSupplier supplier = DefaultAttributes.getSupplier(k);
            AttributeSupplier.Builder newBuilder = supplier != null ? new AttributeSupplier.Builder(supplier) : new AttributeSupplier.Builder();
            newBuilder.combine(v);
            FORGE_ATTRIBUTES.put(k, newBuilder.build());
        });
    }

    public static void onEntityEnterSection(Entity entity, long packedOldPos, long packedNewPos) {
        MinecraftForge.EVENT_BUS.post(new EntityEvent.EnteringSection(entity, packedOldPos, packedNewPos));
    }

    public static ShieldBlockEvent onShieldBlock(LivingEntity blocker, DamageSource source, float blocked) {
        ShieldBlockEvent e = new ShieldBlockEvent(blocker, source, blocked);
        MinecraftForge.EVENT_BUS.post(e);
        return e;
    }

    public static LivingSwapItemsEvent.Hands onLivingSwapHandItems(LivingEntity livingEntity) {
        LivingSwapItemsEvent.Hands event = new LivingSwapItemsEvent.Hands(livingEntity);
        MinecraftForge.EVENT_BUS.post(event);
        return event;
    }

    public static void writeAdditionalLevelSaveData(WorldData worldData, CompoundTag levelTag) {
        CompoundTag fmlData = new CompoundTag();
        ListTag modList = new ListTag();
        ModList.get().getMods().forEach(mi -> {
            CompoundTag mod = new CompoundTag();
            mod.putString("ModId", mi.getModId());
            mod.putString("ModVersion", MavenVersionStringHelper.artifactVersionToString(mi.getVersion()));
            modList.add(mod);
        });
        fmlData.put("LoadingModList", modList);
        CompoundTag registries = new CompoundTag();
        fmlData.put("Registries", registries);
        LOGGER.debug(WORLDPERSISTENCE, "Gathering id map for writing to world save {}", worldData.getLevelName());
        for (Entry<ResourceLocation, ForgeRegistry.Snapshot> e : RegistryManager.ACTIVE.takeSnapshot(true).entrySet()) {
            registries.put(((ResourceLocation) e.getKey()).toString(), ((ForgeRegistry.Snapshot) e.getValue()).write());
        }
        LOGGER.debug(WORLDPERSISTENCE, "ID Map collection complete {}", worldData.getLevelName());
        levelTag.put("fml", fmlData);
    }

    @Internal
    public static void readAdditionalLevelSaveData(CompoundTag rootTag, LevelStorageSource.LevelDirectory levelDirectory) {
        CompoundTag tag = rootTag.getCompound("fml");
        if (tag.contains("LoadingModList")) {
            ListTag modList = tag.getList("LoadingModList", 10);
            Map<String, ArtifactVersion> mismatchedVersions = new HashMap(modList.size());
            Map<String, ArtifactVersion> missingVersions = new HashMap(modList.size());
            for (int i = 0; i < modList.size(); i++) {
                CompoundTag mod = modList.getCompound(i);
                String modId = mod.getString("ModId");
                if (!Objects.equals("minecraft", modId)) {
                    String modVersion = mod.getString("ModVersion");
                    DefaultArtifactVersion previousVersion = new DefaultArtifactVersion(modVersion);
                    ModList.get().getModContainerById(modId).ifPresentOrElse(container -> {
                        ArtifactVersion loadingVersion = container.getModInfo().getVersion();
                        if (!loadingVersion.equals(previousVersion)) {
                            mismatchedVersions.put(modId, previousVersion);
                        }
                    }, () -> missingVersions.put(modId, previousVersion));
                }
            }
            ModMismatchEvent mismatchEvent = new ModMismatchEvent(levelDirectory, mismatchedVersions, missingVersions);
            ModLoader.get().postEvent(mismatchEvent);
            StringBuilder resolved = new StringBuilder("The following mods have version differences that were marked resolved:");
            StringBuilder unresolved = new StringBuilder("The following mods have version differences that were not resolved:");
            mismatchEvent.getResolved().forEachOrdered(res -> {
                String modid = res.modid();
                ModMismatchEvent.MismatchedVersionInfo diff = res.versionDifference();
                if (res.wasSelfResolved()) {
                    resolved.append(System.lineSeparator()).append(diff.isMissing() ? "%s (version %s -> MISSING, self-resolved)".formatted(modid, diff.oldVersion()) : "%s (version %s -> %s, self-resolved)".formatted(modid, diff.oldVersion(), diff.newVersion()));
                } else {
                    String resolver = res.resolver().getModId();
                    resolved.append(System.lineSeparator()).append(diff.isMissing() ? "%s (version %s -> MISSING, resolved by %s)".formatted(modid, diff.oldVersion(), resolver) : "%s (version %s -> %s, resolved by %s)".formatted(modid, diff.oldVersion(), diff.newVersion(), resolver));
                }
            });
            mismatchEvent.getUnresolved().forEachOrdered(unres -> {
                String modid = unres.modid();
                ModMismatchEvent.MismatchedVersionInfo diff = unres.versionDifference();
                unresolved.append(System.lineSeparator()).append(diff.isMissing() ? "%s (version %s -> MISSING)".formatted(modid, diff.oldVersion()) : "%s (version %s -> %s)".formatted(modid, diff.oldVersion(), diff.newVersion()));
            });
            if (mismatchEvent.anyResolved()) {
                resolved.append(System.lineSeparator()).append("Things may not work well.");
                LOGGER.debug(WORLDPERSISTENCE, resolved.toString());
            }
            if (mismatchEvent.anyUnresolved()) {
                unresolved.append(System.lineSeparator()).append("Things may not work well.");
                LOGGER.warn(WORLDPERSISTENCE, unresolved.toString());
            }
        }
        Multimap<ResourceLocation, ResourceLocation> failedElements = null;
        if (tag.contains("Registries")) {
            Map<ResourceLocation, ForgeRegistry.Snapshot> snapshot = new HashMap();
            CompoundTag regs = tag.getCompound("Registries");
            for (String key : regs.getAllKeys()) {
                snapshot.put(new ResourceLocation(key), ForgeRegistry.Snapshot.read(regs.getCompound(key)));
            }
            failedElements = GameData.injectSnapshot(snapshot, true, true);
        }
        if (failedElements != null && !failedElements.isEmpty()) {
            StringBuilder buf = new StringBuilder();
            buf.append("Forge Mod Loader could not load this save.\n\n").append("There are ").append(failedElements.size()).append(" unassigned registry entries in this save.\n").append("You will not be able to load until they are present again.\n\n");
            failedElements.asMap().forEach((name, entries) -> {
                buf.append("Missing ").append(name).append(":\n");
                entries.forEach(rl -> buf.append("    ").append(rl).append("\n"));
            });
            LOGGER.error(WORLDPERSISTENCE, buf.toString());
        }
    }

    public static String encodeLifecycle(Lifecycle lifecycle) {
        if (lifecycle == Lifecycle.stable()) {
            return "stable";
        } else if (lifecycle == Lifecycle.experimental()) {
            return "experimental";
        } else if (lifecycle instanceof com.mojang.serialization.Lifecycle.Deprecated dep) {
            return "deprecated=" + dep.since();
        } else {
            throw new IllegalArgumentException("Unknown lifecycle.");
        }
    }

    public static Lifecycle parseLifecycle(String lifecycle) {
        if (lifecycle.equals("stable")) {
            return Lifecycle.stable();
        } else if (lifecycle.equals("experimental")) {
            return Lifecycle.experimental();
        } else if (lifecycle.startsWith("deprecated=")) {
            return Lifecycle.deprecated(Integer.parseInt(lifecycle.substring(lifecycle.indexOf(61) + 1)));
        } else {
            throw new IllegalArgumentException("Unknown lifecycle.");
        }
    }

    public static void saveMobEffect(CompoundTag nbt, String key, MobEffect effect) {
        ResourceLocation registryName = ForgeRegistries.MOB_EFFECTS.getKey(effect);
        if (registryName != null) {
            nbt.putString(key, registryName.toString());
        }
    }

    @Nullable
    public static MobEffect loadMobEffect(CompoundTag nbt, String key, @Nullable MobEffect fallback) {
        String registryName = nbt.getString(key);
        if (Strings.isNullOrEmpty(registryName)) {
            return fallback;
        } else {
            try {
                return ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(registryName));
            } catch (ResourceLocationException var5) {
                return fallback;
            }
        }
    }

    public static boolean shouldSuppressEnderManAnger(EnderMan enderMan, Player player, ItemStack mask) {
        return mask.isEnderMask(player, enderMan) || MinecraftForge.EVENT_BUS.post(new EnderManAngerEvent(enderMan, player));
    }

    @Nullable
    public static StructuresBecomeConfiguredFix.Conversion getStructureConversion(String originalBiome) {
        return (StructuresBecomeConfiguredFix.Conversion) ((Map) FORGE_CONVERSION_MAP.get()).get(originalBiome);
    }

    public static boolean checkStructureNamespace(String biome) {
        ResourceLocation biomeLocation = ResourceLocation.tryParse(biome);
        return biomeLocation != null && !biomeLocation.getNamespace().equals("minecraft");
    }

    public static Map<PackType, Integer> readTypedPackFormats(JsonObject json) {
        Builder<PackType, Integer> map = ImmutableMap.builder();
        for (PackType packType : PackType.values()) {
            String key = makePackFormatKey(packType);
            if (json.has(key)) {
                map.put(packType, GsonHelper.getAsInt(json, key));
            }
        }
        return map.buildOrThrow();
    }

    public static void writeTypedPackFormats(JsonObject json, PackMetadataSection section) {
        int packFormat = section.getPackFormat();
        for (PackType packType : PackType.values()) {
            int format = section.getPackFormat(packType);
            if (format != packFormat) {
                json.addProperty(makePackFormatKey(packType), format);
            }
        }
    }

    private static String makePackFormatKey(PackType packType) {
        return "forge:" + packType.name().toLowerCase(Locale.ROOT) + "_pack_format";
    }

    public static String prefixNamespace(ResourceLocation registryKey) {
        return registryKey.getNamespace().equals("minecraft") ? registryKey.getPath() : registryKey.getNamespace() + "/" + registryKey.getPath();
    }

    public static boolean canUseEntitySelectors(SharedSuggestionProvider provider) {
        if (provider.hasPermission(2)) {
            return true;
        } else {
            if (provider instanceof CommandSourceStack source && source.source instanceof ServerPlayer player) {
                return PermissionAPI.getPermission(player, ForgeMod.USE_SELECTORS_PERMISSION);
            }
            return false;
        }
    }

    @Internal
    public static <T> HolderLookup.RegistryLookup<T> wrapRegistryLookup(final HolderLookup.RegistryLookup<T> lookup) {
        return new HolderLookup.RegistryLookup.Delegate<T>() {

            @Override
            protected HolderLookup.RegistryLookup<T> parent() {
                return lookup;
            }

            @Override
            public Stream<HolderSet.Named<T>> listTags() {
                return Stream.empty();
            }

            @Override
            public Optional<HolderSet.Named<T>> get(TagKey<T> key) {
                return Optional.of(HolderSet.emptyNamed(lookup, key));
            }
        };
    }

    public static void onLivingBreathe(LivingEntity entity, int consumeAirAmount, int refillAirAmount) {
        boolean isAir = entity.getEyeInFluidType().isAir() || entity.m_9236_().getBlockState(BlockPos.containing(entity.m_20185_(), entity.m_20188_(), entity.m_20189_())).m_60713_(Blocks.BUBBLE_COLUMN);
        boolean canBreathe = !entity.canDrownInFluidType(entity.getEyeInFluidType()) || MobEffectUtil.hasWaterBreathing(entity) || entity instanceof Player && ((Player) entity).getAbilities().invulnerable;
        LivingBreatheEvent breatheEvent = new LivingBreatheEvent(entity, isAir || canBreathe, consumeAirAmount, refillAirAmount, isAir);
        MinecraftForge.EVENT_BUS.post(breatheEvent);
        if (breatheEvent.canBreathe()) {
            if (breatheEvent.canRefillAir()) {
                entity.m_20301_(Math.min(entity.m_20146_() + breatheEvent.getRefillAirAmount(), entity.m_6062_()));
            }
        } else {
            entity.m_20301_(entity.m_20146_() - breatheEvent.getConsumeAirAmount());
        }
        if (entity.m_20146_() <= 0) {
            LivingDrownEvent drownEvent = new LivingDrownEvent(entity, entity.m_20146_() <= -20, 2.0F, 8);
            if (!MinecraftForge.EVENT_BUS.post(drownEvent) && drownEvent.isDrowning()) {
                entity.m_20301_(0);
                Vec3 vec3 = entity.m_20184_();
                for (int i = 0; i < drownEvent.getBubbleCount(); i++) {
                    double d2 = entity.getRandom().nextDouble() - entity.getRandom().nextDouble();
                    double d3 = entity.getRandom().nextDouble() - entity.getRandom().nextDouble();
                    double d4 = entity.getRandom().nextDouble() - entity.getRandom().nextDouble();
                    entity.m_9236_().addParticle(ParticleTypes.BUBBLE, entity.m_20185_() + d2, entity.m_20186_() + d3, entity.m_20189_() + d4, vec3.x, vec3.y, vec3.z);
                }
                if (drownEvent.getDamageAmount() > 0.0F) {
                    entity.hurt(entity.m_269291_().drown(), drownEvent.getDamageAmount());
                }
            }
        }
        if (!isAir && !entity.m_9236_().isClientSide && entity.m_20159_() && entity.m_20202_() != null && !entity.m_20202_().canBeRiddenUnderFluidType(entity.getEyeInFluidType(), entity)) {
            entity.stopRiding();
        }
    }

    public static void onCreativeModeTabBuildContents(CreativeModeTab tab, ResourceKey<CreativeModeTab> tabKey, CreativeModeTab.DisplayItemsGenerator originalGenerator, CreativeModeTab.ItemDisplayParameters params, CreativeModeTab.Output output) {
        MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> entries = new MutableHashedLinkedMap<>(ItemStackLinkedSet.TYPE_AND_TAG, (key, left, right) -> CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        originalGenerator.accept(params, (stack, vis) -> {
            if (stack.getCount() != 1) {
                throw new IllegalArgumentException("The stack count must be 1");
            } else {
                entries.put(stack, vis);
            }
        });
        ModLoader.get().postEvent(new BuildCreativeModeTabContentsEvent(tab, tabKey, params, entries));
        for (Entry<ItemStack, CreativeModeTab.TabVisibility> entry : entries) {
            output.accept((ItemStack) entry.getKey(), (CreativeModeTab.TabVisibility) entry.getValue());
        }
    }

    @FunctionalInterface
    public interface BiomeCallbackFunction {

        Biome apply(Biome.ClimateSettings var1, BiomeSpecialEffects var2, BiomeGenerationSettings var3, MobSpawnSettings var4);
    }

    private static class LootTableContext {

        public final ResourceLocation name;

        public final boolean vanilla;

        public final boolean custom;

        public int poolCount = 0;

        private LootTableContext(ResourceLocation name, boolean custom) {
            this.name = name;
            this.custom = custom;
            this.vanilla = "minecraft".equals(this.name.getNamespace());
        }
    }
}