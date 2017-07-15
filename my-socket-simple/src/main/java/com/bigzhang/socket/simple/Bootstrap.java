package com.bigzhang.socket.simple;

import com.bigzhang.socket.simple.bootstrap.SystemInfo;
import com.bigzhang.socket.simple.bootstrap.TimerCenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bootstrap {

	private static String identification;
	private static Logger logger;

	private static void initializeRunningInfo() {

		long startTime = TimerCenter.self.getCurrentTimeMillis();
		String threadId = SystemInfo.getJVMRunningId();
		String serviceIP = SystemInfo.getFirstServiceIp();

		if (startTime <= 0 || threadId == null || threadId.isEmpty() || serviceIP == null || serviceIP.isEmpty()) {
			throw new IllegalArgumentException("generateIdentification方法，获取参数失败");
		}

		identification = serviceIP + ":" + threadId + ":" + startTime;
		System.setProperty("com.bigzhang.instance.name", identification);// 日志用

		String logId = serviceIP + "-" + threadId ;
		System.setProperty("com.bigzhang.instance.log.id", logId); // 日志用

		logger = LoggerFactory.getLogger(Bootstrap.class);
		logger.info("Startup successfully, identification:{},   logId:{}", identification, logId);
	}


	public static void main(String[] args) {
		try {
			// 加载启动信息
			initializeRunningInfo();
		} catch (Exception ex) {
			logger.error("Startup error! ", ex);
		}
	}
}