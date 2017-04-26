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
package io.github.proxysprojects.mct.crafting;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import javax.annotation.Nonnull;
import java.util.List;

public class ExtendedRecipeShaped extends ShapedOreRecipe implements IExtendedRecipe {
    public ExtendedRecipeShaped(Block result, Object... args) {
        super(result, args);
    }

    public ExtendedRecipeShaped(Item result, Object... args) {
        super(result, args);
    }

    public ExtendedRecipeShaped(@Nonnull ItemStack result, Object... args) {
        super(result, args);
    }

    @Override
    public boolean matches(InventoryCrafting craftingInv, World world) {
        for (int x = 0; x <= craftingInv.getWidth() - width; x++) {
            for (int y = 0; y <= craftingInv.getHeight() - height; y++) {
                if (checkMatch(craftingInv, x, y, false))
                    return true;
                if (mirrored && checkMatch(craftingInv, x, y, true))
                    return true;
            }
        }
        return false;
    }

    @Override
    protected boolean checkMatch(InventoryCrafting craftingInv, int startX, int startY, boolean mirrored) {
        for (int x = 0; x < craftingInv.getWidth(); x++) {
            yLoop:
            for (int y = 0; y < craftingInv.getHeight(); y++) {
                int subX = x - startX;
                int subY = y - startY;
                Object ingredient = null;
                if (subX >= 0 && subY >= 0 && subX < width && subY < height) {
                    if (mirrored)
                        ingredient = input[width - subX - 1 + subY * width];
                    else
                        ingredient = input[subX + subY * width];
                }

                ItemStack existingStack = craftingInv.getStackInRowAndColumn(x, y);
                if (ingredient instanceof ItemStack) {
                    if (!OreDictionary.itemMatches((ItemStack) ingredient, existingStack, false))
                        return false;
                } else if (ingredient instanceof List) {
                    for (ItemStack ingredientStack : (List<ItemStack>) ingredient)
                        if (OreDictionary.itemMatches(ingredientStack, existingStack, false))
                            continue yLoop;
                    return false;
                } else if (ingredient == null && !existingStack.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }
}
