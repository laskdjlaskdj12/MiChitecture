package com.laskdjlaskdj12.Event;

import com.laskdjlaskdj12.EventLoop.EventLoop;
import com.laskdjlaskdj12.Player.PlayerBlockStorageCache;
import com.laskdjlaskdj12.VO.PlayerBlockAreaInfoVO;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.PlayerInventory;

public class PlayerBreakBlockEvent implements Listener {

    private PlayerBlockStorageCache cache;
    private EventLoop blockScanExecutor;

    public PlayerBreakBlockEvent(PlayerBlockStorageCache cache,
                                 EventLoop executor){
        this.cache = cache;
        this.blockScanExecutor = executor;
    }

    @EventHandler
    public void onPlayerBreakBlockEvent(BlockBreakEvent e){

        Player player = e.getPlayer();

        PlayerInventory playerInventory = player.getInventory();

        //현재 플레이어가 나무 도끼를 가지고 있을때
        if(playerInventory.getItemInMainHand().getType() != Material.WOOD_AXE){

            return;
        }

        //해당 플레이어가 원하는 블록에 나무도끼로 때렸을때
        if(cache.isOneTapped(player.getUniqueId().toString())){

            player.sendRawMessage("첫번째 블록을 터치했습니다.");

            //첫번재 블록은 터치했음을 알려줌
            cache.setFirstBlock(player.getUniqueId().toString(), player, e.getBlock());

            e.setCancelled(true);
            return;
        }

        //두번째 블록을 터치했을경우 마지막 영역의 위치를 정함
        cache.setLastBlock(player.getUniqueId().toString(), e.getBlock());

        //멀티스레드로 블록들을 등록하는 과정을 거침
        player.sendRawMessage("블록들을 등록하고 있습니다... ");

        PlayerBlockAreaInfoVO playerBlockAreaInfoVO = cache.getBlockAreaInfo(player.getUniqueId().toString());

        //블록을 스캔 Executor에 요청함
        blockScanExecutor.scanExecute(playerBlockAreaInfoVO);

        //이벤트들을 캔슬함
        e.setCancelled(true);
    }
}
