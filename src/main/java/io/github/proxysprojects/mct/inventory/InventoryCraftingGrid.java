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

import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;

public class InventoryCraftingGrid extends InventoryCrafting {
    private final IInventoryCraftingTable inventory;
    private Container container;

    public InventoryCraftingGrid(Container container, IInventoryCraftingTable inventory) {
        super(container, 0, 0);
        this.container = container;
        this.inventory = inventory;
    }

    @Override
    public int getSizeInventory() {
        return inventory.getCraftingGridSize();
    }

    @Override
    public boolean isEmpty() {
        for (int index = 0; index < getSizeInventory(); index++)
            if (!inventory.getCraftingGridStack(index).isEmpty())
                return false;
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return index < 0 || index >= getSizeInventory() ? ItemStack.EMPTY : inventory.getCraftingGridStack(index);
    }

    @Override
    public ItemStack getStackInRowAndColumn(int row, int column) {
        int width = inventory.getCraftingGridWidth();
        int height = inventory.getCraftingGridHeight();
        return row >= 0 && row < width && column >= 0 && column <= height ? getStackInSlot(row + column * width) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return inventory.removeStackFromSlot(inventory.getCraftingGridIndex(index));
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        ItemStack stack = inventory.decrStackSize(inventory.getCraftingGridIndex(index), count);
        if (!stack.isEmpty())
            container.onCraftMatrixChanged(this);
        return stack;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        inventory.setCraftingGridStack(index, stack);
        container.onCraftMatrixChanged(this);
    }

    @Override
    public void clear() {
        inventory.clear();
    }

    @Override
    public int getHeight() {
        return inventory.getCraftingGridHeight();
    }

    @Override
    public int getWidth() {
        return inventory.getCraftingGridWidth();
    }
}
