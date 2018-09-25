package com.laskdjlaskdj12.Runnable;

import com.laskdjlaskdj12.Player.PlayerBlockStorageCache;
import org.bukkit.scheduler.BukkitRunnable;

public class CacheStoredSchedular extends BukkitRunnable {

    private PlayerBlockStorageCache cache;

    public CacheStoredSchedular(PlayerBlockStorageCache cache){
        this.cache = cache;
    }

    @Override
    public void run() {
        //현재 캐시에 블록이 있는지 확인
        if(cache.getSavedDownloadedBlock() == null){
            return;
        }

        if(cache.getSavedDownloadedBlock().isEmpty()){
            return;
        }

        cache.saveDownloadedReadyBlocks();
    }
}
