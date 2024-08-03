package com.rekindled.embers.blockentity;

import com.rekindled.embers.RegistryManager;
import com.rekindled.embers.api.capabilities.EmbersCapabilities;
import com.rekindled.embers.upgrade.AtmosphericBellowsUpgrade;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public class AtmosphericBellowsBlockEntity extends BlockEntity {

    public AtmosphericBellowsUpgrade upgrade = new AtmosphericBellowsUpgrade(this);

    public AtmosphericBellowsBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(RegistryManager.ATMOSPHERIC_BELLOWS_ENTITY.get(), pPos, pBlockState);
    }

    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return !this.f_58859_ && this.f_58857_.getBlockState(this.f_58858_).m_61138_(BlockStateProperties.HORIZONTAL_FACING) && cap == EmbersCapabilities.UPGRADE_PROVIDER_CAPABILITY && side == this.f_58857_.getBlockState(this.f_58858_).m_61143_(BlockStateProperties.HORIZONTAL_FACING) ? this.upgrade.getCapability(cap, side) : super.getCapability(cap, side);
    }
}