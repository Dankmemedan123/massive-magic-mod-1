package com.arcana.data;

import com.arcana.ArcanaMod;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * All 15 custom enchantments for Arcana.
 * Each has unique behavior handled in ArcanaEnchantEvents.
 */
public class ArcanaEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, ArcanaMod.MOD_ID);

    private static final EquipmentSlot[] ARMOR_SLOTS = {
            EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET
    };
    private static final EquipmentSlot[] HAND_SLOTS = {EquipmentSlot.MAINHAND};

    // ─── Weapon enchantments ───
    public static final RegistryObject<Enchantment> MANA_SIPHON = ENCHANTMENTS.register("mana_siphon",
            () -> new SimpleEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.WEAPON, HAND_SLOTS, 3));
    public static final RegistryObject<Enchantment> LIFE_STEAL = ENCHANTMENTS.register("life_steal",
            () -> new SimpleEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.WEAPON, HAND_SLOTS, 3));
    public static final RegistryObject<Enchantment> SOUL_HARVEST = ENCHANTMENTS.register("soul_harvest",
            () -> new SimpleEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.WEAPON, HAND_SLOTS, 2));
    public static final RegistryObject<Enchantment> ELEMENTAL_FIRE = ENCHANTMENTS.register("elemental_fire",
            () -> new SimpleEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.WEAPON, HAND_SLOTS, 3));
    public static final RegistryObject<Enchantment> ELEMENTAL_ICE = ENCHANTMENTS.register("elemental_ice",
            () -> new SimpleEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.WEAPON, HAND_SLOTS, 3));
    public static final RegistryObject<Enchantment> ELEMENTAL_LIGHTNING = ENCHANTMENTS.register("elemental_lightning",
            () -> new SimpleEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.WEAPON, HAND_SLOTS, 3));
    public static final RegistryObject<Enchantment> ELEMENTAL_WIND = ENCHANTMENTS.register("elemental_wind",
            () -> new SimpleEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.WEAPON, HAND_SLOTS, 3));

    // ─── Staff enchantments ───
    public static final RegistryObject<Enchantment> SPELL_POWER = ENCHANTMENTS.register("spell_power",
            () -> new SimpleEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.WEAPON, HAND_SLOTS, 5));
    public static final RegistryObject<Enchantment> MANA_EFFICIENCY = ENCHANTMENTS.register("mana_efficiency",
            () -> new SimpleEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.WEAPON, HAND_SLOTS, 5));

    // ─── Armor enchantments ───
    public static final RegistryObject<Enchantment> WARDING = ENCHANTMENTS.register("warding",
            () -> new SimpleEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.ARMOR, ARMOR_SLOTS, 4));
    public static final RegistryObject<Enchantment> MANA_SHIELD = ENCHANTMENTS.register("mana_shield",
            () -> new SimpleEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.ARMOR, ARMOR_SLOTS, 3));
    public static final RegistryObject<Enchantment> FEATHERFALL_PLUS = ENCHANTMENTS.register("featherfall_plus",
            () -> new SimpleEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.ARMOR_FEET, new EquipmentSlot[]{EquipmentSlot.FEET}, 1));

    // ─── Tool enchantments ───
    public static final RegistryObject<Enchantment> ARCANE_HASTE = ENCHANTMENTS.register("arcane_haste",
            () -> new SimpleEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.DIGGER, HAND_SLOTS, 3));

    // ─── Universal enchantments ───
    public static final RegistryObject<Enchantment> AUTO_REPAIR = ENCHANTMENTS.register("auto_repair",
            () -> new SimpleEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.BREAKABLE, ARMOR_SLOTS, 3));

    // ─── Simple enchantment implementation ───
    private static class SimpleEnchantment extends Enchantment {
        private final int maxLvl;

        protected SimpleEnchantment(Rarity rarity, EnchantmentCategory category,
                                     EquipmentSlot[] slots, int maxLevel) {
            super(rarity, category, slots);
            this.maxLvl = maxLevel;
        }

        @Override public int getMaxLevel() { return maxLvl; }
        @Override public int getMinCost(int level) { return 10 + (level - 1) * 8; }
        @Override public int getMaxCost(int level) { return getMinCost(level) + 15; }
    }
}
