package gtexpert.integration.ffm.recipes;

import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gtexpert.api.GTEValues;
import net.minecraft.item.ItemStack;

import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.integration.IntegrationUtil.getModItem;

public class FFMCoreRecipe {

    public static void init() {
        items();
    }

    public static void remove() {
        ModHandler.removeRecipeByName("forestry:sturdy_casing");

    }

    public static void items() {
        //Study Casing
        ModHandler.addShapedRecipe(true, "sturdy_casing_crafting",
                getModItem(GTEValues.MODID_FFM, "sturdy_machine", 0),
                        "PPP", "PwP", "PPP",
                'P', new UnificationEntry(OrePrefix.plate, Bronze));
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, Bronze, 8)
                .circuitMeta(8)
                .output(getModItem(GTEValues.MODID_FFM, "sturdy_machine", 0).getItem())
                .duration(50).EUt(16).buildAndRegister();

        //Hardened Casing
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(getModItem(GTEValues.MODID_FFM, "sturdy_machine", 0).getItem())
                .input(OrePrefix.plate, Diamond, 4)
                .output(getModItem(GTEValues.MODID_FFM, "hardened_machine", 0).getItem())
                .duration(1200).EUt(120).buildAndRegister();
    }
}
