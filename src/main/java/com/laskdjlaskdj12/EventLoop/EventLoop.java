package com.laskdjlaskdj12.EventLoop;

import com.laskdjlaskdj12.Block.Make.BlockMake;
import com.laskdjlaskdj12.Block.Scan.BlockScan;
import com.laskdjlaskdj12.Network.Upload.Upload;
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

    private List<Future<String>> FutureList;

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

    public void uploadExecute(String BlockAreaJson, Player player) throws NullPointerException{

        //BlockAreaJson이 NPE 경우
        if(BlockAreaJson == null){
            NullPointerException exception = new NullPointerException("BlockAreaJson is NULL");

            throw exception;
        }

        //Player가 NPE 일 경우
        if(player == null){
            NullPointerException exception = new NullPointerException("player is NULL");

            throw exception;
        }

        //업로드를 실행함
        Future uploadFuture = executor.submit(new Upload(BlockAreaJson, player));

        FutureList.add(uploadFuture);
    }


    public void shutdown() {
        executor.shutdown();
    }
}
