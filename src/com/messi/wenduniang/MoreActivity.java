package com.messi.wenduniang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;

import com.messi.wenduniang.util.KeyUtil;
import com.messi.wenduniang.util.Settings;

public class MoreActivity extends BaseActivity implements OnClickListener {

	private FrameLayout aboutus_layout,cailing_layout,game_layout,yuedu_layout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_activity);
		init();
	}

	private void init() {
		getSupportActionBar().setTitle(getResources().getString(R.string.action_more));
		cailing_layout = (FrameLayout) findViewById(R.id.cailing_layout);
		game_layout = (FrameLayout) findViewById(R.id.game_layout);
		yuedu_layout = (FrameLayout) findViewById(R.id.yuedu_layout);
		aboutus_layout = (FrameLayout) findViewById(R.id.aboutus_layout);
		cailing_layout.setOnClickListener(this);
		game_layout.setOnClickListener(this);
		yuedu_layout.setOnClickListener(this);
		aboutus_layout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cailing_layout:
			toCailingActivity();
			break;
		case R.id.game_layout:
			toGameCenterActivity();
			break;
		case R.id.yuedu_layout:
			toYueduActivity();
			break;
		case R.id.aboutus_layout:
			toActivity(AboutActivity.class, null);
			break;
		default:
			break;
		}
	}
	
	private void toCailingActivity(){
		Intent intent = new Intent(this,WebViewActivity.class);
		intent.putExtra(KeyUtil.URL, Settings.CaiLingUrl);
		intent.putExtra(KeyUtil.ActionbarTitle, this.getResources().getString(R.string.title_cailing));
		this.startActivity(intent);
	}
	
	private void toGameCenterActivity(){
		Intent intent = new Intent(this,WebViewActivity.class);
		intent.putExtra(KeyUtil.URL, Settings.GameUrl);
		intent.putExtra(KeyUtil.ActionbarTitle, this.getResources().getString(R.string.leisuer_game));
		this.startActivity(intent);
	}
	
	private void toYueduActivity(){
		Intent intent = new Intent(this,WebViewActivity.class);
		intent.putExtra(KeyUtil.URL, Settings.YueduUrl);
		intent.putExtra(KeyUtil.ActionbarTitle, this.getResources().getString(R.string.title_reading));
		intent.putExtra(KeyUtil.IsReedPullDownRefresh, false);
		this.startActivity(intent);
	}
	
}
