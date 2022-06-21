package org.gardendeco;

import org.gardendeco.block.EntityBlockMimic;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class MimicHandler {
	public static boolean TryMakeMimic(Level level, BlockPos pos, ItemStack stack) {
		BlockState state = level.getBlockState(pos);

		if (state.isAir()) {
			return false;
		}

		Block block = state.getBlock();

		CompoundTag tag = stack.getTag();
		if (tag == null)
			return false;

		BlockPos biomePos = BlockPos.of(tag.getLong("biomePos"));

		Block target = null;

		if (block == Blocks.GRASS_BLOCK || block == GardenDeco.BLOCK_MIMIC_GRASS.get()) {
			target = GardenDeco.BLOCK_MIMIC_GRASS.get();
		}

		if (block == Blocks.FERN || block == GardenDeco.BLOCK_MIMIC_FERN.get()) {
			target = GardenDeco.BLOCK_MIMIC_FERN.get();
		}

		if (target == null)
			return false;

		level.setBlockAndUpdate(pos, target.defaultBlockState());
		EntityBlockMimic entity = (EntityBlockMimic) level.getBlockEntity(pos);
		if (biomePos.equals(entity.getBiomePos()))
			return false; // Do nothing if already correct biome

		entity.setBiomePos(biomePos);
		level.sendBlockUpdated(pos, state, state, 0);

		return true;
	}
}
