package education.zhiyuan.com.picturebooks.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Spring on 2017/8/3.
 * 输入金额
 */

public class MoneyUtils {
    /**
     * 是否为整数
     */
    public boolean isZs(String value) {
        Pattern p = Pattern.compile("[1-9][0-9]*");
        Matcher m = p.matcher(value);
        return m.matches();
    }
    /**
     * 输入金额是否规范
     * */

    public boolean isNumber(String value) {
        return isZs(value) || isDouble(value);
    }

    /**
     * 判断字符串是否是浮点数
     */
    public boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return value.contains(".") && limitPoint(value);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean limitPoint(String value) {  //0.001
        return !value.contains(".00");
    }
}
