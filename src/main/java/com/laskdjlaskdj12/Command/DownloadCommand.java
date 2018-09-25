package com.laskdjlaskdj12.Command;

import com.google.gson.Gson;
import com.laskdjlaskdj12.EventLoop.EventLoop;
import com.laskdjlaskdj12.Player.PlayerBlockStorageCache;
import com.laskdjlaskdj12.VO.BlockAreaVO;
import com.laskdjlaskdj12.VO.BlockInfoVO;
import com.laskdjlaskdj12.VO.BlockScanVO;
import com.laskdjlaskdj12.VO.PlayerBlockAreaInfoVO;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DownloadCommand implements CommandExecutor{

    private final EventLoop eventLoop;
    private final PlayerBlockStorageCache BlockStorage;

    public DownloadCommand(PlayerBlockStorageCache blockStorage, EventLoop eventLoop){
        this.BlockStorage = blockStorage;
        this.eventLoop = eventLoop;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)){
            sender.sendMessage("플레이어만 다운로드를 할수있습니다.");
            return true;
        }

        Player player = (Player) sender;

        if(args.length < 1){
            player.sendRawMessage("/downloads [ 다운로드 오브젝트 ID ]");

            return true;
        }

        //다운로드를 요청함
        player.sendRawMessage("블록 다운로드를 요청합니다.");

        eventLoop.downloadExecute(player, args[0], BlockStorage);

        return true;
    }

    private String getJsonBlockAreaInfo(BlockAreaVO blockAreaVO) throws IllegalArgumentException {

        if (blockAreaVO == null){
            throw new IllegalArgumentException("blockAreaInfoVo is Empty");
        }

        Gson gson = new Gson();

        String blockAreaJson = gson.toJson(blockAreaVO);

        return blockAreaJson;
    }

    private BlockAreaVO converToBlockAreaInfoVO(PlayerBlockAreaInfoVO savedAreaBlock) {

        List<BlockInfoVO> blockInfoVOList = new ArrayList<BlockInfoVO>();

        //블록들의 위치, Material 정보들을 변환함
        for (BlockScanVO block: savedAreaBlock.blockScanArea){

            BlockInfoVO insertBlockInfoVO = new BlockInfoVO();

            insertBlockInfoVO.setX(block.BlockLocation.getX());
            insertBlockInfoVO.setY(block.BlockLocation.getY());
            insertBlockInfoVO.setZ(block.BlockLocation.getZ());
            insertBlockInfoVO.setMaterialID(block.BlockMaterial.getId());

            blockInfoVOList.add(insertBlockInfoVO);
        }

        //사용자 이름을 넣음
        BlockAreaVO blockAreaVO = new BlockAreaVO();

        blockAreaVO.setUserPID(savedAreaBlock.player.getUniqueId().toString());
        blockAreaVO.setBlockInfoVO(blockInfoVOList);

        return blockAreaVO;
    }

}