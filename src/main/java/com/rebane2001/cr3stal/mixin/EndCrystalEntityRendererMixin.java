package com.rebane2001.cr3stal.mixin;

import com.rebane2001.cr3stal.RubicsCubeRenderer;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EndCrystalEntityRenderer;
import net.minecraft.client.render.entity.model.EndCrystalEntityModel;
import net.minecraft.client.render.entity.state.EndCrystalEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EndCrystalEntityRenderer.class)
public abstract class EndCrystalEntityRendererMixin {

    @Shadow
    @Final
    private EndCrystalEntityModel model;

    @Shadow
    @Final
    private static RenderLayer END_CRYSTAL;

    @Unique
    private RubicsCubeRenderer rubicsCubeRenderer;

    @Inject(method = "render(Lnet/minecraft/client/render/entity/state/EndCrystalEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "HEAD"))
    public void render(EndCrystalEntityRenderState endCrystalEntity, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        model.outerGlass.hidden = true;
        model.cube.hidden = true;
    }

    @Inject(method = "render(Lnet/minecraft/client/render/entity/state/EndCrystalEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(shift = Shift.AFTER, value = "INVOKE", target = "net/minecraft/client/render/entity/model/EndCrystalEntityModel.render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;II)V"))
    public void renderCore(EndCrystalEntityRenderState endCrystalEntity, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        if (rubicsCubeRenderer == null) {
            rubicsCubeRenderer = new RubicsCubeRenderer();
        }
        matrixStack.push();
        model.outerGlass.rotate(matrixStack);
        model.innerGlass.rotate(matrixStack);
        model.cube.rotate(matrixStack);
        matrixStack.scale(1.1f, 1.1f, 1.1f);

        model.cube.hidden = false;
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
}
