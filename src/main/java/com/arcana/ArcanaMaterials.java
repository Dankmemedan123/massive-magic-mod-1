package com.arcana;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Blocks;

/**
 * Master material definitions for Arcana.
 * Each material generates: ore block, raw ore item, ingot/gem, nugget, storage block.
 * Tool and armor materials reference these for stats.
 */
public class ArcanaMaterials {

    // ─── ORE MATERIALS (generate ore blocks + raw + ingot + nugget + block) ───
    public enum OreMaterial {
        // Overworld ores
        ARCANITE("arcanite", 0, 48, 8, 3.0f, 1),         // y 0-48, 8 per vein, 3.0 hardness, iron harvest
        RUNESTONE("runestone", 0, 32, 6, 4.0f, 2),
        MOONSILVER("moonsilver", 0, 24, 5, 4.5f, 2),
        SUNSTEEL("sunsteel", 0, 16, 4, 5.0f, 2),
        VOIDSTONE("voidstone", -64, 0, 3, 6.0f, 3),
        STARMETAL("starmetal", 32, 128, 3, 5.5f, 3),

        // Dimension ores (worldgen configured separately)
        AETHRIUM("aethrium", 0, 64, 5, 6.0f, 3),
        DRACONIC("draconic", 0, 48, 3, 8.0f, 4),
        CELESTIUM("celestium", 0, 64, 4, 7.0f, 4),
        SHADOWITE("shadowite", 0, 48, 4, 6.5f, 3),
        CRYSTALITE("crystalite", 0, 64, 6, 5.0f, 2),
        ETERNIUM("eternium", 0, 16, 1, 10.0f, 4);

        public final String name;
        public final int minY, maxY, veinSize;
        public final float hardness;
        public final int harvestLevel;

        OreMaterial(String name, int minY, int maxY, int veinSize, float hardness, int harvestLevel) {
            this.name = name;
            this.minY = minY;
            this.maxY = maxY;
            this.veinSize = veinSize;
            this.hardness = hardness;
            this.harvestLevel = harvestLevel;
        }
    }

    // ─── ALLOY MATERIALS (generate ingot + nugget + block only, no ore) ───
    public enum AlloyMaterial {
        ENCHANTED_IRON("enchanted_iron"),
        RUNIC_STEEL("runic_steel"),
        MOONFORGED_STEEL("moonforged_steel"),
        SUNFORGED_STEEL("sunforged_steel"),
        VOID_ALLOY("void_alloy"),
        STAR_ALLOY("star_alloy"),
        ARCANE_BRONZE("arcane_bronze"),
        MITHRIL("mithril"),
        ADAMANTINE("adamantine"),
        AETHER_STEEL("aether_steel"),
        DRAGONSCALE_ALLOY("dragonscale_alloy"),
        CELESTIAL_ALLOY("celestial_alloy"),
        SHADOW_STEEL("shadow_steel"),
        CRYSTAL_ALLOY("crystal_alloy"),
        ETERNIUM_ALLOY("eternium_alloy"),
        PHOENIX_ALLOY("phoenix_alloy"),
        FROST_ALLOY("frost_alloy"),
        STORM_ALLOY("storm_alloy");

        public final String name;

        AlloyMaterial(String name) {
            this.name = name;
        }
    }

    // ─── GEM MATERIALS (generate gem item + block, no ore unless crystal) ───
    public enum GemMaterial {
        MANA_CRYSTAL_DIM("mana_crystal_dim"),
        MANA_CRYSTAL_GLOWING("mana_crystal_glowing"),
        MANA_CRYSTAL_RADIANT("mana_crystal_radiant"),
        MANA_CRYSTAL_BLAZING("mana_crystal_blazing"),
        MANA_CRYSTAL_PRISMATIC("mana_crystal_prismatic"),
        FIRE_RUBY("fire_ruby"),
        FROST_SAPPHIRE("frost_sapphire"),
        LIGHTNING_TOPAZ("lightning_topaz"),
        EARTH_EMERALD("earth_emerald"),
        WIND_OPAL("wind_opal"),
        SHADOW_ONYX("shadow_onyx"),
        LIGHT_PEARL("light_pearl"),
        ARCANE_AMETHYST("arcane_amethyst"),
        VOID_DIAMOND("void_diamond");

        public final String name;

        GemMaterial(String name) {
            this.name = name;
        }
    }

    // ─── ESSENCE MATERIALS (generate essence items) ───
    public enum EssenceMaterial {
        // Elemental
        FIRE("fire_essence"), ICE("ice_essence"), LIGHTNING("lightning_essence"),
        EARTH("earth_essence"), WIND("wind_essence"), SHADOW("shadow_essence"),
        LIGHT("light_essence"), ARCANE("arcane_essence"),
        // Abstract
        LIFE("life_essence"), DEATH("death_essence"), CHAOS("chaos_essence"),
        ORDER("order_essence"), TIME("time_essence"), SPACE("space_essence"),
        SOUL("soul_essence"), MIND("mind_essence");

        public final String name;

