package org.gardendeco;

import java.util.function.Function;

import org.gardendeco.block.BlockMimicGrass;
import org.gardendeco.item.BlockItemMimic;
import org.gardendeco.item.ItemSoilTestkit;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(GardenDeco.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class GardenDeco {
	public static final String MODID = "gardendeco";

	public static TabGarden TAB_GARDEN = new TabGarden();

	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

	public static final RegistryObject<Block> BLOCK_MIMIC_GRASS = registerMimicBlock("mimic_grass", Blocks.GRASS_BLOCK, BlockMimicGrass::new);
	public static final RegistryObject<Item> ITEM_MIMIC_GRASS = registerMimicBlockItem("mimic_grass", BLOCK_MIMIC_GRASS);

	public static final RegistryObject<Item> ITEM_SOIL_TESTKIT = registerItem("soil_testkit", ItemSoilTestkit::new);

	private static RegistryObject<Block> registerMimicBlock(String name, Block original, Function<BlockBehaviour.Properties, Block> constructor) {
		BlockState originalState = original.defaultBlockState();
		BlockBehaviour.Properties properties = BlockBehaviour.Properties.of(originalState.getMaterial())
				.destroyTime(original.defaultDestroyTime())
				.explosionResistance(originalState.getExplosionResistance(null, null, null))
				.sound(originalState.getSoundType());

		if (originalState.isRandomlyTicking()) {
			properties.randomTicks();
		}
		return BLOCKS.register(name, () -> constructor.apply(properties));
	}

	private static RegistryObject<Item> registerItem(String name, Function<Item.Properties, Item> constructor) {
		Item.Properties properties = new Item.Properties().tab(TAB_GARDEN);
		return ITEMS.register(name, () -> constructor.apply(properties));
	}

	private static RegistryObject<Item> registerMimicBlockItem(String name, RegistryObject<Block> block) {
		return registerItem(name, properties -> new BlockItemMimic(block.get(), properties));
	}

	@SubscribeEvent
	public static void onColorHandlerEvent(ColorHandlerEvent.Block event) {
		event.getBlockColors().register(new GardenBlockColor(),
				GardenDeco.BLOCK_MIMIC_GRASS.get());
	}

	@SubscribeEvent
	public static void doClientStuff(final FMLClientSetupEvent event) {
		ItemBlockRenderTypes.setRenderLayer(BLOCK_MIMIC_GRASS.get(), RenderType.cutoutMipped());
	}

	public GardenDeco() {
		BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		MinecraftForge.EVENT_BUS.register(this);
	}
}
