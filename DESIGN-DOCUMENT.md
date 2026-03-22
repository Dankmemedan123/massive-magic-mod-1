# ARCANA — Complete Mod Design Document
# Target: Forge 1.20.1 | Single mod to replace an entire modpack
# Scale: 500+ items, 200+ blocks, 80+ entities, 3 dimensions, 12 biomes

---

## CONTENT INVENTORY (Target Totals)

| Category | Count | Status |
|----------|-------|--------|
| New ores | 12 | Planned |
| New metals/alloys | 18 | Planned |
| New gems/crystals | 10 | Planned |
| Decorative blocks | 60+ | Planned |
| Machine blocks | 25 | Planned |
| Ritual/magic blocks | 15 | Planned |
| Natural blocks (worldgen) | 20 | Planned |
| Tools (per material × type) | 90+ | Planned |
| Armor sets | 15 | Planned |
| Weapons (swords, staffs, bows, etc.) | 40+ | Planned |
| Magic staffs/wands | 24 | Planned |
| Spell scrolls | 40 | Planned |
| Potions/elixirs | 20 | Planned |
| Food items | 15 | Planned |
| Crafting materials | 50+ | Planned |
| Essences/reagents | 16 | Planned |
| Baubles/accessories | 20 | Planned |
| Hostile mobs | 30 | Planned |
| Passive/neutral mobs | 12 | Planned |
| Boss mobs | 8 | Planned |
| NPCs (villager-like) | 6 types | Planned |
| Dimensions | 3 | Planned |
| Biomes | 12 | Planned |
| Structures (overworld) | 15 types | Planned |
| Structures (dimensions) | 20 types | Planned |
| Dungeons (procedural) | 8 types | Planned |
| Enchantments | 15 | Planned |
| Status effects | 12 | Planned |
| Commands | 10+ | Planned |

**Estimated total: 700+ unique registry entries**

---

## 1. MAGIC SYSTEM (Massive — Thaumcraft + Botania + Blood Magic scale)

### 1.1 Mana System
- **Mana** is the core energy. Every player has a mana bar (0-100, expandable to 500 via artifacts).
- **Mana regenerates** slowly (1/sec base), faster near mana sources.
- **Mana Crystals** generate mana passively when placed. 5 tiers of crystal.
- **Mana Conduits** transport mana between blocks (like Botania spreaders but as pipes).
- **Mana Relays** boost mana transfer speed and range.
- **Mana Batteries** store large amounts of mana (like RS energy cells).

### 1.2 Eight Schools of Magic
Each school has 5 spells (40 total), a dedicated staff, and a mastery progression.

| School | Element | Spells (5 per school) |
|--------|---------|----------------------|
| Pyromancy | Fire | Fireball, Fire Wall, Inferno, Meteor Strike, Phoenix Form |
| Cryomancy | Ice | Frost Bolt, Ice Shield, Blizzard, Glacial Tomb, Absolute Zero |
| Electromancy | Lightning | Spark, Chain Lightning, Thunderclap, Storm Call, Zeus's Wrath |
| Geomancy | Earth | Stone Skin, Earthquake, Mud Trap, Crystal Lance, Golem Summon |
| Aeromancy | Wind | Gust, Levitate, Tornado, Wind Walk, Hurricane |
| Umbramancy | Shadow | Shadow Step, Dark Pulse, Fear, Shadow Clone, Void Rift |
| Luxomancy | Light | Heal, Purify, Sun Beam, Holy Shield, Divine Judgment |
| Arcanomancy | Arcane | Mana Bolt, Dispel, Arcane Shield, Telekinesis, Time Slow |

### 1.3 Spell Casting System
- **Spell Scrolls** — crafted at Inscription Table. Teach spells permanently.
- **Staffs** — 8 school-specific staffs + 3 hybrid staffs + 1 legendary Archmage Staff.
- **Casting** — Right-click with staff to cast selected spell. Shift+scroll to switch spells.
- **Cooldowns** — Each spell has a cooldown (5-60 seconds depending on power).
- **Mastery** — Using spells from a school increases mastery (0-100). Higher mastery = reduced mana cost, increased damage, unlocks final spell at mastery 80.

### 1.4 Ritual System
- **Ritual Altar** — 5×5 multiblock structure built from Ritual Stone + Pillars.
- **Ritual Circles** — Drawn on the ground with Arcane Chalk (4 colors for 4 tiers).
- **20 Rituals** including:
  - Transmutation (convert metals)
  - Enchantment Infusion (add spell effects to weapons)
  - Soul Binding (capture mob souls for summoning)
  - Dimensional Rift (open portals)
  - Weather Control
  - Resurrection (revive with kept items)
  - Warding (protect an area from mob spawning)
  - Mana Storm (massive mana generation burst)
  - Blood Offering (sacrifice health for rare materials)
  - Creation (create items from raw mana + reagents)

