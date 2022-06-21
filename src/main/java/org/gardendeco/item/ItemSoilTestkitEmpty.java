package org.gardendeco.item;

import org.gardendeco.GardenDeco;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class ItemSoilTestkitEmpty extends Item {

	public ItemSoilTestkitEmpty(Properties properties) {
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

		ItemStack stack = new ItemStack(GardenDeco.ITEM_SOIL_TESTKIT.get());
		String biomeKey = level.getBiome(pos).value().getRegistryName().toString();
		CompoundTag tag = new CompoundTag();
		tag.putString("biomeKey", biomeKey);
		stack.setTag(tag);
		context.getPlayer().setItemInHand(interactionhand, stack);
		return InteractionResult.SUCCESS;
	}

}
