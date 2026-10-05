package net.candiebunnie.starlightbutterflies.entity.custom;

import net.candiebunnie.starlightbutterflies.StarlightButterflies;
import net.candiebunnie.starlightbutterflies.entity.client.textures.LayeredTexture;
import net.candiebunnie.starlightbutterflies.item.ModItems;
import net.candiebunnie.starlightbutterflies.item.custom.ButterflyCharmItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.client.event.RenderTooltipEvent;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static net.minecraft.util.Mth.clamp;

public abstract class AbstractButterflyGenetics extends Animal {
    private static final int mutationChance = 100; // there is a 1 in x chance for a gene to mutate when generating from parents
    private static final String[] colourCodes = {"0f","2d","4b","69","87","a5","c3","e1"}; // provides hex codes because I don't want to deal with bases
    private static final char[] digits = {'0','1','2','3','4','5','6','7','8','9'}; // things aren't working and I don't know why so we're just doing this >:(

    protected static final EntityDataAccessor<String> GENE_DATA = SynchedEntityData.<String>defineId(AbstractButterflyGenetics.class, EntityDataSerializers.STRING);
    protected static final EntityDataAccessor<Integer> WING_DAMAGE = SynchedEntityData.<Integer>defineId(AbstractButterflyGenetics.class, EntityDataSerializers.INT);

