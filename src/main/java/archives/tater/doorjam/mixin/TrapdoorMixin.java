package archives.tater.doorjam.mixin;

import archives.tater.doorjam.DoorJam;
import archives.tater.doorjam.data.DJBlockTags;
import archives.tater.doorjam.data.DoorJamBlockTagProvider;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable.OxidationLevel;
import net.minecraft.block.OxidizableTrapdoorBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
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

@Mixin(TrapdoorBlock.class)
public abstract class TrapdoorMixin {

	@Shadow protected abstract void playToggleSound(@Nullable PlayerEntity player, World world, BlockPos pos, boolean open);

	@Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
	private void doorOverride(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir){
		Random random = world.getRandom();
		if (DoorJam.isJammed(state, random)){
			playToggleSound(player, world, pos, false);
			player.swingHand(player.getActiveHand());
			cir.setReturnValue(ActionResult.SUCCESS);
			cir.cancel();
		}
	}
}
