# ARCANA — Magic System Deep Design
# Every spell, staff, and interaction designed for WOW factor.
# No reskins. No "same thing, different color."

---

## WHAT MAKES THIS DIFFERENT FROM EVERY OTHER MAGIC MOD

### Innovations no Minecraft mod has combined before:

1. **SPELL WEAVING** — Cast two spells in quick succession to create a combo spell.
   Fire Wall + Wind Gust = sweeping flame wave that travels forward.
   This is NOT scripted combos — it's a physics-like interaction system where 
   elements react to each other in the world.

2. **ENVIRONMENTAL REACTIONS** (Divinity: OS2 style, first time in MC)
   - Rain/water + Lightning = electrified water zone (damages anything standing in it)
   - Fire spell on sand = creates glass blocks
   - Ice spell on water = creates walkable ice bridge
   - Earth spell on lava = creates obsidian platform
   - Wind spell on fire = spreads flames in wind direction
   - Shadow spell on light source = extinguishes it, creates darkness zone
   - Lightning on Creeper = supercharges it (risky!)

3. **SOUL BINDING** — Kill mobs with a Soul Staff to capture their essence.
   Bound souls are consumed as spell components for the most powerful magic.
   A bound Blaze soul lets you cast Inferno without fire essence reagents.
   A bound Enderman soul powers teleportation spells.

4. **PREPARATION MAGIC** (Outward-inspired, new for MC)
   - Place Rune Traps that trigger when mobs walk over them
   - Draw Ward Circles that buff allies inside and weaken enemies
   - Set up Spell Anchors that auto-cast a stored spell when conditions are met
     (e.g., "cast Heal when my health drops below 4 hearts")

5. **SPELL CHARGING** — Hold right-click to charge any projectile spell.
   Uncharged Fireball = small, fast, cheap. 
   Full charge (3 sec) = massive, slow, explosive, 4x mana cost.
   Visual feedback: staff glows brighter as you charge.

6. **SYMPATHETIC LINKS** — Link two entities together. Damage to one 
   transfers 50% to the other. Link a boss to one of its minions, 
   then kill the minion to bypass the boss's shield phase.

7. **REACTIVE ENCHANTMENTS** — Enchant your armor with conditional spells.
   "When hit, 20% chance to cast Frost Nova around self"
   "When health drops below 30%, auto-cast Stone Skin"
   "When sneaking, passively drain nearby mobs' health"

---

## STAFF DESIGNS — Each changes your entire playstyle

### Staff of Pyromancy — "The Cinderfall"
- **Passive:** Your footsteps leave fading embers (light source, 2 sec)
- **Special Ability (Shift+Right):** Ignite — set yourself on fire harmlessly, 
  gaining fire immunity and dealing touch damage for 10 seconds
- **Spell Modifier:** All fire spells leave behind lingering flame patches (3 sec)
- **Visual:** Charred black wood shaft, molten core visible through cracks, 
  ember particles constantly drift upward from the tip

### Staff of Cryomancy — "Frostweaver's Crook"
- **Passive:** Water freezes to ice within 3 blocks of you (walk on water!)
- **Special Ability:** Flash Freeze — instantly freeze all water/rain in 8-block radius
- **Spell Modifier:** Ice spells create persistent frost zones that slow for 5 sec
- **Visual:** Pale blue crystal shaft, frost constantly forming and melting on surface,
  tiny snowflake particles, the tip is a jagged ice crystal

### Staff of Electromancy — "Stormcaller's Rod"
- **Passive:** During rain/thunderstorms, your spells cost 50% less mana
- **Special Ability:** Lightning Rod — strike your position with real lightning,
  dealing massive AoE damage but also 2 hearts to yourself
- **Spell Modifier:** Lightning spells chain to 1 extra target automatically
- **Visual:** Copper-wrapped dark wood, electricity arcs between metal bands,
  the tip crackles with constant sparks, glows during storms

### Staff of Geomancy — "Earthshaper's Pillar"
- **Passive:** Mining speed +30% while held, silk touch on stone
- **Special Ability:** Terraform — raise/lower a 3x3 terrain patch by 1 block
- **Spell Modifier:** Earth spells have +50% duration on constructed walls/barriers
- **Visual:** Thick stone and root shaft, embedded emerald crystal, 
  tiny pebbles orbit the tip, moss grows on the lower half

