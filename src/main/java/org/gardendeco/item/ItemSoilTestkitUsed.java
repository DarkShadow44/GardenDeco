package org.gardendeco.item;

import org.gardendeco.MimicHandler;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.biome.Biomes;

public class ItemSoilTestkitUsed extends Item {

	public ItemSoilTestkitUsed(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		if (context.getHand() != InteractionHand.MAIN_HAND) {
			return super.useOn(context);
		}

		ItemStack stack = context.getItemInHand();

		if (!MimicHandler.tryMakeMimic(context.getLevel(), context.getClickedPos(), stack)) {
			return super.useOn(context);
		}

		stack.setDamageValue(stack.getDamageValue() + 1);
		if (stack.getDamageValue() >= stack.getMaxDamage()) {
			context.getPlayer().setItemInHand(context.getHand(), ItemStack.EMPTY);
		}
		return InteractionResult.SUCCESS;
	}

	@Override
	public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> stacks) {
		ItemStack stack = new ItemStack(this);
		CompoundTag tag = new CompoundTag();
		tag.putString("biomeKey", Biomes.PLAINS.getRegistryName().toString());
		stack.setTag(tag);
		stacks.add(stack);
	}
}
