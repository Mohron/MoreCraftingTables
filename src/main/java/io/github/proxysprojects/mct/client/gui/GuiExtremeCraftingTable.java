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
package io.github.proxysprojects.mct.client.gui;

import io.github.proxysprojects.mct.MoreCraftingTables;
import io.github.proxysprojects.mct.inventory.ContainerExtremeCraftingTable;
import io.github.proxysprojects.mct.tile.TileEntityExtremeCraftingTable;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiExtremeCraftingTable extends GuiCraftingTable<TileEntityExtremeCraftingTable> {
    private static final ResourceLocation CRAFTING_TEXTURE_LOCATION = new ResourceLocation(MoreCraftingTables.MOD_ID, "textures/gui/extreme_crafting_table.png");
    private static final ResourceLocation INVENTORY_TEXTURE_LOCATION = new ResourceLocation(MoreCraftingTables.MOD_ID, "textures/gui/extreme_crafting_table_inventory.png");

    private final int ySizeCrafting = 185;
    private final int ySizeInventory = 93;
    private final int yOverlap = 4;

    public GuiExtremeCraftingTable(TileEntityExtremeCraftingTable craftingTable, InventoryPlayer playerInv) {
        super(craftingTable, playerInv, ContainerExtremeCraftingTable::new);
        xSize = 238;
        ySize = ySizeCrafting + ySizeInventory - yOverlap;
        craftingTextX = 8;
        craftingTextY = 6;
        inventoryTextX = 8;
        inventoryTextY = ySize - 96 + 2;
    }

    @Override
    protected void drawCraftingGui(int xOffset, int yOffset) {
        mc.getTextureManager().bindTexture(CRAFTING_TEXTURE_LOCATION);
        drawTexturedModalRect(xOffset, yOffset, 0, 0, xSize, ySizeCrafting);
        mc.getTextureManager().bindTexture(INVENTORY_TEXTURE_LOCATION);
        drawTexturedModalRect(xOffset, yOffset + ySizeCrafting - yOverlap, 0, 0, xSize, ySizeInventory);
    }
}
