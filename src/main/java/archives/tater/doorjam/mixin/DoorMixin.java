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
import org.spongepowered.asm.mixin.injection.ModifyArg;

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
				world.playSound(null, pos, this.getBlockSetType().doorClose(), SoundCategory.BLOCKS, 0.9f, 0.4f);
				return ActionResult.SUCCESS;
			}
		}

		// Mostly copied from DoorBlock
		if (!this.getBlockSetType().canOpenByHand()) {
			return ActionResult.PASS;
		} else {
			state = state.cycle(OPEN);
			world.setBlockState(pos, state, 10);
			((DoorBlockInvoker) this).invokePlayOpenCloseSound(oxidized ? null : player, world, pos, state.get(OPEN));
			world.emitGameEvent(player, this.isOpen(state) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
			return ActionResult.success(world.isClient);
		}
	}
}
