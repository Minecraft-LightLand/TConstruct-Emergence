package dev.xkmc.tconstruct_emergence.event;

import dev.xkmc.mob_weapon_api.example.vanilla.VanillaMobManager;
import dev.xkmc.tconstruct_emergence.content.materials.MaterialPicker;
import dev.xkmc.tconstruct_emergence.init.TCEModConfig;
import dev.xkmc.tconstruct_emergence.init.TCETagGen;
import dev.xkmc.tconstruct_emergence.init.TConstructEmergence;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import slimeknights.tconstruct.tools.TinkerTools;

@Mod.EventBusSubscriber(modid = TConstructEmergence.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MobSpawnEventHandler {

	@SubscribeEvent
	public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
		var e = event.getEntity();
		if (!(e instanceof PathfinderMob mob)) return;
		if (mob.getPersistentData().getBoolean("apoth.boss")) return;
		if (e.getTags().contains(TConstructEmergence.MODID + "_checked")) {
			if (e.getTags().contains(TConstructEmergence.MODID + "_applied")) {
				ItemStack stack = mob.getMainHandItem();
				VanillaMobManager.attachGoal(mob, stack);
			}
			return;
		}
		e.addTag(TConstructEmergence.MODID + "_checked");
		if (mob.getRandom().nextDouble() > TCEModConfig.COMMON.weaponChance.get()) return;
		ItemStack old = mob.getMainHandItem();
		if (e.getType().is(TCETagGen.VALID_CROSSBOW_TARGET)) {
			if (old.isEmpty() || old.is(Items.CROSSBOW)) {
				MaterialPicker.tryAssignRangedWeapon(mob, TinkerTools.crossbow.get());
			}
		}
		if (e.getType().is(TCETagGen.VALID_BOW_TARGET)) {
			if (old.isEmpty() || old.is(Items.BOW)) {
				MaterialPicker.tryAssignRangedWeapon(mob, TinkerTools.longbow.get());
			}
		}
	}


}
