package org.gardendeco;

import org.gardendeco.block.EntityBlockMimic;
import org.gardendeco.block.IMimicBlock;
import org.gardendeco.color.ColorType;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

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

		for (RegistryObject<Block> blockHolder : GardenDeco.BLOCKS_MIMIC) {
			Block mimic = blockHolder.get();
			Block original = ((IMimicBlock) mimic).getOriginal();
			if (block == original || block == mimic) {
				target = mimic;
			}
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

	public static int getBiomeColor(String biomeKey, ColorType type) {
		if (biomeKey == null)
			return -1;

		Biome biome = ForgeRegistries.BIOMES.getValue(new ResourceLocation(biomeKey));
		if (biome == null)
			return -1;
		switch (type) {
		case GRASS:
			return biome.getGrassColor(0, 0);
		case FOLIAGE:
			return biome.getFoliageColor();
		case WATER:
			return biome.getWaterColor();
		}
		return -1;
	}

	public static boolean tryRemoveMimic(Level level, BlockPos pos) {
		BlockState state = level.getBlockState(pos);

		if (state.isAir()) {
			return false;
		}

		Block block = state.getBlock();
		Block target = null;

		for (RegistryObject<Block> blockHolder : GardenDeco.BLOCKS_MIMIC) {
			Block mimic = blockHolder.get();
			Block original = ((IMimicBlock) mimic).getOriginal();
			if (block == mimic) {
				target = original;
			}
		}

		if (target == null)
			return false;

		level.setBlockAndUpdate(pos, target.defaultBlockState());
		return true;
	}
}
