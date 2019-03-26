package com.far.autoclick;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

@SuppressLint({"NewApi", "LocalSuppress"})
public class MsgAccessibilityService extends BaseAccessibilityService {
    private final String TAG = "=eagle_access=";
    private Context mContext;
    private boolean blDone = false;
    private boolean k_bl_hot = true;
    private int Ikind = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            //当下发卸载app指令时,needAccessibility赋值为true
            blDone = intent.getBooleanExtra("bl_done", false);
            Ikind = intent.getIntExtra("Ikind", 0);
        }
        return START_STICKY;
    }

    @SuppressLint("NewApi")
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        try {   //防关闭
            if (blDone && event != null) {
                AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
                int eventType = event.getEventType();
                String eventText = event.getText() == null ? "" : event.getText().toString();
                String eventClassName = event.getClassName() == null ? "" : event.getClassName().toString();
                String packageName = event.getPackageName() == null ? "" : event.getPackageName().toString();
                AccessibilityNodeInfo eventSource = event.getSource();
                Log.d(TAG,  "==eventText==" + eventText + "==eventSource==" + eventSource);

                // -----------------------keep-----------------------
                if ("com.gotokeep.keep".equalsIgnoreCase(packageName)) {
                    //===========热门===========
                    List<AccessibilityNodeInfo> recView = nodeInfo.findAccessibilityNodeInfosByViewId("com.gotokeep.keep:id/recycler_view");
                    List<AccessibilityNodeInfo> infos = nodeInfo.findAccessibilityNodeInfosByViewId("com.gotokeep.keep:id/stroke_view");
                    List<AccessibilityNodeInfo> infos1 = nodeInfo.findAccessibilityNodeInfosByText("你可能感兴趣");
//                    Log.d(TAG, "==infos1==" + infos1.);
                    if ((infos1 == null || (infos1 != null && infos1.size() == 0)) && infos != null && Ikind == 1) {
                        if (infos.size() > 4) {
                            for (int i = 0; i < 4; i++) {
                                AccessibilityNodeInfo info = infos.get(i);
                                if (info != null && info.isClickable()) {
                                    info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                }
                                SystemClock.sleep(200);
                            }
                        } else {
                            for (int i = 0; i < infos.size(); i++) {
                                AccessibilityNodeInfo info = infos.get(i);
                                if (info != null && info.isClickable()) {
                                    info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                }
                                SystemClock.sleep(200);
                            }
                        }
                        if (recView != null && recView.size() > 0) {
                            AccessibilityNodeInfo info = recView.get(0);
//                            Log.d(TAG, "==info==" + info.getViewIdResourceName());
                            info.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
                        }
                        SystemClock.sleep(800);
                    }

                    //=============关注==============
                    List<AccessibilityNodeInfo> infos2 = nodeInfo.findAccessibilityNodeInfosByViewId("com.gotokeep.keep:id/item_community_comment_container");
                    if (Ikind == 2 && infos2 != null && infos2.size() > 0) {
                        AccessibilityNodeInfo info2 = infos2.get(0);
                        if (info2 != null && info2.isClickable()) {
                            info2.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            SystemClock.sleep(1000);
                        }
                        List<AccessibilityNodeInfo> infos3 = getTouchRootNodeInfo(getRootInActiveWindow()).findAccessibilityNodeInfosByViewId("com.gotokeep.keep:id/layout_comment");
                        if (infos3 != null && infos3.size() > 0) {
                            AccessibilityNodeInfo info3 = infos3.get(infos3.size() - 1);
                            if (info3 != null) {
                                info3.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                SystemClock.sleep(500);
                                List<AccessibilityNodeInfo> infos4 = nodeInfo.findAccessibilityNodeInfosByViewId("com.gotokeep.keep:id/text_hint");
                                Log.d(TAG, "==infos4==" + infos4);
                                if (infos4 != null && infos4.size() > 0) {
                                    AccessibilityNodeInfo info4 = infos3.get(0);
                                    if (info4 != null) {
                                        info4.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                        SystemClock.sleep(500);
                                    }
                                }
                            }
                        }

                    }


                }


            }
        } catch (Exception e) {
        }
    }

    private AccessibilityNodeInfo getTouchRootNodeInfo(AccessibilityNodeInfo rootInActiveWindow) {
        AccessibilityServiceInfo info = getServiceInfo();
        int oldFlags = info.flags;
        info.flags |= AccessibilityServiceInfo.FLAG_REQUEST_TOUCH_EXPLORATION_MODE;
        this.setServiceInfo(info);
        info.flags = oldFlags;
        this.setServiceInfo(info);
        return rootInActiveWindow;
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.flags = AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS;
        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        setServiceInfo(info);
    }

    @Override
    public void onInterrupt() {
    }
}
