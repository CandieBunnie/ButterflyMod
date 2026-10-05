package net.candiebunnie.starlightbutterflies.item.custom;

import net.candiebunnie.starlightbutterflies.block.entity.ButterflyEggBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class ButterflyEggItem extends BlockItem {

    public ButterflyEggItem(Block p_40565_, Properties p_40566_) {
        super(p_40565_, p_40566_);
    }

    @Override
    @NotNull
    public InteractionResult place(BlockPlaceContext blockplacecontext) {
        InteractionResult result = super.place(blockplacecontext);
        if(result != InteractionResult.FAIL){
            ItemStack itemstack = blockplacecontext.getItemInHand();
            ButterflyEggBlockEntity blockentity = (ButterflyEggBlockEntity) blockplacecontext.getLevel().getBlockEntity(blockplacecontext.getClickedPos());
            CompoundTag tag = itemstack.getTag();
            if (tag != null && blockentity != null) {
                blockentity.load(tag);
            }
        }
        return result;
    }

}
