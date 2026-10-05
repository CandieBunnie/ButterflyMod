package net.candiebunnie.starlightbutterflies.block;

import net.candiebunnie.starlightbutterflies.StarlightButterflies;
import net.candiebunnie.starlightbutterflies.block.custom.ButterflyEggBlock;
import net.candiebunnie.starlightbutterflies.item.ModItems;
import net.candiebunnie.starlightbutterflies.item.custom.ButterflyEggItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, StarlightButterflies.MOD_ID);

    public static final RegistryObject<Block> BUTTERFLYEGG = registerEggBlock("butterfly_egg", () -> new ButterflyEggBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(0.5F).sound(SoundType.SLIME_BLOCK).noOcclusion()));

    public static final RegistryObject<Block> ICEEGG = registerBlock("egg_in_ice", () -> new HalfTransparentBlock(BlockBehaviour.Properties.of().mapColor(MapColor.ICE).friction(0.98F).randomTicks().strength(0.5F).sound(SoundType.GLASS).noOcclusion()));

    public static final RegistryObject<Block> AMBEREGG = registerBlock("egg_in_amber", () -> new HalfTransparentBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).strength(0.5F).sound(SoundType.CALCITE).noOcclusion()));
    public static final RegistryObject<Block> AMBER = registerBlock("amber", () -> new HalfTransparentBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).strength(0.5F).sound(SoundType.CALCITE).noOcclusion()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }
    private static <T extends Block> RegistryObject<T> registerEggBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerEggItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
    private static <T extends Block> RegistryObject<Item> registerEggItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new ButterflyEggItem(block.get(), new Item.Properties().stacksTo(1)));
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
