package com.yjg.appui.util;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * @author 
 *
 */
public class Log {
	private static Logger log = Logger.getLogger(Log.class);
	private static Log logger;
	private static Thread thread;
	public static Log getLogger(Class<?> T) {
		init();
		log = Logger.getLogger(T);
		logger = new Log();
		thread=Thread.currentThread();
		return logger;
	}
	public static void init() {
		PropertyConfigurator.configure("configs/log4j.properties");
	}

	public void debug(Object message) {
		log.debug(thread.getName()+message);
	}

	public void debug(Object message, Throwable t) {
		log.debug(thread.getName()+message, t);
	}

	public void info(Object message) {
		log.info(thread.getName()+message);
	}

	public void info(Object message, Throwable t) {
		log.info(thread.getName()+message, t);
	}

	public void warn(Object message) {
		log.warn(thread.getName()+message);
	}

	public void warn(Object message, Throwable t) {
		log.warn(thread.getName()+message, t);
	}

	public void error(Object message) {
		log.error(thread.getName()+message);
	}

	public void error(Object message, Throwable t) {
		log.error(thread.getName()+message, t);
	}
}