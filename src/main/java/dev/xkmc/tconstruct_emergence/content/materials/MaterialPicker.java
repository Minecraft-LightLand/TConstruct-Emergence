package dev.xkmc.tconstruct_emergence.content.materials;

import dev.xkmc.mob_weapon_api.example.vanilla.VanillaMobManager;
import dev.xkmc.tconstruct_emergence.init.TCEModConfig;
import dev.xkmc.tconstruct_emergence.init.TCETagGen;
import dev.xkmc.tconstruct_emergence.init.TConstructEmergence;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import slimeknights.tconstruct.library.materials.MaterialRegistry;
import slimeknights.tconstruct.library.materials.definition.IMaterial;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.definition.module.ToolHooks;
import slimeknights.tconstruct.library.tools.nbt.MaterialNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.ArrayList;
import java.util.List;

public class MaterialPicker {

	public static void tryAssignRangedWeapon(PathfinderMob mob, Item tool, int bonusTier) {
		ItemStack stack = tool.getDefaultInstance();
		int maxTier = TCEModConfig.COMMON.maxMaterialTier.get() + bonusTier;
		double drop = TCEModConfig.COMMON.dropChanceOverride.get();
		if (MaterialPicker.randomizeMaterial(stack, mob.getRandom(), maxTier)) {
			if (VanillaMobManager.attachGoal(mob, stack)) {
				mob.addTag(TConstructEmergence.MODID + "_applied");
				mob.setItemInHand(InteractionHand.MAIN_HAND, stack);
				mob.setDropChance(EquipmentSlot.MAINHAND, (float) drop);
			}
		}
	}

	public static void tryAssignMeleeWeapon(PathfinderMob mob, int bonusTier, boolean allowAdvanced) {
		var reg = ForgeRegistries.ITEMS.tags();
		if (reg == null) return;
		var basic = reg.getTag(TCETagGen.BASIC_MELEE_WEAPONS).getRandomElement(mob.getRandom());
		var advanced = reg.getTag(TCETagGen.ADVANCED_MELEE_WEAPONS).getRandomElement(mob.getRandom());
		var tool = !allowAdvanced ? basic.orElse(null) :
				basic.isEmpty() ? advanced.orElse(null) :
				advanced.filter(item -> mob.getRandom().nextBoolean()).orElseGet(basic::get);
		if (tool == null) return;
		ItemStack stack = tool.getDefaultInstance();
		int maxTier = TCEModConfig.COMMON.maxMaterialTier.get() + bonusTier;
		double drop = TCEModConfig.COMMON.dropChanceOverride.get();
		if (MaterialPicker.randomizeMaterial(stack, mob.getRandom(), maxTier)) {
			mob.setItemInHand(InteractionHand.MAIN_HAND, stack);
			mob.setDropChance(EquipmentSlot.MAINHAND, (float) drop);
		}
	}

	public static boolean randomizeMaterial(ItemStack stack, RandomSource random, int maxTier) {
		ToolStack tool = ToolStack.from(stack);
		ToolDefinition definition = tool.getDefinition();
		var parts = definition.getHook(ToolHooks.TOOL_PARTS).getParts(definition);
		var reg = MaterialRegistry.getInstance();
		MaterialNBT.Builder builder = MaterialNBT.builder();
		for (var part : parts) {
			var stat = part.getStatType();
			List<IMaterial> availableMats = new ArrayList<>();
			for (var mat : reg.getAllMaterials()) {
				var matId = mat.getIdentifier();
				if (mat.getTier() > maxTier) continue;
				var matStatOpt = reg.getMaterialStats(matId, stat);
				if (matStatOpt.isEmpty()) continue;
				availableMats.add(mat);
			}
			if (availableMats.isEmpty()) return false;
			builder.add(availableMats.get(random.nextInt(availableMats.size())));
		}
		tool.setMaterials(builder.build());
		return true;
	}

}
