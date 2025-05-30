package com.github.gtexpert.core.integration.extracpus;

import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;

import com.github.gtexpert.core.api.GTEValues;
import com.github.gtexpert.core.api.modules.GTEModule;
import com.github.gtexpert.core.api.util.Mods;
import com.github.gtexpert.core.integration.GTEIntegrationSubmodule;
import com.github.gtexpert.core.integration.extracpus.recipes.EXCPUSBlocksRecipe;
import com.github.gtexpert.core.integration.extracpus.recipes.EXCPUSItemsRecipe;
import com.github.gtexpert.core.modules.GTEModules;

@GTEModule(
           moduleID = GTEModules.MODULE_EXCPUS,
           containerID = GTEValues.MODID,
           modDependencies = { Mods.Names.APPLIED_ENERGISTICS2, Mods.Names.AE_ADDITIONS, Mods.Names.EXTRA_CPUS },
           name = "GTExpert Extra CPUs Integration",
           description = "Extra CPUs Integration Module")
public class EXCPUSModule extends GTEIntegrationSubmodule {

    @Override
    public void registerRecipesNormal(RegistryEvent.Register<IRecipe> event) {
        EXCPUSItemsRecipe.init();
        EXCPUSBlocksRecipe.init();
    }
}
