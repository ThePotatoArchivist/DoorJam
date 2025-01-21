package archives.tater.doorjam.mixin;

import archives.tater.doorjam.DoorJam;
import com.boyonk.shutter.block.ShutterBlock;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
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

@Mixin(ShutterBlock.class)
public class ShutterBlockMixin {
    @Shadow @Final private BlockSetType blockSetType;

    @Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
    private void doorOverride(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir){
        if (DoorJam.tryOpenDoor(world, player, pos, state, this.blockSetType)) return;

        player.swingHand(player.getActiveHand());
        cir.setReturnValue(ActionResult.SUCCESS);
    }

    @ModifyArg(
            method = "onUse",
            at = @At(value = "INVOKE", target = "Lcom/boyonk/shutter/block/ShutterBlock;playOpenCloseSound(Lnet/minecraft/entity/Entity;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Z)V"),
            index = 0
    )
    private Entity playSoundToClient(Entity entity, @Local(argsOnly = true) BlockState blockState) {
        return DoorJam.mayJam(blockState) ? null : entity;
    }
}
