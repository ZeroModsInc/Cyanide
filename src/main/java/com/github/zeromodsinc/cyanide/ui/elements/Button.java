package com.github.zeromodsinc.cyanide.ui.elements;

import com.github.zeromodsinc.cyanide.ui.core.DrawList;
import com.github.zeromodsinc.cyanide.ui.core.Drawable;
import com.github.zeromodsinc.cyanide.ui.core.Vector2;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Button extends Drawable {
    public interface DrawListCallback {
        DrawList get(String text, float x, float y, float width, float height, boolean selected, boolean keyboardMode);
    }

    private float x;
    private float y;
    private float width;
    private float height;
    private DrawListCallback list;
    private String text;
    private Runnable onPress;

    @Getter
    private boolean hovered;
    @Getter
    private boolean hoveredFromKeyboard;

    public static Button create(String text, DrawListCallback list) {
        return new Button(-1f, -1f, 140f, 24f, list, text, () -> {},
                false, false);
    }

    public void render() {
        this.list.get(this.text, this.x, this.y, this.width, this.height, this.hovered, this.hoveredFromKeyboard)
                .render();
    }

    public Vector2<Float> size() {
        return new Vector2<>(this.width, this.height);
    }

    public Button update(float mouseX, float mouseY, boolean hoveredFromKeyboard) {
        this.hovered = mouseX >= this.x
                && mouseX <= this.x + this.width
                && mouseY >= this.y
                && mouseY <= this.y + this.height;
        this.hoveredFromKeyboard = hoveredFromKeyboard;
        return this;
    }

    public Button setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Button setSize(float width, float height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public Button setDimensions(float x, float y, float width, float height) {
        return this.setPosition(x, y).setSize(width, height);
    }

    public Button setText(String text) {
        this.text = text;
        return this;
    }

    public Button setOnPress(Runnable onPress) {
        this.onPress = onPress;
        return this;
    }

    public void onPress() {
        this.onPress.run();
    }
}
