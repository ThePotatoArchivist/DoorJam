package archives.tater.doorjam;

import archives.tater.doorjam.data.DJBlockTags;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.random.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DoorJam implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("doorjam");

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
