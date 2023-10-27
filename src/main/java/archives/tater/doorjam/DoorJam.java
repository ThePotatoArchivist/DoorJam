package archives.tater.doorjam;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.Oxidizable.OxidationLevel;
import net.minecraft.block.OxidizableDoorBlock;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DoorJam implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("doorjam");

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");

		UseBlockCallback.EVENT.register((player, world, hand, blockHitResult) -> {
            /* Manual spectator check is necessary because AttackBlockCallbacks
               fire before the spectator check */
			if (player.isSpectator()) return ActionResult.PASS;

			BlockPos pos = blockHitResult.getBlockPos();
			BlockState state = world.getBlockState(pos);
			Block block = state.getBlock();

			// If block is not door or door is not acacia skip
			if (!(block instanceof OxidizableDoorBlock door) || door.getDegradationLevel() == OxidationLevel.UNAFFECTED) return ActionResult.PASS;

			if (world.isClient()) return ActionResult.SUCCESS;

			// Exposed door has a 25% chance of jamming
			if (door.getDegradationLevel() == OxidationLevel.EXPOSED && Math.random() > 0.25) return ActionResult.PASS;
			// Weathered door has a 50% chance of jamming
			if (door.getDegradationLevel() == OxidationLevel.WEATHERED && Math.random() > 0.5) return ActionResult.PASS;

			world.playSound(null, pos, SoundEvents.BLOCK_COPPER_DOOR_CLOSE, SoundCategory.BLOCKS, 0.9f, 0.8f);

			return ActionResult.SUCCESS;
		});
	}
}