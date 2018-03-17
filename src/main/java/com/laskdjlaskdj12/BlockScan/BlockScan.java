package com.laskdjlaskdj12.BlockScan;

import com.laskdjlaskdj12.Player.PlayerBlockStorageCache;
import com.laskdjlaskdj12.ScanBlockStruct.ScanBlockStruct;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Vector;

public class BlockScan implements Listener {


    private boolean isTouched = false;
    private Location firstLocation;
    private Location lastLocation;
    private PlayerBlockStorageCache blockStorageCache;

    public BlockScan(PlayerBlockStorageCache cache) {
        blockStorageCache = cache;
    }

    @EventHandler
    public void playerTouchArea(BlockBreakEvent event) {

        Player touchPlayer = event.getPlayer();
        World world = event.getPlayer().getWorld();


        if (touchPlayer.getInventory().getItemInMainHand().getType() == Material.WOOD_AXE) {

            event.getPlayer().sendMessage("월드정보 로딩중..");
            if (world == null) {
                event.getPlayer().sendRawMessage("월드정보가 로딩이 안됬습니다.");
            }

            event.getPlayer().sendRawMessage(world.getName());

            /*
            Todo 만약 터치를 한적이 있다면 첫번째 터치 장소와 마지막 터치 장소 안에 있는 모든 블록들을 스캔해서 Json함
            Todo Block Vector를 좌표 Vector와 Material을 저장하는 struct를 만들어서 함 
            */

            event.setCancelled(true);

            if (isTouched) {
                event.getPlayer().sendRawMessage("두번째 태그를 했습니다.");
                Vector<ScanBlockStruct> ScanLocationList = new Vector<ScanBlockStruct>();
                lastLocation = event.getBlock().getLocation();

                //블록 타입의 스캔 방향을 정함
                if ((firstLocation.getX() < lastLocation.getX()) && (firstLocation.getZ() < lastLocation.getZ())) {

                    //블록을 스캔함
                    for (double indexY = firstLocation.getY(); indexY <= lastLocation.getY(); indexY++) {
                        for (double indexZ = firstLocation.getZ(); indexZ <= lastLocation.getZ(); indexZ++) {
                            for (double indexX = firstLocation.getX(); indexX <= lastLocation.getX(); indexX++) {
                                Block ScanBlockInfo = ScanBlock(event, world, indexY, indexZ, indexX);
                                ScanBlockStruct ListInsertBlock = castToScanBlockStruct(ScanBlockInfo);
                                ScanLocationList.add(ListInsertBlock);
                            }
                        }
                    }
                } else if ((firstLocation.getX() > lastLocation.getX()) && (firstLocation.getZ() < lastLocation.getZ())) {

                    //블록을 스캔함
                    for (double indexY = firstLocation.getY(); indexY <= lastLocation.getY(); indexY++) {
                        for (double indexZ = firstLocation.getZ(); indexZ <= lastLocation.getZ(); indexZ++) {
                            for (double indexX = firstLocation.getX(); indexX >= lastLocation.getX(); indexX--) {
                                Block ScanBlockInfo = ScanBlock(event, world, indexY, indexZ, indexX);
                                ScanBlockStruct ListInsertBlock = castToScanBlockStruct(ScanBlockInfo);
                                ScanLocationList.add(ListInsertBlock);
                            }
                        }
                    }

                } else if ((firstLocation.getX() < lastLocation.getX()) && (firstLocation.getZ() > lastLocation.getZ())) {

                    //블록을 스캔함
                    for (double indexY = firstLocation.getY(); indexY <= lastLocation.getY(); indexY++) {
                        for (double indexZ = firstLocation.getZ(); indexZ >= lastLocation.getZ(); indexZ--) {
                            for (double indexX = firstLocation.getX(); indexX <= lastLocation.getX(); indexX++) {
                                Block ScanBlockInfo = ScanBlock(event, world, indexY, indexZ, indexX);
                                ScanBlockStruct ListInsertBlock = castToScanBlockStruct(ScanBlockInfo);
                                ScanLocationList.add(ListInsertBlock);
                            }
                        }
                    }
                } else {

                    //블록을 스캔함
                    for (double indexY = firstLocation.getY(); indexY <= lastLocation.getY(); indexY++) {
                        for (double indexZ = firstLocation.getZ(); indexZ >= lastLocation.getZ(); indexZ--) {
                            for (double indexX = firstLocation.getX(); indexX >= lastLocation.getX(); indexX--) {
                                Block ScanBlockInfo = ScanBlock(event, world, indexY, indexZ, indexX);
                                ScanBlockStruct ListInsertBlock = castToScanBlockStruct(ScanBlockInfo);
                                ScanLocationList.add(ListInsertBlock);                            }
                        }
                    }

                }

                if (ScanLocationList.isEmpty()) {
                    event.getPlayer().sendRawMessage("스캔에 실패했습니다.");
                } else {
                    event.getPlayer().sendRawMessage(ScanLocationList.size() + "개 스캔 완료");
                    blockStorageCache.saveCache(touchPlayer.getPlayerListName(), ScanLocationList);
                }
                isTouched = false;
            } else {
                event.getPlayer().sendRawMessage("첫번재 태그를 했습니다.");
                firstLocation = event.getBlock().getLocation();
                isTouched = true;
            }
        }
    }

    private ScanBlockStruct castToScanBlockStruct(Block scanBlockInfo) {
        ScanBlockStruct ScanBlock = new ScanBlockStruct();
        ScanBlock.BlockLocation = scanBlockInfo.getLocation();
        ScanBlock.BlockMaterial = scanBlockInfo.getType();
        return ScanBlock;
    }

    private Block ScanBlock(BlockBreakEvent event, World world, double indexY, double indexZ, double indexX) {

        Location ScanBlockLocation = new Location(world, indexX, indexY, indexZ);
        Block ScanBlock = world.getBlockAt(ScanBlockLocation);
        event.getPlayer().sendMessage("스캔 물질 : " + ScanBlock.getType().name());
        return ScanBlock;
    }
}
