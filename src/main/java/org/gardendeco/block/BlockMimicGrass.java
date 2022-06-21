package org.gardendeco.block;

import java.util.List;
import java.util.Random;

import org.gardendeco.color.ColorType;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext.Builder;

public class BlockMimicGrass extends GrassBlock implements EntityBlock, IMimicBlock {

	private GrassBlock original;

	public BlockMimicGrass(Properties properties, Block original) {
		super(properties);
		this.original = (GrassBlock) original;
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<ItemStack> getDrops(BlockState state, Builder builder) {
		return original.getDrops(state, builder);
	}

	@Override
	public String getDescriptionId() {
		return original.getDescriptionId();
	}

	@Override
	public void performBonemeal(ServerLevel level, Random random, BlockPos pos, BlockState state) {
		original.performBonemeal(level, random, pos, state);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new EntityBlockMimic(pos, state);
	}

	@Override
	public Block getOriginal() {
		return original;
	}

	@Override
	public ColorType getColorType() {
		return ColorType.GRASS;
	}
}
