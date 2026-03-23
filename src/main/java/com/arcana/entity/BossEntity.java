package com.arcana.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * Boss entity with multi-phase fights, unique mechanics per boss type.
 *
 * Boss types (determined by EntityType registry name):
 * 1. Crystal Golem King — 200HP, melee + crystal rain, drops Crystal Heart
 * 2. Corrupted Mage — 300HP, cycles 4 spell schools, drops Corrupted Staff
 * 3. Shadow Lich — 500HP, summons minions + life drain + teleport
 * 4. The Archon — 500HP, mana beam + shield phases + constructs
 * 5. Infernal Drake — 400HP, flight + fire breath + dive bomb
 * 6. Frost Wyrm — 400HP, ice breath + freeze AoE
 * 7. Arcane Dragon — 1000HP, 3 phases ALL elements
 * 8. Void Incarnate — 2000HP, secret boss, reality-warping attacks
 */
public class BossEntity extends Monster {

    private final ServerBossEvent bossBar;
    private int phase = 1;
    private int attackCooldown = 0;
    private int specialCooldown = 0;

    public BossEntity(EntityType<? extends BossEntity> type, Level level) {
        super(type, level);
        this.bossBar = new ServerBossEvent(
                this.getDisplayName(),
                getBossBarColor(),
                BossEvent.BossBarOverlay.PROGRESS
        );
        this.xpReward = 500;
        this.setPersistenceRequired();
    }

