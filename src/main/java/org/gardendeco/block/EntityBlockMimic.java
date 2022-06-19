package org.gardendeco.block;

import org.gardendeco.GardenDeco;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class EntityBlockMimic extends BlockEntity {

	BlockPos biomePos = null;

	public EntityBlockMimic(BlockPos pos, BlockState state) {
		super(GardenDeco.BLOCK_ENTITY_MIMIC.get(), pos, state);
	}

	@Override
	protected void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		if (biomePos != null) {
			tag.putLong("pos", biomePos.asLong());
		}
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		biomePos = BlockPos.of(tag.getLong("pos"));
	}

	public BlockPos getBiomePos() {
		return biomePos;
	}

	public void setBiomePos(BlockPos pos) {
		biomePos = pos;
	}
}
