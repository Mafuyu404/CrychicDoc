package de.keksuccino.fancymenu;

import de.keksuccino.fancymenu.commands.Commands;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class FancyMenuForgeServerEvents {

    public static void registerAll() {
        MinecraftForge.EVENT_BUS.register(new FancyMenuForgeServerEvents());
    }

    @SubscribeEvent
    public void onRegisterServerCommands(RegisterCommandsEvent e) {
        Commands.registerAll(e.getDispatcher());
    }
}