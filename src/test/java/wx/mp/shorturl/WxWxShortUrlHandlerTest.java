package wx.mp.shorturl;

import com.shui.wx.beans.json.shorturl.WxShortUrl;
import com.shui.wx.http.handler.shorturl.WxWxShortUrlHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import wx.mp.base.BaseTest;

@Slf4j
public class WxWxShortUrlHandlerTest extends BaseTest {

    @Test
    public void testCreateWxShortUrl() throws Exception{
        //{"errcode":0,"errmsg":"ok","short_url":"https://w.url.cn/s/Ai6ti7p"}
        WxShortUrl wxShortUrl = new WxShortUrl("long2short","http://cluster.cardiochina.net/medcare/dest/client/fdSignWX/index.html?hospitalId=8a8383a860c567cc0160d43c1554002f");
        log.info(WxWxShortUrlHandler.createWxShortUrl(APPID, APPSECRET, wxShortUrl).toJsonString());
    }
}
