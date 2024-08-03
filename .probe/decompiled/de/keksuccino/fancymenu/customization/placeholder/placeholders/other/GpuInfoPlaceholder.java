package de.keksuccino.fancymenu.customization.placeholder.placeholders.other;

import com.mojang.blaze3d.platform.GlUtil;
import de.keksuccino.fancymenu.customization.placeholder.DeserializedPlaceholderString;
import de.keksuccino.fancymenu.customization.placeholder.Placeholder;
import de.keksuccino.fancymenu.util.LocalizationUtils;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.resources.language.I18n;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GpuInfoPlaceholder extends Placeholder {

    public GpuInfoPlaceholder() {
        super("gpuinfo");
    }

    @Nullable
    @Override
    public List<String> getAlternativeIdentifiers() {
        return List.of("drippy_gpu_info");
    }

    @Override
    public String getReplacementFor(DeserializedPlaceholderString dps) {
        return GlUtil.getRenderer();
    }

    @Nullable
    @Override
    public List<String> getValueNames() {
        return null;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return I18n.get("fancymenu.placeholders.gpu_info");
    }

    @Override
    public List<String> getDescription() {
        return Arrays.asList(LocalizationUtils.splitLocalizedStringLines("fancymenu.placeholders.gpu_info.desc"));
    }

    @Override
    public String getCategory() {
        return I18n.get("fancymenu.fancymenu.editor.dynamicvariabletextfield.categories.other");
    }

    @NotNull
    @Override
    public DeserializedPlaceholderString getDefaultPlaceholderString() {
        return new DeserializedPlaceholderString(this.getIdentifier(), null, "");
    }
}