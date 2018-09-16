package com.laskdjlaskdj12.VO;

import java.util.List;

public class BlockAreaInfoVO {
    private List<BlockInfoVO> blockInfoVO;
    private String PID;

    public String getUserPID() {
        return PID;
    }

    public void setUserPID(String PID) {
        this.PID = PID;
    }

    public List<BlockInfoVO> getBlockInfoVO() {
        return blockInfoVO;
    }

    public void setBlockInfoVO(List<BlockInfoVO> blockInfoVO) {
        this.blockInfoVO = blockInfoVO;
    }
}
