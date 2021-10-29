package wx.mp.oauth;

import com.shui.wx.beans.json.oauth.WxOAuth2Code;
import com.shui.wx.http.handler.oauth.WxOAuth2Handler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import wx.mp.base.BaseTest;

@Slf4j
public class WxOAuth2HandlerTest extends BaseTest {

    @Test
    public void testGetUserInfo() throws Exception{
        WxOAuth2Code wxOAuth2Code = new WxOAuth2Code();
        wxOAuth2Code.setCode("123");
        log.info(WxOAuth2Handler.getUserInfo(APPID, APPSECRET, wxOAuth2Code).toJsonString());
    }
}
