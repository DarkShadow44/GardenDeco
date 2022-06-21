package org.gardendeco;

import java.util.Arrays;
import java.util.List;

import org.gardendeco.block.BlockMimicGrass;
import org.gardendeco.block.BlockMimicTallGrassBlock;
import org.gardendeco.block.EntityBlockMimic;
import org.gardendeco.color.ColorType;
import org.gardendeco.color.GardenBlockColor;
import org.gardendeco.item.ItemSoilTestkit;
import org.gardendeco.item.ItemSoilTestkitEmpty;
import org.gardendeco.item.ItemSoilTestkitImproved;
import org.gardendeco.item.ItemSoilTestkitNullifier;
import org.gardendeco.recipe.RecipeTestkitImproved;
import org.gardendeco.setup.RegistryFuncs;

import net.minecraft.Util;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SimpleFoiledItem;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegistryObject;

@Mod(GardenDeco.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class GardenDeco {
	public static final String MODID = "gardendeco";

	public static TabGarden TAB_GARDEN = new TabGarden();

	private static final RegistryObject<Block> BLOCK_MIMIC_GRASS = RegistryFuncs.registerMimicBlock("mimic_grass", Blocks.GRASS_BLOCK, BlockMimicGrass::new);
	private static final RegistryObject<Block> BLOCK_MIMIC_FERN = RegistryFuncs.registerMimicBlock("mimic_fern", Blocks.FERN, BlockMimicTallGrassBlock::new);

	public static final List<RegistryObject<Block>> BLOCKS_MIMIC = Arrays.asList(
			BLOCK_MIMIC_GRASS,
			BLOCK_MIMIC_FERN);

	public static final RegistryObject<Item> ITEM_SOIL_TESTKIT_EMPTY = RegistryFuncs.registerItem("soil_testkit_empty", ItemSoilTestkitEmpty::new, 1, 0);
	public static final RegistryObject<Item> ITEM_SOIL_TESTKIT = RegistryFuncs.registerItem("soil_testkit", ItemSoilTestkit::new, 1, 64);
	public static final RegistryObject<Item> ITEM_SOIL_TESTKIT_IMPROVED = RegistryFuncs.registerItem("soil_testkit_improved", ItemSoilTestkitImproved::new, 1, 0);
	public static final RegistryObject<Item> ITEM_SOIL_TESTKIT_NULLIFIER = RegistryFuncs.registerItem("soil_testkit_nullifier", ItemSoilTestkitNullifier::new, 1, 0);
	public static final RegistryObject<Item> ITEM_SOIL_CATALYST = RegistryFuncs.registerItem("soil_catalyst", SimpleFoiledItem::new, 64, 0);

	public static final RegistryObject<BlockEntityType<?>> BLOCK_ENTITY_MIMIC = RegistryFuncs.registerBlockEntity("mimic", EntityBlockMimic::new, BLOCKS_MIMIC);

	public static final RegistryObject<RecipeSerializer<?>> RECIPE_TESTKIT_IMPROVED = RegistryFuncs.registerRecipeSerializer("testkit_improved", RecipeTestkitImproved::new);

	@SubscribeEvent
	public static void onColorHandlerEvent(ColorHandlerEvent.Block event) {
		GardenBlockColor colors = new GardenBlockColor();

		for (RegistryObject<Block> block : BLOCKS_MIMIC) {
			event.getBlockColors().register(colors, block.get());
		}
	}

	@SubscribeEvent
	public static void doClientStuff(FMLClientSetupEvent event) {
		ItemBlockRenderTypes.setRenderLayer(BLOCK_MIMIC_GRASS.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(BLOCK_MIMIC_FERN.get(), RenderType.cutout());
	}

	public GardenDeco() {
		RegistryFuncs.register();
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
	public static class EventHandler {
		@SubscribeEvent
		public static void onTooltip(ItemTooltipEvent event) {
			ItemStack stack = event.getItemStack();

			if (stack.getItem() instanceof ItemSoilTestkit) {
				CompoundTag tag = stack.getTag();
				if (tag == null)
					return;

				String biomeKey = tag.getString("biomeKey");
				String biomeName = Util.makeDescriptionId("biome", new ResourceLocation(biomeKey));
				int color = MimicHandler.getBiomeColor(biomeKey, ColorType.GRASS);
				Style style = Style.EMPTY.withColor(color);
				event.getToolTip().add(new TranslatableComponent(biomeName).withStyle(style));
			}
		}

	}
}
