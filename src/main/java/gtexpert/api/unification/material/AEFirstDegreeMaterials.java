package gtexpert.api.unification.material;

import gregtech.api.fluids.fluidType.FluidTypes;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialIconSet;
import gregtech.api.unification.material.properties.BlastProperty.GasTier;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gregtech.api.util.GTUtility.gregtechId;
import static gtexpert.api.unification.material.GTEMaterials.*;

public class AEFirstDegreeMaterials {

    /**
     * 24151 - 24175
     */
    public static void init() {
        // Charged Certus Quartz
        CHARGED_CERTUS_QUARTZ = new Material.Builder(24151, gregtechId("charged_certus_quartz"))
                .dust()
                .fluid(FluidTypes.LIQUID, false).fluidTemp(1200)
                .color(0xCFDAFF).iconSet(MaterialIconSet.CERTUS)
                .flags(GENERATE_PLATE, GENERATE_LENS, DISABLE_DECOMPOSITION)
                .components(Silicon, 1, Oxygen, 2)
                .build();

        // Fluix
        FLUIX = new Material.Builder(24152, gregtechId("fluix"))
                .dust()
                .fluid(FluidTypes.LIQUID, false).fluidTemp(1200)
                .color(0x846994).iconSet(MaterialIconSet.CERTUS)
                .flags(GENERATE_PLATE, GENERATE_LENS, DISABLE_DECOMPOSITION)
                .components(Silicon, 2, Oxygen, 4, Redstone, 1)
                .build();

        // Fluix Alloy
        FLUIX_ALLOY = new Material.Builder(24153, gregtechId("fluix_alloy"))
                .ingot()
                .fluid(FluidTypes.LIQUID, false).fluidTemp(1200)
                .color(0x4A3954).iconSet(MaterialIconSet.SHINY)
                .flags(GENERATE_PLATE, DISABLE_DECOMPOSITION)
                .blastTemp(2700, GasTier.LOW, VA[HV], 1072)
                .components(FLUIX, 2, Carbon, 2, Silicon, 1, Iron, 1)
                .build();
        FLUIX_ALLOY.setFormula("(Si2O4(Si(FeS2)5)(CrAl2O3)Hg3))2C2SiFe?", true);
    }
}
