package wx.mp.http.proxy.token;

import com.shui.wx.beans.json.token.WxAccessToken;
import com.shui.wx.http.proxy.token.AccessTokenProxy;
import com.shui.wx.utils.json.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import wx.mp.base.BaseTest;

@Slf4j
public class AccessTokenProxyTest extends BaseTest {

    @Test
    public void testGetAccessToken() throws Exception {
        String accessToken = AccessTokenProxy.getAccessToken(APPID, APPSECRET);
        WxAccessToken wxAccessToken = JsonUtils.json2Bean(accessToken, WxAccessToken.class);
        log.info("" +accessToken);
        log.info("" +wxAccessToken);
    }
}
