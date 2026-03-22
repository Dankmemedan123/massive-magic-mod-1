package com.arcana.item.tools;

import com.arcana.ArcanaMaterials.ArcanaToolMaterial;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public class ArcanaToolItem {

    public static Tier makeTier(ArcanaToolMaterial mat) {
        return new Tier() {
            @Override public int getUses() { return mat.durability; }
            @Override public float getSpeed() { return mat.speed; }
            @Override public float getAttackDamageBonus() { return mat.attackDamage; }
            @Override public int getLevel() { return mat.harvestLevel; }
            @Override public int getEnchantmentValue() { return mat.enchantability; }
            @Override public Ingredient getRepairIngredient() { return Ingredient.EMPTY; }
        };
    }

    public static SwordItem createSword(ArcanaToolMaterial mat) {
        return new SwordItem(makeTier(mat), 3, -2.4f, new Item.Properties());
    }

    public static PickaxeItem createPickaxe(ArcanaToolMaterial mat) {
        return new PickaxeItem(makeTier(mat), 1, -2.8f, new Item.Properties());
    }

    public static AxeItem createAxe(ArcanaToolMaterial mat) {
        return new AxeItem(makeTier(mat), 5.0f, -3.0f, new Item.Properties());
    }

    public static ShovelItem createShovel(ArcanaToolMaterial mat) {
        return new ShovelItem(makeTier(mat), 1.5f, -3.0f, new Item.Properties());
    }

    public static HoeItem createHoe(ArcanaToolMaterial mat) {
        return new HoeItem(makeTier(mat), -3, 0.0f, new Item.Properties());
    }
}
