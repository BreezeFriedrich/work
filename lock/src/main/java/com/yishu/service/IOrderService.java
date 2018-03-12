package com.yishu.service;

import com.yishu.pojo.AuthOrder;
import com.yishu.pojo.CardInfo;

import java.util.List;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2018-03-12 15:59 admin
 * @since JDK1.7
 */
public interface IOrderService {
    public List<AuthOrder> getAuthOrder(String ownerPhoneNumber,String startTime,String endTime);
    public boolean increaseOrder(String ownerPhoneNumber, String roomTypeId, String roomId, String startTime, String endTime, String password,List<CardInfo> cardInfoList);
    public boolean modifyAuthOrder(String ownerPhoneNumber,String orderNumber,String password, String roomId, String startTime, String endTime,List<CardInfo> cardInfoList);
    public boolean cancleAuthOrder(String ownerPhoneNumber,String orderNumber);
}
