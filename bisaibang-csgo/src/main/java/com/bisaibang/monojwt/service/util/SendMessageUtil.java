package com.bisaibang.monojwt.service.util;

import com.taobao.api.*;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

import java.util.Optional;

/**
 * from bisaibang v1
 * https://github.com/pkuhammer/bisaibang/blob/master/src/main/java/com/bisai666/util/SendMessage.java
 * Created by xiazhen on 16/7/14.
 * bsb v2
 */
public final class SendMessageUtil {





    private static final String SIGN_METHOD_MD5 = "md5";
    private static final String SIGN_METHOD_HMAC = "hmac";
    private static final String CHARSET_UTF8 = "utf-8";
    private static final String CONTENT_ENCODING_GZIP = "gzip";

    // TOP服务地址，正式环境需要设置为http://gw.api.taobao.com/router/rest
    private static final String serverUrl = "http://gw.api.taobao.com/router/rest";
    private static final String appKey = "24328279"; // 可替换为您的沙箱环境应用的appKey
    private static final String appSecret = "1a298293d4ab5b3afeaa9f173d195f91"; // 可替换为您的沙箱环境应用的appSecret
//    private static final String sessionKey = "test"; // 必须替换为沙箱账号授权得到的真实有效sessionKey

    /*private static String sendRegisterMessage(String cellPhone, String code) throws Exception{
        TaobaoClient client = new DefaultTaobaoClient(serverUrl, appKey, appSecret);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setExtend("123456");
        req.setSmsType("normal");
        req.setSmsFreeSignName("比赛帮");
        req.setSmsParamString("{\"code\":\""+code+"\",\"product\":\"OW公开赛\"}");
        req.setRecNum(cellPhone);
        req.setSmsTemplateCode("SMS_9691267");
        AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);

        return (rsp.getResult().getErrCode());
    }*/

   /* private static String sendEnrollMessage(String cellPhone) throws Exception{
        TaobaoClient client = new DefaultTaobaoClient(serverUrl, appKey, appSecret);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setSmsType("normal");
        req.setSmsFreeSignName("比赛帮");
        req.setSmsTemplateCode("SMS_55170002");
        req.setRecNum(cellPhone);
        AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);

        return (rsp.getResult().getErrCode());
    }*/

    private static String sendRegisterMessage(String cellPhone, String code) throws Exception {
        TaobaoClient client = new DefaultTaobaoClient(serverUrl, appKey, appSecret);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setExtend("123456");
        req.setSmsType("normal");
        req.setSmsFreeSignName("联盟电竞");
        req.setSmsParamString("{\"code\":\"" + code + "\",\"product\":\"CS:GO传奇系列赛\"}");
        req.setRecNum(cellPhone);
        req.setSmsTemplateCode("SMS_58120134");
        AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);

        return (rsp.getResult().getErrCode());
    }

    private static String sendResetMessage(String cellPhone, String code) throws Exception{
        TaobaoClient client = new DefaultTaobaoClient(serverUrl, appKey, appSecret);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setExtend("123456");
        req.setSmsType("normal");
        req.setSmsFreeSignName("联盟电竞");
        req.setSmsParamString("{\"code\":\"" + code + "\",\"product\":\"CS:GO传奇系列赛\"}");
        req.setRecNum(cellPhone);
        req.setSmsTemplateCode("SMS_58120132");
        AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);

        return (rsp.getResult().getErrCode());
    }

    /**
     *给已经注册过的用户发短信
     */
    public static String sendAlreadyRegisterMessage(String cellPhone) throws Exception{
        TaobaoClient client = new DefaultTaobaoClient(serverUrl, appKey, appSecret);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setExtend("123456");
        req.setSmsType("normal");
        req.setSmsFreeSignName("联盟电竞");
        req.setRecNum(cellPhone);
        req.setSmsTemplateCode("SMS_86635060");
        AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);

        return (rsp.getResult().getErrCode());
    }

    /**
     *给报名成功的用户发短信
     */
    public static String sendEnrollMessage(String cellPhone) throws Exception{
       TaobaoClient client = new DefaultTaobaoClient(serverUrl, appKey, appSecret);
       AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
       req.setExtend("123456");
       req.setSmsType("normal");
       req.setSmsFreeSignName("联盟电竞");
       req.setRecNum(cellPhone);
       req.setSmsTemplateCode("SMS_86675063");
       AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);

       return (rsp.getResult().getErrCode());
   }



    private static String sendLeaveTeamMessage(String cellPhone) throws Exception {
        TaobaoClient client = new DefaultTaobaoClient(serverUrl, appKey, appSecret);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setExtend("123456");
        req.setSmsType("normal");
        req.setSmsFreeSignName("联盟电竞");
        req.setRecNum(cellPhone);
        req.setSmsTemplateCode("SMS_86635053");
        AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);

        return (rsp.getResult().getErrCode());
    }

    /**
     *给所有绑定战网ID的人发短信
     */
    private static String sendBattleIdMessage(String cellPhone) throws Exception {
        TaobaoClient client = new DefaultTaobaoClient(serverUrl, appKey, appSecret);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setExtend("123456");
        req.setSmsType("normal");
        req.setSmsFreeSignName("联盟电竞");
        req.setRecNum(cellPhone);
        req.setSmsTemplateCode("SMS_86600061");
        AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);

        return (rsp.getResult().getErrCode());
    }

   /* private static String sendResetMessage(String cellPhone, String code) throws Exception{
        TaobaoClient client = new DefaultTaobaoClient(serverUrl, appKey, appSecret);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setExtend("123456");
        req.setSmsType("normal");
        req.setSmsFreeSignName("比赛帮");
        req.setSmsParamString("{\"code\":\""+code+"\",\"product\":\"比赛帮\"}");
        req.setRecNum(cellPhone);
        req.setSmsTemplateCode("SMS_9711279");//SMS_13010848
        AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);

        return (rsp.getResult().getErrCode());
    }*/



    public static Optional<Boolean> sendCode(String phone, String code) {
        try {
            SendMessageUtil.sendRegisterMessage(phone, code);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.of(null);//httpstatus 为500
        }
        return Optional.of(true);
    }

    public static Boolean sendForgetCode(String phone, String code) {
        try {
            SendMessageUtil.sendResetMessage(phone, code);
        } catch (Exception e) {
            e.printStackTrace();
            return false;//httpstatus 为500
        }
        return true;
    }

    public static Optional<Boolean> sendRegisterOKMessage(String phone){
        try {
            SendMessageUtil.sendAlreadyRegisterMessage(phone);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.of(null);//httpstatus 为500
        }
        return Optional.of(true);
    }

    public static Optional<Boolean> sendEnrollOKMessage(String phone){
        try {
            SendMessageUtil.sendEnrollMessage(phone);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.of(null);//httpstatus 为500
        }
        return Optional.of(true);
    }



    public static Optional<Boolean> sendTeamMessage(String phone) {
        try {
            SendMessageUtil.sendLeaveTeamMessage(phone);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.of(null);//httpstatus 为500
        }
        return Optional.of(true);
    }

    public static Optional<Boolean> sendBattleMessage(String phone) {
        try {
            SendMessageUtil.sendBattleIdMessage(phone);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.of(null);//httpstatus 为500
        }
        return Optional.of(true);
    }


}
