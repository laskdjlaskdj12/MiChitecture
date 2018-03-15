package com.laskdjlaskdj12.Command;

import com.laskdjlaskdj12.Player.PlayerBlockStorageCache;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Vector;

public class MakeCommand implements CommandExecutor {

    PlayerBlockStorageCache cache;

    public MakeCommand(PlayerBlockStorageCache cache) {
        this.cache = cache;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            ((Player) sender).sendRawMessage("블록 제작.");
            Vector<Block> BlockList = cache.getBlock(((Player) sender).getPlayerListName());

            for (Block block : BlockList) {
                Block TempBlock = Bukkit.getServer().getWorlds().get(0).getBlockAt(block.getLocation());
                TempBlock.setType(block.getType());
            }

            ((Player) sender).sendRawMessage("제작완료");
            return true;
        }

        return false;
    }

}
