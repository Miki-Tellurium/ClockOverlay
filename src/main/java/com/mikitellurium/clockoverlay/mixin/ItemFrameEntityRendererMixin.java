package com.mikitellurium.clockoverlay.mixin;

import com.mikitellurium.clockoverlay.config.Configuration;
import com.mikitellurium.clockoverlay.util.ClientDataHelper;
import com.mikitellurium.clockoverlay.util.ClockColor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.ItemFrameEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemFrameEntityRenderer.class)
public class ItemFrameEntityRendererMixin {

    @Inject(method = "render(Lnet/minecraft/entity/decoration/ItemFrameEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/ItemFrameEntityRenderer;getLight(Lnet/minecraft/entity/decoration/ItemFrameEntity;II)I",
                    shift = At.Shift.AFTER, ordinal = 1))
    private void renderClockTimeLabel(ItemFrameEntity itemFrame, float yaw, float deltaTick, MatrixStack matrixStack,
                                      VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        if (itemFrame.getHeldItemStack().isOf(Items.CLOCK)) {
            renderTimeLabel(itemFrame, matrixStack, vertexConsumers, MinecraftClient.getInstance().getEntityRenderDispatcher(),
                    MinecraftClient.getInstance().textRenderer, light, deltaTick);
        }
    }

    @Unique
    private void renderTimeLabel(ItemFrameEntity itemFrame, MatrixStack matrixStack, VertexConsumerProvider vertexConsumers,
                                 EntityRenderDispatcher entityRenderer, TextRenderer textRenderer, int light, float deltaTick) {
        double distance = entityRenderer.getSquaredDistanceToCamera(itemFrame);
        if (distance > 4096.0F || !Configuration.SHOW_ITEM_FRAME_CLOCK.getValue()) {
            return;
        }
        float yPos = itemFrame.getNameLabelHeight() - 0.2F;
        if (itemFrame.getHeldItemStack().hasCustomName()) yPos += 0.3F;
        Vec3d cameraPos = entityRenderer.camera.getPos();
        Vec3d itemFramePos = itemFrame.getPos().add(0.0F, yPos, 0.0F);
        BlockHitResult hitResult = MinecraftClient.getInstance().world.raycast(
                new RaycastContext(cameraPos, itemFramePos, RaycastContext.ShapeType.VISUAL, RaycastContext.FluidHandling.NONE, itemFrame));
        if (hitResult.getType() == HitResult.Type.BLOCK && distance > 512.0F) {
            return; // Don't render if a block is obstructing the view from too far
        }

        String text = ClientDataHelper.getTimeString();
        ClockColor clockColor = Configuration.ITEM_FRAME_CLOCK_COLOR.getValue();
        long offset = clockColor == ClockColor.RAINBOW ? -itemFrame.getUuid().getLeastSignificantBits() % 1000 : 0;
        int textColor = clockColor.getColor(offset);
        boolean isSneaky = !itemFrame.isSneaky();
        float xPos = (float)-textRenderer.getWidth(text) / 2;
        matrixStack.push();

        matrixStack.translate(0.0F, yPos, -0.25F);
        matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180.0F));
        matrixStack.scale(0.025F, 0.025F, 0.025F);

        Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
        float opacity = MinecraftClient.getInstance().options.getTextBackgroundOpacity(0.25f);
        int finalOpacity = (int)(opacity * 255.0f) << 24;

        textRenderer.draw(text, xPos, yPos, textColor, false, matrix4f, vertexConsumers,
                isSneaky ? TextRenderer.TextLayerType.SEE_THROUGH : TextRenderer.TextLayerType.NORMAL, finalOpacity, light);
        if (isSneaky) {
            textRenderer.draw(text, xPos, yPos, textColor, false, matrix4f, vertexConsumers,
                    TextRenderer.TextLayerType.NORMAL, 0, light);
        }

        matrixStack.pop();
    }

}
