package com.shui.wx.utils.xml.handler;

import com.shui.wx.utils.xml.XStreamInitializer;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.CompactWriter;
import com.thoughtworks.xstream.security.AnyTypePermission;
import org.apache.commons.lang.StringEscapeUtils;

import java.io.StringWriter;

/**
 * XML处理器类
 */
public class XStreamHandler {

    public static String toXml(Object obj) {
        XStream xstream = XStreamInitializer.getInstance();
        // 识别obj类中的注解
        xstream.processAnnotations(obj.getClass());
        // 以格式化的方式输出XML
        xstream.aliasSystemAttribute(null, "class");
        xstream.addPermission(AnyTypePermission.ANY);
        xstream.ignoreUnknownElements();

        StringWriter writer = new StringWriter();
        xstream.marshal(obj, new CompactWriter(writer));
        String xml = writer.toString();
        return StringEscapeUtils.unescapeXml(xml);
    }

    public static <T> T toBean(String xmlStr, Class<T> cls) {
        XStream xstream = XStreamInitializer.getInstance();
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(new Class[]{cls});
        xstream.addPermission(AnyTypePermission.ANY);
        xstream.ignoreUnknownElements();
        xstream.processAnnotations(cls);
        @SuppressWarnings("unchecked")
        T t = (T) xstream.fromXML(xmlStr);
        return t;
    }
}
