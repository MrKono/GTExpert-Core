package com.github.gtexpert.core.loaders.recipe;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.ore.OrePrefix.*;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import org.jetbrains.annotations.NotNull;

import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.fluids.store.FluidStorageKeys;
import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.builders.AssemblyLineRecipeBuilder;
import gregtech.api.recipes.ingredients.nbtmatch.NBTCondition;
import gregtech.api.recipes.ingredients.nbtmatch.NBTMatcher;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.info.MaterialFlags;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.BlockMachineCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;

import gregicality.multiblocks.api.fluids.GCYMFluidStorageKeys;

import com.github.gtexpert.core.api.GTEValues;
import com.github.gtexpert.core.api.recipes.GTERecipeMaps;
import com.github.gtexpert.core.api.unification.material.GTEMaterials;
import com.github.gtexpert.core.api.util.GTELog;
import com.github.gtexpert.core.api.util.GTEUtility;
import com.github.gtexpert.core.api.util.Mods;
import com.github.gtexpert.core.common.GTEConfigHolder;
import com.github.gtexpert.core.common.blocks.GTEBlockMetalCasing;
import com.github.gtexpert.core.common.blocks.GTEMetaBlocks;
import com.github.gtexpert.core.common.items.GTEMetaItems;
import com.github.gtexpert.core.common.metatileentities.GTEMetaTileEntities;

public class GTERecipe {

    public static void init() {
        materials();
        items();
        blocks();
        tools();
        if (GTEConfigHolder.gteFlag.addCreativeRecipe) end_contents();
    }

    private static void materials() {
        // Nether Star Dust
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Materials.Diamond, 1)
                .input(dust, Materials.Iridium, 1)
                .fluidInputs(Materials.NetherAir.getFluid(8000))
                .fluidInputs(Materials.RocketFuel.getFluid(1000))
                .cleanroom(CleanroomType.CLEANROOM)
                .output(dust, Materials.NetherStar, 2)
                .duration(400).EUt(GTEConfigHolder.gteFlag.peacefulFlag ? VA[IV] : VA[LuV])
                .buildAndRegister();

        // Ender Eye
        RecipeMaps.MIXER_RECIPES.recipeBuilder()
                .circuitMeta(1)
                .fluidInputs(Materials.EnderPearl.getFluid(144))
                .fluidInputs(Materials.Blaze.getFluid(144))
                .fluidOutputs(Materials.EnderEye.getFluid(144))
                .duration(50).EUt(VA[HV])
                .buildAndRegister();

        // NM_HEA_NPs Dust
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder()
                .input(dust, GTEMaterials.NM_HEA_NPs, 8)
                .output(dust, Materials.Gold, 1)
                .output(dust, Materials.Silver, 1)
                .output(dust, Materials.Ruthenium, 1)
                .output(dust, Materials.Rhodium, 1)
                .output(dust, Materials.Palladium, 1)
                .output(dust, Materials.Osmium, 1)
                .fluidOutputs(Materials.Iridium.getFluid(144))
                .fluidOutputs(Materials.Platinum.getFluid(144))
                .duration(10).EUt(VA[LV])
                .buildAndRegister();
        RecipeMaps.ELECTROLYZER_RECIPES.recipeBuilder()
                .input(dust, GTEMaterials.NM_HEA_NPs, 8)
                .output(dust, Materials.Gold, 1)
                .output(dust, Materials.Silver, 1)
                .output(dust, Materials.Ruthenium, 1)
                .output(dust, Materials.Rhodium, 1)
                .output(dust, Materials.Palladium, 1)
                .output(dust, Materials.Osmium, 1)
                .fluidOutputs(Materials.Iridium.getFluid(144))
                .fluidOutputs(Materials.Platinum.getFluid(144))
                .duration(10).EUt(VA[LV])
                .buildAndRegister();

        // Naquadah Rocket Fuel
        RecipeMaps.MIXER_RECIPES.recipeBuilder()
                .fluidInputs(Materials.NaquadahEnriched.getFluid(1296))
                .fluidInputs(Materials.RocketFuel.getFluid(5000))
                .fluidOutputs(GTEMaterials.NaquadahRocketFuel.getFluid(6000))
                .duration(20).EUt(VA[IV])
                .buildAndRegister();
        RecipeMaps.COMBUSTION_GENERATOR_FUELS.recipeBuilder()
                .fluidInputs(GTEMaterials.NaquadahRocketFuel.getFluid(1))
                .duration(750).EUt(32)
                .buildAndRegister();

        // Liquid Air, Liquid Nether Air, Liquid Ender Air
        GTERecipeMaps.LARGE_GAS_COLLECTOR_RECIPES.recipeBuilder()
                .circuitMeta(4)
                .input(dust, Materials.Stone, 32)
                .fluidOutputs(Materials.Air.getFluid(10000))
                .duration(200).EUt(VA[LuV])
                .buildAndRegister();
        GTERecipeMaps.LARGE_GAS_COLLECTOR_RECIPES.recipeBuilder()
                .circuitMeta(4)
                .input(dust, Materials.Netherrack, 32)
                .fluidOutputs(Materials.NetherAir.getFluid(10000))
                .duration(200).EUt(VA[LuV])
                .buildAndRegister();
        GTERecipeMaps.LARGE_GAS_COLLECTOR_RECIPES.recipeBuilder()
                .circuitMeta(4)
                .input(dust, Materials.Endstone, 32)
                .fluidOutputs(Materials.EnderAir.getFluid(10000))
                .duration(200).EUt(VA[LuV])
                .buildAndRegister();
        GTERecipeMaps.LARGE_GAS_COLLECTOR_RECIPES.recipeBuilder()
                .circuitMeta(5)
                .input(dust, Materials.Stone, 32)
                .fluidInputs(Materials.Helium.getFluid(FluidStorageKeys.LIQUID, 5000))
                .fluidOutputs(Materials.Helium.getFluid(2500))
                .fluidOutputs(Materials.LiquidAir.getFluid(10000))
                .duration(200).EUt(VA[LuV])
                .buildAndRegister();
        GTERecipeMaps.LARGE_GAS_COLLECTOR_RECIPES.recipeBuilder()
                .circuitMeta(5)
                .input(dust, Materials.Netherrack, 32)
                .fluidInputs(Materials.Helium.getFluid(FluidStorageKeys.LIQUID, 5000))
                .fluidOutputs(Materials.Helium.getFluid(2500))
                .fluidOutputs(Materials.LiquidNetherAir.getFluid(10000))
                .duration(200).EUt(VA[LuV])
                .buildAndRegister();
        GTERecipeMaps.LARGE_GAS_COLLECTOR_RECIPES.recipeBuilder()
                .circuitMeta(5)
                .input(dust, Materials.Endstone, 32)
                .fluidInputs(Materials.Helium.getFluid(FluidStorageKeys.LIQUID, 5000))
                .fluidOutputs(Materials.Helium.getFluid(2500))
                .fluidOutputs(Materials.LiquidEnderAir.getFluid(10000))
                .duration(200).EUt(VA[LuV])
                .buildAndRegister();

