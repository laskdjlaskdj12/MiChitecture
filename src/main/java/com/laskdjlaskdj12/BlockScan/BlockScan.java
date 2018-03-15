package com.laskdjlaskdj12.BlockScan;

import com.laskdjlaskdj12.Player.PlayerBlockStorageCache;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
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

    public BlockScan(PlayerBlockStorageCache cache){
        blockStorageCache = cache;
    }

    @EventHandler
    public void playerTouchArea(BlockBreakEvent event) {

        Player touchPlayer = event.getPlayer();
        World world = Bukkit.getServer().getWorlds().get(0);


        if (touchPlayer.getInventory().getItemInMainHand().getType() == Material.WOOD_AXE) {
            if(world == null){
                event.getPlayer().sendRawMessage("월드정보가 로딩이 안됬습니다.");
            }

            event.getPlayer().sendRawMessage(world.getName());

            /*
            Todo 만약 터치를 한적이 있다면 첫번째 터치 장소와 마지막 터치 장소 안에 있는 모든 블록들을 스캔해서 Json함
            */

            event.setCancelled(true);

            if (isTouched) {
                event.getPlayer().sendRawMessage("두번째 태그를 했습니다.");
                Vector<Block> ScanBlockList = new Vector<Block>();
                lastLocation = event.getBlock().getLocation();

                //블록 타입의 스캔 방향을 정함
                if ((firstLocation.getX() < lastLocation.getX()) && (firstLocation.getZ() < lastLocation.getZ())) {

                    //블록을 스캔함
                    for (double indexY = firstLocation.getY(); indexY < lastLocation.getY() + 1; indexY++) {
                        for (double indexZ = firstLocation.getZ(); indexZ < lastLocation.getZ() + 1; indexZ++) {
                            for (double indexX = firstLocation.getX(); indexX < lastLocation.getX() + 1; indexX++) {
                                Block ScanBlock = world.getBlockAt((int) indexX, (int) indexY, (int) indexZ);

                                ScanBlockList.add(ScanBlock);
                            }
                        }
                    }
                } else if ((firstLocation.getX() > lastLocation.getX()) && (firstLocation.getZ() < lastLocation.getZ())) {

                    //블록을 스캔함
                    for (double indexY = firstLocation.getY(); indexY < lastLocation.getY() + 1; indexY++) {
                        for (double indexZ = firstLocation.getZ(); indexZ < lastLocation.getZ() + 1; indexZ++) {
                            for (double indexX = firstLocation.getX(); indexX > lastLocation.getX() - 1; indexX--) {
                                Block ScanBlock = world.getBlockAt((int) indexX, (int) indexY, (int) indexZ);
                                ScanBlockList.add(ScanBlock);
                            }
                        }
                    }

                } else if ((firstLocation.getX() < lastLocation.getX()) && (firstLocation.getZ() > lastLocation.getZ())) {

                    //블록을 스캔함
                    for (double indexY = firstLocation.getY(); indexY < lastLocation.getY() + 1; indexY++) {
                        for (double indexZ = firstLocation.getZ(); indexZ > lastLocation.getZ() - 1; indexZ--) {
                            for (double indexX = firstLocation.getX(); indexX < lastLocation.getX() + 1; indexX++) {
                                Block ScanBlock = world.getBlockAt((int) indexX, (int) indexY, (int) indexZ);
                                ScanBlockList.add(ScanBlock);
                            }
                        }
                    }
                } else {

                    //블록을 스캔함
                    for (double indexY = firstLocation.getY(); indexY < lastLocation.getY() + 1; indexY++) {
                        for (double indexZ = firstLocation.getZ(); indexZ > lastLocation.getZ() - 1; indexZ--) {
                            for (double indexX = firstLocation.getX(); indexX > lastLocation.getX() - 1; indexX--) {
                                Block ScanBlock = world.getBlockAt((int) indexX, (int) indexY, (int) indexZ);
                                ScanBlockList.add(ScanBlock);
                            }
                        }
                    }
                }

                if(ScanBlockList.isEmpty()){
                    event.getPlayer().sendRawMessage("스캔에 실패했습니다.");
                }else {
                    event.getPlayer().sendRawMessage(ScanBlockList.size() + "개 스캔 완료");
                    blockStorageCache.saveCache(touchPlayer.getPlayerListName(), ScanBlockList);
                }
                isTouched = false;
            } else {
                event.getPlayer().sendRawMessage("첫번재 태그를 했습니다.");
                firstLocation = event.getBlock().getLocation();
                isTouched = true;
            }
        }
    }
}
