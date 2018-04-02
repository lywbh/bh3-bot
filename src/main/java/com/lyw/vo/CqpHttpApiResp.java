package com.lyw.vo;

import java.util.Map;

/**
 * @Description
 * @ProjectName maintain-robot
 * @Package com.lyw.vo
 * @Author yangjh5
 * @CreateDate 2017/12/2
 */
public class CqpHttpApiResp {

    private int retcode;

    private String status;

    private Map<String, Object> data;

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
