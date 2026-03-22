# ARCANA — Player Journey Walkthrough (Final Verification)
# Walking through every minute of gameplay to find remaining holes.

---

## MINUTE 0-5: Brand New World

**Player spawns. What do they see?**
- Overworld looks normal... but wait: 
  ✓ Enchanted Forest biome visible in distance (glowing trees — "what's that?")
  ✓ Mana Butterflies flutter near flowers (ambient magic presence)
  ✓ Possibly a Wizard Tower on the horizon (structure generation)

**Player mines stone, gets wood, makes iron tools. Standard Minecraft.**
- No friction here. Arcana doesn't interfere with vanilla early game.
- QUESTION: When does the player FIRST encounter Arcana content?
  → Arcanite Ore spawns Y 0-48, common. They'll hit it while iron mining.
  → Enchanted Forest biome has glowing trees visible from surface.
  → Mana Butterflies spawn in any biome at low rates.
  → Wizard Towers generate like vanilla structures.
  VERDICT: ✓ Player encounters Arcana naturally within first 10 minutes.

---

## MINUTE 5-15: First Contact with Magic

**Player finds Arcanite Ore while mining.**
- Needs iron pick (harvest level 1). ✓ Correct — they have iron by now.
- Drops Raw Arcanite. ✓ Registered item.
- ⚠️ GAP FOUND: Player gets Raw Arcanite. Now what? There's no vanilla 
  advancement trigger yet that tells them what to do. The guidebook fix 
  from the audit says "auto-granted on first Arcanite pickup" but...
  
  NEW FIX NEEDED: Add an **Advancement** "Arcane Awakening" triggered by 
  picking up Raw Arcanite. The advancement description says:
  "You feel a strange energy. Smelt this ore and craft a Book + Ingot 
  into an Arcane Guidebook to begin your journey."
  
  This is the HOOK. Without it, raw arcanite is just another ingot.

**Player smelts Arcanite Ingot. Crafts Arcane Guidebook (Book + Arcanite Ingot).**
- ✓ Recipe exists conceptually. Needs to be added to data/recipes.
- Guidebook opens, shows "Getting Started" page.

**Guidebook tells them: Craft an Arcane Workbench.**
- Recipe: 4 Arcanite Ingots + Crafting Table (shapeless? shaped?)
- ⚠️ GAP FOUND: Arcane Workbench recipe not explicitly defined anywhere.
  NEW FIX: Shaped recipe — Arcanite Ingot on all 4 sides, Crafting Table center.
  This matches vanilla patterns and is intuitive.

**Guidebook tells them: Craft your first Wand.**
- Recipe: 2 Sticks + 1 Arcanite Ingot + 1 elemental gem at Arcane Workbench
- ⚠️ GAP FOUND: Where does the player get their first elemental gem?
  They haven't explored structures yet. They just started mining.
  
  NEW FIX: The BASIC wand (arcanite_wand) requires NO gem — just
  2 Sticks + 2 Arcanite Ingots. This gives access to Mana Bolt (the 
  school-neutral Tier 1 spell from Arcanomancy). The school-specific 
  wands that auto-know their Tier 1 spell DO require the school gem.
  
  So the progression is:
  1. Craft basic Arcanite Wand → can cast Mana Bolt immediately
  2. Find a gem in a structure → craft school wand → learn that school's Tier 1
  3. Eventually craft school Staffs (Tier 2 material + gem + essence)

**Player crafts Arcanite Wand, casts Mana Bolt for the first time.**
- Advancement: "Apprentice Mage"
- ✓ Mana bar appears on HUD
- ✓ 100 starting mana, 3/sec regen → can cast ~12 Mana Bolts before empty
- ✓ 8 mana cost, 1 sec cooldown → satisfying rapid fire
- VERDICT: ✓ First magic experience is fast, easy, and fun.

---

## MINUTE 15-60: Early Exploration

**Player now has: Arcanite tools, Arcanite armor, basic wand.**
**Guidebook suggests: "Explore the world for structures, gems, and spell scrolls."**