### Staff of Aeromancy — "Zephyr's Grace"  
- **Passive:** Permanent slow fall + no fall damage while held
- **Special Ability:** Updraft — launch yourself 15 blocks straight up (great with elytra)
- **Spell Modifier:** All spells travel 50% faster as projectiles
- **Visual:** Impossibly thin willow shaft that sways like wind is hitting it,
  the tip is a spinning ring of compressed air, leaves drift around it

### Staff of Umbramancy — "Voidtouched Scepter"
- **Passive:** Invisible to mobs beyond 8 blocks while sneaking at night
- **Special Ability:** Shadow Meld — become fully invisible + intangible for 3 sec
  (can walk through walls!) but can't attack or cast during
- **Spell Modifier:** Shadow spells apply 3 sec blindness to targets
- **Visual:** Obsidian shaft that seems to absorb light, the tip is a void sphere
  that warps light around it, shadow tendrils writhe at the base

### Staff of Luxomancy — "Radiant Beacon"
- **Passive:** Emit constant light level 12. Undead within 5 blocks take wither damage.
- **Special Ability:** Sanctuary — create a 5-block dome of light for 15 sec.
  Allies inside regenerate, hostile projectiles can't enter.
- **Spell Modifier:** Healing spells also cleanse negative effects
- **Visual:** Golden-white crystal staff, warm glow emanates from every surface,
  motes of golden light orbit the tip, gentle hum sound

### Staff of Arcanomancy — "The Paradox"
- **Passive:** Can see mana levels of nearby blocks/entities (overlay)
- **Special Ability:** Spell Mirror — for 5 sec, any spell cast AT you is 
  reflected back at the caster (works on boss spells!)
- **Spell Modifier:** All spells cost 20% less mana but have +10% cooldown
- **Visual:** Crystal staff that shifts between all school colors,
  impossible geometry at the tip (MC Escher-like), reality warps around it

### Hybrid Staffs (3) — Require mastery in both schools

**Tempest Staff** (Pyro+Electro) — Steam explosions + superheated lightning
**Glacier Staff** (Cryo+Geo) — Permafrost constructs + crystalline barriers  
**Eclipse Staff** (Umbra+Lux) — Twilight magic: spells deal holy AND shadow damage

### Archmage Staff — "Eternity's Will"
- **Requires:** All 8 schools at mastery 50+
- **Passive:** ALL staff passives at 50% effectiveness simultaneously
- **Special Ability:** Arcane Overload — for 10 seconds, ALL cooldowns reset and
  mana cost is 0. After 10 sec, you're mana-drained and can't cast for 30 sec.
- **Spell Modifier:** Can cast spells from ANY school
- **Visual:** Constantly morphing staff that shifts between all elements

---

## ALL 40 SPELLS — Detailed unique mechanics

### PYROMANCY (Fire — Destruction + Area Denial)

**1. Fireball** (Projectile, Tier 1)
- Mana: 15 | Cooldown: 3s | Mastery unlock: 0
- CHARGED: Hold to grow from tennis ball to basketball size. Uncharged = 4 damage.
  Full charge (2s) = 12 damage + 3-block explosion + ignites terrain.
- WHY IT'S SPECIAL: The explosion breaks weak blocks (leaves, glass, wool) and 
  the fire spreads realistically based on flammable blocks nearby.

**2. Fire Wall** (Construct, Tier 2)
- Mana: 30 | Cooldown: 12s | Mastery: 15
- Places a 5-block-wide, 3-block-tall wall of flames that lasts 8 seconds.
- Entities passing through take 6 damage + 5 sec fire. Blocks light source.
- COMBO: Wind Gust through a Fire Wall = flame wave travels 15 blocks forward.

**3. Inferno** (AoE, Tier 3)
- Mana: 60 | Cooldown: 30s | Mastery: 40
- 8-block radius firestorm. Every entity takes 4 damage/sec for 6 sec.
- Leaves fire on every exposed surface. Rain extinguishes it early.
- COMBO: Cast on a frozen enemy = steam explosion (10 bonus damage + blindness)

**4. Meteor Strike** (Charge+Projectile, Tier 4)
- Mana: 100 | Cooldown: 60s | Mastery: 70
- Aim at the sky — after 3 seconds, a meteor crashes at the target location.
- 6-block radius, 30 damage at center, creates a crater. Sets everything on fire.
- Screen shakes for all players within 32 blocks.

