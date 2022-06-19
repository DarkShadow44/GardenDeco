package org.gardendeco;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

public class TabGarden extends CreativeModeTab {
	public TabGarden() {
		super("gardendeco_tab");
	}

	@Override
	public ItemStack makeIcon() {
		return new ItemStack(Blocks.TALL_GRASS);
	}
}
