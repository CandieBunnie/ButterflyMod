package net.candiebunnie.starlightbutterflies.event;

import net.candiebunnie.starlightbutterflies.StarlightButterflies;
import net.candiebunnie.starlightbutterflies.entity.client.models.ButterflyModel;
import net.candiebunnie.starlightbutterflies.entity.client.models.LarvaModel;
import net.candiebunnie.starlightbutterflies.entity.client.ModModelLayers;
import net.candiebunnie.starlightbutterflies.item.ModItems;
import net.candiebunnie.starlightbutterflies.item.custom.ButterflyCharmItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterItemDecorationsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = StarlightButterflies.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.BUTTERFLY_LAYER, ButterflyModel::createBodyLayer);

        event.registerLayerDefinition(ModModelLayers.LARVA_LAYER, LarvaModel::createSolidBodyLayer);
        event.registerLayerDefinition(ModModelLayers.LARVA_OUTER_LAYER, LarvaModel::createOuterBodyLayer);
    }

    @SubscribeEvent
    public static void registerItemColours(RegisterColorHandlersEvent.Item event) {
        event.register(ButterflyCharmItem::getColour, ModItems.FILLEDBUTTERFLYCHARM.get());
    }
}
