package com.github.zeromodsinc.cyanide.ui.core;

import java.util.HashMap;
import java.util.Map;

public class DrawList {
    private final Map<String, Drawable> drawables = new HashMap<>();

    public DrawList add(String id, Drawable drawable) {
        this.drawables.put(id, drawable);
        return this;
    }

    public Drawable get(String id) {
        return this.drawables.get(id);
    }

    public void render() {
        this.drawables.forEach((id, drawable) -> {
            drawable.render();
        });
    }
}
