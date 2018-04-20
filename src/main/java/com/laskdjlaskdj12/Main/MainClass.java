package com.laskdjlaskdj12.Main;

import com.laskdjlaskdj12.BlockScan.BlockScan;
import com.laskdjlaskdj12.Command.DownloadBlock;
import com.laskdjlaskdj12.Command.MakeCommand;
import com.laskdjlaskdj12.Command.UploadBlock;
import com.laskdjlaskdj12.Player.PlayerBlockStorageCache;
import com.laskdjlaskdj12.Player.PlayerTouchLocation;
import org.bukkit.plugin.java.JavaPlugin;

public class MainClass extends JavaPlugin {

    @Override
    public void onEnable(){

        System.out.println("로딩 완료.");

        PlayerBlockStorageCache cache = new PlayerBlockStorageCache();
        PlayerTouchLocation LastPlayerTouch = new PlayerTouchLocation();

        getServer().getPluginManager().registerEvents(new BlockScan(cache), this);
        this.getCommand("make").setExecutor(new MakeCommand(cache));
        this.getCommand("upload").setExecutor(new UploadBlock(cache));
        this.getCommand("download").setExecutor(new DownloadBlock(cache));
//        this.getCommand("startmakeDownloadLocation").setExecutor(new MakeBlock(cache, LastPlayerTouch));
    }

    @Override
    public void onDisable(){
    }
}
