package wx.mp.core.token;

import com.shui.wx.http.handler.token.AccessTokenHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import wx.mp.base.BaseTest;

@Slf4j
public class AccessTokenWrapperTest extends BaseTest {

    @Test
    public void testGetAccessToken()throws Exception{
        String access_token = AccessTokenHandler.getAccessToken(APPID, APPSECRET);
        log.info(access_token);
    }
}
