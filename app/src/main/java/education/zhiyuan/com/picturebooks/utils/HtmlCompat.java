package education.zhiyuan.com.picturebooks.utils;

import android.text.Html;
import android.text.Spanned;

/**
 * Html compatible utl
 * Created by syy on 2016/10/28.
 */
public class HtmlCompat {

  public static Spanned fromHtml(String s) {
    if (s == null) {
      if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        return Html.fromHtml("", Html.FROM_HTML_MODE_LEGACY);
      } else {
        return Html.fromHtml("");
      }
    }
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
      return Html.fromHtml(s, Html.FROM_HTML_MODE_LEGACY);
    } else {
      return Html.fromHtml(s);
    }
  }

}
