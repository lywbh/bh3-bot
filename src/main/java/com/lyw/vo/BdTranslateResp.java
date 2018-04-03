package com.lyw.vo;

import java.util.List;

/**
 * Created by yiweiliang.
 * User: yiweiliang1
 * Date: 2018/4/3
 */
public class BdTranslateResp {

    private String from;

    private String to;

    private List<BdTranslateResult> trans_result;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public List<BdTranslateResult> getTrans_result() {
        return trans_result;
    }

    public void setTrans_result(List<BdTranslateResult> trans_result) {
        this.trans_result = trans_result;
    }
}