package com.laskdjlaskdj12.Main;

import com.laskdjlaskdj12.BlockScan.BlockScan;
import com.laskdjlaskdj12.Command.MakeCommand;
import com.laskdjlaskdj12.Command.SaveCommand;
import com.laskdjlaskdj12.Player.PlayerBlockStorageCache;
import com.laskdjlaskdj12.UploadBlock.Upload;
import org.bukkit.plugin.java.JavaPlugin;

public class MainClass extends JavaPlugin {

    @Override
    public void onEnable(){

        System.out.println("로딩 완료.");

        PlayerBlockStorageCache cache = new PlayerBlockStorageCache();

        getServer().getPluginManager().registerEvents(new BlockScan(cache), this);
        this.getCommand("make").setExecutor(new MakeCommand(cache));
        this.getCommand("upload").setExecutor(new UploadBlock(cache));
    }

    @Override
    public void onDisable(){
    }
}
