package dev.xkmc.tconstruct_emergence.init;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.providers.ProviderType;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(TConstructEmergence.MODID)
@Mod.EventBusSubscriber(modid = TConstructEmergence.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TConstructEmergence {

	public static final String MODID = "tconstruct_emergence";
	public static final Registrate REGISTRATE = Registrate.create(MODID);

	public TConstructEmergence() {
		TCEModConfig.init();
	}

	@SubscribeEvent
	public static void onCommonSetup(FMLCommonSetupEvent event) {

	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void onGatherData(GatherDataEvent event) {
		REGISTRATE.addDataGenerator(ProviderType.ENTITY_TAGS, TCETagGen::genEntityTag);
	}

}
