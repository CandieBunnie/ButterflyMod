package net.candiebunnie.starlightbutterflies.entity.client.render;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.candiebunnie.starlightbutterflies.StarlightButterflies;
import net.candiebunnie.starlightbutterflies.entity.client.ModModelLayers;
import net.candiebunnie.starlightbutterflies.entity.client.models.ButterflyModel;
import net.candiebunnie.starlightbutterflies.entity.client.textures.LayeredTexture;
import net.candiebunnie.starlightbutterflies.entity.custom.ButterflyEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class ButterflyRenderer extends MobRenderer<ButterflyEntity, ButterflyModel<ButterflyEntity>> {
    private static final Map<String, ResourceLocation> BUTTERFLY_LOCATION_CACHE = Maps.newHashMap();

    public ButterflyRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new ButterflyModel<>(pContext.bakeLayer(ModModelLayers.BUTTERFLY_LAYER)), 1.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(ButterflyEntity entity) {
        ResourceLocation resourcelocation = BUTTERFLY_LOCATION_CACHE.get(entity.getTextureName()); // checks if there is a resourcelocation set up already

        if (resourcelocation == null) // if not
        {
            resourcelocation = ResourceLocation.fromNamespaceAndPath(StarlightButterflies.MOD_ID,entity.getTextureName()); // create a new resourcelocation
            Minecraft.getInstance().getTextureManager().register(
                    resourcelocation,
                    entity.getTexture()
            ); // creates a new texture image thing and gives it to minecraft
            BUTTERFLY_LOCATION_CACHE.put(entity.getTextureName(), resourcelocation); // saves the texture at the place for future reference
            //System.out.println("texture registered successfully");
        }
        return resourcelocation;
        // // DEBUG
        //return new ResourceLocation(StarlightButterflies.MOD_ID,"textures/entity/butterflybase.png");
    }

    @Override
    public void render(ButterflyEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

}
