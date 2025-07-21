package archives.tater.doorjam.data;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class DoorJamBlockTagProvider extends FabricTagProvider.BlockTagProvider {

    public DoorJamBlockTagProvider(FabricDataOutput output, CompletableFuture<WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    protected static final Map<String, String> COMPAT_LIST = ImmutableMap.of(
            "shutter", "shutter",
            "metalfences", "fence_gate"
    );

    @Override
    protected void configure(WrapperLookup wrapperLookup) {
        valueLookupBuilder(DoorJamBlockTags.SLIGHT_JAMMING_CHANCE)
                .add(Blocks.EXPOSED_COPPER_DOOR)
                .add(Blocks.EXPOSED_COPPER_TRAPDOOR);

        valueLookupBuilder(DoorJamBlockTags.HALF_JAMMING_CHANCE)
                .add(Blocks.WEATHERED_COPPER_DOOR)
                .add(Blocks.WEATHERED_COPPER_TRAPDOOR);
        valueLookupBuilder(DoorJamBlockTags.FULL_JAMMING_CHANCE)
                .add(Blocks.OXIDIZED_COPPER_DOOR)
                .add(Blocks.OXIDIZED_COPPER_TRAPDOOR);

        COMPAT_LIST.forEach((modid, itemName) -> {
            getTagBuilder(DoorJamBlockTags.SLIGHT_JAMMING_CHANCE).addOptional(Identifier.of(modid, "exposed_copper_" + itemName));
            getTagBuilder(DoorJamBlockTags.HALF_JAMMING_CHANCE).addOptional(Identifier.of(modid, "weathered_copper_" + itemName));
            getTagBuilder(DoorJamBlockTags.FULL_JAMMING_CHANCE).addOptional(Identifier.of(modid, "oxidized_copper_" + itemName));
        });
    }
}
