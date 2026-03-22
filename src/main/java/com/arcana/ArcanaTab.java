package com.arcana;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ArcanaTab {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ArcanaMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> ARCANA_TAB = TABS.register("arcana_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.arcana"))
                    .icon(() -> {
                        var ingot = ArcanaItems.INGOTS.get("arcanite");
                        return ingot != null ? new ItemStack(ingot.get()) : ItemStack.EMPTY;
                    })
                    .displayItems((params, output) -> {
                        // Add ALL items from every registry map
                        ArcanaItems.RAW_ORES.values().forEach(r -> output.accept(r.get()));
                        ArcanaItems.INGOTS.values().forEach(r -> output.accept(r.get()));
                        ArcanaItems.NUGGETS.values().forEach(r -> output.accept(r.get()));
                        ArcanaItems.GEMS.values().forEach(r -> output.accept(r.get()));
                        ArcanaItems.ESSENCES.values().forEach(r -> output.accept(r.get()));
                        ArcanaItems.TOOLS.values().forEach(r -> output.accept(r.get()));
                        ArcanaItems.ARMOR.values().forEach(r -> output.accept(r.get()));
                        ArcanaItems.WEAPONS.values().forEach(r -> output.accept(r.get()));
                        ArcanaItems.STAFFS.values().forEach(r -> output.accept(r.get()));

                        // Add all block items
                        ArcanaBlocks.ORE_BLOCKS.values().forEach(r -> output.accept(r.get()));
                        ArcanaBlocks.DEEPSLATE_ORE_BLOCKS.values().forEach(r -> output.accept(r.get()));
                        ArcanaBlocks.STORAGE_BLOCKS.values().forEach(r -> output.accept(r.get()));
                        ArcanaBlocks.GEM_BLOCKS.values().forEach(r -> output.accept(r.get()));
                        ArcanaBlocks.MACHINE_BLOCKS.values().forEach(r -> output.accept(r.get()));
                        ArcanaBlocks.DECORATIVE_BLOCKS.values().forEach(r -> output.accept(r.get()));
                    })
                    .build());
}
