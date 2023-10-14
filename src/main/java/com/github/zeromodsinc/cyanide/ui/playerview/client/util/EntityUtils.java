package com.github.zeromodsinc.cyanide.ui.playerview.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityUtils {
    @SideOnly(Side.CLIENT)
    public static float getModelSize(EntityLivingBase ent) {
        return 1.8F;
    }

    @SideOnly(Side.CLIENT)
    public static void drawEntityOnScreen(int posX, int posY, float scale, float mouseX, float mouseY, EntityLivingBase ent) {
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.translate(posX, posY, 50.0F);
        GlStateManager.scale(-scale, scale, scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float f2 = ent.renderYawOffset;
        float f3 = ent.rotationYaw;
        float f4 = ent.rotationPitch;
        float f5 = ent.prevRotationYawHead;
        float f6 = ent.rotationYawHead;
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-((float) Math.atan(mouseY / 40.0F)) * 20.0F, 1.0F, 0.0F, 0.0F);
        ent.renderYawOffset = (float) Math.atan(mouseX / 40.0F) * 20.0F;
        ent.rotationYaw = (float) Math.atan(mouseX / 40.0F) * 40.0F;
        ent.rotationPitch = -((float) Math.atan(mouseY / 40.0F)) * 20.0F;
        ent.rotationYawHead = ent.rotationYaw;
        ent.prevRotationYawHead = ent.rotationYaw;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);

        if (ent instanceof EntityPlayer) {
            updateCape((EntityPlayer) ent);
        }

        try {
            RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
            rendermanager.setPlayerViewY(180.0F);
            rendermanager.setRenderShadow(false);
            rendermanager.renderEntityWithPosYaw(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
            rendermanager.setRenderShadow(true);
        } finally {
            ent.renderYawOffset = f2;
            ent.rotationYaw = f3;
            ent.rotationPitch = f4;
            ent.prevRotationYawHead = f5;
            ent.rotationYawHead = f6;
            GlStateManager.popMatrix();
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableRescaleNormal();
            GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GlStateManager.disableTexture2D();
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
            GlStateManager.translate(0.0F, 0.0F, 20.0F);
            GlStateManager.resetColor();
        }
    }

    @SideOnly(Side.CLIENT)
    public static void drawEntityOnScreenNoRotation(float posX, float posY, float scale, EntityLivingBase ent) {
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.translate(posX, posY, 50.0F);
        GlStateManager.scale(-scale, scale, scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float f2 = ent.renderYawOffset;
        float f3 = ent.rotationYaw;
        float f4 = ent.rotationPitch;
        float f5 = ent.prevRotationYawHead;
        float f6 = ent.rotationYawHead;
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(0f, 1.0F, 0.0F, 0.0F);
        ent.renderYawOffset = 180f;
        ent.rotationYaw = 180f;
        ent.rotationPitch = 0f;
        ent.rotationYawHead = ent.rotationYaw;
        ent.prevRotationYawHead = ent.rotationYaw;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);

        if (ent instanceof EntityPlayer) {
            updateCape((EntityPlayer) ent);
        }

        try {
            RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
            rendermanager.setPlayerViewY(180.0F);
            rendermanager.setRenderShadow(false);
            rendermanager.renderEntityWithPosYaw(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
            rendermanager.setRenderShadow(true);
        } finally {
            ent.renderYawOffset = f2;
            ent.rotationYaw = f3;
            ent.rotationPitch = f4;
            ent.prevRotationYawHead = f5;
            ent.rotationYawHead = f6;
            GlStateManager.popMatrix();
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableRescaleNormal();
            GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GlStateManager.disableTexture2D();
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
            GlStateManager.translate(0.0F, 0.0F, 20.0F);
            GlStateManager.resetColor();
        }
    }

    public static void updateCape(EntityPlayer ent)
    {
        ent.prevChasingPosX = ent.chasingPosX;
        ent.prevChasingPosY = ent.chasingPosY;
        ent.prevChasingPosZ = ent.chasingPosZ;
        double d0 = ent.posX - ent.chasingPosX;
        double d1 = ent.posY - ent.chasingPosY;
        double d2 = ent.posZ - ent.chasingPosZ;

        if (d0 > 10.0D)
        {
            ent.chasingPosX = ent.posX;
            ent.prevChasingPosX = ent.chasingPosX;
        }

        if (d2 > 10.0D)
        {
            ent.chasingPosZ = ent.posZ;
            ent.prevChasingPosZ = ent.chasingPosZ;
        }

        if (d1 > 10.0D)
        {
            ent.chasingPosY = ent.posY;
            ent.prevChasingPosY = ent.chasingPosY;
        }

        if (d0 < -10.0D)
        {
            ent.chasingPosX = ent.posX;
            ent.prevChasingPosX = ent.chasingPosX;
        }

        if (d2 < -10.0D)
        {
            ent.chasingPosZ = ent.posZ;
            ent.prevChasingPosZ = ent.chasingPosZ;
        }

        if (d1 < -10.0D)
        {
            ent.chasingPosY = ent.posY;
            ent.prevChasingPosY = ent.chasingPosY;
        }

        ent.chasingPosX += d0 * 0.25D;
        ent.chasingPosZ += d2 * 0.25D;
        ent.chasingPosY += d1 * 0.25D;
    }
}
