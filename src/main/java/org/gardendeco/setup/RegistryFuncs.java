package org.gardendeco.setup;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

import org.gardendeco.GardenDeco;
import org.gardendeco.item.BlockItemMimic;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RegistryFuncs {

	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, GardenDeco.MODID);
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, GardenDeco.MODID);
	private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, GardenDeco.MODID);

	@SafeVarargs
	public static RegistryObject<BlockEntityType<?>> registerBlockEntity(String name, BlockEntitySupplier<? extends BlockEntity> constructor, RegistryObject<Block>... validBlocks) {
		return BLOCK_ENTITIES.register(name, () -> {
			Block[] blocks = Stream.of(validBlocks).map(block -> block.get()).toArray(Block[]::new);
			return BlockEntityType.Builder.of(constructor, blocks).build(null);
		});
	}

	@SuppressWarnings("deprecation")
	public static RegistryObject<Block> registerMimicBlock(String name, Block original, BiFunction<BlockBehaviour.Properties, Block, Block> constructor) {
		return BLOCKS.register(name, () -> {
			BlockState originalState = original.defaultBlockState();
			BlockBehaviour.Properties properties = BlockBehaviour.Properties.of(originalState.getMaterial())
					.destroyTime(original.defaultDestroyTime())
					.explosionResistance(originalState.getExplosionResistance(null, null, null))
					.sound(originalState.getSoundType());
			if (originalState.isRandomlyTicking()) {
				properties.randomTicks();
			}
			if (!originalState.canOcclude()) {
				properties.noOcclusion();
			}
			if (original.getCollisionShape(originalState, null, null, null).isEmpty()) {
				properties.noCollission();
			}
			return constructor.apply(properties, original);
		});
	}

	public static RegistryObject<Item> registerItem(String name, Function<Item.Properties, Item> constructor, int stackSize) {
		return ITEMS.register(name, () -> {
			Item.Properties properties = new Item.Properties()
					.stacksTo(stackSize)
					.tab(GardenDeco.TAB_GARDEN);
			return constructor.apply(properties);
		});
	}

	public static RegistryObject<Item> registerMimicBlockItem(String name, RegistryObject<Block> block) {
		return registerItem(name, properties -> new BlockItemMimic(block.get(), properties), 64);
	}

	public static void register() {
		BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		BLOCK_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
}