### 1.5 Research System (Thaumcraft-inspired)
- **Arcane Tome** — guidebook item, crafted early. Contains all mod info.
- **Research Points** earned by: discovering new materials, killing mobs, exploring structures.
- **Research Tree** — unlock recipes, spells, machines progressively.
- **4 Research Branches**: Elemental, Artifice, Exploration, Forbidden Knowledge.

---

## 2. ORES, METALS, AND MATERIALS

### 2.1 Overworld Ores (6)
| Ore | Y-level | Rarity | Primary Use |
|-----|---------|--------|-------------|
| Arcanite Ore | 0-48 | Common | Starter magical metal |
| Runestone Ore | 0-32 | Uncommon | Rune crafting |
| Moonsilver Ore | 0-24 | Uncommon | Weapons, anti-undead |
| Sunsteel Ore | 0-16 | Rare | Armor, fire resistance |
| Voidstone Ore | -64 to 0 | Rare | Shadow magic |
| Starmetal Ore | 32-128 | Very rare | High-tier alloys (mountain only) |

### 2.2 Dimension Ores (6)
| Ore | Dimension | Use |
|-----|-----------|-----|
| Aethrium Ore | Arcane Realm | Flight + levitation items |
| Draconic Ore | Dragon's Abyss | Dragon-tier gear |
| Celestium Ore | Celestial Spire | Endgame alloys |
| Shadowite Ore | Shadow Depths | Shadow magic amplification |
| Crystalite Ore | Crystal Caverns | Mana storage |
| Eternium Ore | Final boss arena | Endgame ultimate material |

### 2.3 Alloys (18)
| Alloy | Components | Tier |
|-------|-----------|------|
| Enchanted Iron | Iron + Arcanite | 1 |
| Runic Steel | Steel + Runestone | 2 |
| Moonforged Steel | Moonsilver + Iron | 2 |
| Sunforged Steel | Sunsteel + Gold | 2 |
| Void Alloy | Voidstone + Obsidian | 3 |
| Star Alloy | Starmetal + Diamond | 3 |
| Arcane Bronze | Copper + Arcanite + Tin | 1 |
| Mithril | Moonsilver + Starmetal | 4 |
| Adamantine | Sunsteel + Voidstone + Diamond | 4 |
| Aether Steel | Aethrium + Iron | 4 |
| Dragonscale Alloy | Draconic + Adamantine | 5 |
| Celestial Alloy | Celestium + Star Alloy | 5 |
| Shadow Steel | Shadowite + Void Alloy | 5 |
| Crystal Alloy | Crystalite + Mithril | 5 |
| Eternium Alloy | Eternium + all Tier 5 alloys | 6 |
| Phoenix Alloy | Sunsteel + Fire Essence + Gold | 3 |
| Frost Alloy | Moonsilver + Ice Essence + Iron | 3 |
| Storm Alloy | Starmetal + Lightning Essence + Copper | 3 |

### 2.4 Gems and Crystals (10)
- Mana Crystal (5 tiers: Dim, Glowing, Radiant, Blazing, Prismatic)
- Fire Ruby, Frost Sapphire, Lightning Topaz, Earth Emerald, Wind Opal
- Shadow Onyx, Light Pearl, Arcane Amethyst, Void Diamond

### 2.5 Essences (16)
Dropped by mobs and found in structures. Used in rituals and alchemy.
- Fire, Ice, Lightning, Earth, Wind, Shadow, Light, Arcane (8 elemental)
- Life, Death, Chaos, Order, Time, Space, Soul, Mind (8 abstract)

---

## 3. TOOLS AND ARMOR (90+ items)

### 3.1 Tool Sets (Sword, Pickaxe, Axe, Shovel, Hoe × 10 materials = 50 tools)
Materials: Arcanite, Enchanted Iron, Runic Steel, Moonforged, Sunforged, 
           Void Alloy, Star Alloy, Mithril, Adamantine, Eternium

