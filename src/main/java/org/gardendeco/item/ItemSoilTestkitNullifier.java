package org.gardendeco.item;

import org.gardendeco.MimicHandler;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemSoilTestkitNullifier extends ItemSoilTestkit {

	public ItemSoilTestkitNullifier(Properties properties) {
		super(properties);
	}

	@Override
	public boolean applyTestkit(Level level, BlockPos pos, ItemStack stack) {
		return MimicHandler.tryRemoveMimic(level, pos);
	}

}
