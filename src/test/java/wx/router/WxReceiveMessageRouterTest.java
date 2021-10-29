package wx.router;

import com.shui.wx.beans.router.receive.WxReceiveRouterResult;
import com.shui.wx.beans.xml.message.receive.base.WxBaseReceiveMessage;
import com.shui.wx.beans.xml.message.receive.image.WxReceiveImageMessage;
import com.shui.wx.beans.xml.message.receive.text.WxReceiveTextMessage;
import com.shui.wx.constants.WxConstants;
import com.shui.wx.core.router.WxReceiveMessageRouter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class WxReceiveMessageRouterTest {

    private Map<String, Class<? extends WxBaseReceiveMessage>> map = new HashMap<String, Class<? extends WxBaseReceiveMessage>>();


    @Test
    public void testTextRouter() throws Exception {
        map.put(WxConstants.ROUTER_TEXT, WxReceiveTextMessage.class);
        log.info(map.toString());
        long startTime = System.currentTimeMillis();
        log.info("开始：" + startTime);
        String str = "<message><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>1348831860</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[this is a test]]></Content><MsgId>1234567890123456</MsgId></message>";
        WxReceiveRouterResult wxReceiveRouterResult = WxReceiveMessageRouter.router(str);
        if(WxConstants.ROUTER_TEXT.equals(wxReceiveRouterResult.getRouterType())){
            log.info(wxReceiveRouterResult.toJsonString());
            log.info(wxReceiveRouterResult.getBaseReceiveMessage().toJsonString());
            log.info(String.valueOf(wxReceiveRouterResult.getBaseReceiveMessage() instanceof  WxReceiveTextMessage));
            WxReceiveTextMessage wxReceiveTextMessage = (WxReceiveTextMessage) wxReceiveRouterResult.getBaseReceiveMessage();
            log.info(wxReceiveTextMessage.toString());
        }
        long endTime = System.currentTimeMillis();
        log.info("结束：" + endTime);
        log.info("间隔：" + (endTime - startTime));
    }
    @Test
    public void testImageRouter(){
        long startTime = System.currentTimeMillis();
        log.info("开始：" + startTime);
        String str = "<message><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>1348831860</CreateTime><MsgType><![CDATA[image]]></MsgType><PicUrl><![CDATA[this is a url]]></PicUrl><MediaId><![CDATA[media_id]]></MediaId><MsgId>1234567890123456</MsgId></message>";
        WxReceiveRouterResult wxReceiveRouterResult = WxReceiveMessageRouter.router(str);
        if(WxConstants.ROUTER_IMAGE.equals(wxReceiveRouterResult.getRouterType())){
            log.info(wxReceiveRouterResult.toJsonString());
            log.info(wxReceiveRouterResult.getBaseReceiveMessage().toJsonString());
            WxReceiveImageMessage wxReceiveImageMessage = (WxReceiveImageMessage) wxReceiveRouterResult.getBaseReceiveMessage();
            log.info(wxReceiveImageMessage.toString());
        }
        long endTime = System.currentTimeMillis();
        log.info("结束：" + endTime);
        log.info("间隔：" + (endTime - startTime));
    }
    @Test
    public void testScanSubscriveRouter(){
        long startTime = System.currentTimeMillis();
        log.info("开始：" + startTime);
        String str = "<message><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[FromUser]]></FromUserName><CreateTime>123456789</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[subscribe]]></Event><EventKey><![CDATA[qrscene_123123]]></EventKey><Ticket><![CDATA[TICKET]]></Ticket></message>";
        WxReceiveRouterResult wxReceiveRouterResult = WxReceiveMessageRouter.router(str);
        log.info(wxReceiveRouterResult.toJsonString());
        long endTime = System.currentTimeMillis();
        log.info("结束：" + endTime);
        log.info("间隔：" + (endTime - startTime));
    }
}
