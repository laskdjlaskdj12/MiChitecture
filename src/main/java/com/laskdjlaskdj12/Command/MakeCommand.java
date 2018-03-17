package com.laskdjlaskdj12.Command;

import com.laskdjlaskdj12.Player.PlayerBlockStorageCache;
import com.laskdjlaskdj12.ScanBlockStruct.ScanBlockStruct;
import org.bukkit.Location;
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
            Vector<ScanBlockStruct> BlockList = cache.getBlock(((Player) sender).getPlayerListName());

            if (BlockList.isEmpty()) {
                ((Player) sender).sendRawMessage("저장된 블록이 없습니다.");
                return true;
            }

            for (ScanBlockStruct block : BlockList) {
                Location setLocation = block.BlockLocation;
                setLocation.getBlock().setType(block.BlockMaterial);
            }

            ((Player) sender).sendRawMessage("제작완료");
            return true;
        }

        return false;
    }

}
