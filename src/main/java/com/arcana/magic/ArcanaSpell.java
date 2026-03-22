package com.arcana.magic;

/**
 * All 40 spells defined. Each spell has:
 * - School index (0-7)
 * - Tier (1-5)
 * - Mana cost
 * - Cooldown in ticks
 * - Cast type (PROJECTILE, BEAM, INSTANT, CONSTRUCT, SUMMON, AURA, CHARGE)
 * - Mastery required to unlock
 */
public enum ArcanaSpell {

    // ═══ PYROMANCY (school 0) ═══
    FIREBALL       (0, 1, 15,  60,  CastType.CHARGE,     0,  "Launches an explosive fireball. Hold to charge for more damage."),
    FIRE_WALL      (0, 2, 30,  240, CastType.CONSTRUCT,  15, "Creates a 5-wide wall of flames that burns enemies who pass through."),
    INFERNO        (0, 3, 60,  600, CastType.AURA,       40, "8-block firestorm dealing 4 damage/sec for 6 seconds."),
    METEOR_STRIKE  (0, 4, 100, 1200,CastType.CHARGE,     70, "Call a meteor from the sky after 3 seconds. Devastating impact."),
    PHOENIX_FORM   (0, 5, 150, 6000,CastType.INSTANT,    90, "Transform into a Phoenix: flight, fire trail, fireball stream. 20s."),

    // ═══ CRYOMANCY (school 1) ═══
    FROST_BOLT     (1, 1, 12,  50,  CastType.PROJECTILE, 0,  "Fast ice shard. Slows target. Freezes water in its path."),
    ICE_SHIELD     (1, 2, 25,  300, CastType.INSTANT,    15, "Summon 4 orbiting ice crystals that each absorb one hit."),
    BLIZZARD       (1, 3, 50,  500, CastType.AURA,       40, "10-block snowstorm. Slows all entities, reduces visibility."),
    GLACIAL_TOMB   (1, 4, 80,  900, CastType.PROJECTILE, 70, "Encase target in ice for 5s. Invulnerable + immobile. Shatters for damage."),
    ABSOLUTE_ZERO  (1, 5, 140, 6000,CastType.INSTANT,    90, "15-block instant freeze. All entities frozen 8s. 3x shatter bonus."),

    // ═══ ELECTROMANCY (school 2) ═══
    SPARK          (2, 1, 10,  30,  CastType.PROJECTILE, 0,  "Instant-hit lightning bolt. Double damage to wet targets."),
    CHAIN_LIGHTNING(2, 2, 35,  200, CastType.PROJECTILE, 15, "Hits target then chains to 4 nearby entities at 75% damage each."),
    THUNDERCLAP    (2, 3, 45,  400, CastType.INSTANT,    40, "Point-blank AoE: 12 damage + knockback 6 blocks. Shatters glass."),
    STORM_CALL     (2, 4, 90,  1200,CastType.INSTANT,    70, "Summon a real thunderstorm for 60s. Lightning spells cost 50% less."),
    ZEUS_WRATH     (2, 5, 160, 6000,CastType.PROJECTILE, 90, "Massive bolt from sky: 40 damage. Stuns nearby for 3s. Visible 100+ blocks."),

    // ═══ GEOMANCY (school 3) ═══
    STONE_SKIN     (3, 1, 15,  160, CastType.INSTANT,    0,  "+4 armor for 15 seconds. Slowness I while active."),
    EARTHQUAKE     (3, 2, 35,  300, CastType.INSTANT,    15, "8-block ground slam. 8 damage + launches entities 3 blocks up."),
    MUD_TRAP       (3, 3, 25,  400, CastType.CONSTRUCT,  40, "5x5 mud patch. Slowness IV + no jumping for 15 seconds."),
    CRYSTAL_LANCE  (3, 4, 70,  600, CastType.PROJECTILE, 70, "Massive amethyst spike pierces all entities in a line. 15 damage. Pins 3s."),
    GOLEM_SUMMON   (3, 5, 130, 6000,CastType.SUMMON,     90, "Summon an Arcane Golem ally for 60s. 200HP, 15 damage, commandable."),

