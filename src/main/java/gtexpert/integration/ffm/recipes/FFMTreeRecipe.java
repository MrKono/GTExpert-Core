package gtexpert.integration.ffm.recipes;

import forestry.core.config.Config;
import forestry.core.fluids.Fluids;

import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.ingredients.GTRecipeItemInput;
import gregtech.api.recipes.ingredients.nbtmatch.ListNBTCondition;
import gregtech.api.recipes.ingredients.nbtmatch.NBTCondition;
import gregtech.api.recipes.ingredients.nbtmatch.NBTMatcher;
import gregtech.api.recipes.ingredients.nbtmatch.NBTTagType;
import gregtech.api.unification.material.Materials;
import gregtech.integration.forestry.ForestryConfig;
import gregtech.integration.forestry.ForestryUtil;

import gtexpert.api.GTEValues;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.FluidStack;


import static gregtech.integration.IntegrationUtil.getModItem;


public class FFMTreeRecipe {

    /* Target Module
    * Arboriculture
    * Charcoal
    * */

    public static void init() {
        arboriculture.register();
    }

    public static void remove() {}

    //Module: arboriculture
    public static class arboriculture {
        public static void register() {
            brewing();
        }

        public static void remove() {
        }

        public static void brewing() {
            //remove (added by CEu)
            if (ForestryUtil.apicultureEnabled() && ForestryConfig.harderForestryRecipes) {
                GTRecipeHandler.removeRecipesByInputs(RecipeMaps.BREWING_RECIPES,
                        new ItemStack[]{getModItem(GTEValues.MODID_FFM, "sapling", 0)},
                        new FluidStack[]{Materials.Water.getFluid(100)});

                if (Config.isFluidEnabled(Fluids.FOR_HONEY)) {
                    GTRecipeHandler.removeRecipesByInputs(RecipeMaps.BREWING_RECIPES,
                            new ItemStack[]{getModItem(GTEValues.MODID_FFM, "sapling", 0)},
                            new FluidStack[]{Fluids.FOR_HONEY.getFluid(100)});
                }
                if (Config.isFluidEnabled(Fluids.JUICE)) {
                    GTRecipeHandler.removeRecipesByInputs(RecipeMaps.BREWING_RECIPES,
                            new ItemStack[]{getModItem(GTEValues.MODID_FFM, "sapling", 0)},
                            new FluidStack[]{Fluids.JUICE.getFluid(100)});
                }
            }

            String[] para = {"Lowest", "Lower", "Low", "Average", "High", "Higher", "Highest"};
            Double[] multiple = {1.5, 3.0, 4.5, 6.0, 9.0, 12.0, 15.0};
            for (int i = 0; i < 7; i++) {
               /*
                               */
                if (Config.isFluidEnabled(Fluids.FOR_HONEY)) {
                    RecipeMaps.BREWING_RECIPES.recipeBuilder()
                            .input(new GTRecipeItemInput(getModItem(GTEValues.MODID_FFM, "sapling", 0)).setNBTMatchingCondition(
                                    NBTMatcher.RECURSIVE_EQUAL_TO, NBTCondition.create(
                                                    NBTTagType.COMPOUND, "Genome", ListNBTCondition.create(
                                                            NBTTagType.LIST, "Chromosomes", NBTCondition.create(
                                                                    NBTTagType.COMPOUND, "5", NBTCondition.create(
                                                                            NBTTagType.STRING, "UID0", "forestry.sappiness" + para[i]))))))
                            .fluidInputs(Fluids.FOR_HONEY.getFluid(100))
                            .fluidOutputs(Materials.Biomass.getFluid((int) (100 * multiple[i])))
                            .duration(600).EUt(3).buildAndRegister();

                    RecipeMaps.BREWING_RECIPES.recipeBuilder().input("treeSapling", 1)
                            .circuitMeta(1)
                            .fluidInputs(Fluids.FOR_HONEY.getFluid(100))
                            .fluidOutputs(Materials.Biomass.getFluid(150))
                            .duration(600).EUt(3).buildAndRegister();
                }
                if (Config.isFluidEnabled(Fluids.JUICE)) {
                    RecipeMaps.BREWING_RECIPES.recipeBuilder()
                            .input(new GTRecipeItemInput(getModItem(GTEValues.MODID_FFM, "sapling", 0)).setNBTMatchingCondition(
                                    NBTMatcher.RECURSIVE_EQUAL_TO, NBTCondition.create(
                                            NBTTagType.COMPOUND, "5", NBTCondition.create(
                                                    NBTTagType.STRING, "UID0", "forestry.sappiness" + para[i]))))
                            .fluidInputs(Fluids.JUICE.getFluid(100))
                            .fluidOutputs(Materials.Biomass.getFluid((int) (100 * multiple[i])))
                            .duration(600).EUt(3).buildAndRegister();

                    RecipeMaps.BREWING_RECIPES.recipeBuilder().input("treeSapling", 1)
                            .circuitMeta(1)
                            .fluidInputs(Fluids.JUICE.getFluid(100))
                            .fluidOutputs(Materials.Biomass.getFluid(150))
                            .duration(600).EUt(3).buildAndRegister();
                }
                RecipeMaps.BREWING_RECIPES.recipeBuilder()
                        .input(new GTRecipeItemInput(getModItem(GTEValues.MODID_FFM, "sapling", 0)).setNBTMatchingCondition(
                                NBTMatcher.RECURSIVE_EQUAL_TO, NBTCondition.create(
                                        NBTTagType.COMPOUND, "5", NBTCondition.create(
                                                NBTTagType.STRING, "UID0", "forestry.sappiness" + para[i]))))
                        .fluidInputs(Materials.Water.getFluid(100))
                        .fluidOutputs(Materials.Biomass.getFluid((int) (100 * multiple[i] / 1.5)))
                        .duration(800).EUt(3).buildAndRegister();

                RecipeMaps.BREWING_RECIPES.recipeBuilder().input("treeSapling", 1)
                        .circuitMeta(1)
                        .fluidInputs(Materials.Water.getFluid(100))
                        .fluidOutputs(Materials.Biomass.getFluid(150))
                        .duration(800).EUt(3).buildAndRegister();
            }
        }
    }

    //Module: charcoal
    public static class charcoal {}
}
