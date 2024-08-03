package com.mna.recipes.spells;

import com.mna.Registries;
import com.mna.api.spells.base.ISpellComponent;
import com.mna.api.spells.parts.Modifier;
import com.mna.items.ItemInit;
import com.mna.recipes.ItemAndPatternRecipe;
import com.mna.recipes.RecipeInit;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.IForgeRegistry;

public class ModifierRecipe extends ItemAndPatternRecipe implements ISpellComponentRecipe {

    public ModifierRecipe(ResourceLocation idIn) {
        super(idIn);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeInit.MODIFIER_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeInit.MODIFIER_TYPE.get();
    }

    @Override
    protected int maxItems() {
        return 9;
    }

    @Override
    protected int maxPatterns() {
        return 9;
    }

    @Override
    public ItemStack getGuiRepresentationStack() {
        ISpellComponent me = this.getComponent();
        return me != null ? new ItemStack(ItemInit.VELLUM.get()).setHoverName(Component.translatable(me.getRegistryName().toString())) : ItemStack.EMPTY;
    }

    @Override
    public ISpellComponent getComponent() {
        ResourceLocation myLocation = this.output.getNamespace().equals("mna") ? new ResourceLocation(this.output.getNamespace(), "modifiers/" + this.output.getPath()) : this.output;
        return (Modifier) ((IForgeRegistry) Registries.Modifier.get()).getValue(myLocation);
    }

    @Override
    public ResourceLocation getRegistryId() {
        return this.m_6423_();
    }
}