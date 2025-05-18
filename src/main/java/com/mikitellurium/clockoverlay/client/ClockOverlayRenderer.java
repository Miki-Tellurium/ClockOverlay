package com.mikitellurium.clockoverlay.client;

import com.mikitellurium.clockoverlay.config.Configuration;
import com.mikitellurium.clockoverlay.util.ClientDataHelper;
import com.mikitellurium.clockoverlay.clock.OverlayPos;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.ColorHelper;

public class ClockOverlayRenderer {
    public static void renderOverlay(DrawContext context, float tickDelta) {
        if (ClientDataHelper.isHudHidden() || ClientDataHelper.playerIsSpectator() || ClientDataHelper.isDebugScreenOpen())
            return;

        if (shouldRender()) {
            boolean showCurrentDay = Configuration.SHOW_CURRENT_DAY.get();

            TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
            String timeText = ClientDataHelper.getTimeString();
            String dayText = ClientDataHelper.getDayString();
            int timeColor = Configuration.CLOCK_COLOR.get().getColor(tickDelta);
            int dayColor = Configuration.DAY_COLOR.get().getColor(tickDelta);
            float opacity = ((float) 255 / 100) * Configuration.CLOCK_OPACITY.get();
            int width = context.getScaledWindowWidth();
            int height = context.getScaledWindowHeight();
            int textCenter = showCurrentDay ? Math.max(textRenderer.getWidth(dayText), textRenderer.getWidth(timeText)) / 2 :
                    textRenderer.getWidth(timeText) / 2;
            int xPos = 0;
            int yPos = 0;

            if (Configuration.BIG_CLOCK.get()) {
                MatrixStack matrixStack = context.getMatrices();
                matrixStack.push();
                int scale = 16;
                matrixStack.scale(scale, scale, scale);
                width /= scale;
                height /= scale;
                xPos = width / 2 - textCenter;
                yPos = height / 2 - textRenderer.fontHeight / 2;
                context.drawTextWithShadow(textRenderer, timeText, xPos, yPos, withAlpha((int)opacity, timeColor));
                matrixStack.pop();
            } else {
                int xOffset = 4;
                int yOffset = showCurrentDay ? 20 : 10;
                OverlayPos pos = OverlayPos.getScreenPosConfig();
                switch (pos) {
                    case OVER_INVENTORY:
                        xPos = width / 2;
                        yPos = height - 64;
                        if (!showCurrentDay) yPos += 10;
                        if (ClientDataHelper.hasStatusBar()) yPos -= 14;
                        break;
                    case TOP_LEFT:
                        xPos = textCenter + xOffset;
                        yPos = 3;
                        break;
                    case TOP_RIGHT:
                        xPos = width - textCenter - xOffset;
                        yPos = 3;
                        break;
                    case BOTTOM_LEFT:
                        xPos = textCenter + xOffset;
                        yPos = height - yOffset;
                        break;
                    case BOTTOM_RIGHT:
                        xPos = width - textCenter - xOffset;
                        yPos = height - yOffset;
                        break;
                }

                boolean renderBroken = renderBrokenClock();
                if (showCurrentDay) {
                    if (!renderBroken && !(pos == OverlayPos.TOP_LEFT || pos == OverlayPos.TOP_RIGHT)) yPos += 10;
                    context.drawCenteredTextWithShadow(textRenderer, dayText, xPos, yPos, withAlpha((int)opacity, dayColor));
                    yPos += 10;
                }
                if (renderBroken) {
                    context.drawCenteredTextWithShadow(textRenderer, timeText, xPos, yPos, withAlpha((int)opacity, timeColor));
                }
            }
        }
    }

    private static boolean shouldRender() {
        return Configuration.CLOCK_HOLDING_REQUIRED.get() ? ClientDataHelper.playerIsHoldingClock() : ClientDataHelper.playerHasClockInInventory();
    }

    private static boolean renderBrokenClock() {
        return !(Configuration.HIDE_BROKEN_CLOCK.get() && ClientDataHelper.shouldClockBeBroken());
    }

    private static int withAlpha(int alpha, int rgb) {
        int r = ColorHelper.Argb.getRed(rgb);
        int g = ColorHelper.Argb.getGreen(rgb);
        int b = ColorHelper.Argb.getBlue(rgb);
        return ColorHelper.Argb.getArgb(alpha, r, g, b);
    }
}
