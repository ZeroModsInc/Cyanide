package com.github.zeromodsinc.cyanide.ui.core;

import com.github.zeromodsinc.cyanide.bridge.client.gui.GuiScreenBridge;
import com.github.zeromodsinc.cyanide.ui.core.drawables.Rectangle;
import com.github.zeromodsinc.cyanide.ui.elements.Button;
import com.github.zeromodsinc.cyanide.ui.elements.PanelHeader;
import com.github.zeromodsinc.cyanide.ui.panorama.PanoramaRenderer;
import com.github.zeromodsinc.cyanide.utility.Utility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

import java.awt.*;

public abstract class Panel extends GuiScreen {
    protected final Minecraft mc = Minecraft.getMinecraft();

    protected String title;
    protected GuiScreen parent;
    protected boolean renderHeader;

    protected final PanelHeader header = PanelHeader.create("");

    public Panel(String title, GuiScreen parent, boolean renderHeader) {
        this.title = title;
        this.parent = parent;
        this.renderHeader = renderHeader;
    }

    public Panel(String title, GuiScreen parent) {
        this(title, parent, true);
    }

    public Panel(String title) {
        this(title, null);
    }

    public abstract void init();
    public abstract void render(int mouseX, int mouseY, float delta);

    public void initGui() {
        this.init();
    }

    public void drawScreen(int mouseX, int mouseY, float delta) {
        ScaledResolution res = Utility.res();
        GlStateManager.enableAlpha();

        if (this.mc.theWorld == null) {
            PanoramaRenderer.INSTANCE.render(delta / 4f, 1f);
        } else {
            Rectangle.create().setDimensions(0f, 0f, res.getScaledWidth(), res.getScaledHeight())
                    .setColor(new Color(0, 0, 0, 100))
                    .render();
        }

        if (this.renderHeader) {
            this.header.setTitle(this.title).render();
        }

        this.render(mouseX, mouseY, delta);

        super.drawScreen(mouseX, mouseY, delta);
    }

    public void add(Button button) {
        GuiScreenBridge.from(this).bridge$getCyanideButtonList().add(button);
    }

    public void transfer(GuiScreen screen) {
        this.mc.displayGuiScreen(screen);
    }

    protected float midWidthRounded() {
        return (int) (this.width / 2f);
    }

    protected float midHeightRounded() {
        return (int) (this.height / 2f);
    }

    protected void texture(ResourceLocation location, float left, float top, float right, float bottom) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer buffer = tessellator.getWorldRenderer();

        this.mc.getTextureManager().bindTexture(location);
        GlStateManager.color(1f, 1f, 1f, 1f);
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        buffer.pos(left, bottom, 0d).tex(0d, 1d).color(1f, 1f, 1f, 1f).endVertex();
        buffer.pos(right, bottom, 0d).tex(1d, 1d).color(1f, 1f, 1f, 1f).endVertex();
        buffer.pos(right, top, 0d).tex(1d, 0d).color(1f, 1f, 1f, 1f).endVertex();
        buffer.pos(left, top, 0d).tex(0d, 0d).color(1f, 1f, 1f, 1f).endVertex();
        tessellator.draw();
    }
}
