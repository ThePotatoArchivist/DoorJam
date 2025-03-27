package archives.tater.doorjam;

import archives.tater.doorjam.data.DoorJamAltBlockTagProvider;
import archives.tater.doorjam.data.DoorJamBlockTagProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class DoorJamDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        var pack = fabricDataGenerator.createPack();
        pack.addProvider(DoorJamBlockTagProvider::new);

        var altPack = fabricDataGenerator.createBuiltinResourcePack(DoorJam.MOST_OXIDIZED_ID);
        altPack.addProvider(DoorJamAltBlockTagProvider::new);
    }
}
