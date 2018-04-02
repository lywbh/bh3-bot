package com.lyw.vo;

/**
 * @Description
 * @ProjectName maintain-robot
 * @Package com.lyw.vo
 * @Author yangjh5
 * @CreateDate 2017/11/30
 */
public class CqpRespDiscussMsg extends CqpRespPrivMsg {

    private Boolean at_sender = true;

    public Boolean getAt_sender() {
        return at_sender;
    }

    public void setAt_sender(Boolean at_sender) {
        this.at_sender = at_sender;
    }
}
