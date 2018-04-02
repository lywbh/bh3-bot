package com.lyw.vo;

/**
 * @Description
 * @ProjectName maintain-robot
 * @Package com.lyw.vo
 * @Author yangjh5
 * @CreateDate 2017/11/30
 */
public class CqpRespGroupMsg extends CqpRespDiscussMsg {

    // 撤回
    private Boolean delete;

    // 把发送者踢出群
    private Boolean kick;

    // 把发送者禁言
    private Boolean ban;

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    public Boolean getKick() {
        return kick;
    }

    public void setKick(Boolean kick) {
        this.kick = kick;
    }

    public Boolean getBan() {
        return ban;
    }

    public void setBan(Boolean ban) {
        this.ban = ban;
    }
}
