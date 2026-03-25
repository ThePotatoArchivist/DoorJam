package archives.tater.doorjam.data;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Blocks;
import java.util.concurrent.CompletableFuture;

public class DoorJamAltBlockTagProvider extends DoorJamBlockTagProvider {
    public DoorJamAltBlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        valueLookupBuilder(DoorJamBlockTags.FULL_JAMMING_CHANCE).setReplace(true);

        valueLookupBuilder(DoorJamBlockTags.MOST_JAMMING_CHANCE)
                .add(Blocks.OXIDIZED_COPPER_DOOR)
                .add(Blocks.OXIDIZED_COPPER_TRAPDOOR);

        COMPAT_LIST.forEach((modid, itemName) ->
                getOrCreateRawBuilder(DoorJamBlockTags.MOST_JAMMING_CHANCE).addOptionalElement(Identifier.fromNamespaceAndPath(modid, "oxidized_copper_" + itemName))
        );
    }
}
