package com.github.zeromodsinc.cyanide.ui.core.drawables;

import com.github.zeromodsinc.cyanide.ui.core.Drawable;
import com.github.zeromodsinc.cyanide.ui.core.Vector2;
import com.github.zeromodsinc.cyanide.ui.render.CyanideGL;
import com.github.zeromodsinc.cyanide.utility.Utility;
import lombok.AllArgsConstructor;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

import java.awt.Color;

@AllArgsConstructor
public class Rectangle extends Drawable {
    private float x;
    private float y;
    private float width;
    private float height;
    private boolean gradient;
    private boolean outline;
    private float opacityMultiplier;
    private float outlineThickness;

    // Disabled if 'gradient' is true
    private Color color;

    // Disabled if 'gradient' is false
    private Color colorTopLeft;
    private Color colorTopRight;
    private Color colorBottomLeft;
    private Color colorBottomRight;

    private void renderInternal(float left, float top, float right, float bottom,
                                float r, float g, float b, float a,
                                float tlR, float tlG, float tlB, float tlA,
                                float trR, float trG, float trB, float trA,
                                float blR, float blG, float blB, float blA,
                                float brR, float brG, float brB, float brA,
                                float z,
                                boolean gradient) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA,
                GL11.GL_ONE, GL11.GL_ZERO);

        if (gradient) {
            GlStateManager.shadeModel(GL11.GL_SMOOTH);
            worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
            worldRenderer.pos(right, top, z).color(trR, trG, trB, trA).endVertex();
            worldRenderer.pos(left, top, z).color(tlR, tlG, tlB, tlA).endVertex();
            worldRenderer.pos(left, bottom, z).color(blR, blG, blB, blA).endVertex();
            worldRenderer.pos(right, bottom, z).color(brR, brG, brB, brA).endVertex();
            tessellator.draw();
            GlStateManager.shadeModel(GL11.GL_FLAT);
        } else {
            GlStateManager.color(r, g, b, a);
            worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
            worldRenderer.pos(left, bottom, z).endVertex();
            worldRenderer.pos(right, bottom, z).endVertex();
            worldRenderer.pos(right, top, z).endVertex();
            worldRenderer.pos(left, top, z).endVertex();
            tessellator.draw();
        }

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public void render() {
        float rawRight = this.x + this.width;
        float rawBottom = this.y + this.height;

        float left = Math.min(this.x, rawRight);
        float top = Math.min(this.y, rawBottom);
        float right = Math.max(this.x, rawRight);
        float bottom = Math.max(this.y, rawBottom);

        float r = this.color.getRed() / 255f;
        float g = this.color.getGreen() / 255f;
        float b = this.color.getBlue() / 255f;
        float a = this.color.getAlpha() / 255f * this.opacityMultiplier;

        float tlR = this.colorTopLeft.getRed() / 255f;
        float tlG = this.colorTopLeft.getGreen() / 255f;
        float tlB = this.colorTopLeft.getBlue() / 255f;
        float tlA = this.colorTopLeft.getAlpha() / 255f * this.opacityMultiplier;

        float trR = this.colorTopRight.getRed() / 255f;
        float trG = this.colorTopRight.getGreen() / 255f;
        float trB = this.colorTopRight.getBlue() / 255f;
        float trA = this.colorTopRight.getAlpha() / 255f * this.opacityMultiplier;

        float blR = this.colorBottomLeft.getRed() / 255f;
        float blG = this.colorBottomLeft.getGreen() / 255f;
        float blB = this.colorBottomLeft.getBlue() / 255f;
        float blA = this.colorBottomLeft.getAlpha() / 255f * this.opacityMultiplier;

        float brR = this.colorBottomRight.getRed() / 255f;
        float brG = this.colorBottomRight.getGreen() / 255f;
        float brB = this.colorBottomRight.getBlue() / 255f;
        float brA = this.colorBottomRight.getAlpha() / 255f * this.opacityMultiplier;

        float z = 0f;

        if (this.outline) {
            CyanideGL.scissor(left, top, this.size().getX(), this.outlineThickness, () ->
                    this.renderInternal(left, top, right, bottom, r, g, b, a, tlR, tlG, tlB, tlA, trR, trG, trB, trA,
                            blR, blG, blB, blA, brR, brG, brB, brA, z, this.gradient));
            CyanideGL.scissor(right - this.outlineThickness, top + this.outlineThickness,
                    this.outlineThickness, this.size().getY() - this.outlineThickness, () ->
                            this.renderInternal(left, top, right, bottom, r, g, b, a, tlR, tlG, tlB, tlA,
                                    trR, trG, trB, trA, blR, blG, blB, blA, brR, brG, brB, brA, z, this.gradient));
            CyanideGL.scissor(left + this.outlineThickness, bottom - this.outlineThickness,
                    this.size().getX() - this.outlineThickness * 2, this.outlineThickness, () ->
                            this.renderInternal(left, top, right, bottom, r, g, b, a, tlR, tlG, tlB, tlA,
                                    trR, trG, trB, trA, blR, blG, blB, blA, brR, brG, brB, brA, z, this.gradient));
            CyanideGL.scissor(left, top + this.outlineThickness,
                    this.outlineThickness, this.size().getY() - this.outlineThickness, () ->
                            this.renderInternal(left, top, right, bottom, r, g, b, a, tlR, tlG, tlB, tlA,
                                    trR, trG, trB, trA, blR, blG, blB, blA, brR, brG, brB, brA, z, this.gradient));
        } else {
            this.renderInternal(left, top, right, bottom, r, g, b, a, tlR, tlG, tlB, tlA, trR, trG, trB, trA,
                    blR, blG, blB, blA, brR, brG, brB, brA, z, this.gradient);
        }
    }

    public Vector2<Float> size() {
        float left = Math.min(this.x, this.x + this.width);
        float top = Math.min(this.y, this.y + this.height);
        float right = Math.max(this.x, this.x + this.width);
        float bottom = Math.max(this.y, this.y + this.height);

        float width = right - left;
        float height = bottom - top;

        return new Vector2<>(width, height);
    }

    public static Rectangle create() {
        Color c = Color.BLACK;
        return new Rectangle(-1f, -1f, 1f, 1f, false, false,
                1f, 1f, c, c, c, c, c);
    }

    public Rectangle setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Rectangle setSize(float width, float height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public Rectangle setDimensions(float x, float y, float width, float height) {
        return this.setPosition(x, y).setSize(width, height);
    }

    public Rectangle setGradient(boolean gradient) {
        this.gradient = gradient;
        return this;
    }

    public Rectangle setOutline(boolean outline) {
        this.outline = outline;
        return this;
    }

    public Rectangle setOpacityMultiplier(float opacityMultiplier) {
        this.opacityMultiplier = opacityMultiplier;
        return this;
    }

    public Rectangle setOutlineThickness(float outlineThickness) {
        this.outlineThickness = outlineThickness;
        return this;
    }

    public Rectangle setColor(Color color) {
        this.color = color;
        return this;
    }

    public Rectangle setColorTopLeft(Color colorTopLeft) {
        this.colorTopLeft = colorTopLeft;
        return this;
    }

    public Rectangle setColorTopRight(Color colorTopRight) {
        this.colorTopRight = colorTopRight;
        return this;
    }

    public Rectangle setColorBottomLeft(Color colorBottomLeft) {
        this.colorBottomLeft = colorBottomLeft;
        return this;
    }

    public Rectangle setColorBottomRight(Color colorBottomRight) {
        this.colorBottomRight = colorBottomRight;
        return this;
    }

    public Rectangle setColor(Color colorTopLeft, Color colorTopRight, Color colorBottomLeft, Color colorBottomRight) {
        return this.setColorTopLeft(colorTopLeft)
                .setColorTopRight(colorTopRight)
                .setColorBottomLeft(colorBottomLeft)
                .setColorBottomRight(colorBottomRight);
    }
}
