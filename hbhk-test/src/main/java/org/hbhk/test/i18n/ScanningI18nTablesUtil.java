package org.hbhk.test.i18n;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hbhk.test.i18n.annotation.I18n;
import org.hbhk.test.i18n.annotation.Table;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

public class ScanningI18nTablesUtil {
	
	private static  Set<String> i18nTables = new HashSet<String>();
	
	private static  Set<String> AllTables = new HashSet<String>();
	
   /**
	* @author 何波
	* @Description: 获取i18n表
	* @throws ClassNotFoundException   
    */
	public static Set<String> getI18nTables(String scannPackages) throws ClassNotFoundException {
		if(i18nTables.size() > 0){
			return i18nTables;
		}
	//	Properties metainfo = ProfileConfigUtil.findPro("config/metainfo.properties");
		/**
		 * 多个包以,分隔
		 */
		if(StringUtils.isEmpty(scannPackages)){
			return i18nTables;
		}
		String[] sps = scannPackages.split(",");
		if(sps==null|| sps.length == 0){
			return i18nTables;
		}
		// 是否使用默认过滤器true使用
		ClassPathScanningCandidateComponentProvider packageScan = new ClassPathScanningCandidateComponentProvider(
				false);
		packageScan.addIncludeFilter(new AnnotationTypeFilter(I18n.class));
		for (String pack : sps) {
			Set<BeanDefinition> bds = packageScan.findCandidateComponents(pack);
			for (BeanDefinition beanDefinition : bds) {
				Class<?> clazz = Class.forName(beanDefinition.getBeanClassName());
				I18n i18n = clazz.getAnnotation(I18n.class);
				Table tab = clazz.getAnnotation(Table.class);
				if(i18n != null && tab != null){
					i18nTables.add(tab.name().toLowerCase());
				}
			}
		}
		return i18nTables;
	}
	public static Set<String> getAllTables(String scannPackages) throws ClassNotFoundException {
		if(AllTables.size() > 0){
			return AllTables;
		}
		/**
		 * 多个包以,分隔
		 */
		if(StringUtils.isEmpty(scannPackages)){
			return AllTables;
		}
		String[] sps = scannPackages.split(",");
		if(sps==null|| sps.length == 0){
			return AllTables;
		}
		// 是否使用默认过滤器true使用
		ClassPathScanningCandidateComponentProvider packageScan = new ClassPathScanningCandidateComponentProvider(
				false);
		packageScan.addIncludeFilter(new AnnotationTypeFilter(Table.class));
		for (String pack : sps) {
			Set<BeanDefinition> bds = packageScan.findCandidateComponents(pack);
			for (BeanDefinition beanDefinition : bds) {
				Class<?> clazz = Class.forName(beanDefinition.getBeanClassName());
				Table tab = clazz.getAnnotation(Table.class);
				if(tab != null){
					String name = tab.name().toLowerCase();
					AllTables.add(name);
					I18n i18n = clazz.getAnnotation(I18n.class);
					if(i18n != null){
						AllTables.add(name+"_"+CustomLocaleContextHolder.EN_US.toLowerCase());
						i18nTables.add(name);
					}
				}
				
			}
		}
		return AllTables;
	}

}

