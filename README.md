# Arcana — Magic, Tech & Adventure

**Forge 1.20.1** | **JDK 17** | All Phases Combined

350+ items, 125+ blocks, 40 spells, 8 magic schools, 20 biomes, 44 structures, 3 dimensions, 8 bosses.

## Setup
1. Create repo on GitHub
2. Add file `.github/workflows/main.yml` with this content:

```yaml
name: Build Mod
on: { push: { branches: ["**"] } }
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with: { java-version: '17', distribution: 'temurin' }
      - uses: gradle/actions/setup-gradle@v3
        with: { gradle-version: '8.1.1' }
      - run: gradle build
      - uses: actions/upload-artifact@v4
        with: { name: arcana-mod, path: build/libs/*.jar }
```

3. Unzip mod into repo root, commit, push. Download JAR from Actions.

## Content: 12 ores, 18 alloys, 14 gems, 16 essences, 50 tools, 60 armor, 12 weapons, 12 staffs, 10 wands, 40 spells (8 schools x 5), 13 spell combos, 40 scrolls, 17 accessories, 8 bosses, 20+ biomes, 44 structures, 3 dimensions, 32 machines, 8 lore books, mastery system, spell weaving, soul binding, environmental reactions, preparation magic.

## See design docs for full details:
- DESIGN-DOCUMENT.md — Complete content inventory
- MAGIC-DESIGN.md — All 40 spells with unique mechanics
- WORLD-DESIGN.md — All 20 biomes and 44 structures
- INTEGRATION-AUDIT.md — Progression gap analysis
- PLAYER-WALKTHROUGH.md — Minute-by-minute experience verification
