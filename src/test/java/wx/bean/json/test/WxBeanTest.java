package wx.bean.json.test;

import com.shui.wx.beans.json.mass.openid.WxMassOpenIdMediaId;
import com.shui.wx.beans.json.mass.openid.news.WxMassOpenIdNewsMassage;
import com.shui.wx.beans.json.mass.openid.video.WxMassOpenIdMedia;
import com.shui.wx.beans.json.mass.openid.video.WxMassOpenIdVideoMessage;
import com.shui.wx.beans.json.mass.preview.WxMassPreviewMediaId;
import com.shui.wx.beans.json.mass.preview.news.WxMassPreviewNewsMessage;
import com.shui.wx.beans.json.mass.preview.wxcard.WxMassPreviewCardInfo;
import com.shui.wx.beans.json.mass.preview.wxcard.WxMassPreviewCardInfoExt;
import com.shui.wx.beans.json.mass.preview.wxcard.WxMassPreviewCardMessage;
import com.shui.wx.beans.json.template.WxTemplateDataItemSend;
import com.shui.wx.beans.json.template.WxTemplateMiniprogramSend;
import com.shui.wx.beans.json.template.subscribe.WxTemplateContentSubscribe;
import com.shui.wx.beans.json.template.subscribe.WxTemplateSubscribe;
import com.shui.wx.beans.xml.message.receive.event.template.WxTemplateEventMessage;
import com.shui.wx.utils.xml.handler.XStreamHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class WxBeanTest {


    @Test
    public void testMessage() throws Exception {
        WxMassOpenIdNewsMassage wxMassOpenIdNewsMassage = new WxMassOpenIdNewsMassage();
        List<String> openIds = new ArrayList<>();
        openIds.add("123");
        openIds.add("456");

        WxMassOpenIdMediaId wxMassOpenIdMediaId = new WxMassOpenIdMediaId();
        wxMassOpenIdMediaId.setMedia_id("media_id");

        wxMassOpenIdNewsMassage.setMpnews(wxMassOpenIdMediaId);
        wxMassOpenIdNewsMassage.setMsgtype("msg_type");
        wxMassOpenIdNewsMassage.setSend_ignore_reprint(0);
        wxMassOpenIdNewsMassage.setTouser(openIds);

        log.info(wxMassOpenIdNewsMassage.toString());
    }


    @Test
    public void testWxMassOpenIdVideoMessage() {
        List<String> touser = Arrays.asList("123", "456");
        WxMassOpenIdMedia wxMassOpenIdMedia = new WxMassOpenIdMedia();
        wxMassOpenIdMedia.setDescription("desc");
        wxMassOpenIdMedia.setTitle("title");
        wxMassOpenIdMedia.setMedia_id("media_id");

        WxMassOpenIdVideoMessage wxMassOpenIdVideoMessage = new WxMassOpenIdVideoMessage();
        wxMassOpenIdVideoMessage.setMpvideo(wxMassOpenIdMedia);
        wxMassOpenIdVideoMessage.setMsgtype("mpvideo");
        wxMassOpenIdVideoMessage.setTouser(touser);

        log.info(wxMassOpenIdVideoMessage.toString());
    }

    @Test
    public void testWxMassPreviewNewsMessage() {
        WxMassPreviewNewsMessage wxMassPreviewNewsMessage = new WxMassPreviewNewsMessage();
        wxMassPreviewNewsMessage.setMpnews(new WxMassPreviewMediaId("media_id"));
        wxMassPreviewNewsMessage.setMsgtype("msg_type");
        wxMassPreviewNewsMessage.setTouser("toUser");
        log.info(wxMassPreviewNewsMessage.toString());
    }


    @Test
    public void testWxMassPreviewCardMessage() {
        WxMassPreviewCardInfoExt wxMassPreviewCardInfoExt = new WxMassPreviewCardInfoExt();
        wxMassPreviewCardInfoExt.setCode("code");
        wxMassPreviewCardInfoExt.setOpenid("openId");
        wxMassPreviewCardInfoExt.setSignature("signature");
        wxMassPreviewCardInfoExt.setTimestamp(System.currentTimeMillis() / 1000);

        WxMassPreviewCardInfo wxMassPreviewCardInfo = new WxMassPreviewCardInfo();
        wxMassPreviewCardInfo.setCard_id("card_id");
        wxMassPreviewCardInfo.setCard_ext(wxMassPreviewCardInfoExt);

        WxMassPreviewCardMessage wxMassPreviewCardMessage = new WxMassPreviewCardMessage();
        wxMassPreviewCardMessage.setMsgtype("wxcard");
        wxMassPreviewCardMessage.setWxcard(wxMassPreviewCardInfo);
        wxMassPreviewCardMessage.setTouser("toUser");
        //wxMassPreviewCardMessage.setTowxname("toWxname");

        log.info(wxMassPreviewCardMessage.toString());


    }

    @Test
    public void testXml2WxTemplateEventMessage() {
        String str = "<message>" +
                "<ToUserName><![CDATA[gh_7f083739789a]]></ToUserName>" +
                "<FromUserName><![CDATA[oia2TjuEGTNoeX76QEjQNrcURxG8]]></FromUserName>" +
                "<CreateTime>1395658920</CreateTime>" +
                "<MsgType><![CDATA[event]]></MsgType>" +
                "<Event><![CDATA[TEMPLATESENDJOBFINISH]]></Event>" +
                "<MsgID>200163836</MsgID>" +
                "<Status><![CDATA[success]]></Status>" +
                "</message>";
        log.info(str);
        WxTemplateEventMessage wxTemplateEventMessage = XStreamHandler.toBean(str, WxTemplateEventMessage.class);
        log.info(wxTemplateEventMessage.toJsonString());
        String msg = XStreamHandler.toXml(wxTemplateEventMessage);
        log.info(msg);
        log.info(String.valueOf(str.equals(msg)));
    }

    @Test
    public void testWxTemplateSubacribe() {
        WxTemplateContentSubscribe wxTemplateContentSubacribe = new WxTemplateContentSubscribe(new WxTemplateDataItemSend("你好", "#777777"));
        WxTemplateMiniprogramSend wxTemplateMiniprogramSend = new WxTemplateMiniprogramSend("test", "test");
        WxTemplateSubscribe wxTemplateSubacribe = new WxTemplateSubscribe();
        wxTemplateSubacribe.setData(wxTemplateContentSubacribe);
        wxTemplateSubacribe.setMiniprogram(wxTemplateMiniprogramSend);
        wxTemplateSubacribe.setScene("sence");
        wxTemplateSubacribe.setTemplate_id("template_id");
        wxTemplateSubacribe.setTitle("title");
        wxTemplateSubacribe.setTouser("toUser");
        wxTemplateSubacribe.setUrl("http://www.baidu.com");

        log.info(wxTemplateSubacribe.toJsonString());
    }
}
