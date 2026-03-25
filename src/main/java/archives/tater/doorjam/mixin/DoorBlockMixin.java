package archives.tater.doorjam.mixin;

import archives.tater.doorjam.DoorJam;

import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.phys.BlockHitResult;

import org.jspecify.annotations.Nullable;

@Mixin(DoorBlock.class)
public abstract class DoorBlockMixin {

	@Shadow @Final private BlockSetType type;

	@Inject(method = "useWithoutItem", at = @At("HEAD"), cancellable = true)
	private void doorOverride(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir){
		if (DoorJam.tryOpenDoor(level, player, pos, state, this.type)) return;

		player.swing(player.getUsedItemHand());
		cir.setReturnValue(InteractionResult.SUCCESS);
	}

	@ModifyArg(
			method = "useWithoutItem",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/DoorBlock;playSound(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Z)V"),
			index = 0
	)
	private @Nullable Entity playSoundToClient(@Nullable Entity entity, @Local(argsOnly = true) BlockState blockState) {
		return DoorJam.mayJam(blockState) ? null : entity;
	}
}
