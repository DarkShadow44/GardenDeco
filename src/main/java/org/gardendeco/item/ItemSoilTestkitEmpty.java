package org.gardendeco.item;

import org.gardendeco.GardenDeco;
import org.gardendeco.block.IMimicBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
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

		if (state.getBlock() instanceof IMimicBlock) {
			context.getPlayer().displayClientMessage(new TranslatableComponent("gardendeco.message.cannot_pick_mimic"), true);
			return super.useOn(context);
		}

		if (state.isAir() || interactionhand != InteractionHand.MAIN_HAND) {
			return super.useOn(context);
		}

		String biomeKey = level.getBiome(pos).value().getRegistryName().toString();
		ItemStack stack = BaseItemTestkit.createTestkit(GardenDeco.ITEM_SOIL_TESTKIT.get(), biomeKey);
		context.getPlayer().setItemInHand(interactionhand, stack);
		return InteractionResult.SUCCESS;
	}
}
