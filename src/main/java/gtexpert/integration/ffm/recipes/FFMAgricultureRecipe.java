package gtexpert.integration.ffm.recipes;

import gregtech.api.GTValues;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.metatileentities.MetaTileEntities;
import gtexpert.api.GTEValues;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.jetbrains.annotations.NotNull;

import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.integration.IntegrationUtil.getModItem;

public class FFMAgricultureRecipe {

    /* Target Module
    * cultivation
    * farming
    * greenhouse
    */

    public static void init() {
        farming.register();
    }

    public static void remove() {}

    //cultivation
    public static class cultivation {}

    //farming
    public static class farming {
        public static void register() {
            blocks();
        }

        public static void blocks() {
            registerFarmingBlockRecipe(new ItemStack(Blocks.STONEBRICK, 1, 0), 0);
            registerFarmingBlockRecipe(new ItemStack(Blocks.STONEBRICK, 1, 1), 1);
            registerFarmingBlockRecipe(new ItemStack(Blocks.STONEBRICK, 1, 2), 2);
            registerFarmingBlockRecipe(new ItemStack(Blocks.BRICK_BLOCK), 3);
            registerFarmingBlockRecipe(new ItemStack(Blocks.SANDSTONE, 1, 2), 4);
            registerFarmingBlockRecipe(new ItemStack(Blocks.SANDSTONE, 1, 1), 5);
            registerFarmingBlockRecipe(new ItemStack(Blocks.NETHER_BRICK), 6);
            registerFarmingBlockRecipe(new ItemStack(Blocks.STONEBRICK, 1, 3), 7);
            registerFarmingBlockRecipe(new ItemStack(Blocks.QUARTZ_BLOCK, 1, 0), 8);
            registerFarmingBlockRecipe(new ItemStack(Blocks.QUARTZ_BLOCK, 1, 1), 9);
            registerFarmingBlockRecipe(new ItemStack(Blocks.QUARTZ_BLOCK, 1, 2), 10);
        }

        public static void registerFarmingBlockRecipe(@NotNull ItemStack stack, int tagMeta) {
            int FarmingVoltage = GTValues.MV; // ToDo:  set by config
            NBTTagCompound tag = new NBTTagCompound();
            tag.setInteger("FarmBlock", tagMeta);

            ItemStack Base = getModItem(GTEValues.MODID_FFM, "ffarm", 0);
            Base.setTagCompound(tag);
            ModHandler.addShapedRecipe(true, "farm_basic." + tagMeta, Base,
                    "S S", "PBP", "STS",
                    'S', new UnificationEntry(OrePrefix.screw, Steel),
                    'P', new UnificationEntry(OrePrefix.plate, Copper),
                    'B', stack,
                    'T', getModItem(GTEValues.MODID_FFM, "thermionic_tubes", 1));

            ItemStack GearBox = getModItem(GTEValues.MODID_FFM, "ffarm", 2);
            GearBox.setTagCompound(tag);
            ModHandler.addShapedRecipe(true, "farm_gearbox." + tagMeta, GearBox,
                    "S S", "GBG", "SES",
                    'S', new UnificationEntry(OrePrefix.screw, RedAlloy),
                    'G', new UnificationEntry(OrePrefix.gear, Tin),
                    'B', Base,
                    'E', MetaTileEntities.ENERGY_INPUT_HATCH[FarmingVoltage].getStackForm());

        }
    }

    //greenhouse
    public static class greenhouse {}
}
