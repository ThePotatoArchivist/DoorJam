package archives.tater.doorjam.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class DoorJamAltBlockTagProvider extends DoorJamBlockTagProvider {
    public DoorJamAltBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(DoorJamBlockTags.FULL_JAMMING_CHANCE).setReplace(true);

        getOrCreateTagBuilder(DoorJamBlockTags.MOST_JAMMING_CHANCE)
                .add(Blocks.OXIDIZED_COPPER_DOOR)
                .add(Blocks.OXIDIZED_COPPER_TRAPDOOR);

        COMPAT_LIST.forEach((modid, itemName) -> {
            getOrCreateTagBuilder(DoorJamBlockTags.MOST_JAMMING_CHANCE).addOptional(Identifier.of(modid, "oxidized_copper_" + itemName));
        });
    }
}
