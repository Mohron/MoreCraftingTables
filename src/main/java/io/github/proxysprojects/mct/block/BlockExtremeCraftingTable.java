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

import io.github.proxysprojects.mct.network.GuiHandler;
import io.github.proxysprojects.mct.tile.TileEntityExtremeCraftingTable;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class BlockExtremeCraftingTable extends BlockCustomCraftingTable<TileEntityExtremeCraftingTable> {
    public BlockExtremeCraftingTable() {
        super(Material.ROCK, "extreme_crafting_table");
        setHardness(50.0F);
        setResistance(500.0F);
        setHarvestLevel("pickaxe", 3);
    }

    @Override
    protected Class<TileEntityExtremeCraftingTable> getCraftingTableClass() {
        return TileEntityExtremeCraftingTable.class;
    }

    @Override
    protected int getGuiId() {
        return GuiHandler.EXTREME_CRAFTING_TABLE_ID;
    }

    @Override
    public TileEntityExtremeCraftingTable createNewTileEntity(World world, int meta) {
        return new TileEntityExtremeCraftingTable();
    }
}