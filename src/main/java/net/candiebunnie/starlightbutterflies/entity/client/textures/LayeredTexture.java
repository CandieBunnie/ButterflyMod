package net.candiebunnie.starlightbutterflies.entity.client.textures;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import net.candiebunnie.starlightbutterflies.StarlightButterflies;
import net.candiebunnie.starlightbutterflies.entity.custom.AbstractButterflyGenetics;
import net.candiebunnie.starlightbutterflies.entity.custom.ButterflyEntity;
import net.candiebunnie.starlightbutterflies.entity.custom.LarvaEntity;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import org.apache.commons.lang3.ArrayUtils;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class LayeredTexture extends AbstractTexture {
    private final ArrayList<TextureLayer> layers;
    private final ArrayList<TextureLayer> patterns;
    private static final Map<String, ResourceLocation> RESOURCE_LOCATIONS = Maps.newHashMap();
    private static final String[] colourCodes = {"0f","2d","4b","69","87","a5","c3","e1"}; // provides hex codes because I don't want to deal with conversions
    private static final int[] colourInts = {15,43,77,105,133,165,195,225}; // they have been unreliable throughout all of this

    public LayeredTexture(AbstractButterflyGenetics entity){
        layers = new ArrayList<>();
        patterns = new ArrayList<>();

        if(entity instanceof LarvaEntity){
            this.createLarvaTexture(entity);
        } else if (entity instanceof ButterflyEntity){
            this.createButterflyTexture(entity);
        }
    }

    private void createLarvaTexture(AbstractButterflyGenetics entity) {
        // Universal Textures
        newLayer("textures/entity/larva/body_1.png",entity.getBodyColour1()); // Body1
        newLayer("textures/entity/larva/body_2.png",entity.getBodyColour2()); // Body2
        newLayer("textures/entity/larva/body_3.png",entity.getBodySpotColour()); // Body3 (interior)
        newLayer("textures/entity/larva/eyes.png",entity.getEyeColour()); // Eyes
        // Antenna Texture (contains feet also)
        newLayer("textures/entity/larva/antenna/"+entity.getAntennaShape()+".png");
    }

    private void createButterflyTexture(AbstractButterflyGenetics entity) {
        // Universal Textures
        newLayer("textures/entity/butterfly/body/body_1_base.png"); // Body 1
        newPattern("textures/entity/butterfly/body/body_1.png",entity.getBodyColour1());
        newLayer("textures/entity/butterfly/body/body_2.png",entity.getBodyColour2()); // Body 2
        newLayer("textures/entity/butterfly/body/eyes.png",entity.getEyeColour()); // Eyes
        // Fluff
        if (entity.isFluffDark()) {
            newLayer("textures/entity/butterfly/body/fluff.png","0f0f0f"); // Fluff is black
        } else {
            newLayer("textures/entity/butterfly/body/fluff.png","e1e1e1"); // Fluff is white
        }
        // Spot Texture
        newPattern("textures/entity/butterfly/body/spots/"+entity.getBodySpotShape()+".png",entity.getBodySpotColour()); // Body Spot
        // Wing base Textures
        newLayer("textures/entity/butterfly/forewing/base/"+entity.getForewingShape()+".png"); // Forewing Base
        newLayer("textures/entity/butterfly/hindwing/base/"+entity.getHindwingShape()+".png"); // Hindwing Base

        // Antenna Texture (contains legs also)
        newLayer("textures/entity/butterfly/body/antenna/"+entity.getAntennaShape()+".png"); // Antenna
        // Wing Damage (should be the last newLayer called)
        newLayer("textures/entity/butterfly/damage/"+entity.getWingDamage()+".png",true); // wing damage

        // Patterns!
        newPattern("textures/entity/butterfly/forewing/pattern/base/"+entity.getForewingShape()+".png",entity.getForewingColour()); // Forewing Base
        //System.out.println("getForewingColour(): "+entity.getForewingColour());
        newPattern("textures/entity/butterfly/hindwing/pattern/base/"+entity.getHindwingShape()+".png",entity.getHindwingColour()); // Hindwing Base
        //System.out.println("getHindwingColour(): "+entity.getHindwingColour());
        if(entity.getVeinColour() != null){
            if(!entity.getVeinColour().isEmpty()){
                newPattern("textures/entity/butterfly/forewing/pattern/veins/"+entity.getForewingShape()+".png",entity.getVeinColour()); // Forewing Veins
                newPattern("textures/entity/butterfly/hindwing/pattern/veins/"+entity.getHindwingShape()+".png",entity.getVeinColour()); // Hindwing Veins
            }
        }
        //System.out.println("getVeinColour(): "+entity.getVeinColour());
        boolean[] stripes = entity.getForewingStripes();
        for(int i = 0; i < stripes.length; i++){
            if(stripes[i]){
                newPattern("textures/entity/butterfly/forewing/pattern/stripes/"+i+"/"+entity.getForewingShape()+".png",entity.getWingStripeColour(i));
                //System.out.println("getForewingStripeColour("+i+"): "+entity.getForewingStripeColour(i));
            }
        }
        stripes = entity.getHindwingStripes();
        for(int i = 0; i < stripes.length; i++){
            if(stripes[i]){
                newPattern("textures/entity/butterfly/hindwing/pattern/stripes/"+i+"/"+entity.getHindwingShape()+".png",entity.getWingStripeColour(i));
                //System.out.println("getHindwingStripeColour("+i+"): "+entity.getHindwingStripeColour(i));
            }
        }
        int[] spots = entity.getForewingSpots();
        for(int i = 0; i < spots.length; i++){
            if(spots[i] > 0){
                newPattern("textures/entity/butterfly/forewing/pattern/spots/"+i+"/"+(spots[i]-1)+"/"+entity.getForewingShape()+".png",entity.getWingSpotColour());
            }
        }
        spots = entity.getHindwingSpots();
        for(int i = 0; i < spots.length; i++){
            if(spots[i] > 0){
                newPattern("textures/entity/butterfly/hindwing/pattern/spots/"+i+"/"+(spots[i]-1)+"/"+entity.getHindwingShape()+".png",entity.getWingSpotColour());
            }
        }
        spots = entity.getEyeSpots();
        for(int i = 0; i < spots[0]; i++){
            newPattern("textures/entity/butterfly/forewing/pattern/eyes/"+i+"/"+entity.getForewingShape()+".png",entity.getEyeSpotColour(i));
        }
        for(int i = 0; i < spots[1]; i++){
            newPattern("textures/entity/butterfly/hindwing/pattern/eyes/"+i+"/"+entity.getHindwingShape()+".png",entity.getEyeSpotColour(i));
        }
    }

    private void newLayer(String location, boolean erase){
        if(erase){
            // the provided texture is used to erase pixels
            layers.add(new TextureLayer(location,getLocation(location), TextureLayer.Type.ERASE));
        }else{
            // adds a normal texture, but without colour
            layers.add(new TextureLayer(location,getLocation(location), TextureLayer.Type.BASE));
        }
    }
    private void newLayer(String location, String colour){
        // adds a normal texture, with colour applied directly
        layers.add(new TextureLayer(location,getLocation(location),colourToFloats(colour)));
    }
    private void newLayer(String location){
        // shortcut to add a base layer
        newLayer(location, false);
    }
    private void newPattern(String location, String colour){
        patterns.add(new TextureLayer(location,getLocation(location),colourToFloats(colour)));
    }

    public String getName(){
        String output = "";
        for(TextureLayer layer : layers){
            output = output + layer.getName();
        }
        for(TextureLayer layer : patterns){
            output = output + layer.getName();
        }
        //System.out.println("LayeredTexture.getName returning "+output);
        return output;
    }

    private static ResourceLocation getLocation(String location) {
        ResourceLocation output = RESOURCE_LOCATIONS.get(location);
        if (output == null) {
            //System.out.println("Adding resource location: "+location);
            output = ResourceLocation.fromNamespaceAndPath(StarlightButterflies.MOD_ID, location);
            RESOURCE_LOCATIONS.put(location, output);
        }
        return output;
    }

    private static float[] colourToFloats(String input){
        // input = 6 digit hex code, as generated in AbstractButterflyGenetics
        int[] output = new int[3];
        char[] chars = new char[2];

        if(input!=null){
            if(!input.isEmpty()){
            for(int i = 0; i<3; i++){
                chars[0] = input.charAt(i*2);
                chars[1] = input.charAt(i*2+1);
                output[i] = colourInts[ArrayUtils.indexOf(colourCodes, new String(chars))];
            }
            return new float[]{(float)output[0] / 255.0F, (float)output[1] / 255.0F, (float)output[2] / 255.0F};
        } else {
            //System.out.println("starlightbutterflies:LayeredTexture: colourToFloats input is empty");
            return new float[]{0f, 0f, 0f};
        }} else {
            //System.out.println("starlightbutterflies:LayeredTexture: colourToFloats input is null");
            return new float[]{0f, 0f, 0f};
        }
    }

    @Override
    public void load(ResourceManager manager) throws IOException {
        if(layers.isEmpty()){
            throw new IOException("attempting to load LayeredTexture without any layers");
        }
        NativeImage imageBase = layers.get(0).getImage(manager);

        for(int i = 0; i < layers.size(); i++){
            layers.get(i).addTo(imageBase, manager);
        }
        if(!patterns.isEmpty()){
            NativeImage imagePattern = patterns.get(0).getImage(manager);
            for(int i = 0; i < patterns.size(); i++){
                patterns.get(i).addTo(imagePattern, manager);
            }
            TextureLayer.applyPattern(imageBase, imagePattern);
        }

        if (!RenderSystem.isOnRenderThreadOrInit()) {
            RenderSystem.recordRenderCall(() -> {
                this.loadImage(imageBase);
            });
        } else {
            this.loadImage(imageBase);
        }
    }

    private void loadImage(NativeImage imageIn) {
        TextureUtil.prepareImage(this.getId(), imageIn.getWidth(), imageIn.getHeight());
        imageIn.upload(0, 0, 0, true);
    }

}
