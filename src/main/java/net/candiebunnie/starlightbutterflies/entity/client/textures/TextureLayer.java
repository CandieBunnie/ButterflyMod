package net.candiebunnie.starlightbutterflies.entity.client.textures;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class TextureLayer {
    private static final Map<ResourceLocation, NativeImage> IMAGES = Maps.newHashMap();

    private final ResourceLocation texture;
    private final float[] colour;
    private final Type type;
    private final String name;

    private TextureLayer(String locName, ResourceLocation location, float[] colour, Type type) {
        this.texture = location;
        this.colour = colour;
        this.type = type;
        String[] locNameList = locName.split("/");
        // start at 2 to cut out texture/entity
        // names don't have to be unique, probably, as they will still be ordered and stuff
        char[] chars = new char[locNameList.length-2];
        for (int i = 0; i < locNameList.length-2; i++) {
            chars[i] = locNameList[i+2].charAt(0);
        }
        if(Arrays.equals(colour, new float[]{0, 0, 0})){
            this.name = new String(chars) + locNameList[locNameList.length-2];
        } else {
            this.name = new String(chars) + locNameList[locNameList.length-2] + nameColour(colour);
        }
        //System.out.println("New TextureLayer created: " + this.name);
    }

    public TextureLayer(String locName, ResourceLocation location, float[] colour) {
        this(locName, location, colour, Type.NORMAL);
    }

    public TextureLayer(String locName, ResourceLocation location, Type type) {
        this(locName, location, new float[]{0, 0, 0}, type);
    }

    public static void applyPattern(NativeImage base, NativeImage pattern){
        // combine pattern and base via multiplication
            for (int y = 0; y < base.getHeight(); ++y) {
                // loop over every pixel
                for (int x = 0; x < base.getWidth(); ++x) {
                    // if new pixel is not transparent
                    if (getAlphaAsFloat(pattern, x, y) == 1) {
                        // get replacement colours
                        float r = getRedAsFloat(base, x, y)*getRedAsFloat(pattern, x, y);
                        float g = getGreenAsFloat(base, x, y)*getGreenAsFloat(pattern, x, y);
                        float b = getBlueAsFloat(base, x, y)*getBlueAsFloat(pattern, x, y);
                        float a = getAlphaAsFloat(base, x, y);

                        // replace the old pixel
                        setRGBA(base, x, y, r, g, b, a);
                    }
                }
            }
    }

    public void addTo(NativeImage image, ResourceManager manager) {
        switch (type) {
            case NORMAL:
                this.addNormal(image, manager);
                break;
            case BASE:
                this.addBase(image, manager);
                break;
            case ERASE:
                this.addErase(image, manager);
                break;
        }
    }

    private void addNormal(NativeImage image, ResourceManager manager) {
        // add layer to image, with colour
        NativeImage newImage = this.getUncolouredImage(manager);
        if(newImage != null) {
            for (int y = 0; y < image.getHeight(); ++y) {
                // loop over every pixel
                for (int x = 0; x < image.getWidth(); ++x) {
                    // if new pixel is not transparent
                    if (getAlphaAsFloat(newImage, x, y) == 1) {
                        // get replacement colours
                        float r = getMultipliedRedAsFloat(newImage, x, y);
                        float g = getMultipliedGreenAsFloat(newImage, x, y);
                        float b = getMultipliedBlueAsFloat(newImage, x, y);

                        // replace the old pixel
                        setRGBA(image, x, y, r, g, b, 1);
                    }
                }
            }
        }
    }

    private void addBase(NativeImage image, ResourceManager manager) {
        // add layer to image, without involving our colour
        NativeImage newImage = this.getUncolouredImage(manager);
        if(newImage != null) {
            for (int y = 0; y < image.getHeight(); ++y) {
                // loop over every pixel
                for (int x = 0; x < image.getWidth(); ++x) {
                    // if new pixel is not transparent
                    if (getAlphaAsFloat(newImage, x, y) == 1) {
                        // get replacement colours
                        float r = getRedAsFloat(newImage, x, y);
                        float g = getGreenAsFloat(newImage, x, y);
                        float b = getBlueAsFloat(newImage, x, y);

                        // replace the old pixel
                        setRGBA(image, x, y, r, g, b, 1);
                    }
                }
            }
        }
    }

    private void addErase(NativeImage image, ResourceManager manager) {
        // erase layer from image
        NativeImage newImage = this.getUncolouredImage(manager);
        if(newImage != null) {
            for (int y = 0; y < image.getHeight(); ++y) {
                // loop over every pixel
                for (int x = 0; x < image.getWidth(); ++x) {
                    // if new pixel is not transparent
                    if (getAlphaAsFloat(newImage, x, y) == 1) {
                        // replace the old pixel
                        setRGBA(image, x, y, 0, 0, 0, 0);
                    }
                }
            }
        }
    }

    public NativeImage getImage(ResourceManager manager) {
        NativeImage output = getUncolouredImage(manager);
        if (output != null && type == Type.NORMAL) {
                colourLayer(output);
        }
        return output;
    }

    private NativeImage getUncolouredImage(ResourceManager manager) {
        // use our resourceLocation to get our base texture
        NativeImage source = loadImage(manager);
        if(source == null) {
            return null;
        }
        NativeImage base = new NativeImage(source.format(), source.getWidth(), source.getHeight(), false);
        base.copyFrom(source);
        return base;
    }

    private NativeImage loadImage(ResourceManager manager) {
        if (!IMAGES.containsKey(texture)) {
            try {
                Resource resource = manager.getResource(texture).orElseThrow();
                IMAGES.put(texture, NativeImage.read(resource.open()));
            } catch (IOException ioexception) {
                System.err.println("Failed to load texture: " + texture);
                return null;
            }
        }
        return IMAGES.get(texture);
    }

    public enum Type {
        NORMAL, // colour your image, and layer over old pixels (ex. eyes, which don't have further colouring done)
        BASE, // do not colour the image to leave its texture intact
        ERASE // if alpha!=0, set alpha to 0
        // perhaps we should add together all patterns before adding them to the final texture
        // so starting at the bottom, the new pattern is dyed and added normally to the old
        // then at the end of all that we have a matrix of colours to multiply with the bases
        // yeah that makes the most sense I think

        // so process the texture like so:
        // layer base, using normal and base type layers
        // layer the patterns, using pattern type layers
        // combine the patterns with the base
        // add whichever erase type layers you may have

        // patterns should probably just be considered normal type and kept separate in the LayeredTexture
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    private static String nameColour(float[] colour){
        int[] ints = new int[3];
        for(int i = 0; i < 3; i++){
            ints[i] = (int)(colour[i] * 255);
        }
        String[] strings = new String[3];
        for(int i = 0; i < 3; i++){
            if(ints[i] < 10){
                strings[i] = "00" + ints[i];
            } else if (ints[i] < 100) {
                strings[i] = "0" + ints[i];
            } else {
                strings[i] = Integer.toString(ints[i]);
            }
        }
        return strings[0]+strings[1]+strings[2];
    }

    // all below this is copied from sekelsta's "realistic horse genetics" mod
    private static void setRGBA(NativeImage image, int x, int y, float r, float g, float b, float a) {
        int ir = clamp((int)(r * 255));
        int ig = clamp((int)(g * 255));
        int ib = clamp((int)(b * 255));
        int ia = clamp((int)(a * 255));

        int redOffset = image.format().luminanceOrRedOffset();
        int greenOffset = image.format().luminanceOrGreenOffset();
        int blueOffset = image.format().luminanceOrBlueOffset();
        int alphaOffset = image.format().luminanceOrAlphaOffset();

        int color = (ir << redOffset) | (ig << greenOffset) | (ib << blueOffset) | (ia << alphaOffset);

        image.setPixelRGBA(x, y, color);
    }

    protected void colourLayer(NativeImage image) {
        for(int y = 0; y < image.getHeight(); ++y) {
            for(int x = 0; x < image.getWidth(); ++x) {
                float imgR = getMultipliedRedAsFloat(image, x, y);
                float imgG = getMultipliedGreenAsFloat(image, x, y);
                float imgB = getMultipliedBlueAsFloat(image, x, y);
                float imgA = getAlphaAsFloat(image, x, y);

                setRGBA(image, x, y, imgR, imgG, imgB, imgA);
            }
        }
    }

    // Restrict to range [0, 255]
    private static int clamp(int x) {
        return Math.max(0, Math.min(x, 255));
    }

    private static float getRedAsFloat(NativeImage image, int x, int y) {
        byte b = image.getRedOrLuminance(x, y);
        int i = (int)b & 255;
        return i / 255f;
    }

    private static float getGreenAsFloat(NativeImage image, int x, int y) {
        byte b = image.getGreenOrLuminance(x, y);
        int i = (int)b & 255;
        return i / 255f;
    }

    private static float getBlueAsFloat(NativeImage image, int x, int y) {
        byte b = image.getBlueOrLuminance(x, y);
        int i = (int)b & 255;
        return i / 255f;
    }

    private static float getAlphaAsFloat(NativeImage image, int x, int y) {
        byte b = image.getLuminanceOrAlpha(x, y);
        int i = (int)b & 255;
        return i / 255f;
    }

    private float getMultipliedRedAsFloat(NativeImage image, int x, int y) {
        return getRedAsFloat(image, x, y) * colour[0];
    }

    private float getMultipliedGreenAsFloat(NativeImage image, int x, int y) {
        return getGreenAsFloat(image, x, y) * colour[1];
    }

    private float getMultipliedBlueAsFloat(NativeImage image, int x, int y) {
        return getBlueAsFloat(image, x, y) * colour[2];
    }

}