**5. Phoenix Form** (Self-Buff, Tier 5 — ULTIMATE)
- Mana: 150 | Cooldown: 300s | Mastery: 90
- Transform into a Phoenix for 20 seconds. Creative flight, fire immunity,
  fire trail behind you, right-click shoots fireball streams.
  If you die during Phoenix Form, revive with 50% HP (once).
- VISUALS: Player model replaced with glowing phoenix entity, fire particles everywhere

### CRYOMANCY (Ice — Control + Terrain Manipulation)

**6. Frost Bolt** (Projectile, Tier 1)
- Mana: 12 | Cooldown: 2.5s | Mastery: 0
- Fast ice shard. 5 damage + Slowness II for 4 sec.
- SPECIAL: Converts water source blocks in its path to ice. Build ice bridges 
  by shooting across water! Ice melts after 30 sec.

**7. Ice Shield** (Self-Buff, Tier 2)
- Mana: 25 | Cooldown: 15s | Mastery: 15
- Creates 4 orbiting ice crystals that each absorb one hit (any damage).
  Crystals shatter with a satisfying sound + frost particles.
  Melee attackers take 3 damage when shattering a crystal.

**8. Blizzard** (AoE, Tier 3)
- Mana: 50 | Cooldown: 25s | Mastery: 40
- 10-block radius snowstorm for 10 sec. Applies Slowness III to all entities.
  Visibility drops to 5 blocks inside (like fog). Water freezes. Fire extinguishes.
- TACTICAL: Cast on a boss arena to blind the boss and slow its attacks.

**9. Glacial Tomb** (Targeted, Tier 4)
- Mana: 80 | Cooldown: 45s | Mastery: 70
- Encase a single target in ice for 5 seconds. They're invulnerable AND immobile.
  When the ice breaks (or after 5s), they take 20 shatter damage.
- STRATEGIC: Tomb a boss during its most dangerous attack phase. Freeze a 
  creeper that's about to explode. Tomb yourself to stall for mana regen.

**10. Absolute Zero** (AoE, Tier 5 — ULTIMATE)
- Mana: 140 | Cooldown: 300s | Mastery: 90
- 15-block radius instant freeze. ALL entities frozen for 8 seconds.
  All water becomes permanent ice. All fire extinguished. Snow appears on ground.
  Frozen entities take 3x damage from next physical hit (shatter bonus).
- THE SETUP: Freeze everything, then walk through shattering frozen mobs one by one.

### ELECTROMANCY (Lightning — Burst Damage + Chain Reactions)

**11. Spark** (Projectile, Tier 1)
- Mana: 10 | Cooldown: 1.5s | Mastery: 0
- Fastest spell in the game. Instant-hit lightning bolt, 4 damage.
- SPECIAL: If target is wet (in rain, in water, hit by water bottle), 
  damage doubles to 8 AND arcs to 1 nearby mob.

**12. Chain Lightning** (Targeted, Tier 2)
- Mana: 35 | Cooldown: 10s | Mastery: 15
- Hits primary target for 8 damage, then chains to up to 4 nearby entities,
  each chain dealing 75% of previous hit's damage. Chains prioritize wet targets.
- COMBO: Cast Blizzard first → mobs are wet from melting ice → Chain Lightning 
  arcs through ALL of them at double damage.

**13. Thunderclap** (Self-AoE, Tier 3)
- Mana: 45 | Cooldown: 20s | Mastery: 40
- Massive point-blank AoE. 12 damage + knockback 6 blocks to everything nearby.
  Creates a shockwave ring on the ground (visible expanding ring particle).
  All glass blocks within 8 blocks shatter.

**14. Storm Call** (World Event, Tier 4)
- Mana: 90 | Cooldown: 60s | Mastery: 70
- SUMMONS A REAL THUNDERSTORM for 60 seconds. During the storm:
  All lightning spells cost 50% less. Every 5 seconds, lightning randomly strikes
  within 32 blocks of the caster (avoids allies). Rain falls, wetting all entities.
- THIS CHANGES THE BATTLEFIELD: Now every lightning spell arcs and doubles.

**15. Zeus's Wrath** (Targeted, Tier 5 — ULTIMATE)
- Mana: 160 | Cooldown: 300s | Mastery: 90
- Point at an entity. After 1 sec delay, a MASSIVE lightning bolt from the sky
  hits them for 40 damage. The bolt is visible from 100+ blocks away.
  Leaves behind a charged creeper-style explosion at the impact point.
  All entities within 10 blocks are stunned for 3 seconds.

