package com.arcana.item.weapons;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

/**
 * Base class for special named weapons (Flame Tongue, Frost Brand, etc.)
 * Uses a high-tier base with 8 attack damage. Special abilities will be
 * added via onHitEntity / onUse overrides in later phases.
 */
public class SpecialWeaponItem extends SwordItem {
    private static final Tier SPECIAL_TIER = new Tier() {
        @Override public int getUses() { return 2000; }
        @Override public float getSpeed() { return 9.0f; }
        @Override public float getAttackDamageBonus() { return 5.0f; }
        @Override public int getLevel() { return 4; }
        @Override public int getEnchantmentValue() { return 22; }
        @Override public Ingredient getRepairIngredient() { return Ingredient.EMPTY; }
    };

    public SpecialWeaponItem(Item.Properties properties) {
        super(SPECIAL_TIER, 3, -2.4f, properties);
    }
}
