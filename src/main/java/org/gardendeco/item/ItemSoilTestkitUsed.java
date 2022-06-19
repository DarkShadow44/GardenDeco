package org.gardendeco.item;

import org.gardendeco.GardenDeco;
import org.gardendeco.block.EntityBlockMimic;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ItemSoilTestkitUsed extends Item {

	public ItemSoilTestkitUsed(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		BlockPos pos = context.getClickedPos();
		Level level = context.getLevel();
		BlockState state = level.getBlockState(pos);
		InteractionHand interactionhand = context.getHand();
		if (state.isAir() || interactionhand != InteractionHand.MAIN_HAND) {
			return super.useOn(context);
		}

		Block block = state.getBlock();
		CompoundTag tag = context.getItemInHand().getTag();
		if (tag == null)
			return super.useOn(context);

		BlockPos biomePos = BlockPos.of(tag.getLong("biomePos"));

		Block target = null;

		if (block == Blocks.GRASS_BLOCK || block == GardenDeco.BLOCK_MIMIC_GRASS.get()) {
			target = GardenDeco.BLOCK_MIMIC_GRASS.get();
		}

		if (target != null) {
			level.setBlockAndUpdate(pos, target.defaultBlockState());
			EntityBlockMimic entity = (EntityBlockMimic) level.getBlockEntity(pos);
			entity.setBiomePos(biomePos);
		}

		return InteractionResult.SUCCESS;
	}
}
