package com.arcana;

import com.arcana.ArcanaMaterials.*;
import com.arcana.item.tools.ArcanaToolItem;
import com.arcana.item.armor.ArcanaArmorItem;
import com.arcana.item.weapons.SpecialWeaponItem;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;

/**
 * Registers ALL items for the Arcana mod.
 * Uses enum-driven loops to register hundreds of items from compact code.
 *
 * Item count breakdown:
 *   12 raw ores + 12 ingots + 12 nuggets = 36 ore items
 *   18 alloy ingots + 18 alloy nuggets = 36 alloy items
 *   10 gems = 10 gem items
 *   16 essences = 16 essence items
 *   10 materials × 5 tool types = 50 tools
 *   15 armor sets × 4 pieces = 60 armor items
 *   12 special weapons = 12 weapon items
 *   8 staffs = 8 staff items
 *   Misc crafting materials = ~20 items
 *   TOTAL: ~248 items
 */
public class ArcanaItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ArcanaMod.MOD_ID);

    // Storage maps for lookup by material name
    public static final Map<String, RegistryObject<Item>> RAW_ORES = new LinkedHashMap<>();
    public static final Map<String, RegistryObject<Item>> INGOTS = new LinkedHashMap<>();
    public static final Map<String, RegistryObject<Item>> NUGGETS = new LinkedHashMap<>();
    public static final Map<String, RegistryObject<Item>> GEMS = new LinkedHashMap<>();
    public static final Map<String, RegistryObject<Item>> ESSENCES = new LinkedHashMap<>();
    public static final Map<String, RegistryObject<Item>> TOOLS = new LinkedHashMap<>();
    public static final Map<String, RegistryObject<Item>> ARMOR = new LinkedHashMap<>();
    public static final Map<String, RegistryObject<Item>> WEAPONS = new LinkedHashMap<>();
    public static final Map<String, RegistryObject<Item>> STAFFS = new LinkedHashMap<>();

    static {
        // ─── ORE ITEMS: raw ore + ingot + nugget for each ore ───
        for (OreMaterial mat : OreMaterial.values()) {
            RAW_ORES.put(mat.name, ITEMS.register("raw_" + mat.name,
                    () -> new Item(new Item.Properties())));
            INGOTS.put(mat.name, ITEMS.register(mat.name + "_ingot",
                    () -> new Item(new Item.Properties())));
            NUGGETS.put(mat.name, ITEMS.register(mat.name + "_nugget",
                    () -> new Item(new Item.Properties())));
        }

        // ─── ALLOY ITEMS: ingot + nugget for each alloy ───
        for (AlloyMaterial mat : AlloyMaterial.values()) {
            INGOTS.put(mat.name, ITEMS.register(mat.name + "_ingot",
                    () -> new Item(new Item.Properties())));
            NUGGETS.put(mat.name, ITEMS.register(mat.name + "_nugget",
                    () -> new Item(new Item.Properties())));
        }

        // ─── GEM ITEMS ───
        for (GemMaterial mat : GemMaterial.values()) {
            GEMS.put(mat.name, ITEMS.register(mat.name,
                    () -> new Item(new Item.Properties())));
        }

        // ─── ESSENCE ITEMS ───
        for (EssenceMaterial mat : EssenceMaterial.values()) {
            ESSENCES.put(mat.name, ITEMS.register(mat.name,
                    () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON))));
        }

        // ─── TOOLS: sword + pickaxe + axe + shovel + hoe for each tool material ───
        for (ArcanaToolMaterial mat : ArcanaToolMaterial.values()) {
            String n = mat.name;
            TOOLS.put(n + "_sword", ITEMS.register(n + "_sword",
                    () -> ArcanaToolItem.createSword(mat)));
            TOOLS.put(n + "_pickaxe", ITEMS.register(n + "_pickaxe",
                    () -> ArcanaToolItem.createPickaxe(mat)));
            TOOLS.put(n + "_axe", ITEMS.register(n + "_axe",
                    () -> ArcanaToolItem.createAxe(mat)));
            TOOLS.put(n + "_shovel", ITEMS.register(n + "_shovel",
                    () -> ArcanaToolItem.createShovel(mat)));
            TOOLS.put(n + "_hoe", ITEMS.register(n + "_hoe",
                    () -> ArcanaToolItem.createHoe(mat)));
        }

        // ─── ARMOR: helmet + chestplate + leggings + boots for each armor material ───
        for (ArcanaArmorMaterial mat : ArcanaArmorMaterial.values()) {
            String n = mat.name;
            ARMOR.put(n + "_helmet", ITEMS.register(n + "_helmet",
                    () -> ArcanaArmorItem.create(mat, ArmorItem.Type.HELMET)));
            ARMOR.put(n + "_chestplate", ITEMS.register(n + "_chestplate",
                    () -> ArcanaArmorItem.create(mat, ArmorItem.Type.CHESTPLATE)));
            ARMOR.put(n + "_leggings", ITEMS.register(n + "_leggings",
                    () -> ArcanaArmorItem.create(mat, ArmorItem.Type.LEGGINGS)));
            ARMOR.put(n + "_boots", ITEMS.register(n + "_boots",
                    () -> ArcanaArmorItem.create(mat, ArmorItem.Type.BOOTS)));
        }

        // ─── SPECIAL WEAPONS ───
        registerWeapon("flame_tongue", Rarity.RARE);
        registerWeapon("frost_brand", Rarity.RARE);
        registerWeapon("thunderstrike", Rarity.RARE);
        registerWeapon("terra_breaker", Rarity.RARE);
        registerWeapon("gale_force", Rarity.RARE);
        registerWeapon("shadow_fang", Rarity.RARE);
        registerWeapon("sunbeam_blade", Rarity.RARE);
        registerWeapon("arcane_saber", Rarity.EPIC);
        registerWeapon("dragon_slayer", Rarity.EPIC);
        registerWeapon("void_reaper", Rarity.EPIC);
        registerWeapon("celestial_bow", Rarity.EPIC);
        registerWeapon("eternium_blade", Rarity.EPIC);

        // ─── STAFFS ───
        for (MagicSchool school : MagicSchool.values()) {
            STAFFS.put(school.name + "_staff", ITEMS.register(school.name + "_staff",
                    () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE))));
        }

        // ─── MISC CRAFTING MATERIALS ───
        ITEMS.register("arcane_dust", () -> new Item(new Item.Properties()));
        ITEMS.register("mana_dust", () -> new Item(new Item.Properties()));
        ITEMS.register("ritual_chalk_white", () -> new Item(new Item.Properties()));
        ITEMS.register("ritual_chalk_red", () -> new Item(new Item.Properties()));
        ITEMS.register("ritual_chalk_blue", () -> new Item(new Item.Properties()));
        ITEMS.register("ritual_chalk_gold", () -> new Item(new Item.Properties()));
        ITEMS.register("dragon_scale", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
        ITEMS.register("dragon_soul", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
        ITEMS.register("void_heart", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
        ITEMS.register("shadow_core", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
        ITEMS.register("arcane_core", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
        ITEMS.register("crystal_heart", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
        ITEMS.register("archon_heart", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
        ITEMS.register("lich_crown", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
        ITEMS.register("drake_scale", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
        ITEMS.register("wyrm_scale", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
        ITEMS.register("fire_heart", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
        ITEMS.register("frost_heart", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
        ITEMS.register("star_feather", () -> new Item(new Item.Properties()));
        ITEMS.register("spell_page_blank", () -> new Item(new Item.Properties()));
        ITEMS.register("arcane_tome", () -> new Item(new Item.Properties().stacksTo(1)));

        // ─── ITEMS FROM INTEGRATION AUDIT — previously missing ───

        // Consumables
        ITEMS.register("mana_berries", () -> new Item(new Item.Properties()
                .food(new net.minecraft.world.food.FoodProperties.Builder()
                        .nutrition(2).saturationMod(0.3f).alwaysEat().fast().build())));

        // Boss summon keys (one per boss, dropped by respective minion types)
        ITEMS.register("crystal_key", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1)));
        ITEMS.register("corrupted_tome", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1)));
        ITEMS.register("shadow_sigil", () -> new Item(new Item.Properties().rarity(Rarity.RARE).stacksTo(1)));
        ITEMS.register("archon_rune", () -> new Item(new Item.Properties().rarity(Rarity.RARE).stacksTo(1)));
        ITEMS.register("drake_fang", () -> new Item(new Item.Properties().rarity(Rarity.RARE).stacksTo(1)));
        ITEMS.register("wyrm_tooth", () -> new Item(new Item.Properties().rarity(Rarity.RARE).stacksTo(1)));
        ITEMS.register("dragon_scale_key", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));
        ITEMS.register("void_fragment", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));

        // Crafting components for hybrid staffs
        ITEMS.register("fusion_catalyst", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));

        // Soul binding
        ITEMS.register("soul_staff", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).durability(500)));
        ITEMS.register("empty_soul_gem", () -> new Item(new Item.Properties()));
        ITEMS.register("filled_soul_gem", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1)));

        // Functional guidebook
        ITEMS.register("arcane_guidebook", () -> new Item(new Item.Properties().stacksTo(1)));

        // Hybrid staffs (require mastery 50 in both schools + fusion catalyst)
        STAFFS.put("tempest_staff", ITEMS.register("tempest_staff",
                () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC))));
        STAFFS.put("glacier_staff", ITEMS.register("glacier_staff",
                () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC))));
        STAFFS.put("eclipse_staff", ITEMS.register("eclipse_staff",
                () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC))));
        STAFFS.put("archmage_staff", ITEMS.register("archmage_staff",
                () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC))));

        // Wands (12 — one per tool material, weaker than staffs but general purpose)
        for (ArcanaMaterials.ArcanaToolMaterial mat : ArcanaMaterials.ArcanaToolMaterial.values()) {
            String wn = mat.name + "_wand";
            ITEMS.register(wn, () -> new Item(new Item.Properties().stacksTo(1).durability(mat.durability / 2)));
        }

        // ─── ITEMS FROM PLAYER WALKTHROUGH AUDIT (Gaps 9-20) ───

        // Mastery respec (Gap 16)
        ITEMS.register("tome_of_reallocation", () -> new Item(
                new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));

        // Farmable mana source (Gap 15)
        ITEMS.register("mana_berry_seeds", () -> new Item(new Item.Properties()));

        // Spell scrolls — one per spell across all 8 schools (40 total)
        // These are CONSUMABLE (right-click to learn) or used at Inscription Table to copy
        String[] allSpells = {
            // Pyromancy
            "scroll_fireball", "scroll_fire_wall", "scroll_inferno", "scroll_meteor_strike", "scroll_phoenix_form",
            // Cryomancy
            "scroll_frost_bolt", "scroll_ice_shield", "scroll_blizzard", "scroll_glacial_tomb", "scroll_absolute_zero",
            // Electromancy
            "scroll_spark", "scroll_chain_lightning", "scroll_thunderclap", "scroll_storm_call", "scroll_zeus_wrath",
            // Geomancy
            "scroll_stone_skin", "scroll_earthquake", "scroll_mud_trap", "scroll_crystal_lance", "scroll_golem_summon",
            // Aeromancy
            "scroll_gust", "scroll_levitate", "scroll_tornado", "scroll_wind_walk", "scroll_hurricane",
            // Umbramancy
            "scroll_shadow_step", "scroll_dark_pulse", "scroll_fear", "scroll_shadow_clone", "scroll_void_rift",
            // Luxomancy
            "scroll_heal", "scroll_purify", "scroll_sun_beam", "scroll_holy_shield", "scroll_divine_judgment",
            // Arcanomancy
            "scroll_mana_bolt", "scroll_dispel", "scroll_arcane_shield", "scroll_telekinesis", "scroll_time_slow"
        };
        for (String scroll : allSpells) {
            ITEMS.register(scroll, () -> new Item(new Item.Properties().stacksTo(16).rarity(Rarity.UNCOMMON)));
        }

        // Accessory items — baubles (Gap 18: equip via right-click into capability slots)
        ITEMS.register("ring_of_mana", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
        ITEMS.register("ring_of_fire", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
        ITEMS.register("ring_of_ice", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
        ITEMS.register("ring_of_lightning", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
        ITEMS.register("ring_of_earth", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
        ITEMS.register("ring_of_wind", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
        ITEMS.register("ring_of_shadow", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
        ITEMS.register("ring_of_light", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
        ITEMS.register("ring_of_arcane", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
        ITEMS.register("amulet_of_protection", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
        ITEMS.register("amulet_of_life", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));
        ITEMS.register("amulet_of_teleportation", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
        ITEMS.register("belt_of_holding", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
        ITEMS.register("belt_of_speed", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
        ITEMS.register("belt_of_strength", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
        ITEMS.register("crown_of_the_archmage", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));
        ITEMS.register("void_pendant", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));

        // Lore books (found in structures — written book items)
        ITEMS.register("lore_book_crystal_king", () -> new Item(new Item.Properties().stacksTo(1)));
        ITEMS.register("lore_book_shadow_lich", () -> new Item(new Item.Properties().stacksTo(1)));
        ITEMS.register("lore_book_archon", () -> new Item(new Item.Properties().stacksTo(1)));
        ITEMS.register("lore_book_dragon", () -> new Item(new Item.Properties().stacksTo(1)));
        ITEMS.register("lore_book_void", () -> new Item(new Item.Properties().stacksTo(1)));
        ITEMS.register("lore_book_ancient_mages", () -> new Item(new Item.Properties().stacksTo(1)));
        ITEMS.register("lore_book_dimensions", () -> new Item(new Item.Properties().stacksTo(1)));
        ITEMS.register("lore_book_spell_weaving", () -> new Item(new Item.Properties().stacksTo(1)));
    }

    private static void registerWeapon(String name, Rarity rarity) {
        WEAPONS.put(name, ITEMS.register(name,
                () -> new SpecialWeaponItem(new Item.Properties().stacksTo(1).rarity(rarity)
                        .durability(2000))));
    }
}
