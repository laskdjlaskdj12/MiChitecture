package com.laskdjlaskdj12.Command;

import com.laskdjlaskdj12.EventLoop.EventLoop;
import com.laskdjlaskdj12.Player.PlayerBlockStorageCache;
import com.laskdjlaskdj12.VO.BlockScanVO;
import com.laskdjlaskdj12.VO.PlayerBlockAreaInfoVO;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MakeCommand implements CommandExecutor {

    private PlayerBlockStorageCache cache;
    private EventLoop eventLoop;

    public MakeCommand(PlayerBlockStorageCache cache, EventLoop eventLoop) {
        this.cache = cache;
        this.eventLoop = eventLoop;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("반드시 플레이어로 해야합니다.");
            return false;
        }

        Player player = (Player) sender;

        PlayerBlockAreaInfoVO info = cache.getBlockAreaInfo(player.getUniqueId().toString());

        //첫번째 블록을 터치했는지 체크
        if (info.firstBlock == null) {
            player.sendRawMessage("설치할 블록의 위치를 반드시 터치해야합니다.");
            return true;
        }

        //만약 블록 스캔 Area가 없을경우
        if (info.blockScanArea == null) {
            player.sendRawMessage("저장된 블록이 없습니다.");
            return true;
        }

        Location startScanBlockLocation = info.firstBlock.getLocation();

        eventLoop.makeExecute(startScanBlockLocation, info);

        info.firstBlock = null;

        return true;

    }
}
