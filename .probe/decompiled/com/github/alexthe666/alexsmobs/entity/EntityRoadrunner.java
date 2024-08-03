package com.github.alexthe666.alexsmobs.entity;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.config.AMConfig;
import com.github.alexthe666.alexsmobs.entity.ai.AnimalAIWanderRanged;
import com.github.alexthe666.alexsmobs.item.AMItemRegistry;
import com.github.alexthe666.alexsmobs.misc.AMSoundRegistry;
import com.github.alexthe666.alexsmobs.misc.AMTagRegistry;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class EntityRoadrunner extends Animal {

    public float oFlapSpeed;

    public float oFlap;

    public float wingRotDelta = 1.0F;

    public float wingRotation;

    public float destPos;

    public float prevAttackProgress;

    public float attackProgress;

    private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(EntityRoadrunner.class, EntityDataSerializers.INT);

    public int timeUntilNextFeather = this.f_19796_.nextInt(24000) + 24000;

    private boolean hasMeepSpeed = false;

    protected EntityRoadrunner(EntityType type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        this.f_21345_.addGoal(0, new FloatGoal(this));
        this.f_21345_.addGoal(1, new PanicGoal(this, 1.1));
        this.f_21345_.addGoal(1, new MeleeAttackGoal(this, 1.0, false));
        this.f_21345_.addGoal(2, new BreedGoal(this, 1.0));
        this.f_21345_.addGoal(4, new FollowParentGoal(this, 1.1));
        this.f_21345_.addGoal(4, new TemptGoal(this, 1.1, Ingredient.of(AMTagRegistry.INSECT_ITEMS), false));
        this.f_21345_.addGoal(5, new AnimalAIWanderRanged(this, 50, 1.0, 25, 7));
        this.f_21345_.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.f_21345_.addGoal(7, new RandomLookAroundGoal(this));
        this.f_21346_.addGoal(1, new NearestAttackableTargetGoal(this, EntityRattlesnake.class, 55, true, true, null));
        this.f_21346_.addGoal(2, new HurtByTargetGoal(this, EntityRattlesnake.class, Player.class).setAlertOthers());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("FeatherTime")) {
            this.timeUntilNextFeather = compound.getInt("FeatherTime");
        }
    }

    @Override
    public boolean checkSpawnRules(LevelAccessor worldIn, MobSpawnType spawnReasonIn) {
        return AMEntityRegistry.rollSpawn(AMConfig.roadrunnerSpawnRolls, this.m_217043_(), spawnReasonIn);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("FeatherTime", this.timeUntilNextFeather);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return !this.isMeep() && this.f_19796_.nextInt(2000) != 0 ? AMSoundRegistry.ROADRUNNER_IDLE.get() : AMSoundRegistry.ROADRUNNER_MEEP.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return AMSoundRegistry.ROADRUNNER_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AMSoundRegistry.ROADRUNNER_HURT.get();
    }

    @Override
    protected void defineSynchedData() {
        super.m_8097_();
        this.f_19804_.define(ATTACK_TICK, 0);
    }

    @Override
    public boolean doHurtTarget(Entity entityIn) {
        this.f_19804_.set(ATTACK_TICK, 5);
        return true;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.CACTUS) || source.getMsgId().equals("anvil") || super.m_6673_(source);
    }

    public static AttributeSupplier.Builder bakeAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 8.0).add(Attributes.ATTACK_DAMAGE, 1.0).add(Attributes.MOVEMENT_SPEED, 0.45F).add(Attributes.FOLLOW_RANGE, 10.0);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.oFlap = this.wingRotation;
        this.prevAttackProgress = this.attackProgress;
        this.oFlapSpeed = this.destPos;
        this.destPos = (float) ((double) this.destPos + (double) (this.m_20096_() ? -1 : 4) * 0.3);
        this.destPos = Mth.clamp(this.destPos, 0.0F, 1.0F);
        if (!this.m_20096_() && this.wingRotDelta < 1.0F) {
            this.wingRotDelta = 1.0F;
        }
        if (!this.m_9236_().isClientSide && this.m_6084_() && !this.m_6162_() && --this.timeUntilNextFeather <= 0) {
            this.m_19998_(AMItemRegistry.ROADRUNNER_FEATHER.get());
            this.timeUntilNextFeather = this.f_19796_.nextInt(24000) + 24000;
        }
        this.wingRotDelta = (float) ((double) this.wingRotDelta * 0.9);
        Vec3 vector3d = this.m_20184_();
        if (!this.m_20096_() && vector3d.y < 0.0) {
            this.m_20256_(vector3d.multiply(1.0, 0.8, 1.0));
        }
        this.wingRotation = this.wingRotation + this.wingRotDelta * 2.0F;
        if (this.f_19804_.get(ATTACK_TICK) > 0) {
            if (this.f_19804_.get(ATTACK_TICK) == 2 && this.m_5448_() != null && (double) this.m_20270_(this.m_5448_()) < 1.3) {
                this.m_5448_().hurt(this.m_269291_().mobAttack(this), 2.0F);
            }
            this.f_19804_.set(ATTACK_TICK, this.f_19804_.get(ATTACK_TICK) - 1);
            if (this.attackProgress < 5.0F) {
                this.attackProgress++;
            }
        } else if (this.attackProgress > 0.0F) {
            this.attackProgress--;
        }
    }

    @Override
    public void tick() {
        super.m_8119_();
        if (this.isMeep()) {
            if (!this.hasMeepSpeed) {
                this.m_21051_(Attributes.MOVEMENT_SPEED).setBaseValue(1.0);
                this.hasMeepSpeed = true;
            }
        } else if (this.hasMeepSpeed) {
            this.m_21051_(Attributes.MOVEMENT_SPEED).setBaseValue(0.45F);
            this.hasMeepSpeed = false;
        }
        if (this.m_9236_().isClientSide && this.isMeep() && this.m_20096_() && !this.m_20072_() && this.m_20184_().lengthSqr() > 0.03) {
            Vec3 vector3d = this.m_20252_(0.0F);
            float yRotRad = this.m_146908_() * (float) (Math.PI / 180.0);
            float f = Mth.cos(yRotRad) * 0.2F;
            float f1 = Mth.sin(yRotRad) * 0.2F;
            float f2 = 1.2F - this.f_19796_.nextFloat() * 0.7F;
            for (int i = 0; i < 2; i++) {
                this.m_9236_().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.m_20185_() - vector3d.x * (double) f2 + (double) f, this.m_20186_() + (double) (this.f_19796_.nextFloat() * 0.2F), this.m_20189_() - vector3d.z * (double) f2 + (double) f1, 0.0, 0.0, 0.0);
                this.m_9236_().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.m_20185_() - vector3d.x * (double) f2 - (double) f, this.m_20186_() + (double) (this.f_19796_.nextFloat() * 0.2F), this.m_20189_() - vector3d.z * (double) f2 - (double) f1, 0.0, 0.0, 0.0);
            }
        }
    }

    public boolean causeFallDamage(float distance, float damageMultiplier) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        if (!this.isMeep()) {
            this.m_5496_(SoundEvents.CHICKEN_STEP, 0.15F, 1.0F);
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(AMTagRegistry.INSECT_ITEMS);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_241840_1_, AgeableMob p_241840_2_) {
        return AMEntityRegistry.ROADRUNNER.get().create(p_241840_1_);
    }

    public static boolean canRoadrunnerSpawn(EntityType<? extends Animal> animal, LevelAccessor worldIn, MobSpawnType reason, BlockPos pos, RandomSource random) {
        boolean spawnBlock = worldIn.m_8055_(pos.below()).m_204336_(AMTagRegistry.ROADRUNNER_SPAWNS);
        return spawnBlock && worldIn.m_45524_(pos, 0) > 8;
    }

    public boolean isMeep() {
        String s = ChatFormatting.stripFormatting(this.m_7755_().getString());
        return s != null && s.toLowerCase().contains("meep") || AlexsMobs.isAprilFools();
    }
}