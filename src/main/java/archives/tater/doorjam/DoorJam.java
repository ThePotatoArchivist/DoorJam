package archives.tater.doorjam;

import archives.tater.doorjam.data.DoorJamBlockTags;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DoorJam implements ModInitializer {

    public static final String MOD_ID = "doorjam";

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final Identifier MOST_OXIDIZED_ID = id("most_oxidized");

    public static boolean mayJam(BlockState state) {
        return state.isIn(DoorJamBlockTags.SLIGHT_JAMMING_CHANCE) ||
                state.isIn(DoorJamBlockTags.HALF_JAMMING_CHANCE) ||
                state.isIn(DoorJamBlockTags.MOST_JAMMING_CHANCE);
    }

    public static boolean alwaysJam(BlockState state) {
        return state.isIn(DoorJamBlockTags.FULL_JAMMING_CHANCE);
    }

    public static void playLockedSound(World world, @Nullable PlayerEntity source, BlockPos pos, BlockSetType type) {
        world.playSound(source, pos, type.doorClose(), SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.1F + 0.5F);
    }

    /**
     * Both doors and trapdoors use this code.
     * @return if the door jammed
     */
    public static boolean tryOpenDoor(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockSetType blockSetType) {
        if (DoorJam.alwaysJam(state)) {
            DoorJam.playLockedSound(world, player, pos, blockSetType);
        } else if (DoorJam.mayJam(state)) {
            if (!world.isClient)
                if (DoorJam.isJammed(state, world.getRandom())) {
                    DoorJam.playLockedSound(world, null, pos, blockSetType);
                } else {
                    return true;
                }
        } else return true;

        return false;
    }

    public static boolean isJammed(BlockState state, Random random){
        return state.isIn(DoorJamBlockTags.FULL_JAMMING_CHANCE) ||
                (state.isIn(DoorJamBlockTags.MOST_JAMMING_CHANCE) && random.nextFloat() < 0.75f) ||
                (state.isIn(DoorJamBlockTags.HALF_JAMMING_CHANCE) && random.nextFloat() < 0.5f) ||
                (state.isIn(DoorJamBlockTags.SLIGHT_JAMMING_CHANCE) && random.nextFloat() < 0.25f);
    }

    @Override
    public void onInitialize() {
        DoorJamBlockTags.init();
        //noinspection OptionalGetWithoutIsPresent
        ResourceManagerHelper.registerBuiltinResourcePack(
                MOST_OXIDIZED_ID,
                FabricLoader.getInstance().getModContainer(MOD_ID).get(),
                Text.literal("Most Oxidized"),
                ResourcePackActivationType.NORMAL
        );
    }
}
