package net.candiebunnie.starlightbutterflies.block.entity;

import net.candiebunnie.starlightbutterflies.StarlightButterflies;
import net.candiebunnie.starlightbutterflies.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ButterflyEggBlockEntity extends BlockEntity implements Nameable {

    @Nullable
    private Component name;
    private String genome;
    private boolean dropItem = true;

    public ButterflyEggBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(ModBlockEntities.BUTTERFLYEGGBE.get(), p_155229_, p_155230_);
    }

    @Override
    public Component getName() {
        return (Component)(this.name != null ? this.name : Component.translatable("block.starlightbutterflies.butterfly_egg") );
    }

    @Override
    public Component getCustomName() {
        return this.name;
    }

    public void setCustomName(String input) {
        this.name = Component.Serializer.fromJson(input);
    }

    public String getGenome() {
        return this.genome;
    }
    public void setGenome(String genome) {
        this.genome = genome;
    }

    protected void saveAdditional(CompoundTag p_187456_) {
        super.saveAdditional(p_187456_);
        if (this.genome != null) {
            p_187456_.putString(StarlightButterflies.MOD_ID + ".genome", this.genome);
        }

        if (this.name != null) {
            p_187456_.putString("Name", Component.Serializer.toJson(this.name));
        }

    }

    public void load(@NotNull CompoundTag p_155042_) {
        super.load(p_155042_);
        CompoundTag tag = (CompoundTag) p_155042_.get("display");
        if (tag != null) {
            setCustomName(tag.getString("Name"));
        } else {
            setCustomName(p_155042_.getString("Name"));
        }
        this.setGenome(p_155042_.getString(StarlightButterflies.MOD_ID + ".genome"));
    }

    public ItemStack getItem() {
        ItemStack itemstack = new ItemStack(ModBlocks.BUTTERFLYEGG.get());
        if (this.genome != null) {
            CompoundTag nbtData = new CompoundTag();
            nbtData.putString(StarlightButterflies.MOD_ID + ".genome",genome);
            itemstack.setTag(nbtData);
        }

        if (this.name != null) {
            itemstack.setHoverName(this.name);
        }

        return itemstack;
    }

    public void dropItem(Level p_18993_, double p_18994_, double p_18995_, double p_18996_) {
        if(this.dropItem) {
            ItemEntity itementity = new ItemEntity(p_18993_, p_18994_, p_18995_, p_18996_, this.getItem());
            float f = 0.05F;
            itementity.setDeltaMovement(p_18993_.random.triangle(0.0D, 0.11485000171139836D), p_18993_.random.triangle(0.2D, 0.11485000171139836D), p_18993_.random.triangle(0.0D, 0.11485000171139836D));
            p_18993_.addFreshEntity(itementity);
        }
    }

    public void hatch(){
        this.dropItem = false;
    }

}
