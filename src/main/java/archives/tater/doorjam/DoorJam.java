package archives.tater.doorjam;

import archives.tater.doorjam.data.DoorJamBlockTags;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.pack.PackActivationType;
import net.fabricmc.loader.api.FabricLoader;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DoorJam implements ModInitializer {

    public static final String MOD_ID = "doorjam";

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final Identifier MOST_OXIDIZED_ID = id("most_oxidized");

    public static boolean mayJam(BlockState state) {
        return state.is(DoorJamBlockTags.SLIGHT_JAMMING_CHANCE) ||
                state.is(DoorJamBlockTags.HALF_JAMMING_CHANCE) ||
                state.is(DoorJamBlockTags.MOST_JAMMING_CHANCE);
    }

    public static boolean alwaysJam(BlockState state) {
        return state.is(DoorJamBlockTags.FULL_JAMMING_CHANCE);
    }

    public static void playLockedSound(Level level, @Nullable Player source, BlockPos pos, BlockSetType type) {
        level.playSound(source, pos, type.doorClose(), SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.1F + 0.5F);
    }

    /**
     * Both doors and trapdoors use this code.
     * @return if the door jammed
     */
    public static boolean tryOpenDoor(Level level, Player player, BlockPos pos, BlockState state, BlockSetType blockSetType) {
        if (DoorJam.alwaysJam(state)) {
            DoorJam.playLockedSound(level, player, pos, blockSetType);
        } else if (DoorJam.mayJam(state)) {
            if (!level.isClientSide())
                if (DoorJam.isJammed(state, level.getRandom())) {
                    DoorJam.playLockedSound(level, null, pos, blockSetType);
                } else {
                    return true;
                }
        } else return true;

        return false;
    }

    public static boolean isJammed(BlockState state, RandomSource random){
        return state.is(DoorJamBlockTags.FULL_JAMMING_CHANCE) ||
                (state.is(DoorJamBlockTags.MOST_JAMMING_CHANCE) && random.nextFloat() < 0.75f) ||
                (state.is(DoorJamBlockTags.HALF_JAMMING_CHANCE) && random.nextFloat() < 0.5f) ||
                (state.is(DoorJamBlockTags.SLIGHT_JAMMING_CHANCE) && random.nextFloat() < 0.25f);
    }

    @Override
    public void onInitialize() {
        DoorJamBlockTags.init();
        //noinspection OptionalGetWithoutIsPresent
        ResourceLoader.registerBuiltinPack(
                MOST_OXIDIZED_ID,
                FabricLoader.getInstance().getModContainer(MOD_ID).get(),
                Component.literal("Most Oxidized"),
                PackActivationType.NORMAL
        );
    }
}
