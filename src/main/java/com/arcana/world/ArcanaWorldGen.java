package com.arcana.world;

import com.arcana.ArcanaBlocks;
import com.arcana.ArcanaMaterials.OreMaterial;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.arcana.ArcanaMod;

import java.util.List;

/**
 * World generation for Arcana mod.
 * Registers ore veins for all 6 overworld ores at appropriate Y-levels.
 * 
 * Ores:
 * - Arcanite: Y 0-48, common (8 per vein, 12 veins per chunk)
 * - Runestone: Y 0-32, uncommon (6 per vein, 8 veins per chunk)
 * - Moonsilver: Y 0-24, uncommon (5 per vein, 6 veins per chunk)
 * - Sunsteel: Y 0-16, rare (4 per vein, 4 veins per chunk)
 * - Voidstone: Y -64 to 0, rare (3 per vein, 3 veins per chunk)
 * - Starmetal: Y 32-128, very rare, mountains only (3 per vein, 2 veins per chunk)
 *
 * Dimension ores are configured separately when dimensions are registered.
 * 
 * NOTE: In Forge 1.20.1, ore generation is done via datapack JSON.
 * This class provides the data generation helpers and documents the
 * worldgen JSON files that need to exist in:
 *   data/arcana/worldgen/configured_feature/
 *   data/arcana/worldgen/placed_feature/
 *   data/forge/biome_modifier/
 */
@Mod.EventBusSubscriber(modid = ArcanaMod.MOD_ID)
public class ArcanaWorldGen {

    // Ore generation is configured through JSON datapacks in 1.20.1
    // The following constants document the intended generation parameters
    // that are written into the JSON files by generate_worldgen.py

    public static final int ARCANITE_VEINS_PER_CHUNK = 12;
    public static final int RUNESTONE_VEINS_PER_CHUNK = 8;
    public static final int MOONSILVER_VEINS_PER_CHUNK = 6;
    public static final int SUNSTEEL_VEINS_PER_CHUNK = 4;
    public static final int VOIDSTONE_VEINS_PER_CHUNK = 3;
    public static final int STARMETAL_VEINS_PER_CHUNK = 2;

    /**
     * Generates the worldgen JSON files needed for ore generation.
     * Called by the resource generation script.
     */
    public static String[][] getOreConfigs() {
        return new String[][] {
            // name, minY, maxY, veinSize, veinsPerChunk, replaceTarget
            {"arcanite",   "0",   "48",  "8",  "12", "stone"},
            {"runestone",  "0",   "32",  "6",  "8",  "stone"},
            {"moonsilver", "0",   "24",  "5",  "6",  "stone"},
            {"sunsteel",   "0",   "16",  "4",  "4",  "stone"},
            {"voidstone",  "-64", "0",   "3",  "3",  "deepslate"},
            {"starmetal",  "32",  "128", "3",  "2",  "stone"},
        };
    }
}
