package net.candiebunnie.starlightbutterflies.item;

import net.candiebunnie.starlightbutterflies.Config;
import net.candiebunnie.starlightbutterflies.StarlightButterflies;
import net.candiebunnie.starlightbutterflies.entity.ModEntities;
import net.candiebunnie.starlightbutterflies.item.custom.ButterflyCharmItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, StarlightButterflies.MOD_ID);

    public static final RegistryObject<Item> EMPTYBUTTERFLYCHARM = ITEMS.register("butterfly_charm", () -> new ButterflyCharmItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> FILLEDBUTTERFLYCHARM = ITEMS.register("butterfly_charm_full", () -> new ButterflyCharmItem(new Item.Properties().stacksTo(1).defaultDurability(12000).setNoRepair().fireResistant()));

    public static final RegistryObject<Item> WINGHEALER = ITEMS.register("wing_poultice", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BUTTERFLYSPAWNEGG = ITEMS.register("butterfly_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.BUTTERFLY, 0x6c30a1, 0xe3a1e1, new Item.Properties()));
    public static final RegistryObject<Item> LARVASPAWNEGG = ITEMS.register("butterfly_larva_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.LARVA, 0xe3a1e1, 0xdaC767, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
