package de.keksuccino.fancymenu.mixin.mixins.common.client;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import com.mojang.blaze3d.systems.RenderSystem;
import de.keksuccino.fancymenu.util.rendering.ui.widget.CustomizableSlider;
import de.keksuccino.fancymenu.util.rendering.ui.widget.CustomizableWidget;
import de.keksuccino.fancymenu.util.resource.PlayableResource;
import de.keksuccino.fancymenu.util.resource.RenderableResource;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ AbstractSliderButton.class })
public abstract class MixinAbstractSliderButton extends AbstractWidget implements CustomizableSlider {

    @Unique
    private static final ResourceLocation SLIDER_LOCATION_FANCYMENU = new ResourceLocation("textures/gui/slider.png");

    @Shadow
    private boolean canChangeValue;

    @Shadow
    protected double value;

    @Unique
    @Nullable
    private RenderableResource customSliderBackgroundNormalFancyMenu;

    @Unique
    @Nullable
    private RenderableResource customSliderBackgroundHighlightedFancyMenu;

    @Unique
    private boolean sliderInitializedFancyMenu = false;

    @Unique
    private boolean nineSliceSliderBackground_FancyMenu = false;

    @Unique
    private int nineSliceSliderBackgroundBorderX_FancyMenu = 5;

    @Unique
    private int nineSliceSliderBackgroundBorderY_FancyMenu = 5;

    @Unique
    private boolean nineSliceSliderHandle_FancyMenu = false;

    @Unique
    private int nineSliceSliderHandleBorderX_FancyMenu = 5;

    @Unique
    private int nineSliceSliderHandleBorderY_FancyMenu = 5;

    public MixinAbstractSliderButton(int $$0, int $$1, int $$2, int $$3, Component $$4) {
        super($$0, $$1, $$2, $$3, $$4);
    }

    @Inject(method = { "renderWidget" }, at = { @At("HEAD") })
    private void beforeRenderWidgetFancyMenu(GuiGraphics graphics, int $$1, int $$2, float $$3, CallbackInfo ci) {
        if (!this.sliderInitializedFancyMenu) {
            this.initializeSliderFancyMenu();
        }
        this.sliderInitializedFancyMenu = true;
    }

