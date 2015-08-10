package com.smart.app.framework.ui.control.dialog;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.*;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.smart.app.framework.R;
import com.smart.app.framework.utils.SmartUI;
import org.json.JSONArray;
import org.json.JSONObject;

public class SmartDialogUtil {

    private static ProgressDialog progressDialog;


    public static void showToast(Context context, String msg) {
        showToast(context, msg, Toast.LENGTH_LONG);
    }

    protected static void showToast(Context context, String msg, int length) {
        Toast toast = Toast.makeText(context, msg, length);
        toast.show();
    }

    public static void showLoading(Context context) {
        showLoading(context, "努力加载中...");
    }

    public static void showLoading(Context context, String message) {
        // 隐藏键盘
        //((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage(message);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    public static void closeLoading() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public static void showDialog(final Context context,
                                     final WebView webView,
                                     String title,
                                     String content,
                                     String description,
                                     JSONObject options,
                                     JSONArray btnList){

        final Dialog dialog = new Dialog(context, R.style.smart_dialog_style);
        LinearLayout dialogLayout = new LinearLayout(context);
        dialogLayout.setOrientation(LinearLayout.VERTICAL);

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View dialogView = layoutInflater.inflate(R.layout.smart_common_dialog, null);

        // 设置标题-title
        if (!TextUtils.isEmpty(title)) {
            LinearLayout titleLiner = (LinearLayout) dialogView.findViewById(R.id.liner_title);
            titleLiner.setVisibility(View.VISIBLE);
            TextView titleTv = (TextView) dialogView.findViewById(R.id.title);
            titleTv.setText(Html.fromHtml(title));
        }

        // 设置内容-content
        TextView txtContent = (TextView) dialogView.findViewById(R.id.content);
        txtContent.setText(Html.fromHtml(content));

        // 设置内容-description
        if (!TextUtils.isEmpty(description)) {
            TextView txtDescription = (TextView) dialogView.findViewById(R.id.description);
            txtDescription.setText(Html.fromHtml(description));
        }

        dialogLayout.addView(dialogView);

        // 动态绘制按钮
        LinearLayout btnLayout = new LinearLayout(context);
        btnLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btnLayout.setOrientation(LinearLayout.HORIZONTAL);
        btnLayout.setGravity(Gravity.CENTER);

        int width = options == null ? 300 : options.optInt("width", 300);
        int btnCount = btnList.length();
        int btnWidth = (SmartUI.dip2px(context, width) - 40) / btnCount;
        ViewGroup.LayoutParams btnLayoutParams = new ViewGroup.LayoutParams(btnWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < btnCount; i++) {
            final JSONObject jsonBtn = btnList.optJSONObject(i);
            Button btn = new Button(context);
            btn.setLayoutParams(btnLayoutParams);
            btn.setText(jsonBtn.optString("name"));
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    webView.loadUrl("javascript:" + jsonBtn.optString("script"));
                    dialog.dismiss();
                }
            });
            btnLayout.addView(btn);
        }
        dialogLayout.addView(btnLayout);

        dialog.setContentView(dialogLayout, new ViewGroup.LayoutParams(SmartUI.dip2px(context, width),
                LinearLayout.LayoutParams.MATCH_PARENT));
        dialog.show();
    }
}
