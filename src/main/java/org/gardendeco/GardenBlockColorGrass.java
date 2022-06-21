package org.gardendeco;

import org.gardendeco.block.EntityBlockMimic;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

public class GardenBlockColorGrass implements BlockColor {

	@Override
	public int getColor(BlockState state, BlockAndTintGetter blockAndTintGetter, BlockPos pos, int unk) {
		int color = -1;
		EntityBlockMimic entity = (EntityBlockMimic) blockAndTintGetter.getBlockEntity(pos);
		if (entity != null) {
			color = MimicHandler.getBiomeColorGrass(entity.getBiomeKey());
		}

		return color != -1 ? color : BiomeColors.getAverageGrassColor(blockAndTintGetter, pos);
	}
}