    @WrapWithCondition(method = { "renderWidget" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitNineSliced(Lnet/minecraft/resources/ResourceLocation;IIIIIIIIII)V") })
    private boolean wrapBlitNineSlicedInRenderWidgetFancyMenu(GuiGraphics graphics, ResourceLocation location, int $$1, int $$2, int blitWidth, int blitHeight, int $$5, int $$6, int $$7, int $$8, int $$9, int $$10) {
        CustomizableWidget cus = this.getAsCustomizableWidgetFancyMenu();
        boolean isHandle = blitWidth == 8;
        boolean renderVanilla;
        if (isHandle) {
            int handleX = this.m_252754_() + (int) (this.value * (double) (this.m_5711_() - 8));
            renderVanilla = cus.renderCustomBackgroundFancyMenu(this, graphics, handleX, this.m_252907_(), 8, this.m_93694_());
        } else {
            renderVanilla = this.renderSliderBackgroundFancyMenu(graphics, (AbstractSliderButton) this, this.canChangeValue);
        }
        RenderSystem.setShaderTexture(0, SLIDER_LOCATION_FANCYMENU);
        graphics.setColor(1.0F, 1.0F, 1.0F, this.f_93625_);
        return renderVanilla;
    }

    @Unique
    private void initializeSliderFancyMenu() {
        CustomizableWidget cus = this.getAsCustomizableWidgetFancyMenu();
        cus.addResetCustomizationsListenerFancyMenu(() -> {
            if (this.getCustomSliderBackgroundNormalFancyMenu() instanceof PlayableResource p) {
                p.stop();
            }
            if (this.getCustomSliderBackgroundHighlightedFancyMenu() instanceof PlayableResource p) {
                p.stop();
            }
            this.setCustomSliderBackgroundNormalFancyMenu(null);
            this.setCustomSliderBackgroundHighlightedFancyMenu(null);
        });
        cus.addHoverOrFocusStateListenerFancyMenu(hoveredOrFocused -> {
            CustomizableWidget.CustomBackgroundResetBehavior behavior = cus.getCustomBackgroundResetBehaviorFancyMenu();
            if (hoveredOrFocused && (behavior == CustomizableWidget.CustomBackgroundResetBehavior.RESET_ON_HOVER || behavior == CustomizableWidget.CustomBackgroundResetBehavior.RESET_ON_HOVER_AND_UNHOVER)) {
                if (this.getCustomSliderBackgroundNormalFancyMenu() instanceof PlayableResource p) {
                    p.stop();
                }
                if (this.getCustomSliderBackgroundHighlightedFancyMenu() instanceof PlayableResource p) {
                    p.stop();
                }
            }
            if (!hoveredOrFocused && (behavior == CustomizableWidget.CustomBackgroundResetBehavior.RESET_ON_UNHOVER || behavior == CustomizableWidget.CustomBackgroundResetBehavior.RESET_ON_HOVER_AND_UNHOVER)) {
                if (this.getCustomSliderBackgroundNormalFancyMenu() instanceof PlayableResource p) {
                    p.stop();
                }
                if (this.getCustomSliderBackgroundHighlightedFancyMenu() instanceof PlayableResource p) {
                    p.stop();
                }
            }
        });
    }

    @Unique
    @Override
    public void setNineSliceCustomSliderBackground_FancyMenu(boolean nineSlice) {
        this.nineSliceSliderBackground_FancyMenu = nineSlice;
    }

    @Unique
    @Override
    public boolean isNineSliceCustomSliderBackground_FancyMenu() {
        return this.nineSliceSliderBackground_FancyMenu;
    }

    @Unique
    @Override
    public void setNineSliceSliderBackgroundBorderX_FancyMenu(int nineSliceSliderBorderX_FancyMenu) {
        this.nineSliceSliderBackgroundBorderX_FancyMenu = nineSliceSliderBorderX_FancyMenu;
    }

    @Unique
    @Override
    public int getNineSliceSliderBackgroundBorderX_FancyMenu() {
        return this.nineSliceSliderBackgroundBorderX_FancyMenu;
    }

    @Unique
    @Override
    public void setNineSliceSliderBackgroundBorderY_FancyMenu(int nineSliceSliderBorderY_FancyMenu) {
        this.nineSliceSliderBackgroundBorderY_FancyMenu = nineSliceSliderBorderY_FancyMenu;
    }

    @Unique
    @Override
    public int getNineSliceSliderBackgroundBorderY_FancyMenu() {
        return this.nineSliceSliderBackgroundBorderY_FancyMenu;
    }

    @Unique
    @Override
    public void setNineSliceCustomSliderHandle_FancyMenu(boolean nineSlice) {
        this.nineSliceSliderHandle_FancyMenu = nineSlice;
    }

    @Unique
    @Override
    public boolean isNineSliceCustomSliderHandle_FancyMenu() {
        return this.nineSliceSliderHandle_FancyMenu;
    }

    @Unique
    @Override
    public void setNineSliceSliderHandleBorderX_FancyMenu(int nineSliceSliderHandleBorderX_FancyMenu) {
        this.nineSliceSliderHandleBorderX_FancyMenu = nineSliceSliderHandleBorderX_FancyMenu;
    }

    @Unique
    @Override
    public int getNineSliceSliderHandleBorderX_FancyMenu() {
        return this.nineSliceSliderHandleBorderX_FancyMenu;
    }

    @Unique
    @Override
    public void setNineSliceSliderHandleBorderY_FancyMenu(int nineSliceSliderHandleBorderY_FancyMenu) {
        this.nineSliceSliderHandleBorderY_FancyMenu = nineSliceSliderHandleBorderY_FancyMenu;
    }

    @Unique
    @Override
    public int getNineSliceSliderHandleBorderY_FancyMenu() {
        return this.nineSliceSliderHandleBorderY_FancyMenu;
    }

    @Unique
    @Override
    public void setCustomSliderBackgroundNormalFancyMenu(@Nullable RenderableResource background) {
        this.customSliderBackgroundNormalFancyMenu = background;
    }

    @Unique
    @Nullable
    @Override
    public RenderableResource getCustomSliderBackgroundNormalFancyMenu() {
        return this.customSliderBackgroundNormalFancyMenu;
    }

    @Unique
    @Override
    public void setCustomSliderBackgroundHighlightedFancyMenu(@Nullable RenderableResource background) {
        this.customSliderBackgroundHighlightedFancyMenu = background;
    }

    @Unique
    @Nullable
    @Override
    public RenderableResource getCustomSliderBackgroundHighlightedFancyMenu() {
        return this.customSliderBackgroundHighlightedFancyMenu;
    }

    @Unique
    private CustomizableWidget getAsCustomizableWidgetFancyMenu() {
        return (CustomizableWidget) this;
    }
}