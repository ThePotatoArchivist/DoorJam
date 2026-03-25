package archives.tater.doorjam.mixin;

import archives.tater.doorjam.DoorJam;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.phys.BlockHitResult;

@Mixin(ChestBlock.class)
public abstract class ChestBlockMixin {

	@Inject(method = "useWithoutItem", at = @At("HEAD"), cancellable = true)
	private void doorOverride(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir){
		if (DoorJam.tryOpenDoor(level, player, pos, state, BlockSetType.COPPER)) return;

		cir.setReturnValue(InteractionResult.SUCCESS);
	}
}
