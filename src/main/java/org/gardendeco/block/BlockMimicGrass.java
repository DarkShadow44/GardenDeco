package org.gardendeco.block;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext.Builder;

public class BlockMimicGrass extends GrassBlock implements EntityBlock {

	public BlockMimicGrass(Properties p_53685_) {
		super(p_53685_);
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<ItemStack> getDrops(BlockState state, Builder builder) {
		return Blocks.GRASS_BLOCK.getDrops(state, builder);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new EntityBlockMimic(pos, state);
	}
}
