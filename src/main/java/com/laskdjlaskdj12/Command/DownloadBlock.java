package com.laskdjlaskdj12.Command;

import com.laskdjlaskdj12.DownloadBlock.Download;
import com.laskdjlaskdj12.Player.PlayerBlockStorageCache;
import com.laskdjlaskdj12.VO.BlockScanVO;
import com.laskdjlaskdj12.Type.BlockCordinateStruct;
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

            sender.sendMessage(RequestBlockListPid + " 을 다운로드 하는중..");

            //웹에서 Struct를 다운로드
            Download download = new Download();

            Vector<BlockCordinateStruct> DownloadBlocks = download.RequestBlockList(RequestBlockListPid);

            Vector<BlockScanVO> PlayerDownalodScanBlockList = ToScanBlockList(DownloadBlocks);

            PlayerDownloadBlockStorage.saveCache(player.getUniqueId().toString(), PlayerDownalodScanBlockList);

        } catch (Exception e) {
            ((Player) sender).sendRawMessage("블록을 정상적으로 다운로드 하지 못했습니다.");
            ((Player) sender).sendRawMessage("Reason : " + e.getMessage());
        }

        return true;
    }

    private List<BlockCordinateStruct> DownloadBlockList(String pid) throws Exception{
        List<BlockCordinateStruct> ReturnDownloadBlockCordinateStruct = new Vector<BlockCordinateStruct>();
        return ReturnDownloadBlockCordinateStruct;
    }

    private Vector<BlockScanVO> ToScanBlockList(List<BlockCordinateStruct> blocklist){
        Vector<BlockScanVO> returnBlockScanVOList = new Vector<BlockScanVO>();

        return returnBlockScanVOList;
    }

    private PlayerBlockStorageCache PlayerDownloadBlockStorage;
}
