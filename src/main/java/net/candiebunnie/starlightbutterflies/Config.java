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

    private static final ForgeConfigSpec.IntValue MUTATION_CHANCE = BUILDER
            .comment("Genes have a 1 in x+1 chance to mutate, set to -1 to disable mutations, set to 0 to disable genetics: Default = 99")
            .defineInRange("MutationChance", 99, -1, 99);

    private static final ForgeConfigSpec.IntValue EGG_TIME = BUILDER
            .comment("\n Base Ticks for an egg to hatch, this will only apply to newly placed eggs: Default = 12000")
            .defineInRange("EggTicks", 12000, 300, 24000);

    private static final ForgeConfigSpec.IntValue EGG_OFFSET = BUILDER
            .comment("\n Random Tick Offset for an egg to hatch, this will only apply to newly placed eggs: Default = 300")
            .defineInRange("EggOffset", 300, 30, 600);

    private static final ForgeConfigSpec.IntValue CHRYSALIS_TIME = BUILDER
            .comment("\n Progress/Tick for charm metamorphosis, completed at 24000: Default = 2")
            .defineInRange("MetamorphosisTimer", 2, 1, 80);

    private static final ForgeConfigSpec.BooleanValue WING_DAMAGE_ON = BUILDER
            .comment("\n Whether butterfly wings can be damaged, changing this will not heal existing butterflies: Default = true")
            .define("DoWingDamage", true);

    // unlikely to be implemented
//    private static final ForgeConfigSpec.BooleanValue WING_DAMAGE_ITEM = BUILDER
//            .comment("Enables crafting an item to heal butterfly wings: Default = false")
//            .define("WingHealing", false);

    // kept as example in case I need it
//    public static final ForgeConfigSpec.ConfigValue<String> MAGIC_NUMBER_INTRODUCTION = BUILDER
//            .comment("What you want the introduction message to be for the magic number")
//            .define("magicNumberIntroduction", "The magic number is... ");

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static int MutationChance;
    public static int EggTicks;
    public static int EggOffset;
    public static int MetamorphosisTimer;
    public static boolean doWingDamage;
    //public static boolean WingHealing;
    //public static String magicNumberIntroduction;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        MutationChance = MUTATION_CHANCE.get();
        EggTicks = EGG_TIME.get();
        EggOffset = EGG_OFFSET.get();
        MetamorphosisTimer = CHRYSALIS_TIME.get();
        doWingDamage = WING_DAMAGE_ON.get();
        //WingHealing = WING_DAMAGE_ITEM.get();
        //magicNumberIntroduction = MAGIC_NUMBER_INTRODUCTION.get();

    }
}
