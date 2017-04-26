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
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class ExtendedCraftingRegistry {
    private static final List<IExtendedRecipe> RECIPES = new ArrayList<>();

    public static List<IExtendedRecipe> getRecipes() {
        return RECIPES;
    }

    public static void registerRecipe(IExtendedRecipe recipe) {
        RECIPES.add(recipe);
    }

    public static void registerShapedRecipe(Block result, Object... args) {
        registerRecipe(new ExtendedRecipeShaped(result, args));
    }

    public static void registerShapedRecipe(Item result, Object... args) {
        registerRecipe(new ExtendedRecipeShaped(result, args));
    }

    public static void registerShapedRecipe(@Nonnull ItemStack result, Object... args) {
        registerRecipe(new ExtendedRecipeShaped(result, args));
    }

    public static void registerShapelessRecipe(Block result, Object... args) {
        registerRecipe(new ExtendedRecipeShapeless(result, args));
    }

    public static void registerShapelessRecipe(Item result, Object... args) {
        registerRecipe(new ExtendedRecipeShapeless(result, args));
    }

    public static void registerShapelessRecipe(@Nonnull ItemStack result, Object... args) {
        registerRecipe(new ExtendedRecipeShapeless(result, args));
    }

    public static ItemStack getCraftingResult(InventoryCrafting craftingInv, World world) {
        for (IExtendedRecipe recipe : RECIPES)
            if (recipe.matches(craftingInv, world))
                return recipe.getCraftingResult(craftingInv);
        return ItemStack.EMPTY;
    }

    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting craftingInv, World world) {
        for (IExtendedRecipe recipe : RECIPES)
            if (recipe.matches(craftingInv, world))
                return recipe.getRemainingItems(craftingInv);

        NonNullList<ItemStack> stacks = NonNullList.withSize(craftingInv.getSizeInventory(), ItemStack.EMPTY);
        for (int index = 0; index < stacks.size(); ++index)
            stacks.set(index, craftingInv.getStackInSlot(index));
        return stacks;
    }

    static {
        // TODO: implement and register vanilla wrapper
    }
}
