package org.gardendeco;

import org.gardendeco.block.EntityBlockMimic;
import org.gardendeco.block.IMimicBlock;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

public class GardenBlockColor implements BlockColor {

	@Override
	public int getColor(BlockState state, BlockAndTintGetter blockAndTintGetter, BlockPos pos, int unk) {
		int color = -1;
		ColorType type = ((IMimicBlock) state.getBlock()).getColorType();
		EntityBlockMimic entity = (EntityBlockMimic) blockAndTintGetter.getBlockEntity(pos);
		if (entity != null) {
			color = MimicHandler.getBiomeColor(entity.getBiomeKey(), type);
		}

		if (color == -1) {
			switch (type) {
			case GRASS:
				return BiomeColors.getAverageGrassColor(blockAndTintGetter, pos);
			case FOLIAGE:
				return BiomeColors.getAverageFoliageColor(blockAndTintGetter, pos);
			case WATER:
				return BiomeColors.getAverageWaterColor(blockAndTintGetter, pos);
			}
		}

		return color;
	}
}
