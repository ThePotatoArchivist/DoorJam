package archives.tater.doorjam.data;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class DoorJamBlockTags {

    public static final TagKey<Block> FULL_JAMMING_CHANCE = of("full_jamming_chance");
    public static final TagKey<Block> HALF_JAMMING_CHANCE = of("half_jamming_chance");
    public static final TagKey<Block> SLIGHT_JAMMING_CHANCE = of("slight_jamming_chance");

    private static TagKey<Block> of(String id) {
        return TagKey.of(RegistryKeys.BLOCK, new Identifier("doorjam",id));
    }


    public static void init(){}
}
