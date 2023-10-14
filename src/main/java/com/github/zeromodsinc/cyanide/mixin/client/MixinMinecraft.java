package com.github.zeromodsinc.cyanide.mixin.client;

import com.github.zeromodsinc.cyanide.utility.Utility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraftforge.common.BiomeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    @Shadow public WorldClient theWorld;
    @Shadow public GuiScreen currentScreen;
    @Shadow public GameSettings gameSettings;

    /**
     * @author iAmSpace
     * @reason Main menu
     */
    @Overwrite
    public int getLimitFramerate() {
        return this.theWorld == null && this.currentScreen != null ? Utility.getMonitorRefreshRate()
                : this.gameSettings.limitFramerate;
    }
}