**Player finds a Wizard Tower.**
- Contains: 1 spell scroll (random Tier 1-2), 1 mana crystal, loot chest
- ⚠️ CHECK: Can the player USE the spell scroll? 
  → Spell scrolls are used at the Inscription Table to LEARN spells.
  → But Inscription Table requires Runic Steel (Tier 2 alloy).
  → Player only has Arcanite (Tier 1).
  
  ⚠️ GAP FOUND: Player finds exciting loot they can't use yet!
  
  NEW FIX: Spell scrolls work TWO ways:
  1. CONSUME directly (right-click) to learn the spell permanently — but the 
     scroll is destroyed. This is the "exploration path."
  2. Use at Inscription Table to COPY to a blank page — creating a second scroll 
     without destroying the original. This is the "automation path" for later.
  
  This means: found scrolls = immediately useful. The Inscription Table 
  becomes a duplication/creation tool, not a gatekeeper.

**Player finds a Crystal Cave.**
- Contains: Gem clusters, Crystal Golem mobs, possibly Crystal Golem King arena
- ⚠️ CHECK: Can the player fight Crystal Golems at this stage?
  → Player has Arcanite Sword (2.5 attack damage bonus + 3 base = 5.5 damage)
  → Crystal Golem should have ~30 HP, deal ~4 damage per hit
  → This is a fair challenge with iron-equivalent gear. ✓
  
- Player finds first elemental gem (e.g., Earth Emerald from Crystal Cave).
  Now they can craft a Geomancy Wand → auto-learns Stone Skin. ✓

**Player finds a Mana Spring.**
- Contains: Mana Berry Bushes (farmable!), glowing water, ambient particles
- Player learns they can farm Mana Berries for mana restoration. ✓
- ⚠️ CHECK: Can they transplant the bush?
  → Mana Berry Bush block needs to drop itself with silk touch, or 
     drop Mana Berry items that can be planted on Enchanted Soil.
  NEW FIX: Mana Berries (item) are plantable on any farmland block.
  They grow slowly into Mana Berry Bushes. This makes farming viable.

**Player finds a Dark Shrine.**
- Contains: Shadow Essence, Voidstone Ore nearby, Shadow Wraith spawns
- ⚠️ CHECK: Shadow Wraith difficulty appropriate?
  → Shadow Wraith: teleports, fast, spawns at night
  → At Arcanite gear level this should be a dangerous but beatable fight
  → Drop: Shadow Essence (guaranteed) + chance of Shadow Onyx gem
  → Player can now access Umbramancy school if they craft the wand. ✓

---

## MINUTE 60-180: Establishing a Base (Hours 1-3)

**Player now has: Multiple school wands, some Tier 1-2 spells, Arcanite gear.**
**They're building a base and starting to craft machines.**

**Player crafts Arcane Forge (Tier 2 machine).**
- Recipe: 4 Arcanite Ingots + 1 Furnace + 4 Iron Ingots
- Used for: Alloy smelting
- ⚠️ CHECK: Can they make Tier 2 alloys now?
  → Runic Steel = "Steel + Runestone" — but "Steel" isn't a vanilla item!
  
  ⚠️ GAP FOUND: Several alloy recipes reference "Steel" which doesn't exist 
  in vanilla Minecraft 1.20.1. 
  
  NEW FIX: Replace "Steel" references:
  - Runic Steel = Iron Ingot + Runestone Ingot (at Arcane Forge)
  - All alloy recipes use only ores/metals that exist in our mod or vanilla.
  Specifically audit every Tier 1-2 alloy:
  - Enchanted Iron = Iron Ingot + Arcanite Ingot ✓
  - Runic Steel = Iron Ingot + Runestone Ingot ✓ (FIXED)
  - Moonforged Steel = Moonsilver Ingot + Iron Ingot ✓ (FIXED)
  - Sunforged Steel = Sunsteel Ingot + Gold Ingot ✓
  - Arcane Bronze = Copper Ingot + Arcanite Ingot ✓ (removed Tin — not vanilla)
  
**Player starts Mana Generator.**
- Burns fuel → produces mana for nearby machines
- ⚠️ CHECK: What fuel does it burn? 
  → Should burn any vanilla fuel (coal, wood, lava buckets) 
  → PLUS Mana Crystals for bonus mana output
  → PLUS Essences for large mana bursts
  NEW FIX: Document fuel values explicitly.

**Player crafts Mana Conduit + Mana Battery.**
- ⚠️ CHECK: How does the player LEARN about the mana network?
  → Guidebook chapter: "Mana Infrastructure" unlocked when crafting 
    first Mana Generator. Shows conduit placement, relay boosting, etc.
  ✓ This is covered by the guidebook system.

---

## HOUR 3-8: Mid-Game Progression

**Player has Tier 2 armor, multiple schools at mastery 15-30, 3-4 spells per school.**
**They've explored several structures, fought Crystal Golem King boss.**

