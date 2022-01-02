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

public class CommandSetStrat extends CommandBase implements ICommand {
    @Override
    public String getCommandName() {
        return "setstrat";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/setstrat (pastebin url)";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        EventListener.stratLines.clear();
        EventListener.stratLines.add("");
        EventListener.currentLine = 0;

        if (args.length == 0) {
            sender.addChatMessage(new TextComponentString("Cleared Strat"));
        } else {
            if (!args[0].startsWith("https://pastebin.com/raw/")) {
                sender.addChatMessage(new TextComponentString("the url must start with https://pastebin.com/raw/"));
                return;
            }
            try {
                URL url = new URL(args[0]);
                HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                huc.setDoInput(true);
                huc.connect();;
                BufferedReader br = new BufferedReader(new InputStreamReader(huc.getInputStream()));
                String str;
                while ((str = br.readLine()) != null) {
                    EventListener.stratLines.add(str);
                }
                EventListener.recalcActualLines();

                br.close();
                huc.getInputStream().close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
