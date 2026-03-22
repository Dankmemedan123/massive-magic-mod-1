package com.arcana.command;

import com.arcana.magic.ArcanaSpell;
import com.arcana.magic.ManaCapability;
import com.arcana.network.ArcanaNetwork;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class ArcanaCommands {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("arcana")
            // /arcana mana [set|add] <amount>
            .then(Commands.literal("mana")
                .then(Commands.literal("set")
                    .then(Commands.argument("amount", IntegerArgumentType.integer(0, 1000))
                        .executes(ctx -> {
                            ServerPlayer p = ctx.getSource().getPlayerOrException();
                            int amount = IntegerArgumentType.getInteger(ctx, "amount");
                            p.getCapability(ManaCapability.MANA_CAP).ifPresent(d -> {
                                d.setMana(amount);
                                ArcanaNetwork.syncToClient(p);
                            });
                            ctx.getSource().sendSuccess(() -> Component.literal("Mana set to " + amount), true);
                            return 1;
                        })))
                .then(Commands.literal("add")
                    .then(Commands.argument("amount", IntegerArgumentType.integer(-1000, 1000))
                        .executes(ctx -> {
                            ServerPlayer p = ctx.getSource().getPlayerOrException();
                            int amount = IntegerArgumentType.getInteger(ctx, "amount");
                            p.getCapability(ManaCapability.MANA_CAP).ifPresent(d -> {
                                d.setMana(d.getMana() + amount);
                                ArcanaNetwork.syncToClient(p);
                            });
                            ctx.getSource().sendSuccess(() -> Component.literal("Added " + amount + " mana"), true);
                            return 1;
                        }))))

            // /arcana mastery <school> <level>
            .then(Commands.literal("mastery")
                .then(Commands.argument("school", IntegerArgumentType.integer(0, 7))
                    .then(Commands.argument("level", IntegerArgumentType.integer(0, 100))
                        .executes(ctx -> {
                            ServerPlayer p = ctx.getSource().getPlayerOrException();
                            int school = IntegerArgumentType.getInteger(ctx, "school");
                            int level = IntegerArgumentType.getInteger(ctx, "level");
                            p.getCapability(ManaCapability.MANA_CAP).ifPresent(d -> {
                                d.setMastery(school, level);
                                ArcanaNetwork.syncToClient(p);
                            });
                            String schoolName = ArcanaSpell.byIndex(school * 5).getSchoolName();
                            ctx.getSource().sendSuccess(() -> Component.literal(schoolName + " mastery set to " + level), true);
                            return 1;
                        }))))

            // /arcana learn <spellIndex>
            .then(Commands.literal("learn")
                .then(Commands.argument("spell", IntegerArgumentType.integer(0, 39))
                    .executes(ctx -> {
                        ServerPlayer p = ctx.getSource().getPlayerOrException();
                        int idx = IntegerArgumentType.getInteger(ctx, "spell");
                        p.getCapability(ManaCapability.MANA_CAP).ifPresent(d -> {
                            d.learnSpell(idx);
                            ArcanaNetwork.syncToClient(p);
                        });
                        ArcanaSpell spell = ArcanaSpell.byIndex(idx);
                        ctx.getSource().sendSuccess(() -> Component.literal("Learned: " + spell.name()), true);
                        return 1;
                    })))

            // /arcana learnall
            .then(Commands.literal("learnall")
                .requires(cs -> cs.hasPermission(2))
                .executes(ctx -> {
                    ServerPlayer p = ctx.getSource().getPlayerOrException();
                    p.getCapability(ManaCapability.MANA_CAP).ifPresent(d -> {
                        for (int i = 0; i < 40; i++) d.learnSpell(i);
                        for (int i = 0; i < 8; i++) d.setMastery(i, 100);
                        d.setMaxMana(500);
                        d.setMana(500);
                        ArcanaNetwork.syncToClient(p);
                    });
                    ctx.getSource().sendSuccess(() -> Component.literal("§dAll spells learned! All schools mastered!"), true);
                    return 1;
                }))

            // /arcana info
            .then(Commands.literal("info")
                .executes(ctx -> {
                    ServerPlayer p = ctx.getSource().getPlayerOrException();
                    p.getCapability(ManaCapability.MANA_CAP).ifPresent(d -> {
                        p.sendSystemMessage(Component.literal("§d═══ Arcana Status ═══"));
                        p.sendSystemMessage(Component.literal(String.format("§bMana: §f%.0f / %.0f §7(+%.1f/s)",
                                d.getMana(), d.getMaxMana(), d.getRegenRate())));
                        for (int i = 0; i < 8; i++) {
                            String school = ArcanaSpell.byIndex(i * 5).getSchoolName();
                            p.sendSystemMessage(Component.literal(String.format("§e%s: §f%d/100", school, d.getMastery(i))));
                        }
                        int known = 0;
                        for (int i = 0; i < 40; i++) if (d.knowsSpell(i)) known++;
                        p.sendSystemMessage(Component.literal(String.format("§aSpells known: §f%d/40", known)));
                    });
                    return 1;
                }))
        );
    }
}
