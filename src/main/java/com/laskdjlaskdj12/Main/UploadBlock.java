package com.laskdjlaskdj12.Main;

import com.laskdjlaskdj12.Player.PlayerBlockStorageCache;
import com.laskdjlaskdj12.ScanBlockStruct.ScanBlockStruct;
import com.laskdjlaskdj12.UploadBlock.Upload;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Vector;

public class UploadBlock implements CommandExecutor {

    private PlayerBlockStorageCache cache;

    public UploadBlock(PlayerBlockStorageCache cache){
        cache = cache;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player player = (Player)sender;

            player.sendRawMessage("업로드중...");

            Vector<ScanBlockStruct> ScanBlockList = cache.getBlock(player.getName());

            if(ScanBlockList.isEmpty()){
                player.sendRawMessage("지정된 블록이 없습니다.");
                return true;
            }

            Upload uploadClass = new Upload();

            Boolean uploadReesult = uploadClass.uploadBlockInfo(ScanBlockList, sender.getName());

            if(!uploadReesult){
                player.sendRawMessage("업로드에 실패했습니다.");
            }else{
                player.sendRawMessage("업로드에 성공했습니다.");
            }

            return true;
        }

        return false;
    }
}
