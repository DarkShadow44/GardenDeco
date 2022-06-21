package org.gardendeco.setup;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.gardendeco.GardenDeco;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RegistryFuncs {

	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, GardenDeco.MODID);
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, GardenDeco.MODID);
	private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, GardenDeco.MODID);
	private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, GardenDeco.MODID);

	public static RegistryObject<BlockEntityType<?>> registerBlockEntity(String name, BlockEntitySupplier<? extends BlockEntity> constructor, List<RegistryObject<Block>> validBlocks) {
		return BLOCK_ENTITIES.register(name, () -> {
			Block[] blocks = validBlocks.stream().map(block -> block.get()).toArray(Block[]::new);
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

	public static RegistryObject<Item> registerItem(String name, Function<Item.Properties, Item> constructor, int stackSize, int durability) {
		return ITEMS.register(name, () -> {
			Item.Properties properties = new Item.Properties()
					.stacksTo(stackSize)
					.durability(durability)
					.setNoRepair()
					.tab(GardenDeco.TAB_GARDEN);
			return constructor.apply(properties);
		});
	}

	public static RegistryObject<RecipeSerializer<?>> registerRecipeSerializer(String name, Function<ResourceLocation, Recipe<?>> constructor) {
		return RECIPE_SERIALIZERS.register(name, () -> {
			return new SimpleRecipeSerializer<>(constructor);
		});
	}

	public static void register() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		BLOCKS.register(bus);
		ITEMS.register(bus);
		BLOCK_ENTITIES.register(bus);
		RECIPE_SERIALIZERS.register(bus);
	}
}
