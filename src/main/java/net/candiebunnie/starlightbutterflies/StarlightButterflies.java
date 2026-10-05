package net.candiebunnie.starlightbutterflies;

import com.mojang.logging.LogUtils;
import net.candiebunnie.starlightbutterflies.block.ModBlocks;
import net.candiebunnie.starlightbutterflies.block.entity.ModBlockEntities;
import net.candiebunnie.starlightbutterflies.entity.ModEntities;
import net.candiebunnie.starlightbutterflies.entity.client.render.ButterflyRenderer;
import net.candiebunnie.starlightbutterflies.entity.client.render.LarvaRenderer;
import net.candiebunnie.starlightbutterflies.item.ModItems;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(StarlightButterflies.MOD_ID)
public class StarlightButterflies
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "starlightbutterflies";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public StarlightButterflies(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        ModEntities.register(modEventBus);
        ModBlockEntities.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        //adds to vanilla tabs
        if(event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModItems.EMPTYBUTTERFLYCHARM);
            event.accept(ModItems.WINGHEALER);
        }
        if(event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(ModItems.BUTTERFLYSPAWNEGG);
            event.accept(ModItems.LARVASPAWNEGG);
        }
        if(event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(ModBlocks.AMBER.get());
            event.accept(ModBlocks.AMBEREGG.get());
            event.accept(ModBlocks.ICEEGG.get());
            event.accept(ModBlocks.BUTTERFLYEGG.get());
        }

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            EntityRenderers.register(ModEntities.BUTTERFLY.get(), ButterflyRenderer::new);
            EntityRenderers.register(ModEntities.LARVA.get(), LarvaRenderer::new);
        }
    }
}