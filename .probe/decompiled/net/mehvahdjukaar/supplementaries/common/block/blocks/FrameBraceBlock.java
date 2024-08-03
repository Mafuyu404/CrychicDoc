package net.mehvahdjukaar.supplementaries.common.block.blocks;

import net.mehvahdjukaar.supplementaries.common.block.ModBlockProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

public class FrameBraceBlock extends FrameBlock {

    public static final BooleanProperty FLIPPED = ModBlockProperties.FLIPPED;

    public FrameBraceBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.m_49959_((BlockState) this.m_49966_().m_61124_(FLIPPED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FLIPPED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        Direction direction = context.m_43719_();
        return (BlockState) super.getStateForPlacement(context).m_61124_(FLIPPED, direction != Direction.DOWN && (direction == Direction.UP || context.m_43720_().y - (double) blockpos.m_123342_() <= 0.5));
    }

    public BlockState applyRotationLock(Level world, BlockPos blockPos, BlockState state, Direction direction, int half) {
        return (BlockState) state.m_61124_(FLIPPED, half == 1);
    }
}