package com.github.gtexpert.core.integration.deda.recipes;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.ore.OrePrefix.*;

import net.foxmcloud.draconicadditions.DAFeatures;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.brandon3055.draconicevolution.DEFeatures;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.ingredients.nbtmatch.NBTCondition;
import gregtech.api.recipes.ingredients.nbtmatch.NBTMatcher;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;

import gregicality.multiblocks.api.fluids.GCYMFluidStorageKeys;

import com.github.gtexpert.core.api.GTEValues;
import com.github.gtexpert.core.api.unification.material.GTEMaterials;
import com.github.gtexpert.core.api.util.Mods;
import com.github.gtexpert.core.integration.deda.recipemaps.GTEDraconicRecipeMaps;

public class DraconicToolsRecipe {

    public static void init() {
        // ########################################
        // Draconic Evolution
        // ########################################
        // Crystal Binder
        ModHandler.removeRecipeByName(Mods.DraconicEvolution.getResource("crystal_binder"));
        ModHandler.addShapedRecipe(true, "crystal_binder", new ItemStack(DEFeatures.crystalBinder),
                "PhP", " R ", " C ",
                'P', OreDictUnifier.get(plate, GTEMaterials.Draconium),
                'R', Mods.EnderIO.isModLoaded() ?
                        OreDictUnifier.get(stick, GTEMaterials.EnergeticAlloy) :
                        OreDictUnifier.get(stick, Materials.BlueAlloy),
                'C', DEFeatures.wyvernCore);

        // Wyvern Flux Capacitor
        ModHandler.removeRecipeByName(Mods.DraconicEvolution.getResource("draconium_capacitor"));
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(2)
                .inputs(new ItemStack(DEFeatures.wyvernEnergyCore, 4, 0))
                .input(plate, GTEMaterials.Draconium, 4)
                .input(DEFeatures.wyvernCore, 1)
                .outputs(new ItemStack(DEFeatures.draconiumCapacitor))
                .duration(100).EUt(VA[GTEValues.dedaVoltageTier])
                .withRecycling()
                .buildAndRegister();

        // Draconic Flux Capacitor
        ModHandler.removeRecipeByName(Mods.DraconicEvolution.getResource("draconium_capacitor_1"));
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(2)
                .inputs(new ItemStack(DEFeatures.draconiumCapacitor, 4, 0))
                .input(plate, GTEMaterials.AwakenedDraconium, 4)
                .input(DEFeatures.awakenedCore, 1)
                .outputs(new ItemStack(DEFeatures.draconiumCapacitor, 1, 1))
                .duration(100).EUt(VA[GTEValues.dedaVoltageTier + 1])
                .withRecycling()
                .buildAndRegister();

        // ########################################
        // Draconic Additions
        // ########################################
        // Chaos Container
        GTEDraconicRecipeMaps.AWAKENED_DRACONIUM_FUSION_RECIPES.recipeBuilder()
                .inputNBT(DEFeatures.dislocator, NBTMatcher.ANY, NBTCondition.ANY)
                .input(DEFeatures.infusedObsidian, 2)
                .input(DEFeatures.chaoticCore, 1)
                .input(DAFeatures.chaoticEnergyCore, 1)
                .fluidInputs(GTEMaterials.Cryotheum.getFluid(48000))
                .outputs(new ItemStack(DAFeatures.chaosContainer, 1))
                .fluidOutputs(GTEMaterials.Pyrotheum.getFluid(GCYMFluidStorageKeys.MOLTEN, 12000))
                .duration(100).EUt(VA[UHV])
                .buildAndRegister();

        // Portable Wired Wyvern Charger
        ModHandler.addShapedRecipe(true, "portable_wired_charger_wyvern", DAFeatures.pwcWyvern,
                "DDD", "ICI", "DDD",
                'D', Mods.EnderIO.isModLoaded() ?
                        OreDictUnifier.get(plate, GTEMaterials.DarkSteel) :
                        OreDictUnifier.get(plate, Materials.Steel),
                'I', OreDictUnifier.get(plate, Materials.Iron),
                'C', new ItemStack(DEFeatures.wyvernCore));

        // Portable Wired Wyvern Discharger
        ModHandler.addShapedRecipe(true, "portable_wired_discharger_wyvern", DAFeatures.pwdWyvern,
                "DDD", "GCG", "DDD",
                'D', Mods.EnderIO.isModLoaded() ?
                        OreDictUnifier.get(plate, GTEMaterials.DarkSteel) :
                        OreDictUnifier.get(plate, Materials.Steel),
                'G', OreDictUnifier.get(plate, Materials.Gold),
                'C', new ItemStack(DEFeatures.wyvernCore));

        // Portable Wired Draconic Charger
        ModHandler.addShapelessRecipe("portable_wired_charger_draconic", DAFeatures.pwcBasic,
                DAFeatures.pwcWyvern,
                new ItemStack(DEFeatures.draconicCore));

        // Portable Wired Draconic Discharger
        ModHandler.addShapelessRecipe("portable_wired_discharger_draconic", DAFeatures.pwdBasic,
                DAFeatures.pwcWyvern,
                new ItemStack(DEFeatures.draconicCore));

        // Portable Wired Awakened Charger
        ModHandler.addShapelessRecipe("portable_wired_charger_awakened", DAFeatures.pwcDraconic,
                DAFeatures.pwcBasic,
                new ItemStack(DEFeatures.awakenedCore));

        // Portable Wired Awakened Discharger
        ModHandler.addShapelessRecipe("portable_wired_discharger_awakened", DAFeatures.pwdDraconic,
                DAFeatures.pwdBasic,
                new ItemStack(DEFeatures.awakenedCore));

        // Portable Wired Chaotic Charger
        ModHandler.addShapelessRecipe("portable_wired_charger_chaotic", DAFeatures.pwcChaotic,
                DAFeatures.pwcDraconic,
                new ItemStack(DEFeatures.chaoticCore));

        // Portable Wired Chaotic Discharger
        ModHandler.addShapelessRecipe("portable_wired_discharger_chaotic", DAFeatures.pwdChaotic,
                DAFeatures.pwdDraconic,
                new ItemStack(DEFeatures.chaoticCore));

        // Wyvern Necklace of Shielding
        ModHandler.addShapedRecipe(true, "wyvern_shield_necklace", new ItemStack(DAFeatures.wyvernShieldNecklace, 1),
                "SSS", "SES", " C ",
                'S', OreDictUnifier.get(stick, Materials.RoseGold),
                'E', new ItemStack(DEFeatures.wyvernEnergyCore),
                'C', new ItemStack(DEFeatures.wyvernCore));

        // Draconic Necklace of Shielding
        ModHandler.addShapelessRecipe("draconic_shield_necklace", new ItemStack(DAFeatures.basicShieldNecklace, 1),
                new ItemStack(DAFeatures.wyvernShieldNecklace),
                new ItemStack(DEFeatures.draconicCore));

        // Awakened Necklace of Shielding
        ModHandler.addShapelessRecipe("awakened_shield_necklace", new ItemStack(DAFeatures.draconicShieldNecklace, 1),
                new ItemStack(DAFeatures.basicShieldNecklace),
                new ItemStack(DEFeatures.awakenedCore));

        // Draconic Belt of Overloading
        ModHandler.addShapedRecipe(true, "overload_belt", new ItemStack(DAFeatures.overloadBelt, 1),
                "SSS", "SLS", " C ",
                'S', OreDictUnifier.get(stick, GTEMaterials.Draconium),
                'C', new ItemStack(DEFeatures.awakenedCore),
                'L', Items.LEATHER);

        // Ring of Inertia Cancellation
        ModHandler.addShapedRecipe(true, "inertia_cancel_ring", new ItemStack(DAFeatures.inertiaCancelRing, 1),
                "SES", "E E", "SES",
                'S', OreDictUnifier.get(stick, Materials.RoseGold),
                'E', new ItemStack(DEFeatures.wyvernEnergyCore));
    }
}
