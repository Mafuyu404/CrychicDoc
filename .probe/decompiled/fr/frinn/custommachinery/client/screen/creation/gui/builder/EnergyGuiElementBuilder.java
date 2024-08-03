package fr.frinn.custommachinery.client.screen.creation.gui.builder;

import fr.frinn.custommachinery.api.guielement.GuiElementType;
import fr.frinn.custommachinery.client.screen.BaseScreen;
import fr.frinn.custommachinery.client.screen.creation.MachineEditScreen;
import fr.frinn.custommachinery.client.screen.creation.gui.GuiElementBuilderPopup;
import fr.frinn.custommachinery.client.screen.creation.gui.IGuiElementBuilder;
import fr.frinn.custommachinery.client.screen.creation.gui.MutableProperties;
import fr.frinn.custommachinery.client.screen.popup.PopupScreen;
import fr.frinn.custommachinery.common.guielement.EnergyGuiElement;
import fr.frinn.custommachinery.common.init.Registration;
import fr.frinn.custommachinery.impl.guielement.AbstractGuiElement;
import java.util.function.Consumer;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class EnergyGuiElementBuilder implements IGuiElementBuilder<EnergyGuiElement> {

    @Override
    public GuiElementType<EnergyGuiElement> type() {
        return (GuiElementType<EnergyGuiElement>) Registration.ENERGY_GUI_ELEMENT.get();
    }

    public EnergyGuiElement make(AbstractGuiElement.Properties properties, @Nullable EnergyGuiElement from) {
        return from != null ? new EnergyGuiElement(properties, from.getEmptyTexture(), from.getFilledTexture(), from.highlight()) : new EnergyGuiElement(properties, EnergyGuiElement.BASE_ENERGY_STORAGE_EMPTY_TEXTURE, EnergyGuiElement.BASE_ENERGY_STORAGE_FILLED_TEXTURE, true);
    }

    public PopupScreen makeConfigPopup(MachineEditScreen parent, MutableProperties properties, @Nullable EnergyGuiElement from, Consumer<EnergyGuiElement> onFinish) {
        return new EnergyGuiElementBuilder.EnergyGuiElementBuilderPopup(parent, properties, from, onFinish);
    }

    public static class EnergyGuiElementBuilderPopup extends GuiElementBuilderPopup<EnergyGuiElement> {

        private ResourceLocation textureEmpty = EnergyGuiElement.BASE_ENERGY_STORAGE_EMPTY_TEXTURE;

        private ResourceLocation textureFilled = EnergyGuiElement.BASE_ENERGY_STORAGE_FILLED_TEXTURE;

        private Checkbox highlight;

        public EnergyGuiElementBuilderPopup(BaseScreen parent, MutableProperties properties, @Nullable EnergyGuiElement from, Consumer<EnergyGuiElement> onFinish) {
            super(parent, properties, from, onFinish);
        }

        public EnergyGuiElement makeElement() {
            return new EnergyGuiElement(this.properties.build(), this.textureEmpty, this.textureFilled, this.highlight.selected());
        }

        @Override
        public void addWidgets(GridLayout.RowHelper row) {
            this.addTexture(row, Component.translatable("custommachinery.gui.creation.gui.empty"), texture -> this.textureEmpty = texture, this.textureEmpty);
            this.addTexture(row, Component.translatable("custommachinery.gui.creation.gui.filled"), texture -> this.textureFilled = texture, this.textureFilled);
            this.addPriority(row);
            row.addChild(new StringWidget(Component.translatable("custommachinery.gui.creation.gui.highlight"), this.f_96547_));
            this.highlight = row.addChild(new Checkbox(0, 0, 20, 20, Component.translatable("custommachinery.gui.creation.gui.highlight"), this.baseElement == null || this.baseElement.highlight()));
        }
    }
}