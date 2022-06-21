package org.gardendeco.item;

import org.gardendeco.MimicHandler;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemSoilTestkitImproved extends ItemSoilTestkitUsed {

	public ItemSoilTestkitImproved(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		if (context.getHand() != InteractionHand.MAIN_HAND) {
			return super.useOn(context);
		}

		if (!MimicHandler.tryMakeMimic(context.getLevel(), context.getClickedPos(), context.getItemInHand())) {
			return super.useOn(context);
		}

		return InteractionResult.SUCCESS;
	}

	@Override
	public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> stacks) {
		for (ResourceLocation biome : ForgeRegistries.BIOMES.getKeys()) {
			ItemStack stack = new ItemStack(this);
			CompoundTag tag = new CompoundTag();
			tag.putString("biomeKey", biome.toString());
			stack.setTag(tag);
			stacks.add(stack);
		}
	}
}