        EssenceMaterial(String name) {
            this.name = name;
        }
    }

    // ─── TOOL MATERIALS (defines which metals can make tools + stats) ───
    public enum ArcanaToolMaterial {
        //                    name              harvest  durability speed  damage enchant  tier
        ARCANITE(            "arcanite",         2,       350,      6.5f,  2.5f,  14),    // 1
        ENCHANTED_IRON(      "enchanted_iron",   2,       400,      7.0f,  2.5f,  16),    // 1
        RUNIC_STEEL(         "runic_steel",      2,       550,      7.5f,  3.0f,  18),    // 2
        MOONFORGED(          "moonforged",       3,       600,      7.5f,  3.5f,  20),    // 2
        SUNFORGED(           "sunforged",        3,       600,      7.5f,  3.5f,  20),    // 2
        VOID_ALLOY(          "void_alloy",       3,       750,      8.0f,  4.0f,  22),    // 3
        STAR_ALLOY(          "star_alloy",       3,       800,      8.5f,  4.0f,  22),    // 3
        MITHRIL(             "mithril",          4,       1200,     9.0f,  4.5f,  26),    // 4
        ADAMANTINE(          "adamantine",       4,       1500,     8.0f,  5.5f,  24),    // 4
        ETERNIUM(            "eternium",         5,       3000,     10.0f, 6.0f,  30);    // 6

        public final String name;
        public final int harvestLevel, durability, enchantability;
        public final float speed, attackDamage;

        ArcanaToolMaterial(String name, int harvestLevel, int durability, float speed,
                           float attackDamage, int enchantability) {
            this.name = name;
            this.harvestLevel = harvestLevel;
            this.durability = durability;
            this.speed = speed;
            this.attackDamage = attackDamage;
            this.enchantability = enchantability;
        }
    }

    // ─── ARMOR MATERIALS (defines which metals can make armor + stats) ───
    public enum ArcanaArmorMaterial {
        //                         name               dur  helm chest legs boots ench toughness kb
        ARCANITE(                 "arcanite",          18,  2,   5,   4,   2,    14,  0.0f,  0.0f),
        RUNIC(                    "runic_steel",       22,  3,   6,   5,   2,    18,  1.0f,  0.0f),
        MOONFORGED(               "moonforged",        25,  3,   7,   5,   3,    20,  1.0f,  0.0f),
        SUNFORGED(                "sunforged",         25,  3,   7,   5,   3,    20,  1.0f,  0.0f),
        VOID(                     "void_alloy",        30,  3,   7,   6,   3,    22,  2.0f,  0.0f),
        STAR(                     "star_alloy",        30,  3,   7,   6,   3,    22,  2.0f,  0.0f),
        MITHRIL(                  "mithril",           35,  4,   8,   7,   3,    26,  2.5f,  0.1f),
        ADAMANTINE(               "adamantine",        38,  4,   9,   7,   4,    24,  3.0f,  0.2f),
        AETHER(                   "aether_steel",      33,  3,   8,   6,   3,    28,  2.0f,  0.0f),
        DRAGONSCALE(              "dragonscale_alloy",  40,  4,   9,   8,   4,    26,  3.5f,  0.2f),
        CELESTIAL(                "celestial_alloy",   42,  4,   9,   8,   4,    28,  3.0f,  0.1f),
        SHADOW(                   "shadow_steel",      35,  3,   8,   7,   3,    24,  2.0f,  0.0f),
        CRYSTAL(                  "crystal_alloy",     30,  3,   7,   6,   3,    30,  1.5f,  0.0f),
        ETERNIUM(                 "eternium_alloy",    50,  5,  10,   9,   4,    30,  4.0f,  0.3f),
        ARCHMAGE(                 "archmage",          20,  2,   4,   3,   2,    35,  0.0f,  0.0f);

        public final String name;
        public final int durabilityMultiplier;
        public final int[] slotProtections; // helmet, chest, legs, boots
        public final int enchantability;
        public final float toughness, knockbackResistance;

        ArcanaArmorMaterial(String name, int durMult, int helm, int chest, int legs, int boots,
                            int enchant, float toughness, float knockbackRes) {
            this.name = name;
            this.durabilityMultiplier = durMult;
            this.slotProtections = new int[]{helm, chest, legs, boots};
            this.enchantability = enchant;
            this.toughness = toughness;
            this.knockbackResistance = knockbackRes;
        }
    }

    // ─── MAGIC SCHOOLS ───
    public enum MagicSchool {
        PYROMANCY("pyromancy", "fire"),
        CRYOMANCY("cryomancy", "ice"),
        ELECTROMANCY("electromancy", "lightning"),
        GEOMANCY("geomancy", "earth"),
        AEROMANCY("aeromancy", "wind"),
        UMBRAMANCY("umbramancy", "shadow"),
        LUXOMANCY("luxomancy", "light"),
        ARCANOMANCY("arcanomancy", "arcane");

        public final String name, element;

        MagicSchool(String name, String element) {
            this.name = name;
            this.element = element;
        }
    }
}
