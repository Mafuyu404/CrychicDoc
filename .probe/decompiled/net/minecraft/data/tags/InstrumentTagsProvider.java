package net.minecraft.data.tags;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.InstrumentTags;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.Instruments;

public class InstrumentTagsProvider extends TagsProvider<Instrument> {

    public InstrumentTagsProvider(PackOutput packOutput0, CompletableFuture<HolderLookup.Provider> completableFutureHolderLookupProvider1) {
        super(packOutput0, Registries.INSTRUMENT, completableFutureHolderLookupProvider1);
    }

    @Override
    protected void addTags(HolderLookup.Provider holderLookupProvider0) {
        this.m_206424_(InstrumentTags.REGULAR_GOAT_HORNS).add(Instruments.PONDER_GOAT_HORN).add(Instruments.SING_GOAT_HORN).add(Instruments.SEEK_GOAT_HORN).add(Instruments.FEEL_GOAT_HORN);
        this.m_206424_(InstrumentTags.SCREAMING_GOAT_HORNS).add(Instruments.ADMIRE_GOAT_HORN).add(Instruments.CALL_GOAT_HORN).add(Instruments.YEARN_GOAT_HORN).add(Instruments.DREAM_GOAT_HORN);
        this.m_206424_(InstrumentTags.GOAT_HORNS).addTag(InstrumentTags.REGULAR_GOAT_HORNS).addTag(InstrumentTags.SCREAMING_GOAT_HORNS);
    }
}