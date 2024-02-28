package gtexpert.integration.ffm.recipes;

import gregtech.api.recipes.ModHandler;
import gregtech.common.items.MetaItems;
import gtexpert.api.GTEValues;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.integration.IntegrationUtil.getModItem;

public class FFMApicultureRecipe {
    // Target Module: apiculture
    public static void init() {
        items();
        blocks();
    }

    public static void remove() {
        ModHandler.removeRecipeByName("forestry:wax_cast");
        ModHandler.removeRecipeByName("forestry:apiarist_hat");
        ModHandler.removeRecipeByName("forestry:apiarist_chest");
        ModHandler.removeRecipeByName("forestry:apiarist_legs");
        ModHandler.removeRecipeByName("forestry:apiarist_boots");
    }

    public static void items() {
        //Wax Cast
        List<ItemStack> wax = new ArrayList<>();
        wax.add(getModItem(GTEValues.MODID_FFM, "beeswax", 0));
        wax.add(getModItem(GTEValues.MODID_FFM, "refractory_wax", 0));
        for (ItemStack input : wax) {
            ALLOY_SMELTER_RECIPES.recipeBuilder()
                    .input(input.getItem(), 8)
                    .notConsumable(MetaItems.SHAPE_MOLD_BLOCK)
                    .output(getModItem(GTEValues.MODID_FFM, "wax_cast", 0).getItem())
                    .duration(200).EUt(16).buildAndRegister();
        }

        // Apiarist's Armors
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(getModItem(GTEValues.MODID_FFM, "crafting_material", 3).getItem(), 5, 3)
                .input(Items.LEATHER_HELMET)
                .output(getModItem(GTEValues.MODID_FFM, "apiarist_helmet", 0).getItem())
                .duration(1200).EUt(64).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(getModItem(GTEValues.MODID_FFM, "crafting_material", 3).getItem(), 8, 3)
                .input(Items.LEATHER_CHESTPLATE)
                .output(getModItem(GTEValues.MODID_FFM, "apiarist_chest", 0).getItem())
                .duration(1200).EUt(64).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(getModItem(GTEValues.MODID_FFM, "crafting_material", 3).getItem(), 8, 3)
                .input(Items.LEATHER_LEGGINGS)
                .output(getModItem(GTEValues.MODID_FFM, "apiarist_legs", 0).getItem())
                .duration(1200).EUt(64).buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(getModItem(GTEValues.MODID_FFM, "crafting_material", 3).getItem(), 4, 3)
                .input(Items.LEATHER_BOOTS)
                .output(getModItem(GTEValues.MODID_FFM, "apiarist_boots", 0).getItem())
                .duration(1200).EUt(64).buildAndRegister();

        // Minecart with Bee House
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(getModItem(GTEValues.MODID_FFM, "bee_house", 0).getItem())
                .input(Items.MINECART)
                .output(getModItem(GTEValues.MODID_FFM, "cart.beehouse", 0).getItem())
                .duration(100).EUt(4).buildAndRegister();

        // Minecart with Apiary
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(getModItem(GTEValues.MODID_FFM, "apiary", 0).getItem())
                .input(Items.MINECART)
                .output(getModItem(GTEValues.MODID_FFM, "cart.beehouse", 0).getItem(), 1, 1)
                .duration(100).EUt(4).buildAndRegister();

    }

    public static void blocks() {
        //Alveary
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(getModItem(GTEValues.MODID_FFM, "impregnated_casing", 0).getItem())
                .input(getModItem(GTEValues.MODID_FFM, "crafting_material", 6).getItem(), 6, 6)
                .output(getModItem(GTEValues.MODID_FFM, "alveary.plain", 0).getItem())
                .duration(1200).EUt(64).buildAndRegister();
    }
}
