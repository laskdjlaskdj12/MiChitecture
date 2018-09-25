package com.laskdjlaskdj12.VO;

public class BlockCacheVO {
    private String PlyaerID;
    private BlockAreaVO blockAreaVO;

    public String getPlayerID() {
        return PlyaerID;
    }

    public void setPlayerID(String plyaerID) {
        PlyaerID = plyaerID;
    }

    public BlockAreaVO getBlockAreaVO() {
        return blockAreaVO;
    }

    public void setBlockAreaVO(BlockAreaVO blockAreaVO) {
        this.blockAreaVO = blockAreaVO;
    }
}
