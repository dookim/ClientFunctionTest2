package com.urqa.Collector;

import java.io.File;
import java.math.BigInteger;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class DeviceCollector {

	Context m_Context;

	public DeviceCollector(Context context) {
		// TODO Auto-generated constructor stub
		m_Context = context;

	}

    static public String getDeviceId(Context context, String apiKey) {
        String a = new BigInteger(1, android.os.Build.MANUFACTURER.getBytes()).toString(16);
        String b = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        String c = new BigInteger(1, android.os.Build.BRAND.getBytes()).toString(16);
        String d = apiKey;
        return String.format("%s-%s-%s-%s", nullToUnknown(a), nullToUnknown(b), nullToUnknown(c), nullToUnknown(d));
    }
    
    
    static private String nullToUnknown(String s) {
    	if (s == null || "".equals(s))
    		return "unknown";
    	else 
    		return s;
    }


	/**
	 * 
	 * @return �쒖“
	 */
	static public String getManufacturer() {
		return android.os.Build.MANUFACTURER;
	}

	/**
	 * 
	 * @return �쒖“
	 */
	static public String getVersionRelease() {
		return android.os.Build.VERSION.RELEASE;
	}

	/**
	 * 
	 * @return �붾컮�댁뒪 紐⑤뜽紐�	 */
	static public String getDeviceModel() {
		return android.os.Build.MODEL;
	}

	/**
	 * �듭떊
	 * 
	 * @return
	 */
	static public String getCarrierName(Context context) {
		try {
			String name;
			TelephonyManager manager = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			name = manager.getSimOperatorName();
			if (name == null || "".equals(name)) {
				name = manager.getNetworkOperatorName();
				if (name == null || "".equals(name)) {
					return "unknown";
				}
			}
			return name;
		} catch (Exception e) {
			return "unknown";
		}
	}

	static public String getCountry(Context context) {
		String name = getCountryIso(context);
		if ("".equals(name)) {
			return "unknown";
		} else {
			return name;
		}
	}

	static private Criteria getCriteria() {
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(false);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		return criteria;
	}

	static private String getFromLocation(Context context, Location location) {
		if (location == null) {
			return "unknown";
		} else {
			Geocoder geocoder = new Geocoder(context, Locale.US);
			String country = "";
			try {
				List<Address> list = geocoder.getFromLocation(
						location.getLatitude(), location.getLongitude(), 3);
				if (!list.isEmpty()) {
					Address address = list.get(0);
					country = address.getCountryCode();
					if (country == null || "".equals(country)) {
						return "unknown";
					} else {
						return country;
					}
				}

				return "unknown";
			} catch (Exception e) {
				return "unknown";
			}
		}
	}

	static private String getCountryIso(Context context) {
		try {
			String name;
			TelephonyManager manager = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			name = manager.getSimCountryIso();
			if (name == null || "".equals(name)) {
				name = manager.getNetworkCountryIso();
				if (name == null || "".equals(name)) {
					return "";
				}
			}
			return name;
		} catch (Exception e) {
			return "";
		}
	}

	static public int BytetoMegaByte(Long Byte) {
		Long kb = Byte / 1024;
		return (int) (kb / 1024);
	}

	static private boolean GetNetwork(Context context, int Type) {
		boolean use = false;
		try {
			PackageManager packagemanager = context.getPackageManager();
			if (packagemanager.checkPermission(
					"android.permission.ACCESS_NETWORK_STATE",
					context.getPackageName()) == 0) {
				ConnectivityManager manager = (ConnectivityManager) context
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				use = manager.getNetworkInfo(Type).isConnected();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return use;
	}

	static public boolean GetMobileNetwork(Context context) {
		return GetNetwork(context, ConnectivityManager.TYPE_MOBILE);
	}

	static public boolean GetWiFiNetwork(Context context) {
		return GetNetwork(context, ConnectivityManager.TYPE_WIFI);
	}

	static public String GetAppVersion(Context context) {
		try {
			PackageManager packagemanager = context.getPackageManager();
			PackageInfo packageinfo = packagemanager.getPackageInfo(
					context.getPackageName(), 0);
			return packageinfo.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "unknown";
		}
	}

	static public int GetBatteryLevel(Context context) {
		Intent bat = new Intent(); // 諛고꽣由�媛믪쓣 諛쏅뒗 �명뀗��蹂�닔
		int batLevel = 0; // �명뀗�몄뿉��諛고꽣由�媛믪쓣 諛쏅뒗��
		bat = context.registerReceiver(null, new IntentFilter(
				Intent.ACTION_BATTERY_CHANGED));
		batLevel = bat.getIntExtra("level", -1);
		return batLevel;
	}

	/**
	 * �섎씪 肄붾뱶瑜�媛�졇�⑤떎.
	 * 
	 * @since 2012. 11. 15.�ㅼ쟾 6:00:49 TODO
	 * @author JeongSeungsu
	 * @param context
	 * @return �섎씪肄붾뱶媛��쒕�濡��덈맆��Unknown諛섑솚 �꾨땲硫�kr 媛숈� �섎씪肄붾뱶 諛섑솚
	 */
	static public String GetNational(Context context) {
		Locale nowlocale = context.getResources().getConfiguration().locale;
		String isNull = "";
		if (isNull.equals(nowlocale.getCountry()))
			return "unknown";
		else
			return nowlocale.getCountry();
	}

	/**
	 * Gps �곹깭瑜�媛�졇�⑤떎.
	 * 
	 * @since 2012. 11. 15.�ㅼ쟾 6:01:19 TODO
	 * @author JeongSeungsu
	 * @param context
	 * @return true �ъ슜以�false 硫�鍮꾩궗��	 */
	static public boolean GetGps(Context context) {
		PackageManager packagemanager = context.getPackageManager();
		if (packagemanager.checkPermission(
				"android.permission.ACCESS_FINE_LOCATION",
				context.getPackageName()) == 0) {
			LocationManager locationManager = (LocationManager) context
					.getSystemService(Context.LOCATION_SERVICE);
			if (!locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER))
				return false;
			else
				return true;
		}
		return false;
	}

	/**
	 * 媛�줈 �ㅽ겕由��ъ씠利덈� 媛�졇�⑤떎.
	 * 
	 * @since 2012. 11. 15.�ㅼ쟾 6:01:42 TODO
	 * @author JeongSeungsu
	 * @param context
	 * @return 媛�줈 �ㅽ겕由��ш린
	 */
	static public int GetWidthScreenSize(Context context) {
		Display display = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		return display.getWidth();
	}

	/**
	 * �몃줈 �ㅽ겕由��ъ씠利덈� 媛�졇�⑤떎.
	 * 
	 * @since 2012. 11. 15.�ㅼ쟾 6:01:58 TODO
	 * @author JeongSeungsu
	 * @param context
	 * @return �몃줈 �ㅽ겕由��ш린
	 */
	static public int GetHeightScreenSize(Context context) {
		Display display = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		return display.getHeight();
	}

	static final int ERROR = -1;

	static public boolean externalMemoryAvailable() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}

	static public long getAvailableInternalMemorySize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return availableBlocks * blockSize;
	}

	static public long getTotalInternalMemorySize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();
		return totalBlocks * blockSize;
	}

	static public long getAvailableExternalMemorySize() {
		if (externalMemoryAvailable()) {
			File path = Environment.getExternalStorageDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long availableBlocks = stat.getAvailableBlocks();
			return availableBlocks * blockSize;
		} else {
			return ERROR;
		}
	}

	static public long getTotalExternalMemorySize() {
		if (externalMemoryAvailable()) {
			File path = Environment.getExternalStorageDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long totalBlocks = stat.getBlockCount();
			return totalBlocks * blockSize;
		} else {
			return ERROR;
		}
	}

	static public boolean CheckRoot() {
		boolean flag = false;
		String as[] = { "/sbin/", "/system/bin/", "/system/xbin/",
				"/data/local/xbin/", "/data/local/bin/", "/system/sd/xbin/",
				"/system/bin/failsafe/", "/data/local/" };
		String as1[] = as;
		int i = as1.length;
		int j = 0;
		do {
			if (j >= i)
				break;
			String s = as1[j];
			File file = new File((new StringBuilder()).append(s).append("su")
					.toString());
			if (file.exists()) {
				flag = true;
				break;
			}
			j++;
		} while (true);
		return flag;
	}

	static public long GetTotalMemory() {
		long total = Runtime.getRuntime().totalMemory();
		return total;
	}

	static public long GetFreeMemory() {
		long free = Runtime.getRuntime().freeMemory();
		return free;
	}

	static public long GetMaxMemory() {
		long max = Runtime.getRuntime().maxMemory();
		return max;
	}

	static public String GetLinuxKernelVersion() {
		return System.getProperty("os.version");
	}

	static public float GetXDPI(Context context) {
		DisplayMetrics metrics = new DisplayMetrics();
		((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay().getMetrics(metrics);
		return metrics.xdpi;	}

	static public float GetYDPI(Context context) {
		DisplayMetrics metrics = new DisplayMetrics();
		((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay().getMetrics(metrics);
		return metrics.ydpi; 	}

	static public String GetLocale(Context context) {
		return context.getResources().getConfiguration().locale
				.getDisplayLanguage();
	}

	/**
	 *
	 * @since 2013. 8. 12.�ㅽ썑 7:41:37
	 * @author JeongSeungsu
	 * @param context
	 * @return 0 �몃줈 1 媛�줈
	 */
	static public int GetOrientation(Context context) {
		return ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
				.getOrientation();
	}

	static public boolean GetSystemLowMemory() {
		android.app.ActivityManager.MemoryInfo memoryinfo = new android.app.ActivityManager.MemoryInfo();
		return memoryinfo.lowMemory;
	}
}
