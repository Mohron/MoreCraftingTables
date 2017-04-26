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

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ExtendedCraftingRegistry {
    private static final List<IRecipe> RECIPES = new ArrayList<>();

    /**
     * Gets the list of registered extended crafting recipes.
     *
     * @return The list of recipes
     */
    public static List<IRecipe> getRecipes() {
        return RECIPES;
    }

    /**
     * Registers an extended crafting recipe.
     *
     * @param recipe The recipe
     */
    public static void registerRecipe(IRecipe recipe) {
        RECIPES.add(recipe);
    }

    /**
     * Gets the result of a crafting inventory.
     *
     * @param craftingInv The crafting inventory
     * @param world The world in which the inventory is located
     * @return The crafting result
     */
    public static ItemStack getCraftingResult(InventoryCrafting craftingInv, World world) {
        // TODO: implement this
        return CraftingManager.getInstance().findMatchingRecipe(craftingInv, world);
    }
}
