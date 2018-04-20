package com.laskdjlaskdj12.Command;

import com.laskdjlaskdj12.Player.PlayerBlockStorageCache;
import com.laskdjlaskdj12.ScanBlockStruct.ScanBlockStruct;
import com.laskdjlaskdj12.UploadBlock.BlockCordinateStruct;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Vector;

public class DownloadBlock implements CommandExecutor {

    public DownloadBlock(PlayerBlockStorageCache cache) {
        PlayerDownloadBlockStorage = cache;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        String RequestBlockListPid = args[1];

        try {
            List<BlockCordinateStruct> PlayerDonwloadedBlockList = DownloadBlockList(RequestBlockListPid);
            Vector<ScanBlockStruct> PlayerDownalodScanBlockList = ToScanBlockList(PlayerDonwloadedBlockList);

            PlayerDownloadBlockStorage.saveCache(player.getName(), PlayerDownalodScanBlockList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    private List<BlockCordinateStruct> DownloadBlockList(String pid) throws Exception{
        List<BlockCordinateStruct> ReturnDownloadBlockCordinateStruct = new Vector<BlockCordinateStruct>();
        return ReturnDownloadBlockCordinateStruct;
    }

    private Vector<ScanBlockStruct> ToScanBlockList(List<BlockCordinateStruct> blocklist){
        Vector<ScanBlockStruct> ReturnScanBlockStructList = new Vector<ScanBlockStruct>();

        return ReturnScanBlockStructList;
    }

    private PlayerBlockStorageCache PlayerDownloadBlockStorage;
}
