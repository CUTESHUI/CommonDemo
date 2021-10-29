package wx.mp.autoreply;

import com.shui.wx.http.handler.autoreply.WxAutoReplyHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import wx.mp.base.BaseTest;

@Slf4j
public class WxAutoReplyHandlerTest extends BaseTest {

    @Test
    public void testAutoReply() throws Exception{
        log.info(WxAutoReplyHandler.getAutoReply(APPID, APPSECRET));
    }
}
