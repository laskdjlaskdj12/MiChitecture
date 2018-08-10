package com.laskdjlaskdj12.Type;

import com.laskdjlaskdj12.VO.BlockScanVO;

public class BlockCordinateStruct {

    public BlockCordinateStruct(BlockScanVO Block){

        X = Block.BlockLocation.getX();
        Y = Block.BlockLocation.getY();
        Z = Block.BlockLocation.getZ();
        MaterialName = Block.BlockMaterial.name();
    }

    public BlockCordinateStruct(){

    }

    public double X;
    public double Y;
    public double Z;
    public String MaterialName;
}
