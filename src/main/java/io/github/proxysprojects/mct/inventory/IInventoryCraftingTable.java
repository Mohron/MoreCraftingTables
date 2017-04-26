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

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public interface IInventoryCraftingTable extends IInventory {
    /**
     * Gets the width of the crafting grid.
     *
     * @return The crafting grid width
     */
    int getCraftingGridWidth();

    /**
     * Gets the height of the crafting grid.
     *
     * @return The crafting grid height
     */
    int getCraftingGridHeight();

    /**
     * Gets the size of the crafting grid.
     *
     * @return The crafting grid size
     */
    default int getCraftingGridSize() {
        return getCraftingGridWidth() * getCraftingGridHeight();
    }

    /**
     * Converts a crafting grid index into an inventory index.
     *
     * @param gridIndex The grid index
     * @return The inventory index
     */
    int getCraftingGridIndex(int gridIndex);

    /**
     * Gets an item stack in the crafting grid.
     *
     * @param gridIndex The grid index
     * @return The grid stack
     */
    default ItemStack getCraftingGridStack(int gridIndex) {
        return getStackInSlot(getCraftingGridIndex(gridIndex));
    }

    /**
     * Sets an item stack in the crafting grid.
     *
     * @param gridIndex The grid index
     * @param stack The item stack
     */
    default void setCraftingGridStack(int gridIndex, ItemStack stack) {
        setInventorySlotContents(getCraftingGridIndex(gridIndex), stack);
    }
}
