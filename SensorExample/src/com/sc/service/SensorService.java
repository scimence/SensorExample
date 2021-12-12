
package com.sc.service;

import com.sc.screen.ScreenTool;
import com.sc.sensor.SensorTool;
import com.sc.sensor.SensorTool.ShakeListener;

import android.content.Context;
import android.widget.Toast;

/* 在AndroidManifest.xml添加 <service/>
<application ...>
....
        <service
            android:name="com.sc.service.SensorServices"
            android:enabled="true"
            android:exported="true" >
        </service>
</application>
*/

/**
 * 定义：继承BaseService，重写自定义服务逻辑serviceLogic()
 * 
 * 1、获取服务：SensorServicess.GetInstance()
 * 
 * 2、启动服务：SensorServices.GetInstance().start(context, SensorServices.class);
 * 3、停止服务：SensorServicess.GetInstance().stop();
 * */
public class SensorService extends BaseService
{
	int count = 0;
	
	private boolean sensor = false;
	
	/** 在service中待执行的逻辑（在service未停止时，会多次循环执行）*/
	public void serviceLogic()
	{
//		Toast.makeText(this, "serviceLogic: " + count++, Toast.LENGTH_SHORT).show();
		// TODO Auto-generated method stub
		// ...
		// this.stop(); // 停止服务
		
		if(!sensor)
		{
			sensor = true;
			
			final Context context = this;
			
			// 添加摇一摇事件监听
			SensorTool.SetShakeListener(this, new ShakeListener()
			{
				@Override
				public void onShake()
				{
					ScreenTool.lightOn(context);	// 摇一摇时，点亮屏幕
//					ScreenTool.keyLockOff(context); // 键盘解锁
					
					Toast.makeText(context, "通过摇一摇，点亮屏幕", Toast.LENGTH_SHORT).show();
				}
			});
			
			Toast.makeText(this, "摇一摇亮屏，服务已启动", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void stop()
	{
		Toast.makeText(this, " 摇一摇解锁，服务已停止 !", Toast.LENGTH_SHORT).show();
		sensor = false;
		
		// 移除摇一摇事件监听
		SensorTool.RemoveShakeListener();	
		super.stop();
	}
}
