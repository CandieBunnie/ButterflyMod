package net.candiebunnie.starlightbutterflies.event;

import net.candiebunnie.starlightbutterflies.StarlightButterflies;
import net.candiebunnie.starlightbutterflies.entity.ModEntities;
import net.candiebunnie.starlightbutterflies.entity.custom.ButterflyEntity;
import net.candiebunnie.starlightbutterflies.entity.custom.LarvaEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = StarlightButterflies.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.BUTTERFLY.get(), ButterflyEntity.createAttributes().build());
        event.put(ModEntities.LARVA.get(), LarvaEntity.createAttributes().build());
    }

}
