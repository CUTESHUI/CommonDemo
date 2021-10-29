package wx.mp.mass;

import com.shui.wx.beans.json.mass.speed.WxMassSpeed;
import com.shui.wx.http.handler.mass.WxMassHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import wx.mp.base.BaseTest;

@Slf4j
public class MassTest extends BaseTest {

    @Test
    public void testGetMassSpeed() throws Exception{
        log.info(WxMassHandler.getMassSpeed(APPID, APPSECRET));
    }

    @Test
    public void testSetMassSpeed() throws Exception{
        log.info(WxMassHandler.setMassSpeed(APPID, APPSECRET, new WxMassSpeed(1)));
    }
}
