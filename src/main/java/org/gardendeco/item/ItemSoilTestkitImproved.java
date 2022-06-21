package org.gardendeco.item;

import org.gardendeco.MimicHandler;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemSoilTestkitImproved extends BaseItemTestkit {

	public ItemSoilTestkitImproved(Properties properties) {
		super(properties);
	}

	@Override
	public boolean applyTestkit(Level level, BlockPos pos, ItemStack stack) {
		return MimicHandler.tryMakeMimic(level, pos, stack);
	}

	@Override
	public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> stacks) {
		if (!allowdedIn(tab))
			return;
		for (ResourceLocation biome : ForgeRegistries.BIOMES.getKeys()) {
			ItemStack stack = BaseItemTestkit.createTestkit(this, biome.toString());
			stacks.add(stack);
		}
	}

	public boolean isFoil(ItemStack pStack) {
		return true;
	}
}
