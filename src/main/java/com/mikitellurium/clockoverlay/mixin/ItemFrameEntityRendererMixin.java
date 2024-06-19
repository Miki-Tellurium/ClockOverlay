package com.mikitellurium.clockoverlay.mixin;

import com.mikitellurium.clockoverlay.config.Configuration;
import com.mikitellurium.clockoverlay.util.ClientDataHelper;
import com.mikitellurium.clockoverlay.clock.ClockColor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.ItemFrameEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAttachmentType;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.Items;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
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
        Vec3d vec3d = itemFrame.getAttachments().getPointNullable(EntityAttachmentType.NAME_TAG, 0, itemFrame.getYaw(deltaTick));
        if (vec3d == null) {
            return;
        }
        float yPos = (float)vec3d.y + 0.3F;
        if (itemFrame.getHeldItemStack().get(DataComponentTypes.CUSTOM_NAME) != null) yPos += 0.3F;

        String text = ClientDataHelper.getTimeString();
        ClockColor clockColor = Configuration.ITEM_FRAME_CLOCK_COLOR.getValue();
        int offset = clockColor == ClockColor.RAINBOW ? itemFrame.getId() : 0;
        int textColor = clockColor.getColor(offset, deltaTick);
        float xPos = (float)-textRenderer.getWidth(text) / 2;
        matrixStack.push();

        matrixStack.translate(0.0F, yPos, -0.25F);
        matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180.0F));
        matrixStack.scale(0.025F, 0.025F, 0.025F);

        Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
        float opacity = MinecraftClient.getInstance().options.getTextBackgroundOpacity(0.25f);
        int finalOpacity = (int)(opacity * 255.0f) << 24;

        textRenderer.draw(text, xPos, yPos, textColor, false, matrix4f, vertexConsumers,
                TextRenderer.TextLayerType.POLYGON_OFFSET, finalOpacity, light);

        matrixStack.pop();
    }

}
