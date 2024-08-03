package net.minecraft.data.worldgen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

public class AncientCityStructurePieces {

    public static final ResourceKey<StructureTemplatePool> START = Pools.createKey("ancient_city/city_center");

    public static void bootstrap(BootstapContext<StructureTemplatePool> bootstapContextStructureTemplatePool0) {
        HolderGetter<StructureProcessorList> $$1 = bootstapContextStructureTemplatePool0.lookup(Registries.PROCESSOR_LIST);
        Holder<StructureProcessorList> $$2 = $$1.getOrThrow(ProcessorLists.ANCIENT_CITY_START_DEGRADATION);
        HolderGetter<StructureTemplatePool> $$3 = bootstapContextStructureTemplatePool0.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> $$4 = $$3.getOrThrow(Pools.EMPTY);
        bootstapContextStructureTemplatePool0.register(START, new StructureTemplatePool($$4, ImmutableList.of(Pair.of(StructurePoolElement.single("ancient_city/city_center/city_center_1", $$2), 1), Pair.of(StructurePoolElement.single("ancient_city/city_center/city_center_2", $$2), 1), Pair.of(StructurePoolElement.single("ancient_city/city_center/city_center_3", $$2), 1)), StructureTemplatePool.Projection.RIGID));
        AncientCityStructurePools.bootstrap(bootstapContextStructureTemplatePool0);
    }
}