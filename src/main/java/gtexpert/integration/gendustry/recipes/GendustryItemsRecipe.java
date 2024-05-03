package gtexpert.integration.gendustry.recipes;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;
import gtexpert.api.util.Mods;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.ore.OrePrefix.*;

public class GendustryItemsRecipe {

    public static void init() {
        //Mutagen Tank
        ModHandler.removeRecipeByName(Mods.Gendustry.getResource("recipe4"));
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaTileEntities.STAINLESS_STEEL_DRUM)
                .input(plateDouble, Materials.RhodiumPlatedPalladium)
                .fluidInputs(Materials.Plutonium239.getFluid(576))
                .outputs(Mods.Gendustry.getItem("mutagen_tank"))
                .EUt(VA[EV]).duration(300).buildAndRegister();

        //Bee Receptacle
        ModHandler.removeRecipeByName("recipe5");

        //Power Module
        ModHandler.removeRecipeByName(Mods.Gendustry.getResource("recipe6"));

        //Genetics Processor
        ModHandler.removeRecipeByName(Mods.Gendustry.getResource("recipe10"));
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, Materials.Diamond, 4)
                .input(plate, Materials.EnderPearl)
                .input("circuitEv", 2)
                .outputs(Mods.Gendustry.getItem("genetics_processor"))
                .EUt(VA[EV]).duration(400).buildAndRegister();

        //Environmental Processor
        ModHandler.removeRecipeByName(Mods.Gendustry.getResource("recipe11"));
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, Materials.Diamond, 4)
                .input(plate, Materials.Lapis, 4)
                .input("circuitEv", 2)
                .fluidInputs(FluidRegistry.getFluidStack("liquiddna", 500))
                .outputs(Mods.Gendustry.getItem("env_processor"))
                .EUt(VA[EV]).duration(400).buildAndRegister();

        //Upgrade Frame


        //Climate Control Module
        ModHandler.removeRecipeByName(Mods.Gendustry.getResource("recipe8"));
    }

    public static void initMode() {}

    public static void normal() {
        //Bee Receptacle
        ModHandler.addShapedRecipe("bee_receptacle", Mods.Gendustry.getItem("bee_receptacle"),
                "SCS", "PLP", "SdS",
                'S', new UnificationEntry(screw, Materials.StainlessSteel),
                'C', "beeComb",
                'P', new UnificationEntry(plate, Materials.Titanium),
                'L', new UnificationEntry(stickLong, Materials.Platinum));
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(screw, Materials.StainlessSteel, 4)
                .input("beeComb")
                .input(plate, Materials.Titanium, 2)
                .input(stickLong, Materials.Platinum)
                .outputs(Mods.Gendustry.getItem("bee_receptacle"))
                .circuitMeta(10)
                .EUt(VA[EV]).duration(200).buildAndRegister();

        //Power Module
        ModHandler.addShapedRecipe("power_module", Mods.Gendustry.getItem("power_module"),
                "WIW", "PCP", "GMG",
                'W', new UnificationEntry(cableGtSingle, Materials.Aluminium),
                'I', MetaItems.POWER_INTEGRATED_CIRCUIT,
                'P', new UnificationEntry(plate, Materials.Platinum),
                'C', "circuitEv",
                'G', new UnificationEntry(gearSmall, Materials.Titanium),
                'M', MetaItems.ELECTRIC_MOTOR_EV);

        //Climate Control Module
        ModHandler.addShapedRecipe("climate_module", Mods.Gendustry.getItem("climate_module"),
                "GCG", "SRS", "PMP",
                'G', new UnificationEntry(gear, Materials.Bronze),
                'C', "circuitEv",
                'S', new UnificationEntry(stick, Materials.Platinum),
                'R', new UnificationEntry(rotor, Materials.Titanium),
                'P', new UnificationEntry(plate, Materials.Aluminium),
                'M', MetaItems.ELECTRIC_MOTOR_EV);
    }

    public static void hard() {
        //Bee Receptacle
        ModHandler.addShapedRecipe("bee_receptacle", Mods.Gendustry.getItem("bee_receptacle"),
                "SCS", "PLP", "SdS",
                'S', new UnificationEntry(screw, Materials.Titanium),
                'C', "beeComb",
                'P', new UnificationEntry(plate, Materials.Iridium),
                'L', new UnificationEntry(stickLong, Materials.Osmium));
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(screw, Materials.Titanium, 4)
                .input("beeComb")
                .input(plate, Materials.Iridium, 2)
                .input(stickLong, Materials.Osmium)
                .outputs(Mods.Gendustry.getItem("bee_receptacle"))
                .circuitMeta(10)
                .EUt(VA[EV]).duration(200).buildAndRegister();

        //Power Module
        ModHandler.addShapedRecipe("power_module", Mods.Gendustry.getItem("power_module"),
                "WIW", "PCP", "GMG",
                'W', new UnificationEntry(cableGtSingle, Materials.VanadiumGallium),
                'I', MetaItems.POWER_INTEGRATED_CIRCUIT,
                'P', new UnificationEntry(plate, Materials.Iridium),
                'C', "circuitEv",
                'G', new UnificationEntry(gearSmall, Materials.Osmium),
                'M', MetaItems.ELECTRIC_MOTOR_LuV);

        //Climate Control Module
        ModHandler.addShapedRecipe("climate_module", Mods.Gendustry.getItem("climate_module"),
                "GCG", "SRS", "PMP",
                'G', new UnificationEntry(gear, Materials.Bronze),
                'C', "circuitEv",
                'S', new UnificationEntry(stick, Materials.RhodiumPlatedPalladium),
                'R', new UnificationEntry(rotor, Materials.Osmium),
                'P', new UnificationEntry(plate, Materials.Iridium),
                'M', MetaItems.ELECTRIC_MOTOR_LuV);
    }
}
