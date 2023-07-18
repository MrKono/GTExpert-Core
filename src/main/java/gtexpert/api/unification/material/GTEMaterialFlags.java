package gtexpert.api.unification.material;

import gregtech.api.unification.material.properties.*;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;

public class GTEMaterialFlags {

    public static void init() {
        // Iron
        Iron.addFlags(GENERATE_DOUBLE_PLATE);

        // Ender Peral
        EnderPearl.setProperty(PropertyKey.FLUID, new FluidProperty());

        // Ender Eye
        EnderEye.setProperty(PropertyKey.FLUID, new FluidProperty());
        EnderEye.setFormula("(BeK4N5)(CS)", true);

        // NetherQuartz
        NetherQuartz.setProperty(PropertyKey.FLUID, new FluidProperty());
        NetherQuartz.addFlags(GENERATE_LENS, GENERATE_ROD);

        // Certus Quartz
        CertusQuartz.setProperty(PropertyKey.FLUID, new FluidProperty());
        CertusQuartz.addFlags(GENERATE_LENS, GENERATE_ROD);

        // Quartzite
        Quartzite.addFlags(GENERATE_ROD);

        // Glowstone
        Glowstone.setFormula("Au(Si(FeS2)5(CrAl2O3)Hg3)", true);

        // Darmstadtium
        Darmstadtium.addFlags(GENERATE_GEAR, GENERATE_FRAME);

        // Osmium
        Osmium.setProperty(PropertyKey.ORE, new OreProperty());

        // Iridium
        Iridium.setProperty(PropertyKey.ORE, new OreProperty());
    }
}
