package net.candiebunnie.starlightbutterflies.entity.custom;

import net.candiebunnie.starlightbutterflies.StarlightButterflies;
import net.candiebunnie.starlightbutterflies.block.ModBlocks;
import net.candiebunnie.starlightbutterflies.block.custom.ButterflyEggBlock;
import net.candiebunnie.starlightbutterflies.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class ButterflyEntity extends AbstractButterflyGenetics implements PlayerRideableJumping {
    private static final int MaxWingDamage = 5;

    protected static final EntityDataAccessor<Float> WINGZROTATION = SynchedEntityData.<Float>defineId(ButterflyEntity.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Integer> WINGRESETTIMEOUT = SynchedEntityData.<Integer>defineId(ButterflyEntity.class, EntityDataSerializers.INT);

    protected float playerJumpPendingScale;
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    private float wingRotationz;
    private float wingRotationy;

    public ButterflyEntity(EntityType<? extends Animal> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
    }

    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide()) {
            setupAnimationStates();
        } else {
            if(this.getHealth() < this.getMaxHealth() && !this.isDeadOrDying()) {
                if(random.nextInt(900) == 0){
                    this.setHealth(this.getHealth()+1);
                }
            }

        }
        if(!this.isVehicle()){
            resetWingRotation();
        }
        updateWingZRotation();

    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick){
        float f;
        if(this.getPose() == Pose.STANDING){
            f = Math.min(pPartialTick * 6F, 1f);
        } else {
            f = 0f;
        }

        this.walkAnimation.update(f, 0.2f);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(WINGZROTATION, 10F);
        this.entityData.define(WINGRESETTIMEOUT, 0);
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putFloat(StarlightButterflies.MOD_ID + ".rotation", this.entityData.get(WINGZROTATION));
        compoundTag.putInt(StarlightButterflies.MOD_ID + ".timeout", this.entityData.get(WINGRESETTIMEOUT));
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.entityData.set(WINGZROTATION, (compoundTag.getFloat(StarlightButterflies.MOD_ID + ".rotation")));
        this.entityData.set(WINGRESETTIMEOUT, (compoundTag.getInt(StarlightButterflies.MOD_ID + ".timeout")));
    }

    public float[] getWingRotation(){
        float y = this.wingRotationy/2;
        float z = ((this.wingRotationy/5)+2); // 1-6
        z = z*-15;
        z += this.wingRotationz-20;
        return new float[]{y,z};
    }

    private void resetWingRotation(){
        if(getWingResetTimeout() > 0){
            setWingResetTimeout(getWingResetTimeout()-1);
            this.entityData.set(WINGZROTATION, (getTargetWingRotation()+10)/2F);
            this.wingRotationy = this.wingRotationy*0.75F;
        }  else {
            this.entityData.set(WINGZROTATION, 10F);
            this.wingRotationy = 0;
        }
    }
    private void setWingRotation(float x){
        setWingResetTimeout(120);
        this.entityData.set(WINGZROTATION, x);
    }

    private float getTargetWingRotation(){
        return this.entityData.get(WINGZROTATION);
    }

    private void setWingResetTimeout(int x){
        this.entityData.set(WINGRESETTIMEOUT, x);
    }

    private int getWingResetTimeout(){
        return this.entityData.get(WINGRESETTIMEOUT);
    }

    private void updateWingZRotation(){
        if(getTargetWingRotation() != this.wingRotationz){
            if(getTargetWingRotation() < wingRotationz){
                this.wingRotationz -= 10;
            } else {
                this.wingRotationz += 10;
            }
        } else {
            if(getTargetWingRotation() != 10){
                setWingRotation(10F);
            }
        }
    }

    private void updateWingYRotation(double y){
        setWingResetTimeout(40);
        this.wingRotationy = (float) (25*y)-5;
    }

    @Override
    protected void registerGoals(){
        //behaviour/ai, lower number = higher priority
        this.goalSelector.addGoal(0,new FloatGoal(this)); // floats in water

        this.goalSelector.addGoal(1, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.0D, Ingredient.of(Items.HONEYCOMB), false));

        this.goalSelector.addGoal(3,new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(4,new LookAtPlayerGoal(this, Player.class, 3f));
        this.goalSelector.addGoal(5,new RandomLookAroundGoal(this));

    }

    public InteractionResult mobInteract(Player p_29489_, InteractionHand p_29490_) {
        boolean flag = this.isFood(p_29489_.getItemInHand(p_29490_));
        if (!flag && !this.isVehicle() && !p_29489_.isSecondaryUseActive()) {
            if (!this.level().isClientSide) {
                p_29489_.startRiding(this);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            InteractionResult interactionResult = super.mobInteract(p_29489_, p_29490_);
            if (!interactionResult.consumesAction()) {
                ItemStack itemstack = p_29489_.getItemInHand(p_29490_);
                if(itemstack.is(ModItems.EMPTYBUTTERFLYCHARM.get())){
                    return itemstack.interactLivingEntity(p_29489_, this, p_29490_);
                } else if(itemstack.is(ModItems.WINGHEALER.get()) && this.getWingDamage() > 0){
                    this.setWingDamage(getWingDamage()-1);
                    this.usePlayerItem(p_29489_, p_29490_, itemstack);
                    return InteractionResult.SUCCESS;
                } else {
                    return InteractionResult.PASS;
                }
            } else {
                return interactionResult;
            }
        }
    }

    protected void tickRidden(Player p_278233_, Vec3 p_275693_) {
        super.tickRidden(p_278233_, p_275693_);
        Vec2 vec2 = this.getRiddenRotation(p_278233_);
        this.setRot(vec2.y, vec2.x);
        this.yRotO = this.yBodyRot = this.yHeadRot = this.getYRot();
        if (this.isControlledByLocalInstance()) {
            if (this.playerJumpPendingScale>0) {
                    this.executeRidersJump(this.playerJumpPendingScale,p_275693_);
            }
        }

    }

    public void onPlayerJump(int p_30591_) {
        if (p_30591_ >= 90) {
            setWingRotation(70f);
            this.playerJumpPendingScale = 1.0F;
        } else {
            setWingRotation(50f);
            this.playerJumpPendingScale = 0.4F + 0.4F * (float)p_30591_ / 90.0F;
        }
    }

    public boolean canJump() {
        return true;
    }

    public void handleStartJump(int p_30574_) {
        // does this do anything??? Maybe. idk.
        if (p_30574_ >= 90) {
            this.entityData.set(WINGZROTATION,70f);
        } else {
            this.entityData.set(WINGZROTATION,50f);
        }
    }

    public void handleStopJump() {
    }

    protected void executeRidersJump(float p_248808_, Vec3 p_275435_) {
        double d0 = this.getCustomJump() * (double)p_248808_ * (double)this.getBlockJumpFactor();
        double d1 = d0 + (double)this.getJumpBoostPower();
        Vec3 vec3 = this.getDeltaMovement();
        this.setDeltaMovement(vec3.x, d1, vec3.z);
        this.hasImpulse = true;
        net.minecraftforge.common.ForgeHooks.onLivingJump(this);
        if (p_275435_.z > 0.0D) {
            float f = Mth.sin(this.getYRot() * ((float)Math.PI / 180F));
            float f1 = Mth.cos(this.getYRot() * ((float)Math.PI / 180F));
            this.setDeltaMovement(this.getDeltaMovement().add((double)(-0.4F * f * p_248808_), 0.0D, (double)(0.4F * f1 * p_248808_)));
        }
        this.playerJumpPendingScale = 0;
    }

    public double getCustomJump() {
        return 2D+(this.getWingDurability()*((5-this.getWingDamage())*0.05))-((double) this.getWingDamage() /4);
    }

    protected Vec2 getRiddenRotation(LivingEntity p_275502_) {
        return new Vec2(p_275502_.getXRot() * 0.5F, p_275502_.getYRot());
    }

    protected Vec3 getRiddenInput(Player p_278278_, Vec3 p_275506_) {
        if (!this.onGround()) {
            double z = p_278278_.getLookAngle().y; // is a value between 1 and -1
            if((z>0.0D)){ // is now a value between 0 and 1 that gets farther from 0 when looking down
                z = 0;
            }else if(z<0.0D) {
                z = z*-1;
            }
            // take a moment to animate, higher number = more forward
            updateWingYRotation(z);
            z = 1-z; // z is now a value between 0 and 1 that gets closer to 0 when looking down
            // LIFT CALCULATIONS START HERE
            double damp = ((5-this.getWingDamage())*0.1)/2; // increase dampening effect when wings are whole
            double y = 0.0D-(this.getDeltaMovement().y*(z+damp)); // dampen vertical motion when looking up
            if(y<0.0D) { // do not dampen upwards movement
                y = 0.0D;
            }
            // the minimum amount of movement should change based on wing damage, but without increasing speed overall
            if(z<damp*2) { // add a (soft) floor to the forward speed
                z = (z+(damp*2))/2;
            }
            return new Vec3(0.0D, y, z);
        } else {
            resetWingRotation();
            float f = p_278278_.xxa * 0.5F;
            float f1 = p_278278_.zza;
            if (f1 <= 0.0F) {
                f1 *= 0.25F;
            }
            return new Vec3((double)f, 0.0D, (double)f1);
        }
    }

    protected float getRiddenSpeed(Player p_278336_) {
        if (this.onGround()) {
            return (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED);
        } else {
            return (float) this.getAttributeValue(Attributes.FLYING_SPEED)/((this.getWingDamage()/2F)+1)*0.8F;
        }
    }

    @Nullable
    public LivingEntity getControllingPassenger() {
        Entity entity = this.getFirstPassenger();
        if (entity instanceof Mob) {
            return (Mob)entity;
        } else {
                entity = this.getFirstPassenger();
                if (entity instanceof Player) {
                    return (Player)entity;
                }
            return null;
        }
    }

    protected void positionRider(Entity p_289569_, Entity.MoveFunction p_289558_) {
        super.positionRider(p_289569_, p_289558_);
        float f = Mth.sin(this.yBodyRot * ((float)Math.PI / 180F));
        float f1 = Mth.cos(this.yBodyRot * ((float)Math.PI / 180F));
        float f2 = 0.3F; //backwards
        float f3 = -0.28F; //upwards
        p_289558_.accept(p_289569_, this.getX() + (double)(f2 * f), this.getY() + this.getPassengersRidingOffset() + p_289569_.getMyRidingOffset() + (double)f3, this.getZ() - (double)(f2 * f1));
        if (p_289569_ instanceof LivingEntity) {
            ((LivingEntity)p_289569_).yBodyRot = this.yBodyRot;
        }
    }

    @javax.annotation.Nullable
    private Vec3 getDismountLocationInDirection(Vec3 p_30562_, LivingEntity p_30563_) {
        double d0 = this.getX() + p_30562_.x;
        double d1 = this.getBoundingBox().minY;
        double d2 = this.getZ() + p_30562_.z;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for(Pose pose : p_30563_.getDismountPoses()) {
            blockpos$mutableblockpos.set(d0, d1, d2);
            double d3 = this.getBoundingBox().maxY + 0.75D;

            while(true) {
                double d4 = this.level().getBlockFloorHeight(blockpos$mutableblockpos);
                if ((double)blockpos$mutableblockpos.getY() + d4 > d3) {
                    break;
                }

                if (DismountHelper.isBlockFloorValid(d4)) {
                    AABB aabb = p_30563_.getLocalBoundsForPose(pose);
                    Vec3 vec3 = new Vec3(d0, (double)blockpos$mutableblockpos.getY() + d4, d2);
                    if (DismountHelper.canDismountTo(this.level(), p_30563_, aabb.move(vec3))) {
                        p_30563_.setPose(pose);
                        return vec3;
                    }
                }

                blockpos$mutableblockpos.move(Direction.UP);
                if (!((double)blockpos$mutableblockpos.getY() < d3)) {
                    break;
                }
            }
        }

        return null;
    }

    public Vec3 getDismountLocationForPassenger(LivingEntity p_30576_) {
        Vec3 vec3 = getCollisionHorizontalEscapeVector((double)this.getBbWidth(), (double)p_30576_.getBbWidth(), this.getYRot() + (p_30576_.getMainArm() == HumanoidArm.RIGHT ? 90.0F : -90.0F));
        Vec3 vec31 = this.getDismountLocationInDirection(vec3, p_30576_);
        if (vec31 != null) {
            return vec31;
        } else {
            Vec3 vec32 = getCollisionHorizontalEscapeVector((double)this.getBbWidth(), (double)p_30576_.getBbWidth(), this.getYRot() + (p_30576_.getMainArm() == HumanoidArm.LEFT ? 90.0F : -90.0F));
            Vec3 vec33 = this.getDismountLocationInDirection(vec32, p_30576_);
            return vec33 != null ? vec33 : this.position();
        }
    }

    public static AttributeSupplier.Builder createAttributes(){
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 30D)
                .add(Attributes.MOVEMENT_SPEED, 0.1D)
                .add(Attributes.FOLLOW_RANGE, 24D)
                .add(Attributes.FLYING_SPEED, 1D)
                .add(Attributes.ARMOR, 2D);
    }

    public void setAttributes(){
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20D+this.getGeneticHealth());
        if(this.hasFluff()){
            this.getAttribute(Attributes.ARMOR).setBaseValue(8D);
            this.getAttribute(Attributes.FLYING_SPEED).setBaseValue(1D+(this.getFlightSpeed()/5F));
        } else {
            this.getAttribute(Attributes.FLYING_SPEED).setBaseValue(1D+(this.getFlightSpeed()/4F));
        }
    }

    @Override
    public boolean hurt(DamageSource p_19946_, float p_19947_) {
        if (!this.isInvulnerableTo(p_19946_)) {
            int wingDamage = this.getWingDamage();
            if (wingDamage < MaxWingDamage) {
                if(random.nextInt((this.getWingDurability()*2) + 10) == 0){
                    this.setWingDamage(getWingDamage()+1);
                }
            }
        }
        return super.hurt(p_19946_, p_19947_);
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.HONEYCOMB);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }

    @Override
    public void spawnChildFromBreeding(ServerLevel p_277923_, Animal p_277857_) {
        ButterflyEntity partner = (ButterflyEntity) p_277857_;

        ButterflyEggBlock itemType = (ButterflyEggBlock) ModBlocks.BUTTERFLYEGG.get();

        ItemStack itemstack = new ItemStack(itemType);
        CompoundTag nbtData = new CompoundTag();
        nbtData.putString(StarlightButterflies.MOD_ID + ".genome",generatedGenes(this.getGenes(),partner.getGenes()));
        itemstack.setTag(nbtData);

        ItemEntity itementity = new ItemEntity(p_277923_, this.position().x(), this.position().y(), this.position().z(), itemstack);
        itementity.setDefaultPickUpDelay();
        this.finalizeSpawnChildFromBreeding(p_277923_, p_277857_, (AgeableMob)null);
        this.playSound(SoundEvents.SNIFFER_EGG_PLOP, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 0.5F);
        p_277923_.addFreshEntity(itementity);
    }

    public boolean isPushable() {
        return !this.isVehicle();
    }

    public boolean isBaby(){
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.AXOLOTL_IDLE_AIR;
    }
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.AXOLOTL_DEATH;
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource){
        return SoundEvents.AXOLOTL_HURT;
    }

}