### 3.2 Armor Sets (15 sets × 4 pieces = 60 armor items)
Each set has a unique full-set bonus:
| Armor Set | Full-Set Bonus |
|-----------|---------------|
| Arcanite | +25% mana regen |
| Runic | Spells cost 15% less mana |
| Moonforged | Night vision + increased damage at night |
| Sunforged | Fire resistance + increased damage during day |
| Void | Invisibility when sneaking |
| Star | Permanent slow falling + star light particles |
| Mithril | +3 hearts + magic resistance |
| Adamantine | Knockback immunity + thorns III equivalent |
| Aether | Creative flight (slow) |
| Dragonscale | Fire + explosion immunity |
| Celestial | Full creative flight + auto-repair |
| Shadow | Teleport behind attacker on hit |
| Crystal | +200 max mana + mana shield (absorb damage) |
| Eternium | All of the above at 50% effectiveness |
| Archmage Robes | +500 max mana, all spells unlocked, -50% cooldowns |

### 3.3 Special Weapons (12)
- Flame Tongue (fire sword, ignites enemies)
- Frost Brand (ice sword, slows enemies)
- Thunderstrike (lightning sword, chain lightning on hit)
- Terra Breaker (earth hammer, AoE ground slam)
- Gale Force (wind dagger, extreme attack speed)
- Shadow Fang (shadow dagger, teleport-strike)
- Sunbeam Blade (light sword, extra damage to undead)
- Arcane Saber (arcane sword, mana-powered damage scaling)
- Dragon Slayer (greatsword, massive boss damage)
- Void Reaper (scythe, life steal)
- Celestial Bow (long range, homing arrows)
- Eternium Blade (endgame sword, adapts element to target weakness)

### 3.4 Staffs and Wands (24)
- 8 school staffs (one per school)
- 3 hybrid staffs (Pyro+Cryo, Electro+Geo, Shadow+Light)
- 1 Archmage Staff (all schools)
- 12 basic wands (one per material, lower tier than staffs)

---

## 4. MACHINES AND AUTOMATION (25 blocks)

### 4.1 Mana Network
- Mana Generator (burns fuel → mana)
- Solar Mana Collector (generates mana from sunlight)
- Lunar Mana Collector (generates mana from moonlight)
- Mana Conduit (transfers mana between blocks)
- Mana Relay (boosts conduit speed/range)
- Mana Battery (stores 10,000 mana, 3 tiers)

### 4.2 Processing
- Arcane Forge (alloy smelting, 2 inputs → 1 output)
- Crystal Grower (grows crystals over time)
- Essence Extractor (extracts essences from items)
- Mana Infuser (infuses items with mana for enchanting)
- Inscription Table (creates spell scrolls)
- Disenchanter (removes and stores enchantments)

### 4.3 Utility
- Ritual Altar Core (center of ritual multiblock)
- Ritual Pillar (corner of ritual multiblock)
- Arcane Workbench (crafting table for mod recipes, shows research-locked recipes)
- Warding Stone (prevents mob spawning in 32 block radius)
- Teleportation Pad (paired teleporters)
- Mana Lamp (light source powered by mana, configurable color)

### 4.4 Storage
- Arcane Chest (double chest capacity)
- Crystal Vault (quadruple chest, sorted)
- Essence Jar (stores one essence type, 64 stack)
- Mana Tank (visible liquid mana storage)

### 4.5 Redstone/Logic
- Mana Detector (redstone output based on mana level)
- Spell Trigger (casts stored spell on redstone signal)
- Entity Sensor (detects specific mob types)

---

## 5. WORLD GENERATION

### 5.1 Overworld Structures (15)
- Wizard Tower (small, loot + spell scroll)
- Ruined Wizard Tower (broken version, less loot)
- Crystal Cave Entrance (leads to crystal caverns)
- Arcane Library (medium, books + research points)
- Abandoned Ritual Site (has working altar sometimes)
- Mana Spring (natural mana source, glowing water)
- Enchanted Grove (special trees, passive mobs)
- Dark Shrine (shadow essence + shadow mobs)
- Dragon Roost (overworld dragon egg + draconic loot)
- Ancient Forge (pre-built forge + rare alloys)
- Mage Guild Outpost (NPC mages, trading)
- Elemental Nexus (4-element themed tower, one per element)
- Sky Altar (floating structure at y=200+)
- Underground Cathedral (massive underground structure)
- Portal Ruins (broken portal, repairable to access dimensions)

### 5.2 Overworld Biomes (4 new)
- Enchanted Forest (glowing trees, crystal flowers, mana particles)
- Crystal Wastes (exposed crystal ore, crystal formations)
- Shadow Marsh (dark, foggy, shadow mobs spawn)
- Starfall Peaks (mountain biome, starmetal ore, floating rocks)

