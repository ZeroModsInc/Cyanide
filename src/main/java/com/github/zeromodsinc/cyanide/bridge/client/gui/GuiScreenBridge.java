package com.github.zeromodsinc.cyanide.bridge.client.gui;

import com.github.zeromodsinc.cyanide.ui.elements.Button;

import java.util.List;

public interface GuiScreenBridge {
    static GuiScreenBridge from(Object instance) {
        return (GuiScreenBridge) instance;
    }

    List<Button> bridge$getCyanideButtonList();
}
