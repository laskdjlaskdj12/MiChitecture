package com.laskdjlaskdj12.UploadBlock;

import com.laskdjlaskdj12.ScanBlockStruct.ScanBlockStruct;

public class BlockCordinateStruct {

    public BlockCordinateStruct(ScanBlockStruct Block){

        X = Block.BlockLocation.getX();
        Y = Block.BlockLocation.getY();
        Z = Block.BlockLocation.getZ();
        MaterialName = Block.BlockMaterial.name();
    }

    private double X;
    private double Y;
    private double Z;
    private String MaterialName;
}
