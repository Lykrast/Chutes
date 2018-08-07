package lykrast.chutes;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

@Mod(modid = Chutes.MODID, 
	name = Chutes.NAME, 
	version = Chutes.VERSION, 
	acceptedMinecraftVersions = "[1.12, 1.13)")
@Mod.EventBusSubscriber
public class Chutes {
    public static final String MODID = "chutes";
    public static final String NAME = "Chutes";
    public static final String VERSION = "@VERSION@";

	public static Logger logger = LogManager.getLogger(MODID);
	
	public static Block chute, chuteEntry;
	private static List<Item> itemBlocks;
	
	private static Block registerBlock(IForgeRegistry<Block> reg, Block block, String name, CreativeTabs tab) {
		block.setRegistryName(name);
		block.setUnlocalizedName(MODID + "." + name);
		if (tab != null) block.setCreativeTab(tab);
		
		reg.register(block);
		
		Item item = new ItemBlock(block);
		item.setRegistryName(block.getRegistryName());
		itemBlocks.add(item);
		
		return block;
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		IForgeRegistry<Block> registry = event.getRegistry();
		itemBlocks = new ArrayList<>();
		
		chute = registerBlock(registry, new BlockChute(1.0F, 10.0F), "chute", CreativeTabs.REDSTONE);
	}
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		IForgeRegistry<Item> reg = event.getRegistry();
		for (Item i : itemBlocks) reg.register(i);
	}

	private static void initModel(Block b) {
		initModel(Item.getItemFromBlock(b));
	}

	private static void initModel(Item i) {
		ModelLoader.setCustomModelResourceLocation(i, 0, new ModelResourceLocation(i.getRegistryName(), "inventory"));
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void registerModels(ModelRegistryEvent evt) {
		initModel(chute);
		
		for (Item i : itemBlocks) initModel(i);
	}
}
