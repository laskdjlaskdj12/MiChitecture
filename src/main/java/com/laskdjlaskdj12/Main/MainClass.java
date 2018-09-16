package com.laskdjlaskdj12.Main;

import com.laskdjlaskdj12.Command.UploadCommand;
import com.laskdjlaskdj12.Event.PlayerBreakBlockEvent;
import com.laskdjlaskdj12.EventLoop.EventLoop;
import com.laskdjlaskdj12.Player.PlayerBlockStorageCache;
import org.bukkit.plugin.java.JavaPlugin;

//import com.laskdjlaskdj12.Command.Download;
import com.laskdjlaskdj12.Command.MakeCommand;
//import com.laskdjlaskdj12.Command.UploadCommand;

public class MainClass extends JavaPlugin {

    private EventLoop executor;
    private PlayerBlockStorageCache cache;

    @Override
    public void onEnable(){

        System.out.println("로딩 완료.");

        EventLoop executor = new EventLoop(this);
        PlayerBlockStorageCache cache = new PlayerBlockStorageCache();

        getServer().getPluginManager().registerEvents(new PlayerBreakBlockEvent(cache, executor), this);
        this.getCommand("make").setExecutor(new MakeCommand(cache, executor));
        this.getCommand("upload").setExecutor(new UploadCommand(cache, executor));
//        this.getCommand("download").setExecutor(new Download(cache));
//        this.getCommand("startmakeDownloadLocation").setExecutor(new MakeBlock(cache, LastPlayerTouch));
    }

    @Override
    public void onDisable(){
        if (executor != null){
            executor.shutdown();
        }
    }
}
