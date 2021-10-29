package wx.bean.json.test;

import com.shui.wx.beans.json.kfaccount.message.WxKfaccountTextMessage;
import com.shui.wx.beans.json.kfaccount.message.WxKfaccountTextMessageItem;
import com.shui.wx.http.handler.kfaccount.WxKfaccountHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import wx.mp.base.BaseTest;

@Slf4j
public class WxKfaccountTextMessageTest extends BaseTest {

    private WxKfaccountTextMessage getWxKfaccountTextMessage(){
        WxKfaccountTextMessageItem wxKfaccountTextMessageItem = new WxKfaccountTextMessageItem();
        wxKfaccountTextMessageItem.setContent("hello world");
        WxKfaccountTextMessage wxKfaccountTextMessage = new WxKfaccountTextMessage();
        wxKfaccountTextMessage.setText(wxKfaccountTextMessageItem);
        wxKfaccountTextMessage.setMsgtype("text");
        wxKfaccountTextMessage.setTouser("olhDZjpv-GZmITBOwBeb6t8KNPkw");
        return wxKfaccountTextMessage;
    }

    @Test
    public void testWxKfaccountTextMessage(){
        WxKfaccountTextMessage wxKfaccountTextMessage = getWxKfaccountTextMessage();
        log.info(wxKfaccountTextMessage.toJsonString());
    }

    @Test
    public void testSendWxKfaccountTextMessage() throws Exception{
        WxKfaccountTextMessage wxKfaccountTextMessage = getWxKfaccountTextMessage();
        String ret = WxKfaccountHandler.sendWxKfaccountTextMessage(APPID, APPSECRET, wxKfaccountTextMessage);
        log.info(ret);
    }
}
