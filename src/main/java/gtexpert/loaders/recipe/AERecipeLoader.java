package gtexpert.loaders.recipe;

import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.ingredients.nbtmatch.NBTCondition;
import gregtech.api.recipes.ingredients.nbtmatch.NBTMatcher;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.ConfigHolder;
import gregtech.common.items.MetaItems;

import gtexpert.api.GTEValues;
import gtexpert.common.GTEConfigHolder;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;

import appeng.api.util.AEColor;
import crazypants.enderio.base.init.ModObject;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.util.*;
import java.util.stream.IntStream;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.SHAPE_EMPTY;
import static gtexpert.api.unification.material.GTEMaterials.*;
import static gtexpert.api.util.GTEUtility.getModItem;
import static gtexpert.common.GTEConfigHolder.ae2Integration;
import static gtexpert.common.items.GTEMetaItems.*;
import static gtexpert.integration.ae.AEHelper.*;

public class AERecipeLoader {

    private static final Material[] tierMaterials = new Material[] {
            WroughtIron,
            Steel,
            Aluminium,
            StainlessSteel,
            Titanium,
            TungstenSteel,
            RhodiumPlatedPalladium,
            NaquadahAlloy,
            Darmstadtium,
            Neutronium
    };

    public static void init() {
        materials();
        items();
        blocks();
        tools();
    }

