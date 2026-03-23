package com.arcana.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * Base class for passive/neutral Arcana mobs (butterflies, deer, foxes, NPCs, etc.)
 * Extends Animal (not Monster) so they won't be hostile and won't despawn.
 */
public class ArcanaPassiveMob extends Animal {

    public ArcanaPassiveMob(EntityType<? extends ArcanaPassiveMob> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }

    // Animals require this but we don't need breeding
    @Override
    public ArcanaPassiveMob getBreedOffspring(net.minecraft.server.level.ServerLevel level,
                                               net.minecraft.world.entity.AgeableMob mob) {
        return null;
    }

    public static AttributeSupplier.Builder createPassiveAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FOLLOW_RANGE, 16.0D);
    }
}
