package net.candiebunnie.starlightbutterflies.item.custom;

import net.candiebunnie.starlightbutterflies.Config;
import net.candiebunnie.starlightbutterflies.entity.ModEntities;
import net.candiebunnie.starlightbutterflies.entity.custom.AbstractButterflyGenetics;
import net.candiebunnie.starlightbutterflies.entity.custom.ButterflyEntity;
import net.candiebunnie.starlightbutterflies.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nullable;
import java.util.Optional;

public class ButterflyCharmItem extends Item {
    private static final String[] colourCodes = {"0f","2d","4b","69","87","a5","c3","e1"}; // provides hex codes because I don't want to deal with bases
    private static final int[] colourInts = {15,43,77,105,133,165,195,225}; // they have been unreliable throughout all of this

    public ButterflyCharmItem(Item.Properties properties) {
        super(properties);
    }

    // we are using durability to keep track of age because model overrides are my nemesis and the automatic progress bar that comes of this is useful
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotIndex, boolean pBool) {
        if(pStack.getOrCreateTag().getInt("Damage") > 0){
            CompoundTag tag = pStack.getTag();
            int damage = tag.getInt("Damage");
            damage--;
            tag.putInt("Damage", damage);
        }
    }

    public static int getColour(ItemStack pStack, int index) {
        CompoundTag tag = pStack.getOrCreateTagElement("display");
        switch (index) {
            case 0:
                return -1;
            case 1:
                return colourToInt(tag.getString("1"));
            case 2:
                return colourToInt(tag.getString("2"));
            case 3:
                return colourToInt(tag.getString("3"));
            case 4:
                return colourToInt(tag.getString("4"));
        }
        return -1;
    }

    private static int colourToInt(String input){
        if(input.length() > 5){
            String r = "" + input.charAt(0) + input.charAt(1);
            String g = "" + input.charAt(2) + input.charAt(3);
            String b = "" + input.charAt(4) + input.charAt(5);
            int[] output = new int[3];
            output[0] = colourInts[ArrayUtils.indexOf(colourCodes, r)];
            output[1] = colourInts[ArrayUtils.indexOf(colourCodes, g)];
            output[2] = colourInts[ArrayUtils.indexOf(colourCodes, b)];
            return (output[0] << 8 | output[1]) << 8 | output[2];
        }
        return -1;
    }

    public InteractionResult useOn(UseOnContext p_43223_) {
        Level level = p_43223_.getLevel();
        if (!(level instanceof ServerLevel)) {
            return InteractionResult.SUCCESS;
        } else {
            ItemStack itemstack = p_43223_.getItemInHand();
            if(itemstack.getTag().getInt("Damage") == 0 && itemstack.is(ModItems.FILLEDBUTTERFLYCHARM.get())){
                BlockPos blockpos = p_43223_.getClickedPos();
                Direction direction = p_43223_.getClickedFace();
                BlockState blockstate = level.getBlockState(blockpos);

                BlockPos blockpos1;
                if (blockstate.getCollisionShape(level, blockpos).isEmpty()) {
                    blockpos1 = blockpos;
                } else {
                    blockpos1 = blockpos.relative(direction);
                }

                spawn((ServerLevel)level, itemstack, blockpos1, direction);
                ItemStack itemstack1 = new ItemStack(ModItems.EMPTYBUTTERFLYCHARM.get());
                p_43223_.getPlayer().setItemInHand(p_43223_.getHand(), itemstack1);
                return InteractionResult.SUCCESS;
            }

            return super.useOn(p_43223_);
        }
    }

    private void spawn(ServerLevel p_151142_, ItemStack p_151143_, BlockPos p_151144_, Direction face) {
        ButterflyEntity butterfly = ModEntities.BUTTERFLY.get().create(p_151142_);
        if (butterfly != null && p_151143_.getTag() != null) {
            Vec3 vec3 = p_151144_.getCenter();
            butterfly.loadFromCharmTag(p_151143_.getTag());
            if(p_151143_.hasCustomHoverName()){
                butterfly.setCustomName(p_151143_.getHoverName());
            }
            if(face == Direction.DOWN){
                vec3 = vec3.subtract(0,1,0);
            } else {
                vec3 = vec3.subtract(0,0.5,0);
            }
            butterfly.moveTo(vec3.x(), vec3.y(), vec3.z(), Mth.wrapDegrees(p_151142_.random.nextFloat() * 360.0F), 0.0F);
            p_151142_.addFreshEntity(butterfly);
        }
    }

    public static Optional<InteractionResult> butterflyPickup(Player pPlayer, InteractionHand pInteractionHand, AbstractButterflyGenetics pEntity) {
        ItemStack itemstack = pPlayer.getItemInHand(pInteractionHand);
        if (itemstack.getItem() == ModItems.EMPTYBUTTERFLYCHARM.get() && pEntity.isAlive()) {
            pEntity.playSound(pEntity.getPickupSound(), 1.0F, 1.0F);
            ItemStack itemstack1 = new ItemStack(ModItems.FILLEDBUTTERFLYCHARM.get());
            pEntity.saveToCharmTag(itemstack1);
            pPlayer.setItemInHand(pInteractionHand, itemstack1);
            Level level = pEntity.level();

            pEntity.discard();
            return Optional.of(InteractionResult.sidedSuccess(level.isClientSide));
        } else {
            return Optional.empty();
        }
    }

}