    // ═══ AEROMANCY (school 4) ═══
    GUST           (4, 1, 10,  40,  CastType.PROJECTILE, 0,  "Invisible wind push. 8 blocks knockback. No damage. Deflects projectiles."),
    LEVITATE       (4, 2, 0,   0,   CastType.BEAM,       15, "Hold to hover. 5 mana/sec. Move with WASD while floating."),
    TORNADO        (4, 3, 55,  500, CastType.PROJECTILE, 40, "Summon a traveling tornado for 10s. Launches entities, destroys foliage."),
    WIND_WALK      (4, 4, 60,  900, CastType.INSTANT,    70, "20s: Speed III, Jump III, no fall damage, double-jump ability."),
    HURRICANE      (4, 5, 150, 6000,CastType.AURA,       90, "30-block wind zone for 15s. Pulls entities to center then launches up."),

    // ═══ UMBRAMANCY (school 5) ═══
    SHADOW_STEP    (5, 1, 20,  100, CastType.INSTANT,    0,  "Teleport to looked-at block (16 range). Brief invulnerability."),
    DARK_PULSE     (5, 2, 30,  200, CastType.INSTANT,    15, "6-block shadow burst. 6 damage + Blindness 4s. Extinguishes lights."),
    FEAR           (5, 3, 40,  400, CastType.PROJECTILE, 40, "Target flees for 8s. Drops held items. Bosses slowed instead."),
    SHADOW_CLONE   (5, 4, 75,  900, CastType.SUMMON,     70, "Shadow copy fights with you 30s. Your gear, 50% damage. Draws aggro."),
    VOID_RIFT      (5, 5, 160, 6000,CastType.CONSTRUCT,  90, "3-block void portal for 10s. 30 damage + teleports entities randomly."),

    // ═══ LUXOMANCY (school 6) ═══
    HEAL           (6, 1, 20,  100, CastType.INSTANT,    0,  "Heal self/target 6HP. Deals 6 damage to undead. Golden particles."),
    PURIFY         (6, 2, 25,  240, CastType.INSTANT,    15, "Remove ALL negative effects from you + allies in 5 blocks."),
    SUN_BEAM       (6, 3, 0,   0,   CastType.BEAM,       40, "Continuous light beam. 6/sec to mobs, 12/sec to undead. Lights up path."),
    HOLY_SHIELD    (6, 4, 80,  800, CastType.CONSTRUCT,  70, "Golden dome 5-block radius, 12s. Regen I inside. Blocks projectiles."),
    DIVINE_JUDGMENT(6, 5, 170, 6000,CastType.PROJECTILE, 90, "Sky beam on target: 50 to undead, 25 to others. Heals allies 20HP."),

    // ═══ ARCANOMANCY (school 7) ═══
    MANA_BOLT      (7, 1, 8,   20,  CastType.PROJECTILE, 0,  "Cheapest, fastest spell. 3 damage. Affected by all staff modifiers."),
    DISPEL         (7, 2, 30,  300, CastType.PROJECTILE, 15, "Remove ALL magical effects from target. Destroys magical constructs."),
    ARCANE_SHIELD  (7, 3, 40,  400, CastType.INSTANT,    40, "Absorb next 30 damage over 20s. Works on ALL damage types."),
    TELEKINESIS    (7, 4, 50,  200, CastType.PROJECTILE, 70, "Grab block/entity + hold in air. Move with crosshair. Throw on click."),
    TIME_SLOW      (7, 5, 180, 6000,CastType.INSTANT,    90, "20-block radius: everything at 25% speed for 8s. You move normally.");

    public final int schoolIndex;
    public final int tier;
    public final float manaCost;
    public final int cooldownTicks;
    public final CastType castType;
    public final int masteryRequired;
    public final String description;

    ArcanaSpell(int school, int tier, float mana, int cooldown, CastType type,
                int mastery, String desc) {
        this.schoolIndex = school;
        this.tier = tier;
        this.manaCost = mana;
        this.cooldownTicks = cooldown;
        this.castType = type;
        this.masteryRequired = mastery;
        this.description = desc;
    }

    public int getIndex() { return ordinal(); }

    public static ArcanaSpell byIndex(int idx) {
        ArcanaSpell[] vals = values();
        return (idx >= 0 && idx < vals.length) ? vals[idx] : MANA_BOLT;
    }

    public String getSchoolName() {
        return switch (schoolIndex) {
            case 0 -> "Pyromancy";
            case 1 -> "Cryomancy";
            case 2 -> "Electromancy";
            case 3 -> "Geomancy";
            case 4 -> "Aeromancy";
            case 5 -> "Umbramancy";
            case 6 -> "Luxomancy";
            case 7 -> "Arcanomancy";
            default -> "Unknown";
        };
    }

    public enum CastType {
        PROJECTILE, BEAM, INSTANT, CONSTRUCT, SUMMON, AURA, CHARGE
    }
}
