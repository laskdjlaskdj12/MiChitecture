package com.laskdjlaskdj12.EventLoop;

import com.laskdjlaskdj12.BlockMake.BlockMake;
import com.laskdjlaskdj12.BlockScan.BlockScan;
import com.laskdjlaskdj12.VO.BlockScanVO;
import com.laskdjlaskdj12.VO.PlayerBlockAreaInfoVO;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventLoop {
    private ExecutorService executor;
    private Plugin plugin;

    public EventLoop(Plugin plugin) {
        this.executor = Executors.newCachedThreadPool();
        this.plugin = plugin;
    }

    public void scanExecute(PlayerBlockAreaInfoVO playerBlockAreaInfoVO) {
        executor.execute(new BlockScan(playerBlockAreaInfoVO));
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

    public void shutdown() {
        executor.shutdown();
    }
}
