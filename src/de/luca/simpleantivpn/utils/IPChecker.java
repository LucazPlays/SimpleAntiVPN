package de.luca.simpleantivpn.utils;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.google.gson.Gson;

public class IPChecker {

	private static IPChecker Instance = new IPChecker();

	private boolean serviceonline = false;

	public boolean isServiceonline() {
		return serviceonline;
	}

	Set<String> badips = ConcurrentHashMap.newKeySet();

	ThreadPoolExecutor threads = (ThreadPoolExecutor) Executors.newCachedThreadPool();

	public IPChecker() {
		new Thread(() -> {
			while (true) {
				try {
					RestAPIResponse ipcheckeralive = RestAPI.getInstance().get("http://ipcheck.skydb.de/alive");
					if (ipcheckeralive.getFailed()) {
						serviceonline = false;
					} else {
						serviceonline = true;
					}
					Thread.sleep(60000);
				} catch (Exception e) {
				}
			}
		}).start();
	}

	public boolean isipresidental(String ip) {
		if (badips.contains(ip)) {
			return false;
		}
		if (serviceonline) {
			RestAPIResponse isipresidental = RestAPI.getInstance().get("http://ipcheck.skydb.de/residental?ip=" + ip);
			if (isipresidental.getFailed()) {
				serviceonline = false;
			} else {
				if (isipresidental.getText().contains("false")) {
					badips.add(ip);
					return false;
				} else {
					return true;
				}
			}
		}
		return true;
	}

	public IPCheckerResult getIPInfo(String ip) {
		if (serviceonline) {
			RestAPIResponse getIPInfo = RestAPI.getInstance().get("http://ipcheck.skydb.de/getinfo?ip=" + ip);
			if (getIPInfo.getFailed()) {
				serviceonline = false;
			} else {
				Gson gson = new Gson();
				return gson.fromJson(getIPInfo.getText(), IPCheckerResult.class);
			}

		}
		return null;
	}


	public ProxysResult getProxyList() {
		if (serviceonline) {
			RestAPIResponse getIPInfo = RestAPI.getInstance().get("http://ipinfo.skydb.de/getproxys");
			if (getIPInfo.getFailed()) {
				serviceonline = false;
			} else {
				Gson gson = new Gson();
				return gson.fromJson(getIPInfo.getText(), ProxysResult.class);
			}

		}
		return null;
	}

	public void start(Runnable run) {
		threads.execute(run);
	}

	public static IPChecker getInstance() {
		return Instance;
	}

	public static void setInstance(IPChecker instance) {
		Instance = instance;
	}
}