        if (GTEValues.isModLoadedDEDA()) {
            GTERecipeMaps.LARGE_GAS_COLLECTOR_RECIPES.recipeBuilder()
                    .circuitMeta(6)
                    .input(dust, Materials.Stone, 32)
                    .fluidInputs(GTEMaterials.Cryotheum.getFluid(1000))
                    .fluidOutputs(GTEMaterials.Pyrotheum.getFluid(GCYMFluidStorageKeys.MOLTEN, 250))
                    .fluidOutputs(Materials.LiquidAir.getFluid(10000))
                    .duration(20).EUt(VA[LuV])
                    .buildAndRegister();
            GTERecipeMaps.LARGE_GAS_COLLECTOR_RECIPES.recipeBuilder()
                    .circuitMeta(6)
                    .input(dust, Materials.Netherrack, 32)
                    .fluidInputs(GTEMaterials.Cryotheum.getFluid(1000))
                    .fluidOutputs(GTEMaterials.Pyrotheum.getFluid(GCYMFluidStorageKeys.MOLTEN, 250))
                    .fluidOutputs(Materials.LiquidNetherAir.getFluid(10000))
                    .duration(20).EUt(VA[LuV])
                    .buildAndRegister();
            GTERecipeMaps.LARGE_GAS_COLLECTOR_RECIPES.recipeBuilder()
                    .circuitMeta(6)
                    .input(dust, Materials.Endstone, 32)
                    .fluidInputs(GTEMaterials.Cryotheum.getFluid(1000))
                    .fluidOutputs(GTEMaterials.Pyrotheum.getFluid(GCYMFluidStorageKeys.MOLTEN, 250))
                    .fluidOutputs(Materials.LiquidEnderAir.getFluid(10000))
                    .duration(20).EUt(VA[LuV])
                    .buildAndRegister();
        }

