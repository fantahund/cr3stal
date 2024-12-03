package com.rebane2001.cr3stal.mixin;

import com.rebane2001.cr3stal.RubicsCubeRenderer;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EndCrystalEntityRenderer;
import net.minecraft.client.render.entity.model.EndCrystalEntityModel;
import net.minecraft.client.render.entity.state.EndCrystalEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EndCrystalEntityRenderer.class)
public abstract class EndCrystalEntityRendererMixin {

    private RubicsCubeRenderer rubicsCubeRenderer;

    @Shadow
    private EndCrystalEntityModel model;
    @Shadow
    private static RenderLayer END_CRYSTAL;

    private EndCrystalEntityRenderState endCrystalEntity;

    @Inject(method = "render", at = @At(value = "HEAD"))
    public void render(EndCrystalEntityRenderState endCrystalEntity, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        this.endCrystalEntity = endCrystalEntity;
        // model.cube.visible = false;
        model.outerGlass.hidden = true;
        model.cube.hidden = true;
    }

    @Inject(method = "render", at = @At(shift = Shift.AFTER, value = "INVOKE", target = "net/minecraft/client/render/entity/model/EndCrystalEntityModel.render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;II)V"))
    public void renderCore(EndCrystalEntityRenderState endCrystalEntity, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        if (rubicsCubeRenderer == null) {
            rubicsCubeRenderer = new RubicsCubeRenderer();
        }
        matrixStack.push();
        // matrixStack.translate(0, 1f, 0);
        model.outerGlass.rotate(matrixStack);
        model.innerGlass.rotate(matrixStack);
        model.cube.rotate(matrixStack);
        matrixStack.scale(1.1f, 1.1f, 1.1f);

        model.cube.hidden = false;
        // model.outerGlass.
        rubicsCubeRenderer.render(model.cube, matrixStack, vertexConsumerProvider.getBuffer(END_CRYSTAL), i, OverlayTexture.DEFAULT_UV);

        matrixStack.pop();
    }

    /**
     * @author Brokkonaut
     * @reason fancy render ^^
     */
    @Overwrite
    public static float getYOffset(float f) {
        return -1.0f;
    }

    // @Redirect(method = "render", at = @At(value = "INVOKE", target = "net/minecraft/client/util/math/MatrixStack.translate(FFF)V", ordinal = 1))
    // public void translate(MatrixStack matrixStack, float x, float y, float z) {
    // matrixStack.translate(x, this.endCrystalEntity != null && this.endCrystalEntity.baseVisible ? 1.2f : 1f, z);
    // }
    //
    // @Redirect(method = "render", at = @At(value = "INVOKE", target = "net/minecraft/client/model/ModelPart.render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;II)V", ordinal = 1))
    // public void renderFrame(ModelPart modelPart, MatrixStack matrices, VertexConsumer vertices, int light, int overlay) {
    // }
    //
    // @Redirect(method = "render", at = @At(value = "INVOKE", target = "net/minecraft/client/model/ModelPart.render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;II)V", ordinal = 3))
    // public void renderCore(ModelPart core, MatrixStack matrices, VertexConsumer vertices, int light, int overlay) {
    // if (rubicsCubeRenderer == null) {
    // rubicsCubeRenderer = new RubicsCubeRenderer();
    // }
    //
    // rubicsCubeRenderer.render(core, matrices, vertices, light, overlay);
    // }
}
