package com.laskdjlaskdj12.Player;

import com.laskdjlaskdj12.VO.BlockScanVO;
import com.laskdjlaskdj12.VO.PlayerBlockAreaInfoVO;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Vector;

public class PlayerBlockStorageCache {

    private HashMap<String, PlayerBlockAreaInfoVO> playersBlockAreaInfo;

    public PlayerBlockStorageCache() {
        playersBlockAreaInfo = new HashMap<String, PlayerBlockAreaInfoVO>();
    }

    public boolean isOneTapped(String PID) {

        PlayerBlockAreaInfoVO info = playersBlockAreaInfo.get(PID);

        return (info == null || info.firstBlock == null);
    }

    public void setFirstBlock(String PID, Player player, Block block) {

        PlayerBlockAreaInfoVO playerAreaInfo = playersBlockAreaInfo.get(PID);

        //만약 등록이 되어있지 않다면 등록함
        if (playerAreaInfo == null) {

            playerAreaInfo = new PlayerBlockAreaInfoVO();
            playerAreaInfo.player = player;

            playersBlockAreaInfo.put(PID, playerAreaInfo);
        }

        playerAreaInfo.firstBlock = block;
    }

    public void setLastBlock(String PID, Block block) {

        PlayerBlockAreaInfoVO info = playersBlockAreaInfo.get(PID);

        info.lastBlock = block;
    }

    public PlayerBlockAreaInfoVO getBlockAreaInfo(String PID) {
        return playersBlockAreaInfo.get(PID);
    }
}
