package gtexpert.integration.ffm;

import gtexpert.integration.ffm.recipes.*;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;

import gtexpert.api.GTEValues;
import gtexpert.api.modules.GTEModule;
import gtexpert.integration.GTEIntegrationSubmodule;
import gtexpert.integration.ffm.loaders.FFMOreDictionaryLoader;
import gtexpert.modules.GTEModules;

@GTEModule(
           moduleID = GTEModules.MODULE_FFM,
           containerID = GTEValues.MODID,
           modDependencies = GTEValues.MODID_FFM,
           name = "GTExpert Forestry For Minecraft Integration",
           description = "Forestry For Minecraft Integration Module")
public class FFMModule extends GTEIntegrationSubmodule {

    @Override
    public void registerRecipesNormal(RegistryEvent.Register<IRecipe> event) {
        FFMOreDictionaryLoader.init();
    }

    @Override
    public void registerRecipesLowest(RegistryEvent.Register<IRecipe> event) {
        FFMApicultureRecipe.init();
        FFMAgricultureRecipe.init();
        FFMCoreRecipe.init();
        FFMItemRecipe.init();
        FFMLepidopterologyRecipe.init();
        FFMMachineRecipe.init();
        FFMTreeRecipe.init();
    }
}
