package com.arcana;

import com.arcana.command.ArcanaCommands;
import com.arcana.data.ArcanaEffects;
import com.arcana.data.ArcanaEnchantments;
import com.arcana.entity.ArcanaEntities;
import com.arcana.entity.ArcanaMob;
import com.arcana.entity.ArcanaPassiveMob;
import com.arcana.entity.BossEntity;
import com.arcana.magic.ArcanaSpell;
import com.arcana.network.ArcanaNetwork;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ArcanaMod.MOD_ID)
public class ArcanaMod {
    public static final String MOD_ID = "arcana";
    public static final Logger LOGGER = LogManager.getLogger();

    public ArcanaMod() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register ALL deferred registers (all phases)
        ArcanaItems.ITEMS.register(modBus);
        ArcanaBlocks.BLOCKS.register(modBus);
        ArcanaTab.TABS.register(modBus);
        ArcanaEntities.ENTITIES.register(modBus);         // Phase 5
        ArcanaEnchantments.ENCHANTMENTS.register(modBus);  // Phase 6
        ArcanaEffects.EFFECTS.register(modBus);            // Phase 6

        modBus.addListener(this::commonSetup);
        modBus.addListener(this::onEntityAttributes);
        MinecraftForge.EVENT_BUS.register(this);

        LOGGER.info("[Arcana] ═══════════════════════════════════════════════");
        LOGGER.info("[Arcana] Initializing Arcana — Magic, Tech & Adventure");
        LOGGER.info("[Arcana] Phase 1: {} ores, {} alloys, {} gems, {} essences",
                ArcanaMaterials.OreMaterial.values().length,
                ArcanaMaterials.AlloyMaterial.values().length,
                ArcanaMaterials.GemMaterial.values().length,
                ArcanaMaterials.EssenceMaterial.values().length);
        LOGGER.info("[Arcana] Phase 1: {} tools, {} armor, 12 weapons, 12 staffs, 10 wands",
                ArcanaMaterials.ArcanaToolMaterial.values().length * 5,
                ArcanaMaterials.ArcanaArmorMaterial.values().length * 4);
        LOGGER.info("[Arcana] Phase 2: {} spells, {} schools, 13 combos",
                ArcanaSpell.values().length, ArcanaMaterials.MagicSchool.values().length);
        LOGGER.info("[Arcana] Phase 3: 20 biomes, 44 structures, 6 ore veins");
        LOGGER.info("[Arcana] Phase 4: 3 dimensions (Arcane Realm, Shadow Depths, Celestial Spire)");
        LOGGER.info("[Arcana] Phase 5: 56 entities (30 hostile, 12 passive, 8 bosses, 6 NPCs)");
        LOGGER.info("[Arcana] Phase 6: 15 enchantments, 12 effects, 40 scrolls, 17 accessories");
        LOGGER.info("[Arcana] ═══════════════════════════════════════════════");
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ArcanaNetwork.register();
            LOGGER.info("[Arcana] Network registered");
        });
    }

    /** Register attributes for ALL entity types */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private void onEntityAttributes(EntityAttributeCreationEvent event) {
        ArcanaEntities.ENTITIES.getEntries().forEach(entry -> {
            String name = entry.getId().getPath();
            try {
                EntityType rawType = entry.get();
                if (name.contains("crystal_golem_king") || name.contains("corrupted_mage_boss")
                        || name.contains("shadow_lich") || name.contains("the_archon")
                        || name.contains("infernal_drake") || name.contains("frost_wyrm")
                        || name.contains("arcane_dragon") || name.contains("void_incarnate")) {
                    event.put(rawType, BossEntity.createDefaultBossAttributes().build());
                } else if (name.contains("butterfly") || name.contains("deer") || name.contains("fox")
                        || name.contains("jellyfish") || name.contains("cat") || name.contains("bird")
                        || name.contains("eagle") || name.contains("strider") || name.contains("beetle")
                        || name.contains("pixie") || name.contains("soul_wisp")
                        || name.contains("wandering") || name.contains("guild") || name.contains("alchemist")
                        || name.contains("runesmith") || name.contains("merchant") || name.contains("lorekeeper")) {
                    event.put(rawType, ArcanaPassiveMob.createPassiveAttributes().build());
                } else {
                    event.put(rawType, ArcanaMob.createDefaultAttributes().build());
                }
            } catch (Exception e) {
                LOGGER.warn("[Arcana] Failed to register attributes for {}: {}", name, e.getMessage());
            }
        });
        LOGGER.info("[Arcana] Entity attributes registered for {} entity types",
                ArcanaEntities.ENTITIES.getEntries().size());
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        ArcanaCommands.register(event.getDispatcher());
        LOGGER.info("[Arcana] Commands registered");
    }
}
