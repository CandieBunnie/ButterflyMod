package net.candiebunnie.starlightbutterflies.entity.client.render;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.candiebunnie.starlightbutterflies.StarlightButterflies;
import net.candiebunnie.starlightbutterflies.entity.client.ModModelLayers;
import net.candiebunnie.starlightbutterflies.entity.client.models.LarvaModel;
import net.candiebunnie.starlightbutterflies.entity.client.models.LarvaOuterLayer;
import net.candiebunnie.starlightbutterflies.entity.client.textures.LayeredTexture;
import net.candiebunnie.starlightbutterflies.entity.custom.LarvaEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class LarvaRenderer extends MobRenderer<LarvaEntity, LarvaModel<LarvaEntity>> {
    private static final Map<String, ResourceLocation> LARVA_LOCATION_CACHE = Maps.newHashMap();

    public LarvaRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new LarvaModel<>(pContext.bakeLayer(ModModelLayers.LARVA_LAYER)), 0.5f);
        this.addLayer(new LarvaOuterLayer<>(this, pContext.getModelSet()));
    }

    @Override
    public ResourceLocation getTextureLocation(LarvaEntity entity) {
            ResourceLocation resourcelocation = LARVA_LOCATION_CACHE.get(entity.getTextureName()); // checks if there is a resourcelocation set up already

            if (resourcelocation == null) // if not
            {
                resourcelocation = ResourceLocation.fromNamespaceAndPath(StarlightButterflies.MOD_ID,entity.getTextureName()); // create a new resourcelocation
                Minecraft.getInstance().getTextureManager().register(
                        resourcelocation,
                        entity.getTexture()
                ); // creates a new texture image thing and gives it to minecraft
                LARVA_LOCATION_CACHE.put(entity.getTextureName(), resourcelocation); // saves the texture at the place for future reference
            }
            return resourcelocation;
    }

        @Override
    public void render(LarvaEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pMatrixStack.scale(0.5f, 0.5f, 0.5f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

}
