package com.github.zeromodsinc.cyanide.ui.elements;

import com.github.zeromodsinc.cyanide.ui.core.Drawable;
import com.github.zeromodsinc.cyanide.ui.core.Vector2;
import com.github.zeromodsinc.cyanide.utility.Utility;
import lombok.AllArgsConstructor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.Color;

@AllArgsConstructor
public class PanelHeader extends Drawable {
    private String title;

    private static final float HEIGHT = 25f;
    private static final Color BACKGROUND_MAIN = new Color(230, 232, 235, 255);
    private static final Color BACKGROUND_HIGHLIGHT = new Color(235, 237, 239, 255);
    private static final Color BACKGROUND_HIGHLIGHT_LOWER = new Color(177, 178, 181, 255);
    private static final Color BACKGROUND_SHADOW = new Color(0, 0, 0, 100);
    private static final Color FOREGROUND = new Color(0, 0, 0, 255);

    public static PanelHeader create(String title) {
        return new PanelHeader(title);
    }

    public void render() {
        Vector2<Float> size = this.size();
        DrawLists.panelHeaderList(this.title, 0f, 0f, size.getX(), size.getY(), BACKGROUND_MAIN,
                BACKGROUND_HIGHLIGHT, BACKGROUND_HIGHLIGHT_LOWER, BACKGROUND_SHADOW, FOREGROUND).render();
    }

    public Vector2<Float> size() {
        return new Vector2<>((float) Utility.res().getScaledWidth(), HEIGHT);
    }

    public PanelHeader setTitle(String title) {
        this.title = title;
        return this;
    }
}
