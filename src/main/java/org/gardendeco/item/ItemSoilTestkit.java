package org.gardendeco.item;

import org.gardendeco.MimicHandler;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;

public class ItemSoilTestkit extends BaseItemTestkit {

	public ItemSoilTestkit(Properties properties) {
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

		ItemStack stack = new ItemStack(this);
		CompoundTag tag = new CompoundTag();
		tag.putString("biomeKey", Biomes.PLAINS.location().toString());
		stack.setTag(tag);
		stacks.add(stack);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		return false;
	}
}
