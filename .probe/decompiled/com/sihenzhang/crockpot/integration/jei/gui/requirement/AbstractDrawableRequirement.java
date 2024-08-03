package com.sihenzhang.crockpot.integration.jei.gui.requirement;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.sihenzhang.crockpot.integration.jei.gui.DrawableNineSliceResource;
import com.sihenzhang.crockpot.recipe.cooking.requirement.IRequirement;
import com.sihenzhang.crockpot.recipe.cooking.requirement.RequirementCategoryMax;
import com.sihenzhang.crockpot.recipe.cooking.requirement.RequirementCategoryMaxExclusive;
import com.sihenzhang.crockpot.recipe.cooking.requirement.RequirementCategoryMin;
import com.sihenzhang.crockpot.recipe.cooking.requirement.RequirementCategoryMinExclusive;
import com.sihenzhang.crockpot.recipe.cooking.requirement.RequirementCombinationAnd;
import com.sihenzhang.crockpot.recipe.cooking.requirement.RequirementCombinationOr;
import com.sihenzhang.crockpot.recipe.cooking.requirement.RequirementMustContainIngredient;
import com.sihenzhang.crockpot.recipe.cooking.requirement.RequirementMustContainIngredientLessThan;
import com.sihenzhang.crockpot.util.RLUtils;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public abstract class AbstractDrawableRequirement<T extends IRequirement> implements IDrawable {

    protected final T requirement;

    protected final Component description;

    protected AbstractDrawableRequirement(T requirement, Component description) {
        this.requirement = requirement;
        this.description = description;
    }

    @Override
    public void draw(GuiGraphics guiGraphics, int xOffset, int yOffset) {
        this.drawRequirementBackground(guiGraphics, xOffset, yOffset);
    }

    private void drawRequirementBackground(GuiGraphics guiGraphics, int xOffset, int yOffset) {
        DrawableNineSliceResource drawable = new DrawableNineSliceResource(RLUtils.createRL("textures/gui/jei/requirement_background.png"), 0, 0, 64, 64, this.getWidth(), this.getHeight(), 8, 8, 8, 8, 64, 64);
        drawable.draw(guiGraphics, xOffset, yOffset);
    }

    public abstract List<ItemStack> getInvisibleInputs();

    public abstract List<AbstractDrawableRequirement.GuiItemStacksInfo> getGuiItemStacksInfos(int var1, int var2);

    public static AbstractDrawableRequirement<? extends IRequirement> createDrawable(IRequirement requirement) {
        if (requirement instanceof RequirementCategoryMax requirementCategoryMax) {
            return new DrawableRequirementCategoryMax(requirementCategoryMax);
        } else if (requirement instanceof RequirementCategoryMaxExclusive requirementCategoryMaxExclusive) {
            return new DrawableRequirementCategoryMaxExclusive(requirementCategoryMaxExclusive);
        } else if (requirement instanceof RequirementCategoryMin requirementCategoryMin) {
            return new DrawableRequirementCategoryMin(requirementCategoryMin);
        } else if (requirement instanceof RequirementCategoryMinExclusive requirementCategoryMinExclusive) {
            return new DrawableRequirementCategoryMinExclusive(requirementCategoryMinExclusive);
        } else if (requirement instanceof RequirementCombinationAnd requirementCombinationAnd) {
            return new DrawableRequirementCombinationAnd(requirementCombinationAnd);
        } else if (requirement instanceof RequirementCombinationOr requirementCombinationOr) {
            return new DrawableRequirementCombinationOr(requirementCombinationOr);
        } else if (requirement instanceof RequirementMustContainIngredient requirementMustContainIngredient) {
            return new DrawableRequirementMustContainIngredient(requirementMustContainIngredient);
        } else if (requirement instanceof RequirementMustContainIngredientLessThan requirementMustContainIngredientLessThan) {
            return new DrawableRequirementMustContainIngredientLessThan(requirementMustContainIngredientLessThan);
        } else {
            throw new IllegalArgumentException("No valid requirement was found");
        }
    }

    public static List<AbstractDrawableRequirement<? extends IRequirement>> getDrawables(List<IRequirement> requirements) {
        Builder<AbstractDrawableRequirement<? extends IRequirement>> builder = ImmutableList.builder();
        if (requirements.isEmpty()) {
            builder.add(new AbstractDrawableRequirement<IRequirement>(null, Component.translatable("integration.crockpot.jei.crock_pot_cooking.requirement.no_requirement")) {

                @Override
                public int getWidth() {
                    return 6 + Minecraft.getInstance().font.width(this.description);
                }

                @Override
                public int getHeight() {
                    return 14;
                }

                @Override
                public void draw(GuiGraphics guiGraphics, int xOffset, int yOffset) {
                    super.draw(guiGraphics, xOffset, yOffset);
                    guiGraphics.drawString(Minecraft.getInstance().font, this.description, xOffset + 3, yOffset + 3, 0, false);
                }

                @Override
                public List<ItemStack> getInvisibleInputs() {
                    return List.of();
                }

                @Override
                public List<AbstractDrawableRequirement.GuiItemStacksInfo> getGuiItemStacksInfos(int xOffset, int yOffset) {
                    return List.of();
                }
            });
        } else {
            List<IRequirement> tmpRequirements = new ArrayList(requirements);
            Iterator<IRequirement> it = tmpRequirements.iterator();
            while (it.hasNext()) {
                IRequirement requirement = (IRequirement) it.next();
                if (!(requirement instanceof RequirementMustContainIngredient) && !(requirement instanceof RequirementMustContainIngredientLessThan)) {
                    builder.add(createDrawable(requirement));
                } else if (requirement instanceof RequirementMustContainIngredient) {
                    RequirementMustContainIngredient requirementMustContainIngredient = (RequirementMustContainIngredient) requirement;
                    Optional<RequirementMustContainIngredientLessThan> requirementMustContainIngredientLessThan = tmpRequirements.stream().filter(RequirementMustContainIngredientLessThan.class::isInstance).map(RequirementMustContainIngredientLessThan.class::cast).filter(r -> {
                        if (requirementMustContainIngredient.getQuantity() != r.getQuantity()) {
                            return false;
                        } else {
                            IntList first = requirementMustContainIngredient.getIngredient().getStackingIds();
                            IntList second = r.getIngredient().getStackingIds();
                            return first.size() == second.size() && first.containsAll(second) && second.containsAll(first);
                        }
                    }).findFirst();
                    if (requirementMustContainIngredientLessThan.isPresent()) {
                        builder.add(new DrawableRequirementMustContainIngredient(requirementMustContainIngredient, (RequirementMustContainIngredientLessThan) requirementMustContainIngredientLessThan.get()));
                    } else {
                        builder.add(createDrawable(requirement));
                    }
                } else {
                    RequirementMustContainIngredientLessThan requirementMustContainIngredientLessThan = (RequirementMustContainIngredientLessThan) requirement;
                    Optional<RequirementMustContainIngredient> requirementMustContainIngredient = tmpRequirements.stream().filter(RequirementMustContainIngredient.class::isInstance).map(RequirementMustContainIngredient.class::cast).filter(r -> {
                        if (requirementMustContainIngredientLessThan.getQuantity() != r.getQuantity()) {
                            return false;
                        } else {
                            IntList first = requirementMustContainIngredientLessThan.getIngredient().getStackingIds();
                            IntList second = r.getIngredient().getStackingIds();
                            return first.size() == second.size() && first.containsAll(second) && second.containsAll(first);
                        }
                    }).findFirst();
                    if (requirementMustContainIngredient.isPresent()) {
                        it.remove();
                    } else {
                        builder.add(createDrawable(requirement));
                    }
                }
            }
        }
        return builder.build();
    }

    public static class GuiItemStacksInfo {

        public RecipeIngredientRole role;

        public List<ItemStack> stacks;

        public int x;

        public int y;

        public GuiItemStacksInfo(List<ItemStack> stacks, int x, int y, boolean isRenderOnly) {
            this.role = isRenderOnly ? RecipeIngredientRole.RENDER_ONLY : RecipeIngredientRole.INPUT;
            this.stacks = stacks;
            this.x = x;
            this.y = y;
        }

        public GuiItemStacksInfo(List<ItemStack> stacks, int x, int y) {
            this(stacks, x, y, false);
        }
    }
}