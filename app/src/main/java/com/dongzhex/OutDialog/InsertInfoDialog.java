package com.dongzhex.OutDialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.dongzhex.someactivities.infosystem.R;

/**
 * Created by ASUS on 2018/5/14.
 */

public class InsertInfoDialog extends Dialog {
    Activity context;
    private Button btn_save;
    public EditText text_title;
    public EditText text_content;
    private String buttonString;
    private View.OnClickListener mClickListener;
    public void setListener(View.OnClickListener listener){
        mClickListener = listener;
    }
    public InsertInfoDialog(Activity context,String s) {
        super(context);
        this.context = context;
        buttonString = s;
    }

    public InsertInfoDialog(Activity context, int theme,String s) {
        super(context, theme);
        this.context = context;

        buttonString = s;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 指定布局
        this.setContentView(R.layout.infodialog);

        text_title = (EditText) findViewById(R.id.text_title);

        text_content = (EditText) findViewById(R.id.text_content);

        /*
         * 获取圣诞框的窗口对象及参数对象以修改对话框的布局设置, 可以直接调用getWindow(),表示获得这个Activity的Window
         * 对象,这样这可以以同样的方式改变这个Activity的属性.
         */
        Window dialogWindow = this.getWindow();

        WindowManager m = context.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        // p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.8
        dialogWindow.setAttributes(p);

        // 根据id在布局中找到控件对象
        btn_save = (Button) findViewById(R.id.btn_save_pop);

        // 为按钮绑定点击事件监听器
        btn_save.setOnClickListener(mClickListener);
        btn_save.setText(buttonString);
        this.setCancelable(true);
    }
}
