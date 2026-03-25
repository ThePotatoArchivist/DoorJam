//package archives.tater.doorjam.mixin;
//
//import archives.tater.doorjam.DoorJam;
//import com.boyonk.shutter.block.ShutterBlock;
//import com.llamalad7.mixinextras.sugar.Local;
//import net.minecraft.core.BlockPos;
//import net.minecraft.world.InteractionResult;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.level.block.state.properties.BlockSetType;
//import net.minecraft.world.phys.BlockHitResult;
//import org.spongepowered.asm.mixin.Final;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.ModifyArg;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//
//@Mixin(ShutterBlock.class)
//public class ShutterBlockMixin {
//    @Shadow @Final private BlockSetType blockSetType;
//
//    @Inject(method = "useWithoutItem", at = @At("HEAD"), cancellable = true)
//    private void doorOverride(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir){
//        if (DoorJam.tryOpenDoor(world, player, pos, state, this.blockSetType)) return;
//
//        player.swing(player.getUsedItemHand());
//        cir.setReturnValue(InteractionResult.SUCCESS);
//    }
//
//    @ModifyArg(
//            method = "useWithoutItem",
//            at = @At(value = "INVOKE", target = "Lcom/boyonk/shutter/block/ShutterBlock;playOpenCloseSound(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Z)V"),
//            index = 0
//    )
//    private Entity playSoundToClient(Entity entity, @Local(argsOnly = true) BlockState blockState) {
//        return DoorJam.mayJam(blockState) ? null : entity;
//    }
//}
