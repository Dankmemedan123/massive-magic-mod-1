package com.arcana.client;

import com.arcana.ArcanaMod;
import com.arcana.magic.ArcanaSpell;
import com.arcana.magic.ManaCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ArcanaMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ManaHudOverlay {

    private static final int BAR_WIDTH = 80;
    private static final int BAR_HEIGHT = 5;
    private static final int MANA_COLOR = 0xFF8844FF;      // Purple
    private static final int MANA_BG_COLOR = 0xFF222222;    // Dark grey
    private static final int MANA_LOW_COLOR = 0xFFFF4444;   // Red when low

    @SubscribeEvent
    public static void onRegisterOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("mana_bar", (gui, graphics, partialTick, width, height) -> {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null || mc.options.hideGui) return;

            mc.player.getCapability(ManaCapability.MANA_CAP).ifPresent(data -> {
                renderManaBar(graphics, width, height, data);
            });
        });
    }

    private static void renderManaBar(GuiGraphics graphics, int screenWidth, int screenHeight,
                                       ManaCapability.ManaData data) {
        int x = screenWidth / 2 - BAR_WIDTH / 2;
        int y = screenHeight - 48; // Above hotbar

        float manaPercent = data.getMaxMana() > 0 ? data.getMana() / data.getMaxMana() : 0;
        int filledWidth = (int) (BAR_WIDTH * manaPercent);

        // Background
        graphics.fill(x - 1, y - 1, x + BAR_WIDTH + 1, y + BAR_HEIGHT + 1, 0xFF000000);
        graphics.fill(x, y, x + BAR_WIDTH, y + BAR_HEIGHT, MANA_BG_COLOR);

        // Filled portion
        int color = manaPercent < 0.2f ? MANA_LOW_COLOR : MANA_COLOR;
        if (filledWidth > 0) {
            graphics.fill(x, y, x + filledWidth, y + BAR_HEIGHT, color);
        }

        // Mana text
        String manaText = String.format("%.0f/%.0f", data.getMana(), data.getMaxMana());
        graphics.drawString(Minecraft.getInstance().font, manaText,
                x + BAR_WIDTH + 4, y - 1, 0xFFAA88FF, true);

        // Selected spell name
        ArcanaSpell selected = ArcanaSpell.byIndex(data.getSelectedSpellIndex());
        if (data.knowsSpell(selected.getIndex())) {
            String spellName = selected.name().replace('_', ' ');
            String schoolName = "§7[" + selected.getSchoolName() + "]§r ";
            graphics.drawString(Minecraft.getInstance().font,
                    schoolName + spellName,
                    x - 2, y - 12, 0xFFFFFFFF, true);
        }
    }
}
