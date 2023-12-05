package archives.tater.doorjam.mixin;

import net.minecraft.block.*;
import net.minecraft.block.Oxidizable.OxidationLevel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(OxidizableTrapdoorBlock.class)
public abstract class TrapdoorMixin extends TrapdoorBlock {

	public TrapdoorMixin(BlockSetType type, Settings settings) {
		super(type, settings);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		OxidizableTrapdoorBlock trapdoor = (OxidizableTrapdoorBlock) state.getBlock();
		OxidationLevel oxidationLevel = trapdoor.getDegradationLevel();
		boolean oxidized = oxidationLevel != OxidationLevel.UNAFFECTED;

		if (oxidized) {
			if (world.isClient) return ActionResult.success(true);

			if (
					oxidationLevel == OxidationLevel.OXIDIZED ||
					// Exposed trapdoor has a 25% chance of jamming
					(oxidationLevel == OxidationLevel.EXPOSED && world.getRandom().nextFloat() < 0.25) ||
					// Weathered trapdoor has a 50% chance of jamming
					(oxidationLevel == OxidationLevel.WEATHERED && world.getRandom().nextFloat() < 0.5)
			) {
				world.playSound(player, pos, SoundEvents.BLOCK_COPPER_TRAPDOOR_CLOSE, SoundCategory.BLOCKS, 0.9f, 0.4f);
				return ActionResult.SUCCESS;
			}
		}

		return super.onUse(state, world, pos, player, hand, hit);
	}
}
