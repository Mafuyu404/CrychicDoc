package net.blay09.mods.waystones.core;

import java.util.ArrayList;
import java.util.List;
import net.blay09.mods.waystones.api.IWaystone;
import net.blay09.mods.waystones.api.IWaystoneTeleportContext;
import net.blay09.mods.waystones.api.TeleportDestination;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class WaystoneTeleportContext implements IWaystoneTeleportContext {

    private final Entity entity;

    private final IWaystone targetWaystone;

    private final List<Entity> additionalEntities = new ArrayList();

    private final List<Mob> leashedEntities = new ArrayList();

    private TeleportDestination destination;

    private IWaystone fromWaystone;

    private WarpMode warpMode = WarpMode.CUSTOM;

    private ItemStack warpItem = ItemStack.EMPTY;

    @Nullable
    private Boolean consumesWarpItem;

    private int xpCost;

    private int cooldown;

    private boolean playsSound = true;

    private boolean playsEffect = true;

    public WaystoneTeleportContext(Entity entity, IWaystone targetWaystone, TeleportDestination destination) {
        this.entity = entity;
        this.targetWaystone = targetWaystone;
        this.destination = destination;
    }

    @Override
    public Entity getEntity() {
        return this.entity;
    }

    @Override
    public IWaystone getTargetWaystone() {
        return this.targetWaystone;
    }

    @Override
    public TeleportDestination getDestination() {
        return this.destination;
    }

    @Override
    public void setDestination(TeleportDestination destination) {
        this.destination = destination;
    }

    @Override
    public List<Mob> getLeashedEntities() {
        return this.leashedEntities;
    }

    @Override
    public List<Entity> getAdditionalEntities() {
        return this.additionalEntities;
    }

    @Override
    public void addAdditionalEntity(Entity additionalEntity) {
        this.additionalEntities.add(additionalEntity);
    }

    @Nullable
    @Override
    public IWaystone getFromWaystone() {
        return this.fromWaystone;
    }

    @Override
    public void setFromWaystone(@Nullable IWaystone fromWaystone) {
        this.fromWaystone = fromWaystone;
    }

    @Override
    public ItemStack getWarpItem() {
        return this.warpItem;
    }

    @Override
    public void setWarpItem(ItemStack warpItem) {
        this.warpItem = warpItem;
    }

    @Override
    public boolean isDimensionalTeleport() {
        return this.targetWaystone.getDimension() != this.entity.level().dimension();
    }

    @Override
    public int getXpCost() {
        return this.xpCost;
    }

    @Override
    public void setXpCost(int xpCost) {
        this.xpCost = xpCost;
    }

    @Override
    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    @Override
    public int getCooldown() {
        return this.cooldown;
    }

    @Override
    public WarpMode getWarpMode() {
        return this.warpMode;
    }

    @Override
    public void setWarpMode(WarpMode warpMode) {
        this.warpMode = warpMode;
    }

    @Override
    public boolean playsSound() {
        return this.playsSound;
    }

    @Override
    public void setPlaysSound(boolean playsSound) {
        this.playsSound = playsSound;
    }

    @Override
    public boolean playsEffect() {
        return this.playsEffect;
    }

    @Override
    public void setPlaysEffect(boolean playsEffect) {
        this.playsEffect = playsEffect;
    }

    @Override
    public boolean consumesWarpItem() {
        return this.consumesWarpItem == null ? this.getWarpMode().consumesItem() : this.consumesWarpItem;
    }

    @Override
    public void setConsumesWarpItem(boolean consumesWarpItem) {
        this.consumesWarpItem = consumesWarpItem;
    }
}