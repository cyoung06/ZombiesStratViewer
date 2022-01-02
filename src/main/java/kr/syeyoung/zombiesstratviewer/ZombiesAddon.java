package kr.syeyoung.zombiesstratviewer;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = ZombiesAddon.MODID, version = ZombiesAddon.VERSION)
public class ZombiesAddon
{
    public static final String MODID = "zombies_strat_viewer";
    public static final String VERSION = "1.0";
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        FMLCommonHandler.instance().bus().register(new EventListener());
        ClientCommandHandler.instance.registerCommand(new CommandSetStrat());
        ClientCommandHandler.instance.registerCommand(new CommandSetLoV());
        Keybinds.register();
    }


}
