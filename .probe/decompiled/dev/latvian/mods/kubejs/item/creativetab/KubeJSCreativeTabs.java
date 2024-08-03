package dev.latvian.mods.kubejs.item.creativetab;

import dev.architectury.registry.registries.DeferredRegister;
import dev.latvian.mods.kubejs.CommonProperties;
import dev.latvian.mods.kubejs.item.ItemStackJS;
import dev.latvian.mods.kubejs.platform.MiscPlatformHelper;
import dev.latvian.mods.kubejs.registry.BuilderBase;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class KubeJSCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> REGISTER = DeferredRegister.create("kubejs", Registries.CREATIVE_MODE_TAB);

    public static void init() {
        if (!CommonProperties.get().serverOnly) {
            REGISTER.register("tab", () -> MiscPlatformHelper.get().creativeModeTab(Component.literal("KubeJS"), () -> {
                ItemStack is = ItemStackJS.of(CommonProperties.get().creativeModeTabIcon);
                return is.isEmpty() ? Items.PURPLE_DYE.getDefaultInstance() : is;
            }, (params, output) -> {
                for (BuilderBase<? extends Item> b : RegistryInfo.ITEM) {
                    output.accept(b.get().getDefaultInstance());
                }
            }));
            REGISTER.register();
        }
    }
}