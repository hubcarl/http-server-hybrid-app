package com.smart.app.framework.ui.control.dialog;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import com.smart.app.framework.R;


/**
 * Created by sky on 2015/7/11.
 */
public class SmartMenuPopupWindow extends PopupWindow implements OnClickListener{

    private Context context;
    private View mMenuView;

    public SmartMenuPopupWindow(Activity context, OnClickListener itemsOnClick) {
        super(context);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.smart_header_pop_menu, null);

        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        //设置按钮监听
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(2*w/5);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.smart_header_menu_style);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout2).getTop();
                int y=(int) event.getY();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });

        mMenuView.findViewById(R.id.liner_qrcode).setOnClickListener(SmartMenuPopupWindow.this);
        mMenuView.findViewById(R.id.liner_share).setOnClickListener(SmartMenuPopupWindow.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.liner_qrcode:
                SmartMenuPopupWindow.this.dismiss();
                break;
            case R.id.liner_share:
                // 启动分享发送到属性
                Intent intent = new Intent(Intent.ACTION_SEND);
                // 分享发送到数据类型
                intent.setType("text/plain");
                // 分享的主题
                intent.putExtra(Intent.EXTRA_SUBJECT, "Smart Hybrid SmartApp Framework");
                // 分享的内容
                intent.putExtra(Intent.EXTRA_TEXT, "Smart Hybrid SmartApp Framework");
                // 允许intent启动新的activity
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //目标应用选择对话框的标题
                context.startActivity(Intent.createChooser(intent, "Smart Hybrid SmartApp Framework"));
                SmartMenuPopupWindow.this.dismiss();
                break;
        }
    }
}
