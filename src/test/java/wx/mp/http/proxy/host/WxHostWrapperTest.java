package wx.mp.http.proxy.host;

import com.shui.wx.http.handler.host.WxHostHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import wx.mp.base.BaseTest;

@Slf4j
public class WxHostWrapperTest extends BaseTest {

    @Test
    public void testGetWxHosts() throws Exception{
        String ret = WxHostHandler.getHostList(APPID, APPSECRET);
        log.info(ret);
    }
}
