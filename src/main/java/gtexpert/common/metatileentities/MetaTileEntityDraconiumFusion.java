package gtexpert.common.metatileentities;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.client.renderer.ICubeRenderer;
import gtexpert.api.recipes.GTERecipeMaps;
import gtexpert.client.GTETextures;
import gtexpert.common.GTEBlockMetalCasing;
import gtexpert.common.GTEMetaBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class MetaTileEntityDraconiumFusion extends RecipeMapMultiblockController {
    public MetaTileEntityDraconiumFusion(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTERecipeMaps.DRACONIUM_FUSION_RECIPES);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityDraconiumFusion(metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("CCC", "CCC", "CCC")
                .aisle("CCC", "C#C", "CMC")
                .aisle("CCC", "CSC", "CCC")
                .where('S', selfPredicate())
                .where('C', states(GTEMetaBlocks.GTE_BLOCK_METAL_CASING.getState(GTEBlockMetalCasing.MetalCasingType.DRACONIUM_CASING)).setMinGlobalLimited(15)
                        .or(autoAbilities(true, true, true, true, true, true, false)))
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where('#', any())
                .build();
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) { return GTETextures.DRACONIUM_CASING; }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }
}