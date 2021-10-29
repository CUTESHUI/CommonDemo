package wx.mp.analysis;


import com.shui.wx.beans.json.analysis.req.WxAnalysisDate;
import com.shui.wx.http.handler.analysis.WxAnalysisHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import wx.mp.base.BaseTest;

@Slf4j
public class WxAnalysisHandlerTest extends BaseTest {

    @Test
    public void testGetUserSummary() throws Exception{
        log.info(WxAnalysisHandler.getUserSummary(APPID, APPSECRET, new WxAnalysisDate("2018-07-20", "2018-07-26")).toJsonString());
    }
    @Test
    public void testGetUserCumulate() throws Exception{
        log.info(WxAnalysisHandler.getUserCumulate(APPID, APPSECRET, new WxAnalysisDate("2018-07-20", "2018-07-26")).toJsonString());
    }
}
