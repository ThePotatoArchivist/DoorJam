package archives.tater.doorjam.data;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Blocks;

import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class DoorJamBlockTagProvider extends FabricTagsProvider.BlockTagsProvider {

    public DoorJamBlockTagProvider(FabricPackOutput output, CompletableFuture<Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    protected static final Map<String, String> COMPAT_LIST = ImmutableMap.of(
            "shutter", "shutter",
            "metalfences", "fence_gate"
    );

    @Override
    protected void addTags(Provider wrapperLookup) {
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
            getOrCreateRawBuilder(DoorJamBlockTags.SLIGHT_JAMMING_CHANCE).addOptionalElement(Identifier.fromNamespaceAndPath(modid, "exposed_copper_" + itemName));
            getOrCreateRawBuilder(DoorJamBlockTags.HALF_JAMMING_CHANCE).addOptionalElement(Identifier.fromNamespaceAndPath(modid, "weathered_copper_" + itemName));
            getOrCreateRawBuilder(DoorJamBlockTags.FULL_JAMMING_CHANCE).addOptionalElement(Identifier.fromNamespaceAndPath(modid, "oxidized_copper_" + itemName));
        });
    }
}