**Boss fight flow for Crystal Golem King:**
1. Player found Crystal Cave with boss arena (empty throne room)
2. Crystal Golems in the cave drop Crystal Key (boss summon item) — 10% drop rate
3. Player places Crystal Key on the throne → boss spawns
4. ⚠️ CHECK: Can the player prep the arena?
   → Yes — place Rune Traps on the floor, Ward Circle for buff zone
   → Set up Spell Anchor: "Cast Heal when health < 30%"
   → This is the preparation magic system WORKING AS INTENDED. ✓
5. Boss fight: 200 HP, melee + crystal rain AoE
   → Player with Tier 2 gear + Tier 2 spells: ~5 minute fight. Challenging but fair.
6. Drops: Crystal Heart + Arcanite Block
   → Crystal Heart is used in Glacier Staff recipe (later)
   → ⚠️ CHECK: Is there immediate use for the Crystal Heart?
     → It should also be usable at the Mana Infuser to create a Prismatic 
       Mana Crystal (upgrades dim crystal → glowing → radiant...)
     NEW FIX: Boss drops should ALWAYS have both an immediate use AND 
     a long-term crafting use. Crystal Heart → immediate: +50 max mana 
     permanently when consumed. Long-term: Glacier Staff component.

**Tier 3 alloy crafting begins.**
- Requires essences (GAP 4 fix from audit). ✓
- Player must intentionally farm essences using the logical rules:
  → Need Fire Essence? Fight Blazes or kill mobs near lava.
  → Need Shadow Essence? Kill at night in darkness.
  This drives the player OUT of their base into specific environments. ✓

**First dimension access:**
- Player has 4 elemental gems (Fire Ruby, Frost Sapphire, Lightning Topaz, Earth Emerald)
  → Found in: Dark Shrine (fire), Frozen structure (ice), Sky Altar (lightning), Crystal Cave (earth)
- ⚠️ CHECK: Are all 4 gem types available in overworld structures?
  → Fire Ruby: Dark Shrine loot ✓
  → Frost Sapphire: Needs a cold/ice structure...
  
  ⚠️ GAP FOUND: No dedicated ice structure in the overworld!
  Dark Shrine → fire, Crystal Cave → earth, Sky Altar → wind/lightning
  But there's no ice-themed overworld structure.
  
  NEW FIX: Add **Frozen Sanctum** to overworld structures list.
  - Generates in snowy biomes (like igloos but larger)
  - Contains: Frost Sapphire, Ice Essence, Cryomancy spell scroll
  - Guarded by: Frost-enchanted skeletons (Strays with extra HP)
  - This fills the biome-school linkage for Cryomancy too.

  Similarly check Wind Opal source:
  → Wind Opal: Sky Altar ✓ (floats at y=200, wind-themed)
  
  All 5 elemental gems now have dedicated source structures. ✓

---

## HOUR 8-15: Dimensions

**Player enters Arcane Realm (Dimension 1).**
- ⚠️ CHECK: Is the difficulty spike appropriate?
  → Player has Tier 3 alloys + Tier 3 spells
  → Arcane Realm mobs should challenge Tier 3 gear
  → Arcane Sentinel: ranged laser, ~50 HP → good first-dimension mob ✓
  
- Player finds Aethrium Ore (dimension-exclusive)
- ⚠️ CHECK: Can they mine it?
  → Aethrium has harvest level 3. Tier 3 pickaxes have harvest level 3. ✓
  
- Player explores Arcane Citadel, fights The Archon boss
- ⚠️ CHECK: Archon boss drops — do they connect forward?
  → Archon Heart: Used in Tempest Staff recipe ✓
  → Arcane Core: Used in Tier 4 machine upgrades ✓
  → Also needed for Dragon Scale Key (later) ✓

**Player enters Shadow Depths (Dimension 2).**
- Access: Ritual at midnight + Shadow Essence + Voidstone
- ⚠️ CHECK: Does the player know this recipe?
  → Guidebook chapter "Shadow Depths" unlocks after defeating The Archon
  → Lore books in Arcane Realm hint at the Shadow Depths
  ✓ Discovery path exists.

- Shadow Depths has Void Leech mob (drains mana on hit!)
  → THIS IS AN IMPORTANT DESIGN MOMENT: A mob that attacks your MANA,
    not your health. Forces the player to adapt — bring Mana Berries,
    use physical weapons instead of spells, or cast Purify to remove the drain.
  ✓ This makes Shadow Depths feel mechanically distinct from Arcane Realm.

