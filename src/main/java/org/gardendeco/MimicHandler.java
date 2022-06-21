package org.gardendeco;

import org.gardendeco.block.EntityBlockMimic;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

public class MimicHandler {
	public static boolean tryMakeMimic(Level level, BlockPos pos, ItemStack stack) {
		BlockState state = level.getBlockState(pos);

		if (state.isAir()) {
			return false;
		}

		Block block = state.getBlock();

		CompoundTag tag = stack.getTag();
		if (tag == null)
			return false;

		String biomeKey = tag.getString("biomeKey");

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
		if (biomeKey.equals(entity.getBiomeKey()))
			return false; // Do nothing if already correct biome

		entity.setBiomeKey(biomeKey);
		level.sendBlockUpdated(pos, state, state, 0);

		return true;
	}

	public static int getBiomeColorFoliage(String biomeKey) {
		if (biomeKey == null)
			return -1;

		Biome biome = ForgeRegistries.BIOMES.getValue(new ResourceLocation(biomeKey));
		if (biome == null)
			return -1;

		return biome.getFoliageColor();
	}

	public static int getBiomeColorGrass(String biomeKey) {
		if (biomeKey == null)
			return -1;

		Biome biome = ForgeRegistries.BIOMES.getValue(new ResourceLocation(biomeKey));
		if (biome == null)
			return -1;

		return biome.getGrassColor(0, 0);
	}
}
