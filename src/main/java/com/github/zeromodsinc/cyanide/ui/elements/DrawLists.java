package com.github.zeromodsinc.cyanide.ui.elements;

import com.github.zeromodsinc.cyanide.ui.core.DrawList;
import com.github.zeromodsinc.cyanide.ui.core.drawables.Rectangle;
import com.github.zeromodsinc.cyanide.ui.core.drawables.Text;

import java.awt.Color;

public class DrawLists {
    public static DrawList buttonList(String text, float x, float y, float width, float height,
                                      Color buttonOutlineKeyboardMode, Color buttonOutline, Color buttonMainBackground,
                                      Color buttonLowerHighlight, Color textColor) {
        DrawList list = new DrawList();

        float keyboardModeOutlineSize = 1f;
        float outlineSize = 1f;
        float highlightSize = 2f;

        Text textObject = Text.create(Text.FontType.DEFAULT)
                .setText(text)
                .setAnchor(Text.TextAnchor.CENTER)
                .setShadow(false);

        list.add("keyboard-mode-outline", Rectangle.create()
                        .setOutline(true)
                        .setOutlineThickness(keyboardModeOutlineSize)
                        .setColor(buttonOutlineKeyboardMode)
                        .setPosition(x - keyboardModeOutlineSize, y - keyboardModeOutlineSize)
                        .setSize(width + keyboardModeOutlineSize * 2f,
                                height + keyboardModeOutlineSize * 2f))
                .add("button-outline", Rectangle.create()
                        .setOutline(true)
                        .setOutlineThickness(outlineSize)
                        .setColor(buttonOutline)
                        .setDimensions(x, y, width, height))
                .add("button-main", Rectangle.create()
                        .setColor(buttonMainBackground)
                        .setPosition(x + outlineSize, y + outlineSize)
                        .setSize(width - outlineSize * 2f, height - outlineSize * 2f - highlightSize))
                .add("button-lower-highlight", Rectangle.create()
                        .setColor(buttonLowerHighlight)
                        .setPosition(x + outlineSize, y + height - outlineSize - highlightSize)
                        .setSize(width - outlineSize * 2f, highlightSize))
                .add("text", textObject
                        .setColor(textColor)
                        .setPosition(x + width / 2f, y + ((height - outlineSize * 2f) / 2f) - highlightSize - 2f));

        return list;
    }

    public static DrawList regularButtonList(String text, float x, float y, float width, float height,
                                             boolean selected, boolean keyboardMode) {
        Color buttonOutlineKeyboardMode;
        Color buttonOutline;
        Color buttonMainBackground;
        Color buttonLowerHighlight = new Color(88, 88, 90, 255);
        Color textColor = new Color(30, 30, 31, 255);

        if (keyboardMode) {
            buttonOutlineKeyboardMode = new Color(255, 255, 255, 255);
        } else {
            buttonOutlineKeyboardMode = new Color(255, 255, 255, 0);
        }

        if (selected && !keyboardMode) {
            buttonOutline = new Color(30, 30, 31, 255);
            buttonMainBackground = new Color(177, 178, 181, 255);
        } else {
            buttonOutline = new Color(30, 30, 31, 255);
            buttonMainBackground = new Color(208, 209, 212, 255);
        }

        return buttonList(text, x, y, width, height, buttonOutlineKeyboardMode, buttonOutline,
                buttonMainBackground, buttonLowerHighlight, textColor);
    }

    public static DrawList successButtonList(String text, float x, float y, float width, float height,
                                             boolean selected, boolean keyboardMode) {
        Color buttonOutlineKeyboardMode;
        Color buttonOutline;
        Color buttonMainBackground;
        Color buttonLowerHighlight = new Color(29, 77, 19, 255);
        Color textColor = new Color(255, 255, 255, 255);

        if (keyboardMode) {
            buttonOutlineKeyboardMode = new Color(255, 255, 255, 255);
        } else {
            buttonOutlineKeyboardMode = new Color(255, 255, 255, 0);
        }

        if (selected && !keyboardMode) {
            buttonOutline = new Color(30, 30, 31, 255);
            buttonMainBackground = new Color(42, 100, 28, 255);
        } else {
            buttonOutline = new Color(30, 30, 31, 255);
            buttonMainBackground = new Color(60, 133, 39, 255);
        }

        return buttonList(text, x, y, width, height, buttonOutlineKeyboardMode, buttonOutline,
                buttonMainBackground, buttonLowerHighlight, textColor);
    }

    public static DrawList panelHeaderList(String title, float x, float y, float width, float height,
                                           Color backgroundMain, Color backgroundHighlight,
                                           Color backgroundHighlightLower, Color backgroundShadow, Color foreground) {
        DrawList list = new DrawList();

        float highlightHeight = 1f;
        float highlightLowerHeight = 2f;
        float shadowHeight = 1f;
        float mainHeight = height - highlightHeight - highlightLowerHeight - shadowHeight;

        Text textObject = Text.create(Text.FontType.DEFAULT) // TODO: Change to FontType.TEN
                .setText(title)
                .setAnchor(Text.TextAnchor.CENTER)
                .setColor(foreground)
                .setShadow(false);

        list.add("background-main", Rectangle.create()
                        .setColor(backgroundMain)
                        .setDimensions(x, y, width, mainHeight))
                .add("background-highlight", Rectangle.create()
                        .setColor(backgroundHighlight)
                        .setDimensions(x, y + mainHeight, width, highlightHeight))
                .add("background-highlight-lower", Rectangle.create()
                        .setColor(backgroundHighlightLower)
                        .setPosition(x, y + mainHeight + highlightHeight)
                        .setSize(width, highlightLowerHeight))
                .add("background-shadow", Rectangle.create()
                        .setColor(backgroundShadow)
                        .setPosition(x, y + height - shadowHeight)
                        .setSize(width, shadowHeight))
                .add("0-foreground-text", textObject
                        .setPosition(x + width / 2f, y + (mainHeight / 2f) - (textObject.size().getY() / 2f)));

        return list;
    }
}
