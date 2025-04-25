package dev.xkmc.tconstruct_emergence.init;

import com.tterrag.registrate.providers.RegistrateItemTagsProvider;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import slimeknights.tconstruct.tools.TinkerTools;

public class TCETagGen {

	public static final TagKey<EntityType<?>> VALID_MELEE_TARGET = tag("valid_melee_mobs");
	public static final TagKey<EntityType<?>> VALID_BOW_TARGET = tag("valid_bow_mobs");
	public static final TagKey<EntityType<?>> VALID_CROSSBOW_TARGET = tag("valid_crossbow_mobs");

	public static final TagKey<Item> BASIC_MELEE_WEAPONS = item("basic_melee_weapons");
	public static final TagKey<Item> ADVANCED_MELEE_WEAPONS = item("advanced_melee_weapons");

	public static void itemTags(RegistrateItemTagsProvider pvd) {
		pvd.addTag(BASIC_MELEE_WEAPONS).add(
				TinkerTools.handAxe.get(),
				TinkerTools.sword.get(),
				TinkerTools.dagger.get(),
				TinkerTools.kama.get(),
				TinkerTools.mattock.get(),
				TinkerTools.pickadze.get(),
				TinkerTools.pickaxe.get()
		);

		pvd.addTag(ADVANCED_MELEE_WEAPONS).add(
				TinkerTools.broadAxe.get(),
				TinkerTools.scythe.get(),
				TinkerTools.excavator.get(),
				TinkerTools.cleaver.get(),
				TinkerTools.sledgeHammer.get()
		);
	}

	public static void genEntityTag(RegistrateTagsProvider.IntrinsicImpl<EntityType<?>> pvd) {
		pvd.addTag(VALID_MELEE_TARGET)
				.add(EntityType.ZOMBIE, EntityType.DROWNED, EntityType.HUSK,
						EntityType.VINDICATOR, EntityType.ZOMBIFIED_PIGLIN, EntityType.WITHER_SKELETON
				);
		pvd.addTag(VALID_BOW_TARGET).add(
				EntityType.SKELETON, EntityType.STRAY, EntityType.WITHER_SKELETON
		);
		pvd.addTag(VALID_CROSSBOW_TARGET).add(
				EntityType.ZOMBIFIED_PIGLIN, EntityType.PILLAGER
		);
	}


	private static TagKey<Item> item(String id) {
		return ItemTags.create(new ResourceLocation(TConstructEmergence.MODID, id));
	}

	private static TagKey<EntityType<?>> tag(String id) {
		return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(TConstructEmergence.MODID, id));
	}

}
