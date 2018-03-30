package education.zhiyuan.com.picturebooks.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.lang.reflect.Field;

import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.view.commodity.custom.QNumberPicker;

/**
 * Created by Spring on 2017/6/29.
 * num选择
 */

public class ChooseDateUtil implements View.OnClickListener, NumberPicker.OnValueChangeListener {

    Context context;
    BottomSheetDialog dialog;
    ChooseDateInterface dateInterface;
    QNumberPicker npYear, npMonth, npDay;
    TextView tvCancel, tvSure;
    int[] newDateArray = new int[3];

    public void createDialog(Context context, String[] oldDateArray, ChooseDateInterface dateInterface) {
        this.context = context;
        this.dateInterface = dateInterface;
        newDateArray[0] = Integer.parseInt(oldDateArray[0]);
        newDateArray[1] = Integer.parseInt(oldDateArray[1]);
        newDateArray[2] = Integer.parseInt(oldDateArray[2]);
        dialog = new BottomSheetDialog(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_choose_date, null);
        dialog.setContentView(dialogView);
        dialog.show();

        //初始化控件
        tvCancel = dialogView.findViewById(R.id.tvCancel);
        tvSure = dialogView.findViewById(R.id.tvSure);
        tvCancel.setOnClickListener(this);
        tvSure.setOnClickListener(this);
        npYear = dialogView.findViewById(R.id.npYear);
        npMonth = dialogView.findViewById(R.id.npMonth);
        npDay = dialogView.findViewById(R.id.npDay);
        //设置选择器最小值、最大值
        npYear.setMinValue(1900);
        npYear.setMaxValue(2100);
        npMonth.setMinValue(1);
        npMonth.setMaxValue(12);

        npDay.setMinValue(1);
        npDay.setMaxValue(31);
        //设置选择器初始值
        npYear.setValue(newDateArray[0]);
        npMonth.setValue(newDateArray[1]);
        npDay.setValue(newDateArray[2]);
        //设置监听
        npYear.setOnValueChangedListener(this);
        npMonth.setOnValueChangedListener(this);
        npDay.setOnValueChangedListener(this);
        //去除分割线
        setNumberPickerDividerColor(npYear);
        setNumberPickerDividerColor(npMonth);
        setNumberPickerDividerColor(npDay);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCancel:
                dialog.dismiss();
                break;
            case R.id.tvSure:
                //TODO 如果手动输入日期，点击确定后，时间不会改变，解决：去除焦点
                npDay.clearFocus();
                npMonth.clearFocus();
                npYear.clearFocus();
                dialog.dismiss();
                dateInterface.sure(newDateArray);
                break;
        }
    }

    //选择器选择值监听
    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        switch (picker.getId()) {
            case R.id.npYear:
                newDateArray[0] = newVal;
                npDay.setMaxValue(DateUtil.getNumberOfDays(newDateArray[0], newDateArray[1]));
                break;
            case R.id.npMonth:
                newDateArray[1] = newVal;
                npDay.setMaxValue(DateUtil.getNumberOfDays(newDateArray[0], newDateArray[1]));
                break;
            case R.id.npDay:
                newDateArray[2] = newVal;
                break;
        }
    }

    /**
     * 设置分割线颜色
     */
    private void setNumberPickerDividerColor(NumberPicker numberPicker) {
        NumberPicker picker = numberPicker;
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    //设置分割线的颜色值
                    pf.set(picker, new ColorDrawable(context.getResources().getColor(R.color.graywhite)));// pf.set(picker, new Div)
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    /**
     * 设置字体颜色
     */
    public static boolean setNumberPickerTextColor(NumberPicker numberPicker, int color) {
        boolean result = false;
        final int count = numberPicker.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = numberPicker.getChildAt(i);
            if (child instanceof EditText) {
                try {
                    Field selectorWheelPaintField = numberPicker.getClass()
                            .getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint) selectorWheelPaintField.get(numberPicker)).setColor(color);
                    ((EditText) child).setTextColor(color);
                    numberPicker.invalidate();
                    result = true;
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public interface ChooseDateInterface {
        void sure(int[] newDateArray);
    }
}
