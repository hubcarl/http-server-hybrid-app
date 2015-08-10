package com.smart.app.framework.ui.activity;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.smart.app.framework.R;
import com.smart.app.framework.bridge.SmartWebView;
import com.smart.app.framework.message.IMessageCallback;
import com.smart.app.framework.message.MessageAction;
import com.smart.app.framework.ui.control.dialog.SmartDialogUtil;
import com.smart.app.framework.ui.control.dialog.SmartMenuPopupWindow;
import com.smart.app.framework.utils.SmartUI;
import com.smart.app.vendor.pulldownrefresh.PtrClassicFrameLayout;
import com.smart.app.vendor.pulldownrefresh.PtrDefaultHandler;
import com.smart.app.vendor.pulldownrefresh.PtrFrameLayout;
import com.smart.app.vendor.pulldownrefresh.PtrHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sky on 2015/7/11.
 */
public class SmartBaseActivity extends Activity {

    private LinearLayout layout;
    private PtrClassicFrameLayout mPtrFrame;
    private View header;
    private ImageView leftButton;
    private TextView txtTitle;
    private TextView rightButton;
    private ProgressBar progressBar;
    private SmartWebView webView;
    private ViewGroup.LayoutParams layoutParams;

    protected String title;
    protected String url;
    protected boolean isHideBack = false;
    protected JSONObject params;
    protected JSONObject options;

    protected void init() {
        initParams();
        initLayout();
        initDynamicHeader();
        initProgress();
        initWebView();
        initRefreshView();
        viewLayout();
    }

    protected void initFullScreen() {
        initLayout();
        initUrl();
        initWebView();
        initRefreshView();
        viewLayout();
    }

    protected void init(String title, String url, boolean isHideBack) {
        this.title = title;
        this.url = url;
        this.isHideBack = isHideBack;
        initLayout();
        initDynamicHeader();
        initProgress();
        initWebView();
        initRefreshView();
        viewLayout();
    }

    private void initLayout() {
        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layoutParams = new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    private void initUrl() {
        this.url = getIntent().getStringExtra("url");
    }

    protected void initParams() {
        this.title = getIntent().getStringExtra("title");
        this.url = getIntent().getStringExtra("url");
        this.isHideBack = getIntent().getBooleanExtra("isBack", false);

        String paramsText = getIntent().getStringExtra("params");
        if (!TextUtils.isEmpty(paramsText)) {
            try {
                this.params = new JSONObject(paramsText);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        String optionsText = getIntent().getStringExtra("options");
        if (!TextUtils.isEmpty(optionsText)) {
            try {
                this.options = new JSONObject(optionsText);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    protected void initHeader(View header) {

        txtTitle = (TextView) header.findViewById(R.id.commonTitle);
        txtTitle.setText(title);

        leftButton = (ImageView) header.findViewById(R.id.leftButton);
        leftButton.setVisibility(isHideBack ? View.INVISIBLE : View.VISIBLE);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (options != null && options.optJSONObject("headerRight") != null) {
            rightButton = (TextView) header.findViewById(R.id.rightButton);
            rightButton.setBackgroundResource(R.drawable.smart_icon_white_more);
            rightButton.setVisibility(View.VISIBLE);
            rightButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SmartMenuPopupWindow menuWindow = new SmartMenuPopupWindow(SmartBaseActivity.this, null);
                    // 计算坐标的偏移量
                    int xOffInPixels = menuWindow.getWidth() - rightButton.getWidth() + 10;
                    menuWindow.showAsDropDown(rightButton, -xOffInPixels, 0);
                }
            });
        }
    }


    protected void initDynamicHeader() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        header = layoutInflater.inflate(R.layout.smart_activity_standard_header, null);
        this.initHeader(header);
        layout.addView(header);
    }

    private void initRefreshView() {
        mPtrFrame = new PtrClassicFrameLayout(this);
        mPtrFrame.setLayoutParams(layoutParams);
        //mPtrFrame.setBackgroundColor(getResources().getColor(R.color.yellow));
        // the following are default settings
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(1000);
        // default is false
        mPtrFrame.setPullToRefresh(false);
        // default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, webView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                Log.d(">>>onRefresh", "onRefreshBegin");
                webView.loadUrl(String.format("javascript:SmartNativeJSBridge.broadcastEvent('%s',%s,%s)","pullRefresh","{}","{message:'onRefreshBegin'}"));
            }
        });
        mPtrFrame.setContentView(webView);
        layout.addView(mPtrFrame);
    }

    private void initProgress() {
        if (url.startsWith("http:") || url.startsWith("https:")) {
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            View progressView = layoutInflater.inflate(R.layout.smart_webview_progress, null);
            progressBar = (ProgressBar) progressView.findViewById(R.id.webview_progress_bar);
            layout.addView(progressBar, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, SmartUI.dip2px(this, 4.0f)));
        }
    }

    private void initWebView() {
        webView = new SmartWebView(this, messageCallback, progressBar);
        webView.setLayoutParams(layoutParams);
        webView.loadUrl(url);
    }

    protected SmartWebView createWebView(ProgressBar progressBar){
        return new SmartWebView(this, messageCallback, progressBar);
    }

    protected void viewLayout() {
        setContentView(layout);
    }

    private IMessageCallback messageCallback = new IMessageCallback() {

        @Override
        public void onReceiveMessage(final MessageAction messageAction, final JSONObject json) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    switch (messageAction) {
                        case SET_TITLE:
                            if (txtTitle != null) {
                                txtTitle.setText(json.optString("title"));
                            }
                            break;
                        case SHOW_TOAST:
                            SmartDialogUtil.showToast(SmartBaseActivity.this,  json.optString("message"));
                            break;
                        case SHOW_LOADING:
                            String title = json.optString("title");
                            String content = json.optString("content");
                            String description = json.optString("description");
                            JSONObject option = json.optJSONObject("option");
                            JSONArray btnList = json.optJSONArray("btnList");
                            SmartDialogUtil.showDialog(SmartBaseActivity.this, webView, title, content, description, option, btnList);
                            break;
                        case SHOW_DIALOG:
                            SmartDialogUtil.showLoading(SmartBaseActivity.this, json.optString("message"));
                            break;
                        case CLOSE_LOADING:
                            SmartDialogUtil.closeLoading();
                            break;
                        default:
                            Log.e(">>>MessageAction#", "default:switch action:" + messageAction.toString() + " not implementation!");
                            break;
                    }
                }
            });
        }

    };

    @Override
    protected void onDestroy() {
        webView = null;
        header = null;
        if (layout != null) {
            layout.removeAllViews();
        }
        super.onDestroy();
    }
}