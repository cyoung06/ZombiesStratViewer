package kr.syeyoung.zombiesstratviewer;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.apache.commons.lang3.text.WordUtils;
import org.lwjgl.opengl.GL11;

import java.util.*;

public class EventListener {

    public static final List<String> stratLines = new ArrayList<String>();
    public static int currentLine = 0;
    public static int linesOfView = 1;

    public static int width = 0;
    public static int height = 0;
    public static List<String> actualLines = new ArrayList<String>();

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event)
    {
        if (Keybinds.scrollDown.isPressed()) {
            if (currentLine+1 < stratLines.size());
                currentLine ++;
            recalcActualLines();
        } else if (Keybinds.scrollUp.isPressed()) {
            if (currentLine > 0)
                currentLine --;
            recalcActualLines();
        }
    }

    public static void recalcActualLines() {
        height = 0;
        width = 20;
        FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
        actualLines.clear();
        for (int j = currentLine; j < currentLine + linesOfView; j++) {
            if (j < stratLines.size()) {
                String line = stratLines.get(j);
                if (line.isEmpty()) {
                    actualLines.add("$CONTROL EMPTY$");
                    height += 10;
                } else {
                    line = WordUtils.wrap(line, 55);
                    for (String str : line.split("\n")) {
                        str = str.replace("\r","");
                        actualLines.add(str);
                        int width = fr.getStringWidth(str);
                        if (width > EventListener.width) EventListener.width = width;
                        height += 10;
                    }
                    actualLines.add("$HEY CONTROL$");
                    height += 5;
                }
            }
        }
    }


    @SubscribeEvent
    public void onRenderGui(RenderGameOverlayEvent.Post event)
    {
        if (event.type != RenderGameOverlayEvent.ElementType.EXPERIENCE) return;

        drawRect(3,3,5+width, 5+height, 0x55555555);
        int y = -5;
        for (String str:actualLines) {
            if (str.equalsIgnoreCase("$HEY CONTROL$")) {
                y += 5;
            } else if (str.equalsIgnoreCase("$CONTROL EMPTY$")) {
                y += 10;
            } else {
                drawString(str, y += 10, 0xFFFFFF);
            }
        }
    }

    public void drawString(String str, int y, int color) {
        FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
        fr.drawStringWithShadow(str, 5, y, color);
    }
    public static void drawRect(int left, int top, int right, int bottom, int color) {
        if (left < right) {
            int temp = left;
            left = right;
            right = temp;
        }

        if (top < bottom) {
            int temp = top;
            top = bottom;
            bottom = temp;
        }

        final float red = (float) (color >> 16 & 255) / 255.0F;
        final float green = (float) (color >> 8 & 255) / 255.0F;
        final float blue = (float) (color & 255) / 255.0F;
        final float alpha = (float) (color >> 24 & 255) / 255.0F;

        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer buffer = tessellator.getWorldRenderer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(red, green, blue, alpha);

        buffer.begin(7, DefaultVertexFormats.POSITION);
        buffer.pos((double) left, (double) bottom, 0.0D).endVertex();
        buffer.pos((double) right, (double) bottom, 0.0D).endVertex();
        buffer.pos((double) right, (double) top, 0.0D).endVertex();
        buffer.pos((double) left, (double) top, 0.0D).endVertex();
        tessellator.draw();

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
}
