package education.zhiyuan.com.picturebooks.view.commodity.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Spring on 2017/7/24.
 */

public class RedLine extends View {
    private Paint mPaint;
    public RedLine(Context context) {
        super(context);
         mPaint=new Paint();
        mPaint.setColor(Color.RED);
    }

    public RedLine(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RedLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawLine(0,0,);
    }
}
