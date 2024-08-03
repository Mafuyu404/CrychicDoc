package mezz.jei.gui;

import mezz.jei.common.gui.textures.Textures;
import mezz.jei.common.util.ImmutableRect2i;
import mezz.jei.common.util.MathUtil;
import mezz.jei.gui.elements.GuiIconButton;
import mezz.jei.gui.input.IPaged;
import mezz.jei.gui.input.IUserInputHandler;
import mezz.jei.gui.input.handlers.CombinedInputHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;

public class PageNavigation {

    private final IPaged paged;

    private final GuiIconButton nextButton;

    private final GuiIconButton backButton;

    private final boolean hideOnSinglePage;

    private String pageNumDisplayString = "1/1";

    private ImmutableRect2i area = ImmutableRect2i.EMPTY;

    public PageNavigation(IPaged paged, boolean hideOnSinglePage, Textures textures) {
        this.paged = paged;
        this.nextButton = new GuiIconButton(textures.getArrowNext(), b -> paged.nextPage(), textures);
        this.backButton = new GuiIconButton(textures.getArrowPrevious(), b -> paged.previousPage(), textures);
        this.hideOnSinglePage = hideOnSinglePage;
    }

    private boolean isVisible() {
        return this.area.isEmpty() ? false : !this.hideOnSinglePage || this.paged.hasNext() || this.paged.hasPrevious();
    }

    public void updateBounds(ImmutableRect2i area) {
        this.area = area;
        int buttonSize = area.getHeight();
        ImmutableRect2i backArea = area.keepLeft(buttonSize);
        this.backButton.updateBounds(backArea);
        ImmutableRect2i nextArea = area.keepRight(buttonSize);
        this.nextButton.updateBounds(nextArea);
    }

    public void updatePageNumber() {
        int pageNum = this.paged.getPageNumber();
        int pageCount = this.paged.getPageCount();
        this.pageNumDisplayString = String.format("%d/%d", pageNum + 1, pageCount);
    }

    public void draw(Minecraft minecraft, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        if (this.isVisible()) {
            guiGraphics.fill(RenderType.gui(), this.backButton.m_252754_() + this.backButton.m_5711_(), this.backButton.m_252907_(), this.nextButton.m_252754_(), this.nextButton.m_252907_() + this.nextButton.m_93694_(), 805306368);
            Font font = minecraft.font;
            ImmutableRect2i centerArea = MathUtil.centerTextArea(this.area, font, this.pageNumDisplayString);
            guiGraphics.drawString(font, this.pageNumDisplayString, centerArea.getX(), centerArea.getY(), -1);
            this.nextButton.render(guiGraphics, mouseX, mouseY, partialTicks);
            this.backButton.render(guiGraphics, mouseX, mouseY, partialTicks);
        }
    }

    public IUserInputHandler createInputHandler() {
        return new CombinedInputHandler(this.nextButton.createInputHandler(), this.backButton.createInputHandler());
    }
}