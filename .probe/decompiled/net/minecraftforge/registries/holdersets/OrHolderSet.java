package net.minecraftforge.registries.holdersets;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.HolderSetCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.common.ForgeMod;

public class OrHolderSet<T> extends CompositeHolderSet<T> {

    public static <T> Codec<? extends ICustomHolderSet<T>> codec(ResourceKey<? extends Registry<T>> registryKey, Codec<Holder<T>> holderCodec, boolean forceList) {
        return HolderSetCodec.create(registryKey, holderCodec, forceList).listOf().xmap(OrHolderSet::new, CompositeHolderSet::homogenize).fieldOf("values").codec();
    }

    public OrHolderSet(List<HolderSet<T>> values) {
        super(values);
    }

    @Override
    public HolderSetType type() {
        return ForgeMod.OR_HOLDER_SET.get();
    }

    @Override
    protected Set<Holder<T>> createSet() {
        return (Set<Holder<T>>) this.getComponents().stream().flatMap(HolderSet::m_203614_).collect(Collectors.toSet());
    }

    public String toString() {
        return "OrSet[" + this.getComponents() + "]";
    }
}