package archives.tater.doorjam.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unchecked")
public class DoorJamBlockTagProvider extends FabricTagProvider<Block> {


    public DoorJamBlockTagProvider(FabricDataOutput output, CompletableFuture registriesFuture) {
        super(output, RegistryKeys.BLOCK, registriesFuture);
    }


    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(DJBlockTags.SLIGHT_JAMMING_CHANCE)
                .add(Blocks.EXPOSED_COPPER_DOOR)
                .add(Blocks.EXPOSED_COPPER_TRAPDOOR);
        getOrCreateTagBuilder(DJBlockTags.HALF_JAMMING_CHANCE)
                .add(Blocks.WEATHERED_COPPER_DOOR)
                .add(Blocks.WEATHERED_COPPER_TRAPDOOR);
        getOrCreateTagBuilder(DJBlockTags.FULL_JAMMING_CHANCE)
                .add(Blocks.OXIDIZED_COPPER_DOOR)
                .add(Blocks.OXIDIZED_COPPER_TRAPDOOR);
    }
}
