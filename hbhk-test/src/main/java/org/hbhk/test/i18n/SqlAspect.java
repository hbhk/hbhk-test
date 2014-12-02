package org.hbhk.test.i18n;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
@Aspect
@Component
public class SqlAspect {
	public static final Logger log = LoggerFactory
			.getLogger(SqlAspect.class);
	
	@Value("#{meta['i18n.aop']}")
	private String i18n_aop;
	@Value("#{meta['scann.packages']}")
	private String scannPackages = null;
	
	@Around("execution(* loxia.dao.DynamicNamedQueryProvider.getDynamicQueryByName(..))")
	public Object doI18n(ProceedingJoinPoint pjp) throws Throwable {
		 Object result =  pjp.proceed();
		//检查是否启用国际化aop
		if(i18n_aop == null || i18n_aop.equals("false") || !i18n_aop.equals("true")){
			return result;
		}
		if(result instanceof String){
			String sql = (String) result;
			log.debug("sql语句:\n{}",sql);
			boolean repeat = false;
			sql = repeatSqlTabName(sql,repeat);
			if(repeat){
				log.debug("替换之后的sql语句:\n{}",sql);
			}
			//必须加上一个空格,原因是loxia在解析动态参数时需要
			result  = sql+" ";
		}
		return result;
	}
	public String  repeatSqlTabName(String sql,boolean repeat) throws ClassNotFoundException {
		Set<String> tables = new HashSet<String>();
		Pattern p = Pattern.compile("(?i)t_\\S{0,}");
		Matcher m = p.matcher(sql.trim());
		//获取当前国际化信息
		String customLocale = CustomLocaleContextHolder.getCurrentContext().getLocale();
		if(customLocale == null){
			String i18nLocale = LocaleContextHolder.getLocale().toString().toLowerCase();
			customLocale = i18nLocale;
		}
		if(customLocale.equals(CustomLocaleContextHolder.ZH_CN)){
			return  sql;
		}
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String name = m.group().trim();
			if(StringUtils.isNotEmpty(name) && name.toLowerCase().startsWith("t_")){
				boolean exits = ScanningI18nTablesUtil.getAllTables(scannPackages).contains(name.toLowerCase());
				if(exits){
					tables.add(name);
					boolean isI18n = ScanningI18nTablesUtil.getI18nTables(scannPackages).contains(name.toLowerCase());
					if(isI18n){
						String newName = name+"_"+customLocale;
						m.appendReplacement(sb, newName);
						repeat = true;
						if(log.isDebugEnabled()){
							log.debug("将表名:{}替换为:{}\n",new Object[]{name,newName});
					    }
					}
				}
			}
		}
		m.appendTail(sb);
		return sb.toString();
	}
}

