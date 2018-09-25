package com.laskdjlaskdj12.Block.Save;

import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.Vector;

public class BlockSave {

    private HashMap<String, Vector<Block>> BlockName;

    public BlockSave(){
        BlockName = new HashMap<String, Vector<Block>>();
    }

    public void saveBlockList(Vector<Block> Block, String name){
        BlockName.put(name, Block);
    }

    public Vector<Block> getBlockList(String name){
        Vector<Block> RetBlock = new Vector<Block>();
        RetBlock = BlockName.get(name);
        return RetBlock;
    }
}
