package com.arcana.world;

import com.arcana.ArcanaMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.*;

/**
 * Registers all 8 overworld biomes + 12 dimension biomes = 20 total.
 * Each biome has unique sky, fog, water color, ambient particles, and mob spawns.
 *
 * Biomes are registered via JSON datapacks in 1.20.1.
 * This class defines the ResourceKeys and provides the bootstrap data.
 */
public class ArcanaBiomes {

    // ─── Overworld Biomes (8) ───
    public static final ResourceKey<Biome> ENCHANTED_FOREST = key("enchanted_forest");
    public static final ResourceKey<Biome> CRYSTAL_WASTES = key("crystal_wastes");
    public static final ResourceKey<Biome> SHADOW_MARSH = key("shadow_marsh");
    public static final ResourceKey<Biome> STARFALL_PEAKS = key("starfall_peaks");
    public static final ResourceKey<Biome> SCORCHED_BARRENS = key("scorched_barrens");
    public static final ResourceKey<Biome> FROZEN_SANCTUM_TUNDRA = key("frozen_sanctum_tundra");
    public static final ResourceKey<Biome> WHISPERING_THICKET = key("whispering_thicket");
    public static final ResourceKey<Biome> ARCANE_NEXUS = key("arcane_nexus");

    // ─── Arcane Realm Biomes (4) ───
    public static final ResourceKey<Biome> MANA_GARDENS = key("mana_gardens");
    public static final ResourceKey<Biome> CRYSTAL_SPIRES = key("crystal_spires");
    public static final ResourceKey<Biome> AETHER_HIGHLANDS = key("aether_highlands");
    public static final ResourceKey<Biome> ARCANE_WASTES = key("arcane_wastes");

    // ─── Shadow Depths Biomes (4) ───
    public static final ResourceKey<Biome> FUNGAL_CAVERNS = key("fungal_caverns");
    public static final ResourceKey<Biome> VOID_CHASM = key("void_chasm");
    public static final ResourceKey<Biome> SHADOW_FOREST = key("shadow_forest");
    public static final ResourceKey<Biome> BONE_FIELDS = key("bone_fields");

    // ─── Celestial Spire Biomes (4) ───
    public static final ResourceKey<Biome> STAR_GARDENS = key("star_gardens");
    public static final ResourceKey<Biome> CRYSTAL_PEAKS = key("crystal_peaks");
    public static final ResourceKey<Biome> SOLAR_PLATEAU = key("solar_plateau");
    public static final ResourceKey<Biome> ETERNAL_DUSK = key("eternal_dusk");

    private static ResourceKey<Biome> key(String name) {
        return ResourceKey.create(Registries.BIOME, new ResourceLocation(ArcanaMod.MOD_ID, name));
    }

    // ─── Biome color definitions (used by JSON datapacks) ───

    /** Sky / fog / water / grass / foliage colors per biome */
    public record BiomeColors(int sky, int fog, int water, int waterFog, int grass, int foliage) {}

