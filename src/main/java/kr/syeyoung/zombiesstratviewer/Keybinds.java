package kr.syeyoung.zombiesstratviewer;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class Keybinds
{
    public static KeyBinding scrollUp;
    public static KeyBinding scrollDown;
 
    public static void register()
    {
        scrollUp = new KeyBinding("key.scrollUo", Keyboard.KEY_O, "key.categories.zsv");
        scrollDown = new KeyBinding("key.scrollDown", Keyboard.KEY_L, "key.categories.zsv");

        ClientRegistry.registerKeyBinding(scrollUp);
        ClientRegistry.registerKeyBinding(scrollDown);
    }
}