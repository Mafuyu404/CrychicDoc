package com.simibubi.create.content.schematics.cannon;

import com.google.common.collect.ImmutableList;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllPackets;
import com.simibubi.create.content.logistics.crate.CreativeCrateBlock;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import com.simibubi.create.foundation.gui.AllIcons;
import com.simibubi.create.foundation.gui.element.GuiGameElement;
import com.simibubi.create.foundation.gui.element.ScreenElement;
import com.simibubi.create.foundation.gui.menu.AbstractSimiContainerScreen;
import com.simibubi.create.foundation.gui.widget.AbstractSimiWidget;
import com.simibubi.create.foundation.gui.widget.IconButton;
import com.simibubi.create.foundation.gui.widget.Indicator;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.utility.Components;
import com.simibubi.create.foundation.utility.Lang;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class SchematicannonScreen extends AbstractSimiContainerScreen<SchematicannonMenu> {

    private static final AllGuiTextures BG_BOTTOM = AllGuiTextures.SCHEMATICANNON_BOTTOM;

    private static final AllGuiTextures BG_TOP = AllGuiTextures.SCHEMATICANNON_TOP;

    private final Component listPrinter = Lang.translateDirect("gui.schematicannon.listPrinter");

    private final String _gunpowderLevel = "gui.schematicannon.gunpowderLevel";

    private final String _shotsRemaining = "gui.schematicannon.shotsRemaining";

    private final String _showSettings = "gui.schematicannon.showOptions";

    private final String _shotsRemainingWithBackup = "gui.schematicannon.shotsRemainingWithBackup";

    private final String _slotGunpowder = "gui.schematicannon.slot.gunpowder";

    private final String _slotListPrinter = "gui.schematicannon.slot.listPrinter";

    private final String _slotSchematic = "gui.schematicannon.slot.schematic";

    private final Component optionEnabled = Lang.translateDirect("gui.schematicannon.optionEnabled");

    private final Component optionDisabled = Lang.translateDirect("gui.schematicannon.optionDisabled");

    protected Vector<Indicator> replaceLevelIndicators;

    protected Vector<IconButton> replaceLevelButtons;

    protected IconButton skipMissingButton;

    protected Indicator skipMissingIndicator;

    protected IconButton skipBlockEntitiesButton;

    protected Indicator skipBlockEntitiesIndicator;

    protected IconButton playButton;

    protected Indicator playIndicator;

    protected IconButton pauseButton;

    protected Indicator pauseIndicator;

    protected IconButton resetButton;

    protected Indicator resetIndicator;

    private IconButton confirmButton;

    private IconButton showSettingsButton;

    private Indicator showSettingsIndicator;

    protected List<AbstractWidget> placementSettingWidgets;

    private final ItemStack renderedItem = AllBlocks.SCHEMATICANNON.asStack();

    private List<Rect2i> extraAreas = Collections.emptyList();

    public SchematicannonScreen(SchematicannonMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.placementSettingWidgets = new ArrayList();
    }

    @Override
    protected void init() {
        this.setWindowSize(BG_TOP.width, BG_TOP.height + BG_BOTTOM.height + 2 + AllGuiTextures.PLAYER_INVENTORY.height);
        this.setWindowOffset(-11, 0);
        super.init();
        int x = this.f_97735_;
        int y = this.f_97736_;
        this.playButton = new IconButton(x + 75, y + 86, AllIcons.I_PLAY);
        this.playButton.withCallback(() -> this.sendOptionUpdate(ConfigureSchematicannonPacket.Option.PLAY, true));
        this.playIndicator = new Indicator(x + 75, y + 79, Components.immutableEmpty());
        this.pauseButton = new IconButton(x + 93, y + 86, AllIcons.I_PAUSE);
        this.pauseButton.withCallback(() -> this.sendOptionUpdate(ConfigureSchematicannonPacket.Option.PAUSE, true));
        this.pauseIndicator = new Indicator(x + 93, y + 79, Components.immutableEmpty());
        this.resetButton = new IconButton(x + 111, y + 86, AllIcons.I_STOP);
        this.resetButton.withCallback(() -> this.sendOptionUpdate(ConfigureSchematicannonPacket.Option.STOP, true));
        this.resetIndicator = new Indicator(x + 111, y + 79, Components.immutableEmpty());
        this.resetIndicator.state = Indicator.State.RED;
        this.addRenderableWidgets(new AbstractSimiWidget[] { this.playButton, this.playIndicator, this.pauseButton, this.pauseIndicator, this.resetButton, this.resetIndicator });
        this.confirmButton = new IconButton(x + 180, y + 117, AllIcons.I_CONFIRM);
        this.confirmButton.withCallback(() -> this.f_96541_.player.closeContainer());
        this.m_142416_(this.confirmButton);
        this.showSettingsButton = new IconButton(x + 9, y + 117, AllIcons.I_PLACEMENT_SETTINGS);
        this.showSettingsButton.withCallback(() -> {
            this.showSettingsIndicator.state = this.placementSettingsHidden() ? Indicator.State.GREEN : Indicator.State.OFF;
            this.initPlacementSettings();
        });
        this.showSettingsButton.setToolTip(Lang.translateDirect("gui.schematicannon.showOptions"));
        this.m_142416_(this.showSettingsButton);
        this.showSettingsIndicator = new Indicator(x + 9, y + 111, Components.immutableEmpty());
        this.m_142416_(this.showSettingsIndicator);
        this.extraAreas = ImmutableList.of(new Rect2i(x + BG_TOP.width, y + BG_TOP.height + BG_BOTTOM.height - 62, 84, 92));
        this.m_86600_();
    }

    private void initPlacementSettings() {
        this.removeWidgets(this.placementSettingWidgets);
        this.placementSettingWidgets.clear();
        if (!this.placementSettingsHidden()) {
            int x = this.f_97735_;
            int y = this.f_97736_;
            this.replaceLevelButtons = new Vector(4);
            this.replaceLevelIndicators = new Vector(4);
            List<AllIcons> icons = ImmutableList.of(AllIcons.I_DONT_REPLACE, AllIcons.I_REPLACE_SOLID, AllIcons.I_REPLACE_ANY, AllIcons.I_REPLACE_EMPTY);
            List<Component> toolTips = ImmutableList.of(Lang.translateDirect("gui.schematicannon.option.dontReplaceSolid"), Lang.translateDirect("gui.schematicannon.option.replaceWithSolid"), Lang.translateDirect("gui.schematicannon.option.replaceWithAny"), Lang.translateDirect("gui.schematicannon.option.replaceWithEmpty"));
            for (int i = 0; i < 4; i++) {
                this.replaceLevelIndicators.add(new Indicator(x + 33 + i * 18, y + 111, Components.immutableEmpty()));
                IconButton replaceLevelButton = new IconButton(x + 33 + i * 18, y + 117, (ScreenElement) icons.get(i));
                int replaceMode = i;
                replaceLevelButton.withCallback(() -> {
                    if (((SchematicannonMenu) this.f_97732_).contentHolder.replaceMode != replaceMode) {
                        this.sendOptionUpdate(ConfigureSchematicannonPacket.Option.values()[replaceMode], true);
                    }
                });
                replaceLevelButton.setToolTip((Component) toolTips.get(i));
                this.replaceLevelButtons.add(replaceLevelButton);
            }
            this.placementSettingWidgets.addAll(this.replaceLevelButtons);
            this.placementSettingWidgets.addAll(this.replaceLevelIndicators);
            this.skipMissingButton = new IconButton(x + 111, y + 117, AllIcons.I_SKIP_MISSING);
            this.skipMissingButton.withCallback(() -> this.sendOptionUpdate(ConfigureSchematicannonPacket.Option.SKIP_MISSING, !((SchematicannonMenu) this.f_97732_).contentHolder.skipMissing));
            this.skipMissingButton.setToolTip(Lang.translateDirect("gui.schematicannon.option.skipMissing"));
            this.skipMissingIndicator = new Indicator(x + 111, y + 111, Components.immutableEmpty());
            Collections.addAll(this.placementSettingWidgets, new AbstractSimiWidget[] { this.skipMissingButton, this.skipMissingIndicator });
            this.skipBlockEntitiesButton = new IconButton(x + 129, y + 117, AllIcons.I_SKIP_BLOCK_ENTITIES);
            this.skipBlockEntitiesButton.withCallback(() -> this.sendOptionUpdate(ConfigureSchematicannonPacket.Option.SKIP_BLOCK_ENTITIES, !((SchematicannonMenu) this.f_97732_).contentHolder.replaceBlockEntities));
            this.skipBlockEntitiesButton.setToolTip(Lang.translateDirect("gui.schematicannon.option.skipBlockEntities"));
            this.skipBlockEntitiesIndicator = new Indicator(x + 129, y + 111, Components.immutableEmpty());
            Collections.addAll(this.placementSettingWidgets, new AbstractSimiWidget[] { this.skipBlockEntitiesButton, this.skipBlockEntitiesIndicator });
            this.addRenderableWidgets(this.placementSettingWidgets);
        }
    }

    protected boolean placementSettingsHidden() {
        return this.showSettingsIndicator.state == Indicator.State.OFF;
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        SchematicannonBlockEntity be = ((SchematicannonMenu) this.f_97732_).contentHolder;
        if (!this.placementSettingsHidden()) {
            for (int replaceMode = 0; replaceMode < this.replaceLevelButtons.size(); replaceMode++) {
                ((IconButton) this.replaceLevelButtons.get(replaceMode)).f_93623_ = replaceMode != be.replaceMode;
                ((Indicator) this.replaceLevelIndicators.get(replaceMode)).state = replaceMode == be.replaceMode ? Indicator.State.ON : Indicator.State.OFF;
            }
            this.skipMissingIndicator.state = be.skipMissing ? Indicator.State.ON : Indicator.State.OFF;
            this.skipBlockEntitiesIndicator.state = !be.replaceBlockEntities ? Indicator.State.ON : Indicator.State.OFF;
        }
        this.playIndicator.state = Indicator.State.OFF;
        this.pauseIndicator.state = Indicator.State.OFF;
        this.resetIndicator.state = Indicator.State.OFF;
        switch(be.state) {
            case PAUSED:
                this.pauseIndicator.state = Indicator.State.YELLOW;
                this.playButton.f_93623_ = true;
                this.pauseButton.f_93623_ = false;
                this.resetButton.f_93623_ = true;
                break;
            case RUNNING:
                this.playIndicator.state = Indicator.State.GREEN;
                this.playButton.f_93623_ = false;
                this.pauseButton.f_93623_ = true;
                this.resetButton.f_93623_ = true;
                break;
            case STOPPED:
                this.resetIndicator.state = Indicator.State.RED;
                this.playButton.f_93623_ = true;
                this.pauseButton.f_93623_ = false;
                this.resetButton.f_93623_ = false;
        }
        this.handleTooltips();
    }

    protected void handleTooltips() {
        if (!this.placementSettingsHidden()) {
            for (AbstractWidget w : this.placementSettingWidgets) {
                if (w instanceof IconButton) {
                    IconButton button = (IconButton) w;
                    if (!button.getToolTip().isEmpty()) {
                        button.setToolTip((Component) button.getToolTip().get(0));
                        button.getToolTip().add(TooltipHelper.holdShift(TooltipHelper.Palette.BLUE, m_96638_()));
                    }
                }
            }
            if (m_96638_()) {
                this.fillToolTip(this.skipMissingButton, this.skipMissingIndicator, "skipMissing");
                this.fillToolTip(this.skipBlockEntitiesButton, this.skipBlockEntitiesIndicator, "skipBlockEntities");
                this.fillToolTip((IconButton) this.replaceLevelButtons.get(0), (Indicator) this.replaceLevelIndicators.get(0), "dontReplaceSolid");
                this.fillToolTip((IconButton) this.replaceLevelButtons.get(1), (Indicator) this.replaceLevelIndicators.get(1), "replaceWithSolid");
                this.fillToolTip((IconButton) this.replaceLevelButtons.get(2), (Indicator) this.replaceLevelIndicators.get(2), "replaceWithAny");
                this.fillToolTip((IconButton) this.replaceLevelButtons.get(3), (Indicator) this.replaceLevelIndicators.get(3), "replaceWithEmpty");
            }
        }
    }

    private void fillToolTip(IconButton button, Indicator indicator, String tooltipKey) {
        if (button.m_274382_()) {
            boolean enabled = indicator.state == Indicator.State.ON;
            List<Component> tip = button.getToolTip();
            tip.add((enabled ? this.optionEnabled : this.optionDisabled).plainCopy().withStyle(ChatFormatting.BLUE));
            tip.addAll(TooltipHelper.cutTextComponent(Lang.translateDirect("gui.schematicannon.option." + tooltipKey + ".description"), TooltipHelper.Palette.ALL_GRAY));
        }
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
        int invX = this.getLeftOfCentered(AllGuiTextures.PLAYER_INVENTORY.width);
        int invY = this.f_97736_ + BG_TOP.height + BG_BOTTOM.height + 2;
        this.renderPlayerInventory(graphics, invX, invY);
        int x = this.f_97735_;
        int y = this.f_97736_;
        BG_TOP.render(graphics, x, y);
        BG_BOTTOM.render(graphics, x, y + BG_TOP.height);
        SchematicannonBlockEntity be = ((SchematicannonMenu) this.f_97732_).contentHolder;
        this.renderPrintingProgress(graphics, x, y, be.schematicProgress);
        this.renderFuelBar(graphics, x, y, be.fuelLevel);
        this.renderChecklistPrinterProgress(graphics, x, y, be.bookPrintingProgress);
        if (!be.inventory.getStackInSlot(0).isEmpty()) {
            this.renderBlueprintHighlight(graphics, x, y);
        }
        GuiGameElement.of(this.renderedItem).<GuiGameElement.GuiRenderBuilder>at((float) (x + BG_TOP.width), (float) (y + BG_TOP.height + BG_BOTTOM.height - 48), -200.0F).scale(5.0).render(graphics);
        graphics.drawCenteredString(this.f_96547_, this.f_96539_, x + (BG_TOP.width - 8) / 2, y + 3, 16777215);
        Component msg = Lang.translateDirect("schematicannon.status." + be.statusMsg);
        int stringWidth = this.f_96547_.width(msg);
        if (be.missingItem != null) {
            stringWidth += 16;
            GuiGameElement.of(be.missingItem).<GuiGameElement.GuiRenderBuilder>at((float) (x + 128), (float) (y + 49), 100.0F).scale(1.0).render(graphics);
        }
        graphics.drawString(this.f_96547_, msg, x + 103 - stringWidth / 2, y + 53, 13426175);
        if ("schematicErrored".equals(be.statusMsg)) {
            graphics.drawString(this.f_96547_, Lang.translateDirect("schematicannon.status.schematicErroredCheckLogs"), x + 103 - stringWidth / 2, y + 65, 13426175);
        }
    }

    protected void renderBlueprintHighlight(GuiGraphics graphics, int x, int y) {
        AllGuiTextures.SCHEMATICANNON_HIGHLIGHT.render(graphics, x + 10, y + 60);
    }

    protected void renderPrintingProgress(GuiGraphics graphics, int x, int y, float progress) {
        progress = Math.min(progress, 1.0F);
        AllGuiTextures sprite = AllGuiTextures.SCHEMATICANNON_PROGRESS;
        graphics.blit(sprite.location, x + 44, y + 64, sprite.startX, sprite.startY, (int) ((float) sprite.width * progress), sprite.height);
    }

    protected void renderChecklistPrinterProgress(GuiGraphics graphics, int x, int y, float progress) {
        AllGuiTextures sprite = AllGuiTextures.SCHEMATICANNON_CHECKLIST_PROGRESS;
        graphics.blit(sprite.location, x + 154, y + 20, sprite.startX, sprite.startY, (int) ((float) sprite.width * progress), sprite.height);
    }

    protected void renderFuelBar(GuiGraphics graphics, int x, int y, float amount) {
        AllGuiTextures sprite = AllGuiTextures.SCHEMATICANNON_FUEL;
        if (((SchematicannonMenu) this.f_97732_).contentHolder.hasCreativeCrate) {
            AllGuiTextures.SCHEMATICANNON_FUEL_CREATIVE.render(graphics, x + 36, y + 19);
        } else {
            graphics.blit(sprite.location, x + 36, y + 19, sprite.startX, sprite.startY, (int) ((float) sprite.width * amount), sprite.height);
        }
    }

    @Override
    protected void renderForeground(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        SchematicannonBlockEntity be = ((SchematicannonMenu) this.f_97732_).contentHolder;
        int x = this.f_97735_;
        int y = this.f_97736_;
        int fuelX = x + 36;
        int fuelY = y + 19;
        if (mouseX >= fuelX && mouseY >= fuelY && mouseX <= fuelX + AllGuiTextures.SCHEMATICANNON_FUEL.width && mouseY <= fuelY + AllGuiTextures.SCHEMATICANNON_FUEL.height) {
            List<Component> tooltip = this.getFuelLevelTooltip(be);
            graphics.renderComponentTooltip(this.f_96547_, tooltip, mouseX, mouseY);
        }
        if (this.f_97734_ != null && !this.f_97734_.hasItem()) {
            if (this.f_97734_.index == 0) {
                graphics.renderComponentTooltip(this.f_96547_, TooltipHelper.cutTextComponent(Lang.translateDirect("gui.schematicannon.slot.schematic"), TooltipHelper.Palette.GRAY_AND_BLUE), mouseX, mouseY);
            }
            if (this.f_97734_.index == 2) {
                graphics.renderComponentTooltip(this.f_96547_, TooltipHelper.cutTextComponent(Lang.translateDirect("gui.schematicannon.slot.listPrinter"), TooltipHelper.Palette.GRAY_AND_BLUE), mouseX, mouseY);
            }
            if (this.f_97734_.index == 4) {
                graphics.renderComponentTooltip(this.f_96547_, TooltipHelper.cutTextComponent(Lang.translateDirect("gui.schematicannon.slot.gunpowder"), TooltipHelper.Palette.GRAY_AND_BLUE), mouseX, mouseY);
            }
        }
        if (be.missingItem != null) {
            int missingBlockX = x + 128;
            int missingBlockY = y + 49;
            if (mouseX >= missingBlockX && mouseY >= missingBlockY && mouseX <= missingBlockX + 16 && mouseY <= missingBlockY + 16) {
                graphics.renderTooltip(this.f_96547_, be.missingItem, mouseX, mouseY);
            }
        }
        int paperX = x + 112;
        int paperY = y + 19;
        if (mouseX >= paperX && mouseY >= paperY && mouseX <= paperX + 16 && mouseY <= paperY + 16) {
            graphics.renderTooltip(this.f_96547_, this.listPrinter, mouseX, mouseY);
        }
        super.renderForeground(graphics, mouseX, mouseY, partialTicks);
    }

    protected List<Component> getFuelLevelTooltip(SchematicannonBlockEntity be) {
        double fuelUsageRate = be.getFuelUsageRate();
        int shotsLeft = (int) ((double) be.fuelLevel / fuelUsageRate);
        int shotsLeftWithItems = (int) ((double) shotsLeft + (double) be.inventory.getStackInSlot(4).getCount() * (be.getFuelAddedByGunPowder() / fuelUsageRate));
        List<Component> tooltip = new ArrayList();
        if (be.hasCreativeCrate) {
            tooltip.add(Lang.translateDirect("gui.schematicannon.gunpowderLevel", "100"));
            tooltip.add(Components.literal("(").append(((CreativeCrateBlock) AllBlocks.CREATIVE_CRATE.get()).m_49954_()).append(")").withStyle(ChatFormatting.DARK_PURPLE));
            return tooltip;
        } else {
            int fillPercent = (int) (be.fuelLevel * 100.0F);
            tooltip.add(Lang.translateDirect("gui.schematicannon.gunpowderLevel", fillPercent));
            tooltip.add(Lang.translateDirect("gui.schematicannon.shotsRemaining", Components.literal(Integer.toString(shotsLeft)).withStyle(ChatFormatting.BLUE)).withStyle(ChatFormatting.GRAY));
            if (shotsLeftWithItems != shotsLeft) {
                tooltip.add(Lang.translateDirect("gui.schematicannon.shotsRemainingWithBackup", Components.literal(Integer.toString(shotsLeftWithItems)).withStyle(ChatFormatting.BLUE)).withStyle(ChatFormatting.GRAY));
            }
            return tooltip;
        }
    }

    protected void sendOptionUpdate(ConfigureSchematicannonPacket.Option option, boolean set) {
        AllPackets.getChannel().sendToServer(new ConfigureSchematicannonPacket(option, set));
    }

    @Override
    public List<Rect2i> getExtraAreas() {
        return this.extraAreas;
    }
}