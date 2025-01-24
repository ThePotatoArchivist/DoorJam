package archives.tater.doorjam.mixin;

import archives.tater.doorjam.DoorJam;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.WoodType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FenceGateBlock.class)
public class FenceGateBlockMixin {

    @Shadow @Final private WoodType type;

    @Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
    private void doorOverride(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir){
        if (DoorJam.tryOpenDoor(world, player, pos, state, this.type.setType())) return;

        player.swingHand(player.getActiveHand());
        cir.setReturnValue(ActionResult.SUCCESS);
    }

    @ModifyArg(
            method = "onUse",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V"),
            index = 0
    )
    private PlayerEntity playSoundToClient(PlayerEntity source, @Local(argsOnly = true) BlockState blockState) {
        return DoorJam.mayJam(blockState) ? null : source;
    }
}
