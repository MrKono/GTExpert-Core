package gtexpert.integration.gendustry.recipes;

import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.items.MetaItems;
import gtexpert.api.util.Mods;

import static gregtech.api.unification.ore.OrePrefix.*;

public class GendustryToolRecipe {

    public static void init() {
        // Industrial Grafter
        ModHandler.removeRecipeByName("recipe1");
        ModHandler.addShapedRecipe("industrial_grafter", Mods.Gendustry.getItem("industrial_grafter"),
                "SGd", "EMG", "PBP",
                'S', new UnificationEntry(screw, Materials.StainlessSteel),
                'G', Mods.Forestry.getItem("Grafter"),
                'E', new UnificationEntry(gearSmall, Materials.StainlessSteel),
                'M', MetaItems.ELECTRIC_MOTOR_LV,
                'P', new UnificationEntry(plate, Materials.StainlessSteel),
                'B', "batteryLv");
        //Scooporator MX200 Turbo
        ModHandler.removeRecipeByName("recipe2");
        ModHandler.addShapedRecipe("industrial_scoop", Mods.Gendustry.getItem("industrial_scoop"),
                "SSd", "EMG", "PBP",
                'S', new UnificationEntry(screw, Materials.StainlessSteel),
                'S', Mods.Forestry.getItem("Scoop"),
                'E', new UnificationEntry(gearSmall, Materials.StainlessSteel),
                'M', MetaItems.ELECTRIC_MOTOR_LV,
                'P', new UnificationEntry(plate, Materials.StainlessSteel),
                'B', "batteryLv");
    }
}
