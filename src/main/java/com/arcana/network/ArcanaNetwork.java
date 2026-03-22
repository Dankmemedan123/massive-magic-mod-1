package com.arcana.network;

import com.arcana.ArcanaMod;
import com.arcana.magic.ArcanaSpell;
import com.arcana.magic.ManaCapability;
import com.arcana.magic.SpellCastHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class ArcanaNetwork {
    private static final String PROTOCOL = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(ArcanaMod.MOD_ID, "main"),
            () -> PROTOCOL, PROTOCOL::equals, PROTOCOL::equals);

    public static void register() {
        int id = 0;
        CHANNEL.registerMessage(id++, CastSpellPacket.class,
                CastSpellPacket::encode, CastSpellPacket::decode, CastSpellPacket::handle);
        CHANNEL.registerMessage(id++, SyncManaPacket.class,
                SyncManaPacket::encode, SyncManaPacket::decode, SyncManaPacket::handle);
        CHANNEL.registerMessage(id++, SelectSpellPacket.class,
                SelectSpellPacket::encode, SelectSpellPacket::decode, SelectSpellPacket::handle);
    }

    // ─── Client → Server: Cast spell ───
    public static class CastSpellPacket {
        private final int spellIndex;

        public CastSpellPacket(int spellIndex) { this.spellIndex = spellIndex; }

        public static void encode(CastSpellPacket pkt, FriendlyByteBuf buf) {
            buf.writeInt(pkt.spellIndex);
        }

        public static CastSpellPacket decode(FriendlyByteBuf buf) {
            return new CastSpellPacket(buf.readInt());
        }

        public static void handle(CastSpellPacket pkt,
                                   Supplier<net.minecraftforge.network.NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();
                if (player != null) {
                    ArcanaSpell spell = ArcanaSpell.byIndex(pkt.spellIndex);
                    SpellCastHandler.cast(player, spell, player.level());
                    // Sync mana back to client
                    syncToClient(player);
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }

    // ─── Server → Client: Sync mana data ───
    public static class SyncManaPacket {
        private final float mana, maxMana, regenRate;
        private final int[] mastery;
        private final int selectedSpell;

        public SyncManaPacket(float mana, float maxMana, float regenRate, int[] mastery, int selectedSpell) {
            this.mana = mana;
            this.maxMana = maxMana;
            this.regenRate = regenRate;
            this.mastery = mastery;
            this.selectedSpell = selectedSpell;
        }

        public static void encode(SyncManaPacket pkt, FriendlyByteBuf buf) {
            buf.writeFloat(pkt.mana);
            buf.writeFloat(pkt.maxMana);
            buf.writeFloat(pkt.regenRate);
            for (int i = 0; i < 8; i++) buf.writeInt(pkt.mastery[i]);
            buf.writeInt(pkt.selectedSpell);
        }

        public static SyncManaPacket decode(FriendlyByteBuf buf) {
            float mana = buf.readFloat();
            float maxMana = buf.readFloat();
            float regenRate = buf.readFloat();
            int[] mastery = new int[8];
            for (int i = 0; i < 8; i++) mastery[i] = buf.readInt();
            int selected = buf.readInt();
            return new SyncManaPacket(mana, maxMana, regenRate, mastery, selected);
        }

        public static void handle(SyncManaPacket pkt,
                                   Supplier<net.minecraftforge.network.NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                // Client-side: update local player's mana capability
                var mc = net.minecraft.client.Minecraft.getInstance();
                if (mc.player != null) {
                    mc.player.getCapability(ManaCapability.MANA_CAP).ifPresent(data -> {
                        data.setMana(pkt.mana);
                        data.setMaxMana(pkt.maxMana);
                        data.setRegenRate(pkt.regenRate);
                        for (int i = 0; i < 8; i++) data.setMastery(i, pkt.mastery[i]);
                        data.setSelectedSpellIndex(pkt.selectedSpell);
                    });
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }

    // ─── Client → Server: Select spell ───
    public static class SelectSpellPacket {
        private final int spellIndex;

        public SelectSpellPacket(int spellIndex) { this.spellIndex = spellIndex; }

        public static void encode(SelectSpellPacket pkt, FriendlyByteBuf buf) {
            buf.writeInt(pkt.spellIndex);
        }

        public static SelectSpellPacket decode(FriendlyByteBuf buf) {
            return new SelectSpellPacket(buf.readInt());
        }

        public static void handle(SelectSpellPacket pkt,
                                   Supplier<net.minecraftforge.network.NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();
                if (player != null) {
                    player.getCapability(ManaCapability.MANA_CAP).ifPresent(data -> {
                        data.setSelectedSpellIndex(pkt.spellIndex);
                    });
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }

    // ─── Helper: sync player's mana to their client ───
    public static void syncToClient(ServerPlayer player) {
        player.getCapability(ManaCapability.MANA_CAP).ifPresent(data -> {
            int[] mastery = new int[8];
            for (int i = 0; i < 8; i++) mastery[i] = data.getMastery(i);
            CHANNEL.send(PacketDistributor.PLAYER.with(() -> player),
                    new SyncManaPacket(data.getMana(), data.getMaxMana(), data.getRegenRate(),
                            mastery, data.getSelectedSpellIndex()));
        });
    }
}
