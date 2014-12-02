package org.hbhk.test.i18n;


public final class CustomLocaleContextHolder {

	public static final String ZH_CN = "zh_cn";
	public static final String EN_US = "en_us";
	public static final String I18_LANG_KEY = "i18n_lang_key";
	
	private static final ThreadLocal<CustomLocaleContextHolder> context = new ThreadLocal<CustomLocaleContextHolder>() {
		@Override
		protected CustomLocaleContextHolder initialValue() {
			return new CustomLocaleContextHolder();
		}
	};
	private String locale;

	public static CustomLocaleContextHolder getCurrentContext() {
		return context.get();

	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public static void setCurrentLocale(String locale) {
		getCurrentContext().locale = locale;
	}
	public static void remove(){
		context.remove();
	}
	
	public static String getFieldName(String arg) {
		String str = arg.substring(3,arg.length());
		str = str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toLowerCase());
		return str;
	}
}
