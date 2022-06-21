package org.gardendeco.recipe;

import org.gardendeco.GardenDeco;
import org.gardendeco.item.BaseItemTestkit;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class RecipeTestkitImproved extends CustomRecipe {
	public RecipeTestkitImproved(ResourceLocation location) {
		super(location);
	}

	public boolean matches(CraftingContainer inv, Level level) {
		int countTestkit = 0;
		int countCatalyst = 0;

		for (int i = 0; i < inv.getContainerSize(); i++) {
			ItemStack stack = inv.getItem(i);
			if (!stack.isEmpty()) {
				if (stack.getItem() == GardenDeco.ITEM_SOIL_TESTKIT.get()) {
					countTestkit++;
				} else if (stack.getItem() == GardenDeco.ITEM_SOIL_CATALYST.get()) {
					countCatalyst++;
				} else {
					return false;
				}
			}
		}

		return countTestkit == 1 && countCatalyst == 1;
	}

	public ItemStack assemble(CraftingContainer inv) {
		String biomeKey = null;

		for (int i = 0; i < inv.getContainerSize(); i++) {
			ItemStack stack = inv.getItem(i);
			if (!stack.isEmpty()) {
				if (stack.getItem() == GardenDeco.ITEM_SOIL_TESTKIT.get()) {
					CompoundTag tag = stack.getTag();
					if (tag != null) {
						biomeKey = tag.getString("biomeKey");
					}
				}
			}
		}

		return BaseItemTestkit.createTestkit(GardenDeco.ITEM_SOIL_TESTKIT_IMPROVED.get(), biomeKey);
	}

	public boolean canCraftInDimensions(int width, int height) {
		return width * height >= 2;
	}

	public RecipeSerializer<?> getSerializer() {
		return GardenDeco.RECIPE_TESTKIT_IMPROVED.get();
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> ret = NonNullList.create();
		ret.add(Ingredient.of(GardenDeco.ITEM_SOIL_CATALYST.get()));
		ret.add(Ingredient.of(BaseItemTestkit.createTestkitDummy(GardenDeco.ITEM_SOIL_TESTKIT.get())));
		return ret;
	}

	@Override
	public ItemStack getResultItem() {
		return BaseItemTestkit.createTestkitDummy(GardenDeco.ITEM_SOIL_TESTKIT_IMPROVED.get());
	}
}
