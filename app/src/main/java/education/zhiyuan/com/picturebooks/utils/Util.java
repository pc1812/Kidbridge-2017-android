package education.zhiyuan.com.picturebooks.utils;


import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;


/**
 * Created by Spring on 2017/8/1.
 * org.apache.commons.lang3.StringUtils;
 * org.apache.commons.codec.digest.DigestUtils
 */

public class Util {

    public static final String DEFAULT_SALT = "Picture_B00k";
    public static String sign(SortedMap<String, Object> sortedMap, String salt) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Object> map : sortedMap.entrySet()) {
            if (map.getValue() == null || StringUtils.isBlank(map.getValue().toString())) {
                continue;
            }
            stringBuilder.append(String.format("%s=%s&", map.getKey(), ((map.getValue() instanceof Number || map.getValue() instanceof String) ? map.getValue() : new Gson().toJson(map.getValue()))));
            System.out.println(map.getValue() instanceof Number);
        }
        stringBuilder.append(String.format("%s=%s", "salt", salt));
        String s = Md5Encryption.md5(stringBuilder.toString()).toUpperCase();
        return s;
    }

    public static String sign(Map<String, Object> param) {
        return sign(new TreeMap<>(param), DEFAULT_SALT);
    }
}
