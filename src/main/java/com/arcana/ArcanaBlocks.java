package com.arcana;

import com.arcana.ArcanaMaterials.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;

/**
 * Registers ALL blocks for Arcana.
 *
 * Block count breakdown:
 *   12 ore blocks + 12 deepslate ore blocks = 24
 *   12 ore storage blocks + 18 alloy storage blocks = 30
 *   10 gem storage blocks = 10
 *   25 machine/functional blocks = 25
 *   20+ decorative blocks = 20
 *   TOTAL: 109+ blocks (each with a BlockItem)
 */
public class ArcanaBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ArcanaMod.MOD_ID);

    // BlockItems are registered in the SAME item registry as ArcanaItems
    // to avoid duplicate DeferredRegister conflict (Forge only allows one per mod per registry)
    // We access ArcanaItems.ITEMS directly.

    // Lookup maps
    public static final Map<String, RegistryObject<Block>> ORE_BLOCKS = new LinkedHashMap<>();
    public static final Map<String, RegistryObject<Block>> DEEPSLATE_ORE_BLOCKS = new LinkedHashMap<>();
    public static final Map<String, RegistryObject<Block>> STORAGE_BLOCKS = new LinkedHashMap<>();
    public static final Map<String, RegistryObject<Block>> GEM_BLOCKS = new LinkedHashMap<>();
    public static final Map<String, RegistryObject<Block>> MACHINE_BLOCKS = new LinkedHashMap<>();
    public static final Map<String, RegistryObject<Block>> DECORATIVE_BLOCKS = new LinkedHashMap<>();

    static {
        // ─── ORE BLOCKS (12 ores × 2 variants = 24 blocks) ───
        for (OreMaterial mat : OreMaterial.values()) {
            ORE_BLOCKS.put(mat.name, registerWithItem(mat.name + "_ore",
                    () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)
                            .strength(mat.hardness, mat.hardness + 1.0f)
                            .requiresCorrectToolForDrops())));

            DEEPSLATE_ORE_BLOCKS.put(mat.name, registerWithItem("deepslate_" + mat.name + "_ore",
                    () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE)
                            .strength(mat.hardness + 1.5f, mat.hardness + 2.5f)
                            .requiresCorrectToolForDrops())));
        }

        // ─── ORE STORAGE BLOCKS (12 blocks) ───
        for (OreMaterial mat : OreMaterial.values()) {
            STORAGE_BLOCKS.put(mat.name, registerWithItem(mat.name + "_block",
                    () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                            .strength(5.0f, 6.0f))));
        }

        // ─── ALLOY STORAGE BLOCKS (18 blocks) ───
        for (AlloyMaterial mat : AlloyMaterial.values()) {
            STORAGE_BLOCKS.put(mat.name, registerWithItem(mat.name + "_block",
                    () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                            .strength(6.0f, 8.0f))));
        }

        // ─── GEM BLOCKS (10 blocks) ───
        for (GemMaterial mat : GemMaterial.values()) {
            GEM_BLOCKS.put(mat.name, registerWithItem(mat.name + "_block",
                    () -> new Block(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)
                            .strength(3.0f, 4.0f)
                            .sound(SoundType.AMETHYST)
                            .lightLevel(state -> 7))));
        }

        // ─── MACHINE BLOCKS (25 blocks) ───
        registerMachine("arcane_workbench", 3.5f);
        registerMachine("arcane_forge", 5.0f);
        registerMachine("mana_generator", 4.0f);
        registerMachine("solar_mana_collector", 3.0f);
        registerMachine("lunar_mana_collector", 3.0f);
        registerMachine("mana_conduit", 2.0f);
        registerMachine("mana_relay", 2.5f);
        registerMachine("mana_battery_basic", 4.0f);
        registerMachine("mana_battery_advanced", 5.0f);
        registerMachine("mana_battery_ultimate", 6.0f);
        registerMachine("crystal_grower", 3.5f);
        registerMachine("essence_extractor", 4.0f);
        registerMachine("mana_infuser", 4.5f);
        registerMachine("inscription_table", 3.0f);
        registerMachine("disenchanter", 4.0f);
        registerMachine("ritual_altar_core", 8.0f);
        registerMachine("ritual_pillar", 6.0f);
        registerMachine("warding_stone", 5.0f);
        registerMachine("teleportation_pad", 4.0f);
        registerMachine("mana_lamp", 2.0f);
        registerMachine("arcane_chest", 3.0f);
        registerMachine("crystal_vault", 5.0f);
        registerMachine("essence_jar", 2.0f);
        registerMachine("mana_tank", 3.5f);
        registerMachine("mana_detector", 2.0f);

        // ─── DECORATIVE BLOCKS (20 blocks) ───
        registerDecorative("arcane_stone");
        registerDecorative("arcane_stone_bricks");
        registerDecorative("arcane_stone_carved");
        registerDecorative("arcane_stone_pillar");
        registerDecorative("arcane_stone_chiseled");
        registerDecorative("arcane_stone_smooth");
        registerDecorative("glowing_arcane_stone");
        registerDecorative("ritual_stone");
        registerDecorative("runic_stone");
        registerDecorative("runic_stone_glowing");
        registerDecorative("shadow_stone");
        registerDecorative("celestial_stone");
        registerDecorative("crystal_glass");
        registerDecorative("crystal_glass_pane");
        registerDecorative("enchanted_planks");
        registerDecorative("enchanted_log");
        registerDecorative("enchanted_leaves");
        registerDecorative("mana_grass");
        registerDecorative("void_stone");
        registerDecorative("starlight_block");

        // ─── NATURAL / WORLDGEN BLOCKS ───
        registerWithItem("mana_crystal_cluster",
                () -> new Block(BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)
                        .lightLevel(s -> 10).sound(SoundType.AMETHYST_CLUSTER)));
        registerWithItem("enchanted_soil",
                () -> new Block(BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK)));
        registerWithItem("crystal_sand",
                () -> new Block(BlockBehaviour.Properties.copy(Blocks.SAND)
                        .lightLevel(s -> 4)));
        registerWithItem("shadow_moss",
                () -> new Block(BlockBehaviour.Properties.copy(Blocks.MOSS_BLOCK)));
        registerWithItem("starfall_stone",
                () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)
                        .lightLevel(s -> 3)));

        // ─── BLOCKS FROM INTEGRATION AUDIT — previously missing ───

        // Preparation magic blocks
        registerMachine("rune_trap_fire", 2.0f);
        registerMachine("rune_trap_ice", 2.0f);
        registerMachine("rune_trap_lightning", 2.0f);
        registerMachine("rune_trap_earth", 2.0f);
        registerMachine("ward_circle", 3.0f);
        registerMachine("spell_anchor", 4.0f);
        registerMachine("spell_trigger", 3.0f);

        // Natural / farmable
        registerWithItem("mana_berry_bush",
                () -> new Block(BlockBehaviour.Properties.copy(Blocks.SWEET_BERRY_BUSH)
                        .lightLevel(s -> 5).noCollission()));

        // Portal blocks
        registerWithItem("arcane_portal_frame",
                () -> new Block(BlockBehaviour.Properties.copy(Blocks.OBSIDIAN)
                        .strength(50.0f, 1200.0f).lightLevel(s -> 12)));
        registerWithItem("shadow_portal_frame",
                () -> new Block(BlockBehaviour.Properties.copy(Blocks.OBSIDIAN)
                        .strength(50.0f, 1200.0f).lightLevel(s -> 4)));
        registerWithItem("celestial_portal_frame",
                () -> new Block(BlockBehaviour.Properties.copy(Blocks.OBSIDIAN)
                        .strength(50.0f, 1200.0f).lightLevel(s -> 15)));
    }

    // ─── Helper methods ───

    private static RegistryObject<Block> registerWithItem(String name, java.util.function.Supplier<Block> blockSupplier) {
        RegistryObject<Block> block = BLOCKS.register(name, blockSupplier);
        ArcanaItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    private static void registerMachine(String name, float hardness) {
        MACHINE_BLOCKS.put(name, registerWithItem(name,
                () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                        .strength(hardness, hardness + 2.0f)
                        .requiresCorrectToolForDrops())));
    }

    private static void registerDecorative(String name) {
        DECORATIVE_BLOCKS.put(name, registerWithItem(name,
                () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)
                        .strength(2.0f, 6.0f))));
    }
}
