package lykrast.chutes;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockChuteEntry extends BlockChute {
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final AxisAlignedBB ENTRY_NORTH = new AABBArray(AABB_SOUTH, AABB_WEST, AABB_EAST);
	public static final AxisAlignedBB ENTRY_SOUTH = new AABBArray(AABB_NORTH, AABB_WEST, AABB_EAST);
	public static final AxisAlignedBB ENTRY_WEST = new AABBArray(AABB_NORTH, AABB_SOUTH, AABB_EAST);
	public static final AxisAlignedBB ENTRY_EAST = new AABBArray(AABB_NORTH, AABB_SOUTH, AABB_WEST);

	public BlockChuteEntry(float hardness, float resistance) {
		super(hardness, resistance);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		switch (state.getValue(FACING)) {
		case SOUTH:
			return ENTRY_SOUTH;
		case WEST:
			return ENTRY_WEST;
		case EAST:
			return ENTRY_EAST;
		case NORTH:
		default:
			return ENTRY_NORTH;
		}
	}
	
	@Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
		EnumFacing facing = state.getValue(FACING);
        if (facing != EnumFacing.NORTH) addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_NORTH);
        if (facing != EnumFacing.SOUTH) addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_SOUTH);
        if (facing != EnumFacing.WEST) addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WEST);
        if (facing != EnumFacing.EAST) addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_EAST);
    }
	
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getHorizontalIndex();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}

}
