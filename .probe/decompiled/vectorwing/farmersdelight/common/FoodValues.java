package vectorwing.farmersdelight.common;

import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import vectorwing.farmersdelight.common.registry.ModEffects;

public class FoodValues {

    public static final int BRIEF_DURATION = 600;

    public static final int SHORT_DURATION = 1200;

    public static final int MEDIUM_DURATION = 3600;

    public static final int LONG_DURATION = 6000;

    public static final FoodProperties CABBAGE = new FoodProperties.Builder().nutrition(2).saturationMod(0.4F).build();

    public static final FoodProperties TOMATO = new FoodProperties.Builder().nutrition(1).saturationMod(0.3F).build();

    public static final FoodProperties ONION = new FoodProperties.Builder().nutrition(2).saturationMod(0.4F).build();

    public static final FoodProperties APPLE_CIDER = new FoodProperties.Builder().alwaysEat().effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 1200, 0), 1.0F).build();

    public static final FoodProperties FRIED_EGG = new FoodProperties.Builder().nutrition(4).saturationMod(0.4F).build();

    public static final FoodProperties TOMATO_SAUCE = new FoodProperties.Builder().nutrition(4).saturationMod(0.4F).build();

    public static final FoodProperties WHEAT_DOUGH = new FoodProperties.Builder().nutrition(2).saturationMod(0.3F).effect(() -> new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F).build();

    public static final FoodProperties RAW_PASTA = new FoodProperties.Builder().nutrition(2).saturationMod(0.3F).effect(() -> new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F).build();

    public static final FoodProperties PIE_CRUST = new FoodProperties.Builder().nutrition(2).saturationMod(0.2F).build();

    public static final FoodProperties PUMPKIN_SLICE = new FoodProperties.Builder().nutrition(3).saturationMod(0.3F).build();

    public static final FoodProperties CABBAGE_LEAF = new FoodProperties.Builder().nutrition(1).saturationMod(0.4F).fast().build();

    public static final FoodProperties MINCED_BEEF = new FoodProperties.Builder().nutrition(2).saturationMod(0.3F).meat().fast().build();

    public static final FoodProperties BEEF_PATTY = new FoodProperties.Builder().nutrition(4).saturationMod(0.8F).meat().fast().build();

    public static final FoodProperties CHICKEN_CUTS = new FoodProperties.Builder().nutrition(1).saturationMod(0.3F).effect(() -> new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F).meat().fast().build();

    public static final FoodProperties COOKED_CHICKEN_CUTS = new FoodProperties.Builder().nutrition(3).saturationMod(0.6F).meat().fast().build();

    public static final FoodProperties BACON = new FoodProperties.Builder().nutrition(2).saturationMod(0.3F).meat().fast().build();

    public static final FoodProperties COOKED_BACON = new FoodProperties.Builder().nutrition(4).saturationMod(0.8F).meat().fast().build();

    public static final FoodProperties COD_SLICE = new FoodProperties.Builder().nutrition(1).saturationMod(0.1F).fast().build();

    public static final FoodProperties COOKED_COD_SLICE = new FoodProperties.Builder().nutrition(3).saturationMod(0.5F).fast().build();

    public static final FoodProperties SALMON_SLICE = new FoodProperties.Builder().nutrition(1).saturationMod(0.1F).fast().build();

    public static final FoodProperties COOKED_SALMON_SLICE = new FoodProperties.Builder().nutrition(3).saturationMod(0.8F).fast().build();

    public static final FoodProperties MUTTON_CHOP = new FoodProperties.Builder().nutrition(1).saturationMod(0.3F).meat().fast().build();

    public static final FoodProperties COOKED_MUTTON_CHOP = new FoodProperties.Builder().nutrition(3).saturationMod(0.8F).meat().fast().build();

    public static final FoodProperties HAM = new FoodProperties.Builder().nutrition(5).saturationMod(0.3F).meat().build();

    public static final FoodProperties SMOKED_HAM = new FoodProperties.Builder().nutrition(10).saturationMod(0.8F).meat().build();

    public static final FoodProperties POPSICLE = new FoodProperties.Builder().nutrition(3).saturationMod(0.2F).fast().alwaysEat().build();

    public static final FoodProperties COOKIES = new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).fast().build();

    public static final FoodProperties CAKE_SLICE = new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).fast().effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400, 0, false, false), 1.0F).build();

    public static final FoodProperties PIE_SLICE = new FoodProperties.Builder().nutrition(3).saturationMod(0.3F).fast().effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 0, false, false), 1.0F).build();

    public static final FoodProperties FRUIT_SALAD = new FoodProperties.Builder().nutrition(6).saturationMod(0.6F).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100, 0), 1.0F).build();

    public static final FoodProperties GLOW_BERRY_CUSTARD = new FoodProperties.Builder().nutrition(7).saturationMod(0.6F).alwaysEat().effect(() -> new MobEffectInstance(MobEffects.GLOWING, 100, 0), 1.0F).build();

    public static final FoodProperties MIXED_SALAD = new FoodProperties.Builder().nutrition(6).saturationMod(0.6F).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100, 0), 1.0F).build();

    public static final FoodProperties NETHER_SALAD = new FoodProperties.Builder().nutrition(5).saturationMod(0.4F).effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 240, 0), 0.3F).build();

    public static final FoodProperties BARBECUE_STICK = new FoodProperties.Builder().nutrition(8).saturationMod(0.9F).build();

    public static final FoodProperties EGG_SANDWICH = new FoodProperties.Builder().nutrition(8).saturationMod(0.8F).build();

    public static final FoodProperties CHICKEN_SANDWICH = new FoodProperties.Builder().nutrition(10).saturationMod(0.8F).build();

    public static final FoodProperties HAMBURGER = new FoodProperties.Builder().nutrition(11).saturationMod(0.8F).build();

    public static final FoodProperties BACON_SANDWICH = new FoodProperties.Builder().nutrition(10).saturationMod(0.8F).build();

    public static final FoodProperties MUTTON_WRAP = new FoodProperties.Builder().nutrition(10).saturationMod(0.8F).build();

    public static final FoodProperties DUMPLINGS = new FoodProperties.Builder().nutrition(8).saturationMod(0.8F).build();

    public static final FoodProperties STUFFED_POTATO = new FoodProperties.Builder().nutrition(10).saturationMod(0.7F).build();

    public static final FoodProperties CABBAGE_ROLLS = new FoodProperties.Builder().nutrition(5).saturationMod(0.5F).build();

    public static final FoodProperties SALMON_ROLL = new FoodProperties.Builder().nutrition(7).saturationMod(0.6F).build();

    public static final FoodProperties COD_ROLL = new FoodProperties.Builder().nutrition(7).saturationMod(0.6F).build();

    public static final FoodProperties KELP_ROLL = new FoodProperties.Builder().nutrition(12).saturationMod(0.6F).build();

    public static final FoodProperties KELP_ROLL_SLICE = new FoodProperties.Builder().nutrition(6).saturationMod(0.5F).fast().build();

    public static final FoodProperties COOKED_RICE = new FoodProperties.Builder().nutrition(6).saturationMod(0.4F).effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), 600, 0), 1.0F).build();

    public static final FoodProperties BONE_BROTH = new FoodProperties.Builder().nutrition(8).saturationMod(0.7F).effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), 1200, 0), 1.0F).build();

    public static final FoodProperties BEEF_STEW = new FoodProperties.Builder().nutrition(12).saturationMod(0.8F).effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), 3600, 0), 1.0F).build();

    public static final FoodProperties VEGETABLE_SOUP = new FoodProperties.Builder().nutrition(12).saturationMod(0.8F).effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), 3600, 0), 1.0F).build();

    public static final FoodProperties FISH_STEW = new FoodProperties.Builder().nutrition(12).saturationMod(0.8F).effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), 3600, 0), 1.0F).build();

    public static final FoodProperties CHICKEN_SOUP = new FoodProperties.Builder().nutrition(14).saturationMod(0.75F).effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), 6000, 0), 1.0F).build();

    public static final FoodProperties FRIED_RICE = new FoodProperties.Builder().nutrition(14).saturationMod(0.75F).effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), 6000, 0), 1.0F).build();

    public static final FoodProperties PUMPKIN_SOUP = new FoodProperties.Builder().nutrition(14).saturationMod(0.75F).effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), 6000, 0), 1.0F).build();

    public static final FoodProperties BAKED_COD_STEW = new FoodProperties.Builder().nutrition(14).saturationMod(0.75F).effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), 6000, 0), 1.0F).build();

    public static final FoodProperties NOODLE_SOUP = new FoodProperties.Builder().nutrition(14).saturationMod(0.75F).effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), 6000, 0), 1.0F).build();

    public static final FoodProperties BACON_AND_EGGS = new FoodProperties.Builder().nutrition(10).saturationMod(0.6F).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 1200, 0), 1.0F).build();

    public static final FoodProperties RATATOUILLE = new FoodProperties.Builder().nutrition(10).saturationMod(0.6F).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 1200, 0), 1.0F).build();

    public static final FoodProperties STEAK_AND_POTATOES = new FoodProperties.Builder().nutrition(12).saturationMod(0.8F).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 3600, 0), 1.0F).build();

    public static final FoodProperties PASTA_WITH_MEATBALLS = new FoodProperties.Builder().nutrition(12).saturationMod(0.8F).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 3600, 0), 1.0F).build();

    public static final FoodProperties PASTA_WITH_MUTTON_CHOP = new FoodProperties.Builder().nutrition(12).saturationMod(0.8F).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 3600, 0), 1.0F).build();

    public static final FoodProperties MUSHROOM_RICE = new FoodProperties.Builder().nutrition(12).saturationMod(0.8F).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 3600, 0), 1.0F).build();

    public static final FoodProperties ROASTED_MUTTON_CHOPS = new FoodProperties.Builder().nutrition(14).saturationMod(0.75F).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 6000, 0), 1.0F).build();

    public static final FoodProperties VEGETABLE_NOODLES = new FoodProperties.Builder().nutrition(14).saturationMod(0.75F).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 6000, 0), 1.0F).build();

    public static final FoodProperties SQUID_INK_PASTA = new FoodProperties.Builder().nutrition(14).saturationMod(0.75F).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 6000, 0), 1.0F).build();

    public static final FoodProperties GRILLED_SALMON = new FoodProperties.Builder().nutrition(14).saturationMod(0.75F).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 3600, 0), 1.0F).build();

    public static final FoodProperties ROAST_CHICKEN = new FoodProperties.Builder().nutrition(14).saturationMod(0.75F).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 6000, 0), 1.0F).build();

    public static final FoodProperties STUFFED_PUMPKIN = new FoodProperties.Builder().nutrition(14).saturationMod(0.75F).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 6000, 0), 1.0F).build();

    public static final FoodProperties HONEY_GLAZED_HAM = new FoodProperties.Builder().nutrition(14).saturationMod(0.75F).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 6000, 0), 1.0F).build();

    public static final FoodProperties SHEPHERDS_PIE = new FoodProperties.Builder().nutrition(14).saturationMod(0.75F).effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 6000, 0), 1.0F).build();

    public static final FoodProperties DOG_FOOD = new FoodProperties.Builder().nutrition(4).saturationMod(0.2F).meat().build();

    public static final Map<Item, FoodProperties> VANILLA_SOUP_EFFECTS = new Builder().put(Items.MUSHROOM_STEW, new FoodProperties.Builder().effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), 3600, 0), 1.0F).build()).put(Items.BEETROOT_SOUP, new FoodProperties.Builder().effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), 3600, 0), 1.0F).build()).put(Items.RABBIT_STEW, new FoodProperties.Builder().effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), 6000, 0), 1.0F).build()).build();
}