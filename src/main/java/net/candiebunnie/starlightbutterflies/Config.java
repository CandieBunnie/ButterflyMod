package net.candiebunnie.starlightbutterflies;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = StarlightButterflies.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

//    private static final ForgeConfigSpec.IntValue EGG_TIME = BUILDER
//            .comment("Ticks for egg to hatch: Default = 12000")
//            .defineInRange("EggTicks", 12000, 300, 24000);
//
//    private static final ForgeConfigSpec.IntValue CHRYSALIS_TIME = BUILDER
//            .comment("Ticks for charm metamorphosis: Default = 12000")
//            .defineInRange("MetamorphosisTicks", 12000, 300, 24000);

//    private static final ForgeConfigSpec.BooleanValue WING_DAMAGE_ON = BUILDER
//            .comment("Whether butterfly wings can be damaged: Default = true")
//            .define("DoWingDamage", true);

//    private static final ForgeConfigSpec.BooleanValue WING_DAMAGE_ITEM = BUILDER
//            .comment("Enables crafting an item to heal butterfly wings: Default = false")
//            .define("WingHealing", false);

//    public static final ForgeConfigSpec.ConfigValue<String> MAGIC_NUMBER_INTRODUCTION = BUILDER
//            .comment("What you want the introduction message to be for the magic number")
//            .define("magicNumberIntroduction", "The magic number is... ");

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static int EggTicks;
    public static int MetamorphosisTicks;
    public static boolean doWingDamage;
    //public static boolean WingHealing;
    //public static String magicNumberIntroduction;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        //EggTicks = EGG_TIME.get();
        //MetamorphosisTicks = CHRYSALIS_TIME.get();
        //doWingDamage = WING_DAMAGE_ON.get();
        //WingHealing = WING_DAMAGE_ITEM.get();
        //magicNumberIntroduction = MAGIC_NUMBER_INTRODUCTION.get();

    }
}
