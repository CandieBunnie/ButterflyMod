package net.candiebunnie.starlightbutterflies.block.entity;

import net.candiebunnie.starlightbutterflies.StarlightButterflies;
import net.candiebunnie.starlightbutterflies.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, StarlightButterflies.MOD_ID);

    public static final RegistryObject<BlockEntityType<ButterflyEggBlockEntity>> BUTTERFLYEGGBE =
            BLOCK_ENTITIES.register("butterfly_egg_be", () ->
                    BlockEntityType.Builder.of(ButterflyEggBlockEntity::new, ModBlocks.BUTTERFLYEGG.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

}
