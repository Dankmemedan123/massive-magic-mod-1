package com.arcana.entity;

import com.arcana.ArcanaMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Registers ALL entities for Arcana mod.
 *
 * Entity count:
 *   Overworld hostile:    10
 *   Arcane Realm hostile:  8
 *   Shadow Depths hostile:  7
 *   Celestial Spire hostile: 5
 *   Passive/neutral:       12
 *   Bosses:                 8
 *   NPCs:                   6
 *   TOTAL:                 56 entities
 */
public class ArcanaEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ArcanaMod.MOD_ID);

    // ═══ OVERWORLD HOSTILE (10) ═══
    public static final RegistryObject<EntityType<ArcanaMob>> MANA_WISP =
            registerMob("mana_wisp", 0.6f, 0.6f, MobCategory.MONSTER);
    public static final RegistryObject<EntityType<ArcanaMob>> CRYSTAL_GOLEM =
            registerMob("crystal_golem", 1.2f, 2.1f, MobCategory.MONSTER);
    public static final RegistryObject<EntityType<ArcanaMob>> SHADOW_WRAITH =
            registerMob("shadow_wraith", 0.6f, 1.8f, MobCategory.MONSTER);
    public static final RegistryObject<EntityType<ArcanaMob>> ARCANE_SKELETON =
            registerMob("arcane_skeleton", 0.6f, 1.99f, MobCategory.MONSTER);
    public static final RegistryObject<EntityType<ArcanaMob>> ENCHANTED_SPIDER =
            registerMob("enchanted_spider", 1.4f, 0.9f, MobCategory.MONSTER);
    public static final RegistryObject<EntityType<ArcanaMob>> RUNE_GUARDIAN =
            registerMob("rune_guardian", 1.0f, 2.5f, MobCategory.MONSTER);
    public static final RegistryObject<EntityType<ArcanaMob>> ELEMENTAL_SPRITE_FIRE =
            registerMob("elemental_sprite_fire", 0.5f, 0.5f, MobCategory.MONSTER);
    public static final RegistryObject<EntityType<ArcanaMob>> ELEMENTAL_SPRITE_ICE =
            registerMob("elemental_sprite_ice", 0.5f, 0.5f, MobCategory.MONSTER);
    public static final RegistryObject<EntityType<ArcanaMob>> CORRUPTED_MAGE =
            registerMob("corrupted_mage", 0.6f, 1.95f, MobCategory.MONSTER);
    public static final RegistryObject<EntityType<ArcanaMob>> VOID_CRAWLER =
            registerMob("void_crawler", 0.8f, 0.5f, MobCategory.MONSTER);
    public static final RegistryObject<EntityType<ArcanaMob>> DARK_KNIGHT =
            registerMob("dark_knight", 0.6f, 1.95f, MobCategory.MONSTER);

    // ═══ ARCANE REALM HOSTILE (8) ═══
    public static final RegistryObject<EntityType<ArcanaMob>> ARCANE_SENTINEL =
            registerMob("arcane_sentinel", 1.0f, 2.5f, MobCategory.MONSTER);
    public static final RegistryObject<EntityType<ArcanaMob>> CRYSTAL_BEAST =
            registerMob("crystal_beast", 1.5f, 1.2f, MobCategory.MONSTER);
    public static final RegistryObject<EntityType<ArcanaMob>> MANA_ELEMENTAL =
            registerMob("mana_elemental", 1.0f, 1.8f, MobCategory.MONSTER);
    public static final RegistryObject<EntityType<ArcanaMob>> ARCANE_PHANTOM =
            registerMob("arcane_phantom", 0.9f, 0.5f, MobCategory.MONSTER);
    public static final RegistryObject<EntityType<ArcanaMob>> GOLEM_SOLDIER =
            registerMob("golem_soldier", 1.4f, 2.7f, MobCategory.MONSTER);
    public static final RegistryObject<EntityType<ArcanaMob>> SPELL_WEAVER =
            registerMob("spell_weaver", 0.6f, 1.95f, MobCategory.MONSTER);
    public static final RegistryObject<EntityType<ArcanaMob>> PRISM_SPIDER =
            registerMob("prism_spider", 1.4f, 0.9f, MobCategory.MONSTER);
    public static final RegistryObject<EntityType<ArcanaMob>> AETHER_KNIGHT =
            registerMob("aether_knight", 0.6f, 1.95f, MobCategory.MONSTER);

    // ═══ SHADOW DEPTHS HOSTILE (7) ═══
    public static final RegistryObject<EntityType<ArcanaMob>> SHADOW_STALKER =
            registerMob("shadow_stalker", 0.6f, 1.8f, MobCategory.MONSTER);
    public static final RegistryObject<EntityType<ArcanaMob>> BONE_REVENANT =
            registerMob("bone_revenant", 0.6f, 1.99f, MobCategory.MONSTER);
    public static final RegistryObject<EntityType<ArcanaMob>> VOID_LEECH =
            registerMob("void_leech", 0.8f, 0.4f, MobCategory.MONSTER);
    public static final RegistryObject<EntityType<ArcanaMob>> FUNGAL_ZOMBIE =
            registerMob("fungal_zombie", 0.6f, 1.95f, MobCategory.MONSTER);
    public static final RegistryObject<EntityType<ArcanaMob>> SHADOW_HOUND =
            registerMob("shadow_hound", 0.8f, 0.8f, MobCategory.MONSTER);
    public static final RegistryObject<EntityType<ArcanaMob>> LICH_MINION =
            registerMob("lich_minion", 0.6f, 1.99f, MobCategory.MONSTER);
    public static final RegistryObject<EntityType<ArcanaMob>> VOID_TENTACLE =
            registerMob("void_tentacle", 0.8f, 2.5f, MobCategory.MONSTER);

    // ═══ CELESTIAL SPIRE HOSTILE (5) ═══
    public static final RegistryObject<EntityType<ArcanaMob>> STAR_GUARDIAN =
            registerMob("star_guardian", 0.6f, 1.95f, MobCategory.MONSTER);
    public static final RegistryObject<EntityType<ArcanaMob>> CRYSTAL_DRAGON =
            registerMob("crystal_dragon", 2.0f, 1.5f, MobCategory.MONSTER);
    public static final RegistryObject<EntityType<ArcanaMob>> CELESTIAL_GOLEM =
            registerMob("celestial_golem", 2.0f, 3.5f, MobCategory.MONSTER);
    public static final RegistryObject<EntityType<ArcanaMob>> LIGHT_WISP =
            registerMob("light_wisp", 0.5f, 0.5f, MobCategory.MONSTER);
    public static final RegistryObject<EntityType<ArcanaMob>> ETERNIUM_CONSTRUCT =
            registerMob("eternium_construct", 1.4f, 2.7f, MobCategory.MONSTER);

    // ═══ PASSIVE / NEUTRAL (12) ═══
    public static final RegistryObject<EntityType<ArcanaPassiveMob>> MANA_BUTTERFLY =
            registerPassive("mana_butterfly", 0.3f, 0.3f, MobCategory.AMBIENT);
    public static final RegistryObject<EntityType<ArcanaPassiveMob>> CRYSTAL_DEER =
            registerPassive("crystal_deer", 0.9f, 1.3f, MobCategory.CREATURE);
    public static final RegistryObject<EntityType<ArcanaPassiveMob>> ARCANE_FOX =
            registerPassive("arcane_fox", 0.6f, 0.7f, MobCategory.CREATURE);
    public static final RegistryObject<EntityType<ArcanaPassiveMob>> LUMINOUS_JELLYFISH =
            registerPassive("luminous_jellyfish", 0.7f, 0.7f, MobCategory.AMBIENT);
    public static final RegistryObject<EntityType<ArcanaPassiveMob>> SHADOW_CAT =
            registerPassive("shadow_cat", 0.6f, 0.7f, MobCategory.CREATURE);
    public static final RegistryObject<EntityType<ArcanaPassiveMob>> STAR_BIRD =
            registerPassive("star_bird", 0.5f, 0.5f, MobCategory.CREATURE);
    public static final RegistryObject<EntityType<ArcanaPassiveMob>> STORM_EAGLE =
            registerPassive("storm_eagle", 1.0f, 0.8f, MobCategory.CREATURE);
    public static final RegistryObject<EntityType<ArcanaPassiveMob>> FROST_STRIDER =
            registerPassive("frost_strider", 1.4f, 0.9f, MobCategory.CREATURE);
    public static final RegistryObject<EntityType<ArcanaPassiveMob>> FIRE_BEETLE =
            registerPassive("fire_beetle", 0.8f, 0.6f, MobCategory.CREATURE);
    public static final RegistryObject<EntityType<ArcanaPassiveMob>> ARCTIC_FOX =
            registerPassive("arctic_fox", 0.6f, 0.7f, MobCategory.CREATURE);
    public static final RegistryObject<EntityType<ArcanaPassiveMob>> PIXIE =
            registerPassive("pixie", 0.3f, 0.5f, MobCategory.CREATURE);
    public static final RegistryObject<EntityType<ArcanaPassiveMob>> SOUL_WISP =
            registerPassive("soul_wisp", 0.4f, 0.4f, MobCategory.AMBIENT);

    // ═══ BOSSES (8) ═══
    public static final RegistryObject<EntityType<BossEntity>> CRYSTAL_GOLEM_KING =
            registerBoss("crystal_golem_king", 2.0f, 3.5f);
    public static final RegistryObject<EntityType<BossEntity>> CORRUPTED_MAGE_BOSS =
            registerBoss("corrupted_mage_boss", 0.8f, 2.5f);
    public static final RegistryObject<EntityType<BossEntity>> SHADOW_LICH =
            registerBoss("shadow_lich", 0.8f, 2.5f);
    public static final RegistryObject<EntityType<BossEntity>> THE_ARCHON =
            registerBoss("the_archon", 1.5f, 3.0f);
    public static final RegistryObject<EntityType<BossEntity>> INFERNAL_DRAKE =
            registerBoss("infernal_drake", 3.0f, 2.0f);
    public static final RegistryObject<EntityType<BossEntity>> FROST_WYRM =
            registerBoss("frost_wyrm", 3.0f, 2.0f);
    public static final RegistryObject<EntityType<BossEntity>> ARCANE_DRAGON =
            registerBoss("arcane_dragon", 5.0f, 4.0f);
    public static final RegistryObject<EntityType<BossEntity>> VOID_INCARNATE =
            registerBoss("void_incarnate", 3.0f, 5.0f);

    // ═══ NPCs (6) — use passive so they're friendly ═══
    public static final RegistryObject<EntityType<ArcanaPassiveMob>> WANDERING_MAGE =
            registerPassive("wandering_mage", 0.6f, 1.95f, MobCategory.CREATURE);
    public static final RegistryObject<EntityType<ArcanaPassiveMob>> MAGE_GUILD_MASTER =
            registerPassive("mage_guild_master", 0.6f, 1.95f, MobCategory.CREATURE);
    public static final RegistryObject<EntityType<ArcanaPassiveMob>> ALCHEMIST_NPC =
            registerPassive("alchemist_npc", 0.6f, 1.95f, MobCategory.CREATURE);
    public static final RegistryObject<EntityType<ArcanaPassiveMob>> RUNESMITH_NPC =
            registerPassive("runesmith_npc", 0.6f, 1.95f, MobCategory.CREATURE);
    public static final RegistryObject<EntityType<ArcanaPassiveMob>> CRYSTAL_MERCHANT =
            registerPassive("crystal_merchant", 0.6f, 1.95f, MobCategory.CREATURE);
    public static final RegistryObject<EntityType<ArcanaPassiveMob>> LOREKEEPER =
            registerPassive("lorekeeper", 0.6f, 1.95f, MobCategory.CREATURE);

    // ─── Registration helpers ───

    private static RegistryObject<EntityType<ArcanaMob>> registerMob(
            String name, float width, float height, MobCategory category) {
        return ENTITIES.register(name, () ->
                EntityType.Builder.<ArcanaMob>of(ArcanaMob::new, category)
                        .sized(width, height)
                        .build(new ResourceLocation(ArcanaMod.MOD_ID, name).toString()));
    }

    private static RegistryObject<EntityType<ArcanaPassiveMob>> registerPassive(
            String name, float width, float height, MobCategory category) {
        return ENTITIES.register(name, () ->
                EntityType.Builder.<ArcanaPassiveMob>of(ArcanaPassiveMob::new, category)
                        .sized(width, height)
                        .build(new ResourceLocation(ArcanaMod.MOD_ID, name).toString()));
    }

    private static RegistryObject<EntityType<BossEntity>> registerBoss(
            String name, float width, float height) {
        return ENTITIES.register(name, () ->
                EntityType.Builder.<BossEntity>of(BossEntity::new, MobCategory.MONSTER)
                        .sized(width, height)
                        .fireImmune()
                        .build(new ResourceLocation(ArcanaMod.MOD_ID, name).toString()));
    }
}
