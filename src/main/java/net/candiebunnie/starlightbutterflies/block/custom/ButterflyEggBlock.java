package net.candiebunnie.starlightbutterflies.block.custom;

import net.candiebunnie.starlightbutterflies.Config;
import net.candiebunnie.starlightbutterflies.block.entity.ButterflyEggBlockEntity;
import net.candiebunnie.starlightbutterflies.entity.ModEntities;
import net.candiebunnie.starlightbutterflies.entity.custom.LarvaEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class ButterflyEggBlock extends BaseEntityBlock {
    public static final IntegerProperty HATCH = BlockStateProperties.HATCH;
    private static final int REGULAR_HATCH_TIME_TICKS = 12000;
    private static final int RANDOM_HATCH_OFFSET_TICKS = 300;
    private static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 12.0D, 12.0D);

    public ButterflyEggBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.stateDefinition.any().setValue(HATCH, Integer.valueOf(0)));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        ButterflyEggBlockEntity entity = new ButterflyEggBlockEntity(p_153215_, p_153216_);
        return entity;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        // Drop self when removed, unless removal is from hatching
        // is called before our block entity is killed so we probably do need to use this one
        if (pState.getBlock() != pNewState.getBlock()) {
            ButterflyEggBlock block;
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (pState.getBlock() instanceof ButterflyEggBlock && blockEntity instanceof ButterflyEggBlockEntity) {
                ((ButterflyEggBlockEntity) blockEntity).dropItem(pLevel, pPos.getX(), pPos.getY(), pPos.getZ());
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_277441_) {
        p_277441_.add(HATCH);
    }

    public VoxelShape getShape(BlockState p_277872_, BlockGetter p_278090_, BlockPos p_277364_, CollisionContext p_278016_) {
        return SHAPE;
    }

    // all hail the fabric wiki for actually having answers on it :D
    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    public int getHatchLevel(BlockState p_279125_) {
        return p_279125_.getValue(HATCH);
    }

    private boolean isReadyToHatch(BlockState p_278021_) {
        return this.getHatchLevel(p_278021_) == 2;
    }

    public void tick(BlockState p_277841_, ServerLevel p_277739_, BlockPos p_277692_, RandomSource p_277973_) {
        if (!this.isReadyToHatch(p_277841_)) {
            p_277739_.playSound((Player)null, p_277692_, SoundEvents.SNIFFER_EGG_CRACK, SoundSource.BLOCKS, 0.7F, 0.9F + p_277973_.nextFloat() * 0.2F);
            p_277739_.setBlock(p_277692_, p_277841_.setValue(HATCH, Integer.valueOf(this.getHatchLevel(p_277841_) + 1)), 2);
        } else {
            p_277739_.playSound((Player)null, p_277692_, SoundEvents.SNIFFER_EGG_HATCH, SoundSource.BLOCKS, 0.7F, 0.9F + p_277973_.nextFloat() * 0.2F);
            LarvaEntity larva = ModEntities.LARVA.get().create(p_277739_);
            if (larva != null) {
                ButterflyEggBlockEntity be = (ButterflyEggBlockEntity) p_277739_.getBlockEntity(p_277692_);
                Vec3 vec3 = p_277692_.getCenter();
                larva.setBaby(true);
                if(be != null && be.getGenome() != null) {
                    larva.setGenes(be.getGenome());
                } else {
                    larva.randomiseGenes();
                }
                if(be != null) {
                    larva.setCustomName(be.getCustomName());
                    be.hatch();
                }
                p_277739_.destroyBlock(p_277692_, false); // have to break this AFTER we get the data out, lol
                larva.moveTo(vec3.x(), vec3.y(), vec3.z(), Mth.wrapDegrees(p_277739_.random.nextFloat() * 360.0F), 0.0F);
                p_277739_.addFreshEntity(larva);
            }

        }
    }

    public void onPlace(BlockState p_277964_, Level p_277827_, BlockPos p_277526_, BlockState p_277618_, boolean p_277819_) {
        int j = REGULAR_HATCH_TIME_TICKS / 3;
        p_277827_.gameEvent(GameEvent.BLOCK_PLACE, p_277526_, GameEvent.Context.of(p_277964_));
        p_277827_.scheduleTick(p_277526_, this, j + p_277827_.random.nextInt(RANDOM_HATCH_OFFSET_TICKS));
    }

    public boolean isPathfindable(BlockState p_279414_, BlockGetter p_279243_, BlockPos p_279294_, PathComputationType p_279299_) {
        return false;
    }

}
