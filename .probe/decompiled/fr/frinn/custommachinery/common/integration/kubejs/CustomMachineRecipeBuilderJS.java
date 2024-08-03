package fr.frinn.custommachinery.common.integration.kubejs;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import dev.latvian.mods.kubejs.recipe.RecipeExceptionJS;
import fr.frinn.custommachinery.api.guielement.IGuiElement;
import fr.frinn.custommachinery.common.crafting.machine.CustomMachineRecipeBuilder;
import fr.frinn.custommachinery.common.init.Registration;
import fr.frinn.custommachinery.common.integration.kubejs.requirements.BiomeRequirementJS;
import fr.frinn.custommachinery.common.integration.kubejs.requirements.BlockRequirementJS;
import fr.frinn.custommachinery.common.integration.kubejs.requirements.ButtonRequirementJS;
import fr.frinn.custommachinery.common.integration.kubejs.requirements.ChunkloadRequirementJS;
import fr.frinn.custommachinery.common.integration.kubejs.requirements.CommandRequirementJS;
import fr.frinn.custommachinery.common.integration.kubejs.requirements.DimensionRequirementJS;
import fr.frinn.custommachinery.common.integration.kubejs.requirements.DropRequirementJS;
import fr.frinn.custommachinery.common.integration.kubejs.requirements.DurabilityRequirementJS;
import fr.frinn.custommachinery.common.integration.kubejs.requirements.EffectRequirementJS;
import fr.frinn.custommachinery.common.integration.kubejs.requirements.EnergyPerTickRequirementJS;
import fr.frinn.custommachinery.common.integration.kubejs.requirements.EnergyRequirementJS;
import fr.frinn.custommachinery.common.integration.kubejs.requirements.EntityRequirementJS;
import fr.frinn.custommachinery.common.integration.kubejs.requirements.ExperiencePerTickRequirementJS;
import fr.frinn.custommachinery.common.integration.kubejs.requirements.ExperienceRequirementJS;
import fr.frinn.custommachinery.common.integration.kubejs.requirements.FluidPerTickRequirementJS;
import fr.frinn.custommachinery.common.integration.kubejs.requirements.FluidRequirementJS;
import fr.frinn.custommachinery.common.integration.kubejs.requirements.FuelRequirementJS;
import fr.frinn.custommachinery.common.integration.kubejs.requirements.FunctionRequirementJS;
import fr.frinn.custommachinery.common.integration.kubejs.requirements.ItemFilterRequirementJS;
import fr.frinn.custommachinery.common.integration.kubejs.requirements.ItemRequirementJS;
import fr.frinn.custommachinery.common.integration.kubejs.requirements.ItemTransformRequirementJS;
import fr.frinn.custommachinery.common.integration.kubejs.requirements.LightRequirementJS;
import fr.frinn.custommachinery.common.integration.kubejs.requirements.LootTableRequirementJS;
import fr.frinn.custommachinery.common.integration.kubejs.requirements.PositionRequirementJS;
import fr.frinn.custommachinery.common.integration.kubejs.requirements.RedstoneRequirementJS;
import fr.frinn.custommachinery.common.integration.kubejs.requirements.SkyRequirementJS;
import fr.frinn.custommachinery.common.integration.kubejs.requirements.StructureRequirementJS;
import fr.frinn.custommachinery.common.integration.kubejs.requirements.TimeRequirementJS;
import fr.frinn.custommachinery.common.integration.kubejs.requirements.WeatherRequirementJS;
import fr.frinn.custommachinery.common.util.Utils;
import java.util.Arrays;
import java.util.function.Consumer;

public class CustomMachineRecipeBuilderJS extends AbstractRecipeJSBuilder<CustomMachineRecipeBuilder> implements ItemRequirementJS, ItemTransformRequirementJS, DurabilityRequirementJS, FluidRequirementJS, FluidPerTickRequirementJS, EnergyRequirementJS, EnergyPerTickRequirementJS, TimeRequirementJS, PositionRequirementJS, BiomeRequirementJS, DimensionRequirementJS, FuelRequirementJS, CommandRequirementJS, EffectRequirementJS, WeatherRequirementJS, RedstoneRequirementJS, LightRequirementJS, EntityRequirementJS, BlockRequirementJS, StructureRequirementJS, LootTableRequirementJS, DropRequirementJS, FunctionRequirementJS, ButtonRequirementJS, SkyRequirementJS, ItemFilterRequirementJS, ExperienceRequirementJS, ExperiencePerTickRequirementJS, ChunkloadRequirementJS {

    public CustomMachineRecipeBuilderJS() {
        super(Registration.CUSTOM_MACHINE_RECIPE.getId());
    }

    public CustomMachineRecipeBuilder makeBuilder() {
        CustomMachineRecipeBuilder builder = new CustomMachineRecipeBuilder(this.getValue(CustomMachineryRecipeSchemas.MACHINE_ID), this.getValue(CustomMachineryRecipeSchemas.TIME).intValue());
        if (this.getValue(CustomMachineryRecipeSchemas.ERROR)) {
            builder.setResetOnError();
        }
        if (this.getValue(CustomMachineryRecipeSchemas.APPEARANCE) != null) {
            builder.withAppearance(this.getValue(CustomMachineryRecipeSchemas.APPEARANCE));
        }
        Arrays.stream(this.getValue(CustomMachineryRecipeSchemas.GUI)).forEach(builder::withGuiElement);
        return builder;
    }

    public CustomMachineRecipeBuilderJS resetOnError() {
        this.setValue(CustomMachineryRecipeSchemas.ERROR, Boolean.valueOf(true));
        return this;
    }

    public CustomMachineRecipeBuilderJS appearance(Consumer<MachineAppearanceBuilderJS> consumer) {
        MachineAppearanceBuilderJS builder = new MachineAppearanceBuilderJS();
        consumer.accept(builder);
        this.setValue(CustomMachineryRecipeSchemas.APPEARANCE, builder.build());
        return this;
    }

    public CustomMachineRecipeBuilderJS gui(JsonObject... elements) {
        for (JsonObject json : elements) {
            IGuiElement.CODEC.read(JsonOps.INSTANCE, json).resultOrPartial(s -> {
                throw new RecipeExceptionJS("Error when parsing recipe custom gui element\n" + json + "\n" + s);
            }).ifPresent(element -> this.setValue(CustomMachineryRecipeSchemas.GUI, Utils.addToArray(this.getValue(CustomMachineryRecipeSchemas.GUI), element)));
        }
        return this;
    }
}