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

import io.github.proxysprojects.mct.crafting.ExtendedCraftingRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class ContainerCraftingTable extends ContainerBase {
    protected static final String CRAFTING_RESULT_ID = "craftingresult";
    protected static final String CRAFTING_GRID_ID = "craftinggrid";

    private final World world;
    private final IInventoryCraftingTable craftingTableInv;
    private final InventoryCrafting craftingGridInv;
    private final IInventory craftingResultInv = new InventoryCraftResult();

    public ContainerCraftingTable(World world, IInventoryCraftingTable craftingTableInv, InventoryPlayer playerInv) {
        this.world = world;
        this.craftingTableInv = craftingTableInv;

        craftingGridInv = new InventoryCraftingGrid(this, craftingTableInv);
        initSlots(craftingGridInv, craftingResultInv, playerInv);
        updateCraftingResult();
    }

    /**
     * Called from the constructor to register slot types and add slots.
     *
     * @param craftingGridInv The crafting grid inventory
     * @param craftingResultInv The crafting result inventory
     * @param playerInv The player inventory
     */
    protected void initSlots(InventoryCrafting craftingGridInv, IInventory craftingResultInv, InventoryPlayer playerInv) {
        registerSlotType(CRAFTING_RESULT_ID, 1);
        registerSlotType(CRAFTING_GRID_ID, craftingGridInv.getWidth() * craftingGridInv.getHeight());
        registerSlotType(PLAYER_STORAGE_ID, PLAYER_STORAGE_WIDTH * PLAYER_STORAGE_HEIGHT);
        registerSlotType(PLAYER_HOTBAR_ID, PLAYER_HOTBAR_SIZE);
    }

    /**
     * Updates the crafting result based on the items that are currently in the
     * crafting grid.
     */
    public void updateCraftingResult() {
        craftingResultInv.setInventorySlotContents(0, ExtendedCraftingRegistry.getCraftingResult(craftingGridInv, world));
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventory) {
        updateCraftingResult();
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return craftingTableInv.isUsableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        Slot slot = inventorySlots.get(index);

        if (slot == null || !slot.getHasStack())
            return ItemStack.EMPTY;

        String slotTypeID = getSlotTypeID(slot);

        if (slotTypeID == null)
            return ItemStack.EMPTY;

        ItemStack slotStack = slot.getStack();
        ItemStack stack = slotStack.copy();

        switch (slotTypeID) {
            // Transfer from crafting result to player storage or hotbar
            case CRAFTING_RESULT_ID:
                slotStack.getItem().onCreated(slotStack, world, player);
                if (!transferStackTo(slotStack, PLAYER_INV_IDS, true))
                    return ItemStack.EMPTY;
                slot.onSlotChange(slotStack, stack);
                break;

            // Transfer from player storage to hotbar
            case PLAYER_STORAGE_ID:
                if (!transferStackTo(slotStack, PLAYER_HOTBAR_ID, false))
                    return ItemStack.EMPTY;
                break;

            // Transfer from player hotbar to inventory
            case PLAYER_HOTBAR_ID:
                if (!transferStackTo(slotStack, PLAYER_STORAGE_ID, false))
                    return ItemStack.EMPTY;
                break;

            // Transfer from crafting grid to player storage or hotbar
            case CRAFTING_GRID_ID:
            default:
                if (!transferStackTo(slotStack, PLAYER_INV_IDS, false))
                    return ItemStack.EMPTY;
                break;
        }

        if (slotStack.isEmpty())
            slot.putStack(ItemStack.EMPTY);
        else
            slot.onSlotChanged();

        if (slotStack.getCount() == stack.getCount())
            return ItemStack.EMPTY;

        if (slotTypeID.equals(CRAFTING_RESULT_ID))
            player.dropItem(slot.onTake(player, slotStack), false);

        return stack;
    }
}
