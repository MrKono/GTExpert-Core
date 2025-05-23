package com.github.gtexpert.core.modules;

import com.github.gtexpert.core.api.GTEValues;
import com.github.gtexpert.core.api.modules.IModuleContainer;

public class GTEModules implements IModuleContainer {

    public static final String MODULE_CORE = "core";
    public static final String MODULE_TOOLS = "tools";
    public static final String MODULE_INTEGRATION = "integration";

    // Integration modules
    public static final String MODULE_JEI = "jei_integration";
    public static final String MODULE_TOP = "top_integration";
    public static final String MODULE_CT = "ct_integration";
    public static final String MODULE_FFM = "ffm_integration";
    public static final String MODULE_GENDUSTRY = "gendustry_integration";
    public static final String MODULE_CHISEL = "chisel_integration";
    public static final String MODULE_AE = "ae_integration";
    public static final String MODULE_AEA = "aeadditions_integration";
    public static final String MODULE_NAE2 = "nae2_integration";
    public static final String MODULE_BINNIES = "binnies_integration";
    public static final String MODULE_BOTANY = "botany_integration";
    public static final String MODULE_EXBEES = "extrabees_integration";
    public static final String MODULE_EXCPUS = "extracpus_integration";
    public static final String MODULE_EXTREES = "extratrees_integration";
    public static final String MODULE_GENETICS = "genetics_integration";
    public static final String MODULE_GTFO = "gtfo_integration";
    public static final String MODULE_EIO = "eio_integration";
    public static final String MODULE_DEDA = "deda_integration";
    public static final String MODULE_AVARITIA = "avaritia_integration";
    public static final String MODULE_TC = "tc_integration";
    public static final String MODULE_TE = "te_integration";

    @Override
    public String getID() {
        return GTEValues.MODID;
    }
}
