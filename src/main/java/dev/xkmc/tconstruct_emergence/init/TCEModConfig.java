package dev.xkmc.tconstruct_emergence.init;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.IConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class TCEModConfig {

	public static class Common {

		public final ForgeConfigSpec.DoubleValue weaponChance;
		public final ForgeConfigSpec.DoubleValue dropChanceOverride;
		public final ForgeConfigSpec.IntValue maxMaterialTier;

		Common(ForgeConfigSpec.Builder builder) {
			weaponChance = builder.defineInRange("weaponChance", 0.1d, 0, 1);
			dropChanceOverride = builder.defineInRange("dropChanceOverride", 0.1, 0, 1);
			maxMaterialTier = builder.defineInRange("maxMaterialTier", 3, 0, 5);
		}

	}

	public static final ForgeConfigSpec COMMON_SPEC;
	public static final Common COMMON;

	static {

		final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = specPair.getRight();
		COMMON = specPair.getLeft();
	}

	public static void init() {
		register(ModConfig.Type.COMMON, COMMON_SPEC);
	}

	private static void register(ModConfig.Type type, IConfigSpec<?> spec) {
		var mod = ModLoadingContext.get().getActiveContainer();
		String path = mod.getModId() + "-" + type.extension() + ".toml";
		ModLoadingContext.get().registerConfig(type, spec, path);
	}

}