### GEOMANCY (Earth — Defense + Summoning + Terrain)

**16. Stone Skin** (Self-Buff, Tier 1)
- Mana: 15 | Cooldown: 8s | Mastery: 0
- +4 armor points for 15 seconds. Slowness I while active.
- VISUAL: Player's skin takes on a stone texture overlay. Tiny rock particles.

**17. Earthquake** (AoE, Tier 2)
- Mana: 35 | Cooldown: 15s | Mastery: 15
- 8-block radius ground slam. 8 damage + all entities launched 3 blocks up.
  Creates cracks in the ground (cosmetic particles, + chance to spawn silverfish).
- COMBO: Launch enemies up with Earthquake → they take fall damage on landing.

**18. Mud Trap** (Construct, Tier 3)
- Mana: 25 | Cooldown: 20s | Mastery: 40
- Creates a 5x5 mud patch. Entities walking on it get Slowness IV + can't jump.
  Lasts 15 seconds. Cast lightning on mud = electrified mud (8 damage on entry).

**19. Crystal Lance** (Projectile, Tier 4)
- Mana: 70 | Cooldown: 30s | Mastery: 70
- Launches a massive amethyst crystal spike that pierces through ALL entities
  in a line, dealing 15 damage to each. Sticks in the ground at the end,
  becoming a temporary block that emits light. Entities hit are pinned (immobile 3s).

**20. Golem Summon** (Summon, Tier 5 — ULTIMATE)
- Mana: 130 | Cooldown: 300s | Mastery: 90
- Summon an Arcane Golem (reskinned iron golem + purple glow) for 60 seconds.
  200 HP, deals 15 damage per hit. Follows you and attacks your targets.
  Right-click the golem to command: Defend Position / Follow / Attack Target.
- THE POWER TRIP: Walk into a boss fight with your personal golem tanking hits.

### AEROMANCY (Wind — Mobility + Displacement)

**21. Gust** (Projectile, Tier 1)
- Mana: 10 | Cooldown: 2s | Mastery: 0
- Invisible wind projectile that pushes entities 8 blocks back. No damage.
- UTILITY: Push mobs off cliffs. Deflect projectiles. Push items around.
  Use on yourself while mid-air for horizontal boost (pseudo-elytra!).

**22. Levitate** (Self-Buff, Tier 2)
- Mana: 5/sec (continuous) | Mastery: 15
- Hold right-click to hover. Move with WASD while floating. Release to fall.
  Not true flight — you can only go UP and hover, not dash.
- COMBO: Levitate up, then Gust yourself sideways = poor man's flight.

**23. Tornado** (AoE, Tier 3)
- Mana: 55 | Cooldown: 25s | Mastery: 40
- Summons a 3-block-wide tornado that travels forward for 10 seconds.
  Entities hit are launched 10+ blocks in the air and spin wildly (nausea 3s).
  Picks up loose items and drops, scattering them everywhere.
  DESTRUCTIVE: Breaks leaves, flowers, crops, tall grass in its path.

**24. Wind Walk** (Self-Buff, Tier 4)
- Mana: 60 | Cooldown: 45s | Mastery: 70
- For 20 seconds: Speed III, Jump Boost III, No Fall Damage, can double-jump.
  Each double-jump creates a visible air burst below you.
  FEEL: You become a ninja. Sprint across rooftops, jump ravines, scale mountains.

**25. Hurricane** (World Event, Tier 5 — ULTIMATE)
- Mana: 150 | Cooldown: 300s | Mastery: 90
- 30-block radius wind zone for 15 seconds. ALL entities (including caster!)
  are violently pushed toward the center, then launched upward.
  Loose blocks (sand, gravel) are ripped up and orbit the eye.
  Arrows and projectiles are deflected. Trees lose their leaves.
- THE CHAOS: Cast this on a mob spawner room and watch the carnage.

### UMBRAMANCY (Shadow — Stealth + Debuffs + Teleportation)

**26. Shadow Step** (Instant Teleport, Tier 1)
- Mana: 20 | Cooldown: 5s | Mastery: 0
- Teleport to the block you're looking at (max 16 blocks). Leaves a puff of 
  shadow particles at departure and arrival. Brief invulnerability during transit.
- THE CORE MOBILITY SPELL: In combat, blink behind enemies. In exploration, 
  cross gaps. In PvP, dodge attacks. This spell alone makes Umbramancy worth it.

