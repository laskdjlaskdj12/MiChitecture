package com.laskdjlaskdj12.Type;

import com.laskdjlaskdj12.ScanBlockStruct.ScanBlockStruct;

public class BlockCordinateStruct {

    public BlockCordinateStruct(ScanBlockStruct Block){

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
