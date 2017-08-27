package com.haloerp.selenium;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import com.haloerp.selenium.resource.Resource;
import com.haloerp.selenium.resource.ResourceLoader;
import com.haloerp.selenium.resource.ResourceUtils;
import com.haloerp.selenium.resource.loader.PathPatternResourceLoader;

/**
 * 
 * @author Halo_Chen
 *
 */
public class ActionDefinitionScanner {

	Map<String, Class<? extends Action>> factory;
	Class<Action> type = Action.class;

	public ActionDefinitionScanner(Map<String, Class<? extends Action>> factory) {
		this.factory = factory;
	}

	@SuppressWarnings("unchecked")
	public boolean doScan(String basePackage) {
		
		if (basePackage == null || basePackage.length() == 0) {
			return false;
		}
		
		try {
			ResourceLoader resourceLoader = new PathPatternResourceLoader();
			Set<Resource> resources = resourceLoader.getResources(basePackage, ResourceUtils.CLASSPATH_ALL_URL_PREFIX
					+ resolveBasePackage(basePackage) + '/' + ResourceUtils.DEFAULT_RESOURCE_PATTERN);
			for (Resource resource : resources) {
				Class<?> clazz = resource.getResourceClass();
				if (clazz != null && type.isAssignableFrom(clazz)) {
					factory.put(clazz.getSimpleName().toLowerCase(), (Class<? extends Action>) clazz);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	protected String resolveBasePackage(String basePackage) {
		return basePackage.replace(ResourceUtils.PACKAGE_SEPARATOR, ResourceUtils.PATH_SEPARATOR);
	}

}
