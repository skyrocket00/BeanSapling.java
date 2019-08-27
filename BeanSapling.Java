package com.KahootIsFun.BeanMod.blocks;

import java.util.Random;

import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.TerrainGen;

public class BeanSapling extends BlockBush implements IGrowable{
    public BeanSapling(String name) {
    	super();
    	setUnlocalizedName(name);
    	setRegistryName(name);
    	setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

	public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);
    protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);
    
    
    //Sapling shape +
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    	return SAPLING_AABB;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockstate, IBlockAccess worldIn, BlockPos pos) {
    	return 	NULL_AABB;
    }
    
    @Override
    public boolean isOpaqueCube(IBlockState state) {
    	return false;
    }
    
    @Override 
    public boolean isFullCube(IBlockState state) {
    	return false;
    }
    
    //Tree code
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
    	if(((Integer)state.getValue(STAGE)).intValue() == 0) {
    		worldIn.setBlockState(pos, state.cycleProperty(STAGE), 4);
    	}else {
    		this.generateTree(worldIn, rand, pos, state);
    	}
    }
    
    public void generateTree(World world, Random rand, BlockPos pos, IBlockState state) {
    	if(TerrainGen.saplingGrowTree(world, rand, pos)) return;
    	WorldGenerator gen = (WorldGenerator)(rand.nextInt(10) == 0 ? new WorldGenBigTree(false) : new WorldGenTrees(false));
    	int i = 0, j = 0;
    	boolean flag = false;
    	
    	//gen = new WorldGenTree();
    	
    	IBlockState iblockstate = Blocks.AIR.getDefaultState();
    	if(flag) {
    		world.setBlockState(pos.add(0, 0, 0), iblockstate, 4);
    		world.setBlockState(pos.add(1, 0, 0), iblockstate, 4);
    		world.setBlockState(pos.add(0, 0, 1), iblockstate, 4);
    		world.setBlockState(pos.add(1, 0, 1), iblockstate, 4);
    	}else {
    		world.setBlockState(pos, iblockstate, 4);
    	}
    	
    	if(!gen.generate(world, rand, pos)) {
        	if(flag) {
        		world.setBlockState(pos.add(0, 0, 0), iblockstate, 4);
        		world.setBlockState(pos.add(1, 0, 0), iblockstate, 4);
        		world.setBlockState(pos.add(0, 0, 1), iblockstate, 4);
        		world.setBlockState(pos.add(1, 0, 1), iblockstate, 4);
        	}else {
        		world.setBlockState(pos, iblockstate, 4);
        	}

    	}
    }
    
    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
    	return true;
    }
    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
    	return (double)worldIn.rand.nextFloat() < 0.45D;
    }
    @Override
    protected boolean canSustainBush(IBlockState state) {
    	return state.getBlock() == Blocks.GRASS || state.getBlock() == Blocks.DIRT || state.getBlock() == Blocks.FARMLAND;
    }
}


