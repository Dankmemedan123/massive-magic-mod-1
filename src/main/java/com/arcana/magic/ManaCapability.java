package com.arcana.magic;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;

/**
 * Player mana capability — stores mana, max mana, regen rate, mastery levels.
 * Attached to every player via AttachCapabilitiesEvent.
 */
public class ManaCapability implements ICapabilitySerializable<CompoundTag> {

    public static final Capability<ManaData> MANA_CAP = CapabilityManager.get(new CapabilityToken<>() {});

    private final ManaData data = new ManaData();
    private final LazyOptional<ManaData> optional = LazyOptional.of(() -> data);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return cap == MANA_CAP ? optional.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() { return data.save(); }

    @Override
    public void deserializeNBT(CompoundTag tag) { data.load(tag); }

    public static class ManaData {
        private float mana = 100f;
        private float maxMana = 100f;
        private float regenRate = 3f; // per second
        private final int[] mastery = new int[8]; // one per school
        private final boolean[] spellsKnown = new boolean[40]; // one per spell
        private int selectedSpellIndex = 0;
        private int accessorySlot1 = -1; // item ID or -1
        private int accessorySlot2 = -1;

        // ─── Mana ───
        public float getMana() { return mana; }
        public float getMaxMana() { return maxMana; }
        public float getRegenRate() { return regenRate; }

        public void setMana(float val) { mana = Math.max(0, Math.min(val, maxMana)); }
        public void setMaxMana(float val) { maxMana = Math.max(1, val); mana = Math.min(mana, maxMana); }
        public void setRegenRate(float val) { regenRate = Math.max(0, val); }

        public boolean consumeMana(float amount) {
            if (mana >= amount) { mana -= amount; return true; }
            return false;
        }

        public void regenTick() {
            // Called every tick — regen is per-second so divide by 20
            mana = Math.min(maxMana, mana + regenRate / 20f);
        }

        public void addMaxMana(float bonus) { maxMana += bonus; }

        // ─── Mastery (0-100 per school) ───
        public int getMastery(int schoolIndex) {
            return (schoolIndex >= 0 && schoolIndex < 8) ? mastery[schoolIndex] : 0;
        }

        public void addMastery(int schoolIndex, int amount) {
            if (schoolIndex >= 0 && schoolIndex < 8) {
                mastery[schoolIndex] = Math.min(100, mastery[schoolIndex] + amount);
            }
        }

        public void setMastery(int schoolIndex, int value) {
            if (schoolIndex >= 0 && schoolIndex < 8) {
                mastery[schoolIndex] = Math.max(0, Math.min(100, value));
            }
        }

        // ─── Spells Known ───
        public boolean knowsSpell(int spellIndex) {
            return (spellIndex >= 0 && spellIndex < 40) && spellsKnown[spellIndex];
        }

        public void learnSpell(int spellIndex) {
            if (spellIndex >= 0 && spellIndex < 40) spellsKnown[spellIndex] = true;
        }

        public int getSelectedSpellIndex() { return selectedSpellIndex; }
        public void setSelectedSpellIndex(int idx) { selectedSpellIndex = Math.max(0, Math.min(39, idx)); }

        // ─── Serialization ───
        public CompoundTag save() {
            CompoundTag tag = new CompoundTag();
            tag.putFloat("mana", mana);
            tag.putFloat("maxMana", maxMana);
            tag.putFloat("regenRate", regenRate);
            tag.putIntArray("mastery", mastery);
            tag.putInt("selectedSpell", selectedSpellIndex);
            // Pack booleans into bytes
            byte[] spellBytes = new byte[5]; // 40 bits = 5 bytes
            for (int i = 0; i < 40; i++) {
                if (spellsKnown[i]) spellBytes[i / 8] |= (1 << (i % 8));
            }
            tag.putByteArray("spellsKnown", spellBytes);
            return tag;
        }

        public void load(CompoundTag tag) {
            mana = tag.getFloat("mana");
            maxMana = tag.contains("maxMana") ? tag.getFloat("maxMana") : 100f;
            regenRate = tag.contains("regenRate") ? tag.getFloat("regenRate") : 3f;
            if (tag.contains("mastery")) {
                int[] loaded = tag.getIntArray("mastery");
                System.arraycopy(loaded, 0, mastery, 0, Math.min(loaded.length, 8));
            }
            selectedSpellIndex = tag.getInt("selectedSpell");
            if (tag.contains("spellsKnown")) {
                byte[] spellBytes = tag.getByteArray("spellsKnown");
                for (int i = 0; i < 40 && i / 8 < spellBytes.length; i++) {
                    spellsKnown[i] = (spellBytes[i / 8] & (1 << (i % 8))) != 0;
                }
            }
        }
    }
}
