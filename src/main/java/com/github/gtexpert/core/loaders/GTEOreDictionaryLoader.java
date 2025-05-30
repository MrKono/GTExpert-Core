package com.github.gtexpert.core.loaders;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.github.gtexpert.core.common.items.GTEMetaItems;

public class GTEOreDictionaryLoader {

    public static void init() {
        OreDictionary.registerOre("stickArtificialBone", GTEMetaItems.ARTIFICIAL_BONE.getStackForm());
        OreDictionary.registerOre("bookshelf", new ItemStack(Blocks.BOOKSHELF));
    }
}
