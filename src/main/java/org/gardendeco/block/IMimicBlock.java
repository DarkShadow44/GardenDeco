package org.gardendeco.block;

import org.gardendeco.color.ColorType;

import net.minecraft.world.level.block.Block;

public interface IMimicBlock {
	Block getOriginal();

	ColorType getColorType();
}
