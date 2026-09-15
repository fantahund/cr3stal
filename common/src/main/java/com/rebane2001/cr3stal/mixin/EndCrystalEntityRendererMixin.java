package com.rebane2001.cr3stal.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.rebane2001.cr3stal.RubicsCubeRenderer;
import net.minecraft.client.model.object.crystal.EndCrystalModel;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EndCrystalRenderer;
import net.minecraft.client.renderer.entity.state.EndCrystalRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EndCrystalRenderer.class)
public abstract class EndCrystalEntityRendererMixin {

    private RubicsCubeRenderer rubicsCubeRenderer;

    @Shadow
    private EndCrystalModel model;

    @Shadow
    private static Identifier END_CRYSTAL_LOCATION;

    @Inject(method = "submit(Lnet/minecraft/client/renderer/entity/state/EndCrystalRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V", at = @At("HEAD"))
    public void onsubmitHead(EndCrystalRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera, CallbackInfo ci) {
        model.outerGlass.skipDraw = true;
        model.cube.skipDraw = true;
    }

    @Inject(method = "submit(Lnet/minecraft/client/renderer/entity/state/EndCrystalRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V", 
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/resources/Identifier;III)V", shift = At.Shift.AFTER))
    public void onsubmitCore(EndCrystalRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera, CallbackInfo ci) {
        if (rubicsCubeRenderer == null) {
            rubicsCubeRenderer = new RubicsCubeRenderer();
        }
        poseStack.pushPose();
        model.outerGlass.translateAndRotate(poseStack);
        model.innerGlass.translateAndRotate(poseStack);
        model.cube.translateAndRotate(poseStack);
        poseStack.scale(1.1f, 1.1f, 1.1f);

        collector.submitCustomGeometry(poseStack, net.minecraft.client.renderer.rendertype.RenderTypes.entityCutout(END_CRYSTAL_LOCATION), new com.rebane2001.cr3stal.CrystalCustomRenderer(rubicsCubeRenderer, model.cube, state.lightCoords));

        poseStack.popPose();
    }

    /**
     * @author Brokkonaut
     * @reason fancy render ^^
     */
    @Overwrite
    public static float getY(float f) {
        return -1.0f;
    }
}
