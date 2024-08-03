package com.simibubi.create.foundation.mixin;

import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.common.extensions.IForgeEntity;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({ Entity.class })
@Implements({ @Interface(iface = IForgeEntity.class, prefix = "iForgeEntity$") })
public abstract class ContraptionDriverInteractMixin extends CapabilityProvider<Entity> {

    private ContraptionDriverInteractMixin(Class<Entity> baseClass) {
        super(baseClass);
    }

    @Shadow
    public abstract Entity getRootVehicle();

    @Nullable
    @Intrinsic
    public boolean iForgeEntity$canRiderInteract() {
        return this.getRootVehicle() instanceof AbstractContraptionEntity;
    }
}