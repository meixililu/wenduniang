package com.messi.wenduniang.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.ClipboardManager;

import com.messi.wenduniang.R;

public class Settings {

	/**baidu translate api**/
	public static String baiduTranslateUrl = "http://openapi.baidu.com/public/2.0/bmt/translate";
	
	/**baidu dictionary api**/
	public static String baiduDictionaryUrl = "http://openapi.baidu.com/public/2.0/translate/dict/simple";
	
	/**jinshan daily sentence api**/
	public static String DailySentenceUrl = "http://open.iciba.com/dsapi/";
	
	public static final String CaiLingUrl = "http://api.openspeech.cn/kyls/NTBhYTEyMTM=";
	
	public static final String YueduUrl = "http://api.openspeech.cn/cmread/NTBhYTEyMTM=";
	
	public static final String HotalUrl = "http://api.openspeech.cn/trip/NTBhYTEyMTM=";
	
	public static final String GameUrl = "http://h5.huosu.com/zhongyinghuyi/";
	
	/**invest list**/
	public static final String InvestListUrl = "http://lilu168.sinaapp.com/list.html";
	
	/**广阅�?**/
	public static final String GuangyuetongUrl = "http://p.contx.cn/v1/access?id=f9136944-bc17-4cb1-9b14-ece9de91b39d&uid=#uid#&ud=#ud#";
	

	public static final String Email = "meixililulu@163.com";
	
	public static final String client_id = "vCV6TTGRTI5QrckdYSKHQIhq";	
	public static String from = "auto";	
	public static String to = "auto";	
	public static String q = "";	
	public static String role = "vimary";	
	public static final int offset = 100;
	
	/**获取配置文件�?
	 * @param context
	 * @return
	 */
	public static SharedPreferences getSharedPreferences(Context context){
		return context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
	}
	
	/**
	 * 保存配置信息
	 * 
	 * @param context
	 * @param value
	 * @param key
	 */
	public static void saveSharedPreferences(SharedPreferences sharedPrefs, String key, String value) {
		LogUtil.DefalutLog("key-value:" + key + "-" + value);
		Editor editor = sharedPrefs.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	/**
	 * 保存配置信息
	 * 
	 * @param context
	 * @param value
	 * @param key
	 */
	public static void saveSharedPreferences(SharedPreferences sharedPrefs, String key, boolean value) {
		LogUtil.DefalutLog("key-value:" + key + "-" + value);
		Editor editor = sharedPrefs.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	
	/**
	 * 保存配置信息
	 * 
	 * @param context
	 * @param value
	 * @param key
	 */
	public static void saveSharedPreferences(SharedPreferences sharedPrefs, String key, long value) {
		LogUtil.DefalutLog("key-value:" + key + "-" + value);
		Editor editor = sharedPrefs.edit();
		editor.putLong(key, value);
		editor.commit();
	}
	
	/**
	 * 保存配置信息
	 * 
	 * @param context
	 * @param value
	 * @param key
	 */
	public static void saveSharedPreferences(SharedPreferences sharedPrefs, String key, int value) {
		LogUtil.DefalutLog("key-value:" + key + "-" + value);
		Editor editor = sharedPrefs.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	
	public static void contantUs(Context mContext){
		try {
			Intent emailIntent = new Intent(Intent.ACTION_SEND);
			emailIntent.setType("message/rfc822");
			emailIntent.putExtra(Intent.EXTRA_EMAIL,new String[] { Email });
			mContext.startActivity(emailIntent);
		} catch (Exception e) {
			copy(mContext,Email);
			e.printStackTrace();
		}
	}
	
	/**
	 * ���ư�ť
	 */
	public static void copy(Context mContext,String dstString){
		// �õ������������
		ClipboardManager cmb = (ClipboardManager)mContext.getSystemService(Context.CLIPBOARD_SERVICE);
		cmb.setText(dstString);
		ToastUtil.diaplayMesShort(mContext, mContext.getResources().getString(R.string.copy_success));
	}

}
