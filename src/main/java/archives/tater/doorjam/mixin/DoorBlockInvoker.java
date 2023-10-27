package archives.tater.doorjam.mixin;

import net.minecraft.block.DoorBlock;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(DoorBlock.class)
public interface DoorBlockInvoker {
    @Invoker("playOpenCloseSound")
    void invokePlayOpenCloseSound(net.minecraft.entity.Entity entity,
                                  @NotNull net.minecraft.world.World world,
                                  net.minecraft.util.math.BlockPos pos,
                                  boolean open);
}
