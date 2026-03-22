package com.arcana.world;

import com.arcana.ArcanaMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;

/**
 * Registers all 44 structures across overworld + 3 dimensions.
 * Each structure is defined by a structure JSON in data/arcana/worldgen/structure/
 * and placed via data/arcana/worldgen/structure_set/
 *
 * Structure generation uses Jigsaw (template pools) for variety.
 * Each structure has:
 *  - Unique block palette
 *  - Loot table reference
 *  - Mob spawner configuration
 *  - At least 1 puzzle mechanic
 *  - At least 1 lore book
 */
public class ArcanaStructures {

    // ─── Overworld Structures (20) ───
    public static final ResourceKey<Structure> WIZARD_TOWER = key("wizard_tower");
    public static final ResourceKey<Structure> RUINED_WIZARD_TOWER = key("ruined_wizard_tower");
    public static final ResourceKey<Structure> CRYSTAL_CAVE = key("crystal_cave");
    public static final ResourceKey<Structure> ARCANE_LIBRARY = key("arcane_library");
    public static final ResourceKey<Structure> ABANDONED_RITUAL_SITE = key("abandoned_ritual_site");
    public static final ResourceKey<Structure> MANA_SPRING = key("mana_spring");
    public static final ResourceKey<Structure> ENCHANTED_GROVE = key("enchanted_grove");
    public static final ResourceKey<Structure> DARK_SHRINE = key("dark_shrine");
    public static final ResourceKey<Structure> DRAGON_ROOST = key("dragon_roost");
    public static final ResourceKey<Structure> ANCIENT_FORGE = key("ancient_forge");
    public static final ResourceKey<Structure> MAGE_GUILD_OUTPOST = key("mage_guild_outpost");
    public static final ResourceKey<Structure> ELEMENTAL_NEXUS_FIRE = key("elemental_nexus_fire");
    public static final ResourceKey<Structure> ELEMENTAL_NEXUS_ICE = key("elemental_nexus_ice");
    public static final ResourceKey<Structure> ELEMENTAL_NEXUS_LIGHTNING = key("elemental_nexus_lightning");
    public static final ResourceKey<Structure> ELEMENTAL_NEXUS_EARTH = key("elemental_nexus_earth");
    public static final ResourceKey<Structure> SKY_ALTAR = key("sky_altar");
    public static final ResourceKey<Structure> UNDERGROUND_CATHEDRAL = key("underground_cathedral");
    public static final ResourceKey<Structure> PORTAL_RUINS = key("portal_ruins");
    public static final ResourceKey<Structure> FROZEN_SANCTUM = key("frozen_sanctum");
    public static final ResourceKey<Structure> FLAME_SHRINE = key("flame_shrine");

    // ─── Arcane Realm Structures (8) ───
    public static final ResourceKey<Structure> CRYSTAL_PALACE = key("crystal_palace");
    public static final ResourceKey<Structure> MANA_WELLSPRING = key("mana_wellspring");
    public static final ResourceKey<Structure> ARCANE_CITADEL = key("arcane_citadel");
    public static final ResourceKey<Structure> FLOATING_MARKET = key("floating_market");
    public static final ResourceKey<Structure> MANA_OBSERVATORY = key("mana_observatory");
    public static final ResourceKey<Structure> CORRUPTED_TOWER = key("corrupted_tower");
    public static final ResourceKey<Structure> CRYSTAL_CONSERVATORY = key("crystal_conservatory");
    public static final ResourceKey<Structure> AETHER_DOCK = key("aether_dock");

    // ─── Shadow Depths Structures (8) ───
    public static final ResourceKey<Structure> SHADOW_FORTRESS = key("shadow_fortress");
    public static final ResourceKey<Structure> LICHS_CRYPT = key("lichs_crypt");
    public static final ResourceKey<Structure> MUSHROOM_VILLAGE = key("mushroom_village");
    public static final ResourceKey<Structure> VOID_GATE = key("void_gate");
    public static final ResourceKey<Structure> OSSUARY = key("ossuary");
    public static final ResourceKey<Structure> SUSPENDED_LIBRARY = key("suspended_library");
    public static final ResourceKey<Structure> WHISPERING_SHRINE = key("whispering_shrine");
    public static final ResourceKey<Structure> SHADOW_OUTPOST = key("shadow_outpost");

    // ─── Celestial Spire Structures (8) ───
    public static final ResourceKey<Structure> DRAGONS_THRONE = key("dragons_throne");
    public static final ResourceKey<Structure> CELESTIAL_CITADEL = key("celestial_citadel");
    public static final ResourceKey<Structure> ETERNIUM_VAULT = key("eternium_vault");
    public static final ResourceKey<Structure> ECLIPSE_TEMPLE = key("eclipse_temple");
    public static final ResourceKey<Structure> CRYSTAL_FORGE = key("crystal_forge");
    public static final ResourceKey<Structure> STAR_WELL = key("star_well");
    public static final ResourceKey<Structure> CELESTIAL_GREENHOUSE = key("celestial_greenhouse");
    public static final ResourceKey<Structure> VOID_INCARNATE_SHRINE = key("void_incarnate_shrine");

    private static ResourceKey<Structure> key(String name) {
        return ResourceKey.create(Registries.STRUCTURE, new ResourceLocation(ArcanaMod.MOD_ID, name));
    }

    /**
     * Structure loot table mappings.
     * Each structure's chests reference these loot tables.
     */
    public static ResourceLocation getLootTable(String structureName) {
        return new ResourceLocation(ArcanaMod.MOD_ID, "chests/" + structureName);
    }

    /**
     * Structure metadata for worldgen configuration.
     * Spacing = min chunks between structures, Separation = additional offset.
     */
    public record StructureConfig(int spacing, int separation, String biomeTag) {}

    public static StructureConfig getConfig(ResourceKey<Structure> structure) {
        // Overworld — more common
        if (structure == WIZARD_TOWER)          return new StructureConfig(24, 8, "is_overworld");
        if (structure == RUINED_WIZARD_TOWER)   return new StructureConfig(20, 6, "is_overworld");
        if (structure == ARCANE_LIBRARY)         return new StructureConfig(32, 12, "is_overworld");
        if (structure == MANA_SPRING)            return new StructureConfig(28, 8, "is_overworld");
        if (structure == MAGE_GUILD_OUTPOST)     return new StructureConfig(40, 16, "is_overworld");
        if (structure == PORTAL_RUINS)           return new StructureConfig(48, 20, "is_overworld");
        // Biome-specific overworld
        if (structure == CRYSTAL_CAVE)           return new StructureConfig(36, 12, "crystal_wastes");
        if (structure == DARK_SHRINE)            return new StructureConfig(28, 10, "shadow_marsh");
        if (structure == DRAGON_ROOST)           return new StructureConfig(48, 20, "scorched_barrens");
        if (structure == SKY_ALTAR)              return new StructureConfig(40, 16, "starfall_peaks");
        if (structure == FROZEN_SANCTUM)         return new StructureConfig(32, 12, "frozen_sanctum_tundra");
        if (structure == FLAME_SHRINE)           return new StructureConfig(32, 12, "scorched_barrens");
        // Underground
        if (structure == UNDERGROUND_CATHEDRAL)  return new StructureConfig(48, 20, "is_overworld");
        if (structure == ANCIENT_FORGE)          return new StructureConfig(36, 14, "is_overworld");
        // Dimension structures — denser since dimensions are smaller
        return new StructureConfig(16, 6, "any");
    }
}
