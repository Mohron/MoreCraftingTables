package io.github.proxysprojects.mct.jei;

import io.github.proxysprojects.mct.MoreCraftingTables;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.ICraftingGridHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.wrapper.ICustomCraftingRecipeWrapper;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class ExtendedCraftingRecipeCategory extends BlankRecipeCategory<ExtendedRecipeWrapper> {

	private static final int craftOutputSlot = 0;
	private static final int craftInputSlot1 = 1;

	private final IDrawable background;
	private final String localizedName;
	private final ICraftingGridHelper craftingGridHelper;

	public ExtendedCraftingRecipeCategory(IGuiHelper guiHelper) {
		ResourceLocation location = new ResourceLocation(MoreCraftingTables.MOD_ID, "textures/gui/extreme_crafting_table_jei.png");
		background = guiHelper.createDrawable(location, 0, 0, 224, 162);
		localizedName = I18n.format("gui.mct.category.crafting");
		craftingGridHelper = guiHelper.createCraftingGridHelper(craftInputSlot1, craftOutputSlot);
	}

	@Override
	public String getUid() {
		return MoreCraftingTablesJEIPlugin.EXTREME_RECIPE_CATEGORY_UID;
	}

	@Override
	public String getTitle() {
		return localizedName;
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, ExtendedRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(craftOutputSlot, false, 167, 73);

		for (int y = 0; y < 9; ++y) {
			for (int x = 0; x < 9; ++x) {
				int index = craftInputSlot1 + x + (y * 9);
				guiItemStacks.init(index, true, x * 18, y * 18);
			}
		}

		if (recipeWrapper instanceof ICustomCraftingRecipeWrapper) {
			ICustomCraftingRecipeWrapper customWrapper = (ICustomCraftingRecipeWrapper) recipeWrapper;
			customWrapper.setRecipe(recipeLayout, ingredients);
			return;
		}

		List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
		List<List<ItemStack>> outputs = ingredients.getOutputs(ItemStack.class);

		if (recipeWrapper instanceof IShapedCraftingRecipeWrapper) {
			IShapedCraftingRecipeWrapper wrapper = (IShapedCraftingRecipeWrapper) recipeWrapper;
			craftingGridHelper.setInputs(guiItemStacks, inputs, wrapper.getWidth(), wrapper.getHeight());
		} else {
			craftingGridHelper.setInputs(guiItemStacks, inputs);
			recipeLayout.setShapeless();
		}
		guiItemStacks.set(craftOutputSlot, outputs.get(0));

	}
}
