package io.papermc.paper.testplugin.behaviors;

import io.papermc.paper.entity.brain.BrainHolder;
import io.papermc.paper.entity.brain.sensor.Sensor;
import io.papermc.paper.testplugin.TestPlugin;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.memory.MemoryKey;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ClosestParrotSensor implements Sensor<LivingEntity> {

    @Override
    public int getInterval() {
        return 10;
    }

    @Override
    public void tick(@NotNull LivingEntity entity) {
        List<Parrot> parrots = entity.getNearbyEntities(10, 5, 10)
            .stream()
            .filter((nearbyEntity) -> nearbyEntity instanceof Parrot)
            .map((parrot) -> ((Parrot) parrot))
            .toList();

        var brainHolder = ((BrainHolder<?>) entity);

        if (!parrots.isEmpty()) {
            brainHolder.setMemory(TestPlugin.NEARBY_PARROTS, new ArrayList<>(parrots), Long.MAX_VALUE);
        } else {
            brainHolder.forgetMemory(TestPlugin.NEARBY_PARROTS);
        }

    }

    @Override
    public @NotNull Collection<MemoryKey<?>> requiredMemories() {
        return Set.of(TestPlugin.NEARBY_PARROTS);
    }
}