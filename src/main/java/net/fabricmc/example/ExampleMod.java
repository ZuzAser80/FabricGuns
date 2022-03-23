package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.example.entity.basic.BasicRegistry;
import net.fabricmc.example.events.GunUseCallback;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExampleMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LogManager.getLogger("FBG");
	@Override
	public void onInitialize() {

		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		//Item FirstAidPack = new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(0).alwaysEdible().snack().statusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 3, 1), 100).build()));
		//Registry.register(Registry.ITEM, new Identifier("fbg", "firstaidpack"), FirstAidPack);
		BasicRegistry.registry();
		GunUseCallback.registry();
		//LOGGER.info("Hello Fabric world!");
	}
}
