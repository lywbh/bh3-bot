package com.lyw.dmo;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

@Table("t_chengyu")
public class Chengyu {

    @Id
    private int id;

    @Name
    @Column
    private String ChengYu;

    @Column
    private String PingYin;

    @Column
    private String DianGu;

    @Column
    private String ChuChu;

    @Column
    private String LiZi;

    @Column
    private String SPingYin;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChengYu() {
        return ChengYu;
    }

    public void setChengYu(String chengYu) {
        ChengYu = chengYu;
    }

    public String getPingYin() {
        return PingYin;
    }

    public void setPingYin(String pingYin) {
        PingYin = pingYin;
    }

    public String getDianGu() {
        return DianGu;
    }

    public void setDianGu(String dianGu) {
        DianGu = dianGu;
    }

    public String getChuChu() {
        return ChuChu;
    }

    public void setChuChu(String chuChu) {
        ChuChu = chuChu;
    }

    public String getLiZi() {
        return LiZi;
    }

    public void setLiZi(String liZi) {
        LiZi = liZi;
    }

    public String getSPingYin() {
        return SPingYin;
    }

    public void setSPingYin(String SPingYin) {
        this.SPingYin = SPingYin;
    }

}
