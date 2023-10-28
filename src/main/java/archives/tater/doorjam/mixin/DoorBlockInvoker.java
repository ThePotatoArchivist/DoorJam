package archives.tater.doorjam.mixin;

import net.minecraft.block.DoorBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(DoorBlock.class)
public interface DoorBlockInvoker {
    @Invoker("playOpenCloseSound")
    void invokePlayOpenCloseSound(Entity entity,
                                  @NotNull World world,
                                  BlockPos pos,
                                  boolean open);
}
