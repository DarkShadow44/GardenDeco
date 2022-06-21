package org.gardendeco.block;

import org.gardendeco.GardenDeco;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

public class EntityBlockMimic extends EntityBlockBase {

	String biomeKey = null;

	public EntityBlockMimic(BlockPos pos, BlockState state) {
		super(GardenDeco.BLOCK_ENTITY_MIMIC, pos, state);
	}

	@Override
	protected void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		if (biomeKey != null) {
			tag.putString("biomeKey", biomeKey);
		}
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		biomeKey = tag.getString("biomeKey");
	}

	public String getBiomeKey() {
		return biomeKey;
	}

	public void setBiomeKey(String biomeKey) {
		this.biomeKey = biomeKey;
		setChanged();
	}
}
