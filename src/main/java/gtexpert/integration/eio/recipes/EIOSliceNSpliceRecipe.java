package gtexpert.integration.eio.recipes;

import static gregtech.api.GTValues.LV;
import static gregtech.api.GTValues.VA;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static gregtech.api.unification.ore.OrePrefix.plate;

import net.minecraft.init.Items;

import gregtech.api.unification.material.Materials;
import gregtech.common.items.MetaItems;

import gtexpert.core.GTERecipeMaps;
import gtexpert.api.unification.material.GTEMaterials;

import crazypants.enderio.base.init.ModObject;
import crazypants.enderio.endergy.init.EndergyObject;

public class EIOSliceNSpliceRecipe {

    public static void init() {
        // Zombie Electrode
        GTERecipeMaps.SLICE_N_SPLICE_RECIPES.recipeBuilder()
                .input(plate, GTEMaterials.EnergeticAlloy)
                .input(Items.SKULL)
                .input(plate, GTEMaterials.EnergeticAlloy)
                .input(MetaItems.SILICON_WAFER, 1)
                .input("craftCapacitorEIO", 1)
                .input(MetaItems.SILICON_WAFER, 1)
                .output(ModObject.itemMaterial.getItemNN(), 1, 40)
                .duration(400).EUt(VA[LV]).buildAndRegister();

        // Z-Logic Controller
        GTERecipeMaps.SLICE_N_SPLICE_RECIPES.recipeBuilder()
                .input(plate, GTEMaterials.Soularium)
                .input(Items.SKULL, 1, 2)
                .input(plate, GTEMaterials.Soularium)
                .input(MetaItems.SILICON_WAFER, 1)
                .input(dust, Materials.Redstone)
                .input(MetaItems.SILICON_WAFER, 1)
                .output(ModObject.itemMaterial.getItemNN(), 1, 41)
                .duration(400).EUt(VA[LV]).buildAndRegister();

        // Ender Resonator
        GTERecipeMaps.SLICE_N_SPLICE_RECIPES.recipeBuilder()
                .input(plate, GTEMaterials.Soularium)
                .input(ModObject.blockEndermanSkull.getItemNN())
                .input(plate, GTEMaterials.Soularium)
                .input(MetaItems.SILICON_WAFER, 1)
                .input(plate, GTEMaterials.VibrantAlloy)
                .input(MetaItems.SILICON_WAFER, 1)
                .output(ModObject.itemMaterial.getItemNN(), 1, 43)
                .duration(400).EUt(VA[LV]).buildAndRegister();

        // Skeletal Controller
        GTERecipeMaps.SLICE_N_SPLICE_RECIPES.recipeBuilder()
                .input(plate, GTEMaterials.Soularium)
                .input(Items.SKULL)
                .input(plate, GTEMaterials.Soularium)
                .input(MetaItems.SILICON_WAFER, 1)
                .input("craftCapacitorEIO", 1)
                .input(MetaItems.SILICON_WAFER, 1)
                .output(ModObject.itemMaterial.getItemNN(), 1, 45)
                .duration(400).EUt(VA[LV]).buildAndRegister();

        // Guardian Diode
        GTERecipeMaps.SLICE_N_SPLICE_RECIPES.recipeBuilder()
                .input(plate, GTEMaterials.EnergeticAlloy)
                .input(Items.PRISMARINE_SHARD)
                .input(plate, GTEMaterials.EnergeticAlloy)
                .input(ModObject.itemMaterial.getItemNN(), 1, 14)
                .input(MetaItems.SILICON_WAFER, 1)
                .input(ModObject.itemMaterial.getItemNN(), 1, 14)
                .output(ModObject.itemMaterial.getItemNN(), 1, 56)
                .duration(400).EUt(VA[LV]).buildAndRegister();

        // Tormented Enderman Head
        GTERecipeMaps.SLICE_N_SPLICE_RECIPES.recipeBuilder()
                .input(plate, GTEMaterials.Soularium)
                .input(ModObject.blockEndermanSkull.getItemNN())
                .input(plate, GTEMaterials.Soularium)
                .input(MetaItems.SILICON_WAFER, 1)
                .input("craftCapacitorEIO", 1)
                .input(MetaItems.SILICON_WAFER, 1)
                .output(ModObject.blockEndermanSkull.getItemNN(), 1, 2)
                .duration(400).EUt(VA[LV]).buildAndRegister();

        // Totemic Capacitor
        GTERecipeMaps.SLICE_N_SPLICE_RECIPES.recipeBuilder()
                .input(plate, GTEMaterials.Soularium)
                .input(Items.TOTEM_OF_UNDYING)
                .input(plate, GTEMaterials.Soularium)
                .input(MetaItems.SILICON_WAFER, 1)
                .input(EndergyObject.itemCapacitorMelodic.getItemNN(), 1)
                .input(MetaItems.SILICON_WAFER, 1)
                .output(ModObject.blockEndermanSkull.getItemNN(), 1, 2)
                .duration(400).EUt(VA[LV]).buildAndRegister();
    }
}
