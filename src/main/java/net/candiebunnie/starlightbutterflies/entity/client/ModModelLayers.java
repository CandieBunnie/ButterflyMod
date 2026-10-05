package net.candiebunnie.starlightbutterflies.entity.client;

import net.candiebunnie.starlightbutterflies.StarlightButterflies;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModModelLayers {
    public static final ModelLayerLocation LARVA_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(StarlightButterflies.MOD_ID, "larva_layer"), "main");
    public static final ModelLayerLocation LARVA_OUTER_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(StarlightButterflies.MOD_ID, "larva_layer"), "outer");

    public static final ModelLayerLocation BUTTERFLY_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(StarlightButterflies.MOD_ID, "butterfly_layer"), "main");
}
