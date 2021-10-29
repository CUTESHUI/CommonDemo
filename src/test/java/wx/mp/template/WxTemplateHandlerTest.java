package wx.mp.template;

import com.shui.wx.beans.json.template.WxTemplateDataItemSend;
import com.shui.wx.beans.json.template.industry.WxTemplateIndustry;
import com.shui.wx.beans.json.template.send.WxTemplateDataSend;
import com.shui.wx.beans.json.template.send.WxTemplateSend;
import com.shui.wx.http.handler.template.send.WxTemplateSendHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import wx.mp.base.BaseTest;

@Slf4j
public class WxTemplateHandlerTest extends BaseTest {

    @Test
    public void testGetIndustry() throws Exception{
        log.info(WxTemplateSendHandler.getIndustry(APPID, APPSECRET));
    }

    @Test
    public void testSetIndustry() throws Exception{
        WxTemplateIndustry wxIndustry = new WxTemplateIndustry();
        wxIndustry.setIndustry_id1("2");
        wxIndustry.setIndustry_id2("41");
        log.info(WxTemplateSendHandler.setIndustry(APPID, APPSECRET, wxIndustry));
    }

    @Test
    public void testAllTemplate() throws Exception{
        log.info(WxTemplateSendHandler.allTemplate(APPID, APPSECRET));
    }

    @Test
    public void testSendTemplate() throws Exception{
        WxTemplateDataSend wxTemplateDataSend = new WxTemplateDataSend();
        wxTemplateDataSend.setFirst(new WxTemplateDataItemSend("欢迎测试模板消息", "#173177"));
        wxTemplateDataSend.setKeyword1(new WxTemplateDataItemSend("测试成功", "#173177"));
        wxTemplateDataSend.setKeyword2(new WxTemplateDataItemSend("测试成功", "#173177"));
        wxTemplateDataSend.setKeyword3(new WxTemplateDataItemSend("测试成功", "#173177"));
        wxTemplateDataSend.setRemark(new WxTemplateDataItemSend("欢迎再次测试", "#173177"));

//        WxTemplateMiniprogramSend wxTemplateMiniprogramSend = new WxTemplateMiniprogramSend();
//        wxTemplateMiniprogramSend.setAppid("appid");
//        wxTemplateMiniprogramSend.setPagepath("pagepath");

        WxTemplateSend wxTemplateSend = new WxTemplateSend();
        wxTemplateSend.setData(wxTemplateDataSend);
//        wxTemplateSend.setMiniprogram(wxTemplateMiniprogramSend);
        wxTemplateSend.setTemplate_id("3zvJ6CV_Pqy9BK43N_8r5-kt5dwra7OQ_MLHsSyxXko");
        wxTemplateSend.setTouser("olhDZjpv-GZmITBOwBeb6t8KNPkw");
        wxTemplateSend.setUrl("https://www.baidu.com");

        log.info(wxTemplateSend.toJsonString());

        log.info(WxTemplateSendHandler.sendTemplate(APPID, APPSECRET, wxTemplateSend));
    }
}
