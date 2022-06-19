package org.gardendeco.block;

import java.util.List;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext.Builder;

public class BlockMimicGrass extends GrassBlock {

	public BlockMimicGrass(Properties p_53685_) {
		super(p_53685_);
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<ItemStack> getDrops(BlockState state, Builder builder) {
		return Blocks.GRASS_BLOCK.getDrops(state, builder);
	}
}
