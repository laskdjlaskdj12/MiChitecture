package com.laskdjlaskdj12.VO;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Vector;

public class PlayerBlockAreaInfoVO {
    public Player player;

    public Block firstBlock;
    public Block lastBlock;

    public Vector<BlockScanVO> blockScanArea;

}
