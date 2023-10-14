package com.github.zeromodsinc.cyanide.ui.panorama;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;

public class CubeMap {
    private final ResourceLocation[] images;

    public CubeMap(ResourceLocation paths) {
        this.images = new ResourceLocation[6];
        for (int x = 0; x < 6; ++x) {
            this.images[x] = new ResourceLocation(paths.getResourceDomain(),
                    paths.getResourcePath() + '_' + x + ".png");
        }
    }

    public void render(Minecraft client, float pitch, float yaw, float alpha) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer buf = tessellator.getWorldRenderer();
        float width = (float) client.displayWidth;
        float height = (float) client.displayHeight;

        GlStateManager.matrixMode(GL11.GL_PROJECTION);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        Project.gluPerspective(85f, width / height, 0.05f, 10f);
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        GlStateManager.color(1f, 1f, 1f, 1f);
        GlStateManager.rotate(180f, 1f, 0f, 0f);
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.disableCull();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);

        for (int renderIdx = 0; renderIdx < 4; ++renderIdx) {
            GlStateManager.pushMatrix();
            float x = ((renderIdx % 2) / 2f - 0.5f) / 256f;
            float y = ((renderIdx / 2f) / 2f - 0.5f) / 256f;

            GlStateManager.translate(x, y, 0f);
            GlStateManager.rotate(pitch, 1f, 0f, 0f);
            GlStateManager.rotate(yaw, 0f, 1f, 0f);
            for (int face = 0; face < 6; ++face) {
                client.getTextureManager().bindTexture(this.images[face]);
                buf.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                final int alphaI = Math.round(255f * alpha) / (renderIdx + 1);
                switch (face) {
                    case 0:
                        buf.pos(-1, -1, 1).tex(0, 0)
                                .color(255, 255, 255, alphaI).endVertex();
                        buf.pos(-1, 1, 1).tex(0.0, 1)
                                .color(255, 255, 255, alphaI).endVertex();
                        buf.pos(1, 1, 1).tex(1, 1)
                                .color(255, 255, 255, alphaI).endVertex();
                        buf.pos(1, -1, 1).tex(1, 0)
                                .color(255, 255, 255, alphaI).endVertex();
                        break;
                    case 1:
                        buf.pos(1, -1, 1).tex(0, 0)
                                .color(255, 255, 255, alphaI).endVertex();
                        buf.pos(1, 1, 1).tex(0, 1)
                                .color(255, 255, 255, alphaI).endVertex();
                        buf.pos(1, 1, -1).tex(1, 1)
                                .color(255, 255, 255, alphaI).endVertex();
                        buf.pos(1, -1, -1).tex(1, 0)
                                .color(255, 255, 255, alphaI).endVertex();
                        break;
                    case 2:
                        buf.pos(1, -1, -1).tex(0, 0)
                                .color(255, 255, 255, alphaI).endVertex();
                        buf.pos(1, 1, -1).tex(0, 1)
                                .color(255, 255, 255, alphaI).endVertex();
                        buf.pos(-1, 1, -1).tex(1, 1)
                                .color(255, 255, 255, alphaI).endVertex();
                        buf.pos(-1, -1, -1).tex(1, 0)
                                .color(255, 255, 255, alphaI).endVertex();
                        break;
                    case 3:
                        buf.pos(-1, -1, -1).tex(0, 0)
                                .color(255, 255, 255, alphaI).endVertex();
                        buf.pos(-1, 1, -1).tex(0, 1)
                                .color(255, 255, 255, alphaI).endVertex();
                        buf.pos(-1, 1, 1).tex(1, 1)
                                .color(255, 255, 255, alphaI).endVertex();
                        buf.pos(-1, -1, 1).tex(1, 0)
                                .color(255, 255, 255, alphaI).endVertex();
                        break;
                    case 4:
                        buf.pos(-1, -1, -1).tex(0, 0)
                                .color(255, 255, 255, alphaI).endVertex();
                        buf.pos(-1, -1, 1).tex(0, 1)
                                .color(255, 255, 255, alphaI).endVertex();
                        buf.pos(1, -1, 1).tex(1, 1)
                                .color(255, 255, 255, alphaI).endVertex();
                        buf.pos(1, -1, -1).tex(1, 0)
                                .color(255, 255, 255, alphaI).endVertex();
                        break;
                    case 5:
                        buf.pos(-1, 1, 1).tex(0, 0)
                                .color(255, 255, 255, alphaI).endVertex();
                        buf.pos(-1, 1, -1).tex(0, 1)
                                .color(255, 255, 255, alphaI).endVertex();
                        buf.pos(1, 1, -1).tex(1, 1)
                                .color(255, 255, 255, alphaI).endVertex();
                        buf.pos(1, 1, 1).tex(1, 0)
                                .color(255, 255, 255, alphaI).endVertex();
                        break;
                }
                tessellator.draw();
            }
            GlStateManager.popMatrix();
            GlStateManager.colorMask(true, true, true, false);
        }
        GlStateManager.colorMask(true, true, true, true);
        GlStateManager.matrixMode(GL11.GL_PROJECTION);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.popMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.enableCull();
        GlStateManager.enableDepth();
    }
}

