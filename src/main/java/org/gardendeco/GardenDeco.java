package org.gardendeco;

import org.gardendeco.block.BlockMimicGrass;
import org.gardendeco.block.BlockMimicTallGrassBlock;
import org.gardendeco.block.EntityBlockMimic;
import org.gardendeco.item.ItemSoilTestkit;
import org.gardendeco.item.ItemSoilTestkitUsed;
import org.gardendeco.setup.RegistryFuncs;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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

	public static final RegistryObject<Block> BLOCK_MIMIC_GRASS = RegistryFuncs.registerMimicBlock("mimic_grass", Blocks.GRASS_BLOCK, BlockMimicGrass::new);
	public static final RegistryObject<Item> ITEM_MIMIC_GRASS = RegistryFuncs.registerMimicBlockItem("mimic_grass", BLOCK_MIMIC_GRASS);

	public static final RegistryObject<Block> BLOCK_MIMIC_FERN = RegistryFuncs.registerMimicBlock("mimic_fern", Blocks.FERN, BlockMimicTallGrassBlock::new);
	public static final RegistryObject<Item> ITEM_MIMIC_FERN = RegistryFuncs.registerMimicBlockItem("mimic_fern", BLOCK_MIMIC_FERN);

	public static final RegistryObject<Item> ITEM_SOIL_TESTKIT = RegistryFuncs.registerItem("soil_testkit", ItemSoilTestkit::new, 1);
	public static final RegistryObject<Item> ITEM_SOIL_TESTKIT_USED = RegistryFuncs.registerItem("soil_testkit_used", ItemSoilTestkitUsed::new, 1);

	public static final RegistryObject<BlockEntityType<?>> BLOCK_ENTITY_MIMIC = RegistryFuncs.registerBlockEntity("mimic", EntityBlockMimic::new,
			BLOCK_MIMIC_GRASS,
			BLOCK_MIMIC_FERN);

	@SubscribeEvent
	public static void onColorHandlerEvent(ColorHandlerEvent.Block event) {
		event.getBlockColors().register(new GardenBlockColor(),
				BLOCK_MIMIC_GRASS.get(),
				BLOCK_MIMIC_FERN.get());
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

			if (stack.getItem() instanceof ItemSoilTestkitUsed) {
				CompoundTag tag = stack.getTag();
				if (tag == null)
					return;
				String biomeKey = tag.getString("biomeKey");
				String biomeName = Util.makeDescriptionId("biome", new ResourceLocation(biomeKey));
				event.getToolTip().add(new TranslatableComponent(biomeName).withStyle(ChatFormatting.GREEN));
			}
		}
	}
}
