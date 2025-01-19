package gtexpert.common.metatileentities;

import static gregtech.common.metatileentities.MetaTileEntities.*;
import static gtexpert.api.util.GTEUtility.gteId;

import java.util.function.Function;

import net.minecraft.util.ResourceLocation;

import gregtech.api.GTValues;
import gregtech.api.recipes.RecipeMap;
import gregtech.client.renderer.ICubeRenderer;

import gtexpert.common.GTEConfigHolder;
import gtexpert.common.metatileentities.multi.*;

public class GTEMetaTileEntities {

    // Single Machine

    // Multi Machine
    public static MetaTileEntityLargeCrackingUnit LARGE_CRACKER;
    public static MetaTileEntityVoidOreMiner VOIDOREMINER;
    public static MetaTileEntityAdvancedChemicalPlant ADVANCED_CHEMICAL_PLANT;
    public static MetaTileEntityLargeGasCollector LARGE_GAS_COLLECTOR;

    public static void init() {
        // Single Machine
        // Free: 11004~11999

        // Multi Machine
        // Free: 12000~12001
        LARGE_CRACKER = registerMetaTileEntity(12002,
                new MetaTileEntityLargeCrackingUnit(gteId("large_cracking_unit")));
        VOIDOREMINER = registerMetaTileEntity(12003,
                new MetaTileEntityVoidOreMiner(gteId("void_ore_miner")));
        // 12004~12005 is reserved for Draconic Evolution
        ADVANCED_CHEMICAL_PLANT = registerMetaTileEntity(12006,
                new MetaTileEntityAdvancedChemicalPlant(gteId("advanced_chemical_plant")));
        LARGE_GAS_COLLECTOR = registerMetaTileEntity(12007,
                new MetaTileEntityLargeGasCollector(gteId(GTEConfigHolder.gteFlag.featureFlag ?
                        "large_gas_collector" : "advanced_gas_collector")));
    }

    public static void registerGTESimpleMetaTileEntity(GTESimpleMachineMetaTileEntity[] machines, int startId,
                                                       String name, RecipeMap<?> map, ICubeRenderer texture,
                                                       boolean hasFrontFacing,
                                                       Function<String, ResourceLocation> resourceId,
                                                       Function<Integer, Integer> tankScalingFunction) {
        for (int i = 0; i < machines.length - 1; ++i) {
            if (i <= 4 || getMidTier(name)) {
                if (i > 7 && !getHighTier(name)) {
                    break;
                }

                String voltageName = GTValues.VN[i + 1].toLowerCase();
                machines[i + 1] = registerMetaTileEntity(startId + i,
                        new GTESimpleMachineMetaTileEntity(resourceId.apply(String.format("%s.%s", name, voltageName)),
                                map, texture, i + 1, hasFrontFacing, tankScalingFunction));
            }
        }
    }
}
