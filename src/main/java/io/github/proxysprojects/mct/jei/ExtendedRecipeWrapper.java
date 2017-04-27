package io.github.proxysprojects.mct.jei;

import io.github.proxysprojects.mct.crafting.ExtendedRecipeShaped;
import io.github.proxysprojects.mct.crafting.IExtendedRecipe;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;

import javax.annotation.Nonnull;

public class ExtendedRecipeWrapper extends BlankRecipeWrapper {

	private final IExtendedRecipe recipe;

	public ExtendedRecipeWrapper(IExtendedRecipe recipe) {
		this.recipe = recipe;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {

	}
}
