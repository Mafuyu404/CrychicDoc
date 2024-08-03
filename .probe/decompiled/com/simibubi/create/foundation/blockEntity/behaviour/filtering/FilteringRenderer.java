package com.simibubi.create.foundation.blockEntity.behaviour.filtering;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllSpecialTextures;
import com.simibubi.create.CreateClient;
import com.simibubi.create.content.logistics.filter.FilterItem;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBox;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxRenderer;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.utility.Iterate;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.foundation.utility.Pair;
import com.simibubi.create.foundation.utility.VecHelper;
import com.simibubi.create.infrastructure.config.AllConfigs;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class FilteringRenderer {

    public static void tick() {
        Minecraft mc = Minecraft.getInstance();
        HitResult target = mc.hitResult;
        if (target != null && target instanceof BlockHitResult result) {
            ClientLevel world = mc.level;
            BlockPos pos = result.getBlockPos();
            BlockState state = world.m_8055_(pos);
            FilteringBehaviour behaviour = BlockEntityBehaviour.get(world, pos, FilteringBehaviour.TYPE);
            if (!mc.player.isShiftKeyDown()) {
                ItemStack mainhandItem = mc.player.m_21120_(InteractionHand.MAIN_HAND);
                if (behaviour != null) {
                    if (behaviour instanceof SidedFilteringBehaviour) {
                        behaviour = ((SidedFilteringBehaviour) behaviour).get(result.getDirection());
                        if (behaviour == null) {
                            return;
                        }
                    }
                    if (behaviour.isActive()) {
                        if (behaviour.slotPositioning instanceof ValueBoxTransform.Sided) {
                            ((ValueBoxTransform.Sided) behaviour.slotPositioning).fromSide(result.getDirection());
                        }
                        if (behaviour.slotPositioning.shouldRender(state)) {
                            ItemStack filter = behaviour.getFilter();
                            boolean isFilterSlotted = filter.getItem() instanceof FilterItem;
                            boolean showCount = behaviour.isCountVisible();
                            Component label = behaviour.getLabel();
                            boolean hit = behaviour.slotPositioning.testHit(state, target.getLocation().subtract(Vec3.atLowerCornerOf(pos)));
                            AABB emptyBB = new AABB(Vec3.ZERO, Vec3.ZERO);
                            AABB bb = isFilterSlotted ? emptyBB.inflate(0.45F, 0.31F, 0.2F) : emptyBB.inflate(0.25);
                            ValueBox box = new ValueBox.ItemValueBox(label, bb, pos, filter, showCount ? behaviour.count : -1, behaviour.upTo);
                            box.passive(!hit || AllBlocks.CLIPBOARD.isIn(mainhandItem));
                            CreateClient.OUTLINER.showValueBox(Pair.of("filter", pos), box.transform(behaviour.slotPositioning)).lineWidth(0.015625F).withFaceTexture(hit ? AllSpecialTextures.THIN_CHECKERED : null).highlightFace(result.getDirection());
                            if (hit) {
                                List<MutableComponent> tip = new ArrayList();
                                tip.add(label.copy());
                                tip.add(Lang.translateDirect(filter.isEmpty() ? "logistics.filter.click_to_set" : "logistics.filter.click_to_replace"));
                                if (showCount) {
                                    tip.add(Lang.translateDirect("logistics.filter.hold_to_set_amount"));
                                }
                                CreateClient.VALUE_SETTINGS_HANDLER.showHoverTip(tip);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void renderOnBlockEntity(SmartBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        if (be != null && !be.m_58901_()) {
            if (!be.isVirtual()) {
                Entity cameraEntity = Minecraft.getInstance().cameraEntity;
                if (cameraEntity != null && be.m_58904_() == cameraEntity.level()) {
                    float max = AllConfigs.client().filterItemRenderDistance.getF();
                    if (cameraEntity.position().distanceToSqr(VecHelper.getCenterOf(be.m_58899_())) > (double) (max * max)) {
                        return;
                    }
                }
            }
            FilteringBehaviour behaviour = be.getBehaviour(FilteringBehaviour.TYPE);
            if (behaviour != null) {
                if (behaviour.isActive()) {
                    if (!behaviour.getFilter().isEmpty() || behaviour instanceof SidedFilteringBehaviour) {
                        ValueBoxTransform slotPositioning = behaviour.slotPositioning;
                        BlockState blockState = be.m_58900_();
                        if (slotPositioning instanceof ValueBoxTransform.Sided sided) {
                            Direction side = sided.getSide();
                            for (Direction d : Iterate.directions) {
                                ItemStack filter = behaviour.getFilter(d);
                                if (!filter.isEmpty()) {
                                    sided.fromSide(d);
                                    if (slotPositioning.shouldRender(blockState)) {
                                        ms.pushPose();
                                        slotPositioning.transform(blockState, ms);
                                        if (AllBlocks.CONTRAPTION_CONTROLS.has(blockState)) {
                                            ValueBoxRenderer.renderFlatItemIntoValueBox(filter, ms, buffer, light, overlay);
                                        } else {
                                            ValueBoxRenderer.renderItemIntoValueBox(filter, ms, buffer, light, overlay);
                                        }
                                        ms.popPose();
                                    }
                                }
                            }
                            sided.fromSide(side);
                        } else {
                            if (slotPositioning.shouldRender(blockState)) {
                                ms.pushPose();
                                slotPositioning.transform(blockState, ms);
                                ValueBoxRenderer.renderItemIntoValueBox(behaviour.getFilter(), ms, buffer, light, overlay);
                                ms.popPose();
                            }
                        }
                    }
                }
            }
        }
    }
}