    protected static final EntityDataAccessor<Integer> HEALTH = SynchedEntityData.<Integer>defineId(AbstractButterflyGenetics.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> SPEED = SynchedEntityData.<Integer>defineId(AbstractButterflyGenetics.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> DURABILITY = SynchedEntityData.<Integer>defineId(AbstractButterflyGenetics.class, EntityDataSerializers.INT);

    protected static final EntityDataAccessor<Integer> ANTENNA_SHAPE = SynchedEntityData.<Integer>defineId(AbstractButterflyGenetics.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> BODY_SPOT = SynchedEntityData.<Integer>defineId(AbstractButterflyGenetics.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> FOREWING_SHAPE = SynchedEntityData.<Integer>defineId(AbstractButterflyGenetics.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> HINDWING_SHAPE = SynchedEntityData.<Integer>defineId(AbstractButterflyGenetics.class, EntityDataSerializers.INT);

    protected static final EntityDataAccessor<Boolean> HAS_FLUFF = SynchedEntityData.<Boolean>defineId(AbstractButterflyGenetics.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> IS_FLUFF_DARK = SynchedEntityData.<Boolean>defineId(AbstractButterflyGenetics.class, EntityDataSerializers.BOOLEAN);

    protected static final EntityDataAccessor<String> EYE_COLOUR = SynchedEntityData.<String>defineId(AbstractButterflyGenetics.class, EntityDataSerializers.STRING);
    protected static final EntityDataAccessor<String> BODY_COLOUR_1 = SynchedEntityData.<String>defineId(AbstractButterflyGenetics.class, EntityDataSerializers.STRING);
    protected static final EntityDataAccessor<String> BODY_COLOUR_2 = SynchedEntityData.<String>defineId(AbstractButterflyGenetics.class, EntityDataSerializers.STRING);
    protected static final EntityDataAccessor<String> BODY_SPOT_COLOUR = SynchedEntityData.<String>defineId(AbstractButterflyGenetics.class, EntityDataSerializers.STRING);

    protected static final EntityDataAccessor<String> WING_COLOUR1 = SynchedEntityData.<String>defineId(AbstractButterflyGenetics.class, EntityDataSerializers.STRING);
    protected static final EntityDataAccessor<String> WING_COLOUR2 = SynchedEntityData.<String>defineId(AbstractButterflyGenetics.class, EntityDataSerializers.STRING);
    protected static final EntityDataAccessor<String> WING_COLOUR3 = SynchedEntityData.<String>defineId(AbstractButterflyGenetics.class, EntityDataSerializers.STRING);
    protected static final EntityDataAccessor<String> WING_COLOUR4 = SynchedEntityData.<String>defineId(AbstractButterflyGenetics.class, EntityDataSerializers.STRING);
    protected static final EntityDataAccessor<String> WING_COLOUR5 = SynchedEntityData.<String>defineId(AbstractButterflyGenetics.class, EntityDataSerializers.STRING);

    protected static final EntityDataAccessor<String> FOREWING_BASE_COLOUR = SynchedEntityData.<String>defineId(AbstractButterflyGenetics.class, EntityDataSerializers.STRING);
    protected static final EntityDataAccessor<String> HINDWING_BASE_COLOUR = SynchedEntityData.<String>defineId(AbstractButterflyGenetics.class, EntityDataSerializers.STRING);
    protected static final EntityDataAccessor<String> WING_VEIN_COLOUR = SynchedEntityData.<String>defineId(AbstractButterflyGenetics.class, EntityDataSerializers.STRING);
    protected static final EntityDataAccessor<String> FOREWING_STRIPES = SynchedEntityData.<String>defineId(AbstractButterflyGenetics.class, EntityDataSerializers.STRING);
    protected static final EntityDataAccessor<String> HINDWING_STRIPES = SynchedEntityData.<String>defineId(AbstractButterflyGenetics.class, EntityDataSerializers.STRING);
    protected static final EntityDataAccessor<String> FOREWING_SPOTS = SynchedEntityData.<String>defineId(AbstractButterflyGenetics.class, EntityDataSerializers.STRING);
    protected static final EntityDataAccessor<String> HINDWING_SPOTS = SynchedEntityData.<String>defineId(AbstractButterflyGenetics.class, EntityDataSerializers.STRING);

    protected static final EntityDataAccessor<Integer> FOREWING_EYE = SynchedEntityData.<Integer>defineId(AbstractButterflyGenetics.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> HINDWING_EYE = SynchedEntityData.<Integer>defineId(AbstractButterflyGenetics.class, EntityDataSerializers.INT);

    protected static final EntityDataAccessor<String> WING_STRIPE_COLOURS = SynchedEntityData.<String>defineId(AbstractButterflyGenetics.class, EntityDataSerializers.STRING);
    protected static final EntityDataAccessor<String> EYE_SPOT_COLOURS = SynchedEntityData.<String>defineId(AbstractButterflyGenetics.class, EntityDataSerializers.STRING);
    protected static final EntityDataAccessor<Integer> WING_SPOT_COLOUR = SynchedEntityData.<Integer>defineId(AbstractButterflyGenetics.class, EntityDataSerializers.INT);


    protected String textureName;
    protected LayeredTexture texture;
    protected int textureWingDamage;

    protected AbstractButterflyGenetics(EntityType<? extends Animal> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
    }

    public InteractionResult mobInteract(Player p_27584_, InteractionHand p_27585_) {
        ItemStack itemstack = p_27584_.getItemInHand(p_27585_);
        if (itemstack.is(ModItems.EMPTYBUTTERFLYCHARM.get())) {
            if (!this.isBaby()) {
                return ButterflyCharmItem.butterflyPickup(p_27584_, p_27585_, this).orElse(super.mobInteract(p_27584_, p_27585_));
            }
        }
        return super.mobInteract(p_27584_, p_27585_);
    }

    public void saveToCharmTag(ItemStack p_149187_) {
        CompoundTag compoundtag = p_149187_.getOrCreateTag();
        if (this.hasCustomName()) {
            p_149187_.setHoverName(this.getCustomName());
        }
        if (this.isNoAi()) {
            compoundtag.putBoolean("NoAI", this.isNoAi());
        }
        if (this.isSilent()) {
            compoundtag.putBoolean("Silent", this.isSilent());
        }
        if (this.isNoGravity()) {
            compoundtag.putBoolean("NoGravity", this.isNoGravity());
        }
        if (this.hasGlowingTag()) {
            compoundtag.putBoolean("Glowing", this.hasGlowingTag());
        }
        if (this.isInvulnerable()) {
            compoundtag.putBoolean("Invulnerable", this.isInvulnerable());
        }
        compoundtag.putInt("Age", this.getAge());
        compoundtag.putString(StarlightButterflies.MOD_ID+".genome", this.getGenes());
        compoundtag.putInt(StarlightButterflies.MOD_ID+".wing_damage", this.getWingDamage());
        if(this instanceof LarvaEntity){
            compoundtag.putFloat("Health", 30F);
            compoundtag.putInt("Damage", ModItems.FILLEDBUTTERFLYCHARM.get().getMaxDamage(p_149187_));
        } else {
            compoundtag.putFloat("Health", this.getHealth());
        }
        CompoundTag display = p_149187_.getOrCreateTagElement("display");
        display.putString("1", this.getBodyColour1());
        display.putString("2", this.getForewingColour());
        if(!this.getVeinColour().isEmpty()){
            display.putString("3", this.getVeinColour());
        } else {
            display.putString("3", this.getWingStripeColour(0));
        }
        if(this.getBodySpotShape()!=0){
            display.putString("4", this.getBodySpotColour());
        } else {
            display.putString("4", this.getBodyColour2());
        }
    }

    public void loadFromCharmTag(CompoundTag p_149163_) {
        if (p_149163_.contains("NoAI")) {
            this.setNoAi(p_149163_.getBoolean("NoAI"));
        }
        if (p_149163_.contains("Silent")) {
            this.setSilent(p_149163_.getBoolean("Silent"));
        }
        if (p_149163_.contains("NoGravity")) {
            this.setNoGravity(p_149163_.getBoolean("NoGravity"));
        }
        if (p_149163_.contains("Glowing")) {
            this.setGlowingTag(p_149163_.getBoolean("Glowing"));
        }
        if (p_149163_.contains("Invulnerable")) {
            this.setInvulnerable(p_149163_.getBoolean("Invulnerable"));
        }
        if (p_149163_.contains("Health", 99)) {
            this.setHealth(p_149163_.getFloat("Health"));
        }
        if (p_149163_.contains("Age")) {
            this.setAge(p_149163_.getInt("Age"));
        }
        setWingDamage(p_149163_.getInt(StarlightButterflies.MOD_ID+".wing_damage"));
        this.setGenes(p_149163_.getString(StarlightButterflies.MOD_ID+".genome"));
    }

    public SoundEvent getPickupSound() {
        return SoundEvents.ARMOR_EQUIP_LEATHER;
    }

    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    // ensures that if genes have not been assigned yet, they are randomised BEFORE the entity is spawned
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, @Nullable SpawnGroupData p_21437_, @Nullable CompoundTag p_21438_) {
        this.getGenes();
        return super.finalizeSpawn(p_21434_, p_21435_,  p_21436_, p_21437_, p_21438_);
    }

    public void setGenes(String input) {
        this.entityData.set(GENE_DATA, input);
        this.loadGenes();
        //System.out.println("[setGenes] Called: " + this.entityData.get(GENE_DATA));
    }

    public String getGenes(){
        //System.out.println("[getGenes] Called: " + this.entityData.get(GENE_DATA));
        if (this.entityData.get(GENE_DATA).length() != ButterflyGenome.GENOME.size) {
            this.randomiseGenes();
        }
        return this.entityData.get(GENE_DATA);
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putString(StarlightButterflies.MOD_ID + ".genome", this.getGenes());
        compoundTag.putInt(StarlightButterflies.MOD_ID + ".wing_damage", this.getWingDamage());
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setGenes(compoundTag.getString(StarlightButterflies.MOD_ID + ".genome"));
        setWingDamage(compoundTag.getInt(StarlightButterflies.MOD_ID + ".wing_damage"));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(GENE_DATA, "");
        this.entityData.define(WING_DAMAGE, 0);

        this.entityData.define(HEALTH, 0);
        this.entityData.define(SPEED, 0);
        this.entityData.define(DURABILITY, 0);

        this.entityData.define(ANTENNA_SHAPE, 0);
        this.entityData.define(BODY_SPOT, 0);
        this.entityData.define(FOREWING_SHAPE, 0);
        this.entityData.define(HINDWING_SHAPE, 0);

        this.entityData.define(HAS_FLUFF, true);
        this.entityData.define(IS_FLUFF_DARK, false);

        this.entityData.define(EYE_COLOUR, "");
        this.entityData.define(BODY_COLOUR_1, "");
        this.entityData.define(BODY_COLOUR_2, "");
        this.entityData.define(BODY_SPOT_COLOUR, "");

        this.entityData.define(WING_COLOUR1, "");
        this.entityData.define(WING_COLOUR2, "");
        this.entityData.define(WING_COLOUR3, "");
        this.entityData.define(WING_COLOUR4, "");
        this.entityData.define(WING_COLOUR5, "");

        this.entityData.define(FOREWING_BASE_COLOUR, "");
        this.entityData.define(HINDWING_BASE_COLOUR, "");
        this.entityData.define(WING_VEIN_COLOUR, "");
        this.entityData.define(FOREWING_STRIPES, "");
        this.entityData.define(HINDWING_STRIPES, "");
        this.entityData.define(FOREWING_SPOTS, "");
        this.entityData.define(HINDWING_SPOTS, "");

        this.entityData.define(FOREWING_EYE, 0);
        this.entityData.define(HINDWING_EYE, 0);

        this.entityData.define(WING_STRIPE_COLOURS, "");
        this.entityData.define(EYE_SPOT_COLOURS, "");

        this.entityData.define(WING_SPOT_COLOUR, 0);
    }

    private static int toInt(char input){
        //System.out.println("[LoadGenes]: ToInt called, input: " + input + ", output: " + ArrayUtils.indexOf(digits, input));
        return ArrayUtils.indexOf(digits, input);
    }

    // Dominant Value = true
    private static boolean assessBooleanGene(char[] input) {
        //System.out.println("[LoadGenes]: assessBooleanGene");
        if(input.length == 1){
            return input[0] == '1';
        } else {
            return input[0] == '1' || input[1] == '1';
        }
    }

    // Adds the values. Straightforward :)
    private static int assessAdditiveGene(char[] input){
        //System.out.println("[LoadGenes]: assessAdditiveGene input = "+Arrays.toString(input));
        int total = 0;
        for(int i = 0; i < input.length; i++){
            total += toInt(input[i]);
        }
        return total;
    }

    private static String assessColourGene(char[] input){
        //System.out.println("[LoadGenes]: assessColourGene input: " + new String(input));
        // good enough and keeps all colours within the same pallete
        //System.out.println("[LoadGenes]: assessColourGene output: " + colourCodes[toInt(input[0])]+colourCodes[toInt(input[1])]+colourCodes[toInt(input[2])]);
        return colourCodes[toInt(input[0])]+colourCodes[toInt(input[1])]+colourCodes[toInt(input[2])];
    }

    private static int[][] genGradColours(char[] input, boolean x){
        //System.out.println("[LoadGenes]: genGradColours input: " + new String(input));
        int[][] gradient = new int[3][3];
        int stepSize;
        // assign shared value
        if(input[7] == '0'){
            // use first value
            input[toInt(input[6])+3] = input[toInt(input[6])];
        } else {
            // use second value
            input[toInt(input[6])] = input[toInt(input[6])+3];
        }
        for(int i = 0; i < 3; i++){
            // calculate step size (rounding down)
            stepSize = (toInt(input[i])-toInt(input[i+3]));
            stepSize = stepSize/2;
            // step from each side
            //System.out.println("[genGradColours] output: " + input[i] + " " + (toInt(input[i])-stepSize) + " " + input[i+3]);
            gradient[0][i]=toInt(input[i]);
            gradient[1][i]=toInt(input[i])-stepSize;
            gradient[2][i]=toInt(input[i+3]);
            //System.out.println("[LoadGenes]: gradient: " + Arrays.deepToString(gradient));
        }
        if(x){
            //System.out.println("[LoadGenes]: genGradColours x = true");
            if(input[7] == '0'){
                // uses first value, revert second
                gradient[2][toInt(input[6])]=toInt(input[toInt(input[6])+3]);
            } else {
                // uses second value, revert first
                gradient[0][toInt(input[6])]=toInt(input[toInt(input[6])]);
            }
        }
        return gradient;
    }

    private static int assessAntennaShape(int curl, char length){
        //System.out.println("[LoadGenes]: assessAntennaShape input: curl="+curl+", length="+length);
        if(length == '0'){
            return curl;
        } else {
            return curl+5;
        }
    }

    private static int assessBodySpot(char[] input){
        //System.out.println("[LoadGenes]: assessBodySpot");
        if(input[0] == input[1] && input[0] == input[2]){
            // if all three are the same
            return toInt(input[0])+12;
        } else if (input[0] == input[1] || input[1] == input[2] || input[0] == input[2]){
            // if only two are the same (return is based on the similar ones)
            if(input[0] == input[1] || input[0] == input[2]){
                return toInt(input[0])+2;
            } else {
                return toInt(input[1])+2;
            }
        } else {
            // if all are different
            return 1;
        }
    }

    private static int assessBinaryGene(char[] input){
        //System.out.println("[LoadGenes]: assessBinaryGene, input="+ Arrays.toString(input));
        // for combinations of boolean genes wherein each combination requires a unique output
        int total = 0;
        int place = 1;
        for(int i = 0; i < input.length; i++){
            total += toInt(input[i])*place;
            place *= 2;
        }
        //System.out.println("[LoadGenes]: assessBinaryGene, output="+ total);
        return total;
    }

    private static String tintBodyColour(String colour, boolean darken){
        //System.out.println("[LoadGenes]: tintBodyColour input: " + colour);
        char[] chars = colour.toCharArray();
        char[] tempchars = new char[2];
        String[] rgb = new String[3];
        int result;
        for(int i = 0; i < 3; i++){
            tempchars[0] = chars[i*2];
            tempchars[1] = chars[(i*2)+1];
            rgb[i] = new String(tempchars);
            //System.out.println("rgb["+i+"]: "+rgb[i]);
        }
        tempchars = new char[3];
        if(darken){
            for(int i = 0; i < 3; i++){
                result = ArrayUtils.indexOf(colourCodes, rgb[i])-2;
                if(result<1){result=1;} // remain in range please thanks, also remain closer to the middle so that eyes have contrast and such
                tempchars[i] =  Character.forDigit(result, 10);
                //System.out.println("tempchars["+i+"]: "+tempchars[i]);
            }
        } else {
            for(int i = 0; i < 3; i++){
                result = ArrayUtils.indexOf(colourCodes, rgb[i])+2;
                if(result>6){result=6;} // remain in range please thanks, also remain closer to the middle so that eyes have contrast and such
                tempchars[i] =  Character.forDigit(result, 10);
                //System.out.println("tempchars["+i+"]: "+tempchars[i]);
            }
        }
        return assessColourGene(tempchars);
    }

    private static int limitColour(int input, int max){
        for(int i = 0; i < max; i++){
            if(input > max){
                input -= max;
            }
        }
        return input;
    }

    private static String limitBodyColour(int input, int max, String[] base, String[] wings){
        if(input > max){
            return base[5];
        } else {
            return wings[input];
        }
    }

    // randomises and assigns a new value
    public void randomiseGenes(){
        //System.out.println("[LoadGenes]: randomiseGenes() called");
        this.setGenes(randomGeneString());
    }

    // randomises and returns a new value
    public char[] randomGenes(){
        int[] output = new int[ButterflyGenome.GENOME.size];
        int index = 0;
        for(int i = 0; i < ButterflyGenome.GENOME.values.length; i++){
            for(int j = 0; j < ButterflyGenome.GENOME.values[i].getCopies(); j++){
                output[index] =  random.nextInt(ButterflyGenome.GENOME.values[i].getRange());
                index++;
            }
        }
        return intArrToCharArr(output);
    }

    // As above but returns a string
    public String randomGeneString(){
        return new String(randomGenes());
    }

    // randomises and returns a value based on the input
    public String generatedGenes(String parent1, String parent2){
        char[] genome1;
        char[] genome2;
        char[] smallGenome1;
        char[] smallGenome2;
        char[] smallOutput;
        // transform parent strings into character arrays, randomising if null or invalid
        if(parent1 == null || parent1.length() != ButterflyGenome.GENOME.size){ genome1 = randomGenes();
        } else { genome1 = parent1.toCharArray(); }
        if(parent2 == null || parent2.length() != ButterflyGenome.GENOME.size){ genome2 = randomGenes();
        } else { genome2 = parent2.toCharArray(); }

        char[] output = new char[ButterflyGenome.GENOME.size];

        int index = 0;
        for(int i = 0; i < ButterflyGenome.GENOME.values.length; i++){

            smallGenome1 = new char[ButterflyGenome.GENOME.values[i].getCopies()];
            smallGenome2 = new char[smallGenome1.length];


                // groups like genes so that our punnet square can work properly
                for(int j = 0; j < ButterflyGenome.GENOME.values[i].getCopies(); j++){
                    // assign values to small genomes
                    smallGenome1[j] = genome1[index+j];
                    smallGenome2[j] = genome2[index+j];
                }
                smallOutput = combine(smallGenome1, smallGenome2);
                for(int j = 0; j < ButterflyGenome.GENOME.values[i].getCopies(); j++){
                    // assign to output
                    if(random.nextInt(mutationChance) != 0){
                        output[index] = smallOutput[j];
                    } else {
                        // mutate
                        output[index] = Character.forDigit(random.nextInt(ButterflyGenome.GENOME.values[i].getRange()), 10);
                    }

                    index++;
                }
        }

        return new String(output);
    }

    // punnet square thing
    private static char[] combine(char[] gene1, char[] gene2) {
        char[] output = new char[gene1.length];
        Random r = new Random();
        gene1 = shuffle(gene1);
        gene2 = shuffle(gene2);
        if(gene1.length == 2){
            output[0] = gene1[0];
            output[1] = gene2[0];
        } else {
            for(int i = 0; i < gene1.length; i++){
                if(r.nextBoolean()) {
                    output[i] = gene1[i];
                } else {
                    output[i] = gene2[i];
                }
            }
        }
        return output;
    }

    // randomise the order of the input array
    private static char[] shuffle(char[] input){
        char[] output = new char[input.length];
        Random r = new Random();
        ArrayList<Character> list = new ArrayList<>();
        int x;
        for(int i = 0; i < input.length; i++){
            list.add(input[i]);
        }
        for(int i = 0; i < input.length; i++){
            x = r.nextInt(list.size());
            output[i] = list.get(x);
            list.remove(x);
        }
        return output;
    }

    private static char[] intArrToCharArr(int[] input){
        //System.out.println("[loadGenes][intArrToCharArr] input: " + Arrays.toString(input));
        char[] output = new char[input.length];
        for(int i = 0; i < input.length; i++){
            output[i] = Character.forDigit(input[i], 10);
        }
        //System.out.println("[loadGenes][intArrToCharArr] output: " + Arrays.toString(output));
        return output;
    }

    private static char[][] intArrToCharArr(int[][] input){
        //System.out.println("[loadGenes][intArrToCharArr] input: " + Arrays.deepToString(input));
        char[][] output = new char[input.length][input[0].length];
        for(int i = 0; i < input.length; i++){
            output[i] = intArrToCharArr(input[i]);
        }
        //System.out.println("[loadGenes][intArrToCharArr] output: " + Arrays.deepToString(output));
        return output;
    }

    // uses GENE_DATA to set all the other things appropriately
    private void loadGenes() {
        char[] genome = this.getGenes().toCharArray();
        char[] gene;
        char[] clearable = new char[1];
        char[] clearable2 = new char[1];
        int[] intList = new int[2];
        char[][] gradient;
        String[] colours = new String[6];
        String[] wingPalette = new String[5];
        colours[3] = assessColourGene(new char[]{'0','0','0'}); // black
        colours[4] = assessColourGene(new char[]{'7','7','7'}); // white
        boolean[] boolbuffer = new boolean[3];
        for(int i = 0; i < ButterflyGenome.GENOME.values.length; ++i) {
            gene = new char[ButterflyGenome.GENOME.values[i].getCopies()];
            for(int j = 0; j < ButterflyGenome.GENOME.values[i].getCopies(); j++){
                gene[j] = genome[ButterflyGenome.GENOME.values[i].getIndex()+j];
            }
            switch (ButterflyGenome.GENOME.values[i]) {
                // functional
                case HEALTH:
                    //System.out.println("[LoadGenes]: Section - functional");
                    this.entityData.set(HEALTH, assessAdditiveGene(gene));
                    break;
                case SPEED:
                    this.entityData.set(SPEED, assessAdditiveGene(gene));
                    break;
                case DURABILITY:
                    this.entityData.set(DURABILITY, assessAdditiveGene(gene));
                    break;
                // head
                case CURL:
                    //System.out.println("[LoadGenes]: Section - head");
                    clearable = gene;
                    break;
                case LENGTH:
                    this.entityData.set(ANTENNA_SHAPE, assessAntennaShape(assessAdditiveGene(clearable), gene[0]));
                    break;
                // thorax
                case FLUFF:
                    //System.out.println("[LoadGenes]: Section - thorax");
                    this.entityData.set(HAS_FLUFF, assessBooleanGene(gene));
                    break;
                case FLUFFSHADE:
                    this.entityData.set(IS_FLUFF_DARK, !assessBooleanGene(gene));
                    if (!assessBooleanGene(gene)) {
                        colours[5] = colours[3];
                    } else {
                        colours[5] = colours[4];
                    }
                    break;
                case BODYSPOT:
                    boolbuffer[1] = assessBooleanGene(gene);
                    break;
                case SPOTSHAPE:
                    if (boolbuffer[1]) {
                        this.entityData.set(BODY_SPOT, assessBodySpot(gene));
                    } else {
                        this.entityData.set(BODY_SPOT, 0);
                    }
                    break;
                // wing shapes
                case DECOR:
                    clearable = new char[3];
                    if (assessBooleanGene(gene)) {
                        clearable[0] = '1';
                    } else {
                        clearable[0] = '0';
                    }
                    boolbuffer[0] = !assessBooleanGene(gene);
                    break;
                case FOREROUND:
                    //System.out.println("[LoadGenes]: Section - wing shapes");
                    if (!assessBooleanGene(gene)) {
                        clearable[1] = '1';
                    } else {
                        clearable[1] = '0';
                    }
                    break;
                case FORESIZE:
                    if (assessBooleanGene(gene)) {
                        clearable[2] = '1';
                    } else {
                        clearable[2] = '0';
                    }
                    this.entityData.set(FOREWING_SHAPE, assessBinaryGene(clearable));
                    break;
                case HINDTAIL:
                    boolbuffer[1] = assessBooleanGene(gene);
                    break;
                case HINDSIZE:
                    intList[0] = assessAdditiveGene(gene);
                    if (!(boolbuffer[1])) {
                        // has swallowtail
                        if (boolbuffer[0]) {
                            // No Decor
                            this.entityData.set(HINDWING_SHAPE, intList[0] + 2);
                        } else {
                            // Has Decor
                            this.entityData.set(HINDWING_SHAPE, intList[0] + 5);
                        }
                    } else {
                        // does not have swallowtail
                        if (boolbuffer[0]) {
                            // No Decor
                            this.entityData.set(HINDWING_SHAPE, 0);
                        } else {
                            // Has Decor
                            this.entityData.set(HINDWING_SHAPE, 1);
                        }
                    }
                    break;
                // wing patterns (stripes)
                case STRIPEAPRESENCE:
                    intList = new int[3];
                    boolbuffer = new boolean[8];
                    boolbuffer[0] = assessBooleanGene(gene);
                    break;
                case STRIPELIMIT1:
                    boolbuffer[1] = !assessBooleanGene(gene);
                    break;
                case STRIPELIMIT2:
                    boolbuffer[2] = !assessBooleanGene(gene);
                    break;
                case STRIPELIMIT3:
                    boolbuffer[3] = !assessBooleanGene(gene);
                    break;
                case STRIPELIMIT4:
                    boolbuffer[4] = !assessBooleanGene(gene);
                    clearable = new char[4];
                    for (int x = 1; x < clearable.length; x++) {
                        if (boolbuffer[x + 1]) {
                            clearable[x] = '1';
                        } else {
                            clearable[x] = '0';
                        }
                    }
                    intList[0] = assessAdditiveGene(clearable);
                    break;
                case STRIPESIMILAR:
                    boolbuffer[2] = assessBooleanGene(gene);
                    break;
                case STRIPESWITCH1:
                    clearable = new char[8];
                    clearable2 = new char[8];
                    clearable[1] = gene[0];
                    break;
                case STRIPESWITCH2:
                    if (intList[0] == 1) {
                        clearable[2] = '1';
                    } else {
                        clearable[2] = gene[0];
                    }

                    break;
                case STRIPESWITCH3:
                    clearable[3] = gene[0];
                    break;
                case STRIPESWITCH4:
                    clearable[4] = gene[0];
                    break;
                case STRIPESWITCH5:
                    clearable[5] = gene[0];
                    boolbuffer[3] = false; // whether we've tampered with the stripe limit
                    intList[2] = intList[0];
                    if(boolbuffer[0]) { // if stripes present
                        // intlist[1] is our running total
                        // boolbuffer[1] is whether there is a stripe or not
                        boolbuffer[1] = intList[0] != 0;
                        if (!boolbuffer[1] || assessAdditiveGene(clearable)==0) {
                            // if limit = 0 it's actually 2 now
                            intList[0] = 2;
                            boolbuffer[3] = true;
                        }
                        if (intList[0] == -1) {
                            // if limit = -1 it's actually 1 now
                            intList[0] = 1;
                            boolbuffer[3] = true;
                        }
                        // whether the hindwing has the same stripes as the forewing
                        boolbuffer[2] = boolbuffer[2] || intList[0] == 1 || boolbuffer[3];

                        for (int x = 0; x < 6; x++) {
                            // if intlist[1] >= intlist[0], boolbuffer[0] = false
                            if (intList[1] >= intList[0]) {
                                boolbuffer[0] = false;
                            }
                            if (clearable[x] == '1' && boolbuffer[0]) {
                                // if gene[0] == 1 and boolbuffer[0], reverse boolbuffer[1], intlist[1]++
                                boolbuffer[1] = !boolbuffer[1];
                                intList[1]++;
                            }
                            // set clearable depending on boolbuffer[1]
                            if (boolbuffer[1]) {
                                clearable[x] = '1';
                            } else {
                                clearable[x] = '0';
                            }

                            if (boolbuffer[2] || intList[2] == 0) {
                                // match forewing
                                clearable2[x] = clearable[x];
                            } else {
                                // reverse forewing
                                if (!boolbuffer[1]) {
                                    clearable2[x] = '1';
                                } else {
                                    clearable2[x] = '0';
                                }
                                intList[2]--;
                            }

                        }
                    } else { // if stripes not present
                        Arrays.fill(clearable, '0');
                        Arrays.fill(clearable2, '0');
                    }
                    break;
                case STRIPEBPRESENCE:
                    boolbuffer[0] = gene[0] == '1';
                    break;
                case STRIPEBWEIGHT:
                    clearable[6] = '0';
                    if (boolbuffer[0]) {
                        clearable[6] = '1';
                        clearable[7] = gene[0];
                    }
                    clearable2[6] = clearable[6];
                    clearable2[7] = clearable[7];
                    this.entityData.set(FOREWING_STRIPES, new String(clearable));
                    this.entityData.set(HINDWING_STRIPES, new String(clearable2));
                    break;
                // wing patterns (first spots)
                case HINDSPOTSMODIFIER:
                    clearable = new char[5];
                    clearable2 = new char[5];
                    intList[0] = assessAdditiveGene(gene) - 1;
                    break;
                case WINGSPOTS0:
                    intList[1] = assessAdditiveGene(gene);
                    clearable[0] = Character.forDigit(intList[1], 10);
                    clearable2[0] = clearable[0];
                    break;
                case WINGSPOTS1:
                    intList[1] = assessAdditiveGene(gene);
                    clearable[1] = Character.forDigit(intList[1], 10);
                    clearable2[1] = Character.forDigit(clamp(intList[1] + intList[0], 0, 2), 10);
                    intList[2] += intList[1];
                    break;
                case WINGSPOTS2:
                    intList[1] = assessAdditiveGene(gene);
                    clearable[2] = Character.forDigit(intList[1], 10);
                    clearable2[2] = Character.forDigit(clamp(intList[1] + intList[0], 0, 3), 10);
                    intList[2] += intList[1];
                    break;
                case WINGSPOTS3:
                    intList[1] = assessAdditiveGene(gene);
                    clearable[3] = Character.forDigit(intList[1], 10);
                    clearable2[3] = Character.forDigit(clamp(intList[1] + intList[0], 0, 2), 10);
                    intList[2] += intList[1];
                    break;
                case WINGSPOTS4:
                    // base presence also on the number of spots already there
                    if(intList[2]+intList[0]>4){ // max 10+1
                        intList[1] = assessAdditiveGene(gene);
                        clearable[4] = Character.forDigit(intList[1], 10);
                    } else {
                        clearable[4] = '0';
                    }
                    clearable2[4] = clearable[0];
                    break;
                case SPOTLIMIT:
                    if(assessBooleanGene(gene)){
                        clearable[2] = '0';
                        clearable[3] = '0';
                        clearable[4] = '0';
                        clearable2[2] = '0';
                        clearable2[3] = '0';
                        clearable2[4] = '0';
                    }
                    break;
                case SPOTPRESENCE:
                    if(assessBooleanGene(gene)){
                        this.entityData.set(FOREWING_SPOTS, new String(clearable));
                        this.entityData.set(HINDWING_SPOTS, new String(clearable2));
                    } else {
                        this.entityData.set(FOREWING_SPOTS, "00000");
                        this.entityData.set(HINDWING_SPOTS, "00000");
                    }
                    break;
                // wing patterns (second spots)
                case EYESPOTLAYERS:
                    intList[0] = assessAdditiveGene(gene)+1;
                    break;
                case FOREEYEPRESENCE:
                    if(!assessBooleanGene(gene)){
                        this.entityData.set(FOREWING_EYE, intList[0]);
                    }
                    break;
                case HINDEYEPRESENCE:
                    if(!assessBooleanGene(gene)){
                        this.entityData.set(HINDWING_EYE, intList[0]);
                    }
                    break;
                // colour palette
                case WINGSHADE1:
                    if(!assessBooleanGene(gene)){
                        // towards fluff
                        if(isFluffDark()){
                            wingPalette[0] = colours[3];
                        } else {
                            wingPalette[0] = colours[4];
                        }
                    } else {
                        // away from fluff
                        if(!isFluffDark()){
                            wingPalette[0] = colours[3];
                        } else {
                            wingPalette[0] = colours[4];
                        }
                    }
                    break;
                case RED1:
                    //System.out.println("[LoadGenes]: Section - colour palette");
                    clearable = new char[8];
                    clearable[0] = gene[0];
                    break;
                case GREEN1:
                    clearable[1] = gene[0];
                    break;
                case BLUE1:
                    clearable[2] = gene[0];
                    break;
                case RED2:
                    clearable[3] = gene[0];
                    break;
                case GREEN2:
                    clearable[4] = gene[0];
                    break;
                case BLUE2:
                    clearable[5] = gene[0];
                    break;
                case GRADSHARED:
                    clearable[6] = gene[0];
                    break;
                case SHAREACTIVE:
                    boolbuffer[0] = !assessBooleanGene(gene);
                    break;
                case GRADCHOICE:
                    clearable[7] = gene[0];
                    gradient = intArrToCharArr(genGradColours(clearable, boolbuffer[0]));
                    colours[0] = assessColourGene(gradient[0]);
                    colours[1] = assessColourGene(gradient[1]);
                    colours[2] = assessColourGene(gradient[2]);
                    break;
                case COLOURLIMIT1:
                    clearable = new char[3];
                    if(!assessBooleanGene(gene)){
                        clearable[0] = '0';
                    } else {
                        clearable[0] = '1';
                    }
                    break;
                case COLOURLIMIT2:
                    if(assessBooleanGene(gene)){
                        clearable[0] = '0';
                    } else {
                        clearable[0] = '1';
                    }
                    break;
                case COLOURLIMIT3:
                    if(assessBooleanGene(gene)){
                        clearable[0] = '0';
                    } else {
                        clearable[0] = '1';
                    }
                    // obtain our colour limit
                    intList[0] = assessAdditiveGene(clearable)+1;
                    break;
                case COLOURSELECT1:
                    intList = new int[5];
                    if(wingPalette[0].equals(colours[3])){
                        intList[0] = 3;
                    } else {
                        intList[0] = 4;
                    }
                    intList[1] = toInt(gene[0])+1;
                    wingPalette[1] = colours[intList[1]];
                    break;
                case COLOURSELECT2:
                    intList[2] = toInt(gene[0]);
                    while(intList[2] == intList[0] || intList[2] == intList[1]){
                        intList[2]++;
                        if(intList[2] > 4){
                            intList[2] = 0;
                        }
                    }
                    wingPalette[2] = colours[intList[2]];
                    break;
                case COLOURSELECT3:
                    if(intList[1] != 3 && intList[1] != 4 && intList[2] != 3 && intList[2] != 4){
                        if(intList[0] == 3){
                            intList[3] = 4;
                        } else {
                            intList[3] = 3;
                        }
                    } else {
                        intList[3] = toInt(gene[0]);
                        for(int aa = 0; aa<4; aa++){
                            if(intList[3] == intList[0] || intList[3] == intList[1] || intList[3] == intList[2]){
                                intList[3]++;
                                if(intList[3] > 4){
                                    intList[3] = 0;
                                }
                            }
                        }
                    }
                    intList[4] = 0;
                    for(int aa = 0; aa<4; aa++){
                        if(intList[4] == intList[0] || intList[4] == intList[1] || intList[4] == intList[2] || intList[4] == intList[3]){
                            intList[4]++;
                            if(intList[4] > 4){
                                intList[4] = 0;
                            }
                        }
                    }
                    wingPalette[3] = colours[intList[3]];
                    wingPalette[4] = colours[intList[4]];
                    // save all this
                    this.entityData.set(WING_COLOUR1, wingPalette[0]);
                    this.entityData.set(WING_COLOUR2, wingPalette[1]);
                    this.entityData.set(WING_COLOUR3, wingPalette[2]);
                    this.entityData.set(WING_COLOUR4, wingPalette[3]);
                    this.entityData.set(WING_COLOUR5, wingPalette[4]);
                    break;
                // colour assignment (body)
                case BODYCOLOUR1:
                    //System.out.println("[LoadGenes]: Section - colour assignment");
                    clearable = new char[2];
                    clearable[0] = gene[0];
                    break;
                case BODYCOLOUR2:
                    clearable[1] = gene[0];
                    break;
                case BODYTINT:
                    //System.out.println("[LoadGenes]: BODYTINT");
                    if(assessBooleanGene(gene)){
                        // towards fluff
                        this.entityData.set(BODY_COLOUR_1, tintBodyColour(limitBodyColour(toInt(clearable[0]),intList[0], colours, wingPalette),isFluffDark()));
                        this.entityData.set(BODY_COLOUR_2, tintBodyColour(limitBodyColour(toInt(clearable[1]),intList[0], colours, wingPalette),isFluffDark()));
                    } else {
                        // away from fluff
                        this.entityData.set(BODY_COLOUR_1, tintBodyColour(limitBodyColour(toInt(clearable[0]),intList[0], colours, wingPalette),!isFluffDark()));
                        this.entityData.set(BODY_COLOUR_2, tintBodyColour(limitBodyColour(toInt(clearable[1]),intList[0], colours, wingPalette),!isFluffDark()));
                    }
                    break;
                case COLOUREDEYES:
                    boolbuffer[0] = !assessBooleanGene(gene);
                    break;
                case EYECOLOUR:
                    if(boolbuffer[0]){
                        this.entityData.set(EYE_COLOUR, wingPalette[limitColour(toInt(gene[0])+1,intList[0])]);
                    } else {
                        this.entityData.set(EYE_COLOUR, colours[3]);
                    }
                    clearable[0] = gene[0];
                    break;
                case BODYSPOTCOLOUR:
                    if(!getBodyColour1().equals(colours[toInt(gene[0])])){
                        this.entityData.set(BODY_SPOT_COLOUR, wingPalette[limitColour(toInt(gene[0])+1,intList[0])]);
                    } else {
                        this.entityData.set(BODY_SPOT_COLOUR, limitBodyColour(toInt(clearable[0]),intList[0], colours, wingPalette));
                    }
                    break;
                // wing colour assignment
                case FOREWINGBASE1:
                    clearable = new char[3];
                    if(!assessBooleanGene(gene)){
                        clearable[0] = '0';
                    } else {
                        clearable[0] = '1';
                    }
                    break;
                case FOREWINGBASE2:
                    if(!assessBooleanGene(gene)){
                        clearable[1] = '0';
                    } else {
                        clearable[1] = '1';
                    }
                    break;
                case FOREWINGBASE3:
                    if(!assessBooleanGene(gene)){
                        clearable[2] = '0';
                    } else {
                        clearable[2] = '1';
                    }
                    this.entityData.set(FOREWING_BASE_COLOUR, wingPalette[limitColour(assessAdditiveGene(clearable)+1,intList[0])]);
                    break;
                case HINDWINGBASE1:
                    if(!assessBooleanGene(gene)){
                        clearable[0] = '0';
                    } else {
                        clearable[0] = '1';
                    }
                    break;
                case HINDWINGBASE2:
                    if(!assessBooleanGene(gene)){
                        clearable[1] = '0';
                    } else {
                        clearable[1] = '1';
                    }
                    break;
                case HINDWINGBASE3:
                    if(!assessBooleanGene(gene)){
                        clearable[2] = '0';
                    } else {
                        clearable[2] = '1';
                    }
                    this.entityData.set(HINDWING_BASE_COLOUR, wingPalette[limitColour(assessAdditiveGene(clearable)+1,intList[0])]);
                    break;
                case BASESIMILAR:
                    boolbuffer[0] = assessBooleanGene(gene);
                    break;
                case BASECHOICE:
                    if(boolbuffer[0]){
                        if(assessBooleanGene(gene)){
                            this.entityData.set(FOREWING_BASE_COLOUR, this.entityData.get(HINDWING_BASE_COLOUR));
                        } else {
                            this.entityData.set(HINDWING_BASE_COLOUR, this.entityData.get(FOREWING_BASE_COLOUR));
                        }
                    }
                    break;
                case VEINPRESENCE:
                    boolbuffer[2] = assessBooleanGene(gene);
                    break;
                case VEINCOLOUR1:
                    if(boolbuffer[2]){
                        boolbuffer[1] = assessBooleanGene(gene);
                    }
                    break;
                case VEINCOLOUR2:
                    intList[2] = limitColour(toInt(gene[0])+1,intList[0]);
                    if(boolbuffer[2]){
                        if(boolbuffer[1]){
                            this.entityData.set(WING_VEIN_COLOUR, wingPalette[0]);
                        } else {
                            this.entityData.set(WING_VEIN_COLOUR, wingPalette[intList[2]]);
                        }
                    }
                    break;
                case STRIPECOLOUR:
                    clearable = new char[8];
                    boolbuffer[1] = true;
                    if(assessBooleanGene(gene)){
                        intList[1] = 0;
                    } else {
                        intList[1] = intList[2];
                    }

                    for(int x = 0; x < clearable.length; x++){
                        if(wingPalette[limitColour(intList[1],intList[0])].equals(this.getForewingColour())){
                            intList[1]++;
                        }
                        clearable[x] = Character.forDigit(limitColour(intList[1],intList[0]), 10);
                    }
                    break;
                case STRIPECOLOUR2PRESENCE:
                    boolbuffer[1] = !assessBooleanGene(gene);
                    break;
                case STRIPECOLOUR2:
                    intList[1] = assessAdditiveGene(gene);
                    break;
                case STRIPECOLOUR2PLACES:
                    if(boolbuffer[1]){

                        for(int x = 0; x < gene.length; x++){
                            if(assessBooleanGene(new char[]{gene[x]})){
                                clearable[x] = Character.forDigit(limitColour(intList[1],intList[0]), 10);
                            }
                        }

                    }
                    this.entityData.set(WING_STRIPE_COLOURS, new String(clearable));
                    break;
                case EYESPOTCOLOUR:
                    clearable = new char[3];
                    for(int x = 0; x < clearable.length; x++){
                        clearable[x] = Character.forDigit(limitColour(toInt(new String(gene).charAt(x)),intList[0]), 10);
                    }
                    this.entityData.set(EYE_SPOT_COLOURS, new String(clearable));
                    break;
                case WINGSPOTCOLOUR:
                    this.entityData.set(WING_SPOT_COLOUR, assessAdditiveGene(gene));
                    break;
            }
        }
        if(this instanceof ButterflyEntity b){
            b.setAttributes();
        }
        refreshTextureName();
    }

    public String getTextureName(){
        if(this.texture == null || this.textureWingDamage != this.getWingDamage()){
            refreshTextureName();
        }
        return this.textureName;
    }

    public LayeredTexture getTexture(){
        if(this.texture == null || this.textureWingDamage != this.getWingDamage()){
            refreshTextureName();
        }
        return this.texture;
    }

    public void refreshTextureName(){
        this.texture = new LayeredTexture(this);
        this.textureName = texture.getName();
        this.textureWingDamage = getWingDamage();
    }

    public void setWingDamage(int input){
        this.entityData.set(WING_DAMAGE, input);
        refreshTextureName();
    }
    public int getWingDamage(){
        return this.entityData.get(WING_DAMAGE);
    }

    public double getGeneticHealth(){return this.entityData.get(HEALTH);}
    public double getFlightSpeed(){return this.entityData.get(SPEED);}
    public int getWingDurability(){return this.entityData.get(DURABILITY);}

    public int getAntennaShape(){
            return this.entityData.get(ANTENNA_SHAPE);
    }
    public int getBodySpotShape(){return this.entityData.get(BODY_SPOT);}
    public int getForewingShape(){return this.entityData.get(FOREWING_SHAPE);}
    public int getHindwingShape(){return this.entityData.get(HINDWING_SHAPE);}

    public boolean hasFluff(){return this.entityData.get(HAS_FLUFF);}
    public boolean isFluffDark(){return this.entityData.get(IS_FLUFF_DARK);}

    public String getEyeColour(){return this.entityData.get(EYE_COLOUR);}
    public String getBodyColour1(){return this.entityData.get(BODY_COLOUR_1);}
    public String getBodyColour2(){return this.entityData.get(BODY_COLOUR_2);}
    public String getBodySpotColour(){return this.entityData.get(BODY_SPOT_COLOUR);}
    public String getForewingColour(){return this.entityData.get(FOREWING_BASE_COLOUR);}
    public String getHindwingColour(){return this.entityData.get(HINDWING_BASE_COLOUR);}
    public String getVeinColour(){return this.entityData.get(WING_VEIN_COLOUR);}

    public boolean[] getForewingStripes(){
        // store as a string of 10010101010??? probably
        boolean[] output = new boolean[8];
        String stripes = this.entityData.get(FOREWING_STRIPES);
        for(int i = 0; i < stripes.length(); i++){
            output[i] = stripes.charAt(i) == '1';
        }
        return output;
    }
    public boolean[] getHindwingStripes(){
        boolean[] output = new boolean[8];
        String stripes = this.entityData.get(HINDWING_STRIPES);
        for(int i = 0; i < stripes.length(); i++){
            output[i] = stripes.charAt(i) == '1';
        }
        return output;
    }

    private String translateColour(char colour){
        switch (colour){
            case '0':
                return this.entityData.get(WING_COLOUR1);
            case '1':
                return this.entityData.get(WING_COLOUR2);
            case '2':
                return this.entityData.get(WING_COLOUR3);
            case '3':
                return this.entityData.get(WING_COLOUR4);
            case '4':
                return this.entityData.get(WING_COLOUR5);
        }
        return "";
    }

    public String getWingStripeColour(int x){
        // store as a string of ints + entity data for each wing colour
        return translateColour(this.entityData.get(WING_STRIPE_COLOURS).charAt(x));
    }

    public String getEyeSpotColour(int x){
        // store as a string of ints + entity data for each wing colour
        return translateColour(this.entityData.get(EYE_SPOT_COLOURS).charAt(x));
    }

    public String getWingSpotColour(){
        return translateColour(this.entityData.get(EYE_SPOT_COLOURS).charAt(this.entityData.get(WING_SPOT_COLOUR)));
    }

    public int[] getForewingSpots(){
        // store same as stripes
        int[] output = new int[5];
        String spots = this.entityData.get(FOREWING_SPOTS);
        for(int i = 0; i < spots.length(); i++){
            output[i] = toInt(spots.charAt(i));
        }
        return output;
    }
    public int[] getHindwingSpots(){
        int[] output = new int[5];
        String spots = this.entityData.get(HINDWING_SPOTS);
        for(int i = 0; i < spots.length(); i++){
            output[i] = toInt(spots.charAt(i));
        }
        return output;
    }
    public int[] getEyeSpots(){
        int[] output = new int[2];
        // 2x 0-3 = how many layers to have
        output[0] = this.entityData.get(FOREWING_EYE);
        output[1] = this.entityData.get(HINDWING_EYE);
        return output;
    }

}