---

## HOUR 15-25: Late Game

**Player has defeated both dimension bosses, has Tier 4-5 gear.**
**Multiple schools at mastery 70+. Tier 4 spells available.**

**Celestial Spire access:**
- Requires: Archon Heart + Shadow Core (from both dimension bosses)
- Used in ritual to open portal
- ⚠️ CHECK: Is it clear this is the path?
  → Guidebook chapter "The Celestial Spire" unlocks after both bosses defeated
  → Lore trail: Archon and Lich were connected to the Dragon. Their drops 
    literally combine to open the path to the Dragon's domain.
  ✓ Narratively and mechanically connected.

**Celestial Spire is the endgame zone.**
- Eternium Construct mobs drop Eternium (endgame material)
- ⚠️ CHECK: Is there enough to DO here before the final boss?
  → Mine Celestium + Eternium ore
  → Fight 5 unique mob types
  → Explore Celestial Citadel, Dragon Aerie, Eternium Vault
  → Craft Tier 5 alloys (requires ritual crafting — multiple steps)
  → Craft ultimate armor sets
  → Reach mastery 90 in primary schools → unlock Ultimate spells
  VERDICT: ✓ Substantial content before the final boss.

**The Arcane Dragon fight:**
- 1000 HP, 3 phases, all elements
- ⚠️ CHECK: Is the player equipped to handle this?
  → Tier 5 armor (Dragonscale or Celestial) provides fire/explosion immunity
  → Multiple mastered spell schools give versatility
  → Phase 1: Dragon uses fire → Cryomancy counters
  → Phase 2: Dragon uses shadow + lightning → Luxomancy shield + Geomancy walls
  → Phase 3: Dragon uses ALL elements → requires school switching mid-fight
  
  ⚠️ GAP FOUND: The dragon fight REQUIRES multiple mastered schools to 
  counter phase-specific elements, but nothing in the game TELLS the player 
  this. They could show up with only Pyromancy mastered and hit a wall.
  
  NEW FIX: Lore books in the Celestial Citadel (found BEFORE the dragon arena) 
  explicitly say: "The Dragon commands all elements. Only a mage who has 
  mastered multiple schools can hope to survive its wrath." 
  
  Additionally: The Arcane Guidebook's Dragon chapter lists:
  "Recommended: Mastery 70+ in at least 3 schools. Bring elemental resistance 
  potions. Set up Ward Circles in the arena before engaging."

---

## HOUR 25-40: Endgame + Secret Boss

**After defeating the Dragon:**
- Drops: Dragon Soul + Eternium Shards
- Dragon Soul → Archmage Staff crafting component
- Eternium Shards → Eternium Alloy (Tier 6, requires ALL Tier 5 alloys)

**The Void Incarnate (Secret Boss):**
- Access: Craft Void Fragment from ALL boss drops
  → Crystal Heart + Corrupted Staff + Lich Crown + Archon Heart + 
    Drake Scale + Wyrm Scale + Dragon Soul = Void Fragment
- ⚠️ CHECK: Does the player know this exists?
  → NOT explicitly. It's a SECRET boss.
  → BUT: Lore breadcrumbs throughout the game hint at "something deeper"
  → Final lore book after Dragon says: "The Dragon was but a puppet. 
    Something stirs in the Void between worlds..."
  → Guidebook shows "???" chapter that unlocks after all bosses defeated
  ✓ Discovery is earned, not handed.

- Void Incarnate: 2000 HP, hardest fight in the game
- Drops: Void Heart (creative-tier power item)
- ⚠️ CHECK: What does Void Heart actually DO?
  → It should be the ultimate trophy + mechanical reward:
  → When held in offhand: Immunity to void damage, creative flight,
    all spell costs reduced by 50%, mana regen 10x normal.
  → This is the "you've beaten everything" reward. It's intentionally 
    overpowered because there's nothing left to trivialize.
  ✓ Appropriate endgame reward.

---

## REMAINING GAPS FOUND IN THIS WALKTHROUGH

### NEW GAP 9: No ice-themed overworld structure
- FIX: Add **Frozen Sanctum** (snowy biome structure with Frost Sapphire)

### NEW GAP 10: Spell scrolls unusable without Inscription Table
- FIX: Scrolls are directly consumable (right-click to learn). Inscription 
  Table copies/creates scrolls, doesn't gate using found ones.

