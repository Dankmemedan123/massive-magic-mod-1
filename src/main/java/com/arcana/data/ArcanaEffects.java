package com.arcana.data;

import com.arcana.ArcanaMod;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * All 12 custom status effects for Arcana.
 */
public class ArcanaEffects {
    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, ArcanaMod.MOD_ID);

    // ─── Harmful effects ───
    public static final RegistryObject<MobEffect> MANA_BURN = EFFECTS.register("mana_burn",
            () -> new ArcanaMobEffect(MobEffectCategory.HARMFUL, 0xFF4444));
    public static final RegistryObject<MobEffect> CORRUPTION = EFFECTS.register("corruption",
            () -> new ArcanaMobEffect(MobEffectCategory.HARMFUL, 0x442266)
                    .addAttributeModifier(Attributes.ATTACK_DAMAGE, "a1b2c3d4-1111-4000-8000-000000000001",
                            -2.0, AttributeModifier.Operation.ADDITION)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED, "a1b2c3d4-1111-4000-8000-000000000002",
                            -0.05, AttributeModifier.Operation.ADDITION));
    public static final RegistryObject<MobEffect> SOUL_LINK = EFFECTS.register("soul_link",
            () -> new ArcanaMobEffect(MobEffectCategory.HARMFUL, 0x88CCCC));

    // ─── Beneficial effects ───
    public static final RegistryObject<MobEffect> MANA_REGEN = EFFECTS.register("mana_regen",
            () -> new ArcanaMobEffect(MobEffectCategory.BENEFICIAL, 0x8844FF));
    public static final RegistryObject<MobEffect> ARCANE_SHIELD_EFFECT = EFFECTS.register("arcane_shield_effect",
            () -> new ArcanaMobEffect(MobEffectCategory.BENEFICIAL, 0xAA88FF));
    public static final RegistryObject<MobEffect> SHADOW_VEIL = EFFECTS.register("shadow_veil",
            () -> new ArcanaMobEffect(MobEffectCategory.BENEFICIAL, 0x332244));
    public static final RegistryObject<MobEffect> STONE_SKIN_EFFECT = EFFECTS.register("stone_skin_effect",
            () -> new ArcanaMobEffect(MobEffectCategory.BENEFICIAL, 0x888888)
                    .addAttributeModifier(Attributes.ARMOR, "a1b2c3d4-2222-4000-8000-000000000003",
                            4.0, AttributeModifier.Operation.ADDITION)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED, "a1b2c3d4-2222-4000-8000-000000000004",
                            -0.03, AttributeModifier.Operation.ADDITION));
    public static final RegistryObject<MobEffect> WIND_SPEED = EFFECTS.register("wind_speed",
            () -> new ArcanaMobEffect(MobEffectCategory.BENEFICIAL, 0xAADDBB)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED, "a1b2c3d4-3333-4000-8000-000000000005",
                            0.06, AttributeModifier.Operation.ADDITION));
    public static final RegistryObject<MobEffect> TIME_DILATION = EFFECTS.register("time_dilation",
            () -> new ArcanaMobEffect(MobEffectCategory.BENEFICIAL, 0xCCAA44));

    // ─── Elemental resistances ───
    public static final RegistryObject<MobEffect> FIRE_RESISTANCE_PLUS = EFFECTS.register("fire_resistance_plus",
            () -> new ArcanaMobEffect(MobEffectCategory.BENEFICIAL, 0xFF6633));
    public static final RegistryObject<MobEffect> ICE_RESISTANCE = EFFECTS.register("ice_resistance",
            () -> new ArcanaMobEffect(MobEffectCategory.BENEFICIAL, 0x88CCFF));
    public static final RegistryObject<MobEffect> LIGHTNING_RESISTANCE = EFFECTS.register("lightning_resistance",
            () -> new ArcanaMobEffect(MobEffectCategory.BENEFICIAL, 0xFFEE44));

    // ─── Base effect class ───
    private static class ArcanaMobEffect extends MobEffect {
        protected ArcanaMobEffect(MobEffectCategory category, int color) {
            super(category, color);
        }

        @Override
        public void applyEffectTick(LivingEntity entity, int amplifier) {
            // Mana Burn: drain 2 mana/sec per amplifier level
            // (Handled in ManaEvents tick handler by checking for this effect)

            // Mana Regen: boost mana regen by 50% per level
            // (Handled in ManaEvents tick handler)

            // Corruption: damage + weakness already applied via attribute modifiers
            // Additional: wither-like damage every 2 seconds
            if (this == CORRUPTION.get() && entity.tickCount % 40 == 0) {
                entity.hurt(entity.damageSources().wither(), 1 + amplifier);
            }
        }

        @Override
        public boolean isDurationEffectTick(int duration, int amplifier) {
            return true; // Tick every tick for mana effects
        }

        public ArcanaMobEffect addAttributeModifier(net.minecraft.world.entity.ai.attributes.Attribute attr,
                                                      String uuid, double amount,
                                                      AttributeModifier.Operation op) {
            super.addAttributeModifier(attr, uuid, amount, op);
            return this;
        }
    }
}
