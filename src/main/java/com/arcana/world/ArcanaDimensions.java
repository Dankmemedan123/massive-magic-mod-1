package com.arcana.world;

import com.arcana.ArcanaMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

/**
 * Registers 3 custom dimensions:
 *  1. The Arcane Realm — floating islands, aurora sky, magical creatures
 *  2. The Shadow Depths — underground darkness, bioluminescent fungi, void pits
 *  3. The Celestial Spire — crystal sky dimension, star islands, endgame
 *
 * Each dimension has:
 *  - Custom DimensionType (light level, ceiling, time, weather)
 *  - Custom ChunkGenerator (via NoiseBasedChunkGenerator with custom settings)
 *  - 4 unique biomes
 *  - 8 structures
 *  - Unique ambient effects (sky, particles, sounds)
 *  - A boss fight
 *  - Progression-gated access
 *
 * Dimensions are registered via JSON datapacks:
 *   data/arcana/dimension_type/  (dimension properties)
 *   data/arcana/dimension/       (chunk generator + biome source)
 *   data/arcana/worldgen/noise_settings/ (terrain shape)
 */
public class ArcanaDimensions {

    // ─── Dimension Level Keys ───
    public static final ResourceKey<Level> ARCANE_REALM =
            ResourceKey.create(Registries.DIMENSION, new ResourceLocation(ArcanaMod.MOD_ID, "arcane_realm"));
    public static final ResourceKey<Level> SHADOW_DEPTHS =
            ResourceKey.create(Registries.DIMENSION, new ResourceLocation(ArcanaMod.MOD_ID, "shadow_depths"));
    public static final ResourceKey<Level> CELESTIAL_SPIRE =
            ResourceKey.create(Registries.DIMENSION, new ResourceLocation(ArcanaMod.MOD_ID, "celestial_spire"));

    // ─── Dimension Type Keys ───
    public static final ResourceKey<DimensionType> ARCANE_REALM_TYPE =
            ResourceKey.create(Registries.DIMENSION_TYPE, new ResourceLocation(ArcanaMod.MOD_ID, "arcane_realm"));
    public static final ResourceKey<DimensionType> SHADOW_DEPTHS_TYPE =
            ResourceKey.create(Registries.DIMENSION_TYPE, new ResourceLocation(ArcanaMod.MOD_ID, "shadow_depths"));
    public static final ResourceKey<DimensionType> CELESTIAL_SPIRE_TYPE =
            ResourceKey.create(Registries.DIMENSION_TYPE, new ResourceLocation(ArcanaMod.MOD_ID, "celestial_spire"));

    /**
     * Dimension properties for JSON generation.
     */
    public record DimensionProperties(
            boolean hasSkylight, boolean hasCeiling, boolean ultraWarm,
            boolean natural, boolean piglinSafe, boolean bedWorks,
            boolean respawnAnchorWorks, boolean hasRaids,
            int minY, int height, int logicalHeight,
            float ambientLight, long fixedTime, // -1 = normal day/night
            String infiniburn, String effects
    ) {}

    public static DimensionProperties getProperties(ResourceKey<Level> dim) {
        if (dim == ARCANE_REALM) {
            return new DimensionProperties(
                    true, false, false,     // skylight, no ceiling, not ultrawarm
                    false, false, false,     // not natural, no piglins, no beds
                    false, false,            // no anchors, no raids
                    0, 384, 384,             // Y range
                    0.15f, -1,               // dim ambient, normal time cycle
                    "minecraft:infiniburn_overworld",
                    "arcana:arcane_realm"    // Custom sky renderer
            );
        }
        if (dim == SHADOW_DEPTHS) {
            return new DimensionProperties(
                    false, true, false,      // no skylight, ceiling, not ultrawarm
                    false, false, false,
                    false, false,
                    -64, 384, 256,
                    0.02f, 18000,            // Very dark, fixed midnight
                    "minecraft:infiniburn_nether",
                    "arcana:shadow_depths"
            );
        }
        if (dim == CELESTIAL_SPIRE) {
            return new DimensionProperties(
                    true, false, false,
                    false, false, false,
                    false, false,
                    0, 512, 512,             // Extra tall for crystal peaks
                    0.2f, -1,                // Moderate ambient, normal time
                    "minecraft:infiniburn_end",
                    "arcana:celestial_spire"
            );
        }
        // Default (shouldn't reach here)
        return new DimensionProperties(true, false, false, true, false, true, false, true,
                -64, 384, 384, 0.0f, -1, "minecraft:infiniburn_overworld", "minecraft:overworld");
    }
}
