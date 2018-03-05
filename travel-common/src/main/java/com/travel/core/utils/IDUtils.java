package com.travel.core.utils;

import java.util.Random;
/**
 * 各种id生成策略
 * @author Administrator
 *
 */
public class IDUtils {

	/**
	 * 图片名生成
	 */
	public static String genImageName() {
		//取当前时间的长整形值包含毫秒
		long millis = System.currentTimeMillis();
		//long millis = System.nanoTime();
		//加上三位随机数
		Random random = new Random();
		int end3 = random.nextInt(999);
		//如果不足三位前面补0
		String str = millis + String.format("%03d", end3);
		
		return str;
	}
	
	/**
	 * 生成6位数随机数字
	 * @return
	 */
	public static String getServiceNum(){
		
		Random random = new Random();

		String result="";

		for(int i=0;i<6;i++){

		result+=random.nextInt(10);

		}

		return result;
	}
	
	

	
}
