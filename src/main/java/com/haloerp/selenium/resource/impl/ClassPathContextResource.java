package com.haloerp.selenium.resource.impl;

import com.haloerp.selenium.resource.Resource;
import com.haloerp.selenium.resource.ResourceUtils;

public class ClassPathContextResource extends ClassPathResource {

	public ClassPathContextResource(String resourceName, String path, ClassLoader classLoader) {
		super(resourceName, path, classLoader);
	}

	public Resource createRelative(String relativePath) {
		String pathToUse = ResourceUtils.applyRelativePath(getPath(), relativePath);
		String relativeName = relativePath.replace(ResourceUtils.PATH_SEPARATOR, ResourceUtils.PACKAGE_SEPARATOR);
		if (relativeName.endsWith(".class")) {
			relativeName = relativeName.replace(".class", "");
		}
		return new ClassPathContextResource(getResourceName() + ResourceUtils.PACKAGE_SEPARATOR + relativeName,
				pathToUse, getResourceClassLoader());
	}

}
