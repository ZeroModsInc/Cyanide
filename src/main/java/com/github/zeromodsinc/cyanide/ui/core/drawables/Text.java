package com.github.zeromodsinc.cyanide.ui.core.drawables;

import com.github.zeromodsinc.cyanide.ui.core.Drawable;
import com.github.zeromodsinc.cyanide.ui.core.Vector2;
import com.github.zeromodsinc.cyanide.ui.render.CyanideGL;
import com.github.zeromodsinc.cyanide.utility.Utility;
import lombok.AllArgsConstructor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
public class Text extends Drawable {
    public static enum FontType {
        DEFAULT,
        TEN,
        CUSTOM
    }

    public static enum TextAnchor {
        LEFT,
        CENTER,
        RIGHT
    }

    private static final FontRenderer mcFontRenderer = Minecraft.getMinecraft().fontRendererObj;

    private FontType type;
    private String text;
    private List<String> lines;
    private float x;
    private float y;
    private Color color;
    private TextAnchor anchor;
    private float wrapWidth;
    private float clipWidth;
    private float clipHeight;
    private boolean shadow;

    public void render() {
        switch (this.type) {
            case DEFAULT:
                if (this.clipWidth != -1f && this.clipHeight != -1f) {
                    float x = this.getCorrectX(this.size().getX());

                    CyanideGL.scissor(x, this.y, this.clipWidth, this.clipHeight, () ->
                            mcFontRenderer.drawString(this.text, x, this.y, this.color.getRGB(), this.shadow));
                } else {
                    float yAdd = 0f;
                    for (String line : this.lines) {
                        float x = this.getCorrectX(mcFontRenderer.getStringWidth(line));
                        mcFontRenderer.drawString(line, x, this.y + yAdd, this.color.getRGB(), this.shadow);
                        yAdd += mcFontRenderer.FONT_HEIGHT + 1f;
                    }
                }
                break;
            case TEN:
                // Not supported
            case CUSTOM:
                // Not supported
            default:
                break;
        }
    }

    public Vector2<Float> size() {
        switch (this.type) {
            case DEFAULT:
                if (this.clipWidth != -1f && this.clipHeight != -1f) {
                    return new Vector2<>(this.clipWidth, this.clipHeight);
                } else {
                    List<Integer> widths = new ArrayList<>();
                    for (String line : this.lines) {
                        widths.add(mcFontRenderer.getStringWidth(line));
                    }
                    int max = Utility.maxInList(widths);

                    return new Vector2<>((float) max,
                            (float) this.lines.size() * mcFontRenderer.FONT_HEIGHT
                                    + (float) (this.lines.size() - 1));
                }
            case TEN:
                // Not supported
            case CUSTOM:
                // Not supported
            default:
                return new Vector2<>(0f, 0f);
        }
    }

    public static Text create(FontType type) {
        return new Text(type, "", new ArrayList<>(), 0f, 0f, Color.WHITE, TextAnchor.LEFT,
                -1f, -1f, -1f, false);
    }

    private void updateLines() {
        this.lines.clear();
        String[] linesSplitByNewLine = this.text.split("\n");

        if (this.wrapWidth != -1f) {
            List<List<String>> wordsOnLines = new ArrayList<>();
            List<String> newLines = new ArrayList<>();

            for (String line : linesSplitByNewLine) {
                wordsOnLines.add(new ArrayList<>(Arrays.asList(line.split(" "))));
            }

            for (List<String> words : wordsOnLines) {
                StringBuilder currentLineContent = new StringBuilder();
                for (String word : words) {
                    if (currentLineContent.length() > 0) {
                        String currentStr = currentLineContent.toString();
                        char lastChar = currentStr.charAt(currentStr.length() - 1);

                        boolean shouldAppend = mcFontRenderer.getStringWidth(currentStr) <= this.wrapWidth
                                || lastChar == '\n';

                        if (shouldAppend) {
                            if (lastChar != '\n') {
                                currentLineContent.append(" ");
                            }
                            currentLineContent.append(word);
                        } else {
                            currentLineContent.append("\n");
                        }
                    } else {
                        currentLineContent.append(word);
                    }
                }

                newLines.addAll(Arrays.asList(currentLineContent.toString().split("\n")));
            }

            this.lines.addAll(newLines);
        } else {
            Collections.addAll(this.lines, linesSplitByNewLine);
        }
    }

    private float getCorrectX(float textWidth) {
        switch (this.anchor) {
            case LEFT:
                return this.x;
            case CENTER:
                return this.x - textWidth / 2f;
            case RIGHT:
                return this.x - textWidth;
            default:
                return 0f;
        }
    }

    public Text setType(FontType type) {
        this.type = type;
        return this;
    }

    public Text setText(String text) {
        this.text = text;
        this.updateLines();
        return this;
    }

    public Text setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Text setColor(Color color) {
        this.color = color;
        return this;
    }

    public Text setAnchor(TextAnchor anchor) {
        this.anchor = anchor;
        return this;
    }

    public Text setWrapWidth(float wrapWidth) {
        this.wrapWidth = wrapWidth;
        this.updateLines();
        return this;
    }

    public Text setClipWidth(float clipWidth) {
        this.clipWidth = clipWidth;
        return this;
    }

    public Text setClipHeight(float clipHeight) {
        this.clipHeight = clipHeight;
        return this;
    }

    public Text setShadow(boolean shadow) {
        this.shadow = shadow;
        return this;
    }
}