### 5.3 Custom Dimensions (3)

#### Dimension 1: The Arcane Realm
- Theme: Magical floating islands, aurora sky
- 4 biomes: Mana Gardens, Crystal Spires, Aether Highlands, Arcane Wastes
- Structures: Arcane Citadel (dungeon), Mana Wellspring, Crystal Palace
- Boss: The Archon (magical construct boss)
- Access: Build portal from Ritual Altar + 4 elemental gems

#### Dimension 2: The Shadow Depths
- Theme: Underground darkness, bioluminescent fungi, void pits
- 4 biomes: Fungal Caverns, Void Chasm, Shadow Forest, Bone Fields
- Structures: Shadow Fortress, Lich's Crypt, Void Gate
- Boss: The Shadow Lich (necromancer boss)
- Access: Ritual at midnight with shadow essence + voidstone

#### Dimension 3: The Celestial Spire
- Theme: Crystalline sky dimension, rainbow bridges, star islands
- 4 biomes: Star Gardens, Crystal Peaks, Solar Plateau, Eternal Dusk
- Structures: Celestial Citadel, Dragon Aerie, Eternium Vault
- Boss: The Arcane Dragon (final boss)
- Access: Defeat both other bosses, use their drops in ritual

---

## 6. MOBS AND ENTITIES

### 6.1 Hostile Mobs (30)
**Overworld** (10):
- Mana Wisp (small, magical projectile)
- Crystal Golem (tough, drops crystal shards)
- Shadow Wraith (fast, teleports, spawns at night)
- Arcane Skeleton (shoots mana bolts instead of arrows)
- Enchanted Spider (webs slow + mana drain)
- Rune Guardian (found in structures, tough)
- Elemental Sprite (fire/ice/lightning/earth variants)
- Corrupted Mage (humanoid, casts random spells)
- Void Crawler (spawns in deep caves)
- Dark Knight (armored, sword + shield)

**Arcane Realm** (8):
- Arcane Sentinel (construct, laser attacks)
- Crystal Beast (charges, shatters into shards)
- Mana Elemental (heals from mana sources)
- Arcane Phantom (flying, phases through blocks)
- Golem Soldier (iron golem variant)
- Spell Weaver (casts AoE spells)
- Prism Spider (rainbow web, confusion effect)
- Aether Knight (flying, sword attacks)

**Shadow Depths** (7):
- Shadow Stalker (invisible until close)
- Bone Revenant (skeleton with regeneration)
- Void Leech (drains mana on hit)
- Fungal Zombie (poison + slowness)
- Shadow Hound (pack hunter, fast)
- Lich Minion (spawned by Shadow Lich)
- Void Tentacle (emerges from ground, pulls player)

**Celestial Spire** (5):
- Star Guardian (ranged, holy damage)
- Crystal Dragon (smaller dragon, fire breath)
- Celestial Golem (massive, slow, devastating)
- Light Wisp (heals other mobs)
- Eternium Construct (endgame mob, drops eternium)

### 6.2 Passive / Neutral Mobs (12)
- Mana Butterfly (drops mana dust)
- Crystal Deer (drops crystal shards when fed)
- Arcane Fox (finds buried treasure)
- Luminous Jellyfish (Arcane Realm, light source)
- Shadow Cat (tamed, detects nearby mobs)
- Star Bird (Celestial Spire, drops star feathers)
- Elemental Familiar (summonable pet, 8 variants)
- Enchanted Horse (faster, mana-powered)

### 6.3 Bosses (8)

**Early Game:**
1. **Crystal Golem King** — Found in Crystal Cave. 200 HP. Melee + crystal rain AoE. Drops: Crystal Heart, Arcanite Block.
2. **The Corrupted Mage** — Spawns from ritual. 300 HP. Cycles through 4 spell schools. Drops: Corrupted Staff, Spell Pages.

**Mid Game:**
3. **Shadow Lich** — Shadow Depths boss. 500 HP. Summons minions, life drain, teleport. Drops: Lich Crown, Shadow Core.
4. **The Archon** — Arcane Realm boss. 500 HP. Arcane constructs, mana beam, shield phases. Drops: Archon Heart, Arcane Core.

**Late Game:**
5. **Infernal Drake** — Fire dragon mini-boss. 400 HP. Flight, fire breath, dive bomb. Drops: Drake Scales, Fire Heart.
6. **Frost Wyrm** — Ice dragon mini-boss. 400 HP. Ice breath, freeze AoE. Drops: Wyrm Scales, Frost Heart.

