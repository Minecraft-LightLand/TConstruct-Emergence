package dev.xkmc.tconstruct_emergence.init;

import com.tterrag.registrate.providers.RegistrateAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import net.minecraft.resources.ResourceLocation;

public class TCAdv {

	public static final ResourceLocation t1 = loc("bonus_tier_1");
	public static final ResourceLocation t2 = loc("bonus_tier_2");
	public static final ResourceLocation t3 = loc("bonus_tier_3");
	public static final ResourceLocation melee = loc("advanced_melee_weapons");

	public static void genAdvancements(RegistrateAdvancementProvider pvd) {
		add(pvd, t1);
		add(pvd, t2);
		add(pvd, t3);
		add(pvd, melee);
	}

	private static void add(RegistrateAdvancementProvider pvd, ResourceLocation id) {
		pvd.accept(Advancement.Builder.advancement().addCriterion("empty", new ImpossibleTrigger.TriggerInstance()).build(id));
	}


	public static ResourceLocation loc(String str) {
		return new ResourceLocation(TConstructEmergence.MODID, str);
	}
}
