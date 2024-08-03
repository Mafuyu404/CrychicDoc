package com.mna.gui.block;

import com.mna.ManaAndArtifice;
import com.mna.api.affinity.Affinity;
import com.mna.blocks.tileentities.wizard_lab.WizardLabTile;
import com.mna.gui.GuiTextures;
import com.mna.gui.containers.block.ContainerTranscriptionTable;
import com.mna.tools.math.MathUtils;
import com.mojang.datafixers.util.Pair;
import java.util.HashMap;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class GuiTranscriptionTable extends SimpleWizardLabDeskGui<ContainerTranscriptionTable> {

    public GuiTranscriptionTable(ContainerTranscriptionTable container, Inventory inv, Component comp) {
        super(container, inv, Component.literal(""));
        this.f_97726_ = 176;
        this.f_97727_ = 159;
    }

    @Override
    public ResourceLocation texture() {
        return GuiTextures.WizardLab.TRANSCRIPTION_TABLE;
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float partialTicks, int mouseX, int mouseY) {
        super.renderBg(pGuiGraphics, partialTicks, mouseX, mouseY);
        if (((ContainerTranscriptionTable) this.f_97732_).isActive()) {
            float pct = ((ContainerTranscriptionTable) this.f_97732_).getProgress();
            pGuiGraphics.blit(this.texture(), this.f_97735_ + 59, this.f_97736_ + 10, 198, 38, 58, (int) (58.0F * pct));
        }
        Player player = ManaAndArtifice.instance.proxy.getClientPlayer();
        float xpPct = MathUtils.clamp01(player.isCreative() ? 1.0F : (float) player.totalExperience / (float) ((ContainerTranscriptionTable) this.f_97732_).getXPCost());
        int VCoord = xpPct < 1.0F ? 5 : 0;
        pGuiGraphics.blit(this.texture(), this.f_97735_ + 128, this.f_97736_ + 52, 220, VCoord, (int) (36.0F * xpPct), 5);
        HashMap<Affinity, WizardLabTile.PowerStatus> powerRequirements = ((ContainerTranscriptionTable) this.f_97732_).powerRequirementStatus();
        if (powerRequirements.size() == 1) {
            powerRequirements.forEach((a, b) -> {
                this.renderPowerConsumeStatusIcon(pGuiGraphics, mouseX, mouseY, this.f_97735_ + 26, this.f_97736_ + 58, a, b);
                ItemStack renderStack = (ItemStack) GuiTextures.affinityIcons.get(a);
                if (!renderStack.isEmpty()) {
                    pGuiGraphics.renderItem(renderStack, this.f_97735_ + 8, this.f_97736_ + 54);
                }
            });
        }
        pGuiGraphics.blit(this.texture(), this.f_97735_ + 7, this.f_97736_ + 30, 202, 0, 18, 18);
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int x, int y) {
        ItemStack stack = new ItemStack(Items.EXPERIENCE_BOTTLE);
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().scale(2.0F, 2.0F, 2.0F);
        pGuiGraphics.pose().translate(0.5, 0.0, 0.0);
        pGuiGraphics.renderItem(stack, 64, 9);
        pGuiGraphics.pose().popPose();
    }

    @Override
    protected Pair<Integer, Integer> goButtonPos() {
        return new Pair(30, 32);
    }

    @Override
    protected Pair<Integer, Integer> goButtonUV() {
        return new Pair(231, 10);
    }
}