package org.gardendeco.item;

import org.gardendeco.MimicHandler;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

public class ItemSoilTestkitUsed extends Item {

	public ItemSoilTestkitUsed(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		if (context.getHand() != InteractionHand.MAIN_HAND) {
			return super.useOn(context);
		}

		if (!MimicHandler.TryMakeMimic(context.getLevel(), context.getClickedPos(), context.getItemInHand())) {
			return super.useOn(context);
		}

		return InteractionResult.SUCCESS;
	}
}
