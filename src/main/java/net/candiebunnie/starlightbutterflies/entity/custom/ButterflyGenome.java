package net.candiebunnie.starlightbutterflies.entity.custom;

public class ButterflyGenome {

    // labels genes and provides ranges for randomisation
    public enum GENOME {
        // comma to separate, semicolon to end
        // functional
        HEALTH(2,10), // added to base health
        SPEED(2, 10), // added to base (flight) speed
        DURABILITY(2, 5), // affects armor and scarring, added to base
        // head
        CURL(2,4), // Antenna Curl
        LENGTH(2,1), // Length of Antennae
        // thorax
        FLUFF(2, 2), // Has neck fluff yes/no, yes = dominant
        FLUFFSHADE(2, 2), // Colour of neck fluff and general undertone, darker = recessive
        BODYSPOT(2,2), // whether thorax spot is present, yes = dominant
        SPOTSHAPE(10, 3), // Shape of the spot
        // wing shapes
        DECOR(2,2), // Whether the wings have smooth edges, no = recessive
        FOREROUND(2,2), // Roundness of the forewing
        FORESIZE(2,2), // Width of the forewing
        HINDTAIL(2,2), // Swallowtail? yes = recessive
        HINDSIZE(2,2), // Determines the size of the tail, if present
        // wing patterns (stripes)
        STRIPEAPRESENCE(2,2),
        STRIPELIMIT1(2,2),
        STRIPELIMIT2(2,2),
        STRIPELIMIT3(2,2),
        STRIPELIMIT4(2,2),
        STRIPESIMILAR(2,2),
        STRIPESWITCH1(2,1),
        STRIPESWITCH2(2,1),
        STRIPESWITCH3(2,1),
        STRIPESWITCH4(2,1),
        STRIPESWITCH5(2,1),
        STRIPEBPRESENCE(2,1),
        STRIPEBWEIGHT(2,1),
        // wing patterns (first spots)
        HINDSPOTSMODIFIER(2,2), // whether the value of spots on the hindwing is -1/0/+1
        WINGSPOTS0(2,2), // spots located on stripe 0
        WINGSPOTS1(2,3), // spots located on stripe 1
        WINGSPOTS2(2,4), // etc
        WINGSPOTS3(2,3),
        WINGSPOTS4(2,2),
        SPOTLIMIT(2,1), // whether spots are limited to the outer edge
        SPOTPRESENCE(2,2), // whether spots are present, yes = dominant
        // wing patterns (second spots)
        EYESPOTLAYERS(2,2), // additive
        FOREEYEPRESENCE(2,2), // presence = recessive
        HINDEYEPRESENCE(2,2),
        // colour palette
        WINGSHADE1(2,2), // whether black or white is added to the wing palette by default, based on fluff, operates opposite to BODYTINT
        RED1(8,1), // Red Value for Colour 1
        GREEN1(8,1), // Green
        BLUE1(8,1), // Blue
        RED2(8,1), // Red Value for Colour 2
        GREEN2(8,1), // Green
        BLUE2(8,1), // Blue
        GRADSHARED(3,1), // which RGB value is shared by both colours of the gradient
        SHAREACTIVE(2,2), // whether we actually use GRADSHARED, yes = dominant
        GRADCHOICE(2,1), // whether to pick the first or second value to share
        COLOURLIMIT1(2,2), // dominant +1
        COLOURLIMIT2(2,2), // recessive +1
        COLOURLIMIT3(2,2), // recessive +1
        COLOURSELECT1(3,1),
        COLOURSELECT2(3,1),
        COLOURSELECT3(2,1),
        // colour assignment
        // available colours: 4x gradient, accent, static white, static black, fluff- 3x choose colour, 1x from gradient or accent
        // body
        BODYCOLOUR1(6,1), // thorax can be anything
        BODYCOLOUR2(6,1), // head/abdomen can also be anything
        BODYTINT(2,2), // whether colours on the body are weighted towards or away from fluff- towards = dominant
        COLOUREDEYES(2,2), // Whether The Eyes are coloured instead of black, Yes = Recessive
        EYECOLOUR(3,1), // which colour the eyes are if so
        BODYSPOTCOLOUR(6,1), // spot can be anything
        // wings
        FOREWINGBASE1(2,2), // which colour the forewing is
        FOREWINGBASE2(2,2),
        FOREWINGBASE3(2,2),
        HINDWINGBASE1(2,2),
        HINDWINGBASE2(2,2),
        HINDWINGBASE3(2,2),
        BASESIMILAR(2,2), // whether the base colours are the same, yes = dominant
        BASECHOICE(1,1), // which to pick if above is true

        VEINPRESENCE(2,1), // whether the veins have pigment
        VEINCOLOUR1(2,2), // whether that pigment is fancy, yes = recessive
        VEINCOLOUR2(4,1), // which colour to use if the above is true

        STRIPECOLOUR(2,2),
        STRIPECOLOUR2PRESENCE(2,2), // presence = recessive
        STRIPECOLOUR2(5,1), // colour choice
        STRIPECOLOUR2PLACES(2,8), // which stripes
        EYESPOTCOLOUR(5,3),
        WINGSPOTCOLOUR(2,2);


        private final int range;
        private final int copies;
        private int index;

        private GENOME(int range, int copies){
            this.range = range;
            this.copies = copies;
        }
        private void setIndex(int i){ this.index = i;}
        public int getRange(){ return this.range; }
        public int getCopies(){ return this.copies; }
        public int getIndex(){ return this.index; }

        public static final int size; // full length of the genome, tallies up all copies
        public static final GENOME[] values;
        static {
            int tempSize = 0;
            values = values();
            for(int i = 0; i < values.length; i++){
                values[i].setIndex(tempSize);
                tempSize += values[i].getCopies();
            }
            size = tempSize;
        }

    }

}
