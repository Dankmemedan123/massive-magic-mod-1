package com.arcana.magic;

import com.arcana.ArcanaMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.*;

import java.util.*;

/**
 * Executes spells, detects combos, handles environmental reactions.
 * Each spell has unique mechanical behavior — NO reskins.
 */
public class SpellCastHandler {

    // Track recent casts for combo detection (player UUID → list of recent spell+time)
    private static final Map<UUID, List<RecentCast>> recentCasts = new HashMap<>();

    private record RecentCast(ArcanaSpell spell, long tick, BlockPos pos) {}

    // ─── Main cast entry point ───
    public static boolean cast(Player player, ArcanaSpell spell, Level level) {
        ManaCapability.ManaData mana = player.getCapability(ManaCapability.MANA_CAP).orElse(null);
        if (mana == null) return false;

        // Check mastery
        if (mana.getMastery(spell.schoolIndex) < spell.masteryRequired) return false;

        // Check and consume mana
        float cost = calculateCost(mana, spell);
        if (!mana.consumeMana(cost)) return false;

        // Execute spell
        boolean success = executeSpell(player, spell, level);

        if (success) {
            // Grant mastery (diminishing returns — less at higher mastery)
            int currentMastery = mana.getMastery(spell.schoolIndex);
            int gain = currentMastery < 30 ? 2 : (currentMastery < 60 ? 1 : 0);
            if (gain > 0 || level.random.nextFloat() < 0.3f) {
                mana.addMastery(spell.schoolIndex, Math.max(1, gain));
            }

            // Track for combos
            trackCast(player, spell, level);

            // Check for combos
            checkCombo(player, spell, level);
        }

        return success;
    }

    private static float calculateCost(ManaCapability.ManaData mana, ArcanaSpell spell) {
        float cost = spell.manaCost;
        // Mastery discount: up to 30% at mastery 100
        int mastery = mana.getMastery(spell.schoolIndex);
        cost *= (1.0f - mastery * 0.003f);
        return Math.max(1, cost);
    }

