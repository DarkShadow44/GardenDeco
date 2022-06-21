package org.gardendeco.item;

import org.gardendeco.MimicHandler;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

public class ItemSoilTestkitNullifier extends Item {

	public ItemSoilTestkitNullifier(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		InteractionHand interactionhand = context.getHand();
		if (interactionhand != InteractionHand.MAIN_HAND) {
			return super.useOn(context);
		}

		if (!MimicHandler.tryRemoveMimic(context.getLevel(), context.getClickedPos())) {
			return super.useOn(context);
		}

		return InteractionResult.SUCCESS;
	}

}
