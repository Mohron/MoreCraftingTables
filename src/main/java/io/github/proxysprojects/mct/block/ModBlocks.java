/*
 * Copyright (c) 2017 Proxy's Projects
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.github.proxysprojects.mct.block;

import io.github.proxysprojects.mct.MoreCraftingTables;
import io.github.proxysprojects.mct.tile.TileEntityBigCraftingTable;
import io.github.proxysprojects.mct.tile.TileEntityExtremeCraftingTable;
import io.github.proxysprojects.mct.tile.TileEntityHugeCraftingTable;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class ModBlocks {
    public static final BlockBigCraftingTable BIG_CRAFTING_TABLE = new BlockBigCraftingTable();
    public static final BlockHugeCraftingTable HUGE_CRAFTING_TABLE = new BlockHugeCraftingTable();
    public static final BlockExtremeCraftingTable EXTREME_CRAFTING_TABLE = new BlockExtremeCraftingTable();

    public static void init() {
        MoreCraftingTables.proxy.registerBlock(BIG_CRAFTING_TABLE);
        MoreCraftingTables.proxy.registerBlock(HUGE_CRAFTING_TABLE);
        MoreCraftingTables.proxy.registerBlock(EXTREME_CRAFTING_TABLE);

        GameRegistry.registerTileEntity(TileEntityBigCraftingTable.class, MoreCraftingTables.MOD_ID + ":big_crafting_table");
        GameRegistry.registerTileEntity(TileEntityHugeCraftingTable.class, MoreCraftingTables.MOD_ID + ":huge_crafting_table");
        GameRegistry.registerTileEntity(TileEntityExtremeCraftingTable.class, MoreCraftingTables.MOD_ID + ":extreme_crafting_table");

        FMLCommonHandler.instance().getDataFixer().registerVanillaWalker(FixTypes.BLOCK_ENTITY, new ItemStackDataLists(TileEntityBigCraftingTable.class, "Items"));
        FMLCommonHandler.instance().getDataFixer().registerVanillaWalker(FixTypes.BLOCK_ENTITY, new ItemStackDataLists(TileEntityHugeCraftingTable.class, "Items"));
        FMLCommonHandler.instance().getDataFixer().registerVanillaWalker(FixTypes.BLOCK_ENTITY, new ItemStackDataLists(TileEntityExtremeCraftingTable.class, "Items"));

        OreDictionary.registerOre("blockBigCraftingTable", ModBlocks.BIG_CRAFTING_TABLE);
        OreDictionary.registerOre("blockHugeCraftingTable", ModBlocks.HUGE_CRAFTING_TABLE);
        OreDictionary.registerOre("blockExtremeCraftingTable", ModBlocks.EXTREME_CRAFTING_TABLE);
    }
}