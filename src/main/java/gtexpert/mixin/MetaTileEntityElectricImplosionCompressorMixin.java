package gtexpert.mixin;

import gregicality.multiblocks.common.metatileentities.multiblock.standard.MetaTileEntityElectricImplosionCompressor;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.recipes.RecipeMaps;
import gtexpert.core.GTERecipeMaps;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(value = {MetaTileEntityElectricImplosionCompressor.class}, remap = false)
public abstract class MetaTileEntityElectricImplosionCompressorMixin extends RecipeMapMultiblockController {
    public MetaTileEntityElectricImplosionCompressorMixin(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTERecipeMaps.ELECTRIC_IMPLOSION_COMPRESSOR_RECIPES);
    }
}
