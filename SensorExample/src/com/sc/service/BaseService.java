
package com.sc.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;


/* 在AndroidManifest.xml添加 <service/>
 <application ...>
 ....
        <service
            android:name="com.sc.service.MsgService"
            android:enabled="true"
            android:exported="true" >
        </service>
 </application>
 */

/** Service类，简化Service调用: 
 * 子类可继承BaseService，重写函数serviceLogic()实现自定义服务逻辑
 * 
 *  1、获取服务单例对象 BaseService.GetInstance() 
 *  2、启动服务 .start(Context context, Class<?> service_cls)（一次启动，持续运行） 
 *  3、停止服务 .stop() */
public class BaseService extends Service
{
	protected BaseService()
	{}
	
	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		if (!isrunning)
		{
			// if (BaseService.intent == null) BaseService.intent = intent;
			// if (context == null) context = this.getBaseContext();
			
			isrunning = true;
			// Log.i(this.getClass().getSimpleName(), "onStartCommand");
			
			// 执行服务处理逻辑
			doServicesLogic(IntervalMillis);
		}
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	/** service执行间隔时间（毫秒） */
	public static long IntervalMillis = 2000;
	
	
	Runnable runable;
	
	/** 循环执行服务处理逻辑 */
	private void doServicesLogic(final long delayMillis)
	{
		if(runable == null)
		{
			runable = new Runnable()
			{
				@Override
				public void run()
				{
					if (isrunning)
					{
						// Toast.makeText(context, "doServicesLogic is running !", Toast.LENGTH_SHORT).show();
						serviceLogic();						// 执行服务处理逻辑
						
						doServicesLogic(delayMillis);		// 处理逻辑执行完成1秒后再次执行
					}
				}
			};
		}
		new Handler().postDelayed(runable, delayMillis);
	}
	
	/** 在service中待执行的逻辑（在service未停止时，会一直执行，每轮逻辑执行间隔IntervalMillis毫秒） */
	public void serviceLogic()
	{};
	
	static BaseService Instance;
	
	/** 获取当前服务的单例对象 */
	public static BaseService GetInstance()
	{
		if (Instance == null) Instance = new BaseService();
		return Instance;
	}
	
	// ------------
	
	private static boolean isrunning = false;
	
	// private static Intent intent;
	// private static Context context;
	
	/** 启动服务, service_cls当前服务对应的类 */
	public void start(Context context, Class<?> service_cls)
	{
		if (!isrunning)
		{
			// BaseService.context = context;
			Intent intent = new Intent(context, service_cls);
			context.startService(intent);
			
			// Toast.makeText(context, this.getClass().getSimpleName() + " 服务已启动 !", Toast.LENGTH_SHORT).show();
		}
	}
	
	/** 停止服务（暂时停止服务，服务逻辑会在应用退出后自行重启，并一直运行） */
	public void stop()
	{
		if (isrunning)
		{
			stopSelf();
			// context.stopService(intent);
			isrunning = false;
			
			// Toast.makeText(context, this.getClass().getSimpleName() + " 服务已停止 !", Toast.LENGTH_SHORT).show();
		}
	}
	
}
