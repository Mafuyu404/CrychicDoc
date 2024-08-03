package dev.latvian.mods.kubejs.player;

import dev.latvian.mods.kubejs.core.DataSenderKJS;
import dev.latvian.mods.kubejs.core.MessageSenderKJS;
import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

@RemapPrefixForJS("kjs$")
public class EntityArrayList extends ArrayList<Entity> implements MessageSenderKJS, DataSenderKJS {

    public final Level level;

    public EntityArrayList(Level l, int size) {
        super(size);
        this.level = l;
    }

    public EntityArrayList(Level l, Iterable<? extends Entity> entities) {
        this(l, entities instanceof Collection c ? c.size() : 4);
        this.addAllIterable(entities);
    }

    public void addAllIterable(Iterable<? extends Entity> entities) {
        if (entities instanceof Collection c) {
            this.addAll(c);
        } else {
            for (Entity entity : entities) {
                this.add(entity);
            }
        }
    }

    @Override
    public Component kjs$getName() {
        return Component.literal("EntityList");
    }

    @Override
    public Component kjs$getDisplayName() {
        return Component.literal(this.toString()).kjs$lightPurple();
    }

    @Override
    public void kjs$tell(Component message) {
        for (Entity entity : this) {
            entity.kjs$tell(message);
        }
    }

    @Override
    public void kjs$setStatusMessage(Component message) {
        for (Entity entity : this) {
            entity.kjs$setStatusMessage(message);
        }
    }

    @Override
    public int kjs$runCommand(String command) {
        int m = 0;
        for (Entity entity : this) {
            m = Math.max(m, entity.kjs$runCommand(command));
        }
        return m;
    }

    @Override
    public int kjs$runCommandSilent(String command) {
        int m = 0;
        for (Entity entity : this) {
            m = Math.max(m, entity.kjs$runCommandSilent(command));
        }
        return m;
    }

    public void kill() {
        for (Entity entity : this) {
            entity.kill();
        }
    }

    public void playSound(SoundEvent id, float volume, float pitch) {
        for (Entity entity : this) {
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), id, entity.getSoundSource(), volume, pitch);
        }
    }

    public void playSound(SoundEvent id) {
        this.playSound(id, 1.0F, 1.0F);
    }

    public EntityArrayList filter(Predicate<Entity> filter) {
        if (this.isEmpty()) {
            return this;
        } else {
            EntityArrayList list = new EntityArrayList(this.level, this.size());
            for (Entity entity : this) {
                if (filter.test(entity)) {
                    list.add(entity);
                }
            }
            return list;
        }
    }

    public EntityArrayList filterSelector(EntitySelector selector) {
        return this.filter(selector.predicate);
    }

    @Override
    public void kjs$sendData(String channel, @Nullable CompoundTag data) {
        for (Entity entity : this) {
            if (entity instanceof Player player) {
                player.kjs$sendData(channel, data);
            }
        }
    }

    @Nullable
    public Entity getFirst() {
        return this.isEmpty() ? null : (Entity) this.get(0);
    }
}