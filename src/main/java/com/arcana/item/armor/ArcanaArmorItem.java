package com.arcana.item.armor;

import com.arcana.ArcanaMod;
import com.arcana.ArcanaMaterials.ArcanaArmorMaterial;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;

public class ArcanaArmorItem {

    public static ArmorItem create(ArcanaArmorMaterial mat, ArmorItem.Type type) {
        return new ArmorItem(makeArmorMaterial(mat), type, new Item.Properties());
    }

    private static ArmorMaterial makeArmorMaterial(ArcanaArmorMaterial mat) {
        // Base durabilities per slot type (vanilla pattern)
        int[] baseDurability = {11, 16, 15, 13}; // boots, leggings, chestplate, helmet

        return new ArmorMaterial() {
            @Override
            public int getDurabilityForType(ArmorItem.Type type) {
                return baseDurability[type.getSlot().getIndex()] * mat.durabilityMultiplier;
            }

            @Override
            public int getDefenseForType(ArmorItem.Type type) {
                return switch (type) {
                    case HELMET -> mat.slotProtections[0];
                    case CHESTPLATE -> mat.slotProtections[1];
                    case LEGGINGS -> mat.slotProtections[2];
                    case BOOTS -> mat.slotProtections[3];
                };
            }

            @Override
            public int getEnchantmentValue() { return mat.enchantability; }

            @Override
            public SoundEvent getEquipSound() { return SoundEvents.ARMOR_EQUIP_DIAMOND; }

            @Override
            public Ingredient getRepairIngredient() { return Ingredient.EMPTY; }

            @Override
            public String getName() { return ArcanaMod.MOD_ID + ":" + mat.name; }

            @Override
            public float getToughness() { return mat.toughness; }

            @Override
            public float getKnockbackResistance() { return mat.knockbackResistance; }
        };
    }
}
