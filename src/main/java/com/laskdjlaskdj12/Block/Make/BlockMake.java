package com.laskdjlaskdj12.Block.Make;

import com.laskdjlaskdj12.VO.BlockScanVO;
import com.laskdjlaskdj12.VO.PlayerBlockAreaInfoVO;
import org.bukkit.Location;

public class BlockMake implements Runnable {
    private final Location baseLocation;
    private final PlayerBlockAreaInfoVO playerBlockAreaInfoVO;

    public BlockMake(Location baseLocation, PlayerBlockAreaInfoVO playerBlockAreaInfoVO){
        this.baseLocation = baseLocation;
        this.playerBlockAreaInfoVO = playerBlockAreaInfoVO;
    }

    @Override
    public void run() {
        try {
            playerBlockAreaInfoVO.player.sendRawMessage("블록 설치를 시작합니다.");

            Location startScanBlockLocation = baseLocation;

            for (BlockScanVO block : playerBlockAreaInfoVO.blockScanArea) {
                Location blockSetLocation = startScanBlockLocation.add(block.BlockLocation);
                blockSetLocation.getBlock().setType(block.BlockMaterial);

                //처음 블록으로 초기화
                startScanBlockLocation.subtract(block.BlockLocation);
            }

            playerBlockAreaInfoVO.player.sendRawMessage("블록 설치가 완료되었습니다.");
        } catch (NullPointerException e) {
            e.printStackTrace();;
        }
    }
}
