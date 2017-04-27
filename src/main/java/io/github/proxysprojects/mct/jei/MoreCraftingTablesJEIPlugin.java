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

		registry.handleRecipes(ExtendedRecipeShaped.class, ExtendedRecipeWrapper::new, EXTREME_RECIPE_CATEGORY_UID);
		registry.handleRecipes(ExtendedRecipeShapeless.class, ExtendedRecipeWrapper::new, EXTREME_RECIPE_CATEGORY_UID);

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
