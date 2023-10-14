package com.github.zeromodsinc.cyanide;

import com.github.zeromodsinc.cyanide.bridge.client.gui.GuiScreenBridge;
import com.github.zeromodsinc.cyanide.ui.elements.Button;
import com.github.zeromodsinc.cyanide.utility.Logger;
import com.github.zeromodsinc.cyanide.utility.Memory;
import com.github.zeromodsinc.cyanide.utility.Utility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.init.Blocks;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;

@Mod(modid = "cyanide", useMetadata = true)
public class Cyanide {
    private static final boolean RENDER_DEBUG_HEADER = false;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        new Thread(() -> {
            while (true) {
                try {
                    Runtime runtime = Runtime.getRuntime();
                    Memory.getMem().set(Memory.SizeType.BYTE, runtime.totalMemory() - runtime.freeMemory());
                    Memory.getHighestMem().set(Memory.SizeType.BYTE, runtime.maxMemory());
                    Memory.getFreeMem().set(Memory.SizeType.BYTE, runtime.freeMemory());

                    Thread.sleep(10);
                } catch (Exception e) {
                    Logger.ERROR.outnf("Caught an error: ", e.getMessage());
                }
            }
        }).start();

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPostDrawScreen(GuiScreenEvent.DrawScreenEvent.Post event) {
        ScaledResolution res = Utility.res();

        if (RENDER_DEBUG_HEADER) {
            Utility.getDebugHeader().setText(Utility.getDebugHeaderText())
                    .setPosition(res.getScaledWidth() / 2f, 2f)
                    .render();
        }

        GuiScreenBridge.from(event.gui).bridge$getCyanideButtonList().forEach(button -> {
            float mouseX = (float) Mouse.getX() / res.getScaleFactor();
            float mouseY = (float) (Minecraft.getMinecraft().displayHeight - Mouse.getY())
                    / res.getScaleFactor();

            button.update(mouseX, mouseY, false);
            button.render();
        });
    }

    @SubscribeEvent
    public void onPreInitGui(GuiScreenEvent.InitGuiEvent.Pre event) {
        GuiScreenBridge.from(event.gui).bridge$getCyanideButtonList().clear();
    }

    @SubscribeEvent
    public void onPostRenderGameOverlay(RenderGameOverlayEvent.Post event) {
        if (event.type != RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            return;
        }

        Utility.getDebugHeader().setText(Utility.getDebugHeaderText())
                .setPosition(Utility.res().getScaledWidth() / 2f, 2f)
                .render();
    }
}
