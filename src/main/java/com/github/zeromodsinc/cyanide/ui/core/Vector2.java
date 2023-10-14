package com.github.zeromodsinc.cyanide.ui.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Vector2<T> {
    private T x;
    private T y;

    public Vector2<T> setX(T x) {
        this.x = x;
        return this;
    }

    public Vector2<T> setY(T y) {
        this.y = y;
        return this;
    }
}
