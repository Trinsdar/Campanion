package com.terraformersmc.campanion;

import com.terraformersmc.campanion.client.CampanionKeybinds;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class CampanionForgeClient {

	public CampanionForgeClient() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		CampanionForgeClient.registerEvents(modEventBus);
		modEventBus.addListener((FMLClientSetupEvent event) -> CampanionForgeClient.init());
	}

	public static void registerEvents(IEventBus modEventBus) {
		modEventBus.addListener((RegisterKeyMappingsEvent event) -> CampanionClient.registerKeybindings(event::register));
		modEventBus.addListener((EntityRenderersEvent.RegisterRenderers event) -> CampanionClient.registerEntityRenderers(event::registerEntityRenderer));
		modEventBus.addListener((RegisterColorHandlersEvent.Item event) -> CampanionClient.registerItemColours(event::register));
	}

	public static void init() {
		CampanionClient.registerClientPacketHandlers();
		CampanionClient.registerModelPredicateProviders(ItemProperties::register);

		MinecraftForge.EVENT_BUS.addListener((TickEvent.ClientTickEvent event) -> {
			if (event.phase == TickEvent.Phase.END) {
				CampanionKeybinds.onClientTick();
			}
		});
	}
}
