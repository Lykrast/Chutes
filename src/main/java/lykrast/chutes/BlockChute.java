package lykrast.chutes;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockChute extends Block {
	public BlockChute(float hardness, float resistance) {
		super(Material.WOOD);
		setSoundType(SoundType.WOOD);
		setHardness(hardness);
		setResistance(resistance);
		setHarvestLevel("axe", 0);
	}

}