    private static void materials() {
        // Iron Ingot
        ModHandler.removeFurnaceSmelting(aeMaterials.ironDust().maybeStack(1).get());

        // Gold Ingot
        ModHandler.removeFurnaceSmelting(aeMaterials.goldDust().maybeStack(1).get());

        // Pure Certus Quartz Crystal
        ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "misc/seeds_certus"));
        RecipeMaps.MIXER_RECIPES.recipeBuilder()
                .input(dust, CertusQuartz, 1)
                .input("sand", 1)
                .outputs(getModItem(GTEValues.MODID_AE, "crystal_seed", 2, 0))
                .duration(20).EUt(VA[GTEConfigHolder.ae2Integration.voltageTier])
                .buildAndRegister();
        RecipeMaps.AUTOCLAVE_RECIPES.recipeBuilder()
                .inputs(getModItem(GTEValues.MODID_AE, "crystal_seed", 1, 0))
                .fluidInputs(DistilledWater.getFluid(50))
                .outputs(aeMaterials.purifiedCertusQuartzCrystal().maybeStack(1).get())
                .duration(600).EUt(VA[GTEConfigHolder.ae2Integration.voltageTier])
                .buildAndRegister();
        RecipeMaps.AUTOCLAVE_RECIPES.recipeBuilder()
                .inputs(getModItem(GTEValues.MODID_AE, "crystal_seed", 1, 0))
                .fluidInputs(Water.getFluid(250))
                .chancedOutput(aeMaterials.purifiedCertusQuartzCrystal().maybeStack(1).get(), 7000, 1000)
                .duration(1200).EUt(VA[GTEConfigHolder.ae2Integration.voltageTier])
                .buildAndRegister();

        // Pure Nether Quartz Crystal
        ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "misc/seeds_nether"));
        RecipeMaps.MIXER_RECIPES.recipeBuilder()
                .input(dust, NetherQuartz, 1)
                .input("sand", 1)
                .outputs(getModItem(GTEValues.MODID_AE, "crystal_seed", 2, 600))
                .duration(20).EUt(VA[GTEConfigHolder.ae2Integration.voltageTier])
                .buildAndRegister();
        RecipeMaps.AUTOCLAVE_RECIPES.recipeBuilder()
                .inputs(getModItem(GTEValues.MODID_AE, "crystal_seed", 1, 600))
                .fluidInputs(DistilledWater.getFluid(50))
                .outputs(aeMaterials.purifiedNetherQuartzCrystal().maybeStack(1).get())
                .duration(600).EUt(VA[GTEConfigHolder.ae2Integration.voltageTier])
                .buildAndRegister();
        RecipeMaps.AUTOCLAVE_RECIPES.recipeBuilder()
                .inputs(getModItem(GTEValues.MODID_AE, "crystal_seed", 1, 600))
                .fluidInputs(Water.getFluid(250))
                .chancedOutput(aeMaterials.purifiedNetherQuartzCrystal().maybeStack(1).get(), 7000, 1000)
                .duration(1200).EUt(VA[GTEConfigHolder.ae2Integration.voltageTier])
                .buildAndRegister();

        // Pure Fluix Crystal
        ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "misc/seeds_fluix"));
        RecipeMaps.MIXER_RECIPES.recipeBuilder()
                .input(dust, Fluix, 1)
                .input("sand", 1)
                .outputs(getModItem(GTEValues.MODID_AE, "crystal_seed", 2, 1200))
                .duration(20).EUt(VA[GTEConfigHolder.ae2Integration.voltageTier])
                .buildAndRegister();
        RecipeMaps.AUTOCLAVE_RECIPES.recipeBuilder()
                .inputs(getModItem(GTEValues.MODID_AE, "crystal_seed", 1, 1200))
                .fluidInputs(DistilledWater.getFluid(50))
                .outputs(aeMaterials.purifiedFluixCrystal().maybeStack(1).get())
                .duration(600).EUt(VA[GTEConfigHolder.ae2Integration.voltageTier])
                .buildAndRegister();
        RecipeMaps.AUTOCLAVE_RECIPES.recipeBuilder()
                .inputs(getModItem(GTEValues.MODID_AE, "crystal_seed", 1, 1200))
                .fluidInputs(Water.getFluid(250))
                .chancedOutput(aeMaterials.purifiedFluixCrystal().maybeStack(1).get(), 7000, 1000)
                .duration(1200).EUt(VA[GTEConfigHolder.ae2Integration.voltageTier])
                .buildAndRegister();

        // ########################################
        // Sky Stone
        // ########################################
        // Dust
        RecipeMaps.MACERATOR_RECIPES.recipeBuilder()
                .inputs(aeBlocks.skyStoneBlock().maybeStack(1).get())
                .outputs(aeMaterials.skyDust().maybeStack(1).get())
                .duration(500).EUt(VA[GTEConfigHolder.ae2Integration.voltageTier])
                .buildAndRegister();

        // Block
        ModHandler.removeFurnaceSmelting(aeBlocks.skyStoneBlock().maybeStack(1).get());
        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder()
                .input(Item.getItemFromBlock(ModObject.block_infinity.getBlockNN()), 4, 2)
                .outputs(aeBlocks.skyStoneBlock().maybeStack(1).get())
                .duration(500).EUt(VA[GTEConfigHolder.ae2Integration.voltageTier])
                .buildAndRegister();
        RecipeMaps.BLAST_RECIPES.recipeBuilder()
                .inputs(aeBlocks.skyStoneBlock().maybeStack(1).get())
                .outputs(aeBlocks.smoothSkyStoneBlock().maybeStack(1).get())
                .duration(100).EUt(VA[GTEConfigHolder.ae2Integration.voltageTier])
                .blastFurnaceTemp(2700)
                .buildAndRegister();
        RecipeMaps.ROCK_BREAKER_RECIPES.recipeBuilder()
                .notConsumable(aeBlocks.skyStoneBlock().maybeStack(1).get())
                .outputs(aeBlocks.skyStoneBlock().maybeStack(1).get())
                .duration(100).EUt(VA[GTEConfigHolder.ae2Integration.voltageTier])
                .buildAndRegister();

        // ########################################
        // Nether Quartz
        // ########################################
        // Fluid
        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder()
                .inputs(aeMaterials.purifiedNetherQuartzCrystal().maybeStack(1).get())
                .fluidOutputs(NetherQuartz.getFluid(72))
                .duration(14).EUt(VA[LV])
                .buildAndRegister();

        // Dust
        RecipeMaps.MACERATOR_RECIPES.recipeBuilder()
                .inputs(aeMaterials.purifiedNetherQuartzCrystal().maybeStack(1).get())
                .output(dustSmall, NetherQuartz, 2)
                .duration(14).EUt(2)
                .buildAndRegister();

        // Block
        ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "decorative/quartz_block_pure"));
        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder()
                .inputs(aeMaterials.purifiedNetherQuartzCrystal().maybeStack(8).get())
                .output(block, NetherQuartz, 1)
                .duration(300).EUt(2)
                .buildAndRegister();

        // Rod
        RecipeMaps.LATHE_RECIPES.recipeBuilder()
                .inputs(aeMaterials.purifiedNetherQuartzCrystal().maybeStack(1).get())
                .output(stick, NetherQuartz, 1)
                .duration(40).EUt(VH[LV])
                .buildAndRegister();

        // ########################################
        // Certus Quartz
        // ########################################
        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.MACERATOR_RECIPES, OreDictUnifier.get(block, CertusQuartz, 1));
        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.COMPRESSOR_RECIPES, OreDictUnifier.get(gem, CertusQuartz, 9));

        // Fluid
        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder()
                .inputs(aeMaterials.purifiedCertusQuartzCrystal().maybeStack(1).get())
                .fluidOutputs(CertusQuartz.getFluid(72))
                .duration(14).EUt(VA[LV])
                .buildAndRegister();

        // Dust
        RecipeMaps.MACERATOR_RECIPES.recipeBuilder()
                .inputs(aeMaterials.purifiedCertusQuartzCrystal().maybeStack(1).get())
                .output(dustSmall, CertusQuartz, 2)
                .duration(14).EUt(2)
                .buildAndRegister();

        // Block
        ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "decorative/certus_quartz_block"));
        ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "decorative/certus_quartz_block_pure"));
        ModHandler.removeRecipeByOutput(aeMaterials.certusQuartzCrystal().maybeStack(4).get());
        ModHandler.addMirroredShapedRecipe("ae2_certus_quartz_block",
                aeBlocks.quartzBlock().maybeStack(1).get(), "B", 'B',
                new UnificationEntry(block, CertusQuartz));
        ModHandler.addMirroredShapedRecipe("ceu_certus_quartz_block", OreDictUnifier.get(block, CertusQuartz), "B", 'B',
                aeBlocks.quartzBlock().maybeStack(1).get());
        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder()
                .inputs(aeMaterials.purifiedCertusQuartzCrystal().maybeStack(8).get())
                .output(block, CertusQuartz, 1)
                .duration(300).EUt(2)
                .buildAndRegister();

        // Rod
        RecipeMaps.LATHE_RECIPES.recipeBuilder()
                .inputs(aeMaterials.purifiedCertusQuartzCrystal().maybeStack(1).get())
                .output(stick, CertusQuartz, 1)
                .duration(40).EUt(VH[LV])
                .buildAndRegister();

        // ########################################
        // Charged Certus Quartz
        // ########################################
        // Gem
        RecipeMaps.ELECTROLYZER_RECIPES.recipeBuilder()
                .input(gem, CertusQuartz, 1)
                .outputs(aeMaterials.certusQuartzCrystalCharged().maybeStack(1).get())
                .duration(100).EUt(VA[LV])
                .buildAndRegister();
        RecipeMaps.ELECTROLYZER_RECIPES.recipeBuilder()
                .input(dust, CertusQuartz, 1)
                .output(dust, ChargedCertusQuartz, 1)
                .duration(100).EUt(VA[LV])
                .buildAndRegister();
        RecipeMaps.AUTOCLAVE_RECIPES.recipeBuilder()
                .input(dust, ChargedCertusQuartz, 1)
                .fluidInputs(DistilledWater.getFluid(50))
                .outputs(aeMaterials.certusQuartzCrystalCharged().maybeStack(1).get())
                .duration(600).EUt(24)
                .buildAndRegister();
        RecipeMaps.AUTOCLAVE_RECIPES.recipeBuilder()
                .input(dust, ChargedCertusQuartz, 1)
                .fluidInputs(Water.getFluid(250))
                .chancedOutput(aeMaterials.certusQuartzCrystalCharged().maybeStack(1).get(), 7000, 1000)
                .duration(1200).EUt(24)
                .buildAndRegister();
        RecipeMaps.IMPLOSION_RECIPES.recipeBuilder()
                .input(dust, ChargedCertusQuartz, 4)
                .explosivesAmount(2)
                .outputs(aeMaterials.certusQuartzCrystalCharged().maybeStack(3).get())
                .output(dustSmall, DarkAsh, 1)
                .duration(20).EUt(VA[LV])
                .buildAndRegister();
        RecipeMaps.IMPLOSION_RECIPES.recipeBuilder()
                .input(dust, ChargedCertusQuartz, 4)
                .explosivesType(MetaItems.DYNAMITE.getStackForm())
                .outputs(aeMaterials.certusQuartzCrystalCharged().maybeStack(3).get())
                .output(dustSmall, DarkAsh, 1)
                .duration(20).EUt(VA[LV])
                .buildAndRegister();

        // Lens
        RecipeMaps.LATHE_RECIPES.recipeBuilder()
                .input(plate, ChargedCertusQuartz, 1)
                .output(lens, ChargedCertusQuartz, 1)
                .output(dustSmall, ChargedCertusQuartz, 1)
                .duration(1200).EUt(VA[MV])
                .buildAndRegister();

        // ########################################
        // Fluix
        // ########################################
        OreDictionary.registerOre("blockFluix", aeBlocks.fluixBlock().maybeStack(1).get());
        OreDictionary.registerOre("gemFluix", aeMaterials.fluixCrystal().maybeStack(1).get());

        // Fluid
        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder()
                .inputs(aeMaterials.purifiedFluixCrystal().maybeStack(1).get())
                .fluidOutputs(Fluix.getFluid(72))
                .duration(14).EUt(VA[LV])
                .buildAndRegister();

        // Dust
        RecipeMaps.MIXER_RECIPES.recipeBuilder()
                .circuitMeta(1)
                .input(dust, ChargedCertusQuartz, 1)
                .input(dust, Redstone, 1)
                .input(dust, NetherQuartz, 1)
                .output(dust, Fluix, 3)
                .duration(200).EUt(VA[3])
                .buildAndRegister();
        RecipeMaps.MACERATOR_RECIPES.recipeBuilder()
                .inputs(aeMaterials.purifiedFluixCrystal().maybeStack(1).get())
                .output(dustSmall, Fluix, 2)
                .duration(14).EUt(2)
                .buildAndRegister();

        // Gem
        RecipeMaps.AUTOCLAVE_RECIPES.recipeBuilder()
                .input(dust, Fluix, 1)
                .fluidInputs(DistilledWater.getFluid(50))
                .outputs(aeMaterials.fluixCrystal().maybeStack(1).get())
                .duration(600).EUt(24)
                .buildAndRegister();
        RecipeMaps.AUTOCLAVE_RECIPES.recipeBuilder()
                .input(dust, Fluix, 1)
                .fluidInputs(Water.getFluid(250))
                .chancedOutput(aeMaterials.fluixCrystal().maybeStack(1).get(), 7000, 1000)
                .duration(1200).EUt(24)
                .buildAndRegister();
        RecipeMaps.IMPLOSION_RECIPES.recipeBuilder()
                .input(dust, Fluix, 4)
                .explosivesAmount(2)
                .outputs(aeMaterials.fluixCrystal().maybeStack(3).get())
                .output(dustSmall, DarkAsh, 1)
                .duration(20).EUt(VA[LV])
                .buildAndRegister();
        RecipeMaps.IMPLOSION_RECIPES.recipeBuilder()
                .input(dust, Fluix, 4)
                .explosivesType(MetaItems.DYNAMITE.getStackForm())
                .outputs(aeMaterials.fluixCrystal().maybeStack(3).get())
                .output(dustSmall, DarkAsh, 1)
                .duration(20).EUt(VA[LV])
                .buildAndRegister();

        // Block
        ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "decorative/fluix_block"));
        ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "decorative/fluix_block_pure"));
        ModHandler.removeRecipeByOutput(aeMaterials.fluixCrystal().maybeStack(4).get());
        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder()
                .inputs(aeMaterials.fluixCrystal().maybeStack(4).get())
                .outputs(aeBlocks.fluixBlock().maybeStack(1).get())
                .duration(300).EUt(2)
                .buildAndRegister();
        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder()
                .inputs(aeMaterials.purifiedFluixCrystal().maybeStack(8).get())
                .outputs(aeBlocks.fluixBlock().maybeStack(1).get())
                .duration(300).EUt(2)
                .buildAndRegister();

        // Lens
        RecipeMaps.LATHE_RECIPES.recipeBuilder()
                .input(plate, Fluix, 1)
                .output(lens, Fluix, 1)
                .output(dustSmall, Fluix, 1)
                .duration(1200).EUt(VA[MV])
                .buildAndRegister();

        // ########################################
        // Fluix Alloy
        // ########################################
        // Dust
        RecipeMaps.MIXER_RECIPES.recipeBuilder()
                .circuitMeta(2)
                .inputs(aeMaterials.skyDust().maybeStack(2).get())
                .input(dust, Fluix, 2)
                .input(dust, Carbon, 2)
                .input(dust, Silicon, 1)
                .input(dust, Iron, 1)
                .output(dust, FluixAlloy, 8)
                .duration(200).EUt(VA[GTEConfigHolder.ae2Integration.voltageTier])
                .buildAndRegister();
    }

    private static void blocks() {
        // ME Interface
        ModHandler.removeRecipeByOutput(getModItem(GTEValues.MODID_AE, "interface", 1, 0));
        ModHandler.removeRecipeByOutput(getModItem(GTEValues.MODID_AE, "part", 1, 440));
        ModHandler.addShapelessNBTClearingRecipe("interface_to_part_interface",
                getModItem(GTEValues.MODID_AE, "part", 1, 440),
                getModItem(GTEValues.MODID_AE, "interface", 1, 0));
        ModHandler.addShapelessNBTClearingRecipe("part_interface_to_interface",
                getModItem(GTEValues.MODID_AE, "interface", 1, 0),
                getModItem(GTEValues.MODID_AE, "part", 1, 440));

        // ME Fluid Interface
        ModHandler.removeRecipeByOutput(getModItem(GTEValues.MODID_AE, "fluid_interface", 1, 0));
        ModHandler.removeRecipeByOutput(getModItem(GTEValues.MODID_AE, "part", 1, 441));
        ModHandler.addShapelessNBTClearingRecipe("fluid_interface_to_part_fluid_interface",
                getModItem(GTEValues.MODID_AE, "part", 1, 441),
                getModItem(GTEValues.MODID_AE, "fluid_interface", 1, 0));
        ModHandler.addShapelessNBTClearingRecipe("part_fluid_interface_to_fluid_interface",
                getModItem(GTEValues.MODID_AE, "fluid_interface", 1, 0),
                getModItem(GTEValues.MODID_AE, "part", 1, 441));

        // ME Dual Interface
        if (Loader.isModLoaded(GTEValues.MODID_AEFC)) {
            ModHandler.removeRecipeByOutput(getModItem(GTEValues.MODID_AEFC, "dual_interface", 1, 0));
            ModHandler.removeRecipeByOutput(getModItem(GTEValues.MODID_AEFC, "part_dual_interface", 1, 0));
            ModHandler.addShapelessNBTClearingRecipe("fluid_interface_to_part_fluid_interface",
                    getModItem(GTEValues.MODID_AEFC, "part_dual_interface", 1, 0),
                    getModItem(GTEValues.MODID_AEFC, "dual_interface", 1, 0));
            ModHandler.addShapelessNBTClearingRecipe("part_fluid_interface_to_fluid_interface",
                    getModItem(GTEValues.MODID_AEFC, "dual_interface", 1, 0),
                    getModItem(GTEValues.MODID_AEFC, "part_dual_interface", 1, 0));
        }

        // Rubber List
        final Map<Material, Integer> rubberMaterials = new Object2ObjectOpenHashMap<>();
        rubberMaterials.put(Rubber, 432);
        rubberMaterials.put(SiliconeRubber, 216);
        rubberMaterials.put(StyreneButadieneRubber, 108);

        // Quartz Fiber
        ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "network/parts/quartz_fiber_part"));
        ModHandler.addMirroredShapedRecipe("nether_quartz_cutter_wire",
                aeParts.quartzFiber().maybeStack(1).get(), "Px", 'P', OreDictUnifier.get(plate, NetherQuartz));
        ModHandler.addMirroredShapedRecipe("certus_quartz_cutter_wire",
                aeParts.quartzFiber().maybeStack(1).get(), "Px", 'P', OreDictUnifier.get(plate, CertusQuartz));
        ModHandler.addMirroredShapedRecipe("quartzite_cutter_wire", aeParts.quartzFiber().maybeStack(1).get(),
                "Px", 'P', OreDictUnifier.get(plate, Quartzite));
        RecipeMaps.WIREMILL_RECIPES.recipeBuilder()
                .circuitMeta(1)
                .input("craftStickQuartz", 1)
                .outputs(aeParts.quartzFiber().maybeStack(2).get())
                .duration(20).EUt(VA[GTEConfigHolder.ae2Integration.voltageTier])
                .buildAndRegister();

        // Glass Cable
        ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "network/cables/glass_fluix"));
        ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "network/cables/glass_fluix_clean"));
        ModHandler.addShapedRecipe("fluix_glass_cable", aeParts.cableGlass().stack(AEColor.TRANSPARENT, 6),
                "SFS", "CCC", "SFS",
                'S', OreDictUnifier.get(stick, tierMaterials[GTEConfigHolder.ae2Integration.voltageTier - 1]),
                'F', OreDictUnifier.get(dust, Fluix),
                'C', aeParts.quartzFiber().maybeStack(1).get());
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(1)
                .input(stick, tierMaterials[GTEConfigHolder.ae2Integration.voltageTier - 1], 2)
                .inputs(aeParts.quartzFiber().maybeStack(3).get())
                .fluidInputs(Fluix.getFluid(144))
                .outputs(aeParts.cableGlass().stack(AEColor.TRANSPARENT, 6))
                .duration(20).EUt(VA[GTEConfigHolder.ae2Integration.voltageTier])
                .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(1)
                .input(stick, tierMaterials[GTEConfigHolder.ae2Integration.voltageTier - 1], 2)
                .inputs(aeParts.quartzFiber().maybeStack(3).get())
                .input(dust, Fluix, 1)
                .outputs(aeParts.cableGlass().stack(AEColor.TRANSPARENT, 6))
                .duration(20).EUt(VA[GTEConfigHolder.ae2Integration.voltageTier])
                .buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input("craftGlassCableColors", 1)
                .fluidInputs(Chlorine.getFluid(25))
                .outputs(getModItem(GTEValues.MODID_AE, "part", 1, 16))
                .duration(20).EUt(VA[ULV])
                .buildAndRegister();
        IntStream.range(0, CHEMICAL_DYES.length).forEach(i -> RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input("craftGlassCable")
                .fluidInputs(CHEMICAL_DYES[i].getFluid(18))
                .outputs(getModItem(GTEValues.MODID_AE, "part", 1, i))
                .duration(20).EUt(VA[ULV])
                .buildAndRegister());

        // Covered Cable
        ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "network/cables/covered_fluix"));
        ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "network/cables/covered_fluix_clean"));
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(aeParts.cableDenseCovered().stack(AEColor.TRANSPARENT, 1))
                .outputs(aeParts.cableCovered().stack(AEColor.TRANSPARENT, 4))
                .duration(100).EUt(VA[ULV])
                .buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input("craftCoveredCableColors", 1)
                .fluidInputs(Chlorine.getFluid(25))
                .outputs(getModItem(GTEValues.MODID_AE, "part", 1, 36))
                .duration(20).EUt(VA[ULV])
                .buildAndRegister();
        IntStream.range(0, CHEMICAL_DYES.length).forEach(i -> RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input("craftCoveredCable")
                .fluidInputs(CHEMICAL_DYES[i].getFluid(18))
                .outputs(getModItem(GTEValues.MODID_AE, "part", 1, 20 + i))
                .duration(20).EUt(VA[ULV])
                .buildAndRegister());
        for (Map.Entry<Material, Integer> materialEntry : rubberMaterials.entrySet()) {
            Material material = materialEntry.getKey();
            Integer materialAmount = materialEntry.getValue();
            ModHandler.addShapedRecipe(material.equals(Rubber), "fluix_covered_cable_" + material.getName(),
                    aeParts.cableCovered().stack(AEColor.TRANSPARENT, 1),
                    "RRR", "CCC", "RRR",
                    'R', new UnificationEntry(plate, material),
                    'C', "craftGlassCable");
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                    .circuitMeta(1)
                    .input("craftGlassCable", 3)
                    .fluidInputs(material.getFluid(materialAmount))
                    .outputs(aeParts.cableCovered().stack(AEColor.TRANSPARENT, 1))
                    .duration(20).EUt(VA[GTEConfigHolder.ae2Integration.voltageTier])
                    .buildAndRegister();
        }

        // Smart Cable
        ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "network/cables/smart_fluix"));
        ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "network/cables/smart_fluix_clean"));
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(aeParts.cableDenseSmart().stack(AEColor.TRANSPARENT, 1))
                .outputs(aeParts.cableSmart().stack(AEColor.TRANSPARENT, 4))
                .duration(100).EUt(VA[ULV])
                .buildAndRegister();
        ModHandler.addShapedRecipe("fluix_smart_cable_1", aeParts.cableSmart().stack(AEColor.TRANSPARENT, 1),
                " G ", "RCR", " G ",
                'G', OreDictUnifier.get(dust, Glowstone),
                'R', OreDictUnifier.get(dust, Redstone),
                'C', aeParts.cableCovered().stack(AEColor.TRANSPARENT, 1));
        ModHandler.addShapedRecipe("fluix_smart_cable_2", aeParts.cableSmart().stack(AEColor.TRANSPARENT, 1),
                " R ", "GCG", " R ",
                'G', OreDictUnifier.get(dust, Glowstone),
                'R', OreDictUnifier.get(dust, Redstone),
                'C', aeParts.cableCovered().stack(AEColor.TRANSPARENT, 1));
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input("craftSmartCableColors", 1)
                .fluidInputs(Chlorine.getFluid(25))
                .outputs(getModItem(GTEValues.MODID_AE, "part", 1, 56))
                .duration(20).EUt(VA[ULV])
                .buildAndRegister();
        IntStream.range(0, CHEMICAL_DYES.length).forEach(i -> RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input("craftSmartCable")
                .fluidInputs(CHEMICAL_DYES[i].getFluid(18))
                .outputs(getModItem(GTEValues.MODID_AE, "part", 1, 40 + i))
                .duration(20).EUt(VA[ULV])
                .buildAndRegister());
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(2)
                .input("craftCoveredCable", 1)
                .input(dust, Glowstone, 1)
                .input(dust, Redstone, 1)
                .outputs(aeParts.cableSmart().stack(AEColor.TRANSPARENT, 1))
                .duration(20).EUt(VA[GTEConfigHolder.ae2Integration.voltageTier])
                .buildAndRegister();
        for (Map.Entry<Material, Integer> materialEntry : rubberMaterials.entrySet()) {
            Material material = materialEntry.getKey();
            Integer materialAmount = materialEntry.getValue();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                    .circuitMeta(2)
                    .input("craftGlassCable", 3)
                    .input(dust, Glowstone, 3)
                    .input(dust, Redstone, 3)
                    .fluidInputs(material.getFluid(materialAmount))
                    .outputs(aeParts.cableSmart().stack(AEColor.TRANSPARENT, 1))
                    .duration(20).EUt(VA[GTEConfigHolder.ae2Integration.voltageTier])
                    .buildAndRegister();
        }

        // Dense Covered Cable
        ModHandler.removeRecipeByName(
                new ResourceLocation(GTEValues.MODID_AE, "network/cables/dense_covered_fluix"));
        ModHandler.removeRecipeByName(
                new ResourceLocation(GTEValues.MODID_AE, "network/cables/dense_covered_fluix_clean"));
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(aeParts.cableCovered().stack(AEColor.TRANSPARENT, 4))
                .outputs(aeParts.cableDenseCovered().stack(AEColor.TRANSPARENT, 1))
                .duration(100).EUt(VA[ULV])
                .buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input("craftDenseCoveredCableColors", 1)
                .fluidInputs(Chlorine.getFluid(25))
                .outputs(getModItem(GTEValues.MODID_AE, "part", 1, 516))
                .duration(20).EUt(VA[ULV])
                .buildAndRegister();
        IntStream.range(0, CHEMICAL_DYES.length).forEach(i -> RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input("craftDenseCoveredCable")
                .fluidInputs(CHEMICAL_DYES[i].getFluid(18))
                .outputs(getModItem(GTEValues.MODID_AE, "part", 1, 500 + i))
                .duration(20).EUt(VA[ULV])
                .buildAndRegister());
        for (Map.Entry<Material, Integer> materialEntry : rubberMaterials.entrySet()) {
            Material material = materialEntry.getKey();
            Integer materialAmount = materialEntry.getValue();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                    .circuitMeta(3)
                    .input("craftGlassCable", 12)
                    .fluidInputs(material.getFluid(materialAmount * 4))
                    .outputs(aeParts.cableDenseCovered().stack(AEColor.TRANSPARENT, 1))
                    .duration(20).EUt(VA[GTEConfigHolder.ae2Integration.voltageTier])
                    .buildAndRegister();
        }

        // Dense Smart Cable
        ModHandler
                .removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "network/cables/dense_smart_fluix"));
        ModHandler
                .removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "network/cables/dense_smart_fluix_clean"));
        ModHandler.addShapedRecipe("fluix_dense_smart_cable_1", aeParts.cableDenseSmart().stack(AEColor.TRANSPARENT, 1),
                " G ", "RCR", " G ",
                'G', OreDictUnifier.get(dust, Glowstone),
                'R', OreDictUnifier.get(dust, Redstone),
                'C', aeParts.cableDenseCovered().stack(AEColor.TRANSPARENT, 1));
        ModHandler.addShapedRecipe("fluix_dense_smart_cable_2", aeParts.cableDenseSmart().stack(AEColor.TRANSPARENT, 1),
                " R ", "GCG", " R ",
                'G', OreDictUnifier.get(dust, Glowstone),
                'R', OreDictUnifier.get(dust, Redstone),
                'C', aeParts.cableDenseCovered().stack(AEColor.TRANSPARENT, 1));
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(aeParts.cableSmart().stack(AEColor.TRANSPARENT, 4))
                .outputs(aeParts.cableDenseSmart().stack(AEColor.TRANSPARENT, 1))
                .duration(100).EUt(VA[ULV])
                .buildAndRegister();
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input("craftDenseSmartCableColors", 1)
                .fluidInputs(Chlorine.getFluid(25))
                .outputs(getModItem(GTEValues.MODID_AE, "part", 1, 76))
                .duration(20).EUt(VA[ULV])
                .buildAndRegister();
        IntStream.range(0, CHEMICAL_DYES.length).forEach(i -> RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input("craftDenseSmartCable")
                .fluidInputs(CHEMICAL_DYES[i].getFluid(18))
                .outputs(getModItem(GTEValues.MODID_AE, "part", 1, 60 + i))
                .duration(20).EUt(VA[ULV])
                .buildAndRegister());
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(4)
                .input("craftDenseCoveredCable", 1)
                .input(dust, Glowstone, 1)
                .input(dust, Redstone, 1)
                .outputs(aeParts.cableDenseSmart().stack(AEColor.TRANSPARENT, 1))
                .duration(20).EUt(VA[GTEConfigHolder.ae2Integration.voltageTier])
                .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(4)
                .input("craftCoveredCable", 4)
                .input(dust, Glowstone, 4)
                .input(dust, Redstone, 4)
                .outputs(aeParts.cableDenseSmart().stack(AEColor.TRANSPARENT, 1))
                .duration(20).EUt(VA[GTEConfigHolder.ae2Integration.voltageTier])
                .buildAndRegister();
        for (Map.Entry<Material, Integer> materialEntry : rubberMaterials.entrySet()) {
            Material material = materialEntry.getKey();
            Integer materialAmount = materialEntry.getValue();
            RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                    .circuitMeta(4)
                    .input("craftGlassCable", 12)
                    .input(dust, Glowstone, 12)
                    .input(dust, Redstone, 12)
                    .fluidInputs(material.getFluid(materialAmount * 4))
                    .outputs(aeParts.cableDenseSmart().stack(AEColor.TRANSPARENT, 1))
                    .duration(20).EUt(VA[GTEConfigHolder.ae2Integration.voltageTier])
                    .buildAndRegister();
        }

        // Crafting Monitor
        ModHandler
                .removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "network/crafting/cpu_crafting_monitor"));
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(aeBlocks.craftingUnit().maybeStack(1).get())
                .inputs(aeParts.storageMonitor().maybeStack(1).get())
                .outputs(aeBlocks.craftingMonitor().maybeStack(1).get())
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(aeBlocks.craftingMonitor().maybeStack(1).get())
                .outputs(aeBlocks.craftingUnit().maybeStack(1).get())
                .outputs(aeParts.storageMonitor().maybeStack(1).get())
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();

        // 1k Crafting Storage
        ModHandler.removeRecipeByName(
                new ResourceLocation(GTEValues.MODID_AE, "network/crafting/cpu_crafting_storage_1k"));
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(aeBlocks.craftingUnit().maybeStack(1).get())
                .inputs(aeMaterials.cell1kPart().maybeStack(1).get())
                .outputs(aeBlocks.craftingStorage1k().maybeStack(1).get())
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(aeBlocks.craftingStorage1k().maybeStack(1).get())
                .outputs(aeBlocks.craftingUnit().maybeStack(1).get())
                .outputs(aeMaterials.cell1kPart().maybeStack(1).get())
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();

        // 4k Crafting Storage
        ModHandler.removeRecipeByName(
                new ResourceLocation(GTEValues.MODID_AE, "network/crafting/cpu_crafting_storage_4k"));
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(aeBlocks.craftingUnit().maybeStack(1).get())
                .inputs(aeMaterials.cell4kPart().maybeStack(1).get())
                .outputs(aeBlocks.craftingStorage4k().maybeStack(1).get())
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(aeBlocks.craftingStorage4k().maybeStack(1).get())
                .outputs(aeBlocks.craftingUnit().maybeStack(1).get())
                .outputs(aeMaterials.cell4kPart().maybeStack(1).get())
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();

        // 16k Crafting Storage
        ModHandler.removeRecipeByName(
                new ResourceLocation(GTEValues.MODID_AE, "network/crafting/cpu_crafting_storage_16k"));
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(aeBlocks.craftingUnit().maybeStack(1).get())
                .inputs(aeMaterials.cell16kPart().maybeStack(1).get())
                .outputs(aeBlocks.craftingStorage16k().maybeStack(1).get())
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(aeBlocks.craftingStorage16k().maybeStack(1).get())
                .outputs(aeBlocks.craftingUnit().maybeStack(1).get())
                .outputs(aeMaterials.cell16kPart().maybeStack(1).get())
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();

        // 64k Crafting Storage
        ModHandler.removeRecipeByName(
                new ResourceLocation(GTEValues.MODID_AE, "network/crafting/cpu_crafting_storage_64k"));
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(aeBlocks.craftingUnit().maybeStack(1).get())
                .inputs(aeMaterials.cell64kPart().maybeStack(1).get())
                .outputs(aeBlocks.craftingStorage64k().maybeStack(1).get())
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(aeBlocks.craftingStorage64k().maybeStack(1).get())
                .outputs(aeBlocks.craftingUnit().maybeStack(1).get())
                .outputs(aeMaterials.cell64kPart().maybeStack(1).get())
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();

        if (Loader.isModLoaded(GTEValues.MODID_AEA) && Loader.isModLoaded(GTEValues.MODID_EXCPU)) {
            // 256k Crafting Storage
            ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_EXCPU, "crafting_storage_256k"));
            RecipeMaps.PACKER_RECIPES.recipeBuilder()
                    .inputs(aeBlocks.craftingUnit().maybeStack(1).get())
                    .inputs(getModItem(GTEValues.MODID_AEA, "storage.component", 1, 0))
                    .outputs(getModItem(GTEValues.MODID_EXCPU, "crafting_storage_256k", 1, 0))
                    .duration(10).EUt(VA[ULV])
                    .buildAndRegister();
            RecipeMaps.PACKER_RECIPES.recipeBuilder()
                    .inputs(getModItem(GTEValues.MODID_EXCPU, "crafting_storage_256k", 1, 0))
                    .outputs(aeBlocks.craftingUnit().maybeStack(1).get())
                    .outputs(getModItem(GTEValues.MODID_AEA, "storage.component", 1, 0))
                    .duration(10).EUt(VA[ULV])
                    .buildAndRegister();

            // 1024k Crafting Storage
            ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_EXCPU, "crafting_storage_1024k"));
            RecipeMaps.PACKER_RECIPES.recipeBuilder()
                    .inputs(aeBlocks.craftingUnit().maybeStack(1).get())
                    .inputs(getModItem(GTEValues.MODID_AEA, "storage.component", 1, 1))
                    .outputs(getModItem(GTEValues.MODID_EXCPU, "crafting_storage_1024k", 1, 0))
                    .duration(10).EUt(VA[ULV])
                    .buildAndRegister();
            RecipeMaps.PACKER_RECIPES.recipeBuilder()
                    .inputs(getModItem(GTEValues.MODID_EXCPU, "crafting_storage_1024k", 1, 0))
                    .outputs(aeBlocks.craftingUnit().maybeStack(1).get())
                    .outputs(getModItem(GTEValues.MODID_AEA, "storage.component", 1, 1))
                    .duration(10).EUt(VA[ULV])
                    .buildAndRegister();

            // 4096k Crafting Storage
            ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_EXCPU, "crafting_storage_4096k"));
            RecipeMaps.PACKER_RECIPES.recipeBuilder()
                    .inputs(aeBlocks.craftingUnit().maybeStack(1).get())
                    .inputs(getModItem(GTEValues.MODID_AEA, "storage.component", 1, 2))
                    .outputs(getModItem(GTEValues.MODID_EXCPU, "crafting_storage_4096k", 1, 0))
                    .duration(10).EUt(VA[ULV])
                    .buildAndRegister();
            RecipeMaps.PACKER_RECIPES.recipeBuilder()
                    .inputs(getModItem(GTEValues.MODID_EXCPU, "crafting_storage_4096k", 1, 0))
                    .outputs(aeBlocks.craftingUnit().maybeStack(1).get())
                    .outputs(getModItem(GTEValues.MODID_AEA, "storage.component", 1, 2))
                    .duration(10).EUt(VA[ULV])
                    .buildAndRegister();

            // 16384k Crafting Storage
            ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_EXCPU, "crafting_storage_16384k"));
            RecipeMaps.PACKER_RECIPES.recipeBuilder()
                    .inputs(aeBlocks.craftingUnit().maybeStack(1).get())
                    .inputs(getModItem(GTEValues.MODID_AEA, "storage.component", 1, 3))
                    .outputs(getModItem(GTEValues.MODID_EXCPU, "crafting_storage_16384k", 1, 0))
                    .duration(10).EUt(VA[ULV])
                    .buildAndRegister();
            RecipeMaps.PACKER_RECIPES.recipeBuilder()
                    .inputs(getModItem(GTEValues.MODID_EXCPU, "crafting_storage_16384k", 1, 0))
                    .outputs(aeBlocks.craftingUnit().maybeStack(1).get())
                    .outputs(getModItem(GTEValues.MODID_AEA, "storage.component", 1, 3))
                    .duration(10).EUt(VA[ULV])
                    .buildAndRegister();
        }
    }

    private static void items() {
        // Remove Inscriber Recipes
        // Stream.of(aeMaterials.logicProcessorPrint(), aeMaterials.calcProcessorPrint(),
        // aeMaterials.engProcessorPrint(),
        // aeMaterials.logicProcessor(), aeMaterials.calcProcessor(), aeMaterials.engProcessor(),
        // aeMaterials.siliconPrint(), aeMaterials.siliconPress(), aeMaterials.logicProcessorPress(),
        // aeMaterials.calcProcessorPress(), aeMaterials.engProcessorPress())
        // .map(inscriberItems -> inscriberItems.maybeStack(1).get()).forEach(AEHelper::removeInscriberRecipe);

        // 1k Storage Cell
        ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "network/cells/storage_cell_1k"));
        ModHandler
                .removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "network/cells/storage_cell_1k_storage"));
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(aeMaterials.emptyStorageCell().maybeStack(1).get())
                .inputs(aeMaterials.cell1kPart().maybeStack(1).get())
                .outputs(aeItems.cell1k().maybeStack(1).get())
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputNBT(aeItems.cell1k().maybeStack(1).get().getItem(), NBTMatcher.ANY, NBTCondition.ANY)
                .outputs(aeMaterials.emptyStorageCell().maybeStack(1).get())
                .outputs(aeMaterials.cell1kPart().maybeStack(1).get())
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();

        // 4k Storage Cell
        ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "network/cells/storage_cell_4k"));
        ModHandler
                .removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "network/cells/storage_cell_4k_storage"));
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(aeMaterials.emptyStorageCell().maybeStack(1).get())
                .inputs(aeMaterials.cell4kPart().maybeStack(1).get())
                .outputs(aeItems.cell4k().maybeStack(1).get())
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputNBT(aeItems.cell4k().maybeStack(1).get().getItem(), NBTMatcher.ANY, NBTCondition.ANY)
                .outputs(aeMaterials.emptyStorageCell().maybeStack(1).get())
                .outputs(aeMaterials.cell4kPart().maybeStack(1).get())
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();

        // 16k Storage Cell
        ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "network/cells/storage_cell_16k"));
        ModHandler
                .removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "network/cells/storage_cell_16k_storage"));
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(aeMaterials.emptyStorageCell().maybeStack(1).get())
                .inputs(aeMaterials.cell16kPart().maybeStack(1).get())
                .outputs(aeItems.cell16k().maybeStack(1).get())
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputNBT(aeItems.cell16k().maybeStack(1).get().getItem(), NBTMatcher.ANY, NBTCondition.ANY)
                .outputs(aeMaterials.emptyStorageCell().maybeStack(1).get())
                .outputs(aeMaterials.cell16kPart().maybeStack(1).get())
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();

        // 64k Storage Cell
        ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "network/cells/storage_cell_64k"));
        ModHandler
                .removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "network/cells/storage_cell_64k_storage"));
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(aeMaterials.emptyStorageCell().maybeStack(1).get())
                .inputs(aeMaterials.cell64kPart().maybeStack(1).get())
                .outputs(aeItems.cell64k().maybeStack(1).get())
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputNBT(aeItems.cell64k().maybeStack(1).get().getItem(), NBTMatcher.ANY, NBTCondition.ANY)
                .outputs(aeMaterials.emptyStorageCell().maybeStack(1).get())
                .outputs(aeMaterials.cell64kPart().maybeStack(1).get())
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();

        // 1k Fluid Storage Cell
        ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "network/cells/fluid_storage_cell_1k"));
        ModHandler.removeRecipeByName(
                new ResourceLocation(GTEValues.MODID_AE, "network/cells/fluid_storage_cell_1k_storage"));
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(getModItem(GTEValues.MODID_AEA, "storage.casing", 1, 1))
                .inputs(aeMaterials.fluidCell1kPart().maybeStack(1).get())
                .outputs(aeItems.fluidCell1k().maybeStack(1).get())
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputNBT(aeItems.fluidCell1k().maybeStack(1).get().getItem(), NBTMatcher.ANY,
                        NBTCondition.ANY)
                .outputs(getModItem(GTEValues.MODID_AEA, "storage.casing", 1, 1))
                .outputs(aeMaterials.fluidCell1kPart().maybeStack(1).get())
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();

        // 4k Fluid Storage Cell
        ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "network/cells/fluid_storage_cell_4k"));
        ModHandler.removeRecipeByName(
                new ResourceLocation(GTEValues.MODID_AE, "network/cells/fluid_storage_cell_4k_storage"));
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(getModItem(GTEValues.MODID_AEA, "storage.casing", 1, 1))
                .inputs(aeMaterials.fluidCell4kPart().maybeStack(1).get())
                .outputs(aeItems.fluidCell4k().maybeStack(1).get())
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputNBT(aeItems.fluidCell4k().maybeStack(1).get().getItem(), NBTMatcher.ANY,
                        NBTCondition.ANY)
                .outputs(getModItem(GTEValues.MODID_AEA, "storage.casing", 1, 1))
                .outputs(aeMaterials.fluidCell4kPart().maybeStack(1).get())
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();

        // 16k Fluid Storage Cell
        ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "network/cells/fluid_storage_cell_16k"));
        ModHandler.removeRecipeByName(
                new ResourceLocation(GTEValues.MODID_AE, "network/cells/fluid_storage_cell_16k_storage"));
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(getModItem(GTEValues.MODID_AEA, "storage.casing", 1, 1))
                .inputs(aeMaterials.fluidCell16kPart().maybeStack(1).get())
                .outputs(aeItems.fluidCell16k().maybeStack(1).get())
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputNBT(aeItems.fluidCell16k().maybeStack(1).get().getItem(), NBTMatcher.ANY,
                        NBTCondition.ANY)
                .outputs(getModItem(GTEValues.MODID_AEA, "storage.casing", 1, 1))
                .outputs(aeMaterials.fluidCell16kPart().maybeStack(1).get())
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();

        // 64k Fluid Storage Cell
        ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "network/cells/fluid_storage_cell_64k"));
        ModHandler.removeRecipeByName(
                new ResourceLocation(GTEValues.MODID_AE, "network/cells/fluid_storage_cell_64k_storage"));
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(getModItem(GTEValues.MODID_AEA, "storage.casing", 1, 1))
                .inputs(aeMaterials.fluidCell64kPart().maybeStack(1).get())
                .outputs(aeItems.fluidCell64k().maybeStack(1).get())
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputNBT(aeItems.fluidCell64k().maybeStack(1).get().getItem(), NBTMatcher.ANY,
                        NBTCondition.ANY)
                .outputs(getModItem(GTEValues.MODID_AEA, "storage.casing", 1, 1))
                .outputs(aeMaterials.fluidCell64kPart().maybeStack(1).get())
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();

        // 2³ Spatial Storage Cell
        ModHandler.removeRecipeByName(
                new ResourceLocation(GTEValues.MODID_AE, "network/cells/spatial_storage_cell_2_cubed"));
        ModHandler.removeRecipeByName(
                new ResourceLocation(GTEValues.MODID_AE, "network/cells/spatial_storage_cell_2_cubed_storage"));
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(getModItem(GTEValues.MODID_AEA, "storage.casing", 1, 0))
                .inputs(aeMaterials.cell2SpatialPart().maybeStack(1).get())
                .outputs(aeItems.spatialCell2().maybeStack(1).get())
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputNBT(aeItems.spatialCell2().maybeStack(1).get().getItem(), NBTMatcher.ANY,
                        NBTCondition.ANY)
                .outputs(getModItem(GTEValues.MODID_AEA, "storage.casing", 1, 0))
                .outputs(aeMaterials.cell2SpatialPart().maybeStack(1).get())
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();

        // 16³ Spatial Cell
        ModHandler.removeRecipeByName(
                new ResourceLocation(GTEValues.MODID_AE, "network/cells/spatial_storage_cell_16_cubed"));
        ModHandler.removeRecipeByName(
                new ResourceLocation(GTEValues.MODID_AE, "network/cells/spatial_storage_cell_16_cubed_storage"));
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(getModItem(GTEValues.MODID_AEA, "storage.casing", 1, 0))
                .inputs(aeMaterials.cell16SpatialPart().maybeStack(1).get())
                .outputs(aeItems.spatialCell16().maybeStack(1).get())
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputNBT(aeItems.spatialCell16().maybeStack(1).get().getItem(), NBTMatcher.ANY,
                        NBTCondition.ANY)
                .outputs(getModItem(GTEValues.MODID_AEA, "storage.casing", 1, 0))
                .outputs(aeMaterials.cell16SpatialPart().maybeStack(1).get())
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();

        // 128³ Spatial Cell
        ModHandler.removeRecipeByName(
                new ResourceLocation(GTEValues.MODID_AE, "network/cells/spatial_storage_cell_128_cubed"));
        ModHandler.removeRecipeByName(
                new ResourceLocation(GTEValues.MODID_AE, "network/cells/spatial_storage_cell_128_cubed_storage"));
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(getModItem(GTEValues.MODID_AEA, "storage.casing", 1, 0))
                .inputs(aeMaterials.cell128SpatialPart().maybeStack(1).get())
                .outputs(aeItems.spatialCell128().maybeStack(1).get())
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputNBT(aeItems.spatialCell128().maybeStack(1).get().getItem(), NBTMatcher.ANY,
                        NBTCondition.ANY)
                .outputs(getModItem(GTEValues.MODID_AEA, "storage.casing", 1, 0))
                .outputs(aeMaterials.cell128SpatialPart().maybeStack(1).get())
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();

        // 256k Storage Cell
        ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AEA, "storagecells/item/owncasing/256k"));
        ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AEA, "storagecells/item/extracasing/256k"));
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(getModItem(GTEValues.MODID_AEA, "storage.casing", 1, 0))
                .inputs(getModItem(GTEValues.MODID_AEA, "storage.component", 1, 0))
                .outputs(getModItem(GTEValues.MODID_AEA, "storage.physical", 1, 0))
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(getModItem(GTEValues.MODID_AEA, "storage.physical", 1, 0))
                .outputs(getModItem(GTEValues.MODID_AEA, "storage.casing", 1, 0))
                .outputs(getModItem(GTEValues.MODID_AEA, "storage.component", 1, 0))
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();

        // 1024k Storage Cell
        ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AEA, "storagecells/item/owncasing/1024k"));
        ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AEA, "storagecells/item/extracasing/1024k"));
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(getModItem(GTEValues.MODID_AEA, "storage.casing", 1, 0))
                .inputs(getModItem(GTEValues.MODID_AEA, "storage.component", 1, 1))
                .outputs(getModItem(GTEValues.MODID_AEA, "storage.physical", 1, 1))
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(getModItem(GTEValues.MODID_AEA, "storage.physical", 1, 1))
                .outputs(getModItem(GTEValues.MODID_AEA, "storage.casing", 1, 0))
                .outputs(getModItem(GTEValues.MODID_AEA, "storage.component", 1, 1))
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();

        // 4096k Storage Cell
        ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AEA, "storagecells/item/owncasing/4096k"));
        ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AEA, "storagecells/item/extracasing/4096k"));
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(getModItem(GTEValues.MODID_AEA, "storage.casing", 1, 0))
                .inputs(getModItem(GTEValues.MODID_AEA, "storage.component", 1, 2))
                .outputs(getModItem(GTEValues.MODID_AEA, "storage.physical", 1, 2))
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(getModItem(GTEValues.MODID_AEA, "storage.physical", 1, 2))
                .outputs(getModItem(GTEValues.MODID_AEA, "storage.casing", 1, 0))
                .outputs(getModItem(GTEValues.MODID_AEA, "storage.component", 1, 2))
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();

        // 16384k Storage Cell
        ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AEA, "storagecells/item/owncasing/16384k"));
        ModHandler
                .removeRecipeByName(new ResourceLocation(GTEValues.MODID_AEA, "storagecells/item/extracasing/16384k"));
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(getModItem(GTEValues.MODID_AEA, "storage.casing", 1, 0))
                .inputs(getModItem(GTEValues.MODID_AEA, "storage.component", 1, 3))
                .outputs(getModItem(GTEValues.MODID_AEA, "storage.physical", 1, 3))
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(getModItem(GTEValues.MODID_AEA, "storage.physical", 1, 3))
                .outputs(getModItem(GTEValues.MODID_AEA, "storage.casing", 1, 0))
                .outputs(getModItem(GTEValues.MODID_AEA, "storage.component", 1, 3))
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();

        // 256k Fluid Storage Cell
        ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AEA, "storagecells/fluid/owncasing/256k"));
        ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AEA, "storagecells/fluid/extracasing/256k"));
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(getModItem(GTEValues.MODID_AEA, "storage.casing", 1, 1))
                .inputs(getModItem(GTEValues.MODID_AEA, "storage.component", 1, 4))
                .outputs(getModItem(GTEValues.MODID_AEA, "storage.fluid", 1, 0))
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(getModItem(GTEValues.MODID_AEA, "storage.fluid", 1, 0))
                .outputs(getModItem(GTEValues.MODID_AEA, "storage.casing", 1, 1))
                .outputs(getModItem(GTEValues.MODID_AEA, "storage.component", 1, 4))
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();

        // 1024k Fluid Storage Cell
        ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AEA, "storagecells/fluid/owncasing/1024k"));
        ModHandler
                .removeRecipeByName(new ResourceLocation(GTEValues.MODID_AEA, "storagecells/fluid/extracasing/1024k"));
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(getModItem(GTEValues.MODID_AEA, "storage.casing", 1, 1))
                .inputs(getModItem(GTEValues.MODID_AEA, "storage.component", 1, 5))
                .outputs(getModItem(GTEValues.MODID_AEA, "storage.fluid", 1, 1))
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(getModItem(GTEValues.MODID_AEA, "storage.fluid", 1, 1))
                .outputs(getModItem(GTEValues.MODID_AEA, "storage.casing", 1, 1))
                .outputs(getModItem(GTEValues.MODID_AEA, "storage.component", 1, 5))
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();

        // 4096k Fluid Storage Cell
        ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AEA, "storagecells/fluid/owncasing/4096k"));
        ModHandler
                .removeRecipeByName(new ResourceLocation(GTEValues.MODID_AEA, "storagecells/fluid/extracasing/4096k"));
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(getModItem(GTEValues.MODID_AEA, "storage.casing", 1, 1))
                .inputs(getModItem(GTEValues.MODID_AEA, "storage.component", 1, 6))
                .outputs(getModItem(GTEValues.MODID_AEA, "storage.fluid", 1, 2))
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();
        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .inputs(getModItem(GTEValues.MODID_AEA, "storage.fluid", 1, 2))
                .outputs(getModItem(GTEValues.MODID_AEA, "storage.casing", 1, 1))
                .outputs(getModItem(GTEValues.MODID_AEA, "storage.component", 1, 6))
                .duration(10).EUt(VA[ULV])
                .buildAndRegister();

        // Recycle - Storage Housing
        ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "network/cells/empty_storage_cell"));
        ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AEA, "storagecells/fluid/e2acasing"));
        RecipeMaps.MACERATOR_RECIPES.recipeBuilder()
                .inputs(aeMaterials.emptyStorageCell().maybeStack(1).get())
                .output(dust, Steel, 2)
                .output(dustTiny, Steel, 2)
                .duration(100).EUt(VH[LV])
                .buildAndRegister();
        RecipeMaps.ARC_FURNACE_RECIPES.recipeBuilder()
                .inputs(aeMaterials.emptyStorageCell().maybeStack(1).get())
                .fluidInputs(Oxygen.getFluid(56))
                .output(ingot, Steel, 2)
                .output(nugget, Steel, 2)
                .duration(56).EUt(VA[LV])
                .buildAndRegister();

        // Recycle - Fluid Storage Housing
        ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AEA, "storagecells/case/fluid"));
        ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AEA, "storagecells/fluid/a2ecasing"));
        RecipeMaps.MACERATOR_RECIPES.recipeBuilder()
                .inputs(getModItem(GTEValues.MODID_AEA, "storage.casing", 1, 1))
                .output(dust, StainlessSteel, 2)
                .output(dustTiny, StainlessSteel, 2)
                .duration(100).EUt(VH[LV])
                .buildAndRegister();
        RecipeMaps.ARC_FURNACE_RECIPES.recipeBuilder()
                .inputs(getModItem(GTEValues.MODID_AEA, "storage.casing", 1, 1))
                .fluidInputs(Oxygen.getFluid(56))
                .output(ingot, StainlessSteel, 2)
                .output(nugget, StainlessSteel, 2)
                .duration(56).EUt(VA[LV])
                .buildAndRegister();

        // Recycle - Advanced Storage Housing
        ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AEA, "storagecells/case/item"));
        RecipeMaps.MACERATOR_RECIPES.recipeBuilder()
                .inputs(getModItem(GTEValues.MODID_AEA, "storage.casing", 1, 0))
                .output(dust, TungstenSteel, 2)
                .output(dustTiny, TungstenSteel, 2)
                .duration(100).EUt(VH[LV])
                .buildAndRegister();
        RecipeMaps.ARC_FURNACE_RECIPES.recipeBuilder()
                .inputs(getModItem(GTEValues.MODID_AEA, "storage.casing", 1, 0))
                .fluidInputs(Oxygen.getFluid(56))
                .output(ingot, TungstenSteel, 2)
                .output(nugget, TungstenSteel, 2)
                .duration(56).EUt(VA[LV])
                .buildAndRegister();

        // Formation Core
        ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "materials/formationcore"));
        ModHandler.addShapedRecipe("formation_core", aeMaterials.formationCore().maybeStack(1).get(),
                "SES", "LQL", "SES",
                'S', OreDictUnifier.get(stick, tierMaterials[GTEConfigHolder.ae2Integration.voltageTier - 1]),
                'Q', "gemNetherQuartz",
                'E', aeMaterials.engProcessor().maybeStack(1).get(),
                'L', aeMaterials.logicProcessor().maybeStack(1).get());
        ModHandler.addShapedRecipe("formation_core_pure", aeMaterials.formationCore().maybeStack(2).get(),
                "SES", "LQL", "SES",
                'S', OreDictUnifier.get(stick, tierMaterials[GTEConfigHolder.ae2Integration.voltageTier - 1]),
                'Q', aeMaterials.purifiedNetherQuartzCrystal().maybeStack(1).get(),
                'E', aeMaterials.engProcessor().maybeStack(1).get(),
                'L', aeMaterials.logicProcessor().maybeStack(1).get());
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(2)
                .input(stick, tierMaterials[GTEConfigHolder.ae2Integration.voltageTier - 1], 2)
                .inputs(aeMaterials.engProcessor().maybeStack(1).get())
                .inputs(aeMaterials.logicProcessor().maybeStack(1).get())
                .input("craftNetherQuartz", 1)
                .outputs(aeMaterials.formationCore().maybeStack(4).get())
                .duration(20).EUt(VA[GTEConfigHolder.ae2Integration.voltageTier + 1])
                .buildAndRegister();

        // Annihilation Core
        ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "materials/annihilationcore"));
        ModHandler.addShapedRecipe("annihilation_core", aeMaterials.annihilationCore().maybeStack(1).get(),
                "SES", "CQC", "SES",
                'S', OreDictUnifier.get(stick, tierMaterials[GTEConfigHolder.ae2Integration.voltageTier - 1]),
                'Q', "gemCertusQuartz",
                'E', aeMaterials.engProcessor().maybeStack(1).get(),
                'C', aeMaterials.calcProcessor().maybeStack(1).get());
        ModHandler.addShapedRecipe("annihilation_core_pure",
                aeMaterials.annihilationCore().maybeStack(2).get(),
                "SES", "CQC", "SES",
                'S', OreDictUnifier.get(stick, tierMaterials[GTEConfigHolder.ae2Integration.voltageTier - 1]),
                'Q', aeMaterials.purifiedCertusQuartzCrystal().maybeStack(1).get(),
                'E', aeMaterials.engProcessor().maybeStack(1).get(),
                'C', aeMaterials.calcProcessor().maybeStack(1).get());
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(2)
                .input(stick, tierMaterials[GTEConfigHolder.ae2Integration.voltageTier - 1], 2)
                .inputs(aeMaterials.engProcessor().maybeStack(1).get())
                .inputs(aeMaterials.calcProcessor().maybeStack(1).get())
                .input("craftCertusQuartz", 1)
                .outputs(aeMaterials.annihilationCore().maybeStack(4).get())
                .duration(20).EUt(VA[GTEConfigHolder.ae2Integration.voltageTier + 1])
                .buildAndRegister();

        // Matrix Core
        ModHandler.addShapedRecipe("matrix_core", MATRIX_CORE.getStackForm(),
                "SAS", "FQF", "SAS",
                'S', OreDictUnifier.get(stick, tierMaterials[GTEConfigHolder.ae2Integration.voltageTier - 1]),
                'Q', aeMaterials.fluixCrystal().maybeStack(1).get(),
                'A', aeMaterials.annihilationCore().maybeStack(1).get(),
                'F', aeMaterials.formationCore().maybeStack(1).get());
        ModHandler.addShapedRecipe("matrix_core_pure", MATRIX_CORE.getStackForm(2),
                "SAS", "FQF", "SAS",
                'S', OreDictUnifier.get(stick, tierMaterials[GTEConfigHolder.ae2Integration.voltageTier - 1]),
                'Q', aeMaterials.purifiedFluixCrystal().maybeStack(1).get(),
                'A', aeMaterials.annihilationCore().maybeStack(1).get(),
                'F', aeMaterials.formationCore().maybeStack(1).get());
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(6)
                .input(stick, tierMaterials[GTEConfigHolder.ae2Integration.voltageTier - 1], 2)
                .inputs(aeMaterials.annihilationCore().maybeStack(1).get())
                .inputs(aeMaterials.formationCore().maybeStack(1).get())
                .input("craftFluix", 1)
                .output(MATRIX_CORE, 4)
                .duration(20).EUt(VA[GTEConfigHolder.ae2Integration.voltageTier + 1])
                .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .circuitMeta(3)
                .input(stick, tierMaterials[GTEConfigHolder.ae2Integration.voltageTier - 1], 4)
                .inputs(aeMaterials.engProcessor().maybeStack(2).get())
                .inputs(aeMaterials.logicProcessor().maybeStack(1).get())
                .inputs(aeMaterials.calcProcessor().maybeStack(1).get())
                .input("craftNetherQuartz", 1)
                .input("craftCertusQuartz", 1)
                .input("craftFluix", 1)
                .output(MATRIX_CORE, 4)
                .duration(100).EUt(VA[GTEConfigHolder.ae2Integration.voltageTier + 1])
                .buildAndRegister();

        // Printed Silicon
        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder()
                .notConsumable(ae2Integration.moveSteelShape ? SHAPE_MOLD_PRINTED_SILICON.getStackForm() :
                        aeMaterials.siliconPress().maybeStack(1).get())
                .input(plate, Silicon, 1)
                .outputs(aeMaterials.siliconPrint().maybeStack(1).get())
                .duration(20).EUt(VA[GTEConfigHolder.ae2Integration.voltageTier])
                .buildAndRegister();

        // Logic Circuit
        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder()
                .notConsumable(ae2Integration.moveSteelShape ? SHAPE_MOLD_LOGIC_PROCESSOR.getStackForm() :
                        aeMaterials.logicProcessorPress().maybeStack(1).get())
                .input(plate, Gold, 1)
                .outputs(aeMaterials.logicProcessorPrint().maybeStack(1).get())
                .duration(20).EUt(VA[GTEConfigHolder.ae2Integration.voltageTier])
                .buildAndRegister();

        // Calc Circuit
        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder()
                .notConsumable(ae2Integration.moveSteelShape ? SHAPE_MOLD_CALCULATION_PROCESSOR.getStackForm() :
                        aeMaterials.calcProcessorPress().maybeStack(1).get())
                .input(plate, CertusQuartz, 1)
                .outputs(aeMaterials.calcProcessorPrint().maybeStack(1).get())
                .duration(20).EUt(VA[GTEConfigHolder.ae2Integration.voltageTier])
                .buildAndRegister();

        // Engineer Circuit
        RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder()
                .notConsumable(ae2Integration.moveSteelShape ? SHAPE_MOLD_ENGINEERING_PROCESSOR.getStackForm() :
                        aeMaterials.engProcessorPress().maybeStack(1).get())
                .input(plate, Diamond, 1)
                .outputs(aeMaterials.engProcessorPrint().maybeStack(1).get())
                .duration(20).EUt(VA[GTEConfigHolder.ae2Integration.voltageTier])
                .buildAndRegister();

        // Logic Processor
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(aeMaterials.siliconPrint().maybeStack(1).get())
                .inputs(aeMaterials.logicProcessorPrint().maybeStack(1).get())
                .fluidInputs(Redstone.getFluid(144))
                .outputs(aeMaterials.logicProcessor().maybeStack(1).get())
                .duration(20).EUt(VA[GTEConfigHolder.ae2Integration.voltageTier])
                .buildAndRegister();

        // Calc Processor
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(aeMaterials.siliconPrint().maybeStack(1).get())
                .inputs(aeMaterials.calcProcessorPrint().maybeStack(1).get())
                .fluidInputs(Redstone.getFluid(144))
                .outputs(aeMaterials.calcProcessor().maybeStack(1).get())
                .duration(20).EUt(VA[GTEConfigHolder.ae2Integration.voltageTier])
                .buildAndRegister();

        // Engineer Processor
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(aeMaterials.siliconPrint().maybeStack(1).get())
                .inputs(aeMaterials.engProcessorPrint().maybeStack(1).get())
                .fluidInputs(Redstone.getFluid(144))
                .outputs(aeMaterials.engProcessor().maybeStack(1).get())
                .duration(20).EUt(VA[GTEConfigHolder.ae2Integration.voltageTier])
                .buildAndRegister();

        if (ae2Integration.moveSteelShape) {
            // All shapes
            Arrays.stream(GTE_SHAPE_MOLDS).forEach(shapeMold -> RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder()
                    .notConsumable(shapeMold.getStackForm())
                    .inputs(SHAPE_EMPTY.getStackForm())
                    .outputs(shapeMold.getStackForm())
                    .duration(120).EUt(22)
                    .buildAndRegister());
            Arrays.stream(GTE_SHAPE_EXTRUDERS).filter(Objects::nonNull)
                    .forEach(shapeExtruder -> RecipeMaps.FORMING_PRESS_RECIPES.recipeBuilder()
                            .notConsumable(shapeExtruder.getStackForm())
                            .inputs(SHAPE_EMPTY.getStackForm())
                            .outputs(shapeExtruder.getStackForm())
                            .duration(120).EUt(22)
                            .buildAndRegister());

            // Mold (Printed Silicon)
            ModHandler.addShapedRecipe("shape_mold_printed_silicon",
                    SHAPE_MOLD_PRINTED_SILICON.getStackForm(),
                    "h  ", "   ", "S  ",
                    'S', SHAPE_EMPTY.getStackForm());
            ModHandler.addShapelessRecipe("silicon_processor_mold_to_gt",
                    SHAPE_MOLD_PRINTED_SILICON.getStackForm(),
                    aeMaterials.siliconPress().maybeStack(1).get());

            // Mold (Logic Processor)
            ModHandler.addShapedRecipe("shape_mold_logic_processor", SHAPE_MOLD_LOGIC_PROCESSOR.getStackForm(),
                    " h ", "   ", "S  ",
                    'S', SHAPE_EMPTY.getStackForm());
            ModHandler.addShapelessRecipe("logic_processor_mold_to_gt", SHAPE_MOLD_LOGIC_PROCESSOR.getStackForm(),
                    aeMaterials.logicProcessorPress().maybeStack(1).get());

            // Mold (Calculation Processor)
            ModHandler.addShapedRecipe("shape_mold_calculation_processor",
                    SHAPE_MOLD_CALCULATION_PROCESSOR.getStackForm(),
                    "   ", "  h", "S  ",
                    'S', SHAPE_EMPTY.getStackForm());
            ModHandler.addShapelessRecipe("calc_processor_mold_to_gt",
                    SHAPE_MOLD_CALCULATION_PROCESSOR.getStackForm(),
                    aeMaterials.calcProcessorPress().maybeStack(1).get());

            // Mold (Engineering Processor)
            ModHandler.addShapedRecipe("shape_mold_engineering_processor",
                    SHAPE_MOLD_ENGINEERING_PROCESSOR.getStackForm(),
                    "   ", "   ", "S h",
                    'S', SHAPE_EMPTY.getStackForm());
            ModHandler.addShapelessRecipe("engineer_processor_mold_to_gt",
                    SHAPE_MOLD_ENGINEERING_PROCESSOR.getStackForm(),
                    aeMaterials.engProcessorPress().maybeStack(1).get());

            // Extruder Shape (Printed Silicon)
            ModHandler.addShapedRecipe("shape_extruder_printed_silicon",
                    SHAPE_EXTRUDER_PRINTED_SILICON.getStackForm(),
                    " x ", " S ", "   ",
                    'S', SHAPE_EMPTY.getStackForm());

            // Extruder Shape (Logic Processor)
            ModHandler.addShapedRecipe("shape_extruder_logic_processor",
                    SHAPE_EXTRUDER_LOGIC_PROCESSOR.getStackForm(),
                    " x ", "S  ", "   ",
                    'S', SHAPE_EXTRUDER_LOGIC_PROCESSOR.getStackForm());

            // Extruder Shape (Calculation Processor)
            ModHandler.addShapedRecipe("shape_extruder_calculation_processor",
                    SHAPE_EXTRUDER_CALCULATION_PROCESSOR.getStackForm(),
                    " x ", " S ", "   ",
                    'S', SHAPE_EXTRUDER_LOGIC_PROCESSOR.getStackForm());

            // Extruder Shape (Engineering Processor)
            ModHandler.addShapedRecipe("shape_extruder_engineering_processor",
                    SHAPE_EXTRUDER_ENGINEERING_PROCESSOR.getStackForm(),
                    " x ", "  S", "   ",
                    'S', SHAPE_EXTRUDER_LOGIC_PROCESSOR.getStackForm());
        } else {
            // Silicon Processor Press
            RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
                    .notConsumable(lens, NetherQuartz)
                    .input(block, Iron, 1)
                    .outputs(aeMaterials.siliconPress().maybeStack(1).get())
                    .duration(2000).EUt(VA[GTEConfigHolder.ae2Integration.voltageTier])
                    .buildAndRegister();

            // Logic Processor Press
            RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
                    .notConsumable(lens, ChargedCertusQuartz)
                    .input(block, Iron, 1)
                    .outputs(aeMaterials.logicProcessorPress().maybeStack(1).get())
                    .duration(2000).EUt(VA[GTEConfigHolder.ae2Integration.voltageTier])
                    .buildAndRegister();

            // Calc Processor Press
            RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
                    .notConsumable(lens, CertusQuartz)
                    .input(block, Iron, 1)
                    .outputs(aeMaterials.calcProcessorPress().maybeStack(1).get())
                    .duration(2000).EUt(VA[GTEConfigHolder.ae2Integration.voltageTier])
                    .buildAndRegister();

            // Engineer Processor Press
            RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
                    .notConsumable(lens, Fluix)
                    .input(block, Iron, 1)
                    .outputs(aeMaterials.engProcessorPress().maybeStack(1).get())
                    .duration(2000).EUt(VA[GTEConfigHolder.ae2Integration.voltageTier])
                    .buildAndRegister();
        }
    }

    private static void tools() {
        if (ConfigHolder.recipes.hardToolArmorRecipes && GTEConfigHolder.ae2Integration.hardToolRecipes) {
            // Nether Quartz Axe
            ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "tools/nether_quartz_axe"));
            ModHandler.addShapedRecipe("nether_quartz_axe", aeItems.netherQuartzAxe().maybeStack(1).get(),
                    "PQf", "PS ", "hS ",
                    'P', OreDictUnifier.get(plate, NetherQuartz),
                    'Q', OreDictUnifier.get(gem, NetherQuartz),
                    'S', OreDictUnifier.get(stick, Wood));

            // Nether Quartz Hoe
            ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "tools/nether_quartz_hoe"));
            ModHandler.addShapedRecipe("nether_quartz_hoe", aeItems.netherQuartzHoe().maybeStack(1).get(),
                    "PQf", "hS ", " S ",
                    'P', OreDictUnifier.get(plate, NetherQuartz),
                    'Q', OreDictUnifier.get(gem, NetherQuartz),
                    'S', OreDictUnifier.get(stick, Wood));

            // Nether Quartz Pickaxe
            ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "tools/nether_quartz_pickaxe"));
            ModHandler.addShapedRecipe("nether_quartz_pickaxe", aeItems.netherQuartzPick().maybeStack(1).get(),
                    "PQQ", "hSf", " S ",
                    'P', OreDictUnifier.get(plate, NetherQuartz),
                    'Q', OreDictUnifier.get(gem, NetherQuartz),
                    'S', OreDictUnifier.get(stick, Wood));

            // Nether Quartz Shovel
            ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "tools/nether_quartz_spade"));
            ModHandler.addShapedRecipe("nether_quartz_spade", aeItems.netherQuartzShovel().maybeStack(1).get(),
                    "hPf", " S ", " S ",
                    'P', OreDictUnifier.get(plate, NetherQuartz),
                    'S', OreDictUnifier.get(stick, Wood));

            // Nether Quartz Sword
            ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "tools/nether_quartz_sword"));
            ModHandler.addShapedRecipe("nether_quartz_sword", aeItems.netherQuartzSword().maybeStack(1).get(),
                    " P ", "hPf", " S ",
                    'P', OreDictUnifier.get(plate, NetherQuartz),
                    'S', OreDictUnifier.get(stick, Wood));

            // Nether Quartz Cutting Knife
            ModHandler
                    .removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "tools/nether_quartz_cutting_knife"));
            ModHandler.addShapedRecipe("nether_quartz_cutting_knife",
                    aeItems.netherQuartzKnife().maybeStack(1).get(),
                    "fPh", "QSQ", " S ",
                    'Q', new ItemStack(Items.QUARTZ),
                    'P', OreDictUnifier.get(plate, NetherQuartz),
                    'S', OreDictUnifier.get(stick, Wood));

            // Nether Quartz Wrench
            ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "tools/nether_quartz_wrench"));
            ModHandler.addShapedRecipe("ether_quartz_wrench", aeItems.netherQuartzWrench().maybeStack(1).get(),
                    "PhP", " P ", " P ",
                    'P', OreDictUnifier.get(plate, NetherQuartz));

            // Certus Quartz Axe
            ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "tools/certus_quartz_axe"));
            ModHandler.addShapedRecipe("certus_quartz_axe", aeItems.certusQuartzAxe().maybeStack(1).get(),
                    "PQf", "PS ", "hS ",
                    'Q', "gemCertusQuartz",
                    'P', OreDictUnifier.get(plate, CertusQuartz),
                    'S', OreDictUnifier.get(stick, Wood));

            // Certus Quartz Hoe
            ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "tools/certus_quartz_hoe"));
            ModHandler.addShapedRecipe("certus_quartz_hoe", aeItems.certusQuartzHoe().maybeStack(1).get(),
                    "PQf", "hS ", " S ",
                    'Q', "gemCertusQuartz",
                    'P', OreDictUnifier.get(plate, CertusQuartz),
                    'S', OreDictUnifier.get(stick, Wood));

            // Certus Quartz Pickaxe
            ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "tools/certus_quartz_pickaxe"));
            ModHandler.addShapedRecipe("certus_quartz_pickaxe", aeItems.certusQuartzPick().maybeStack(1).get(),
                    "PQQ", "hSf", " S ",
                    'Q', "gemCertusQuartz",
                    'P', OreDictUnifier.get(plate, CertusQuartz),
                    'S', OreDictUnifier.get(stick, Wood));

            // Certus Quartz Shovel
            ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "tools/certus_quartz_spade"));
            ModHandler.addShapedRecipe("certus_quartz_spade", aeItems.certusQuartzShovel().maybeStack(1).get(),
                    "hPf", " S ", " S ",
                    'P', OreDictUnifier.get(plate, CertusQuartz),
                    'S', OreDictUnifier.get(stick, Wood));

            // Certus Quartz Sword
            ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "tools/certus_quartz_sword"));
            ModHandler.addShapedRecipe("certus_quartz_sword", aeItems.certusQuartzSword().maybeStack(1).get(),
                    " P ", "hPf", " S ",
                    'P', OreDictUnifier.get(plate, CertusQuartz),
                    'S', OreDictUnifier.get(stick, Wood));

            // Certus Quartz Cutting Knife
            ModHandler
                    .removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "tools/certus_quartz_cutting_knife"));
            ModHandler.addShapedRecipe("certus_quartz_cutting_knife",
                    aeItems.certusQuartzKnife().maybeStack(1).get(),
                    "fPh", "QSQ", " S ",
                    'Q', "gemCertusQuartz",
                    'P', OreDictUnifier.get(plate, CertusQuartz),
                    'S', OreDictUnifier.get(stick, Wood));

            // Certus Quartz Wrench
            ModHandler.removeRecipeByName(new ResourceLocation(GTEValues.MODID_AE, "tools/certus_quartz_wrench"));
            ModHandler.addShapedRecipe("certus_quartz_wrench",
                    aeItems.certusQuartzWrench().maybeStack(1).get(),
                    "PhP", " P ", " P ",
                    'P', OreDictUnifier.get(plate, CertusQuartz));
        }
    }
}
