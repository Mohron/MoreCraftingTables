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
package io.github.proxysprojects.mct.jei;

import io.github.proxysprojects.mct.block.ModBlocks;
import io.github.proxysprojects.mct.client.gui.GuiBigCraftingTable;
import io.github.proxysprojects.mct.client.gui.GuiExtremeCraftingTable;
import io.github.proxysprojects.mct.client.gui.GuiHugeCraftingTable;
import io.github.proxysprojects.mct.crafting.ExtendedCraftingRegistry;
import io.github.proxysprojects.mct.crafting.ExtendedRecipeShaped;
import io.github.proxysprojects.mct.crafting.ExtendedRecipeShapeless;
import io.github.proxysprojects.mct.inventory.ContainerBigCraftingTable;
import io.github.proxysprojects.mct.inventory.ContainerExtremeCraftingTable;
import io.github.proxysprojects.mct.inventory.ContainerHugeCraftingTable;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class MoreCraftingTablesJEIPlugin extends BlankModPlugin {

	static final String EXTREME_RECIPE_CATEGORY_UID = "mct.crafting";

	@Override
	public void register(IModRegistry registry) {
		final IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		final IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

		registry.addRecipes(ExtendedCraftingRegistry.getRecipes(), EXTREME_RECIPE_CATEGORY_UID);

		registry.handleRecipes(ExtendedRecipeShaped.class, (recipe) -> new ExtendedShapedRecipeWrapper(jeiHelpers, recipe), EXTREME_RECIPE_CATEGORY_UID);
		registry.handleRecipes(ExtendedRecipeShapeless.class, (recipe) -> new ExtendedShapelessRecipeWrapper(jeiHelpers, recipe), EXTREME_RECIPE_CATEGORY_UID);

		registry.addRecipeCategories(new ExtendedCraftingRecipeCategory(guiHelper));

		// Add Clickable "Show Recipes" Area
		registry.addRecipeClickArea(GuiBigCraftingTable.class, 106, 49, 28, 23, EXTREME_RECIPE_CATEGORY_UID);
		registry.addRecipeClickArea(GuiHugeCraftingTable.class, 137, 67, 28, 23, EXTREME_RECIPE_CATEGORY_UID);
		registry.addRecipeClickArea(GuiExtremeCraftingTable.class, 173, 85, 28, 23, EXTREME_RECIPE_CATEGORY_UID);

		// Add Recipe Transfer Handler
		IRecipeTransferRegistry recipeTransferRegistry = registry.getRecipeTransferRegistry();
		recipeTransferRegistry.addRecipeTransferHandler(ContainerBigCraftingTable.class, EXTREME_RECIPE_CATEGORY_UID, 1, 25, 26, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerHugeCraftingTable.class, EXTREME_RECIPE_CATEGORY_UID, 1, 49, 50, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerExtremeCraftingTable.class, EXTREME_RECIPE_CATEGORY_UID, 1, 81, 82, 36);

		// Add Crafting Items
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.BIG_CRAFTING_TABLE), EXTREME_RECIPE_CATEGORY_UID);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.HUGE_CRAFTING_TABLE), EXTREME_RECIPE_CATEGORY_UID);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.EXTREME_CRAFTING_TABLE), EXTREME_RECIPE_CATEGORY_UID);
	}
}
