package net.candiebunnie.starlightbutterflies.entity;

import net.candiebunnie.starlightbutterflies.StarlightButterflies;
import net.candiebunnie.starlightbutterflies.entity.custom.ButterflyEntity;
import net.candiebunnie.starlightbutterflies.entity.custom.LarvaEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    // registers the entities
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, StarlightButterflies.MOD_ID);

    // numbers here are the hitbox size
    public static final RegistryObject<EntityType<ButterflyEntity>> BUTTERFLY = ENTITY_TYPES.register("butterfly", () -> EntityType.Builder.of(ButterflyEntity::new, MobCategory.CREATURE).sized(1.75f,1.25f).build("butterfly"));
    public static final RegistryObject<EntityType<LarvaEntity>> LARVA = ENTITY_TYPES.register("larva", () -> EntityType.Builder.of(LarvaEntity::new, MobCategory.CREATURE).sized(1.25f,0.5f).build("larva"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
