package com.haloerp.selenium.resource.loader;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import com.haloerp.selenium.resource.PathMatcher;
import com.haloerp.selenium.resource.Resource;
import com.haloerp.selenium.resource.ResourceRelativeLoader;
import com.haloerp.selenium.resource.ResourceUtils;
import com.haloerp.selenium.resource.impl.FileSystemResource;

public class FileSystemResourceLoader implements ResourceRelativeLoader {

	private PathMatcher pathMatcher;

	FileSystemResourceLoader(PathMatcher pathMatcher) {
		this.pathMatcher = pathMatcher;
	}

	public Set<Resource> getResources(Resource rootDirResource, String subPattern) throws IOException {
		File rootDir = rootDirResource.getFile().getAbsoluteFile();

		if (!rootDir.exists()) {
			return Collections.emptySet();
		}

		if (!rootDir.isDirectory()) {
			return Collections.emptySet();
		}
		if (!rootDir.canRead()) {

			return Collections.emptySet();
		}
		String fullPattern = rootDir.getAbsolutePath().replace(File.separator, "/");
		if (!subPattern.startsWith("/")) {
			fullPattern += "/";
		}
		fullPattern = fullPattern + subPattern.replace(File.separator, "/");

		Set<Resource> result = new LinkedHashSet<Resource>(8);
		doRetrieveMatchingFiles(rootDirResource.getResourceName(), fullPattern, rootDir, result);
		return result;
	}

	protected void doRetrieveMatchingFiles(String packageName, String fullPattern, File dir, Set<Resource> result)
			throws IOException {

		File[] dirContents = dir.listFiles();
		if (dirContents == null) {
			return;
		}

		Arrays.sort(dirContents);
		for (File file : dirContents) {
			String currPath = file.getAbsolutePath().replace(File.separator, "/");
			if (file.isDirectory() && this.pathMatcher.matchStart(fullPattern, currPath + "/")) {
				if (file.canRead()) {
					doRetrieveMatchingFiles(packageName + ResourceUtils.PACKAGE_SEPARATOR + file.getName(), fullPattern,
							file, result);
				}
			}
			if (this.pathMatcher.match(fullPattern, currPath)) {
				Resource resource = new FileSystemResource(
						packageName + ResourceUtils.PACKAGE_SEPARATOR + file.getName().replace(".class", ""), file,
						null);
				result.add(resource);
			}
		}
	}
}
