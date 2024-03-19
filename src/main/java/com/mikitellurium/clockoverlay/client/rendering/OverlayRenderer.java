package com.mikitellurium.clockoverlay.client.rendering;

import com.mikitellurium.clockoverlay.config.Configuration;
import com.mikitellurium.clockoverlay.util.ClientDataHelper;
import com.mikitellurium.clockoverlay.clock.OverlayPos;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

public class OverlayRenderer {

    public static void renderOverlay(DrawContext context, float deltaTick) {
        if (ClientDataHelper.isHudHidden())
            return;
        OverlayPos pos = OverlayPos.getScreenPosConfig();
        if (ClientDataHelper.isDebugScreenOpen() && (pos == OverlayPos.TOP_LEFT || pos == OverlayPos.TOP_RIGHT))
            return;

        if (shouldShowOverlay()) {
            TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
            String text = ClientDataHelper.getTimeString();
            int textColor = Configuration.CLOCK_COLOR.getValue().getColor(deltaTick);
            int width = context.getScaledWindowWidth();
            int height = context.getScaledWindowHeight();
            int textWidth = textRenderer.getWidth(text);
            int xPos = 0;
            int yPos = 0;

            if (Configuration.BIG_CLOCK.getValue()) {
                MatrixStack matrixStack = context.getMatrices();
                matrixStack.push();
                int scale = 16;
                matrixStack.scale(scale, scale, scale);
                width /= scale;
                height /= scale;
                xPos = width / 2 - textWidth / 2;
                yPos = height / 2 - textRenderer.fontHeight / 2;
                context.drawTextWithShadow(textRenderer, text, xPos, yPos, textColor);
                matrixStack.pop();
            } else {
                switch (pos) {
                    case OVER_INVENTORY:
                        xPos = width / 2 - textWidth / 2;
                        yPos = height - 55;
                        if (ClientDataHelper.hasStatusBar()) {
                            yPos -= 14;
                        }
                        break;
                    case TOP_LEFT:
                        xPos = 10;
                        yPos = 3;
                        break;
                    case TOP_RIGHT:
                        xPos = width - textWidth - 10;
                        yPos = 5;
                        break;
                    case BOTTOM_LEFT:
                        xPos = 10;
                        yPos = height - 10;
                        break;
                    case BOTTOM_RIGHT:
                        xPos = width - textWidth - 10;
                        yPos = height - 10;
                        break;
                }
                context.drawTextWithShadow(textRenderer, text, xPos, yPos, textColor);
            }
        }
    }

    private static boolean shouldShowOverlay() {
        if (Configuration.CLOCK_HOLDING_REQUIRED.getValue()) {
            return ClientDataHelper.playerIsHoldingClock();
        } else {
            return ClientDataHelper.playerHasClockInInventory();
        }
    }

}
