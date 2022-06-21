package org.gardendeco.item;

import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;

public abstract class BaseItemTestkit extends Item {

	public BaseItemTestkit(Properties properties) {
		super(properties);
	}

	public abstract boolean applyTestkit(Level level, BlockPos pos, ItemStack stack);

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		if (context.getHand() != InteractionHand.MAIN_HAND) {
			return super.useOn(context);
		}

		ItemStack stack = context.getItemInHand();

		if (!applyTestkit(level, pos, stack)) {
			return super.useOn(context);
		}

		if (stack.getMaxDamage() != 0) {
			stack.setDamageValue(stack.getDamageValue() + 1);
			if (stack.getDamageValue() >= stack.getMaxDamage()) {
				context.getPlayer().setItemInHand(context.getHand(), ItemStack.EMPTY);
			}
		}
		Random random = level.getRandom();
		for (int i = 0; i < 10; i++) {
			level.addParticle(ParticleTypes.HAPPY_VILLAGER,
					pos.getX() + 0.5 + random.nextGaussian() * 0.5,
					pos.getY() + 0.5 + random.nextGaussian() * 0.5,
					pos.getZ() + 0.5 + random.nextGaussian() * 0.5,
					0, 0, 0);
		}
		return InteractionResult.SUCCESS;
	}

	public static ItemStack createTestkit(Item item, String biomeKey) {
		ItemStack stack = new ItemStack(item);
		CompoundTag tag = new CompoundTag();
		tag.putString("biomeKey", biomeKey);
		stack.setTag(tag);
		return stack;
	}

	public static ItemStack createTestkitDummy(Item item) {
		return createTestkit(item, Biomes.PLAINS.location().toString());
	}
}
