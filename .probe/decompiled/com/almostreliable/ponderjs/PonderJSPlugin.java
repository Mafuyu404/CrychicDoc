package com.almostreliable.ponderjs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.rhino.util.wrap.TypeWrappers;

public class PonderJSPlugin extends KubeJSPlugin {

    @Override
    public void registerBindings(BindingsEvent event) {
        if (event.getType().isClient()) {
            PonderJS.addBindings(event);
        }
    }

    @Override
    public void registerTypeWrappers(ScriptType type, TypeWrappers typeWrappers) {
        if (type == ScriptType.CLIENT) {
            PonderJS.addTypeWrappers(type, typeWrappers);
        }
    }

    @Override
    public void registerEvents() {
        PonderEvents.GROUP.register();
    }
}