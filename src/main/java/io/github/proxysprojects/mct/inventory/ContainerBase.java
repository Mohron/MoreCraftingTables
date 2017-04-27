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

import com.google.common.collect.Lists;
import io.github.proxysprojects.mct.inventory.util.CompositeListIterator;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class ContainerBase extends Container {
    protected static final String PLAYER_STORAGE_ID = "playerstorage";
    protected static final String PLAYER_HOTBAR_ID = "playerhotbar";
    protected static final String[] PLAYER_INV_IDS = {PLAYER_STORAGE_ID, PLAYER_HOTBAR_ID};

    protected static final int SLOT_SIZE = 16;
    protected static final int SLOT_PADDING = 2;
    protected static final int SLOT_OFFSET = SLOT_SIZE + SLOT_PADDING;

    protected static final int PLAYER_STORAGE_WIDTH = 9;
    protected static final int PLAYER_STORAGE_HEIGHT = 3;
    protected static final int PLAYER_HOTBAR_SIZE = 9;

    private final Map<String, List<Slot>> typeSlotMap = new HashMap<>();

    /**
     * Registers a slot type for this container.
     *
     * @param slotTypeID The slot type ID
     * @param slotCount The predicted number of slots that will be of this type
     */
    protected void registerSlotType(String slotTypeID, int slotCount) {
        typeSlotMap.put(slotTypeID, new ArrayList<>(slotCount));
    }

    /**
     * Gets the list of slots of a specified slot type.
     *
     * @param slotTypeID The slot type ID
     * @return The list of slots
     */
    protected List<Slot> getTypeSlots(String slotTypeID) {
        List<Slot> slots = typeSlotMap.get(slotTypeID);

        if (slots == null)
            throw new IllegalArgumentException(String.format("Invalid slot type ID: %s", slotTypeID));

        return slots;
    }

    /**
     * Gets the slot type ID of a slot.
     *
     * @param slot The slot
     * @return The slot type ID
     */
    @Nullable
    protected String getSlotTypeID(@Nullable Slot slot) {
        if (slot == null)
            return null;

        for (Map.Entry<String, List<Slot>> typeSlotEntry : typeSlotMap.entrySet())
            if (typeSlotEntry.getValue().contains(slot))
                return typeSlotEntry.getKey();

        return null;
    }

    /**
     * Adds a slot of the specified slot type to the container.
     *
     * @param slotTypeID The slot type ID
     * @param slot The slot to be added
     */
    protected void addSlot(String slotTypeID, Slot slot) {
        List<Slot> slots = getTypeSlots(slotTypeID);
        slots.add(slot);
        addSlotToContainer(slot);
    }

    /**
     * Adds slots from an inventory of the specified width and height.
     *
     * @param slotTypeID The slot type ID of the slots to be added
     * @param inventory The inventory
     * @param inventoryWidth The width of the inventory (e.g. 9 for player inventory storage and hotbar)
     * @param inventoryHeight The height of the inventory (e.g. 3 for player inventory storage)
     * @param xOffset The X offset at which the slots will be rendered
     * @param yOffset The Y offset at which the slots will be rendered
     * @param startIndex The index at which the slots begin in the inventory (e.g. 9 for player inventory storage)
     */
    protected void addInventorySlots(String slotTypeID, IInventory inventory, int inventoryWidth, int inventoryHeight, int xOffset, int yOffset, int startIndex) {
        for (int yIndex = 0; yIndex < inventoryHeight; ++yIndex) {
            for (int xIndex = 0; xIndex < inventoryWidth; ++xIndex) {
                int index = startIndex + xIndex + yIndex * inventoryWidth;
                int x = xOffset + xIndex * SLOT_OFFSET;
                int y = yOffset + yIndex * SLOT_OFFSET;
                addSlot(slotTypeID, new Slot(inventory, index, x, y));
            }
        }
    }

    /**
     * Transfers an item stack to slots with the specified slot type IDs.
     *
     * @param stack The item stack
     * @param slotTypeIDs The slot type IDs of the slots to which the stack will be transferred
     * @param reverseDirection Whether or not the stack will be added in reverse direction
     * @return Whether or not the stack was transferred
     */
    protected boolean transferStackTo(ItemStack stack, String[] slotTypeIDs, boolean reverseDirection) {
        List<List<Slot>> slotsList = Arrays.stream(slotTypeIDs).map(this::getTypeSlots).collect(Collectors.toList());
        return transferStackTo(stack, () -> new CompositeListIterator<>(slotsList), reverseDirection);
    }

    /**
     * Transfers an item stack to slots with the specified slot type ID.
     *
     * @param stack The item stack
     * @param slotTypeID The slot type IDs of the slots to which the stack will be transferred
     * @param reverseDirection Whether or not the stack will be added in reverse direction
     * @return Whether or not the stack was transferred
     */
    protected boolean transferStackTo(ItemStack stack, String slotTypeID, boolean reverseDirection) {
        List<Slot> slots = getTypeSlots(slotTypeID);
        if (reverseDirection)
            slots = Lists.reverse(slots);
        return transferStackTo(stack, slots::iterator, reverseDirection);
    }

    private  boolean transferStackTo(ItemStack stack, Supplier<Iterator<Slot>> slotIteratorSupplier, boolean reverseDirection) {
        boolean transferred = false;

        if (stack.isStackable()) {
            Iterator<Slot> slotIterator = slotIteratorSupplier.get();
            while (!stack.isEmpty()) {
                if (!slotIterator.hasNext())
                    break;

                Slot slot = slotIterator.next();
                ItemStack existingStack = slot.getStack();

                if (existingStack.isEmpty())
                    continue;
                if (existingStack.getItem() != stack.getItem())
                    continue;
                if (stack.getHasSubtypes() && stack.getMetadata() != existingStack.getMetadata())
                    continue;
                if (!ItemStack.areItemStackTagsEqual(stack, existingStack))
                    continue;

                int newCount = existingStack.getCount() + stack.getCount();
                int maxCount = Math.min(slot.getSlotStackLimit(), stack.getMaxStackSize());
                if (newCount <= maxCount) {
                    stack.setCount(0);
                    existingStack.setCount(newCount);
                    slot.onSlotChanged();
                    transferred = true;
                } else if (existingStack.getCount() < maxCount) {
                    stack.shrink(maxCount - existingStack.getCount());
                    existingStack.setCount(maxCount);
                    slot.onSlotChanged();
                    transferred = true;
                }
            }
        }

        if (!stack.isEmpty()) {
            Iterator<Slot> slotIterator = slotIteratorSupplier.get();
            while (slotIterator.hasNext()) {
                Slot slot = slotIterator.next();
                ItemStack existingStack = slot.getStack();
                if (existingStack.isEmpty() && slot.isItemValid(stack)) {
                    int newCount = Math.min(stack.getCount(), slot.getSlotStackLimit());
                    slot.putStack(stack.splitStack(newCount));
                    slot.onSlotChanged();
                    transferred = true;
                    break;
                }
            }
        }

        return transferred;
    }
}
