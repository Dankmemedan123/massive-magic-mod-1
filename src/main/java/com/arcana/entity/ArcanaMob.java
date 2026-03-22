package com.arcana.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * Base class for all Arcana mod mobs (hostile, passive, and NPCs).
 * Stats and behavior are configured per-entity-type using the registry name.
 *
 * Each mob type gets unique stats via getMobStats() lookup.
 * AI goals are added based on mob category (melee, ranged, passive, NPC).
 */
public class ArcanaMob extends Monster {

    public ArcanaMob(EntityType<? extends ArcanaMob> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        String name = this.getType().getDescriptionId();

        // Default hostile AI
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));

        // NPC types don't target players
        if (name.contains("merchant") || name.contains("guild") || name.contains("alchemist")
                || name.contains("runesmith") || name.contains("lorekeeper") || name.contains("wandering_mage")) {
            this.targetSelector.removeAllGoals(g -> true);
            // NPCs use wandering + look at player goals only
        }

        // Passive types don't target players
        if (name.contains("butterfly") || name.contains("deer") || name.contains("fox")
                || name.contains("jellyfish") || name.contains("bird") || name.contains("pixie")
                || name.contains("wisp") && !name.contains("mana_wisp")) {
            this.targetSelector.removeAllGoals(g -> true);
        }
    }

    /**
     * Mob stats lookup by entity type name.
     * Returns: maxHealth, attackDamage, speed, armor, knockbackResistance
     */
    public static AttributeSupplier.Builder createMobAttributes(String typeName) {
        MobStats stats = getMobStats(typeName);
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, stats.health)
                .add(Attributes.ATTACK_DAMAGE, stats.damage)
                .add(Attributes.MOVEMENT_SPEED, stats.speed)
                .add(Attributes.ARMOR, stats.armor)
                .add(Attributes.KNOCKBACK_RESISTANCE, stats.kbResist)
                .add(Attributes.FOLLOW_RANGE, stats.followRange);
    }

    /** Default attribute supplier for registration */
    public static AttributeSupplier.Builder createDefaultAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.ATTACK_DAMAGE, 4.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ARMOR, 0.0D)
                .add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    record MobStats(double health, double damage, double speed, double armor, double kbResist, double followRange) {}

    private static MobStats getMobStats(String name) {
        // ─── Overworld hostile ───
        if (name.contains("mana_wisp"))        return new MobStats(15,  3,  0.35, 0,   0,    24);
        if (name.contains("crystal_golem") && !name.contains("king"))
                                               return new MobStats(50,  8,  0.2,  8,   0.5,  20);
        if (name.contains("shadow_wraith"))    return new MobStats(25,  5,  0.4,  0,   0,    32);
        if (name.contains("arcane_skeleton"))  return new MobStats(22,  4,  0.25, 2,   0,    32);
        if (name.contains("enchanted_spider")) return new MobStats(20,  4,  0.35, 0,   0,    24);
        if (name.contains("rune_guardian"))     return new MobStats(60,  10, 0.2,  12,  0.6,  16);
        if (name.contains("elemental_sprite")) return new MobStats(12,  4,  0.3,  0,   0,    20);
        if (name.contains("corrupted_mage") && !name.contains("boss"))
                                               return new MobStats(35,  6,  0.25, 4,   0,    32);
        if (name.contains("void_crawler"))     return new MobStats(18,  5,  0.3,  2,   0,    16);
        if (name.contains("dark_knight"))      return new MobStats(40,  7,  0.25, 10,  0.3,  24);

        // ─── Arcane Realm ───
        if (name.contains("arcane_sentinel"))  return new MobStats(55,  9,  0.22, 10,  0.4,  32);
        if (name.contains("crystal_beast"))    return new MobStats(40,  10, 0.3,  6,   0.2,  20);
        if (name.contains("mana_elemental"))   return new MobStats(45,  7,  0.25, 4,   0,    24);
        if (name.contains("arcane_phantom"))   return new MobStats(30,  6,  0.35, 0,   0,    40);
        if (name.contains("golem_soldier"))    return new MobStats(70,  12, 0.2,  14,  0.7,  20);
        if (name.contains("spell_weaver"))     return new MobStats(35,  8,  0.23, 2,   0,    40);
        if (name.contains("prism_spider"))     return new MobStats(25,  5,  0.35, 2,   0,    24);
        if (name.contains("aether_knight"))    return new MobStats(45,  9,  0.28, 8,   0.2,  32);

        // ─── Shadow Depths ───
        if (name.contains("shadow_stalker"))   return new MobStats(30,  8,  0.4,  2,   0,    40);
        if (name.contains("bone_revenant"))    return new MobStats(35,  6,  0.25, 6,   0.2,  24);
        if (name.contains("void_leech"))       return new MobStats(20,  4,  0.3,  0,   0,    20);
        if (name.contains("fungal_zombie"))    return new MobStats(28,  5,  0.2,  4,   0.1,  20);
        if (name.contains("shadow_hound"))     return new MobStats(20,  6,  0.45, 0,   0,    32);
        if (name.contains("lich_minion"))      return new MobStats(25,  5,  0.28, 4,   0,    24);
        if (name.contains("void_tentacle"))    return new MobStats(40,  7,  0,    8,   1.0,  16);

        // ─── Celestial Spire ───
        if (name.contains("star_guardian"))     return new MobStats(50,  8,  0.25, 8,   0.2,  40);
        if (name.contains("crystal_dragon"))   return new MobStats(150, 15, 0.3,  10,  0.5,  48);
        if (name.contains("celestial_golem"))  return new MobStats(200, 20, 0.15, 16,  0.8,  24);
        if (name.contains("light_wisp"))       return new MobStats(25,  2,  0.3,  0,   0,    32);
        if (name.contains("eternium_construct"))return new MobStats(120, 16, 0.22, 14,  0.6,  32);

        // ─── Passive / Neutral ───
        if (name.contains("butterfly"))        return new MobStats(4,   0,  0.2,  0,   0,    8);
        if (name.contains("deer"))             return new MobStats(15,  0,  0.3,  0,   0,    12);
        if (name.contains("fox"))              return new MobStats(10,  2,  0.35, 0,   0,    16);
        if (name.contains("jellyfish"))        return new MobStats(8,   0,  0.15, 0,   0,    8);
        if (name.contains("cat"))              return new MobStats(12,  3,  0.35, 0,   0,    16);
        if (name.contains("bird"))             return new MobStats(6,   0,  0.3,  0,   0,    12);
        if (name.contains("eagle"))            return new MobStats(20,  4,  0.35, 0,   0,    24);
        if (name.contains("pixie"))            return new MobStats(8,   0,  0.25, 0,   0,    12);

        // ─── NPCs ───
        if (name.contains("wandering") || name.contains("guild") || name.contains("alchemist")
                || name.contains("runesmith") || name.contains("merchant") || name.contains("lorekeeper"))
                                               return new MobStats(40,  0,  0.23, 6,   0,    16);

        // Default
        return new MobStats(20, 4, 0.25, 0, 0, 32);
    }
}
