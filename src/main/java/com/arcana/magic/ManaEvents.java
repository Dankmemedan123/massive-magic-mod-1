package com.arcana.magic;

import com.arcana.ArcanaMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ArcanaMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ManaEvents {

    private static final ResourceLocation MANA_CAP_ID = new ResourceLocation(ArcanaMod.MOD_ID, "mana");

    // ─── Attach mana capability to all players ───
    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(MANA_CAP_ID, new ManaCapability());
        }
    }

    // ─── Register capability class ───
    @Mod.EventBusSubscriber(modid = ArcanaMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {
        @SubscribeEvent
        public static void onRegisterCaps(RegisterCapabilitiesEvent event) {
            event.register(ManaCapability.ManaData.class);
        }
    }

    // ─── Mana regeneration every tick ───
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (event.player.level().isClientSide) return;

        event.player.getCapability(ManaCapability.MANA_CAP).ifPresent(mana -> {
            // Base regen
            mana.regenTick();

            // Armor set bonus detection (every 20 ticks = 1 second)
            if (event.player.tickCount % 20 == 0) {
                checkArmorSetBonuses(event.player, mana);
            }
        });
    }

    // ─── Preserve mana on death (copy to new player entity) ───
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().reviveCaps();
            event.getOriginal().getCapability(ManaCapability.MANA_CAP).ifPresent(oldMana -> {
                event.getEntity().getCapability(ManaCapability.MANA_CAP).ifPresent(newMana -> {
                    newMana.load(oldMana.save());
                    // Reset current mana to half on death
                    newMana.setMana(newMana.getMaxMana() * 0.5f);
                });
            });
            event.getOriginal().invalidateCaps();
        }
    }

    // ─── Essence drops from mob kills ───
    @SubscribeEvent
    public static void onMobDeath(LivingDeathEvent event) {
        if (event.getEntity().level().isClientSide) return;
        if (!(event.getSource().getEntity() instanceof Player player)) return;

        LivingEntity mob = event.getEntity();

        // Grant mastery for kills
        player.getCapability(ManaCapability.MANA_CAP).ifPresent(mana -> {
            // If player recently cast a spell from a school, grant +2 mastery to that school
            // (Simplified: grant to highest mastery school since we can't track last-cast easily here)
            int highestSchool = 0;
            int highestVal = 0;
            for (int i = 0; i < 8; i++) {
                if (mana.getMastery(i) > highestVal) {
                    highestVal = mana.getMastery(i);
                    highestSchool = i;
                }
            }
            mana.addMastery(highestSchool, 2);
        });

        // Essence drop logic based on environmental rules (from Integration Audit)
        // Fire Essence: mob on fire or near lava
        // Ice Essence: in snowy biome
        // Lightning Essence: during thunderstorm
        // Earth Essence: underground (y < 50)
        // Wind Essence: above y=128
        // Shadow Essence: at night in darkness (light < 4)
        // Light Essence: during day at noon in sunlight
        // Arcane Essence: from mod's magical mobs (checked by entity type)

        // These are placeholder drops — actual item drops added when entity system is complete
        // The logic framework is here for Phase 5 to hook into
    }

    // ─── Armor set bonus detection ───
    private static void checkArmorSetBonuses(Player player, ManaCapability.ManaData mana) {
        // Count armor pieces from each set
        // In full implementation, each armor piece stores its set ID in NBT
        // For now: check if all 4 armor slots have items from same mod material

        int arcanaArmorCount = 0;
        for (ItemStack stack : player.getArmorSlots()) {
            if (!stack.isEmpty()) {
                String id = stack.getItem().toString();
                if (id.contains("arcana")) arcanaArmorCount++;
            }
        }

        // Full set = 4 pieces from same material
        // Specific bonuses applied based on material name matching
        // This will be expanded in Phase 6 with proper NBT checks
    }
}
