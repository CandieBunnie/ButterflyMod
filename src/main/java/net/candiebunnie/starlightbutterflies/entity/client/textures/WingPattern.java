package net.candiebunnie.starlightbutterflies.entity.client.textures;

public class WingPattern {
    // reworking how wing pattern textures are constructed so that adding more is simpler and we can order them whichever way we want
    private final String path;
    private final String colour;
    private final boolean isForewing;
    private final String name;

    private WingPattern(String path, String colour, boolean isForewing){
        this.path = path;
        this.colour = colour;
        this.isForewing = isForewing;

        String[] locNameList = path.split("/");
        char[] chars = new char[locNameList.length];
        for (int i = 0; i < locNameList.length; i++) {
            chars[i] = locNameList[i].charAt(0);
        }
        if(isForewing){
            this.name = "f" + new String(chars) + colour;
        } else {
            this.name = "h" + new String(chars) + colour;
        }
    }
    public String getPath(){
        if(this.isForewing){
            return "textures/entity/butterfly/forewing/pattern/"+this.path+".png";
        } else {
            return "textures/entity/butterfly/hindwing/pattern/"+this.path+".png";
        }
    }
    public String getColour(){
        return this.colour;
    }
    public String getName(){
        return this.name;
    }

    // and now we begin publicly constructing
    // start each folder name with a different letter so that our resource locations don't get confused if we start putting things out of order
    // also, sort these alphabetically by folder name because organisation
    public static WingPattern base(int wingShape, String colour, boolean isForewing){
        return new WingPattern("base/"+wingShape,colour,isForewing);
    }

    public static WingPattern spots(int stripeNum, int spotNum, int wingShape, String colour, boolean isForewing){
        return new WingPattern("dots/"+stripeNum+"/"+(spotNum-1)+"/"+wingShape,colour,isForewing);
    }

    public static WingPattern eyeSpot(int spotNum, int wingShape, String colour, boolean isForewing){
        return new WingPattern("eyes/"+spotNum+"/"+wingShape,colour,isForewing);
    }

    public static WingPattern stripe(int stripeNum, int wingShape, String colour, boolean isForewing){
        return new WingPattern("stripes/"+stripeNum+"/"+wingShape,colour,isForewing);
    }

    public static WingPattern veins(int wingShape, String colour, boolean isForewing){
        return new WingPattern("veins/"+wingShape,colour,isForewing);
    }

}