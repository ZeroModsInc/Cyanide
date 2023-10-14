package com.github.zeromodsinc.cyanide.ui.panorama;

import com.github.zeromodsinc.cyanide.ui.render.CyanideGL;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class PanoramaRenderer {
    public static final PanoramaRenderer INSTANCE = new PanoramaRenderer("previewapp:textures/ui/panorama");

    private final Minecraft minecraft;
    private final CubeMap cubeMap;
    private float time = 0f;

    public PanoramaRenderer(CubeMap cubeMap) {
        this.cubeMap = cubeMap;
        this.minecraft = Minecraft.getMinecraft();
    }

    public PanoramaRenderer(ResourceLocation paths) {
        this(new CubeMap(paths));
    }

    public PanoramaRenderer(String location) {
        this(new ResourceLocation(location));
    }

    public void render(float delta, float alpha) {
        float n = (float) Math.sin(this.time * 0.001f);

        this.time += delta;
        this.cubeMap.render(this.minecraft, n * 5.0f + 25.0f, -this.time * 0.1f, alpha);
        CyanideGL.setupGuiState();
    }
}