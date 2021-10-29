package wx.utils;

import com.shui.wx.utils.map.ReflectMap;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class ReflectMapTest {

    @org.junit.jupiter.api.Test
    public void testMap() throws Exception {
        Test test = new Test("liuyazhuang");
        log.info(test.toJsonString());
        Map<String, Object> map = ReflectMap.beanToMap(test);
        log.info(map.toString());

        Test test1 = ReflectMap.mapToT(map, Test.class);
        log.info(test1.toJsonString());
    }
}
