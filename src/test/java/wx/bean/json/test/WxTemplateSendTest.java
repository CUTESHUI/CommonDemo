package wx.bean.json.test;

import com.shui.wx.beans.json.template.WxTemplateDataItemSend;
import com.shui.wx.beans.json.template.WxTemplateMiniprogramSend;
import com.shui.wx.beans.json.template.send.WxTemplateDataSend;
import com.shui.wx.beans.json.template.send.WxTemplateSend;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class WxTemplateSendTest {

    @Test
    public void testWxTemplateSend(){
        WxTemplateDataSend wxTemplateDataSend = new WxTemplateDataSend();
        wxTemplateDataSend.setFirst(new WxTemplateDataItemSend("巧克力", "#173177"));
        wxTemplateDataSend.setKeyword1(new WxTemplateDataItemSend("巧克力", "#173177"));
        wxTemplateDataSend.setRemark(new WxTemplateDataItemSend("巧克力", "#173177"));

        WxTemplateMiniprogramSend wxTemplateMiniprogramSend = new WxTemplateMiniprogramSend();
        wxTemplateMiniprogramSend.setAppid("appid");
        wxTemplateMiniprogramSend.setPagepath("pagepath");

        WxTemplateSend wxTemplateSend = new WxTemplateSend();
        wxTemplateSend.setData(wxTemplateDataSend);
        wxTemplateSend.setMiniprogram(wxTemplateMiniprogramSend);
        wxTemplateSend.setTemplate_id("template_id");
        wxTemplateSend.setTouser("toUser");
        wxTemplateSend.setUrl("https://www.baidu.com");

        log.info(wxTemplateSend.toJsonString());
    }
}