    public static AttributeSupplier.Builder createBossAttributes(String name) {
        BossConfig cfg = getBossConfig(name);
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, cfg.health)
                .add(Attributes.ATTACK_DAMAGE, cfg.damage)
                .add(Attributes.MOVEMENT_SPEED, cfg.speed)
                .add(Attributes.ARMOR, cfg.armor)
                .add(Attributes.KNOCKBACK_RESISTANCE, cfg.kbResist)
                .add(Attributes.FOLLOW_RANGE, 64.0D);
    }

    public static AttributeSupplier.Builder createDefaultBossAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 200.0D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ARMOR, 8.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.FOLLOW_RANGE, 64.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 16.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.level().isClientSide) return;

        ServerLevel sLevel = (ServerLevel) this.level();
        String name = this.getType().getDescriptionId();

        // Update boss bar
        this.bossBar.setProgress(this.getHealth() / this.getMaxHealth());

        // Phase transitions
        float healthPercent = this.getHealth() / this.getMaxHealth();
        int newPhase = healthPercent > 0.66f ? 1 : (healthPercent > 0.33f ? 2 : 3);
        if (newPhase != phase) {
            phase = newPhase;
            onPhaseTransition(sLevel, name);
        }

        // Attack cooldown
        if (attackCooldown > 0) attackCooldown--;
        if (specialCooldown > 0) specialCooldown--;

        // Boss-specific AI each tick
        if (this.getTarget() != null && this.getTarget().isAlive()) {
            executeBossAI(sLevel, name);
        }
    }

    private void onPhaseTransition(ServerLevel level, String name) {
        // Visual feedback on phase change
        level.sendParticles(ParticleTypes.EXPLOSION_EMITTER,
                this.getX(), this.getY() + 1, this.getZ(), 3, 1, 1, 1, 0);
        this.playSound(SoundEvents.ENDER_DRAGON_GROWL, 2.0f, 0.8f);

        // Phase-specific buffs
        if (phase == 2) {
            this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, Integer.MAX_VALUE, 0));
            this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, Integer.MAX_VALUE, 0));
            this.bossBar.setColor(BossEvent.BossBarColor.YELLOW);
        } else if (phase == 3) {
            this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, Integer.MAX_VALUE, 1));
            this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, Integer.MAX_VALUE, 1));
            this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0));
            this.bossBar.setColor(BossEvent.BossBarColor.RED);
        }

        // Boss-specific phase effects
        if (name.contains("crystal_golem_king") && phase >= 2) {
            // Crystal rain AoE
            crystalRain(level);
        }
        if (name.contains("shadow_lich") && phase >= 2) {
            // Summon lich minions
            summonMinions(level, 3 + phase);
        }
        if (name.contains("arcane_dragon") && phase == 3) {
            // Dragon enrage — all elements
            this.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, Integer.MAX_VALUE, 0));
        }
    }

    private void executeBossAI(ServerLevel level, String name) {
        LivingEntity target = this.getTarget();
        double dist = this.distanceTo(target);

        // ─── Crystal Golem King: melee + crystal rain every 5s ───
        if (name.contains("crystal_golem_king")) {
            if (attackCooldown <= 0 && dist < 20) {
                crystalRain(level);
                attackCooldown = 100; // 5 seconds
            }
        }

        // ─── Corrupted Mage: cycles spell schools ───
        if (name.contains("corrupted_mage_boss")) {
            if (attackCooldown <= 0 && dist < 30) {
                int school = (this.tickCount / 200) % 4; // Switch school every 10s
                castBossSpell(level, target, school);
                attackCooldown = 40; // 2 seconds
            }
        }

        // ─── Shadow Lich: life drain + teleport + minions ───
        if (name.contains("shadow_lich")) {
            if (attackCooldown <= 0 && dist < 25) {
                // Life drain
                target.hurt(this.damageSources().magic(), 4);
                this.heal(4);
                level.sendParticles(ParticleTypes.DAMAGE_INDICATOR,
                        target.getX(), target.getY()+1, target.getZ(), 5, 0.3, 0.5, 0.3, 0);
                attackCooldown = 30;
            }
            if (specialCooldown <= 0) {
                // Teleport behind target
                double tx = target.getX() - target.getLookAngle().x * 3;
                double tz = target.getZ() - target.getLookAngle().z * 3;
                this.teleportTo(tx, target.getY(), tz);
                level.sendParticles(ParticleTypes.LARGE_SMOKE, tx, target.getY()+1, tz, 20, 0.5, 1, 0.5, 0.02);
                specialCooldown = 120; // 6 seconds
            }
            // Summon minions periodically
            if (this.tickCount % 400 == 0 && phase >= 2) {
                summonMinions(level, 2);
            }
        }

        // ─── The Archon: mana beam + shield + constructs ───
        if (name.contains("the_archon")) {
            if (attackCooldown <= 0 && dist < 30) {
                // Mana beam — line damage
                for (int i = 1; i <= 15; i++) {
                    var direction = target.position().subtract(this.position()).normalize();
                    var check = this.getEyePosition().add(direction.scale(i));
                    level.sendParticles(ParticleTypes.ENCHANT, check.x, check.y, check.z, 3, 0.1, 0.1, 0.1, 0.5);
                    if (target.distanceToSqr(check) < 2) {
                        target.hurt(this.damageSources().magic(), 8);
                    }
                }
                attackCooldown = 60;
            }
            // Shield phase — invulnerable + constructs in phase 2+
            if (phase >= 2 && this.tickCount % 600 == 0) {
                this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 4));
                summonMinions(level, 2);
            }
        }

        // ─── Infernal Drake: fire breath + dive bomb ───
        if (name.contains("infernal_drake")) {
            if (attackCooldown <= 0 && dist < 20) {
                // Fire breath cone
                fireBreath(level, target, 10);
                attackCooldown = 80;
            }
        }

        // ─── Frost Wyrm: ice breath + freeze AoE ───
        if (name.contains("frost_wyrm")) {
            if (attackCooldown <= 0 && dist < 20) {
                // Apply slowness + damage
                target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 2));
                target.hurt(this.damageSources().freeze(), 8);
                level.sendParticles(ParticleTypes.SNOWFLAKE,
                        target.getX(), target.getY()+1, target.getZ(), 30, 1, 1, 1, 0.05);
                attackCooldown = 60;
            }
            if (specialCooldown <= 0 && phase >= 2) {
                // Freeze AoE — all entities in range
                for (Entity e : level.getEntities(this, this.getBoundingBox().inflate(8))) {
                    if (e instanceof LivingEntity le && !(e instanceof BossEntity)) {
                        le.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 3));
                        le.hurt(this.damageSources().freeze(), 4);
                    }
                }
                specialCooldown = 200;
            }
        }

        // ─── Arcane Dragon: ALL ELEMENTS, 3 phases ───
        if (name.contains("arcane_dragon")) {
            if (attackCooldown <= 0) {
                int element = (this.tickCount / 100) % 4;
                switch (element) {
                    case 0 -> { // Fire
                        fireBreath(level, target, 15);
                        target.setSecondsOnFire(5);
                    }
                    case 1 -> { // Ice
                        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 3));
                        target.hurt(this.damageSources().freeze(), 10);
                    }
                    case 2 -> { // Lightning
                        LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(level);
                        if (bolt != null) {
                            bolt.moveTo(target.getX(), target.getY(), target.getZ());
                            level.addFreshEntity(bolt);
                        }
                    }
                    case 3 -> { // Shadow
                        target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60, 0));
                        target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 1));
                    }
                }
                attackCooldown = 60 - (phase * 10); // Faster in later phases
            }
            // Phase 3: arena-wide attack
            if (phase == 3 && specialCooldown <= 0) {
                for (Entity e : level.getEntities(this, this.getBoundingBox().inflate(20))) {
                    if (e instanceof LivingEntity le && !(e instanceof BossEntity)) {
                        le.hurt(this.damageSources().magic(), 15);
                    }
                }
                level.sendParticles(ParticleTypes.EXPLOSION_EMITTER,
                        this.getX(), this.getY(), this.getZ(), 5, 5, 2, 5, 0);
                specialCooldown = 300;
            }
        }

        // ─── Void Incarnate: reality-warping attacks ───
        if (name.contains("void_incarnate")) {
            if (attackCooldown <= 0) {
                // Teleport target randomly
                if (random.nextFloat() < 0.3f) {
                    double rx = target.getX() + (random.nextDouble() - 0.5) * 20;
                    double rz = target.getZ() + (random.nextDouble() - 0.5) * 20;
                    target.teleportTo(rx, target.getY(), rz);
                }
                // Mana drain
                target.hurt(this.damageSources().magic(), 10 + phase * 3);
                // Blindness + nausea
                target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 40, 0));
                target.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 60, 0));
                attackCooldown = 40 - (phase * 5);
            }
            // Summon void tentacles periodically
            if (this.tickCount % 200 == 0) {
                summonMinions(level, phase + 1);
            }
        }
    }

    // ─── Boss attack helpers ───

    private void crystalRain(ServerLevel level) {
        for (int i = 0; i < 8; i++) {
            double x = this.getX() + (random.nextDouble() - 0.5) * 16;
            double z = this.getZ() + (random.nextDouble() - 0.5) * 16;
            double y = this.getY() + 10;
            level.sendParticles(ParticleTypes.END_ROD, x, y, z, 20, 0.5, 5, 0.5, 0.05);
            for (Entity e : level.getEntities(this, new net.minecraft.world.phys.AABB(x-1, this.getY()-1, z-1, x+1, this.getY()+3, z+1))) {
                if (e instanceof LivingEntity le && !(e instanceof BossEntity)) {
                    le.hurt(this.damageSources().fallingBlock(this), 8);
                }
            }
        }
    }

    private void fireBreath(ServerLevel level, LivingEntity target, float damage) {
        var direction = target.position().subtract(this.position()).normalize();
        for (int i = 1; i <= 10; i++) {
            var pos = this.getEyePosition().add(direction.scale(i));
            level.sendParticles(ParticleTypes.FLAME, pos.x, pos.y, pos.z, 5, 0.3, 0.3, 0.3, 0.02);
        }
        if (this.distanceTo(target) < 12) {
            target.hurt(this.damageSources().onFire(), damage);
            target.setSecondsOnFire(3);
        }
    }

    private void summonMinions(ServerLevel level, int count) {
        for (int i = 0; i < count; i++) {
            // Spawn a zombie-type minion (placeholder — custom minion types in full version)
            var minion = EntityType.ZOMBIE.create(level);
            if (minion != null) {
                double x = this.getX() + (random.nextDouble() - 0.5) * 8;
                double z = this.getZ() + (random.nextDouble() - 0.5) * 8;
                minion.moveTo(x, this.getY(), z);
                level.addFreshEntity(minion);
            }
        }
    }

    private void castBossSpell(ServerLevel level, LivingEntity target, int school) {
        switch (school) {
            case 0 -> { // Fire
                target.setSecondsOnFire(4);
                target.hurt(this.damageSources().magic(), 6);
            }
            case 1 -> { // Ice
                target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 2));
                target.hurt(this.damageSources().freeze(), 5);
            }
            case 2 -> { // Lightning
                target.hurt(this.damageSources().lightningBolt(), 7);
            }
            case 3 -> { // Shadow
                target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 40, 0));
                target.hurt(this.damageSources().magic(), 5);
            }
        }
    }

    // ─── Boss bar management ───

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossBar.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossBar.removePlayer(player);
    }

    private BossEvent.BossBarColor getBossBarColor() {
        String name = this.getType().getDescriptionId();
        if (name.contains("crystal"))  return BossEvent.BossBarColor.BLUE;
        if (name.contains("shadow") || name.contains("lich"))  return BossEvent.BossBarColor.PURPLE;
        if (name.contains("infernal") || name.contains("drake")) return BossEvent.BossBarColor.RED;
        if (name.contains("frost") || name.contains("wyrm")) return BossEvent.BossBarColor.WHITE;
        if (name.contains("dragon"))   return BossEvent.BossBarColor.YELLOW;
        if (name.contains("void"))     return BossEvent.BossBarColor.PURPLE;
        if (name.contains("archon"))   return BossEvent.BossBarColor.GREEN;
        return BossEvent.BossBarColor.PINK;
    }

    @Override
    public boolean canChangeDimensions() { return false; }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("bossPhase", phase);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        phase = tag.getInt("bossPhase");
        if (this.hasCustomName()) {
            this.bossBar.setName(this.getDisplayName());
        }
    }

    // ─── Boss configuration ───

    record BossConfig(double health, double damage, double speed, double armor, double kbResist) {}

    private static BossConfig getBossConfig(String name) {
        if (name.contains("crystal_golem_king"))  return new BossConfig(200,  12, 0.25, 12, 0.6);
        if (name.contains("corrupted_mage_boss")) return new BossConfig(300,  8,  0.28, 6,  0.3);
        if (name.contains("shadow_lich"))          return new BossConfig(500,  10, 0.3,  8,  0.4);
        if (name.contains("the_archon"))           return new BossConfig(500,  14, 0.22, 14, 0.7);
        if (name.contains("infernal_drake"))       return new BossConfig(400,  16, 0.35, 10, 0.5);
        if (name.contains("frost_wyrm"))           return new BossConfig(400,  14, 0.3,  12, 0.5);
        if (name.contains("arcane_dragon"))        return new BossConfig(1000, 20, 0.28, 16, 0.8);
        if (name.contains("void_incarnate"))       return new BossConfig(2000, 25, 0.25, 20, 1.0);
        return new BossConfig(200, 10, 0.25, 8, 0.5);
    }
}