**27. Dark Pulse** (AoE, Tier 2)
- Mana: 30 | Cooldown: 10s | Mastery: 15
- 6-block radius shadow burst around caster. 6 damage + Blindness 4 sec.
  Extinguishes all torches and light sources in range (they can be relit).
- TACTICAL: Blind a group, then Shadow Step behind the biggest threat.

**28. Fear** (Targeted, Tier 3)
- Mana: 40 | Cooldown: 20s | Mastery: 40
- Target mob flees from you for 8 seconds (like a wolf fleeing). Works on most 
  mobs including mini-bosses. Full bosses resist but are slowed instead.
- SPECIAL: Feared entities drop whatever they're holding (skeletons drop bows!).

**29. Shadow Clone** (Summon, Tier 4)
- Mana: 75 | Cooldown: 45s | Mastery: 70
- Creates a shadow copy of yourself that fights alongside you for 30 seconds.
  The clone has your equipment stats, deals 50% of your damage, and draws aggro.
  Mobs can't tell the clone from you — they randomly target either.
- BOSS STRATEGY: Clone takes half the boss's hits. You deal full damage.

**30. Void Rift** (Construct, Tier 5 — ULTIMATE)
- Mana: 160 | Cooldown: 300s | Mastery: 90
- Opens a 3-block-wide portal to the void for 10 seconds. ANY entity that
  enters is dealt 30 damage + teleported to a random location within 100 blocks.
  The rift pulls entities toward it (suction effect, 5 block range).
  EVEN BOSSES can be pulled in (they take damage but don't teleport).
- THE PANIC BUTTON: Open this in a losing fight to scatter everything.

### LUXOMANCY (Light — Healing + Protection + Anti-Undead)

**31. Heal** (Targeted/Self, Tier 1)
- Mana: 20 | Cooldown: 5s | Mastery: 0
- Heals target (or self) for 6 HP. Warm golden particles. Can target other players.
- SPECIAL: Heals undead in reverse — deals 6 damage to zombies, skeletons, etc.

**32. Purify** (AoE Cleanse, Tier 2)
- Mana: 25 | Cooldown: 12s | Mastery: 15
- Removes ALL negative status effects from caster and allies within 5 blocks.
  Also removes Corruption effect (from dark magic / shadow spells).
- COMBO: Purify in a poison cloud = neutralizes the cloud entirely.

**33. Sun Beam** (Beam, Tier 3)
- Mana: 8/sec (continuous) | Mastery: 40
- Hold right-click to fire a concentrated beam of light. 6 damage/sec to mobs,
  12 damage/sec to undead. The beam creates temporary light source blocks along 
  its path (level 15, fades after 5 sec). LIGHTS UP DARK AREAS as you sweep.
- EXPLORATION: Sweep a dark cave to illuminate it temporarily.

**34. Holy Shield** (Construct, Tier 4)
- Mana: 80 | Cooldown: 40s | Mastery: 70
- Creates a golden dome (5 block radius) for 12 seconds. 
  Allies inside gain Regeneration I. HOSTILE PROJECTILES CANNOT ENTER.
  Mobs can walk in but take 4 damage on entry and are slowed.
- BOSS FIGHTS: Safe zone during AoE attacks. Heal up. Then re-engage.

**35. Divine Judgment** (Targeted, Tier 5 — ULTIMATE)
- Mana: 170 | Cooldown: 300s | Mastery: 90
- Summon a beam of pure light from the sky onto a single target.
  50 damage to undead. 25 damage to other mobs. Heals allies for 20 HP.
  Creates a permanent light source at the target for 5 minutes.
- THE FINISHER: One-shot most undead bosses. The visual is spectacular —
  a column of golden light visible from 200+ blocks away.

### ARCANOMANCY (Arcane — Utility + Meta-Magic + Reality Manipulation)

**36. Mana Bolt** (Projectile, Tier 1)
- Mana: 8 | Cooldown: 1s | Mastery: 0
- Cheapest, fastest repeatable spell. 3 damage. No special effects.
- BUT: Affected by ALL staff modifiers and enchantments. The "base canvas"
  spell that becomes powerful through your build. Stack spell power enchantments
  and this humble bolt becomes a machinegun of destruction.

**37. Dispel** (Targeted, Tier 2)
- Mana: 30 | Cooldown: 15s | Mastery: 15
- Remove ALL magical effects from a target. Buffs, debuffs, shields, everything.
  Also destroys magical constructs (Fire Walls, Ice Shields, Wards, etc.).
- COUNTER-MAGIC: In PvP, strip an opponent's buffs. Against bosses, remove 
  their enrage buff or magical shield.

**38. Arcane Shield** (Self-Buff, Tier 3)
- Mana: 40 | Cooldown: 20s | Mastery: 40
- Absorbs the next 30 damage over 20 seconds. Unlike armor, this absorbs
  ALL damage types including magic, fire, fall, and void.
- VISUAL: Translucent purple hexagonal shield panels orbit the player.

**39. Telekinesis** (Targeted, Tier 4)
- Mana: 50 | Cooldown: 10s | Mastery: 70
- GRAB a block or entity and hold it in the air. Move your crosshair to move it.
  Right-click again to THROW it. Thrown entities take 10 damage on impact.
  Thrown blocks place where they land.
- THE SANDBOX SPELL: Pick up a creeper and throw it at a group of zombies.
  Pick up TNT and place it precisely. Rearrange blocks without mining.

**40. Time Slow** (World Event, Tier 5 — ULTIMATE)
- Mana: 180 | Cooldown: 300s | Mastery: 90
- Everything within 20 blocks moves at 25% speed for 8 seconds. 
  Projectiles hang in the air. Mobs crawl. Falling blocks drift.
  THE CASTER MOVES AT NORMAL SPEED.
- THE POWER FANTASY: Walk between frozen arrows. Dodge boss attacks in slow motion.
  Place 5 TNT while mobs helplessly watch. The ultimate "you are the archmage" moment.

---

## SPELL WEAVING COMBOS (13 unique combos)

These trigger when two specific spells affect the same area/entity within 3 seconds:

| Spell 1 | Spell 2 | Combo Effect |
|---------|---------|--------------|
| Fire Wall | Gust | **Flame Wave** — fire sweeps 15 blocks in wind direction |
| Frost Bolt | Earthquake | **Shatter** — frozen target explodes for 3x ice damage |
| Rain/Water | Chain Lightning | **Electrocution** — water zone becomes electric trap |
| Blizzard | Inferno | **Steam Explosion** — 10 bonus damage + blind all in zone |
| Mud Trap | Lightning | **Electrified Mud** — trapped enemies take continuous shock |
| Shadow Step + attack | Any damage | **Sneak Attack** — first hit after teleport deals 2x |
| Stone Skin | Earthquake | **Tremor Lord** — earthquake radius +50%, no self-knockback |
| Tornado | Fireball | **Fire Tornado** — tornado catches fire, ignites everything |
| Holy Shield | Blizzard | **Fortress of Ice** — dome becomes ice, reflects projectiles |
| Levitate | Thunderclap | **Aerial Shockwave** — AoE radius 2x while airborne |
| Fear | Dark Pulse | **Terror** — fear duration 2x, fleeing mobs are slowed |
| Heal | Sun Beam | **Radiant Burst** — beam also heals allies it passes through |
| Time Slow | Any projectile | **Frozen Arrows** — your projectiles resume speed on time-end |

---

## MASTERY SYSTEM

Each school tracks mastery (0-100) independently. Gained by:
- Casting spells from that school (+1 per cast, diminishing returns)
- Killing mobs with that school's spells (+2 per kill)
- Completing school-specific challenges (+10 per challenge)
- Using the school's essence in rituals (+5)

Mastery unlocks:
| Level | Unlock |
|-------|--------|
| 0 | Tier 1 spell |
| 15 | Tier 2 spell + staff |
| 40 | Tier 3 spell |
| 50 | Hybrid staff available (if both schools 50+) |
| 70 | Tier 4 spell |
| 80 | School's reactive enchantment |
| 90 | Tier 5 ULTIMATE spell |
| 100 | Grand Master title + passive bonus always active |

Grand Master passives (mastery 100):
- Pyromancy: Permanent fire resistance
- Cryomancy: Immune to slowness, freeze water you walk on
- Electromancy: Immune to lightning, +20% attack speed
- Geomancy: Haste I while on stone, +2 armor always
- Aeromancy: Permanent slow fall, double jump always
- Umbramancy: Invisible while sneaking (always), night vision
- Luxomancy: Permanent Regeneration I, undead flee from you
- Arcanomancy: +50 max mana, all spells from all schools -10% cost
