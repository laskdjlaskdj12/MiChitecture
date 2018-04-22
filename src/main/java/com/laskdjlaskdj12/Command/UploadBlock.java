package com.laskdjlaskdj12.Command;

import com.laskdjlaskdj12.Player.PlayerBlockStorageCache;
import com.laskdjlaskdj12.ScanBlockStruct.ScanBlockStruct;
import com.laskdjlaskdj12.UploadBlock.Upload;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Vector;

public class UploadBlock implements CommandExecutor {

    private PlayerBlockStorageCache BlockStorage;

    public UploadBlock(PlayerBlockStorageCache cache){
        BlockStorage = cache;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player player = (Player)sender;

            player.sendRawMessage("업로드중...");

            Vector<ScanBlockStruct> ScanBlockList = BlockStorage.getBlock(player.getUniqueId().toString());

            player.sendRawMessage("블록이 있는지 확인");
            if(ScanBlockList == null){
                player.sendRawMessage("지정된 블록이 없습니다.");
                return true;
            }

            player.sendRawMessage("업로드를 함");
            Upload uploadClass = new Upload();

            Boolean uploadReesult = uploadClass.uploadBlockInfo(ScanBlockList, ((Player) sender).getUniqueId().toString());

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
