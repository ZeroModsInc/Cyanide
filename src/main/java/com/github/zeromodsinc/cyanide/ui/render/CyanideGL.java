package com.github.zeromodsinc.cyanide.ui.render;

import com.github.zeromodsinc.cyanide.utility.Utility;
import com.google.common.base.Throwables;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

public class CyanideGL {
    public static String getVendor() {
        return GL11.glGetString(GL11.GL_VENDOR);
    }

    public static String getRenderer() {
        return GL11.glGetString(GL11.GL_RENDERER);
    }

    public static String getVersion() {
        return GL11.glGetString(GL11.GL_VERSION);
    }

    public static String getShadingLanguageVersion() {
        return GL11.glGetString(GL20.GL_SHADING_LANGUAGE_VERSION);
    }

    public static String getExtensions() {
        return GL11.glGetString(GL11.GL_EXTENSIONS);
    }

    public static void scissor(float xIn, float yIn, float widthIn, float heightIn, Runnable onScissor) {
        float scale = Utility.res().getScaleFactor();

        float width = (widthIn * scale);
        float height = (heightIn * scale);
        float x = (xIn * scale);
        float y = Minecraft.getMinecraft().displayHeight - ((yIn + heightIn) * scale);

        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor((int) x, (int) y, (int) width, (int) height);
        onScissor.run();
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    public static void matrix(Runnable onMatrix) {
        GlStateManager.pushMatrix();
        onMatrix.run();
        GlStateManager.popMatrix();
    }

    public static void setupGuiState() {
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution res = Utility.res();
        float width = mc.displayWidth;
        float height = mc.displayHeight;

        GlStateManager.clear(256);
        GlStateManager.matrixMode(GL11.GL_PROJECTION);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0, width / res.getScaleFactor(), height / res.getScaleFactor(),
                0, 1000, 3000);
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.loadIdentity();
        GlStateManager.translate(0f, 0f, -2000f);
    }
}
