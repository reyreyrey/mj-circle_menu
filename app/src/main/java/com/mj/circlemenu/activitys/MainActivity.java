package com.mj.circlemenu.activitys;

import android.support.v4.view.GravityCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.android.library.base.UIActivity;
import com.android.library.manager.UIManager;
import com.android.library.manager.UserManager;
import com.android.library.utils.ToastUtils;
import com.android.library.utils.activity_manager.ActivityManager;
import com.bumptech.glide.Glide;
import com.mj.circlemenu.R;
import com.mj.circlemenu.databinding.ActivityMain1Binding;
import com.mj.circlemenu.menus.CircleMenuLayout;

/**
 * Created by wiki on 2018/3/5.
 */

public class MainActivity extends UIActivity<ActivityMain1Binding> {
    private String[] mItemTexts = new String[]{"艺术 ", "福利", "鬼故事",
            "漫画", "笑话", "微信精选", "走势", "友圈"};
    private int[] mItemImgs = new int[]{R.drawable.ic_yishu,
            R.drawable.ic_fuli, R.drawable.ic_guigushi,
            R.drawable.ic_manhua, R.drawable.ic_xiaohua,
            R.drawable.ic_weixin, R.drawable.ic_zoushi, R.drawable.ic_quanzi};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main1;
    }

    @Override
    protected void init() {
        tvBack.setVisibility(View.GONE);
        tvTitle.setText(R.string.app_name);
        databinding.idMenulayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);
        databinding.idMenulayout.setOnMenuItemClickListener(new CircleMenuLayout.OnMenuItemClickListener() {
            @Override
            public void itemClick(View view, int pos) {
                switch (pos) {
                    case 0:
                        UIManager.art(context);
                        break;
                    case 1:
                        UIManager.fuli(context);
                        break;
                    case 2:
                        UIManager.guigushi(context);
                        break;
                    case 3:
                        UIManager.manhua(context);
                        break;
                    case 4:
                        UIManager.xiaohua(context);
                        break;
                    case 5:
                        UIManager.wx(context);
                        break;
                    case 6:
                        UIManager.caipiaoHistory(context);
                        break;
                    case 7:
                        UIManager.postList(context);
                        break;
                }
            }

            @Override
            public void itemCenterClick(View view) {
                if (UserManager.isLogin())
                    UIManager.mine(context);
                else
                    UIManager.login(context);
            }
        });
        checkLoginAndShowImage();
    }

    private void checkLoginAndShowImage() {
        if (UserManager.isLogin()) {
            String photo = UserManager.getCurrentUser().getPhoto();
            if (!TextUtils.isEmpty(photo)) {
                Glide.with(context).load(photo).into(databinding.imgHeader);
                return;
            }
        }
        databinding.imgHeader.setImageResource(R.drawable.default_avatar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkLoginAndShowImage();
    }

    private long backTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (backTime == 0) {
                backTime = System.currentTimeMillis();
                ToastUtils.toastWarn(this, getString(R.string.hybrid_exit_app));
                return true;
            }
            if ((System.currentTimeMillis() - backTime) >= 2000) {
                backTime = System.currentTimeMillis();
                ToastUtils.toastWarn(this, getString(R.string.hybrid_exit_app));
                return true;
            }
            ActivityManager.exitApp();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
