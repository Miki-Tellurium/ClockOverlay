package com.mikitellurium.clockoverlay;

import com.mikitellurium.telluriumforge.registry.SimpleRegistrationHelper;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClockOverlay implements ModInitializer {

	private static final String MOD_ID = "clockoverlay";
    private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	private static final SimpleRegistrationHelper registrationHelper = new SimpleRegistrationHelper(MOD_ID);

	@Override
	public void onInitialize() {
	}

	public static String modId() {
		return MOD_ID;
	}

	public static Logger logger() {
		return LOGGER;
	}

	public static Identifier getModIdentifier(String id) {
		return registrationHelper.getIdentifier(id);
	}

}