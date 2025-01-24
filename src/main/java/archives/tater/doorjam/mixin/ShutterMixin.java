package archives.tater.doorjam.mixin;

import archives.tater.doorjam.DoorJam;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.stehschnitzel.shutter.block.WeatheringCopperShutter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WeatheringCopperShutter.class)
public class ShutterMixin {

    @Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
    private void doorOverride(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir){
        if (DoorJam.tryOpenDoor(world, player, pos, state, BlockSetType.COPPER)) return;

        player.swingHand(player.getActiveHand());
        cir.setReturnValue(ActionResult.SUCCESS);
    }

//    @ModifyArg(
//            method = "onUse",
//            at = @At(value = "INVOKE", target = "Lnet/stehschnitzel/shutter/block/WeatheringCopperShutter;playSound(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;I)V"),
//            index = 0
//    )
//    private World playSoundToClient(World par1, @Local(argsOnly = true) BlockState blockState) {
//        return null;
//    }
}
