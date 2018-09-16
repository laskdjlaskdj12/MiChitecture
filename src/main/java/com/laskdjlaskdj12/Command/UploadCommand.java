package com.laskdjlaskdj12.Command;

import com.google.gson.Gson;
import com.laskdjlaskdj12.EventLoop.EventLoop;
import com.laskdjlaskdj12.Player.PlayerBlockStorageCache;
import com.laskdjlaskdj12.VO.BlockAreaInfoVO;
import com.laskdjlaskdj12.VO.BlockInfoVO;
import com.laskdjlaskdj12.VO.BlockScanVO;
import com.laskdjlaskdj12.VO.PlayerBlockAreaInfoVO;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class UploadCommand implements CommandExecutor {

    private PlayerBlockStorageCache BlockStorage;
    private EventLoop eventLoop;

    public UploadCommand(PlayerBlockStorageCache cache, EventLoop eventLoop){
        this.BlockStorage = cache;
        this.eventLoop = eventLoop;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //만약 플레이어가 아닐경우
        if(!(sender instanceof Player)){
            sender.sendMessage("플레이어만 업로드를 할수있습니다.");
        }

        Player player = (Player) sender;

        try {
            //블록들이 저장되어있는지 체크
            PlayerBlockAreaInfoVO savedAreaBlock = BlockStorage.getBlockAreaInfo(player.getUniqueId().toString());

            if (savedAreaBlock == null) {
                player.sendRawMessage("블록이 저장되어있지 않습니다. ");
            }

            //블록 리스트 가 있는지 확인
            if (savedAreaBlock.blockScanArea == null || savedAreaBlock.blockScanArea.isEmpty()) {
                player.sendRawMessage("블록이 없습니다.");
            }

            //블록영역들을 BlockAreaInfoVO으로 변환하기
            BlockAreaInfoVO blockAreaInfoVO = converToBlockAreaInfoVO(savedAreaBlock);

            //블록리스트들을 Json으로 변환
            String jsonBlockAreaInfo = getJsonBlockAreaInfo(blockAreaInfoVO);

            //업로드를 이벤트 루프에 요청을 넣기
            eventLoop.uploadExecute(jsonBlockAreaInfo, player);

        } catch(NullPointerException except){
            except.printStackTrace();
            player.sendRawMessage("업로드에 실패를 했습니다.");
        }

        return true;
    }

    private String getJsonBlockAreaInfo(BlockAreaInfoVO blockAreaInfoVO) {
        Gson gson = new Gson();

        String json = gson.toJson(blockAreaInfoVO.getClass());

        return json;
    }

    private BlockAreaInfoVO converToBlockAreaInfoVO(PlayerBlockAreaInfoVO savedAreaBlock) {

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
        BlockAreaInfoVO blockAreaInfoVO = new BlockAreaInfoVO();

        blockAreaInfoVO.setUserPID(savedAreaBlock.player.getUniqueId().toString());
        blockAreaInfoVO.setBlockInfoVO(blockInfoVOList);

        return blockAreaInfoVO;
    }
}
