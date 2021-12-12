package com.sc.screen;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;


// 点亮屏幕、熄灭屏幕  所需权限
// <uses-permission android:name="android.permission.WAKE_LOCK" />

// 键盘锁屏、解锁屏幕 所需权限
// <uses-permission android:name="android.permission.DISABLE_KEYGUARD" />

public class ScreenTool
{	
	private static WakeLock wl = null;
	
	/** 获取WakeLock对象 */
	private static void getWakeLock(Context context)
	{
		PowerManager pm=(PowerManager) context.getSystemService(Context.POWER_SERVICE);	//获取电源管理器对象
		 
        //获取PowerManager.WakeLock对象，后面的参数|表示同时传入两个值，最后的是调试用的Tag
        wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
	}
	
	
	/** 点亮屏幕，验证有效 */
	@SuppressLint("Wakelock")
	@SuppressWarnings("deprecation")
	public static void lightOn(Context context)
	{
		getWakeLock(context);

        //点亮屏幕
        wl.acquire();
	}
	
	/** 熄灭屏幕 */
	public static void lightOff(Context context)
	{
		if(wl==null) getWakeLock(context);
		if(wl != null) wl.release();
	}
	
	
	@SuppressWarnings("deprecation")
	private static KeyguardLock kl = null;
	
	/** 获取KeyLock对象 */
	@SuppressWarnings("deprecation")
	private static void getKeyLock(Context context)
	{
		//得到键盘锁管理器对象
		KeyguardManager km= (KeyguardManager)context.getSystemService(Context.KEYGUARD_SERVICE);
        kl = km.newKeyguardLock("unLock");
	}
	
	
	/** 键盘锁屏 */
	@SuppressWarnings("deprecation")
	public static void keyLock(Context context)
	{
		getKeyLock(context);

        kl.reenableKeyguard();
	}
	
	/** 键盘解锁  */
	@SuppressWarnings("deprecation")
	public static void keyLockOff(Context context)
	{
		if(kl==null) getKeyLock(context);
		if(kl != null) kl.disableKeyguard();
	}
	
}
