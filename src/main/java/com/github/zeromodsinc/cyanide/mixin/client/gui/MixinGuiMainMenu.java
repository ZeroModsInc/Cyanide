package com.github.zeromodsinc.cyanide.mixin.client.gui;

import com.github.zeromodsinc.cyanide.bridge.client.gui.GuiScreenBridge;
import com.github.zeromodsinc.cyanide.ui.elements.Button;
import com.github.zeromodsinc.cyanide.ui.elements.DrawLists;
import com.github.zeromodsinc.cyanide.ui.elements.PanelHeader;
import com.github.zeromodsinc.cyanide.ui.panels.TitlePanel;
import com.github.zeromodsinc.cyanide.ui.panorama.PanoramaRenderer;
import com.github.zeromodsinc.cyanide.utility.Logger;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMainMenu.class)
public class MixinGuiMainMenu extends GuiScreen {
    @Unique
    private Button cyanide$btnNewTitleScreen;

    @Unique
    private PanelHeader cyanide$panelHeader;

    @Inject(method = "initGui", at = @At("RETURN"))
    public void onInitGui(CallbackInfo ci) {
        if (this.cyanide$btnNewTitleScreen == null) {
            this.cyanide$btnNewTitleScreen = Button.create("New title screen", DrawLists::regularButtonList);
        }

        this.cyanide$btnNewTitleScreen.setPosition(3f, 33f).setOnPress(() -> {
            this.mc.displayGuiScreen(new TitlePanel());
        });

        GuiScreenBridge.from(this).bridge$getCyanideButtonList().add(this.cyanide$btnNewTitleScreen);
    }

    @Inject(method = "drawScreen", at = @At("RETURN"))
    public void onDrawScreen(CallbackInfo ci) {
        if (this.cyanide$panelHeader == null) {
            this.cyanide$panelHeader = PanelHeader.create("Main");
        }

//        this.cyanide$panelHeader.render();
    }

    /**
     * @author iAmSpace
     * @reason New style panorama
     */
    @Overwrite
    private void renderSkybox(int mouseX, int mouseY, float partialTicks) {
        PanoramaRenderer.INSTANCE.render(partialTicks / 4f, 1f);
    }

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiMainMenu;drawGradientRect(IIIIII)V"))
    public void redirect$drawGradientRect(GuiMainMenu _this, int left, int top, int right, int bottom,
                                          int startColor, int endColor) {
    }
}