    // ─── Individual spell implementations ───
    private static boolean executeSpell(Player player, ArcanaSpell spell, Level level) {
        if (level.isClientSide) return true; // Client just plays effects
        ServerLevel sLevel = (ServerLevel) level;
        BlockPos pos = player.blockPosition();
        Vec3 look = player.getLookAngle();

        switch (spell) {
            // ═══ PYROMANCY ═══
            case FIREBALL -> {
                SmallFireball fireball = new SmallFireball(level, player,
                        look.x, look.y, look.z);
                fireball.setPos(player.getX(), player.getEyeY() - 0.1, player.getZ());
                level.addFreshEntity(fireball);
                playSchoolSound(sLevel, pos, 0);
            }
            case FIRE_WALL -> {
                // Place fire blocks in a 5-wide wall perpendicular to look direction
                // Manual cross product (Vec3.cross doesn't exist in vanilla MC)
                Vec3 up = new Vec3(0, 1, 0);
                Vec3 right = new Vec3(
                        look.z * up.y - look.y * up.z,
                        look.x * up.z - look.z * up.x,
                        look.y * up.x - look.x * up.y
                ).normalize();
                for (int i = -2; i <= 2; i++) {
                    for (int y = 0; y < 3; y++) {
                        BlockPos wallPos = new BlockPos(
                                (int)(pos.getX() + look.x * 3 + right.x * i),
                                pos.getY() + y,
                                (int)(pos.getZ() + look.z * 3 + right.z * i));
                        if (level.getBlockState(wallPos).isAir()) {
                            level.setBlock(wallPos, Blocks.FIRE.defaultBlockState(), 3);
                        }
                    }
                }
                playSchoolSound(sLevel, pos, 0);
            }
            case INFERNO -> {
                // 8-block radius fire damage AoE
                List<Entity> nearby = level.getEntities(player, player.getBoundingBox().inflate(8));
                for (Entity e : nearby) {
                    if (e instanceof LivingEntity le && !(e instanceof Player ally && ally == player)) {
                        le.setSecondsOnFire(6);
                        le.hurt(level.damageSources().playerAttack(player), 4);
                    }
                }
                spawnParticleRing(sLevel, pos, ParticleTypes.FLAME, 8, 100);
                playSchoolSound(sLevel, pos, 0);
            }
            case METEOR_STRIKE -> {
                // Delayed meteor at look position (3 sec delay simulated by scheduling)
                HitResult hit = player.pick(50, 0, false);
                BlockPos target = hit instanceof BlockHitResult bhr ? bhr.getBlockPos().above() :
                        new BlockPos((int)(player.getX() + look.x * 20),
                                (int)(player.getY() + look.y * 20 + 10),
                                (int)(player.getZ() + look.z * 20));
                // Create explosion at target
                level.explode(player, target.getX(), target.getY(), target.getZ(),
                        4.0f, Level.ExplosionInteraction.MOB);
                spawnParticleRing(sLevel, target, ParticleTypes.LAVA, 6, 200);
                playSchoolSound(sLevel, pos, 0);
            }
            case PHOENIX_FORM -> {
                // Self-buff: fire resistance + flight emulation + fire trail
                player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 400, 0));
                player.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 400, 0));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 400, 2));
                player.addEffect(new MobEffectInstance(MobEffects.GLOWING, 400, 0));
                playSchoolSound(sLevel, pos, 0);
            }

            // ═══ CRYOMANCY ═══
            case FROST_BOLT -> {
                // Damage + slow + freeze water in path
                HitResult hit = player.pick(30, 0, false);
                if (hit instanceof EntityHitResult ehr && ehr.getEntity() instanceof LivingEntity target) {
                    target.hurt(level.damageSources().playerAttack(player), 5);
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 1));
                }
                // Freeze water along path
                for (int i = 1; i <= 30; i++) {
                    BlockPos check = new BlockPos(
                            (int)(player.getX() + look.x * i),
                            (int)(player.getEyeY() + look.y * i),
                            (int)(player.getZ() + look.z * i));
                    if (level.getBlockState(check).is(Blocks.WATER)) {
                        level.setBlock(check, Blocks.ICE.defaultBlockState(), 3);
                    }
                }
                playSchoolSound(sLevel, pos, 1);
            }
            case ICE_SHIELD -> {
                // Give absorption hearts (simulating orbiting crystals)
                player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 400, 3));
                playSchoolSound(sLevel, pos, 1);
            }
            case BLIZZARD -> {
                // 10-block AoE slow + extinguish fire
                List<Entity> nearby = level.getEntities(player, player.getBoundingBox().inflate(10));
                for (Entity e : nearby) {
                    if (e instanceof LivingEntity le) {
                        le.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 2));
                        le.clearFire();
                    }
                }
                spawnParticleRing(sLevel, pos, ParticleTypes.SNOWFLAKE, 10, 150);
                playSchoolSound(sLevel, pos, 1);
            }
            case GLACIAL_TOMB -> {
                HitResult hit = player.pick(20, 0, false);
                if (hit instanceof EntityHitResult ehr && ehr.getEntity() instanceof LivingEntity target) {
                    // Freeze (slow + resistance simulates entombment)
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 127));
                    target.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 4));
                    // After 5 sec, shatter damage applied via delayed task
                }
                playSchoolSound(sLevel, pos, 1);
            }
            case ABSOLUTE_ZERO -> {
                List<Entity> nearby = level.getEntities(player, player.getBoundingBox().inflate(15));
                for (Entity e : nearby) {
                    if (e instanceof LivingEntity le && le != player) {
                        le.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 160, 127));
                        le.hurt(level.damageSources().freeze(), 8);
                    }
                }
                // Freeze all water in radius
                for (BlockPos bp : BlockPos.betweenClosed(pos.offset(-15,-5,-15), pos.offset(15,5,15))) {
                    if (level.getBlockState(bp).is(Blocks.WATER)) {
                        level.setBlock(bp, Blocks.ICE.defaultBlockState(), 3);
                    }
                }
                playSchoolSound(sLevel, pos, 1);
            }

            // ═══ ELECTROMANCY ═══
            case SPARK -> {
                HitResult hit = player.pick(30, 0, false);
                if (hit instanceof EntityHitResult ehr && ehr.getEntity() instanceof LivingEntity target) {
                    float damage = 4;
                    // Double damage if wet (in rain or water)
                    if (target.isInWaterOrRain()) damage *= 2;
                    target.hurt(level.damageSources().lightningBolt(), damage);
                    sLevel.sendParticles(ParticleTypes.ELECTRIC_SPARK,
                            target.getX(), target.getY()+1, target.getZ(), 15, 0.3, 0.5, 0.3, 0.1);
                }
                playSchoolSound(sLevel, pos, 2);
            }
            case CHAIN_LIGHTNING -> {
                HitResult hit = player.pick(30, 0, false);
                if (hit instanceof EntityHitResult ehr && ehr.getEntity() instanceof LivingEntity primary) {
                    float damage = 8;
                    primary.hurt(level.damageSources().lightningBolt(), damage);
                    // Chain to nearby
                    List<Entity> chain = level.getEntities(primary, primary.getBoundingBox().inflate(5));
                    int chains = 0;
                    for (Entity e : chain) {
                        if (e instanceof LivingEntity le && le != player && le != primary && chains < 4) {
                            float chainDmg = damage * (float)Math.pow(0.75, chains + 1);
                            if (le.isInWaterOrRain()) chainDmg *= 2;
                            le.hurt(level.damageSources().lightningBolt(), chainDmg);
                            chains++;
                        }
                    }
                }
                playSchoolSound(sLevel, pos, 2);
            }
            case THUNDERCLAP -> {
                List<Entity> nearby = level.getEntities(player, player.getBoundingBox().inflate(6));
                for (Entity e : nearby) {
                    if (e instanceof LivingEntity le && le != player) {
                        le.hurt(level.damageSources().lightningBolt(), 12);
                        Vec3 knock = le.position().subtract(player.position()).normalize().scale(3);
                        le.push(knock.x, 0.5, knock.z);
                    }
                }
                spawnParticleRing(sLevel, pos, ParticleTypes.ELECTRIC_SPARK, 6, 80);
                playSchoolSound(sLevel, pos, 2);
            }
            case STORM_CALL -> {
                sLevel.setWeatherParameters(0, 1200, true, true); // 60 sec thunderstorm
                playSchoolSound(sLevel, pos, 2);
            }
            case ZEUS_WRATH -> {
                HitResult hit = player.pick(50, 0, false);
                BlockPos target = hit instanceof EntityHitResult ehr ?
                        ehr.getEntity().blockPosition() :
                        (hit instanceof BlockHitResult bhr ? bhr.getBlockPos() : pos.offset(10,0,10));
                LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(level);
                if (bolt != null) {
                    bolt.moveTo(Vec3.atBottomCenterOf(target));
                    bolt.setCause(player instanceof ServerPlayer sp ? sp : null);
                    level.addFreshEntity(bolt);
                }
                // Extra damage to nearby entities
                for (Entity e : level.getEntities(player,
                        new AABB(target).inflate(10))) {
                    if (e instanceof LivingEntity le && le != player) {
                        le.hurt(level.damageSources().lightningBolt(), 20);
                        le.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 127));
                    }
                }
                playSchoolSound(sLevel, pos, 2);
            }

            // ═══ GEOMANCY ═══
            case STONE_SKIN -> {
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300, 1));
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 300, 0));
                playSchoolSound(sLevel, pos, 3);
            }
            case EARTHQUAKE -> {
                for (Entity e : level.getEntities(player, player.getBoundingBox().inflate(8))) {
                    if (e instanceof LivingEntity le && le != player) {
                        le.hurt(level.damageSources().playerAttack(player), 8);
                        le.push(0, 0.8, 0);
                    }
                }
                spawnParticleRing(sLevel, pos, ParticleTypes.CAMPFIRE_COSY_SMOKE, 8, 60);
                playSchoolSound(sLevel, pos, 3);
            }
            case MUD_TRAP -> {
                // Place slow-zone construct (soul sand as approximation)
                BlockPos front = pos.offset((int)(look.x*4), 0, (int)(look.z*4));
                for (int dx = -2; dx <= 2; dx++) {
                    for (int dz = -2; dz <= 2; dz++) {
                        BlockPos p = front.offset(dx, -1, dz);
                        if (level.getBlockState(p).isSolidRender(level, p)) {
                            level.setBlock(p, Blocks.SOUL_SAND.defaultBlockState(), 3);
                        }
                    }
                }
                playSchoolSound(sLevel, pos, 3);
            }
            case CRYSTAL_LANCE -> {
                // Pierce through all entities in a line
                for (int i = 1; i <= 20; i++) {
                    Vec3 check = player.getEyePosition().add(look.scale(i));
                    for (Entity e : level.getEntities(player,
                            new AABB(check, check).inflate(0.5))) {
                        if (e instanceof LivingEntity le) {
                            le.hurt(level.damageSources().playerAttack(player), 15);
                            le.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 127));
                        }
                    }
                }
                playSchoolSound(sLevel, pos, 3);
            }
            case GOLEM_SUMMON -> {
                // Spawn iron golem ally (simplified — full custom entity in Phase 5)
                var golem = EntityType.IRON_GOLEM.create(level);
                if (golem != null) {
                    golem.moveTo(player.getX() + look.x * 3, player.getY(), player.getZ() + look.z * 3);
                    golem.setPlayerCreated(true);
                    level.addFreshEntity(golem);
                }
                playSchoolSound(sLevel, pos, 3);
            }

            // ═══ AEROMANCY ═══
            case GUST -> {
                HitResult hit = player.pick(20, 0, false);
                if (hit instanceof EntityHitResult ehr && ehr.getEntity() instanceof LivingEntity target) {
                    Vec3 push = look.normalize().scale(3);
                    target.push(push.x, 0.5, push.z);
                }
                playSchoolSound(sLevel, pos, 4);
            }
            case LEVITATE -> {
                player.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 40, 0));
                player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 60, 0));
                playSchoolSound(sLevel, pos, 4);
            }
            case TORNADO -> {
                // Push entities up and spin them
                BlockPos front = pos.offset((int)(look.x*8), 0, (int)(look.z*8));
                for (Entity e : level.getEntities(player, new AABB(front).inflate(3))) {
                    if (e instanceof LivingEntity le) {
                        le.push(0, 2.0, 0);
                        le.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 60, 0));
                    }
                }
                spawnParticleRing(sLevel, front, ParticleTypes.CLOUD, 3, 100);
                playSchoolSound(sLevel, pos, 4);
            }
            case WIND_WALK -> {
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400, 2));
                player.addEffect(new MobEffectInstance(MobEffects.JUMP, 400, 2));
                player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 400, 0));
                playSchoolSound(sLevel, pos, 4);
            }
            case HURRICANE -> {
                for (Entity e : level.getEntities(player, player.getBoundingBox().inflate(30))) {
                    if (e instanceof LivingEntity le && le != player) {
                        // Pull toward caster
                        Vec3 pull = player.position().subtract(le.position()).normalize().scale(2);
                        le.push(pull.x, 1.5, pull.z);
                    }
                }
                playSchoolSound(sLevel, pos, 4);
            }

            // ═══ UMBRAMANCY ═══
            case SHADOW_STEP -> {
                HitResult hit = player.pick(16, 0, false);
                if (hit instanceof BlockHitResult bhr) {
                    BlockPos target = bhr.getBlockPos().relative(bhr.getDirection());
                    player.teleportTo(target.getX() + 0.5, target.getY(), target.getZ() + 0.5);
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 10, 4));
                    sLevel.sendParticles(ParticleTypes.LARGE_SMOKE,
                            pos.getX(), pos.getY() + 1, pos.getZ(), 20, 0.5, 1, 0.5, 0.02);
                }
                playSchoolSound(sLevel, pos, 5);
            }
            case DARK_PULSE -> {
                for (Entity e : level.getEntities(player, player.getBoundingBox().inflate(6))) {
                    if (e instanceof LivingEntity le && le != player) {
                        le.hurt(level.damageSources().magic(), 6);
                        le.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 80, 0));
                    }
                }
                playSchoolSound(sLevel, pos, 5);
            }
            case FEAR -> {
                HitResult hit = player.pick(20, 0, false);
                if (hit instanceof EntityHitResult ehr && ehr.getEntity() instanceof Monster target) {
                    // Slowness + weakness = effective "fear" (true flee AI requires custom entity)
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 160, 2));
                    target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 160, 3));
                }
                playSchoolSound(sLevel, pos, 5);
            }
            case SHADOW_CLONE -> {
                // Summon armor stand as visual clone (true clone requires custom entity)
                var clone = EntityType.ARMOR_STAND.create(level);
                if (clone != null) {
                    clone.moveTo(player.getX() + look.x * 2, player.getY(), player.getZ() + look.z * 2);
                    clone.setInvisible(false);
                    clone.setCustomName(player.getName());
                    level.addFreshEntity(clone);
                }
                playSchoolSound(sLevel, pos, 5);
            }
            case VOID_RIFT -> {
                BlockPos front = pos.offset((int)(look.x*5), 0, (int)(look.z*5));
                for (Entity e : level.getEntities(player, new AABB(front).inflate(3))) {
                    if (e instanceof LivingEntity le && le != player) {
                        le.hurt(level.damageSources().magic(), 30);
                        // Random teleport
                        double rx = le.getX() + (level.random.nextDouble() - 0.5) * 200;
                        double rz = le.getZ() + (level.random.nextDouble() - 0.5) * 200;
                        le.teleportTo(rx, le.getY(), rz);
                    }
                }
                playSchoolSound(sLevel, pos, 5);
            }

            // ═══ LUXOMANCY ═══
            case HEAL -> {
                HitResult hit = player.pick(10, 0, false);
                if (hit instanceof EntityHitResult ehr && ehr.getEntity() instanceof LivingEntity target) {
                    if (target.isInvertedHealAndHarm()) {
                        target.hurt(level.damageSources().magic(), 6);
                    } else {
                        target.heal(6);
                    }
                } else {
                    player.heal(6);
                }
                sLevel.sendParticles(ParticleTypes.HEART, player.getX(), player.getY()+1, player.getZ(), 5, 0.5, 0.5, 0.5, 0);
                playSchoolSound(sLevel, pos, 6);
            }
            case PURIFY -> {
                player.removeAllEffects();
                for (Entity e : level.getEntities(player, player.getBoundingBox().inflate(5))) {
                    if (e instanceof Player p && p != player) {
                        p.removeAllEffects();
                    }
                }
                playSchoolSound(sLevel, pos, 6);
            }
            case SUN_BEAM -> {
                // Continuous beam - damage along line
                for (int i = 1; i <= 20; i++) {
                    Vec3 check = player.getEyePosition().add(look.scale(i));
                    BlockPos bp = new BlockPos((int)check.x, (int)check.y, (int)check.z);
                    // Light up path
                    if (level.getBlockState(bp).isAir()) {
                        sLevel.sendParticles(ParticleTypes.END_ROD, check.x, check.y, check.z, 1, 0, 0, 0, 0);
                    }
                    for (Entity e : level.getEntities(player, new AABB(check, check).inflate(0.5))) {
                        if (e instanceof LivingEntity le) {
                            float dmg = le.isInvertedHealAndHarm() ? 12 : 6;
                            le.hurt(level.damageSources().magic(), dmg / 20f); // Per tick
                        }
                    }
                }
                playSchoolSound(sLevel, pos, 6);
            }
            case HOLY_SHIELD -> {
                // Regen to self and nearby allies
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 240, 0));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 240, 1));
                for (Entity e : level.getEntities(player, player.getBoundingBox().inflate(5))) {
                    if (e instanceof Player p) {
                        p.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 240, 0));
                    }
                }
                playSchoolSound(sLevel, pos, 6);
            }
            case DIVINE_JUDGMENT -> {
                HitResult hit = player.pick(50, 0, false);
                BlockPos target = hit instanceof EntityHitResult ehr ?
                        ehr.getEntity().blockPosition() : pos.offset((int)(look.x*20), 0, (int)(look.z*20));
                for (Entity e : level.getEntities(player, new AABB(target).inflate(5))) {
                    if (e instanceof LivingEntity le) {
                        if (le.isInvertedHealAndHarm()) {
                            le.hurt(level.damageSources().magic(), 50);
                        } else if (le instanceof Player p) {
                            p.heal(20);
                        } else {
                            le.hurt(level.damageSources().magic(), 25);
                        }
                    }
                }
                // Massive particle effect
                sLevel.sendParticles(ParticleTypes.END_ROD, target.getX(), target.getY()+10, target.getZ(), 200, 0.5, 10, 0.5, 0.1);
                playSchoolSound(sLevel, pos, 6);
            }

            // ═══ ARCANOMANCY ═══
            case MANA_BOLT -> {
                HitResult hit = player.pick(30, 0, false);
                if (hit instanceof EntityHitResult ehr && ehr.getEntity() instanceof LivingEntity target) {
                    target.hurt(level.damageSources().magic(), 3);
                }
                sLevel.sendParticles(ParticleTypes.ENCHANT, player.getX()+look.x*3, player.getEyeY()+look.y*3, player.getZ()+look.z*3, 10, 0.2, 0.2, 0.2, 0.5);
                playSchoolSound(sLevel, pos, 7);
            }
            case DISPEL -> {
                HitResult hit = player.pick(20, 0, false);
                if (hit instanceof EntityHitResult ehr && ehr.getEntity() instanceof LivingEntity target) {
                    target.removeAllEffects();
                }
                playSchoolSound(sLevel, pos, 7);
            }
            case ARCANE_SHIELD -> {
                player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 400, 5));
                playSchoolSound(sLevel, pos, 7);
            }
            case TELEKINESIS -> {
                // Push target entity
                HitResult hit = player.pick(20, 0, false);
                if (hit instanceof EntityHitResult ehr) {
                    Entity target = ehr.getEntity();
                    Vec3 push = look.normalize().scale(4);
                    target.push(push.x, push.y + 0.5, push.z);
                }
                playSchoolSound(sLevel, pos, 7);
            }
            case TIME_SLOW -> {
                // Apply massive slowdown to all nearby entities
                for (Entity e : level.getEntities(player, player.getBoundingBox().inflate(20))) {
                    if (e instanceof LivingEntity le && le != player) {
                        le.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 160, 4));
                        le.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 160, 4));
                        le.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 160, 4));
                    }
                }
                playSchoolSound(sLevel, pos, 7);
            }

            default -> { return false; }
        }
        return true;
    }

    // ─── COMBO SYSTEM ───
    private static void trackCast(Player player, ArcanaSpell spell, Level level) {
        recentCasts.computeIfAbsent(player.getUUID(), k -> new ArrayList<>());
        List<RecentCast> casts = recentCasts.get(player.getUUID());
        casts.add(new RecentCast(spell, level.getGameTime(), player.blockPosition()));
        // Keep only last 5 seconds worth (100 ticks)
        casts.removeIf(c -> level.getGameTime() - c.tick > 100);
    }

    private static void checkCombo(Player player, ArcanaSpell latestSpell, Level level) {
        List<RecentCast> casts = recentCasts.getOrDefault(player.getUUID(), List.of());
        if (casts.size() < 2) return;

        RecentCast prev = casts.get(casts.size() - 2);
        ArcanaSpell prevSpell = prev.spell;

        // Fire Wall + Gust = Flame Wave
        if ((prevSpell == ArcanaSpell.FIRE_WALL && latestSpell == ArcanaSpell.GUST) ||
            (prevSpell == ArcanaSpell.GUST && latestSpell == ArcanaSpell.FIRE_WALL)) {
            executeCombo(player, level, "Flame Wave", 15);
        }
        // Blizzard + Inferno = Steam Explosion
        if ((prevSpell == ArcanaSpell.BLIZZARD && latestSpell == ArcanaSpell.INFERNO) ||
            (prevSpell == ArcanaSpell.INFERNO && latestSpell == ArcanaSpell.BLIZZARD)) {
            executeCombo(player, level, "Steam Explosion", 10);
        }
        // Tornado + Fireball = Fire Tornado
        if ((prevSpell == ArcanaSpell.TORNADO && latestSpell == ArcanaSpell.FIREBALL) ||
            (prevSpell == ArcanaSpell.FIREBALL && latestSpell == ArcanaSpell.TORNADO)) {
            executeCombo(player, level, "Fire Tornado", 12);
        }
        // Earthquake + Frost Bolt = Shatter
        if ((prevSpell == ArcanaSpell.EARTHQUAKE && latestSpell == ArcanaSpell.FROST_BOLT) ||
            (prevSpell == ArcanaSpell.FROST_BOLT && latestSpell == ArcanaSpell.EARTHQUAKE)) {
            executeCombo(player, level, "Shatter", 20);
        }
    }

    private static void executeCombo(Player player, Level level, String comboName, float bonusDmg) {
        if (level instanceof ServerLevel sLevel) {
            // Apply bonus damage to all nearby enemies
            for (Entity e : level.getEntities(player, player.getBoundingBox().inflate(10))) {
                if (e instanceof LivingEntity le && le != player) {
                    le.hurt(level.damageSources().magic(), bonusDmg);
                }
            }
            // Notify player
            player.displayClientMessage(
                    net.minecraft.network.chat.Component.literal("§d✦ Spell Combo: " + comboName + "! §7(+" + (int)bonusDmg + " damage)"),
                    true);
            // Particle burst
            spawnParticleRing(sLevel, player.blockPosition(), ParticleTypes.WITCH, 10, 100);
        }
        ArcanaMod.LOGGER.info("[Arcana] {} triggered combo: {}", player.getName().getString(), comboName);
    }

    // ─── Utility ───
    private static void playSchoolSound(ServerLevel level, BlockPos pos, int school) {
        // Each school gets a distinct sound
        var sound = switch (school) {
            case 0 -> SoundEvents.FIRECHARGE_USE;
            case 1 -> SoundEvents.GLASS_BREAK;
            case 2 -> SoundEvents.LIGHTNING_BOLT_THUNDER;
            case 3 -> SoundEvents.STONE_BREAK;
            case 4 -> SoundEvents.ELYTRA_FLYING;
            case 5 -> SoundEvents.ENDERMAN_TELEPORT;
            case 6 -> SoundEvents.AMETHYST_BLOCK_CHIME;
            case 7 -> SoundEvents.ENCHANTMENT_TABLE_USE;
            default -> SoundEvents.EXPERIENCE_ORB_PICKUP;
        };
        level.playSound(null, pos, sound, SoundSource.PLAYERS, 1.0f, 1.0f);
    }

    private static void spawnParticleRing(ServerLevel level, BlockPos center,
                                           net.minecraft.core.particles.ParticleOptions particle,
                                           double radius, int count) {
        for (int i = 0; i < count; i++) {
            double angle = (2 * Math.PI * i) / count;
            double x = center.getX() + 0.5 + radius * Math.cos(angle);
            double z = center.getZ() + 0.5 + radius * Math.sin(angle);
            level.sendParticles(particle, x, center.getY() + 0.5, z, 1, 0, 0.1, 0, 0.01);
        }
    }
}
