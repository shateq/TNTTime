package shateq.tnttime.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.TntEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.TntEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shateq.tnttime.fabric.TntTimeMod;

@Mixin(TntEntityRenderer.class)
public abstract class TntEntityRendererMixin extends EntityRenderer<TntEntity> {

    protected TntEntityRendererMixin(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Inject(method = "render", at = @At(value = "TAIL"))
    public void render(TntEntity tntEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        renderLabel(tntEntity, matrixStack, vertexConsumerProvider, i);
    }

    protected void renderLabel(TntEntity entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        double d = this.dispatcher.getSquaredDistanceToCamera(entity);
        if (!(d > 4096.0D)) {
            Text text = TntTimeMod.getTime(entity.getFuse());

            float f = entity.getHeight() + 0.5F;

            matrices.push();
            matrices.translate(0.0D, f, 0.0D);
            matrices.multiply(this.dispatcher.getRotation());
            matrices.scale(-0.025F, -0.025F, 0.025F);
            final Matrix4f matrix4f = matrices.peek().getPositionMatrix();

            float backgroundOpacity = MinecraftClient.getInstance().options.getTextBackgroundOpacity(0.25F);
            int backgroundColor = (int) (backgroundOpacity * 255.0F) << 24;

            final TextRenderer textRenderer = this.getTextRenderer();
            float x = (float) (-textRenderer.getWidth(text) / 2);
            float y = -4;

            textRenderer.draw(text, x, y, 7, true, matrix4f, vertexConsumers, false, backgroundColor, light);
            matrices.pop();
        }
    }
}
