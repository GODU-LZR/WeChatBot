package com.example.test.util;

import com.example.test.demos.utils.jsonUtils.GsonUtil;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GsonUtilsTest {

    @Test
    public void testExtractFromPayloadName() {
        // 准备测试数据
        String jsonString = "{\"room\":{},\"to\":{\"_events\":{},\"_eventsCount\":0,\"id\":\"@23e03e72996aa004ab59ab5dce98ab9bc3f4dbca775e86e48b89736e71d394c6\",\"payload\":{\"alias\":\"\",\"avatar\":\"http://localhost:3001/resouces?media=%2Fcgi-bin%2Fmmwebwx-bin%2Fwebwxgeticon%3Fseq%3D548650238%26username%3D%4023e03e72996aa004ab59ab5dce98ab9bc3f4dbca775e86e48b89736e71d394c6%26skey%3D%40crypt_270ea856_4453c68807a9ee55ee08db88fd0e77e0\",\"friend\":false,\"gender\":2,\"id\":\"@23e03e72996aa004ab59ab5dce98ab9bc3f4dbca775e86e48b89736e71d394c6\",\"name\":\"bot\",\"phone\":[],\"signature\":\"OpenAI的搬运工\",\"star\":false,\"type\":1}},\"from\":{\"_events\":{},\"_eventsCount\":0,\"id\":\"@813dd1f318337ffcca2fab96111db9803124fd4f227584787944767311985163\",\"payload\":{\"address\":\"\",\"alias\":\"\",\"avatar\":\"http://localhost:3001/resouces?media=%2Fcgi-bin%2Fmmwebwx-bin%2Fwebwxgeticon%3Fseq%3D811643537%26username%3D%40813dd1f318337ffcca2fab96111db9803124fd4f227584787944767311985163%26skey%3D\",\"city\":\"珠海\",\"friend\":true,\"gender\":1,\"id\":\"@813dd1f318337ffcca2fab96111db9803124fd4f227584787944767311985163\",\"name\":\"徐霞客\",\"phone\":[],\"province\":\"广东\",\"signature\":\"有段路要自己走\",\"star\":false,\"weixin\":\"\",\"type\":1}}}";

        // 调用方法并获取结果
        String result = GsonUtil.extractFromPayloadName(jsonString);

        // 打印结果到控制台
        System.out.println("Extracted name: " + result);

        // 验证结果
        assertEquals("徐霞客", result);
    }

    @Test
    public void testExtractFromPayloadNameWithInvalidJson() {
        // 测试无效的JSON
        String result = GsonUtil.extractFromPayloadName("invalid json");
        assertNull(result);
    }

    @Test
    public void testExtractFromPayloadNameWithNullInput() {
        // 测试null输入
        String result = GsonUtil.extractFromPayloadName(null);
        assertNull(result);
    }

    @Test
    public void testExtractFromPayloadNameWithEmptyJson() {
        // 测试空JSON
        String result = GsonUtil.extractFromPayloadName("{}");
        assertNull(result);
    }
}
