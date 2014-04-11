package com.vipshop.microscope.trace.switcher;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.vipshop.microscope.common.util.ConfigurationUtil;
import com.vipshop.microscope.common.util.ThreadPoolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A config reload switcher
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class ConfigReloadSwitcher implements Switcher {

    private static final Logger logger = LoggerFactory.getLogger(ConfigReloadSwitcher.class);

	private static ScheduledExecutorService executor = ThreadPoolUtil.newSingleDaemonScheduledThreadPool("reload-trace.properties-thread");
	
	private static volatile int isopen = 0;
	
	static {

        logger.info("start config reload switcher thread, reload trace.properties file every 10 second");

		executor.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				if (ConfigurationUtil.fileExist("trace.properties")) {
					ConfigurationUtil config = ConfigurationUtil.getConfiguration("trace.properties");
					isopen = config.getInt("switch");
				}
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