        // Netherrack Dust, Endstone Dust
        RecipeMaps.ROCK_BREAKER_RECIPES.recipeBuilder()
                .notConsumable(new ItemStack(Blocks.NETHERRACK, 1))
                .outputs(new ItemStack(Blocks.NETHERRACK, 1))
                .duration(16).EUt(VA[LuV])
                .buildAndRegister();
        RecipeMaps.ROCK_BREAKER_RECIPES.recipeBuilder()
                .notConsumable(new ItemStack(Blocks.END_STONE, 1))
                .outputs(new ItemStack(Blocks.END_STONE, 1))
                .duration(16).EUt(VA[ZPM])
                .buildAndRegister();
    }

    private static void items() {
        // Artificial Bone
        RecipeMaps.LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .circuitMeta(10)
                .input(dust, Materials.Calcium, 10)
                .input(dust, Materials.Phosphate, 6)
                .fluidInputs(Materials.Hydrogen.getFluid(2000))
                .fluidInputs(Materials.Oxygen.getFluid(2000))
                .output(GTEMetaItems.ARTIFICIAL_BONE, 2)
                .duration(400).EUt(VA[IV])
                .buildAndRegister();
        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder()
                .input(dust, GTEMaterials.ArtificialBone, 9)
                .output(block, GTEMaterials.ArtificialBone)
                .duration(300).EUt(2).buildAndRegister();

        // Skeleton Skull
        RecipeMaps.LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .circuitMeta(1)
                .input(GTEMetaItems.ARTIFICIAL_BONE, 8)
                .fluidInputs(Materials.Mutagen.getFluid(500))
                .outputs(Mods.Vanilla.getItem("skull", 1))
                .duration(100).EUt(GTEConfigHolder.gteFlag.peacefulFlag ? VA[HV] : VA[IV])
                .buildAndRegister();

        // Wither Skeleton Skull
        RecipeMaps.LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .circuitMeta(2)
                .input(dust, Materials.Coal, 2)
                .input(GTEMetaItems.ARTIFICIAL_BONE, 8)
                .fluidInputs(Materials.Mutagen.getFluid(500))
                .outputs(Mods.Vanilla.getItem("skull", 1, 1))
                .duration(100).EUt(GTEConfigHolder.gteFlag.peacefulFlag ? VA[HV] : VA[IV])
                .buildAndRegister();

        // Zombie Head
        RecipeMaps.LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .circuitMeta(3)
                .input(dust, Materials.Meat, 2)
                .input(GTEMetaItems.ARTIFICIAL_BONE, 4)
                .fluidInputs(Materials.Mutagen.getFluid(500))
                .outputs(Mods.Vanilla.getItem("skull", 1, 2))
                .duration(100).EUt(GTEConfigHolder.gteFlag.peacefulFlag ? VA[HV] : VA[IV])
                .buildAndRegister();

        // Creeper Head
        RecipeMaps.LARGE_CHEMICAL_RECIPES.recipeBuilder()
                .circuitMeta(4)
                .input(dust, Materials.Gunpowder, 6)
                .input(GTEMetaItems.ARTIFICIAL_BONE, 2)
                .fluidInputs(Materials.Mutagen.getFluid(500))
                .outputs(Mods.Vanilla.getItem("skull", 1, 4))
                .duration(100).EUt(GTEConfigHolder.gteFlag.peacefulFlag ? VA[HV] : VA[IV])
                .buildAndRegister();

        // Primitive Parts
        String componentsName = GTEConfigHolder.gteFlag.componentsName;
        if (componentsName.equals("ulv") || componentsName.equals("primitive")) {
            switch (GTEConfigHolder.gteFlag.componentsRecipeType) {
                case "none" -> {
                    return;
                }
                case "easy" -> {
                    ModHandler.addShapedRecipe(true, "electric_motor_" + componentsName,
                            GTEMetaItems.GTE_ELECTRIC_MOTOR.getStackForm(), "WR", "MW",
                            'R', new UnificationEntry(stick, Materials.Bronze),
                            'M', new UnificationEntry(stick, Materials.IronMagnetic),
                            'W', new UnificationEntry(wireGtSingle, Materials.Tin));

                    ModHandler.addShapedRecipe(true, "electric_piston_" + componentsName,
                            GTEMetaItems.GTE_ELECTRIC_PISTON.getStackForm(), "PR", "MG",
                            'R', new UnificationEntry(stick, Materials.Bronze),
                            'G', new UnificationEntry(gearSmall, Materials.Bronze),
                            'P', new UnificationEntry(plate, Materials.Bronze),
                            'M', GTEMetaItems.GTE_ELECTRIC_MOTOR.getStackForm());

                    ModHandler.addShapedRecipe(true, "electric_pump_" + componentsName,
                            GTEMetaItems.GTE_ELECTRIC_PUMP.getStackForm(), "PR", "MO",
                            'R', new UnificationEntry(rotor, Materials.Bronze),
                            'O', new UnificationEntry(ring, Materials.Rubber),
                            'P', new UnificationEntry(pipeNormalFluid, Materials.Copper),
                            'M', GTEMetaItems.GTE_ELECTRIC_MOTOR.getStackForm());

                    ModHandler.addShapedRecipe(true, "conveyor_module_" + componentsName,
                            GTEMetaItems.GTE_CONVEYOR_MODULE.getStackForm(), "PC", "MP",
                            'P', new UnificationEntry(plate, Materials.Rubber),
                            'C', new UnificationEntry(cableGtSingle, Materials.Lead),
                            'M', GTEMetaItems.GTE_ELECTRIC_MOTOR.getStackForm());

                    ModHandler.addShapedRecipe(true, "robot_arm_" + componentsName,
                            GTEMetaItems.GTE_ROBOT_ARM.getStackForm(), "PR", "MC",
                            'R', new UnificationEntry(stick, Materials.Bronze),
                            'C', new UnificationEntry(circuit, MarkerMaterials.Tier.ULV),
                            'P', GTEMetaItems.GTE_ELECTRIC_PISTON.getStackForm(),
                            'M', GTEMetaItems.GTE_ELECTRIC_MOTOR.getStackForm());

                    ModHandler.addShapedRecipe(true, "fluid_regulator_" + componentsName,
                            GTEMetaItems.GTE_FLUID_REGULATOR.getStackForm(), "PC", "Cd",
                            'C', new UnificationEntry(circuit, MarkerMaterials.Tier.ULV),
                            'P', GTEMetaItems.GTE_ELECTRIC_PUMP.getStackForm());

                    ModHandler.addShapedRecipe(true, "field_generator_" + componentsName,
                            GTEMetaItems.GTE_FIELD_GENERATOR.getStackForm(), "CP", "UG",
                            'C', new UnificationEntry(wireGtDouble, Materials.RedAlloy),
                            'P', new UnificationEntry(plate, Materials.Bronze),
                            'U', new UnificationEntry(circuit, MarkerMaterials.Tier.ULV),
                            'G', new UnificationEntry(gem, Materials.Lapis));

                    ModHandler.addShapedRecipe(true, "emitter_" + componentsName,
                            GTEMetaItems.GTE_EMITTER.getStackForm(), "CS", "UG",
                            'C', new UnificationEntry(cableGtSingle, Materials.RedAlloy),
                            'S', new UnificationEntry(stick, Materials.Bronze),
                            'U', new UnificationEntry(circuit, MarkerMaterials.Tier.ULV),
                            'G', new UnificationEntry(gem, Materials.Lapis));

                    ModHandler.addShapedRecipe(true, "sensor_" + componentsName,
                            GTEMetaItems.GTE_SENSOR.getStackForm(), "PG", "US",
                            'P', new UnificationEntry(plate, Materials.Bronze),
                            'S', new UnificationEntry(stick, Materials.Bronze),
                            'U', new UnificationEntry(circuit, MarkerMaterials.Tier.ULV),
                            'G', new UnificationEntry(gem, Materials.Lapis));
                }
                case "normal" -> {
                    ModHandler.addShapedRecipe(true, "electric_motor_" + componentsName,
                            GTEMetaItems.GTE_ELECTRIC_MOTOR.getStackForm(), "CWR", "WMW", "RWC",
                            'R', new UnificationEntry(stick, Materials.Bronze),
                            'M', new UnificationEntry(stick, Materials.IronMagnetic),
                            'W', new UnificationEntry(wireGtSingle, Materials.Tin),
                            'C', new UnificationEntry(cableGtSingle, Materials.Lead));
                    RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                            .input(cableGtSingle, Materials.Lead, 2)
                            .input(stick, Materials.Bronze, 2)
                            .input(stick, Materials.IronMagnetic)
                            .input(wireGtSingle, Materials.Tin, 4)
                            .output(GTEMetaItems.GTE_ELECTRIC_MOTOR)
                            .duration(100).EUt(VA[ULV]).buildAndRegister();

                    ModHandler.addShapedRecipe(true, "electric_piston_" + componentsName,
                            GTEMetaItems.GTE_ELECTRIC_PISTON.getStackForm(), "PPP", "CRR", "CMG",
                            'R', new UnificationEntry(stick, Materials.Bronze),
                            'G', new UnificationEntry(gearSmall, Materials.Bronze),
                            'P', new UnificationEntry(plate, Materials.Bronze),
                            'C', new UnificationEntry(cableGtSingle, Materials.Lead),
                            'M', GTEMetaItems.GTE_ELECTRIC_MOTOR.getStackForm());
                    RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                            .input(stick, Materials.Bronze, 2)
                            .input(cableGtSingle, Materials.Lead, 2)
                            .input(plate, Materials.Bronze, 3)
                            .input(gearSmall, Materials.Bronze)
                            .input(GTEMetaItems.GTE_ELECTRIC_MOTOR)
                            .output(GTEMetaItems.GTE_ELECTRIC_PISTON)
                            .duration(100).EUt(VA[ULV]).buildAndRegister();

                    ModHandler.addShapedRecipe(true, "electric_pump_" + componentsName,
                            GTEMetaItems.GTE_ELECTRIC_PUMP.getStackForm(), "SRO", "dPw", "OMC",
                            'R', new UnificationEntry(rotor, Materials.Bronze),
                            'S', new UnificationEntry(screw, Materials.Bronze),
                            'O', new UnificationEntry(ring, Materials.Rubber),
                            'P', new UnificationEntry(pipeNormalFluid, Materials.Copper),
                            'C', new UnificationEntry(cableGtSingle, Materials.Lead),
                            'M', GTEMetaItems.GTE_ELECTRIC_MOTOR.getStackForm());
                    RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                            .input(cableGtSingle, Materials.Lead)
                            .input(pipeNormalFluid, Materials.Copper)
                            .input(screw, Materials.Bronze)
                            .input(rotor, Materials.Bronze)
                            .input(ring, Materials.Rubber, 2)
                            .input(GTEMetaItems.GTE_ELECTRIC_MOTOR)
                            .output(GTEMetaItems.GTE_ELECTRIC_PUMP)
                            .duration(100).EUt(VA[ULV]).buildAndRegister();

                    ModHandler.addShapedRecipe(true, "conveyor_module_" + componentsName,
                            GTEMetaItems.GTE_CONVEYOR_MODULE.getStackForm(), "PPP", "MCM", "PPP",
                            'P', new UnificationEntry(plate, Materials.Rubber),
                            'C', new UnificationEntry(cableGtSingle, Materials.Lead),
                            'M', GTEMetaItems.GTE_ELECTRIC_MOTOR.getStackForm());
                    RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                            .input(cableGtSingle, Materials.Lead)
                            .inputs(GTEMetaItems.GTE_ELECTRIC_MOTOR.getStackForm(2))
                            .fluidInputs(Materials.Rubber.getFluid(L * 6))
                            .circuitMeta(1)
                            .output(GTEMetaItems.GTE_CONVEYOR_MODULE)
                            .duration(100).EUt(VA[ULV]).buildAndRegister();

                    ModHandler.addShapedRecipe(true, "robot_arm_" + componentsName,
                            GTEMetaItems.GTE_ROBOT_ARM.getStackForm(), "CCC", "MRM", "PUR",
                            'R', new UnificationEntry(stick, Materials.Bronze),
                            'C', new UnificationEntry(cableGtSingle, Materials.Lead),
                            'U', new UnificationEntry(circuit, MarkerMaterials.Tier.ULV),
                            'P', GTEMetaItems.GTE_ELECTRIC_PISTON.getStackForm(),
                            'M', GTEMetaItems.GTE_ELECTRIC_MOTOR.getStackForm());
                    RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                            .input(cableGtSingle, Materials.Lead, 3)
                            .input(stick, Materials.Bronze, 2)
                            .input(GTEMetaItems.GTE_ELECTRIC_MOTOR, 2)
                            .input(GTEMetaItems.GTE_ELECTRIC_PISTON)
                            .input(circuit, MarkerMaterials.Tier.ULV)
                            .output(GTEMetaItems.GTE_ROBOT_ARM)
                            .duration(100).EUt(VA[ULV]).buildAndRegister();

                    ModHandler.addShapedRecipe(true, "fluid_regulator_" + componentsName,
                            GTEMetaItems.GTE_FLUID_REGULATOR.getStackForm(), " U ", "dPw", " U ",
                            'U', new UnificationEntry(circuit, MarkerMaterials.Tier.ULV),
                            'P', GTEMetaItems.GTE_ELECTRIC_PUMP.getStackForm());
                    RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                            .input(GTEMetaItems.GTE_ELECTRIC_PUMP)
                            .input(circuit, MarkerMaterials.Tier.ULV, 2)
                            .circuitMeta(1)
                            .output(GTEMetaItems.GTE_FLUID_REGULATOR)
                            .EUt(VA[ULV])
                            .duration(400)
                            .withRecycling()
                            .buildAndRegister();

                    ModHandler.addShapedRecipe(true, "field_generator_" + componentsName,
                            GTEMetaItems.GTE_FIELD_GENERATOR.getStackForm(), "CPC", "UGU", "CPC",
                            'C', new UnificationEntry(wireGtDouble, Materials.RedAlloy),
                            'P', new UnificationEntry(plate, Materials.Bronze),
                            'U', new UnificationEntry(circuit, MarkerMaterials.Tier.ULV),
                            'G', new UnificationEntry(gem, Materials.Lapis));
                    RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                            .input(gem, Materials.Lapis)
                            .input(plate, Materials.Bronze, 2)
                            .input(circuit, MarkerMaterials.Tier.ULV, 2)
                            .input(wireGtDouble, Materials.RedAlloy, 4)
                            .output(GTEMetaItems.GTE_FIELD_GENERATOR)
                            .duration(100).EUt(VA[ULV]).buildAndRegister();

                    ModHandler.addShapedRecipe(true, "emitter_" + componentsName,
                            GTEMetaItems.GTE_EMITTER.getStackForm(), "CSU", "SGS", "USC",
                            'C', new UnificationEntry(cableGtSingle, Materials.RedAlloy),
                            'S', new UnificationEntry(stick, Materials.Bronze),
                            'U', new UnificationEntry(circuit, MarkerMaterials.Tier.ULV),
                            'G', new UnificationEntry(gem, Materials.Lapis));
                    RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                            .circuitMeta(1)
                            .input(stick, Materials.Bronze, 4)
                            .input(cableGtSingle, Materials.RedAlloy, 2)
                            .input(circuit, MarkerMaterials.Tier.ULV, 2)
                            .input(gem, Materials.Lapis)
                            .output(GTEMetaItems.GTE_EMITTER)
                            .duration(100).EUt(VA[ULV]).buildAndRegister();

                    ModHandler.addShapedRecipe(true, "sensor_" + componentsName,
                            GTEMetaItems.GTE_SENSOR.getStackForm(), "P G", "PS ", "UPP",
                            'P', new UnificationEntry(plate, Materials.Bronze),
                            'S', new UnificationEntry(stick, Materials.Bronze),
                            'U', new UnificationEntry(circuit, MarkerMaterials.Tier.ULV),
                            'G', new UnificationEntry(gem, Materials.Lapis));
                    RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                            .input(stick, Materials.Bronze)
                            .input(plate, Materials.Bronze, 4)
                            .input(circuit, MarkerMaterials.Tier.ULV)
                            .input(gem, Materials.Lapis)
                            .output(GTEMetaItems.GTE_SENSOR)
                            .duration(100).EUt(VA[ULV]).buildAndRegister();
                }
                default -> GTELog.logger.error("Invalid componentsRecipeType setting: {}",
                        GTEConfigHolder.gteFlag.componentsRecipeType);
            }
        }

        // Solar Panels
        ModHandler.removeRecipeByOutput(MetaItems.COVER_SOLAR_PANEL_ULV.getStackForm());
        ModHandler.removeRecipeByOutput(MetaItems.COVER_SOLAR_PANEL_LV.getStackForm());
        if (GTEConfigHolder.ceuOverride.hardSolarPanel) {
            // Solar Panel
            ModHandler.removeRecipeByOutput(MetaItems.COVER_SOLAR_PANEL.getStackForm());
            ModHandler.addShapedRecipe("solar_panel_basic",
                    MetaItems.COVER_SOLAR_PANEL.getStackForm(), "SGS", "CFC",
                    'S', MetaItems.SILICON_WAFER,
                    'G', paneGlass,
                    'C', new UnificationEntry(circuit, MarkerMaterials.Tier.ULV),
                    'F', MetaItems.CARBON_FIBER_PLATE);
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                    .circuitMeta(1)
                    .input(MetaItems.SILICON_WAFER, 2)
                    .input("paneGlass", 1)
                    .input(circuit, MarkerMaterials.Tier.ULV, 2)
                    .input(MetaItems.CARBON_FIBER_PLATE, 1)
                    .output(MetaItems.COVER_SOLAR_PANEL, 1)
                    .duration(20).EUt(VA[ULV])
                    .buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                    .circuitMeta(1)
                    .input(MetaItems.PHOSPHORUS_WAFER, 2)
                    .input("paneGlass", 4)
                    .input(circuit, MarkerMaterials.Tier.ULV, 8)
                    .input(MetaItems.CARBON_FIBER_PLATE, 4)
                    .output(MetaItems.COVER_SOLAR_PANEL, 4)
                    .duration(20).EUt(VA[MV])
                    .buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                    .circuitMeta(1)
                    .input(MetaItems.NAQUADAH_WAFER, 2)
                    .input("paneGlass", 8)
                    .input(circuit, MarkerMaterials.Tier.ULV, 16)
                    .input(MetaItems.CARBON_FIBER_PLATE, 8)
                    .output(MetaItems.COVER_SOLAR_PANEL, 8)
                    .duration(20).EUt(VA[EV])
                    .buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                    .circuitMeta(1)
                    .input(MetaItems.NEUTRONIUM_WAFER, 2)
                    .input("paneGlass", 16)
                    .input(circuit, MarkerMaterials.Tier.ULV, 32)
                    .input(MetaItems.CARBON_FIBER_PLATE, 16)
                    .output(MetaItems.COVER_SOLAR_PANEL, 16)
                    .duration(20).EUt(VA[LuV])
                    .buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                    .circuitMeta(2)
                    .input(MetaItems.SILICON_WAFER, 16)
                    .input("paneGlass", 8)
                    .input(circuit, MarkerMaterials.Tier.ULV, 16)
                    .input(MetaItems.CARBON_FIBER_PLATE, 8)
                    .output(MetaItems.COVER_SOLAR_PANEL, 8)
                    .duration(20).EUt(VA[LV])
                    .buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                    .circuitMeta(3)
                    .input(MetaItems.SILICON_WAFER, 32)
                    .input("paneGlass", 16)
                    .input(circuit, MarkerMaterials.Tier.ULV, 32)
                    .input(MetaItems.CARBON_FIBER_PLATE, 16)
                    .output(MetaItems.COVER_SOLAR_PANEL, 16)
                    .duration(20).EUt(VA[MV])
                    .buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                    .circuitMeta(4)
                    .input(MetaItems.SILICON_WAFER, 64)
                    .input("paneGlass", 32)
                    .input(circuit, MarkerMaterials.Tier.ULV, 64)
                    .input(MetaItems.CARBON_FIBER_PLATE, 32)
                    .output(MetaItems.COVER_SOLAR_PANEL, 32)
                    .duration(20).EUt(VA[HV])
                    .buildAndRegister();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                    .circuitMeta(5)
                    .input(MetaItems.SILICON_WAFER, 64)
                    .input(MetaItems.SILICON_WAFER, 64)
                    .input("paneGlass", 64)
                    .input(circuit, MarkerMaterials.Tier.ULV, 64)
                    .input(circuit, MarkerMaterials.Tier.ULV, 64)
                    .input(MetaItems.CARBON_FIBER_PLATE, 64)
                    .output(MetaItems.COVER_SOLAR_PANEL, 64)
                    .duration(20).EUt(VA[EV])
                    .buildAndRegister();

            // Solar Panel (8V)
            RecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .input(MetaItems.COVER_SOLAR_PANEL, 8)
                    .input(Blocks.DAYLIGHT_DETECTOR, 8)
                    .input(MetaItems.NAND_CHIP_ULV, 4)
                    .input(MetaItems.ULTRA_LOW_POWER_INTEGRATED_CIRCUIT, 4)
                    .input(Blocks.GLASS)
                    .input(MetaTileEntities.TRANSFORMER[ULV])
                    .fluidInputs(Materials.Silicon.getFluid(L))
                    .fluidInputs(Materials.SolderingAlloy.getFluid(L))
                    .output(MetaItems.COVER_SOLAR_PANEL_ULV)
                    .duration(100).EUt(VA[LuV])
                    .stationResearch(b -> b.researchStack(MetaItems.COVER_SOLAR_PANEL.getStackForm())
                            .CWUt(32).EUt(VA[LuV]))
                    .buildAndRegister();

            // Solar Panel (LV)
            RecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .input(MetaItems.COVER_SOLAR_PANEL_ULV, 4)
                    .input(MetaItems.SENSOR_LV, 8)
                    .input(MetaItems.INTEGRATED_CIRCUIT_LV, 4)
                    .input(MetaItems.ULTRA_LOW_POWER_INTEGRATED_CIRCUIT, 8)
                    .inputs(Mods.AppliedEnergistics2.isModLoaded() ?
                            Mods.AppliedEnergistics2.getItem("quartz_glass") :
                            new ItemStack(Blocks.GLASS))
                    .input(MetaTileEntities.TRANSFORMER[LV])
                    .fluidInputs(Materials.Silicon.getFluid(L << 2))
                    .fluidInputs(Materials.SolderingAlloy.getFluid(L << 2))
                    .output(MetaItems.COVER_SOLAR_PANEL_LV)
                    .duration(100).EUt(VA[LuV])
                    .stationResearch(b -> b.researchStack(MetaItems.COVER_SOLAR_PANEL_ULV.getStackForm())
                            .CWUt(32).EUt(VA[LuV]))
                    .buildAndRegister();

            // Solar Panel (MV)
            RecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .input(MetaItems.COVER_SOLAR_PANEL_LV, 4)
                    .input(MetaItems.SENSOR_MV, 8)
                    .input(MetaItems.INTEGRATED_CIRCUIT_MV, 4)
                    .input(MetaItems.LOW_POWER_INTEGRATED_CIRCUIT, 4)
                    .inputs(Mods.EnderIO.isModLoaded() ?
                            GTEUtility.getModItem(Mods.Names.ENDER_IO, "block_fused_quartz") :
                            new ItemStack(Blocks.GLASS))
                    .input(MetaTileEntities.TRANSFORMER[MV])
                    .fluidInputs(Materials.Silicon.getFluid(L << 3))
                    .fluidInputs(Materials.SolderingAlloy.getFluid(L << 3))
                    .output(MetaItems.COVER_SOLAR_PANEL_MV)
                    .duration(100).EUt(VA[LuV])
                    .stationResearch(b -> b.researchStack(MetaItems.COVER_SOLAR_PANEL_LV.getStackForm())
                            .CWUt(32).EUt(VA[LuV]))
                    .buildAndRegister();

            // Solar Panel (HV)
            RecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .input(MetaItems.COVER_SOLAR_PANEL_MV, 4)
                    .input(MetaItems.SENSOR_HV, 8)
                    .input(MetaItems.INTEGRATED_CIRCUIT_HV, 4)
                    .input(MetaItems.LOW_POWER_INTEGRATED_CIRCUIT, 8)
                    .inputs(Mods.EnderIO.isModLoaded() ?
                            GTEUtility.getModItem(Mods.Names.ENDER_IO, "block_fused_quartz") :
                            MetaBlocks.TRANSPARENT_CASING.getItemVariant(BlockGlassCasing.CasingType.TEMPERED_GLASS))
                    .input(MetaTileEntities.TRANSFORMER[HV])
                    .fluidInputs(Materials.Silicon.getFluid(L << 4))
                    .fluidInputs(Materials.SolderingAlloy.getFluid(L << 4))
                    .output(MetaItems.COVER_SOLAR_PANEL_HV)
                    .duration(100).EUt(VA[LuV])
                    .stationResearch(b -> b.researchStack(MetaItems.COVER_SOLAR_PANEL_MV.getStackForm())
                            .CWUt(48).EUt(VA[LuV]))
                    .buildAndRegister();

            // Solar Panel (EV)
            RecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .input(MetaItems.COVER_SOLAR_PANEL_HV, 4)
                    .input(MetaItems.SENSOR_EV, 8)
                    .input(MetaItems.WORKSTATION_EV, 4)
                    .input(MetaItems.POWER_INTEGRATED_CIRCUIT, 4)
                    .inputs(MetaBlocks.TRANSPARENT_CASING.getItemVariant(BlockGlassCasing.CasingType.TEMPERED_GLASS))
                    .input(MetaTileEntities.TRANSFORMER[EV])
                    .fluidInputs(Materials.Silicon.getFluid(L << 5))
                    .fluidInputs(Materials.SolderingAlloy.getFluid(L << 5))
                    .output(MetaItems.COVER_SOLAR_PANEL_EV)
                    .duration(100).EUt(VA[LuV])
                    .stationResearch(b -> b.researchStack(MetaItems.COVER_SOLAR_PANEL_HV.getStackForm())
                            .CWUt(48).EUt(VA[LuV]))
                    .buildAndRegister();

            if (!ConfigHolder.machines.enableHighTierSolars) return;

            // Solar Panel (IV)
            RecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .input(MetaItems.COVER_SOLAR_PANEL_EV, 4)
                    .input(MetaItems.SENSOR_IV, 8)
                    .input(MetaItems.MAINFRAME_IV, 4)
                    .input(MetaItems.POWER_INTEGRATED_CIRCUIT, 8)
                    .inputs(MetaBlocks.TRANSPARENT_CASING.getItemVariant(BlockGlassCasing.CasingType.LAMINATED_GLASS))
                    .input(MetaTileEntities.TRANSFORMER[IV])
                    .fluidInputs(Materials.Silicon.getFluid(L << 6))
                    .fluidInputs(Materials.SolderingAlloy.getFluid(L << 6))
                    .output(MetaItems.COVER_SOLAR_PANEL_IV)
                    .duration(100).EUt(VA[LuV])
                    .stationResearch(b -> b.researchStack(MetaItems.COVER_SOLAR_PANEL_EV.getStackForm())
                            .CWUt(48).EUt(VA[LuV]))
                    .buildAndRegister();

            // Solar Panel (LuV)
            RecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .input(MetaItems.COVER_SOLAR_PANEL_IV, 4)
                    .input(MetaItems.SENSOR_LuV, 8)
                    .input(MetaItems.NANO_MAINFRAME_LUV, 4)
                    .input(MetaItems.HIGH_POWER_INTEGRATED_CIRCUIT, 8)
                    .inputs(MetaBlocks.TRANSPARENT_CASING.getItemVariant(BlockGlassCasing.CasingType.LAMINATED_GLASS))
                    .input(MetaTileEntities.TRANSFORMER[LuV])
                    .fluidInputs(Materials.Silicon.getFluid(L << 7))
                    .fluidInputs(Materials.SolderingAlloy.getFluid(L << 7))
                    .output(MetaItems.COVER_SOLAR_PANEL_LUV)
                    .duration(100).EUt(VA[LuV])
                    .stationResearch(b -> b.researchStack(MetaItems.COVER_SOLAR_PANEL_IV.getStackForm())
                            .CWUt(64).EUt(VA[LuV]))
                    .buildAndRegister();

            // Solar Panel (ZPM)
            RecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .input(MetaItems.COVER_SOLAR_PANEL_LUV, 4)
                    .input(MetaItems.SENSOR_ZPM, 8)
                    .input(MetaItems.QUANTUM_MAINFRAME_ZPM, 4)
                    .input(MetaItems.HIGH_POWER_INTEGRATED_CIRCUIT, 16)
                    .inputs(MetaBlocks.TRANSPARENT_CASING.getItemVariant(BlockGlassCasing.CasingType.FUSION_GLASS))
                    .input(MetaTileEntities.TRANSFORMER[ZPM])
                    .fluidInputs(Materials.Silicon.getFluid(L << 8))
                    .fluidInputs(Materials.SolderingAlloy.getFluid(L << 8))
                    .output(MetaItems.COVER_SOLAR_PANEL_ZPM)
                    .duration(100).EUt(VA[ZPM])
                    .stationResearch(b -> b.researchStack(MetaItems.COVER_SOLAR_PANEL_LUV.getStackForm())
                            .CWUt(64).EUt(VA[LuV]))
                    .buildAndRegister();

            // Solar Panel (UV)
            RecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .input(MetaItems.COVER_SOLAR_PANEL_ZPM, 4)
                    .input(MetaItems.SENSOR_UV, 8)
                    .input(MetaItems.CRYSTAL_MAINFRAME_UV, 4)
                    .input(MetaItems.ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT, 32)
                    .inputs(MetaBlocks.TRANSPARENT_CASING.getItemVariant(BlockGlassCasing.CasingType.FUSION_GLASS))
                    .input(MetaTileEntities.TRANSFORMER[UV])
                    .fluidInputs(Materials.Silicon.getFluid(L << 9))
                    .fluidInputs(Materials.SolderingAlloy.getFluid(L << 9))
                    .output(MetaItems.COVER_SOLAR_PANEL_UV)
                    .duration(100).EUt(VA[UV])
                    .stationResearch(b -> b.researchStack(MetaItems.COVER_SOLAR_PANEL_ZPM.getStackForm())
                            .CWUt(64).EUt(VA[LuV]))
                    .buildAndRegister();
        } else {
            // Solar Panel (8V)
            ModHandler.addShapedRecipe("solar_panel_basic_gt5u",
                    MetaItems.COVER_SOLAR_PANEL_ULV.getStackForm(), "SSS", "SCS", "SSS",
                    'S', MetaItems.COVER_SOLAR_PANEL,
                    'C', new UnificationEntry(circuit, MarkerMaterials.Tier.HV));

            // Solar Panel (LV)
            ModHandler.addShapedRecipe("solar_panel_lv_gt5u",
                    MetaItems.COVER_SOLAR_PANEL_LV.getStackForm(), " S ", "SCS", " S ",
                    'S', MetaItems.COVER_SOLAR_PANEL_ULV,
                    'C', MetaTileEntities.TRANSFORMER[0].getStackForm());

            // Solar Panel (MV)
            ModHandler.addShapedRecipe("solar_panel_mv_gt5u",
                    MetaItems.COVER_SOLAR_PANEL_MV.getStackForm(), " S ", "SCS", " S ",
                    'S', MetaItems.COVER_SOLAR_PANEL_LV,
                    'C', MetaTileEntities.TRANSFORMER[LV].getStackForm());

            // Solar Panel (HV)
            ModHandler.addShapedRecipe("solar_panel_hv_gt5u",
                    MetaItems.COVER_SOLAR_PANEL_HV.getStackForm(), " S ", "SCS", " S ",
                    'S', MetaItems.COVER_SOLAR_PANEL_MV,
                    'C', MetaTileEntities.TRANSFORMER[MV].getStackForm());

            // Solar Panel (EV)
            ModHandler.addShapedRecipe("solar_panel_ev_gt5u",
                    MetaItems.COVER_SOLAR_PANEL_EV.getStackForm(), " S ", "SCS", " S ",
                    'S', MetaItems.COVER_SOLAR_PANEL_HV,
                    'C', MetaTileEntities.TRANSFORMER[HV].getStackForm());

            if (!ConfigHolder.machines.enableHighTierSolars) return;

            // Solar Panel (IV)
            ModHandler.addShapedRecipe("solar_panel_iv_gt5u",
                    MetaItems.COVER_SOLAR_PANEL_IV.getStackForm(), " S ", "SCS", " S ",
                    'S', MetaItems.COVER_SOLAR_PANEL_EV,
                    'C', MetaTileEntities.TRANSFORMER[EV].getStackForm());

            // Solar Panel (LuV)
            ModHandler.addShapedRecipe("solar_panel_luv_gt5u",
                    MetaItems.COVER_SOLAR_PANEL_LUV.getStackForm(), " S ", "SCS", " S ",
                    'S', MetaItems.COVER_SOLAR_PANEL_IV,
                    'C', MetaTileEntities.TRANSFORMER[IV].getStackForm());

            // Solar Panel (ZPM)
            ModHandler.addShapedRecipe("solar_panel_zpm_gt5u",
                    MetaItems.COVER_SOLAR_PANEL_ZPM.getStackForm(), " S ", "SCS", " S ",
                    'S', MetaItems.COVER_SOLAR_PANEL_LUV,
                    'C', MetaTileEntities.TRANSFORMER[LuV].getStackForm());

            // Solar Panel (UV)
            ModHandler.addShapedRecipe("solar_panel_uv_gt5u",
                    MetaItems.COVER_SOLAR_PANEL_UV.getStackForm(), " S ", "SCS", " S ",
                    'S', MetaItems.COVER_SOLAR_PANEL_ZPM,
                    'C', MetaTileEntities.TRANSFORMER[ZPM].getStackForm());
        }
    }

    private static void blocks() {
        // Large Rock Breaker
        ModHandler.addShapedRecipe(true, "gtexpert.machine.large_rock_breaker",
                GTEMetaTileEntities.LARGE_ROCK_BREAKER.getStackForm(), "CGC", "CFC", "CGC",
                'G', new UnificationEntry(gear, Materials.TungstenSteel),
                'F', MetaTileEntities.ROCK_BREAKER[EV].getStackForm(),
                'C', MetaBlocks.METAL_CASING.getItemVariant(BlockMetalCasing.MetalCasingType.STEEL_SOLID));

        // Large Oil Cracking Unit
        ModHandler.addShapedRecipe(true, "gtexpert.machine.large_oil_cracking_unit",
                GTEMetaTileEntities.LARGE_CRACKER.getStackForm(), "PCP", "FSF", "PCP",
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.ZPM),
                'S', MetaTileEntities.CRACKER.getStackForm(),
                'P', MetaItems.ELECTRIC_PUMP_IV.getStackForm(),
                'F', MetaItems.FIELD_GENERATOR_IV.getStackForm());

        // Advanced Chemical Plant
        RecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(MetaTileEntities.LARGE_CHEMICAL_REACTOR, 1)
                .input(foil, Materials.Polybenzimidazole, 32)
                .input(plate, Materials.IndiumTinBariumTitaniumCuprate, 32)
                .input(stickLong, Materials.Cupronickel, 32)
                .input(pipeLargeFluid, Materials.Polytetrafluoroethylene, 8)
                .input(circuit, MarkerMaterials.Tier.LuV, 4)
                .input(MetaItems.ELECTRIC_MOTOR_LuV, 4)
                .fluidInputs(Materials.SolderingAlloy.getFluid(2304))
                .fluidInputs(Materials.Polytetrafluoroethylene.getFluid(2304))
                .output(GTEMetaTileEntities.ADVANCED_CHEMICAL_PLANT)
                .duration(200).EUt(VA[LuV])
                .buildAndRegister();

        // Large Gas Collector
        RecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(MetaTileEntities.GAS_COLLECTOR[ZPM], 1)
                .input(plate, GTEMaterials.NM_HEA_NPs, 8)
                .input(circuit, MarkerMaterials.Tier.ZPM, 16)
                .input(MetaItems.SENSOR_ZPM, 4)
                .input(MetaItems.FLUID_REGULATOR_ZPM, 4)
                .input(MetaItems.FIELD_GENERATOR_ZPM, 4)
                .fluidInputs(Materials.SolderingAlloy.getFluid(2304))
                .fluidInputs(Materials.Polytetrafluoroethylene.getFluid(2304))
                .output(GTEMetaTileEntities.LARGE_GAS_COLLECTOR)
                .duration(200).EUt(VA[ZPM])
                .buildAndRegister();

        // Void Ore Miner
        AssemblyLineRecipeBuilder builderVOM = RecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(MetaTileEntities.ADVANCED_LARGE_MINER)
                .fluidInputs(Materials.SolderingAlloy.getFluid(18432))
                .output(GTEMetaTileEntities.VOIDOREMINER);
        if (GTEValues.isModLoadedDEDA()) {
            builderVOM.inputs(Mods.DraconicEvolution.getItem("awakened_core", 4, 0));
            builderVOM.input(MetaItems.ELECTRIC_MOTOR_UV, 4);
            builderVOM.input(MetaItems.ELECTRIC_PUMP_UV, 4);
            builderVOM.input(MetaItems.CONVEYOR_MODULE_UV, 4);
            builderVOM.input(MetaItems.ELECTRIC_PISTON_UV, 4);
            builderVOM.input(MetaItems.ROBOT_ARM_UV, 4);
            builderVOM.input(MetaItems.EMITTER_UV, 4);
            builderVOM.input(MetaItems.SENSOR_UV, 4);
            builderVOM.duration(600).EUt(VA[UV]);
            builderVOM.stationResearch(
                    b -> b.researchStack(MetaTileEntities.ADVANCED_LARGE_MINER.getStackForm()).CWUt(96).EUt(VA[UV]));
        } else {
            builderVOM.input(circuit, MarkerMaterials.Tier.ZPM, 4);
            builderVOM.input(MetaItems.ELECTRIC_MOTOR_ZPM, 4);
            builderVOM.input(MetaItems.ELECTRIC_PUMP_ZPM, 4);
            builderVOM.input(MetaItems.CONVEYOR_MODULE_ZPM, 4);
            builderVOM.input(MetaItems.ELECTRIC_PISTON_ZPM, 4);
            builderVOM.input(MetaItems.ROBOT_ARM_ZPM, 4);
            builderVOM.input(MetaItems.EMITTER_ZPM, 4);
            builderVOM.input(MetaItems.SENSOR_ZPM, 4);
            builderVOM.duration(600).EUt(VA[ZPM]);
            builderVOM.stationResearch(
                    b -> b.researchStack(MetaTileEntities.ADVANCED_LARGE_MINER.getStackForm()).CWUt(64).EUt(VA[ZPM]));
        }
        builderVOM.input(MetaItems.ORE_DICTIONARY_FILTER);
        builderVOM.input(gear, Materials.NaquadahAlloy, 4);
        builderVOM.buildAndRegister();

        // Void Ore Miner Recipes
        List<Material> materials = new ArrayList<>(GregTechAPI.materialManager.getRegisteredMaterials());
        materials.forEach(GTERecipe::voidOreMiner);

        // Void Ore Miner Casing
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.ZPM))
                .input(MetaItems.COVER_FLUID_VOIDING_ADVANCED)
                .input(MetaItems.VOLTAGE_COIL_ZPM, 2)
                .input(MetaItems.FIELD_GENERATOR_ZPM)
                .input(plate, GTEMaterials.NM_HEA_NPs, 6)
                .fluidInputs(Materials.EnderPearl.getFluid(GTValues.L << 2))
                .outputs(GTEMetaBlocks.GTE_METAL_CASING
                        .getItemVariant(GTEBlockMetalCasing.MetalCasingType.VOID_ORE_MINER,
                                ConfigHolder.recipes.casingsPerCraft))
                .duration(100).EUt(VA[ZPM])
                .buildAndRegister();

        // Large Adjustable Transformer
        ModHandler.addShapedRecipe(true, "gtexpert.machine.large_transformer",
                GTEMetaTileEntities.LARGE_TRANSFORMER.getStackForm(), "DPD", "CHC", "DPD",
                'D', MetaItems.SMD_DIODE,
                'P', new UnificationEntry(plate, Materials.Palladium),
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.EV),
                'H', MetaTileEntities.HULL[EV].getStackForm());

        // Void Fluid Pump
        AssemblyLineRecipeBuilder builderVFP = RecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(MetaTileEntities.ADVANCED_FLUID_DRILLING_RIG)
                .fluidInputs(Materials.SolderingAlloy.getFluid(18432))
                .output(GTEMetaTileEntities.VOID_FLUID_PUMP);
        if (GTEValues.isModLoadedDEDA()) {
            builderVFP.inputs(Mods.DraconicEvolution.getItem("awakened_core", 4, 0))
                    .input(MetaItems.ELECTRIC_MOTOR_UV, 4)
                    .input(MetaItems.ELECTRIC_PUMP_UV, 4)
                    .input(MetaItems.CONVEYOR_MODULE_UV, 4)
                    .input(MetaItems.ELECTRIC_PISTON_UV, 4)
                    .input(MetaItems.ROBOT_ARM_UV, 4)
                    .input(MetaItems.EMITTER_UV, 4)
                    .input(MetaItems.SENSOR_UV, 4)
                    .duration(600).EUt(VA[UV])
                    .stationResearch(
                            b -> b.researchStack(MetaTileEntities.ADVANCED_FLUID_DRILLING_RIG.getStackForm()).CWUt(96)
                                    .EUt(VA[UV]));
        } else {
            builderVFP.input(circuit, MarkerMaterials.Tier.ZPM, 4)
                    .input(MetaItems.ELECTRIC_MOTOR_ZPM, 4)
                    .input(MetaItems.ELECTRIC_PUMP_ZPM, 4)
                    .input(MetaItems.CONVEYOR_MODULE_ZPM, 4)
                    .input(MetaItems.ELECTRIC_PISTON_ZPM, 4)
                    .input(MetaItems.ROBOT_ARM_ZPM, 4)
                    .input(MetaItems.EMITTER_ZPM, 4)
                    .input(MetaItems.SENSOR_ZPM, 4)
                    .duration(600).EUt(VA[ZPM])
                    .stationResearch(
                            b -> b.researchStack(MetaTileEntities.ADVANCED_FLUID_DRILLING_RIG.getStackForm()).CWUt(64)
                                    .EUt(VA[ZPM]));
        }
        builderVFP.input(MetaItems.FLUID_FILTER)
                .input(gear, Materials.NaquadahAlloy, 4)
                .buildAndRegister();
    }

    private static void tools() {
        // Piston Boots
        ModHandler.addShapedRecipe(true, "piston_boots",
                GTEMetaItems.PISTON_BOOTS.getStackForm(), "EhE", "RLR", "PBP",
                'E', Items.LEATHER,
                'R', new UnificationEntry(plate, Materials.Rubber),
                'L', Items.LEATHER_BOOTS,
                'P', MetaItems.ELECTRIC_PISTON_LV,
                'B', MetaItems.BATTERY_LV_SODIUM);
    }

    private static void end_contents() {
        // Infinite GT Energy Unit Emitter
        AssemblyLineRecipeBuilder builderIGTEUE = RecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(MetaTileEntities.HULL[UHV])
                .fluidInputs(Materials.SolderingAlloy.getFluid(18432))
                .fluidInputs(Materials.UraniumRhodiumDinaquadide.getFluid(9216))
                .output(MetaTileEntities.CREATIVE_ENERGY)
                .duration(2000).EUt(VA[UHV]);
        if (!(GTEValues.isModLoadedDEDA() && Mods.AppliedEnergistics2.isModLoaded() &&
                Mods.EnderIO.isModLoaded())) {
            builderIGTEUE.input(MetaItems.ENERGY_CLUSTER, 4);
            builderIGTEUE.input(MetaItems.FIELD_GENERATOR_UV, 4);
            builderIGTEUE.input(circuit, MarkerMaterials.Tier.UV, 16);
        } else {
            if (GTEValues.isModLoadedDEDA()) {
                builderIGTEUE.inputs(Mods.DraconicAdditions.getItem("chaotic_energy_core", 8, 0));
                builderIGTEUE.inputs(Mods.DraconicAdditions.getItem("chaos_stabilizer_core", 8, 0));
            }
            if (Mods.AppliedEnergistics2.isModLoaded()) {
                builderIGTEUE.inputs(Mods.AppliedEnergistics2.getItem("creative_energy_cell", 4, 0));
                builderIGTEUE.stationResearch(
                        b -> b.researchStack(GTEMetaItems.GTE_ME_FAKE_COMPONENT.getStackForm()).CWUt(128).EUt(VA[UHV]));
            }
            if (Mods.EnderIO.isModLoaded()) {
                builderIGTEUE.inputNBT(GTEUtility.getModItem(Mods.Names.ENDER_IO, "block_cap_bank", 4, 3).getItem(),
                        NBTMatcher.ANY, NBTCondition.ANY);
            }
        }
        if (GTEValues.isModLoadedDEDA() && Mods.AppliedEnergistics2.isModLoaded() &&
                Mods.EnderIO.isModLoaded()) {
            builderIGTEUE.input(GTEMetaItems.GTE_ME_FAKE_COMPONENT, 4);
        } else if (Mods.AppliedEnergistics2.isModLoaded() &&
                !(GTEValues.isModLoadedDEDA() && Mods.EnderIO.isModLoaded())) {
                    builderIGTEUE.input(GTEMetaItems.GTE_ME_FAKE_COMPONENT, 4);
                }
        builderIGTEUE.buildAndRegister();

        // Creative Quantum Tank
        RecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(MetaTileEntities.CREATIVE_ENERGY, 4)
                .input(MetaItems.QUANTUM_STAR, 64)
                .input(MetaItems.ELECTRIC_PUMP_UV, 64)
                .input(MetaItems.FLUID_REGULATOR_UV, 64)
                .input(MetaItems.EMITTER_UV, 64)
                .input(MetaItems.WETWARE_MAINFRAME_UHV, 64)
                .fluidInputs(Materials.SolderingAlloy.getFluid(36864))
                .fluidInputs(Materials.RutheniumTriniumAmericiumNeutronate.getFluid(9216))
                .outputs(MetaTileEntities.CREATIVE_TANK.getStackForm())
                .duration(2000).EUt(VA[UHV])
                .stationResearch(b -> b.researchStack(MetaTileEntities.CREATIVE_ENERGY.getStackForm())
                        .CWUt(160).EUt(VA[UHV]))
                .buildAndRegister();

        ModHandler.addShapelessNBTClearingRecipe("creative_quantum_tank_nbt",
                MetaTileEntities.CREATIVE_TANK.getStackForm(),
                MetaTileEntities.CREATIVE_TANK.getStackForm());

        // Creative Quantum Chest
        RecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(MetaTileEntities.CREATIVE_TANK, 8)
                .input(MetaItems.QUANTUM_STAR, 64)
                .input(MetaItems.CONVEYOR_MODULE_UV, 64)
                .input(MetaItems.ROBOT_ARM_UV, 64)
                .input(MetaItems.EMITTER_UV, 64)
                .input(MetaItems.WETWARE_MAINFRAME_UHV, 64)
                .input(MetaItems.NAN_CERTIFICATE)
                .fluidInputs(Materials.SolderingAlloy.getFluid(36864))
                .fluidInputs(Materials.RutheniumTriniumAmericiumNeutronate.getFluid(9216))
                .outputs(MetaTileEntities.CREATIVE_CHEST.getStackForm())
                .stationResearch(b -> b.researchStack(MetaTileEntities.CREATIVE_TANK.getStackForm())
                        .CWUt(160).EUt(VA[UHV]))
                .duration(2000).EUt(VA[UHV])
                .buildAndRegister();

        ModHandler.addShapelessNBTClearingRecipe("creative_quantum_chest_nbt",
                MetaTileEntities.CREATIVE_CHEST.getStackForm(),
                MetaTileEntities.CREATIVE_CHEST.getStackForm());

        // Creative Data Access Hatch
        AssemblyLineRecipeBuilder builderCDAH = RecipeMaps.ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(MetaTileEntities.ADVANCED_DATA_ACCESS_HATCH)
                .input(MetaItems.TOOL_DATA_MODULE, 4)
                .input(MetaItems.WETWARE_MAINFRAME_UHV, 4)
                .fluidInputs(Materials.SolderingAlloy.getFluid(18432))
                .fluidInputs(Materials.UraniumRhodiumDinaquadide.getFluid(9216))
                .outputs(MetaTileEntities.CREATIVE_DATA_HATCH.getStackForm())
                .duration(2000).EUt(VA[UHV])
                .stationResearch(b -> b.researchStack(MetaTileEntities.ADVANCED_DATA_ACCESS_HATCH.getStackForm())
                        .CWUt(160).EUt(VA[UHV]));
        if (Mods.AppliedEnergistics2.isModLoaded()) {
            builderCDAH.input(GTEMetaItems.GTE_ME_FAKE_COMPONENT, 4);
        }
        builderCDAH.buildAndRegister();
    }

    /**
     * Add recipes for the Void Ore Miner
     *
     * @param material The material to add recipes for
     */
    private static void voidOreMiner(@NotNull Material material) {
        // Skip if the material doesn't have an ore
        if (!material.hasProperty(PropertyKey.ORE)) return;
        if (material.hasFlag(MaterialFlags.DISABLE_ORE_BLOCK)) return;

        // Get the ore
        List<ItemStack> ores = OreDictUnifier.getAll(new UnificationEntry(ore, material));
        ItemStack oreStack = ores.get(ores.size() - 1);
        oreStack.setCount(32);
        GTERecipeMaps.VOID_ORE_MINER_RECIPES.recipeBuilder()
                .input(ore, material)
                .fluidInputs(Materials.EnderPearl.getFluid(576))
                .fluidInputs(Materials.DrillingFluid.getFluid(10000))
                .outputs(oreStack)
                .duration(20).EUt(VA[ZPM])
                .buildAndRegister();
        GTERecipeMaps.VOID_ORE_MINER_RECIPES.recipeBuilder()
                .input(oreNetherrack, material)
                .fluidInputs(Materials.EnderPearl.getFluid(576))
                .fluidInputs(Materials.DrillingFluid.getFluid(10000))
                .output(oreNetherrack, material, 64)
                .duration(20).EUt(VA[ZPM])
                .buildAndRegister();
        GTERecipeMaps.VOID_ORE_MINER_RECIPES.recipeBuilder()
                .input(oreEndstone, material)
                .fluidInputs(Materials.EnderPearl.getFluid(576))
                .fluidInputs(Materials.DrillingFluid.getFluid(10000))
                .output(oreEndstone, material, 64)
                .duration(20).EUt(VA[ZPM])
                .buildAndRegister();
    }
}
