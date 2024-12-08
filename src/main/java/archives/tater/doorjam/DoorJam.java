package archives.tater.doorjam;

import archives.tater.doorjam.data.DJBlockTags;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DoorJam implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("doorjam");

    public static boolean mayJam(BlockState state) {
        return state.isIn(DJBlockTags.SLIGHT_JAMMING_CHANCE) ||
                state.isIn(DJBlockTags.HALF_JAMMING_CHANCE);
    }

    public static boolean alwaysJam(BlockState state) {
        return state.isIn(DJBlockTags.FULL_JAMMING_CHANCE);
    }

    public static void playLockedSound(World world, @Nullable PlayerEntity source, BlockPos pos, BlockSetType type) {
        world.playSound(source, pos, type.doorClose(), SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.1F + 0.5F);
    }

    public static boolean tryOpenDoor(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockSetType blockSetType) {
        if (DoorJam.alwaysJam(state)) {
            DoorJam.playLockedSound(world, player, pos, blockSetType);
        } else if (DoorJam.mayJam(state)) {
            if (!world.isClient)
                if (DoorJam.isJammed(state, world.getRandom())) {
                    DoorJam.playLockedSound(world, null, pos, blockSetType);
                } else {
                    return false;
                }
        } else return false;

        return true;
    }

    /*
        Both doors and trapdoors use this code.
     */
    public static boolean isJammed(BlockState state, Random random){
        return state.isIn(DJBlockTags.FULL_JAMMING_CHANCE) ||
                (state.isIn(DJBlockTags.HALF_JAMMING_CHANCE) && random.nextFloat() < 0.5f) ||
                (state.isIn(DJBlockTags.SLIGHT_JAMMING_CHANCE) && random.nextFloat() < 0.25f);
    }
    @Override
    public void onInitialize() {
        DJBlockTags.init();
    }
}
