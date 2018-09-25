package com.laskdjlaskdj12.Player;

import com.laskdjlaskdj12.VO.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class PlayerBlockStorageCache {

    private List<BlockCacheVO> readyToSaveDownloadsQueue = new ArrayList<BlockCacheVO>();

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

    public void addBlockAreaVO(String playerUID, BlockAreaVO blockAreaVO) {
        PlayerBlockAreaInfoVO playerBlockAreaInfoVO = new PlayerBlockAreaInfoVO();
    }

    //블록을 다운로드했을때 임시적으로 저장을 하기
    public void saveBlockArea(String playerUID, BlockAreaVO blockAreaVO) {

        BlockCacheVO blockCacheVO = new BlockCacheVO();
        blockCacheVO.setPlayerID(playerUID);
        blockCacheVO.setBlockAreaVO(blockAreaVO);

        readyToSaveDownloadsQueue.add(blockCacheVO);
    }

    public void saveDownloadedReadyBlocks(){
        //다운로드가 완료된 블록 Area의 갯수를 가지고 옴
        //다운로드를 한 블록이 멀티쓰레드로 readyToSaveDownload큐에 저장을 하기 때문에
        //이 메소드가 실행하는 동안 다운로드하는 갯수가 늘어날수있음

        long saveBlockAreaCout = readyToSaveDownloadsQueue.size();

        //블록을 HashMap에 저장함 (이건 카운트별로 저장함)
        for (int i = 0; i < saveBlockAreaCout; i++){
            BlockCacheVO blockCacheVO = readyToSaveDownloadsQueue.get(i);

            String ID = blockCacheVO.getPlayerID();
            BlockAreaVO blockAreaVO= blockCacheVO.getBlockAreaVO();

            PlayerBlockAreaInfoVO savedBlockArea = playersBlockAreaInfo.get(ID);

            if(savedBlockArea == null){
                System.out.println("ID : " + ID + "를 가진 플레이어가 없어서 요청한 블록을 SKIP합니다..");
            }

            Player player = savedBlockArea.player;
            savedBlockArea.blockScanArea = castToBlockScanVO(blockAreaVO, player);
            savedBlockArea.player = player;

            player.sendRawMessage(blockAreaVO.getUserPID() + "저장이 완료되었습니다.");

            readyToSaveDownloadsQueue.remove(0);
        }
    }

    public List<BlockCacheVO> getSavedDownloadedBlock(){
        return readyToSaveDownloadsQueue;
    }

    private Vector<BlockScanVO> castToBlockScanVO(BlockAreaVO blockAreaVO, Player player) {

        List<BlockInfoVO> blockInfoVOS = blockAreaVO.getBlockInfoVO();
        Vector<BlockScanVO> blockScanVOS = new Vector<>();

        for(BlockInfoVO blockInfoVO : blockInfoVOS){

            BlockScanVO blockScanVO = new BlockScanVO();

            blockScanVO.BlockLocation = new Location (player.getWorld(),
                    blockInfoVO.getX(),
                    blockInfoVO.getY(),
                    blockInfoVO.getZ());

            blockScanVO.BlockMaterial = Material.getMaterial(blockInfoVO.getMaterialID());

            blockScanVOS.add(blockScanVO);
        }

        return blockScanVOS;
    }

}
