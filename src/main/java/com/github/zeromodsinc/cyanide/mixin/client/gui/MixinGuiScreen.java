package com.github.zeromodsinc.cyanide.mixin.client.gui;

import com.github.zeromodsinc.cyanide.bridge.client.gui.GuiScreenBridge;
import com.github.zeromodsinc.cyanide.ui.elements.Button;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Mixin(GuiScreen.class)
public class MixinGuiScreen implements GuiScreenBridge {
    @Unique
    private final List<Button> cyanide$cyanideButtonList = new ArrayList<>();

    public List<Button> bridge$getCyanideButtonList() {
        return this.cyanide$cyanideButtonList;
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"))
    protected void onMouseClicked(int mouseX, int mouseY, int mouseButton, CallbackInfo callbackInfo) {
        if (mouseButton == 0) {
            GuiScreenBridge.from(this).bridge$getCyanideButtonList().forEach(button -> {
                if (button.isHovered()) {
                    button.onPress();
                }
            });
        }
    }
}
