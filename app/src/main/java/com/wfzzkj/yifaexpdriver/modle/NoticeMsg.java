package com.wfzzkj.yifaexpdriver.modle;

import cn.bmob.v3.BmobObject;

/**
 * 作者--> coding  on 2017/12/23.
 * com.wfzzkj.yifaexpdriver.modle
 * 邮箱--> www14159@163.com
 * Created by coding on 2017/12/23.
 */

public class NoticeMsg extends BmobObject {
    GoodsForYifa good;
    DriverForYifa driverFrom;//发送消息的司机
    DriverForYifa driverMy;//接受消息的司机 本人
    Integer noticeType;//0 来自普通的司机通知     1其他       100最高级别管理员消息 所有人必须接收
    String content;//通知大体内容

    public GoodsForYifa getGood() {
        return good;
    }

    public void setGood(GoodsForYifa good) {
        this.good = good;
    }

    public DriverForYifa getDriverFrom() {
        return driverFrom;
    }

    public void setDriverFrom(DriverForYifa driverFrom) {
        this.driverFrom = driverFrom;
    }

    public DriverForYifa getDriverMy() {
        return driverMy;
    }

    public void setDriverMy(DriverForYifa driverMy) {
        this.driverMy = driverMy;
    }

    public Integer getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(Integer noticeType) {
        this.noticeType = noticeType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