**End Game:**
7. **The Arcane Dragon** — Celestial Spire final boss. 1000 HP. All elements, 3 phases, arena-wide attacks. Drops: Dragon Soul, Eternium Shards.
8. **The Void Incarnate** — Secret boss. 2000 HP. Only accessible after defeating all other bosses. Drops: Void Heart (creative-tier item).

### 6.4 NPCs (6 types)
- **Wandering Mage** — Trades spell scrolls and reagents (like wandering trader)
- **Mage Guild Master** — Found in Mage Guild Outpost, sells research, gives quests
- **Alchemist** — Trades potions and essences
- **Runesmith** — Trades runes and enchanting services
- **Crystal Merchant** — Trades crystals and gems
- **Lorekeeper** — Found in Libraries, gives lore books and research points

---

## 7. ENCHANTMENTS AND EFFECTS

### 7.1 New Enchantments (15)
- Mana Siphon (weapons — gain mana on hit)
- Spell Power (staffs — +damage per level)
- Mana Efficiency (staffs — reduced mana cost)
- Life Steal (weapons — heal on hit)
- Elemental Damage: Fire/Ice/Lightning/Wind (4 enchantments)
- Soul Harvest (weapons — chance to drop soul essence)
- Warding (armor — magic damage reduction)
- Mana Shield (armor — absorb damage with mana)
- Featherfall Plus (boots — negates all fall damage)
- Arcane Haste (tools — increased speed near mana)
- Auto-Repair (any — slowly repairs using ambient mana)

### 7.2 New Status Effects (12)
- Mana Burn (drains mana over time)
- Mana Regen (increased mana regeneration)
- Arcane Shield (absorbs next N damage)
- Elemental Resistance: Fire/Ice/Lightning (3 effects)
- Shadow Veil (partial invisibility)
- Stone Skin (damage reduction + slowness)
- Wind Speed (movement speed + jump boost)
- Time Dilation (nearby mobs move slower)
- Soul Link (share damage with linked entity)
- Corruption (wither + weakness, from dark magic)

---

## 8. BAUBLES AND ACCESSORIES (20)

Worn in special slots (rings, amulets, belts):
- Ring of Mana (passive mana regen)
- Ring of Fire/Ice/Lightning/Earth/Wind/Shadow/Light/Arcane (8 rings, spell damage boost)
- Amulet of Protection (magic damage resistance)
- Amulet of Life (auto-revive once, long cooldown)
- Amulet of Teleportation (right-click to teleport 20 blocks)
- Belt of Holding (extra inventory row)
- Belt of Speed (permanent speed boost)
- Belt of Strength (permanent strength I)
- Crown of the Archmage (all spell schools +20% power)
- Void Pendant (immune to void damage, float in void)

---

## 9. BUILD ORDER (Implementation Priority)

### Phase 1 — Foundation (THIS SESSION)
- [ ] Mod entry point, all DeferredRegisters, creative tab
- [ ] ALL ores (12), ALL metals/alloys (18), ALL gems (10) — items + blocks
- [ ] ALL tool sets (50 tools)
- [ ] ALL armor sets (15 sets = 60 items)
- [ ] ALL special weapons (12)
- [ ] Basic mana system (player capability, mana bar)
- [ ] Ore worldgen (all 6 overworld ores)
- [ ] Arcane Workbench (crafting)
- [ ] Build system, textures, README

### Phase 2 — Magic System
- [ ] 40 spells across 8 schools
- [ ] 24 staffs/wands
- [ ] Spell scroll system
- [ ] Mastery progression
- [ ] Mana network blocks (generator, conduit, battery)

### Phase 3 — World Generation
- [ ] 4 overworld biomes
- [ ] 15 overworld structures
- [ ] Crystal Caves underground biome

### Phase 4 — Dimensions
- [ ] Arcane Realm dimension + 4 biomes + structures
- [ ] Shadow Depths dimension + 4 biomes + structures
- [ ] Celestial Spire dimension + 4 biomes + structures

### Phase 5 — Mobs and Bosses
- [ ] 30 hostile mobs with AI
- [ ] 12 passive/neutral mobs
- [ ] 8 boss fights with phases and arenas
- [ ] 6 NPC types with trading

### Phase 6 — Everything Else
- [ ] 25 machine blocks with GUIs
- [ ] 20 rituals
- [ ] Research system
- [ ] 15 enchantments
- [ ] 12 status effects
- [ ] 20 baubles/accessories
- [ ] Essences and alchemy
- [ ] Arcane Tome guidebook
- [ ] Polish, balancing, JEI integration
