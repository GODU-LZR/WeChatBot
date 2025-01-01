package com.example.test.demos.utils.jsonUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Gson工具类，用于处理JSON相关操作
 */
public final class GsonUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(GsonUtil.class);

    /**
     * 私有构造函数，防止实例化
     */
    private GsonUtil() {
        throw new AssertionError("No GsonUtil instances for you!");
    }

    /**
     * 从JSON字符串中提取from.payload.name字段的值
     *
     * @param jsonString JSON字符串
     * @return name字段的值，如果提取失败则返回null
     */
    public static String extractFromPayloadName(String jsonString) {
        try {
            if (jsonString == null || jsonString.trim().isEmpty()) {
                logger.warn("Input JSON string is null or empty");
                return null;
            }
            // 添加调试日志
            logger.debug("Input JSON string bytes: " + Arrays.toString(jsonString.getBytes("UTF-8")));
            logger.debug("Input JSON string length: " + jsonString.length());

            // 确保UTF-8编码
            byte[] bytes = jsonString.getBytes(StandardCharsets.UTF_8);
            String utf8JsonString = new String(bytes, StandardCharsets.UTF_8);

            JsonElement jsonElement = JsonParser.parseString(utf8JsonString);


            JsonObject jsonObject = jsonElement.getAsJsonObject();
            // 添加解析后的调试日志
            logger.debug("Parsed JSON: " + jsonElement.toString());

            // 获取from对象
            JsonObject fromObject = jsonObject.getAsJsonObject("from");
            if (fromObject == null) {
                logger.warn("'from' object not found in JSON");
                return null;
            }
            
            // 获取payload对象
            JsonObject payloadObject = fromObject.getAsJsonObject("payload");
            if (payloadObject == null) {
                logger.warn("'payload' object not found in 'from' object");
                return null;
            }
            
            // 获取name值
            JsonElement nameElement = payloadObject.get("name");
            if (nameElement == null || nameElement.isJsonNull()) {
                logger.warn("'name' field not found in payload or is null");
                return null;
            }
            
            return nameElement.getAsString();
            
        } catch (Exception e) {
            logger.error("Error extracting name from JSON: " + e.getMessage(), e);
            return null;
        }
    }

    /**
     * 通用方法：从JsonObject中安全地获取字符串值
     *
     * @param jsonObject JSON对象
     * @param key 要获取的键
     * @return 字符串值，如果不存在或出错则返回null
     */
    public static String getStringValue(JsonObject jsonObject, String key) {
        try {
            if (jsonObject == null || !jsonObject.has(key)) {
                return null;
            }
            JsonElement element = jsonObject.get(key);
            return element.isJsonNull() ? null : element.getAsString();
        } catch (Exception e) {
            logger.error("Error getting string value for key '" + key + "': " + e.getMessage(), e);
            return null;
        }
    }

    /**
     * 验证JSON字符串是否有效
     *
     * @param jsonString 要验证的JSON字符串
     * @return 如果是有效的JSON返回true，否则返回false
     */
    public static boolean isValidJson(String jsonString) {
        try {
            JsonParser.parseString(jsonString);
            return true;
        } catch (Exception e) {
            logger.debug("Invalid JSON string: " + e.getMessage());
            return false;
        }
    }

    /**
     * 从JsonObject中安全地获取嵌套的JsonObject
     *
     * @param jsonObject JSON对象
     * @param key 要获取的键
     * @return JsonObject，如果不存在或出错则返回null
     */
    public static JsonObject getNestedObject(JsonObject jsonObject, String key) {
        try {
            if (jsonObject == null || !jsonObject.has(key)) {
                return null;
            }
            JsonElement element = jsonObject.get(key);
            return element.isJsonNull() ? null : element.getAsJsonObject();
        } catch (Exception e) {
            logger.error("Error getting nested object for key '" + key + "': " + e.getMessage(), e);
            return null;
        }
    }
}
