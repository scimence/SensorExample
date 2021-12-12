
package com.sc.sensorDemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.sc.screen.ScreenTool;
import com.sc.sensorexample.R;
import com.sc.service.SensorService;


public class MainActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	// 启动服务
	public void startService(View v)
	{
		SensorService.GetInstance().start(this, SensorService.class);
	}
	
	// 暂时停止服务，服务逻辑会在应用退出后自行重启，并一直运行
	public void stopService(View v)
	{
		SensorService.GetInstance().stop();
	}
	

	// 熄灭屏幕
	public void lightOff(View v)
	{
		ScreenTool.lightOff(this);
	}
	

	// 键盘锁屏
	public void keyLockOff(View v)
	{
		ScreenTool.keyLockOff(this);
	}
	
}
