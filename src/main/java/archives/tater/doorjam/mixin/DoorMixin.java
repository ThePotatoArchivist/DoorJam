package archives.tater.doorjam.mixin;

import net.minecraft.block.*;
import net.minecraft.block.Oxidizable.OxidationLevel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(OxidizableDoorBlock.class)
public abstract class DoorMixin extends DoorBlock {

	public DoorMixin(BlockSetType type, Settings settings) {
		super(type, settings);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		OxidizableDoorBlock door = (OxidizableDoorBlock) state.getBlock();
		OxidationLevel oxidationLevel = door.getDegradationLevel();
		boolean oxidized = oxidationLevel != OxidationLevel.UNAFFECTED;

		if (oxidized) {
			if (world.isClient) return ActionResult.success(true);

			if (
					oxidationLevel == OxidationLevel.OXIDIZED ||
					// Exposed door has a 25% chance of jamming
					(oxidationLevel == OxidationLevel.EXPOSED && world.random.nextFloat() < 0.25) ||
					// Weathered door has a 50% chance of jamming
					(oxidationLevel == OxidationLevel.WEATHERED && world.random.nextFloat() < 0.5)
			) {
				world.playSound(player, pos, SoundEvents.BLOCK_COPPER_DOOR_CLOSE, SoundCategory.BLOCKS, 0.9f, 0.4f);
				return ActionResult.SUCCESS;
			}
		}

		return super.onUse(state, world, pos, player, hand, hit);
	}
}
