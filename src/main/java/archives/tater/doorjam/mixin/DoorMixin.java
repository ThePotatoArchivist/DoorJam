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
import org.spongepowered.asm.mixin.Mixin;

@Mixin(OxidizableDoorBlock.class)
public abstract class DoorMixin extends DoorBlock {

	public DoorMixin(BlockSetType type, Settings settings) {
		super(type, settings);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (!this.getBlockSetType().canOpenByHand()) {
			return ActionResult.PASS;
		}
		OxidizableDoorBlock door = (OxidizableDoorBlock) state.getBlock();

		if (world.isClient && door.getDegradationLevel() != OxidationLevel.UNAFFECTED) return ActionResult.success(true);

		if (
				door.getDegradationLevel() == OxidationLevel.OXIDIZED ||
				// Exposed door has a 25% chance of jamming
				(door.getDegradationLevel() == OxidationLevel.EXPOSED && Math.random() < 0.25) ||
				// Weathered door has a 50% chance of jamming
				(door.getDegradationLevel() == OxidationLevel.WEATHERED && Math.random() < 0.5)
		) {
			world.playSound(null, pos, SoundEvents.BLOCK_COPPER_DOOR_CLOSE, SoundCategory.BLOCKS, 0.9f, 0.4f);
			return ActionResult.SUCCESS;
		}

		// Original code
		state = state.cycle(OPEN);
		world.setBlockState(pos, state, Block.NOTIFY_LISTENERS | Block.REDRAW_ON_MAIN_THREAD);
		((DoorBlockInvoker) this).invokePlayOpenCloseSound(null, world, pos, state.get(OPEN));
		world.emitGameEvent(null, this.isOpen(state) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
		return ActionResult.success(false);
	}
}