    public static BiomeColors getColors(ResourceKey<Biome> biome) {
        if (biome == ENCHANTED_FOREST)       return new BiomeColors(0x99CCFF, 0xCCDDFF, 0x44AAFF, 0x2288CC, 0x55CC77, 0x44BB66);
        if (biome == CRYSTAL_WASTES)         return new BiomeColors(0xFFEEDD, 0xFFDDCC, 0x88CCFF, 0x6699CC, 0xCCBBAA, 0xBBAA99);
        if (biome == SHADOW_MARSH)           return new BiomeColors(0x222233, 0x110022, 0x332244, 0x221133, 0x334422, 0x223311);
        if (biome == STARFALL_PEAKS)         return new BiomeColors(0x112244, 0x0A1133, 0x4466AA, 0x335599, 0x667788, 0x556677);
        if (biome == SCORCHED_BARRENS)       return new BiomeColors(0xFF6633, 0xCC4411, 0xFF4400, 0xCC3300, 0x886644, 0x775533);
        if (biome == FROZEN_SANCTUM_TUNDRA)  return new BiomeColors(0xBBDDFF, 0xAABBDD, 0x88BBEE, 0x6699BB, 0xBBCCDD, 0xAABBCC);
        if (biome == WHISPERING_THICKET)     return new BiomeColors(0xAADDBB, 0x88BBAA, 0x66AA88, 0x448866, 0x66BB88, 0x55AA77);
        if (biome == ARCANE_NEXUS)           return new BiomeColors(0x8844FF, 0x6622CC, 0x7733EE, 0x5522BB, 0x9955FF, 0x8844EE);
        // Arcane Realm
        if (biome == MANA_GARDENS)           return new BiomeColors(0xFFAAFF, 0xDD88DD, 0xFF88FF, 0xDD66DD, 0x88FF88, 0x66DD66);
        if (biome == CRYSTAL_SPIRES)         return new BiomeColors(0xDDEEFF, 0xBBCCDD, 0xAADDFF, 0x88BBDD, 0xCCDDEE, 0xBBCCDD);
        if (biome == AETHER_HIGHLANDS)       return new BiomeColors(0xFFFFDD, 0xEEEEBB, 0xBBDDFF, 0x99BBDD, 0xDDFFDD, 0xBBDDBB);
        if (biome == ARCANE_WASTES)          return new BiomeColors(0x442266, 0x331155, 0x553377, 0x442266, 0x554466, 0x443355);
        // Shadow Depths
        if (biome == FUNGAL_CAVERNS)         return new BiomeColors(0x112211, 0x0A110A, 0x224422, 0x113311, 0x226622, 0x115511);
        if (biome == VOID_CHASM)             return new BiomeColors(0x000011, 0x000008, 0x110022, 0x080011, 0x111122, 0x0A0A11);
        if (biome == SHADOW_FOREST)          return new BiomeColors(0x111122, 0x0A0A11, 0x222233, 0x111122, 0x223322, 0x112211);
        if (biome == BONE_FIELDS)            return new BiomeColors(0x998877, 0x887766, 0x556644, 0x445533, 0x887766, 0x776655);
        // Celestial Spire
        if (biome == STAR_GARDENS)           return new BiomeColors(0x110033, 0x0A0022, 0x8888FF, 0x6666DD, 0x88AACC, 0x7799BB);
        if (biome == CRYSTAL_PEAKS)          return new BiomeColors(0xFFEEFF, 0xDDCCDD, 0xFFDDFF, 0xDDBBDD, 0xEEDDEE, 0xDDCCDD);
        if (biome == SOLAR_PLATEAU)          return new BiomeColors(0xFFDD44, 0xEECC33, 0xFFCC44, 0xDDAA22, 0xDDBB44, 0xCCAA33);
        if (biome == ETERNAL_DUSK)           return new BiomeColors(0x663344, 0x552233, 0x775544, 0x664433, 0x665544, 0x554433);
        // Default
        return new BiomeColors(0x77AAFF, 0xC0D8FF, 0x3F76E4, 0x050533, 0x7CBD6B, 0x59AE30);
    }

    /** Temperature per biome (affects snow, grass tint) */
    public static float getTemperature(ResourceKey<Biome> biome) {
        if (biome == FROZEN_SANCTUM_TUNDRA) return -0.5f;
        if (biome == SCORCHED_BARRENS) return 2.0f;
        if (biome == SHADOW_MARSH) return 0.6f;
        if (biome == STARFALL_PEAKS) return 0.1f;
        if (biome == ENCHANTED_FOREST) return 0.8f;
        if (biome == SOLAR_PLATEAU) return 1.5f;
        if (biome == BONE_FIELDS) return 0.3f;
        if (biome == VOID_CHASM) return 0.0f;
        return 0.7f; // Default temperate
    }
}
