package org.gardendeco.item;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemSoilTestkitImproved extends ItemSoilTestkit {

	public ItemSoilTestkitImproved(Properties properties) {
		super(properties);
	}

	@Override
	public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> stacks) {
		for (ResourceLocation biome : ForgeRegistries.BIOMES.getKeys()) {
			ItemStack stack = new ItemStack(this);
			CompoundTag tag = new CompoundTag();
			tag.putString("biomeKey", biome.toString());
			stack.setTag(tag);
			stacks.add(stack);
		}
	}
}
