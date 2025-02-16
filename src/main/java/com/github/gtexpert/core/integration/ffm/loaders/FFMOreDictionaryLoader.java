package com.github.gtexpert.core.integration.ffm.loaders;

import net.minecraftforge.oredict.OreDictionary;

import com.github.gtexpert.core.api.util.Mods;

public class FFMOreDictionaryLoader {

    public static void init() {
        OreDictionary.registerOre("oreApatite", Mods.Forestry.getItem("resources"));
        OreDictionary.registerOre("oreCopper", Mods.Forestry.getItem("resources", 1, 1));
        OreDictionary.registerOre("oreTin", Mods.Forestry.getItem("resources", 1, 2));
    }
}
