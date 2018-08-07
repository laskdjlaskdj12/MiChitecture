package com.laskdjlaskdj12.Event;

import com.laskdjlaskdj12.Player.PlayerBlockStorageCache;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.EventListener;

public class PlayerBlockBreakEvent implements EventListener {

    private PlayerBlockStorageCache cache;

    public PlayerBlockBreakEvent(PlayerBlockStorageCache cache) {
        this.cache = cache;
    }

    @EventHandler
    public void onPlayerBlockBreakEvent(BlockBreakEvent e) {
        Player player = e.getPlayer();

        //만약 플레이어가 첫번째 영역을 지정을 할때
        if (cache.isFirstTouch(player)) {
            cache.registerStartArea(player, e.getBlock());

            player.sendRawMessage("첫번째 블록이 지정되었습니다.");
        } else {
            //플레이어가 두번째 영역을 지정을 할때
            cache.registerEndArea(player, e.getBlock());

            player.sendRawMessage("두번째 블록이 지정되었습니다.");

            //이제 영역 안의 리스트들을 만들어 보자

        }

        return;
    }
}
