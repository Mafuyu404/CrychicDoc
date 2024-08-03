package me.jellysquid.mods.sodium.client.gui.options;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.function.BiConsumer;
import java.util.function.Function;
import me.jellysquid.mods.sodium.client.gui.options.binding.GenericBinding;
import me.jellysquid.mods.sodium.client.gui.options.binding.OptionBinding;
import me.jellysquid.mods.sodium.client.gui.options.control.Control;
import me.jellysquid.mods.sodium.client.gui.options.storage.OptionStorage;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.Validate;
import org.embeddedt.embeddium.client.gui.options.OptionIdentifier;

public class OptionImpl<S, T> implements Option<T> {

    private final OptionStorage<S> storage;

    private final OptionBinding<S, T> binding;

    private final Control<T> control;

    private final EnumSet<OptionFlag> flags;

    private final OptionIdentifier<T> id;

    private final Component name;

    private final Component tooltip;

    private final OptionImpact impact;

    private T value;

    private T modifiedValue;

    private final boolean enabled;

    private OptionImpl(OptionStorage<S> storage, OptionIdentifier<T> id, Component name, Component tooltip, OptionBinding<S, T> binding, Function<OptionImpl<S, T>, Control<T>> control, EnumSet<OptionFlag> flags, OptionImpact impact, boolean enabled) {
        this.id = id;
        this.storage = storage;
        this.name = name;
        this.tooltip = tooltip;
        this.binding = binding;
        this.impact = impact;
        this.flags = flags;
        this.control = (Control<T>) control.apply(this);
        this.enabled = enabled;
        this.reset();
    }

    @Override
    public OptionIdentifier<T> getId() {
        return this.id;
    }

    @Override
    public Component getName() {
        return this.name;
    }

    @Override
    public Component getTooltip() {
        return this.tooltip;
    }

    @Override
    public OptionImpact getImpact() {
        return this.impact;
    }

    @Override
    public Control<T> getControl() {
        return this.control;
    }

    @Override
    public T getValue() {
        return this.modifiedValue;
    }

    @Override
    public void setValue(T value) {
        this.modifiedValue = value;
    }

    @Override
    public void reset() {
        this.value = this.binding.getValue(this.storage.getData());
        this.modifiedValue = this.value;
    }

    @Override
    public OptionStorage<?> getStorage() {
        return this.storage;
    }

    @Override
    public boolean isAvailable() {
        return this.enabled;
    }

    @Override
    public boolean hasChanged() {
        return !this.value.equals(this.modifiedValue);
    }

    @Override
    public void applyChanges() {
        this.binding.setValue(this.storage.getData(), this.modifiedValue);
        this.value = this.modifiedValue;
    }

    @Override
    public Collection<OptionFlag> getFlags() {
        return this.flags;
    }

    public static <S, T> OptionImpl.Builder<S, T> createBuilder(Class<T> type, OptionStorage<S> storage) {
        return new OptionImpl.Builder<>(storage, type);
    }

    public static class Builder<S, T> {

        private final OptionStorage<S> storage;

        private final Class<T> type;

        private OptionIdentifier<T> id;

        private Component name;

        private Component tooltip;

        private OptionBinding<S, T> binding;

        private Function<OptionImpl<S, T>, Control<T>> control;

        private OptionImpact impact;

        private final EnumSet<OptionFlag> flags = EnumSet.noneOf(OptionFlag.class);

        private boolean enabled = true;

        private Builder(OptionStorage<S> storage, Class<T> type) {
            this.storage = storage;
            this.type = type;
        }

        public OptionImpl.Builder<S, T> setId(ResourceLocation id) {
            Validate.notNull(id, "Id must not be null", new Object[0]);
            this.id = OptionIdentifier.create(id.getNamespace(), id.getPath(), this.type);
            return this;
        }

        public OptionImpl.Builder<S, T> setId(OptionIdentifier<T> id) {
            Validate.notNull(id, "Id must not be null", new Object[0]);
            this.id = id;
            return this;
        }

        public OptionImpl.Builder<S, T> setName(Component name) {
            Validate.notNull(name, "Argument must not be null", new Object[0]);
            this.name = name;
            return this;
        }

        public OptionImpl.Builder<S, T> setTooltip(Component tooltip) {
            Validate.notNull(tooltip, "Argument must not be null", new Object[0]);
            this.tooltip = tooltip;
            return this;
        }

        public OptionImpl.Builder<S, T> setBinding(BiConsumer<S, T> setter, Function<S, T> getter) {
            Validate.notNull(setter, "Setter must not be null", new Object[0]);
            Validate.notNull(getter, "Getter must not be null", new Object[0]);
            this.binding = new GenericBinding<>(setter, getter);
            return this;
        }

        public OptionImpl.Builder<S, T> setBinding(OptionBinding<S, T> binding) {
            Validate.notNull(binding, "Argument must not be null", new Object[0]);
            this.binding = binding;
            return this;
        }

        public OptionImpl.Builder<S, T> setControl(Function<OptionImpl<S, T>, Control<T>> control) {
            Validate.notNull(control, "Argument must not be null", new Object[0]);
            this.control = control;
            return this;
        }

        public OptionImpl.Builder<S, T> setImpact(OptionImpact impact) {
            this.impact = impact;
            return this;
        }

        public OptionImpl.Builder<S, T> setEnabled(boolean value) {
            this.enabled = value;
            return this;
        }

        public OptionImpl.Builder<S, T> setFlags(OptionFlag... flags) {
            Collections.addAll(this.flags, flags);
            return this;
        }

        public OptionImpl<S, T> build() {
            if (this.id == null) {
                this.id = (OptionIdentifier<T>) OptionIdentifier.EMPTY;
            } else {
                if (this.name == null) {
                    this.name = Component.translatable(this.id.getModId() + ".options." + this.id.getPath() + ".name");
                }
                if (this.tooltip == null) {
                    this.tooltip = Component.translatable(this.id.getModId() + ".options." + this.id.getPath() + ".tooltip");
                }
            }
            Validate.notNull(this.name, "Name must be specified or inferred from a specified ID", new Object[0]);
            Validate.notNull(this.tooltip, "Tooltip must be specified or inferred from a specified ID", new Object[0]);
            Validate.notNull(this.binding, "Option binding must be specified", new Object[0]);
            Validate.notNull(this.control, "Control must be specified", new Object[0]);
            return new OptionImpl<>(this.storage, this.id, this.name, this.tooltip, this.binding, this.control, this.flags, this.impact, this.enabled);
        }
    }
}