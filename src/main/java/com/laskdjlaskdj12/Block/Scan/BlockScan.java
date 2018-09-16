package com.laskdjlaskdj12.Block.Scan;

import com.laskdjlaskdj12.VO.BlockScanVO;
import com.laskdjlaskdj12.VO.PlayerBlockAreaInfoVO;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Vector;

public class BlockScan implements Runnable {
    private  PlayerBlockAreaInfoVO playerBlockAreaInfoVO;

    public BlockScan(PlayerBlockAreaInfoVO playerBlockAreaInfoVO) {
        this.playerBlockAreaInfoVO = playerBlockAreaInfoVO;
    }

    @Override
    public void run(){
        try {
            World playerWorld = playerBlockAreaInfoVO.player.getWorld();

            //블록을 스캔함
            ScanBlockArea(playerBlockAreaInfoVO, playerBlockAreaInfoVO.player, playerWorld);
        }catch(NullPointerException e){
            e.printStackTrace();
        }
    }

    private void ScanBlockArea(PlayerBlockAreaInfoVO blockAreaInfo, Player touchPlayer, World world) {

        Block firstLocation = blockAreaInfo.firstBlock;
        Block lastLocation = blockAreaInfo.lastBlock;
        
        Vector<BlockScanVO> blockScanList = new Vector<BlockScanVO>();

        //블록 타입의 스캔 방향을 정함
        if ((firstLocation.getX() < lastLocation.getX()) && (firstLocation.getZ() < lastLocation.getZ())) {

            //블록을 스캔함
            for (double indexY = firstLocation.getY(); indexY <= lastLocation.getY(); indexY++) {
                for (double indexZ = firstLocation.getZ(); indexZ <= lastLocation.getZ(); indexZ++) {
                    for (double indexX = firstLocation.getX(); indexX <= lastLocation.getX(); indexX++) {
                        Block ScanBlockInfo = ScanBlock( world, indexY, indexZ, indexX);
                        BlockScanVO scanBlock = toScanBlock(world, firstLocation.getX(), firstLocation.getZ(), firstLocation.getY(), ScanBlockInfo);
                        blockScanList.add(scanBlock);
                    }
                }
            }
        } else if ((firstLocation.getX() > lastLocation.getX()) && (firstLocation.getZ() < lastLocation.getZ())) {

            //블록을 스캔함
            for (double indexY = firstLocation.getY(); indexY <= lastLocation.getY(); indexY++) {
                for (double indexZ = firstLocation.getZ(); indexZ <= lastLocation.getZ(); indexZ++) {
                    for (double indexX = firstLocation.getX(); indexX >= lastLocation.getX(); indexX--) {
                        Block ScanBlockInfo = ScanBlock( world, indexY, indexZ, indexX);
                        BlockScanVO ListInsertBlock = toScanBlock(world, lastLocation.getX(), firstLocation.getZ(), firstLocation.getY(), ScanBlockInfo);
                        blockScanList.add(ListInsertBlock);
                    }
                }
            }

        } else if ((firstLocation.getX() < lastLocation.getX()) && (firstLocation.getZ() > lastLocation.getZ())) {

            //블록을 스캔함
            for (double indexY = firstLocation.getY(); indexY <= lastLocation.getY(); indexY++) {
                for (double indexZ = firstLocation.getZ(); indexZ >= lastLocation.getZ(); indexZ--) {
                    for (double indexX = firstLocation.getX(); indexX <= lastLocation.getX(); indexX++) {
                        Block ScanBlockInfo = ScanBlock( world, indexY, indexZ, indexX);
                        BlockScanVO ListInsertBlock = toScanBlock(world, firstLocation.getX(), lastLocation.getZ(), firstLocation.getY(), ScanBlockInfo);
                        blockScanList.add(ListInsertBlock);
                    }
                }
            }
        } else {

            //블록을 스캔함
            for (double indexY = firstLocation.getY(); indexY <= lastLocation.getY(); indexY++) {
                for (double indexZ = firstLocation.getZ(); indexZ >= lastLocation.getZ(); indexZ--) {
                    for (double indexX = firstLocation.getX(); indexX >= lastLocation.getX(); indexX--) {
                        Block ScanBlockInfo = ScanBlock( world, indexY, indexZ, indexX);
                        BlockScanVO ListInsertBlock = toScanBlock(world, lastLocation.getX(), lastLocation.getZ(), firstLocation.getY(), ScanBlockInfo);
                        blockScanList.add(ListInsertBlock);
                    }
                }
            }

        }

        if (blockScanList.isEmpty()) {
            touchPlayer.sendRawMessage("스캔에 실패했습니다.");
        } else {
            touchPlayer.sendRawMessage(blockScanList.size() + "개 스캔 완료");
        }

        blockAreaInfo.blockScanArea = blockScanList;

        blockAreaInfo.firstBlock = null;
        blockAreaInfo.lastBlock = null;
    }

    private BlockScanVO toScanBlock(World world, double firstX, double firstZ, double firstY, Block scanBlockInfo) {

        double LocalX = scanBlockInfo.getX() - firstX;
        double LocalY = scanBlockInfo.getY() - firstY;
        double LocalZ = scanBlockInfo.getZ() - firstZ;

        Location reScanLocation = new Location(world, (int) LocalX, (int) LocalY, (int) LocalZ);

        BlockScanVO ScanBlock = new BlockScanVO();
        ScanBlock.BlockLocation = reScanLocation;
        ScanBlock.BlockMaterial = scanBlockInfo.getType();
        return ScanBlock;
    }

    private Block ScanBlock(World world, double indexY, double indexZ, double indexX) {

        Location ScanBlockLocation = new Location(world, indexX, indexY, indexZ);
        Block ScanBlock = world.getBlockAt(ScanBlockLocation);
        return ScanBlock;
    }
}
