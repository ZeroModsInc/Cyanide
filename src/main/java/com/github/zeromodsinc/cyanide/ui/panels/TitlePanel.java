package com.github.zeromodsinc.cyanide.ui.panels;

import com.github.zeromodsinc.cyanide.ui.core.Panel;
import com.github.zeromodsinc.cyanide.ui.core.drawables.Text;
import com.github.zeromodsinc.cyanide.ui.elements.Button;
import com.github.zeromodsinc.cyanide.ui.elements.DrawLists;
import com.github.zeromodsinc.cyanide.ui.playerview.MainMenuRenderTicker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.GuiModList;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TitlePanel extends Panel {
    private static final String COPYRIGHT_STRING = "Copyright Mojang AB";
    private static final String VERSION_STRING = "Cyanide v2-dev\n" +
            "Minecraft 1.8.9";
    private static final ResourceLocation LOGO = new ResourceLocation("previewapp:textures/ui/title.png");
    private static final MainMenuRenderTicker playerView = new MainMenuRenderTicker();

    private static int logoTexWidth = -1;
    private static int logoTexHeight = -1;

    private final Button btnPlay = Button.create("Play", DrawLists::regularButtonList);
    private final Button btnSettings = Button.create("Settings", DrawLists::regularButtonList);
    private final Button btnMods = Button.create("Mods", DrawLists::regularButtonList);

    public TitlePanel() {
        super("Main", null, false);
    }

    public void init() {
        this.renderHeader = false;

        if (logoTexWidth == -1 || logoTexHeight == -1) {
            try {
                BufferedImage image = ImageIO.read(this.mc.getResourceManager().getResource(LOGO).getInputStream());
                logoTexWidth = image.getWidth();
                logoTexHeight = image.getHeight();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        this.add(this.btnPlay.setOnPress(() -> {
            this.transfer(new GuiMultiplayer(this));
        }).setSize(148f, 30f)
                .setPosition(this.midWidthRounded() - this.btnPlay.size().getX() / 2f,
                        this.midHeightRounded() + 20f));

        this.add(this.btnSettings.setOnPress(() -> {
            this.transfer(new GuiOptions(this, this.mc.gameSettings));
        }).setSize(148f, 30f)
                .setPosition(this.midWidthRounded() - this.btnSettings.size().getX() / 2f,
                        this.midHeightRounded() + 52f));

        this.add(this.btnMods.setOnPress(() -> {
                    this.transfer(new GuiModList(this));
                }).setSize(148f, 30f)
                .setPosition(this.midWidthRounded() - this.btnMods.size().getX() / 2f,
                        this.midHeightRounded() + 84f));
    }

    public void render(int mouseX, int mouseY, float delta) {
        float logoWidth = this.width / 2.5f;
        float logoHeight = (logoWidth / (float) logoTexWidth) * (float) logoTexHeight;
        float logoX = this.width / 2f - logoWidth / 2f;
        float logoY = 55f;

        this.texture(LOGO, logoX, logoY, logoX + logoWidth, logoY + logoHeight);

        Text copyrightText = Text.create(Text.FontType.DEFAULT)
                .setText(COPYRIGHT_STRING)
                .setColor(Color.WHITE)
                .setShadow(true);

        Text versionText = Text.create(Text.FontType.DEFAULT)
                .setText(VERSION_STRING)
                .setColor(Color.WHITE)
                .setAnchor(Text.TextAnchor.RIGHT)
                .setShadow(true);

        float textY = this.height - 5f;
        copyrightText.setPosition(5f, textY - copyrightText.size().getY()).render();
        versionText.setPosition(this.width - 5f, textY - versionText.size().getY()).render();

        playerView.onTick();
    }
}
