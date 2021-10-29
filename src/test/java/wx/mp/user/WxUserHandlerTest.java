package wx.mp.user;

import com.shui.wx.beans.json.oauth.WxOAuth2GetUser;
import com.shui.wx.beans.json.user.tag.*;
import com.shui.wx.http.handler.user.WxUserHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import wx.mp.base.BaseTest;

@Slf4j
public class WxUserHandlerTest extends BaseTest {

    @Test
    public void testCreateTag() throws Exception{
        //wxUserTag.setTag(wxUserTagName);
        WxUserTag wxUserTag = new WxUserTag();
        WxUserTagName wxUserTagName = new WxUserTagName();
        wxUserTagName.setName("测试标签");
        wxUserTag.setTag(wxUserTagName);
        log.info(WxUserHandler.createTag(APPID, APPSECRET, wxUserTag).toJsonString());
    }

    @Test
    public void testGetTag() throws Exception{
        log.info(WxUserHandler.getTag(APPID, APPSECRET).toJsonString());
    }

    @Test
    public void testUpdateTag() throws Exception{
        WxUserTagCreateResp wxUserTagCreateResp = new WxUserTagCreateResp();
        WxUserTagName wxUserTagName = new WxUserTagName();
        wxUserTagName.setId(100);
        wxUserTagName.setName("用户标签");
        wxUserTagCreateResp.setTag(wxUserTagName);
        log.info(WxUserHandler.updateTag(APPID, APPSECRET, wxUserTagCreateResp).toJsonString());
    }

    @Test
    public void testGetWxUseInfo() throws Exception{
        WxUserTagLang wxUserTagLang = new WxUserTagLang();
        wxUserTagLang.setLang("zh_CN");
        wxUserTagLang.setOpenid("olhDZjvl7GADzpmkJpqqgiSzAp5M");
        log.info(wxUserTagLang.toMap().toString());
        log.info(WxUserHandler.getWxUseInfo(APPID, APPSECRET, wxUserTagLang).toJsonString());
    }

    @Test
    public void testGetWxUserGetList() throws Exception{
        WxUserTagOpenId wxUserTagOpenId = new WxUserTagOpenId();
        log.info(wxUserTagOpenId.toMap().toString());
        log.info(WxUserHandler.getWxUserGetList(APPID, APPSECRET, wxUserTagOpenId).toJsonString());
    }
    @Test
    public void testGetWxUserBlackList() throws Exception{
        WxUserTagOpenId wxUserTagOpenId = new WxUserTagOpenId();
        log.info(wxUserTagOpenId.toMap().toString());
        log.info(WxUserHandler.getUserBlackList(APPID, APPSECRET, wxUserTagOpenId).toJsonString());
    }

    @Test
    public void testWxOAuth2GetUserTest()throws Exception{
        WxOAuth2GetUser wxOAuth2GetUser = new WxOAuth2GetUser();
        wxOAuth2GetUser.setOpenid("ojTxfw5HQgBheU3j-NgNwjVXL6mA");
        wxOAuth2GetUser.setLang("zh_CN");
        wxOAuth2GetUser.setAccess_token("27273878");
        System.out.println(wxOAuth2GetUser.toMap());
    }
}
