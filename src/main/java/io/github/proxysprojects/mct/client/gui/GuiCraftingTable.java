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

import io.github.proxysprojects.mct.tile.TileEntityCraftingTable;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

import java.util.function.BiFunction;

public abstract class GuiCraftingTable<T extends TileEntityCraftingTable> extends GuiContainer {
    private final T craftingTable;
    private final InventoryPlayer playerInv;

    protected int craftingTextX, craftingTextY;
    protected int inventoryTextX, inventoryTextY;

    public GuiCraftingTable(T craftingTable, InventoryPlayer playerInv, BiFunction<T, InventoryPlayer, Container> containerFunction) {
        super(containerFunction.apply(craftingTable, playerInv));
        this.craftingTable = craftingTable;
        this.playerInv = playerInv;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRenderer.drawString(craftingTable.getDisplayName().getUnformattedText(), craftingTextX, craftingTextY, 0x404040);
        fontRenderer.drawString(playerInv.getDisplayName().getUnformattedText(), inventoryTextX, inventoryTextY, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        int xOffset = (width - xSize) / 2;
        int yOffset = (height - ySize) / 2;
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        drawCraftingGui(xOffset, yOffset);
    }

    protected abstract void drawCraftingGui(int xOffset, int yOffset);
}
