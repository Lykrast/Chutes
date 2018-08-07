package lykrast.chutes;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockChute extends Block {
	public static final AxisAlignedBB AABB_NORTH = new AxisAlignedBB(0, 0, 0, 1, 1, 0.0625);
	public static final AxisAlignedBB AABB_SOUTH = new AxisAlignedBB(0, 0, 0.9375, 1, 1, 1);
	public static final AxisAlignedBB AABB_WEST = new AxisAlignedBB(0, 0, 0, 0.0625, 1, 1);
	public static final AxisAlignedBB AABB_EAST = new AxisAlignedBB(0.9375, 0, 0, 1, 1, 1);
	public static final AxisAlignedBB CHUTE_AABB = new AABBArray(AABB_NORTH, AABB_SOUTH, AABB_WEST, AABB_EAST);
	
	public BlockChute(float hardness, float resistance) {
		super(Material.WOOD);
		setSoundType(SoundType.WOOD);
		setHardness(hardness);
		setResistance(resistance);
		setHarvestLevel("axe", 0);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return CHUTE_AABB;
	}
	
	@Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_NORTH);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_SOUTH);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WEST);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_EAST);
    }

	@Override
    public boolean isTopSolid(IBlockState state) {
        return false;
    }

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return face == EnumFacing.UP || face == EnumFacing.DOWN ? BlockFaceShape.UNDEFINED : BlockFaceShape.SOLID;
	}

}
