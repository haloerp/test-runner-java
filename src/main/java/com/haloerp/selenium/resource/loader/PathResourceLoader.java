package com.haloerp.selenium.resource.loader;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.haloerp.selenium.resource.PathMatcher;
import com.haloerp.selenium.resource.Resource;
import com.haloerp.selenium.resource.ResourceLoader;
import com.haloerp.selenium.resource.ResourceRelativeLoader;
import com.haloerp.selenium.resource.ResourceUtils;

public class PathResourceLoader implements ResourceLoader {

	private PathMatcher pathMatcher;
	private ResourceLoader pathMatchingPatternResourceLoader;

	private ResourceRelativeLoader findPathMatchingJarResourceLoader;
	private ResourceRelativeLoader fileSystemResourceLoader;

	PathResourceLoader(PathMatcher pathMatcher, ResourceLoader pathMatchingPatternResourceLoader) {
		this.pathMatcher = pathMatcher;
		this.pathMatchingPatternResourceLoader = pathMatchingPatternResourceLoader;
	}

	public Set<Resource> getResources(String packageName, String locationPattern) throws IOException {
		Set<Resource> result = new HashSet<Resource>();

		String rootDirPath = determineRootDir(locationPattern);
		String subPattern = locationPattern.substring(rootDirPath.length());
		Set<Resource> rootDirResources = pathMatchingPatternResourceLoader.getResources(packageName, rootDirPath);

		for (Resource rootDirResource : rootDirResources) {
			if (ResourceUtils.isJarURL(rootDirResource.getURL())) {
				result.addAll(this.getFindPathMatchingJarResourceLoader().getResources(rootDirResource, subPattern));
			} else {
				result.addAll(this.getFileSystemResourceLoader().getResources(rootDirResource, subPattern));
			}
		}

		return result;
	}

	protected String determineRootDir(String location) {
		int prefixEnd = location.indexOf(":") + 1;
		int rootDirEnd = location.length();
		while (rootDirEnd > prefixEnd && this.pathMatcher.isPattern(location.substring(prefixEnd, rootDirEnd))) {
			rootDirEnd = location.lastIndexOf('/', rootDirEnd - 2) + 1;
		}
		if (rootDirEnd == 0) {
			rootDirEnd = prefixEnd;
		}
		return location.substring(0, rootDirEnd);
	}

	public ResourceRelativeLoader getFindPathMatchingJarResourceLoader() {
		if (findPathMatchingJarResourceLoader == null) {
			findPathMatchingJarResourceLoader = new JarResourceLoader(pathMatcher);
		}
		return findPathMatchingJarResourceLoader;
	}

	public ResourceRelativeLoader getFileSystemResourceLoader() {
		if (fileSystemResourceLoader == null) {
			fileSystemResourceLoader = new FileSystemResourceLoader(pathMatcher);
		}
		return fileSystemResourceLoader;
	}

}
