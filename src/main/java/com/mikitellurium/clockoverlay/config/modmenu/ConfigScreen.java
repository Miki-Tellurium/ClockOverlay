package com.mikitellurium.clockoverlay.config.modmenu;

import com.mikitellurium.clockoverlay.config.Configuration;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.OptionListWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

import java.util.Arrays;

public class ConfigScreen extends GameOptionsScreen {

    Screen parent;
    private OptionListWidget list;

    public ConfigScreen(Screen parent) {
        super(parent, MinecraftClient.getInstance().options, Text.literal("Clock Overlay"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        this.list = new OptionListWidget(this.client, this.width, this.height - 64, 32, 25);
        Arrays.stream(ModOptions.getOptions()).forEach(this.list::addSingleOptionEntry);

        this.addSelectableChild(this.list);
        this.addDrawableChild(
                ButtonWidget.builder(ScreenTexts.DONE, (button) -> {Configuration.CLIENT_CONFIG.save();
                            this.client.setScreen(this.parent);
                        }).position(this.width / 2 - 100, this.height - 27)
                        .size(200, 20)
                        .build());
    }

    @Override
    public void removed() {
        Configuration.CLIENT_CONFIG.save();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        this.list.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 5, 0xffffff);
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackgroundTexture(context);
    }

}
