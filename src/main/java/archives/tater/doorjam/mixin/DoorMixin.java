package archives.tater.doorjam.mixin;

import archives.tater.doorjam.DoorJam;
import archives.tater.doorjam.data.DJBlockTags;
import archives.tater.doorjam.data.DoorJamBlockTagProvider;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DoorBlock.class)
public abstract class DoorMixin {

	@Shadow protected abstract void playOpenCloseSound(@Nullable Entity entity, World world, BlockPos pos, boolean open);

	@Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
	private void doorOverride(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir){
		Random random = world.getRandom();

		/*
			If Block is in one of the block chance tags and the chance passes, then cancel the block interaction code.
		 */
		if ((DoorJam.isJammed(state, random))){
			playOpenCloseSound(player, world, pos, false);
			player.swingHand(player.getActiveHand());
			cir.setReturnValue(ActionResult.SUCCESS);

			//Cancel block's vanilla code.
			cir.cancel();
		}
	}
}
