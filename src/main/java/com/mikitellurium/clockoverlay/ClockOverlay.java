package com.mikitellurium.clockoverlay;

import com.mikitellurium.telluriumforge.registry.IdentifierProvider;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClockOverlay implements ModInitializer {

	private static final String MOD_ID = "clockoverlay";
    private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	private static final IdentifierProvider registrationHelper = () -> MOD_ID;

	@Override
	public void onInitialize() {
	}

	public static String modId() {
		return MOD_ID;
	}

	public static Logger logger() {
		return LOGGER;
	}

	public static Identifier getModIdentifier(String path) {
		return registrationHelper.modIdentifier(path);
	}

}