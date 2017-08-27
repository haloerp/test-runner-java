package com.haloerp.selenium.resource.loader;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.haloerp.selenium.resource.PathMatcher;
import com.haloerp.selenium.resource.Resource;
import com.haloerp.selenium.resource.ResourceLoader;
import com.haloerp.selenium.resource.ResourceUtils;
import com.haloerp.selenium.resource.matcher.DefaultPathMatcher;

public class PathPatternResourceLoader implements ResourceLoader {

	private PathMatcher pathMatcher = new DefaultPathMatcher();

	private ResourceLoader defaultResourceLoader;
	private ResourceLoader pathMatchingResourceLoader;
	private ResourceLoader classPathResourceLoader;

	public Set<Resource> getResources(String packageName, String locationPattern) throws IOException {
		if (locationPattern == null || locationPattern.length() == 0) {
			return new HashSet<Resource>();
		}

		if (locationPattern.startsWith(ResourceUtils.CLASSPATH_ALL_URL_PREFIX)) {
			if (this.pathMatcher
					.isPattern(locationPattern.substring(ResourceUtils.CLASSPATH_ALL_URL_PREFIX.length()))) {
				return getPathMatchingResourceLoader().getResources(packageName, locationPattern);
			} else {
				return getClassPathResourceLoader().getResources(packageName,
						locationPattern.substring(ResourceUtils.CLASSPATH_ALL_URL_PREFIX.length()));
			}
		} else {
			int prefixEnd = locationPattern.indexOf(":") + 1;
			if (this.pathMatcher.isPattern(locationPattern.substring(prefixEnd))) {
				return getPathMatchingResourceLoader().getResources(packageName, locationPattern);
			} else {
				return getDefaultResourceLoader().getResources(packageName, locationPattern);
			}
		}
	}

	protected PathMatcher getPathMatcher() {
		return pathMatcher;
	}

	protected ResourceLoader getDefaultResourceLoader() {
		if (defaultResourceLoader == null) {
			defaultResourceLoader = new SimpleResourceLoader();
		}
		return defaultResourceLoader;
	}

	protected ResourceLoader getPathMatchingResourceLoader() {
		if (pathMatchingResourceLoader == null) {
			pathMatchingResourceLoader = new PathResourceLoader(pathMatcher, this);
		}
		return pathMatchingResourceLoader;
	}

	protected ResourceLoader getClassPathResourceLoader() {
		if (classPathResourceLoader == null) {
			classPathResourceLoader = new UrlResourceLoader();
		}
		return classPathResourceLoader;
	}

	public Set<Resource> getResources(Resource rootDirResource, String subPattern) throws IOException {
		return null;
	}

}
