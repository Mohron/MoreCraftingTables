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
package io.github.proxysprojects.mct.inventory;

import io.github.proxysprojects.mct.tile.TileEntityExtremeCraftingTable;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.SlotCrafting;

public class ContainerExtremeCraftingTable extends ContainerCraftingTable {
    public static final int CRAFTING_GRID_WIDTH = 9;
    public static final int CRAFTING_GRID_HEIGHT = 9;

    private static final int CRAFTING_GRID_X = 8;
    private static final int CRAFTING_GRID_Y = 17;
    private static final int CRAFTING_RESULT_X = 209;
    private static final int CRAFTING_RESULT_Y = 89;
    private static final int PLAYER_STORAGE_X = 8;
    private static final int PLAYER_STORAGE_Y = CRAFTING_GRID_Y + CRAFTING_GRID_HEIGHT * SLOT_OFFSET + 13;
    private static final int PLAYER_HOTBAR_X = PLAYER_STORAGE_X;
    private static final int PLAYER_HOTBAR_Y = PLAYER_STORAGE_Y + PLAYER_STORAGE_HEIGHT * SLOT_OFFSET + 4;

    public ContainerExtremeCraftingTable(TileEntityExtremeCraftingTable craftingTable, InventoryPlayer playerInv) {
        super(craftingTable.getWorld(), craftingTable, playerInv);
    }

    @Override
    protected void initSlots(InventoryCrafting craftingGridInv, IInventory craftingResultInv, InventoryPlayer playerInv) {
        super.initSlots(craftingGridInv, craftingResultInv, playerInv);
        addSlot(CRAFTING_RESULT_ID, new SlotCrafting(playerInv.player, craftingGridInv, craftingResultInv, 0, CRAFTING_RESULT_X, CRAFTING_RESULT_Y));
        addInventorySlots(CRAFTING_GRID_ID, craftingGridInv, CRAFTING_GRID_WIDTH, CRAFTING_GRID_HEIGHT, CRAFTING_GRID_X, CRAFTING_GRID_Y, 0);
        addInventorySlots(PLAYER_STORAGE_ID, playerInv, PLAYER_STORAGE_WIDTH, PLAYER_STORAGE_HEIGHT, PLAYER_STORAGE_X, PLAYER_STORAGE_Y, PLAYER_HOTBAR_SIZE);
        addInventorySlots(PLAYER_HOTBAR_ID, playerInv, PLAYER_HOTBAR_SIZE, 1, PLAYER_HOTBAR_X, PLAYER_HOTBAR_Y, 0);
    }
}
