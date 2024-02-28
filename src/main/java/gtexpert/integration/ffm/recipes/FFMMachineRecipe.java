package gtexpert.integration.ffm.recipes;

import gregtech.api.GTValues;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.metatileentities.MetaTileEntities;
import gtexpert.api.GTEValues;
import gtexpert.core.GTUtil;
import net.minecraft.item.ItemStack;

import static gregtech.integration.IntegrationUtil.getModItem;

public class FFMMachineRecipe {

    /*Target Module
    * climatology
    * database
    * energy
    * factory
    * sorting
    * worktable
    * */

    public static void init() {}

    public static void remove() {}

    //Module: climatology
    public static class climatology {}

    //Module: database
    public static class database {}

    //Module: energy
    public static class energy {}

    //Module: factory
    public static class factory {

        public static void register() {

        }
        public static void remove() {

        }

        public static void blocks() {
            int MachineVoltage = GTValues.MV; //ToDO: set by config
            ItemStack Sturdy = getModItem(GTEValues.MODID_FFM, "sturdy_machine", 0);
            //Analyzer
            ModHandler.addShapedRecipe(true, "analyzer", getModItem(GTEValues.MODID_FFM, "analyzer", 0),
                    "SAS", "DHD", "CMC",
                    'S', GTUtil.sensor(MachineVoltage),
                    'A', getModItem(GTEValues.MODID_FFM, "portable_alyzer", 0),
                    'H', Sturdy,
                    'D', MetaTileEntities.BRONZE_DRUM.getStackForm(),
                    'C', GTUtil.oreDictionaryCircuit(MachineVoltage));

            //Bottler
            //ModHandler.addShapedRecipe(true, "bottler", getModItem(GTEValues.MODID_FFM, "bottler", 0),
                    //"");
            //Carpenter
            //Centrifuge
            //Fermenter
            //Moister
            //Squeezer
            //Still

            //Analyzer
            //Bottler
            //Carpenter
            //Centrifuge
            //Fermenter
            //Moister
            //Squeezer
            //Still
        }
    }

    //Module: sorting
    public static class sorting {}

    //Module: worktable
    public static class worktable {}
}
