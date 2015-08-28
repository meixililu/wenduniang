package com.messi.wenduniang;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gc.materialdesign.views.ButtonFloat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.lerdian.search.SearchManger;
import com.messi.wenduniang.util.JsonParser;
import com.messi.wenduniang.util.LogUtil;
import com.messi.wenduniang.util.Settings;
import com.messi.wenduniang.util.ToastUtil;
import com.messi.wenduniang.util.XFUtil;

public class MainActivity extends BaseActivity implements OnClickListener {

	private EditText input_et;
	private ButtonRectangle submit_btn;
	private ButtonFloat buttonFloat;
	private FrameLayout clear_btn_layout;
	private LinearLayout record_layout;
	private ImageView record_anim_img;
	private SharedPreferences mSharedPreferences;
	
	private SpeechRecognizer recognizer;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}
	
	private void init(){
		SpeechUtility.createUtility(this, SpeechConstant.APPID + "=" +getString(R.string.app_id));
		setTitle(getResources().getString(R.string.app_name));
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		recognizer = SpeechRecognizer.createRecognizer(this, null);
		mSharedPreferences = Settings.getSharedPreferences(this);
		submit_btn = (ButtonRectangle) findViewById(R.id.submit_btn);
		buttonFloat = (ButtonFloat) findViewById(R.id.buttonFloat);
		input_et = (EditText) findViewById(R.id.input_et);
		clear_btn_layout = (FrameLayout) findViewById(R.id.clear_btn_layout);
		record_layout = (LinearLayout) findViewById(R.id.record_layout);
		record_anim_img = (ImageView) findViewById(R.id.record_anim_img);
		
		submit_btn.setOnClickListener(this);
		buttonFloat.setOnClickListener(this);
		clear_btn_layout.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.global, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_more) {
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.submit_btn) {
			toBaiduActivity();
		}else if (v.getId() == R.id.clear_btn_layout) {
			input_et.setText("");
		}else if (v.getId() == R.id.buttonFloat) {
			showIatDialog();
		}
	}
	
	public void showIatDialog() {
		if(!recognizer.isListening()){
			record_layout.setVisibility(View.VISIBLE);
			input_et.setText("");
			buttonFloat.setDrawableIcon(getResources().getDrawable(R.drawable.ic_stop_white_36dp));
			XFUtil.showSpeechRecognizer(this,mSharedPreferences,recognizer,recognizerListener);
		}else{
			buttonFloat.setDrawableIcon(getResources().getDrawable(R.drawable.ic_mic_white_36dp));
			finishRecord();
			recognizer.stopListening();
			loadding();
		}
	}
	
	/**
	 * finish record
	 */
	private void finishRecord(){
		record_layout.setVisibility(View.GONE);
	}
	
	RecognizerListener recognizerListener = new RecognizerListener() {

		@Override
		public void onBeginOfSpeech() {
			LogUtil.DefalutLog("onBeginOfSpeech");
		}

		@Override
		public void onError(SpeechError err) {
			LogUtil.DefalutLog("onError:"+err.getErrorDescription());
			finishRecord();
			finishLoadding();
			ToastUtil.diaplayMesShort(MainActivity.this, err.getErrorDescription());
		}

		@Override
		public void onEndOfSpeech() {
			LogUtil.DefalutLog("onEndOfSpeech");
			loadding();
			finishRecord();
		}

		@Override
		public void onResult(RecognizerResult results, boolean isLast) {
			LogUtil.DefalutLog("onResult");
			String text = JsonParser.parseIatResult(results.getResultString());
			input_et.append(text);
			input_et.setSelection(input_et.length());
			if(isLast) {
				finishRecord();
				finishLoadding();
				toBaiduActivity();
			}
		}

		@Override
		public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
		}

		@Override
		public void onVolumeChanged(int volume, byte[] arg1) {
			if(volume < 4){
				record_anim_img.setBackgroundResource(R.drawable.speak_voice_1);
			}else if(volume < 8){
				record_anim_img.setBackgroundResource(R.drawable.speak_voice_2);
			}else if(volume < 12){
				record_anim_img.setBackgroundResource(R.drawable.speak_voice_3);
			}else if(volume < 16){
				record_anim_img.setBackgroundResource(R.drawable.speak_voice_4);
			}else if(volume < 20){
				record_anim_img.setBackgroundResource(R.drawable.speak_voice_5);
			}else if(volume < 24){
				record_anim_img.setBackgroundResource(R.drawable.speak_voice_6);
			}else if(volume < 31){
				record_anim_img.setBackgroundResource(R.drawable.speak_voice_7);
			}
		}

	};
	
	private void toBaiduActivity(){
		String data = input_et.getText().toString().replaceAll("[\\p{P}]", "");
		if(TextUtils.isEmpty(data)){
			Intent intent = new Intent(this,com.lerdian.search.SearchResult.class);
			startActivity(intent);
		}else{
			ClipboardManager cm = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
			cm.setText(data);//string为你要传入的值
			SearchManger.openDetail(this);
		}
	}
	
	/**
	 * 通过接口回调activity执行进度条显示控制
	 */
	private void loadding(){
		showProgressbar();
	}
	
	/**
	 * 通过接口回调activity执行进度条显示控制
	 */
	private void finishLoadding(){
		hideProgressbar();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if(recognizer != null){
			recognizer.destroy();
			recognizer = null;
		}
	}
}
