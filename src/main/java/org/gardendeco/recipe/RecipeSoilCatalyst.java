package org.gardendeco.recipe;

import org.gardendeco.GardenDeco;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class RecipeSoilCatalyst extends CustomRecipe {
	public RecipeSoilCatalyst(ResourceLocation location) {
		super(location);
	}

	public boolean matches(CraftingContainer inv, Level level) {
		int countDiamond = 0;
		int countEnderPearl = 0;
		int countNetherQuartz = 0;
		int countWaterBottle = 0;

		for (int i = 0; i < inv.getContainerSize(); i++) {
			ItemStack stack = inv.getItem(i);
			if (!stack.isEmpty()) {
				if (stack.getItem() == Items.DIAMOND) {
					countDiamond++;
				} else if (stack.getItem() == Items.ENDER_PEARL) {
					countEnderPearl++;
				} else if (stack.getItem() == Items.QUARTZ) {
					countNetherQuartz++;
				} else if (stack.getItem() == Items.POTION) {
					if (PotionUtils.getPotion(stack.getTag()) == Potions.EMPTY) {
						countWaterBottle++;
					} else {
						return false;
					}
				} else {
					return false;
				}
			}
		}

		return countDiamond == 1 && countEnderPearl == 1 && countWaterBottle == 1 && countNetherQuartz == 1;
	}

	public ItemStack assemble(CraftingContainer inv) {
		return new ItemStack(GardenDeco.ITEM_SOIL_CATALYST.get());
	}

	public boolean canCraftInDimensions(int width, int height) {
		return width * height >= 4;
	}

	public RecipeSerializer<?> getSerializer() {
		return GardenDeco.RECIPE_SOIL_CATALYST.get();
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> ret = NonNullList.create();
		ItemStack stack = new ItemStack(Items.POTION);
		stack = PotionUtils.setPotion(stack, Potions.EMPTY);
		ret.add(Ingredient.of(stack));
		ret.add(Ingredient.of(Items.DIAMOND));
		ret.add(Ingredient.of(Items.ENDER_PEARL));
		ret.add(Ingredient.of(Items.QUARTZ));
		return ret;
	}

	@Override
	public ItemStack getResultItem() {
		return new ItemStack(GardenDeco.ITEM_SOIL_CATALYST.get());
	}
}
