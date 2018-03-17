package com.laskdjlaskdj12.Player;

import com.laskdjlaskdj12.ScanBlockStruct.ScanBlockStruct;
import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class PlayerBlockStorageCache {
    private HashMap<String, Vector<ScanBlockStruct>> cachedBlockList;

    public PlayerBlockStorageCache(){
        cachedBlockList = new HashMap<String, Vector<ScanBlockStruct>>();
    }

    public void saveCache(String name, Vector<ScanBlockStruct> requestBlock){
        cachedBlockList.put(name, requestBlock);
    }

    public Vector<ScanBlockStruct> getBlock(String name){
        return cachedBlockList.get(name);
    }
}
