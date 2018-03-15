package com.laskdjlaskdj12.Player;

import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class PlayerBlockStorageCache {
    private HashMap<String, Vector<Block>> cachedBlockList;

    public PlayerBlockStorageCache(){
        cachedBlockList = new HashMap<String, Vector<Block>>();
    }

    public void saveCache(String name, Vector<Block> requestBlock){
        cachedBlockList.put(name, requestBlock);
    }

    public Vector<Block> getBlock(String name){
        return cachedBlockList.get(name);
    }
}