### NEW GAP 11: "Steel" doesn't exist in vanilla 1.20.1
- FIX: All alloy recipes use only vanilla metals + our mod's metals. 
  No references to non-existent materials.

### NEW GAP 12: Basic wand needs a gem the player doesn't have yet
- FIX: Arcanite Wand (basic) = 2 Sticks + 2 Arcanite Ingots. No gem needed.
  School-specific wands require their gem.

### NEW GAP 13: Boss drops need immediate + long-term uses
- FIX: Every boss drop is BOTH consumable for an immediate power boost 
  AND a crafting ingredient for endgame recipes. Documented per boss.

### NEW GAP 14: Dragon fight requires multi-school mastery but nothing warns player
- FIX: Lore books in Celestial Citadel + Guidebook chapter explicitly 
  advise mastering multiple schools before the fight.

### NEW GAP 15: Mana Berry Bush can't be farmed if player doesn't find the biome
- FIX: Mana Berries plantable on any farmland. Also: Wandering Mage NPC 
  sells Mana Berry seeds as a trade option.

### NEW GAP 16: No way to respec or change magic schools
- Player may invest 40+ hours into Pyromancy and realize they need Cryomancy 
  for the Dragon fight. Starting from mastery 0 feels terrible.
- FIX: Add **Tome of Reallocation** — crafted from Arcane Essence + Book + 
  Diamond. Allows redistributing 50 mastery points from one school to another.
  This is expensive enough to not be spammed but available when needed.

### NEW GAP 17: Enchantments have no clear acquisition path
- 15 custom enchantments exist but where do players GET them?
- FIX: Custom enchantments appear in the following ways:
  1. Standard enchanting table (Mana Siphon, Warding, etc. at normal rarity)
  2. Structure loot (enchanted books in Wizard Towers, Libraries)
  3. NPC Runesmith sells specific enchantments for essences + emeralds
  4. Boss drops come pre-enchanted (Dragon Slayer has Soul Harvest built in)

### NEW GAP 18: Baubles/accessories have no slot system defined
- The design says "worn in special slots (rings, amulets, belts)" but 
  Forge 1.20.1 doesn't have Baubles/Curios built-in.
- FIX: Implement a simple **2 accessory slots** in the player inventory screen.
  - Slot 1: Ring/Amulet (any one accessory)
  - Slot 2: Belt/Crown (any one accessory)
  - This keeps it simple without requiring Curios API dependency.
  - Accessories work by right-clicking to "equip" (stored in player capability data).

### NEW GAP 19: Machines have no fuel/power documentation
- Player crafts Mana Generator but doesn't know what fuels it accepts.
- FIX: Machine GUIs show a tooltip: "Fuel: Coal (100 mana), Mana Crystal 
  (500 mana), Essence (1000 mana)." Guidebook has a "Machines" chapter.

### NEW GAP 20: Spell weaving combos have no discoverability
- 13 combos exist but the player doesn't know they can combine spells.
- FIX: 
  1. First time two compatible spells overlap: Advancement "Spell Weaver — 
     You discovered a spell combination!"
  2. Guidebook unlocks a "Spell Weaving" chapter showing the combo you found
  3. Subsequent combos are added to the chapter as you discover them
  4. NPC Lorekeeper hints: "Ancient mages combined fire and wind to devastating effect..."

---

## FINAL MISSING ITEMS TO ADD TO CODE

| Item/Block | Reason | Priority |
|-----------|--------|----------|
| Frozen Sanctum structure | No ice structure in overworld | HIGH |
| Tome of Reallocation | Mastery respec needed | MEDIUM |
| Mana Berry seeds | Farmable mana berries | HIGH |
| Structure loot tables | Every structure needs defined loot | HIGH |
| Advancement JSONs (~25) | Discovery/progression tracking | HIGH |
| Recipe JSONs (~200+) | Every craftable item needs a recipe | HIGH |
| Fuel value system | Mana Generator needs fuel definitions | MEDIUM |
| Accessory slot capability | 2 equip slots for baubles | MEDIUM |

---

## VERDICT: Ready to build.

All 20 gaps identified and resolved. The progression flows:
- Mining → First wand → First spell → Explore structures → Find gems → 
  School wands → Better spells → Bosses → Dimensions → Endgame → Secret boss

Every system connects to at least 2 other systems. No dead ends.
No items that exist without a purpose. No purposes without items.
