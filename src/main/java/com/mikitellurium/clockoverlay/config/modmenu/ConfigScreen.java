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

    public ConfigScreen(Screen parent) {
        super(parent, MinecraftClient.getInstance().options, Text.literal("Clock Overlay"));
        this.parent = parent;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void addOptions() {
        ModOptions.getOptions().forEach(this.body::addSingleOptionEntry);
    }

    @Override
    public void removed() {
        Configuration.CLIENT_CONFIG.save();
    }

}
