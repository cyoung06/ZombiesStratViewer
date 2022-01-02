package kr.syeyoung.zombiesstratviewer;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CommandSetLoV extends CommandBase implements ICommand {
    @Override
    public String getCommandName() {
        return "setlines";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/setlinews (lines)";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        if (args.length == 0) {
            sender.addChatMessage(new TextComponentString("/setlinews (lines)"));
        } else {
            try {
                int toBe = Integer.parseInt(args[0]);
                if (toBe < 1) {
                    throw new RuntimeException("hey");
                }
                EventListener.linesOfView = toBe;
                EventListener.recalcActualLines();
                sender.addChatMessage(new TextComponentString("Set Lines of View to "+EventListener.linesOfView));
            } catch (Exception e ) {
                sender.addChatMessage(new TextComponentString("Hey that's not a number, please put valid number"));
            }
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
