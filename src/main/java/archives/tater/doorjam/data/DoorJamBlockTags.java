package archives.tater.doorjam.data;

import archives.tater.doorjam.DoorJam;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class DoorJamBlockTags {

    public static final TagKey<Block> FULL_JAMMING_CHANCE = of("full_jamming_chance");
    public static final TagKey<Block> MOST_JAMMING_CHANCE = of("most_jamming_chance");
    public static final TagKey<Block> HALF_JAMMING_CHANCE = of("half_jamming_chance");
    public static final TagKey<Block> SLIGHT_JAMMING_CHANCE = of("slight_jamming_chance");

    private static TagKey<Block> of(String id) {
        return TagKey.of(RegistryKeys.BLOCK, DoorJam.id(id));
    }


    public static void init(){}
}
