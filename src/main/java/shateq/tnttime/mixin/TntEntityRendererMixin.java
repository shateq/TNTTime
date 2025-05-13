package shateq.tnttime.mixin;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.TntEntityRenderer;
import net.minecraft.client.render.entity.state.TntEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityAttachmentType;
import net.minecraft.entity.TntEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shateq.tnttime.fabric.TntTimeMod;

@Mixin(TntEntityRenderer.class)
public abstract class TntEntityRendererMixin extends EntityRenderer<TntEntity, TntEntityRenderState> {
    protected TntEntityRendererMixin(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Inject(
        method = "render(Lnet/minecraft/client/render/entity/state/TntEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/render/entity/EntityRenderer;render(Lnet/minecraft/client/render/entity/state/EntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
            shift = At.Shift.BEFORE
        )
    )
    private void renderTntLabel(TntEntityRenderState tntEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        super.renderLabelIfPresent(tntEntityRenderState, TntTimeMod.getTime(tntEntityRenderState.fuse), matrixStack, vertexConsumerProvider, i);
    }

    @Inject(
        method = "updateRenderState(Lnet/minecraft/entity/TntEntity;Lnet/minecraft/client/render/entity/state/TntEntityRenderState;F)V",
        at = @At(value = "TAIL")
    )
    private void addTntLabelPosition(TntEntity tntEntity, TntEntityRenderState tntEntityRenderState, float f, CallbackInfo ci) {
        tntEntityRenderState.nameLabelPos = tntEntity.getAttachments().getPointNullable(EntityAttachmentType.NAME_TAG, 0, tntEntity.getLerpedYaw(f));
    }
}
