package com.laskdjlaskdj12.Player;

import com.laskdjlaskdj12.ScanBlockStruct.ScanBlockStruct;
import com.laskdjlaskdj12.VO.PlayerBlockAreaInfoVO;
import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.Vector;

public class PlayerBlockStorageCache {
    @Deprecated
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

    private HashMap<String, PlayerBlockAreaInfoVO> playersBlockAreaInfo;

    public boolean isOneTapped(String PID) {

        PlayerBlockAreaInfoVO info = playersBlockAreaInfo.get(PID);

        return (info == null || info.firstBlock == null);
    }

    public void setFirstBlock(String PID, Block block) {
        PlayerBlockAreaInfoVO info = playersBlockAreaInfo.get(PID);

        //만약 등록이 되어있지 않다면 등록함
        if(info == null){
            info = new PlayerBlockAreaInfoVO();
            playersBlockAreaInfo.put(PID, info);
        }

        info.firstBlock = block;
    }

    public void setLastBlock(String PID, Block block) {
        PlayerBlockAreaInfoVO info = playersBlockAreaInfo.get(PID);

        info.lastBlock = block;
    }

    public PlayerBlockAreaInfoVO getBlockAreaInfo(String PID) {
        return playersBlockAreaInfo.get(PID);
    }
}
