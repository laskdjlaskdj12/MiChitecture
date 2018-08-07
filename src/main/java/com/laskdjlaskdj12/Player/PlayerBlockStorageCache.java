package com.laskdjlaskdj12.Player;

import com.laskdjlaskdj12.ScanBlockStruct.ScanBlockStruct;
import com.laskdjlaskdj12.VO.PlayerAreaInfoVO;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class PlayerBlockStorageCache {

    @Deprecated
    private HashMap<String, Vector<ScanBlockStruct>> cachedBlockList;

    // @ String : Player UniqueID
    // @ PlayerAreaInfoVo : Player가 지정하는 영역의 정보 리스트
    private HashMap<String, PlayerAreaInfoVO> playerAreaInfoVOMap;

    public PlayerBlockStorageCache(){
        cachedBlockList = new HashMap<String, Vector<ScanBlockStruct>>();
    }

    public void saveCache(String playerPID, Vector<ScanBlockStruct> requestBlock){
        cachedBlockList.put(playerPID, requestBlock);
    }

    public Vector<ScanBlockStruct> getBlock(String name){
        return cachedBlockList.get(name);
    }

    public boolean isFirstTouch(Player player) {

        //플레이어의 영역관련된 정보가 있는지 체크
        PlayerAreaInfoVO playerAreaInfoVO = playerAreaInfoVOMap.get(player.getUniqueId().toString());

        //영역의 첫번째 블록이 지정이 되었지 않았는가?
        if ( playerAreaInfoVO.firstBlock != null && !playerAreaInfoVO.firstBlock.isEmpty()){
            return false;
        }

        //첫번째 영역이 지정이 안되었다.
        return true;
    }

    public void registerStartArea(Player player, Block block) {
        PlayerAreaInfoVO playerAreaInfoVO = playerAreaInfoVOMap.get(player.getUniqueId().toString());

        playerAreaInfoVO.firstBlock = block;
    }

    public void registerEndArea(Player player, Block block) {
        PlayerAreaInfoVO playerAreaInfoVO = playerAreaInfoVOMap.get(player.getUniqueId().toString());

        playerAreaInfoVO.lastBlock = block;
    }
}
