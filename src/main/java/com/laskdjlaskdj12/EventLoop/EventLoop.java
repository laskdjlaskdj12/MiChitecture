package com.laskdjlaskdj12.EventLoop;

import com.laskdjlaskdj12.Block.Make.BlockMake;
import com.laskdjlaskdj12.Block.Scan.BlockScan;
import com.laskdjlaskdj12.Network.Download.Download;
import com.laskdjlaskdj12.Network.Upload.Upload;
import com.laskdjlaskdj12.Player.PlayerBlockStorageCache;
import com.laskdjlaskdj12.VO.PlayerBlockAreaInfoVO;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class EventLoop {
    private ExecutorService executor;
    private Plugin plugin;

    public EventLoop(Plugin plugin) {
        this.executor = Executors.newCachedThreadPool();
        this.plugin = plugin;
    }

    public void scanExecute(PlayerBlockAreaInfoVO playerBlockAreaInfoVO) {
        BukkitRunnable runnable =  new BukkitRunnable() {
            @Override
            public void run() {
                BlockScan blockScan = new BlockScan(playerBlockAreaInfoVO);

                blockScan.run();
            }
        };
        runnable.runTask(plugin);
    }

    public void makeExecute(Location baseLocation, PlayerBlockAreaInfoVO playerBlockAreaInfoVO) {

       BukkitRunnable runnable =  new BukkitRunnable() {
            @Override
            public void run() {
                BlockMake blockMake = new BlockMake(baseLocation, playerBlockAreaInfoVO);

                blockMake.run();
            }
        };
        runnable.runTask(plugin);
    }

    public final void uploadExecute(String BlockAreaJson, Player player) {

        try {

            //BlockAreaJson이 NPE 경우
            if (BlockAreaJson == null) {
                throw new NullPointerException("BlockAreaJson is NULL");
            }

            //Player가 NPE 일 경우
            if (player == null) {
                throw new NullPointerException("player is NULL");
            }

            //업로드를 실행함
            executor.execute(new Upload(BlockAreaJson, player));

        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void downloadExecute(Player player, String downloadBlockAddress, PlayerBlockStorageCache blockStorage) {

        try {

            //플레이어가 null일경우
            if (player == null) {
                throw new NullPointerException("Player is Empty");
            }

            //blockAddress가 null일경우
            if (downloadBlockAddress == null) {
                throw new NullPointerException("Request Downloaded Block is Empty");
            }

            //블록 다운로드를 요청함
            executor.execute(new Download(player.getUniqueId().toString(), downloadBlockAddress, blockStorage));

        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void shutdown() {
        executor.shutdown();
    }
}
