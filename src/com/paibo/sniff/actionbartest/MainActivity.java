package com.paibo.sniff.actionbartest;

import java.util.ArrayList;

import com.paibo.sniff.R;



import android.os.Bundle;
import android.os.Handler;
import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ImageView foundDevice, foundDevice2, foundWifi, foundWifi2;
	private RelativeLayout mCommit, mQuit, mRadar;
	private RippleBackground rippleBackground;
	private ImageView mStartCollect, mRadarCenter, mRaderCenterPoint;
	private static Handler handler;
	private LinearLayout mAreaStartCollect, mAreaInfoArea, mItemWifi, mItemMac;
	private ImageView mLoading1, mLoading2, mItemWifiIcon, mItemMacIcon;
	private boolean isPlay = false; // false:ֹͣ true�����ڲ���
	private Animation anim, anim2, anim3;
	// ���뵭����������ʱ��
	private int mAnimationDuration;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initView();
		initHandler();
		initData();
        initListener();
	}

	private void initView() {
		rippleBackground = (RippleBackground) findViewById(R.id.content);
		foundDevice = (ImageView) findViewById(R.id.foundDevice);
        foundDevice2 = (ImageView) findViewById(R.id.foundDevice2);
        foundWifi = (ImageView) findViewById(R.id.foundWifi);
        foundWifi2 = (ImageView) findViewById(R.id.foundWifi2);
        mStartCollect = (ImageView) findViewById(R.id.btn_start_collect);
        mCommit = (RelativeLayout) findViewById(R.id.btn_commit);
        mQuit = (RelativeLayout) findViewById(R.id.btn_quit);
        mRadarCenter = (ImageView) findViewById(R.id.centerImage);
        mRaderCenterPoint = (ImageView) findViewById(R.id.centerImage2);
        mAreaStartCollect = (LinearLayout) findViewById(R.id.start_collect_area);
        mAreaInfoArea = (LinearLayout) findViewById(R.id.collect_info_area);
        mItemWifi = (LinearLayout) findViewById(R.id.item_wifi_num);
        mItemMac = (LinearLayout) findViewById(R.id.item_mac_num);
        mLoading1 = (ImageView) findViewById(R.id.loading1);
        mLoading2 = (ImageView) findViewById(R.id.loading2);
        mRadar = (RelativeLayout) findViewById(R.id.tmp);
        mItemWifiIcon = (ImageView) findViewById(R.id.item_wifi_icon);
        mItemMacIcon = (ImageView) findViewById(R.id.item_mac_icon);
	}
	
	private void initData() {
		// Retrieve and cache the system's default "short" animation time.
		mAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);
	}
	
	private void initHandler() {
		handler = new Handler();
	}
	
	private void initListener() {
		// ��ʼ�ɼ�
		mStartCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//            	mAreaStartCollect.setVisibility(View.GONE);
//            	mAreaInfoArea.setVisibility(View.VISIBLE);
            	crossfade();
            	mItemWifiIcon.setVisibility(View.GONE);
            	mLoading1.setVisibility(View.VISIBLE);
            	mItemMacIcon.setVisibility(View.GONE);
            	mLoading2.setVisibility(View.VISIBLE);
            	
            	// ��ת�״��м��ͼƬ
//            	ObjectAnimator oa = ObjectAnimator.ofFloat(mRaderCenterPoint, "rotation", 0, 360);
//        		oa.setDuration(2000);
//        		oa.setRepeatCount(-1);
//        		oa.setRepeatMode(ValueAnimator.RESTART);
//        		oa.start();
//            	anim = AnimatorInflater.loadAnimator(MainActivity.this, R.anim.rotate_animation);
//            	AnimationUtils.loadAnimation(
//            			MainActivity.this, R.anim.rotate_animation);  
//            	anim.setTarget(mRadarCenter);
//            	anim.start();
            	
            	anim2 = AnimationUtils.loadAnimation(
            			MainActivity.this, R.anim.rotate_animation2);
            	mRaderCenterPoint.startAnimation(anim2);
//            	
            	// ��תloadingͼƬ
            	anim3 = AnimationUtils.loadAnimation(
            				MainActivity.this, R.anim.rotate_animation);
            	mLoading1.startAnimation(anim3);
            	mLoading2.startAnimation(anim3);
            	
                rippleBackground.startRippleAnimation();
                isPlay = true;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        foundDevice();
                    }
                },3000);
            }
        });
		
		// �ύ
		mCommit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(MainActivity.this, "commit", Toast.LENGTH_SHORT).show();
			}
		});
		
		// �˳�
		mQuit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(MainActivity.this, "quit", Toast.LENGTH_SHORT).show();
			}
		});
		
		// ���item wifi
		mItemWifi.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, WifiInfoActivity.class);
				startActivity(intent);
			}
		});
		
		// ���item mac
		mItemMac.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, MacInfoActivity.class);
				startActivity(intent);
			}
		});
		
		// ����״����ģ���ʼ�ɼ���ֹͣ�ɼ�
		mRadar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// �ж��Ƿ��ڲ���
				if (isPlay) { // �����ڲ���
					rippleBackground.stopRippleAnimation();
					isPlay = false;
					
					// ������Ļ�ϵ��ֻ���WiFiͼ��
					foundDevice.setVisibility(View.GONE);
			        foundDevice2.setVisibility(View.GONE);
			        foundWifi.setVisibility(View.GONE);
			        foundWifi2.setVisibility(View.GONE);
			        
			        mItemWifiIcon.setVisibility(View.VISIBLE);
			        mLoading1.clearAnimation();
	            	mLoading1.setVisibility(View.GONE);
	            	mItemMacIcon.setVisibility(View.VISIBLE);
	            	mLoading2.clearAnimation();
	            	mLoading2.setVisibility(View.GONE);
			        
			        // ֹͣ��ת���״�����ͼƬ
//			        anim.cancel();
//			        anim2.cancel();
			        mRaderCenterPoint.clearAnimation();
				} else { // ���Ѿ�ֹͣ
					
					rippleBackground.startRippleAnimation();
	            	mRaderCenterPoint.startAnimation(anim2);
	            	
	            	mItemWifiIcon.setVisibility(View.GONE);
	            	mLoading1.setVisibility(View.VISIBLE);
	            	mLoading1.startAnimation(anim3);
	            	mItemMacIcon.setVisibility(View.GONE);
	            	mLoading2.setVisibility(View.VISIBLE);
	            	mLoading2.startAnimation(anim3);
					
					isPlay = true;
					handler.postDelayed(new Runnable() {
	                    @Override
	                    public void run() {
	                        foundDevice();
	                    }
	                },3000);
				}
			}
		});
	}
	
	private void foundDevice(){
		
		// �ж��״ﶯ���Ƿ����ڲ���,���ڲ��ţ����˳�
		if (!isPlay) return; 
		
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(1000);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        ArrayList<Animator> animatorList=new ArrayList<Animator>();
        
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(foundDevice, "ScaleX", 0f, 1.2f, 1f);
        animatorList.add(scaleXAnimator);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(foundDevice, "ScaleY", 0f, 1.2f, 1f);
        animatorList.add(scaleYAnimator);
        
        ObjectAnimator scaleXAnimator2 = ObjectAnimator.ofFloat(foundDevice2, "ScaleX", 0f, 1.2f, 1f);
        animatorList.add(scaleXAnimator2);
        ObjectAnimator scaleYAnimator2 = ObjectAnimator.ofFloat(foundDevice2, "ScaleY", 0f, 1.2f, 1f);
        animatorList.add(scaleYAnimator2);
        
        ObjectAnimator scaleXWifi = ObjectAnimator.ofFloat(foundWifi, "ScaleX", 0f, 1.2f, 1f);
        animatorList.add(scaleXWifi);
        ObjectAnimator scaleYWifi = ObjectAnimator.ofFloat(foundWifi, "ScaleY", 0f, 1.2f, 1f);
        animatorList.add(scaleYWifi);
        
        
        ObjectAnimator scaleXWifi2 = ObjectAnimator.ofFloat(foundWifi2, "ScaleX", 0f, 1.2f, 1f);
        animatorList.add(scaleXWifi2);
        ObjectAnimator scaleYWifi2 = ObjectAnimator.ofFloat(foundWifi2, "ScaleY", 0f, 1.2f, 1f);
        animatorList.add(scaleYWifi2);
        
        animatorSet.playTogether(animatorList);
        foundDevice.setVisibility(View.VISIBLE);
        foundDevice2.setVisibility(View.VISIBLE);
        foundWifi.setVisibility(View.VISIBLE);
        foundWifi2.setVisibility(View.VISIBLE);
        animatorSet.start();
    }
	

	/**
	 * ���浭�뵭������
	 */
	private void crossfade() {
		
		mAreaStartCollect.animate()
					     .alpha(0.5f)
						 .setDuration(mAnimationDuration)
						 .setListener(new AnimatorListenerAdapter() {
							 @Override
							public void onAnimationEnd(Animator animation) {
								 mAreaStartCollect.setVisibility(View.GONE);
								 
								 mAreaInfoArea.setAlpha(0f);
								 mAreaInfoArea.setVisibility(View.VISIBLE);
								 mAreaInfoArea.animate()
											  .alpha(1f)
											  .setDuration(mAnimationDuration)
											  .setListener(null);
							}
						 });
	}
	
}
