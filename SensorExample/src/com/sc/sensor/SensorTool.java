
package com.sc.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


/*
 * 摇一摇事件监听 工具类
 **/
public class SensorTool
{
	/** 摇一摇事件回调接口 */
	public interface ShakeListener
	{
		public void onShake();
	}
	
	private static final int SENSOR_VALUE = 12;
	
	private static SensorManager sensorManager = null;
	private static SensorEventListener sensorEvent = null;
	private static ShakeListener shakeListener = null;
	
	/** 设置摇一摇事件监听 */
	public static void SetShakeListener(Context context, ShakeListener listener)
	{
		if (sensorManager == null) sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		if (sensorEvent != null) sensorManager.unregisterListener(sensorEvent);
		
		shakeListener = listener;
		if (sensorEvent == null) sensorEvent = new SensorEventListener()
		{
			@Override
			public void onAccuracyChanged(Sensor arg0, int arg1)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onSensorChanged(SensorEvent event)
			{
				int sensorType = event.sensor.getType();
				//values[0]:X轴，values[1]：Y轴，values[2]：Z轴
				float[] values = event.values;
				if (sensorType == Sensor.TYPE_ACCELEROMETER)
				{
					//这里可以调节摇一摇的灵敏度
					if ((Math.abs(values[0]) > SENSOR_VALUE || Math.abs(values[1]) > SENSOR_VALUE || Math.abs(values[2]) > SENSOR_VALUE))
					{
						// System.out.println("onSensorChanged=====>" + " X:" + values[0] + " Y:" + values[1] + " Z:" + values[2]);
						if (null != shakeListener)
						{
							shakeListener.onShake();
						}
					}
				}
			}
		};
		
		sensorManager.registerListener(sensorEvent, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	/** 移除摇一摇事件监听 */
	public static void RemoveShakeListener()
	{
		if (sensorManager != null && sensorEvent != null) sensorManager.unregisterListener(sensorEvent);
	}
}
