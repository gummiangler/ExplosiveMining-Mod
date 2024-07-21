package io.github.gummiangler.explosivemining_mod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.TntEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Explosivemining_mod implements ModInitializer {
    private static final double TNT_PROBABILITY = 0.7; // 70% Wahrscheinlichkeit
    public static final Logger LOGGER = LoggerFactory.getLogger("explosivemining_mod");

    @Override
    public void onInitialize() {
        // Registriere das Blockabbau-Event
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            if (!world.isClient && Math.random() < TNT_PROBABILITY) {
                spawnPrimedTNT(world, pos);
            }
        });
    }

    private void spawnPrimedTNT(World world, BlockPos pos) {
        // Erzeuge eine primed TNT-Entität und zünde sie an
        TntEntity tnt = new TntEntity(EntityType.TNT, world);
        tnt.setFuse(40); // 2 Sekunden bis zur Explosion (40 Ticks)
        tnt.updatePosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
        world.spawnEntity(tnt);
        LOGGER.info("Spawned primed TNT at {}", pos);
    }
}
