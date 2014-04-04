package com.vipshop.microscope.trace.switcher;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.vipshop.microscope.common.util.ThreadPoolUtil;

/**
 * A dynamic switcher
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class DynamicSwitcher implements Switcher {

	private static ScheduledExecutorService executor = ThreadPoolUtil.newSingleDaemonScheduledThreadPool("reload-remote-thread");
	
	private static volatile int isopen = 0;
	
	static {
		executor.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				//isopen = call remote db
			}
		}, 0, 10, TimeUnit.SECONDS);
	}
	
	private static final int OPEN = 1;
	private static final int CLOSE = 0;
	
	@Override
	public boolean isOpen() {
		return isopen == OPEN;
	}

	@Override
	public boolean isClose() {
		return isopen == CLOSE;
	}
	
}
