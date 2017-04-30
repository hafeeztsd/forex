package org.progresssoft.forex.utils;

/**
 * Timer that calculates the time taken by a process.
 * 
 * @author hafeeztsd
 *
 */
public class Timer {

	private long startTime;
	private long endTime;

	public void start() {
		startTime = System.currentTimeMillis();
	}

	public void stop() {
		endTime = System.currentTimeMillis();
	}

	public void reset() {
		startTime = 0;
		endTime = 0;
	}

	public long getMilliSeconds() {
		return endTime - startTime;
	}

	public long getSconds() {
		return getMilliSeconds() / 1000;
	}

}
