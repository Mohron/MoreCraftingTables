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
package io.github.proxysprojects.mct.network;

import io.github.proxysprojects.mct.MoreCraftingTables;
import io.github.proxysprojects.mct.client.gui.GuiBigCraftingTable;
import io.github.proxysprojects.mct.client.gui.GuiExtremeCraftingTable;
import io.github.proxysprojects.mct.client.gui.GuiHugeCraftingTable;
import io.github.proxysprojects.mct.inventory.ContainerBigCraftingTable;
import io.github.proxysprojects.mct.inventory.ContainerExtremeCraftingTable;
import io.github.proxysprojects.mct.inventory.ContainerHugeCraftingTable;
import io.github.proxysprojects.mct.tile.TileEntityBigCraftingTable;
import io.github.proxysprojects.mct.tile.TileEntityExtremeCraftingTable;
import io.github.proxysprojects.mct.tile.TileEntityHugeCraftingTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {
    public static final int BIG_CRAFTING_TABLE_ID = 0;
    public static final int HUGE_CRAFTING_TABLE_ID = 1;
    public static final int EXTREME_CRAFTING_TABLE_ID = 2;

    public static void openGui(EntityPlayer player, int guiID, World world, int x, int y, int z) {
        FMLNetworkHandler.openGui(player, MoreCraftingTables.instance, guiID, world, x, y, z);
    }

    @Override
    @Nullable
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
        if (id == BIG_CRAFTING_TABLE_ID && te instanceof TileEntityBigCraftingTable)
            return new ContainerBigCraftingTable((TileEntityBigCraftingTable) te, player.inventory);
        if (id == HUGE_CRAFTING_TABLE_ID && te instanceof TileEntityHugeCraftingTable)
            return new ContainerHugeCraftingTable((TileEntityHugeCraftingTable) te, player.inventory);
        if (id == EXTREME_CRAFTING_TABLE_ID && te instanceof TileEntityExtremeCraftingTable)
            return new ContainerExtremeCraftingTable((TileEntityExtremeCraftingTable) te, player.inventory);
        return null;
    }

    @Override
    @Nullable
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
        if (id == BIG_CRAFTING_TABLE_ID && te instanceof TileEntityBigCraftingTable)
            return new GuiBigCraftingTable((TileEntityBigCraftingTable) te, player.inventory);
        if (id == HUGE_CRAFTING_TABLE_ID && te instanceof TileEntityHugeCraftingTable)
            return new GuiHugeCraftingTable((TileEntityHugeCraftingTable) te, player.inventory);
        if (id == EXTREME_CRAFTING_TABLE_ID && te instanceof TileEntityExtremeCraftingTable)
            return new GuiExtremeCraftingTable((TileEntityExtremeCraftingTable) te, player.inventory);
        return null;
    }
}
