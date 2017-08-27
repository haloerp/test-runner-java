package com.haloerp.selenium.resource.loader;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import com.haloerp.selenium.resource.Resource;
import com.haloerp.selenium.resource.ResourceLoader;
import com.haloerp.selenium.resource.ResourceUtils;
import com.haloerp.selenium.resource.impl.UrlResource;

public class UrlResourceLoader implements ResourceLoader {

	public Set<Resource> getResources(String packageName, String locationPattern) throws IOException {
		Set<Resource> result = new HashSet<Resource>();
		String path = locationPattern;
		if (path.startsWith("/")) {
			path = path.substring(1);
		}

		ClassLoader cl = ResourceUtils.getDefaultClassLoader();
		Enumeration<URL> resourceUrls = (cl != null ? cl.getResources(path) : ClassLoader.getSystemResources(path));
		while (resourceUrls.hasMoreElements()) {
			URL url = resourceUrls.nextElement();
			result.add(new UrlResource(packageName, url, cl));
		}
		if ("".equals(path)) {
			addAllClassLoaderJarRoots(packageName, cl, result);
		}

		return result;
	}

	protected void addAllClassLoaderJarRoots(String packageName, ClassLoader classLoader, Set<Resource> result) {
		if (classLoader instanceof URLClassLoader) {
			try {
				for (URL url : ((URLClassLoader) classLoader).getURLs()) {
					try {
						UrlResource jarResource = new UrlResource(packageName,
								ResourceUtils.JAR_URL_PREFIX + url.toString() + ResourceUtils.JAR_URL_SEPARATOR, null);
						if (jarResource.exists()) {
							result.add(jarResource);
						}
					} catch (MalformedURLException ex) {
					}
				}
			} catch (Exception ex) {
			}
		}

		if (classLoader == ClassLoader.getSystemClassLoader()) {
			addClassPathManifestEntries(packageName, result);
		}

		if (classLoader != null) {
			try {
				addAllClassLoaderJarRoots(packageName, classLoader.getParent(), result);
			} catch (Exception ex) {

			}
		}
	}

	protected void addClassPathManifestEntries(String packageName, Set<Resource> result) {
		try {
			String javaClassPathProperty = System.getProperty("java.class.path");
			for (String path : ResourceUtils.delimitedListToStringArray(javaClassPathProperty,
					System.getProperty("path.separator"), null)) {
				try {
					File file = new File(path);
					UrlResource jarResource = new UrlResource(packageName, ResourceUtils.JAR_URL_PREFIX
							+ ResourceUtils.FILE_URL_PREFIX + file.getAbsolutePath() + ResourceUtils.JAR_URL_SEPARATOR,
							null);
					if (jarResource.exists()) {
						result.add(jarResource);
					}
				} catch (MalformedURLException ex) {

				}
			}
		} catch (Exception ex) {

		}
	}

}
