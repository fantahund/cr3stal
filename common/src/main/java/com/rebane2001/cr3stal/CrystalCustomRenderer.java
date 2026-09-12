package com.rebane2001.cr3stal;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class CrystalCustomRenderer implements SubmitNodeCollector.CustomGeometryRenderer {
    private final RubicsCubeRenderer renderer;
    private final ModelPart cube;
    private final int lightCoords;

    public CrystalCustomRenderer(RubicsCubeRenderer renderer, ModelPart cube, int lightCoords) {
        this.renderer = renderer;
        this.cube = cube;
        this.lightCoords = lightCoords;
    }

    @Override
    public void render(PoseStack.Pose pose, VertexConsumer vertexConsumer) {
        cube.skipDraw = false;
        PoseStack tempStack = new PoseStack();
        tempStack.last().set(pose);
        renderer.render(cube, tempStack, vertexConsumer, lightCoords, OverlayTexture.NO_OVERLAY);
        cube.skipDraw = true;
    }
}
