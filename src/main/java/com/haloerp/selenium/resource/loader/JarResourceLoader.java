package com.haloerp.selenium.resource.loader;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipException;

import com.haloerp.selenium.resource.PathMatcher;
import com.haloerp.selenium.resource.Resource;
import com.haloerp.selenium.resource.ResourceRelativeLoader;
import com.haloerp.selenium.resource.ResourceUtils;

/**
 * <pre>
 * <code>ResourceRelativeLoader loader = new ResourceRelativeLoader(new DefaultPathMatcher());
 * Resource resource = cl.getResource();
 * if (isJarResource) {
 *     Set&lt;Resource&gt; resources = loader.getResources(resource, ResourcesUtils.DEFAULT_RESOURCE_PATTERN);
 * }
 * </code>
 * </pre>
 * 
 * @author Halo_Chen
 *
 */
public class JarResourceLoader implements ResourceRelativeLoader {

	private PathMatcher pathMatcher;

	JarResourceLoader(PathMatcher pathMatcher) {
		this.pathMatcher = pathMatcher;
	}

	public Set<Resource> getResources(Resource rootDirResource, String subPattern) throws IOException {
		Set<Resource> result = null;
		URL rootDirURL = rootDirResource.getURL();
		URLConnection urlConnection = rootDirURL.openConnection();
		JarFile jarFile;
		String jarFileUrl;
		String rootEntryPath;
		boolean closeJarFile;

		if (urlConnection instanceof JarURLConnection) {
			JarURLConnection jarCon = (JarURLConnection) urlConnection;
			ResourceUtils.useCachesIfNecessary(jarCon);
			jarFile = jarCon.getJarFile();
			jarFileUrl = jarCon.getJarFileURL().toExternalForm();
			JarEntry jarEntry = jarCon.getJarEntry();
			rootEntryPath = (jarEntry != null ? jarEntry.getName() : "");
			closeJarFile = !jarCon.getUseCaches();
		} else {
			String urlFile = rootDirURL.getFile();
			try {
				int separatorIndex = urlFile.indexOf(ResourceUtils.JAR_URL_SEPARATOR);
				if (separatorIndex != -1) {
					jarFileUrl = urlFile.substring(0, separatorIndex);
					rootEntryPath = urlFile.substring(separatorIndex + ResourceUtils.JAR_URL_SEPARATOR.length());
					jarFile = getJarFile(jarFileUrl);
				} else {
					jarFile = new JarFile(urlFile);
					jarFileUrl = urlFile;
					rootEntryPath = "";
				}
				closeJarFile = true;
			} catch (ZipException ex) {
				return Collections.emptySet();
			}
		}

		try {
			if (!"".equals(rootEntryPath) && !rootEntryPath.endsWith("/")) {
				rootEntryPath = rootEntryPath + "/";
			}
			result = new LinkedHashSet<Resource>(8);
			for (Enumeration<JarEntry> entries = jarFile.entries(); entries.hasMoreElements();) {
				JarEntry entry = entries.nextElement();
				String entryPath = entry.getName();
				if (entryPath.startsWith(rootEntryPath)) {
					String relativePath = entryPath.substring(rootEntryPath.length());
					if (this.pathMatcher.match(subPattern, relativePath)) {
						result.add(rootDirResource.createRelative(relativePath));
					}
				}
			}
			return result;
		} finally {
			if (closeJarFile) {
				jarFile.close();
			}
		}
	}

	protected JarFile getJarFile(String jarFileUrl) throws IOException {
		if (jarFileUrl.startsWith(ResourceUtils.FILE_URL_PREFIX)) {
			try {
				return new JarFile(ResourceUtils.toURI(jarFileUrl).getSchemeSpecificPart());
			} catch (URISyntaxException ex) {
				return new JarFile(jarFileUrl.substring(ResourceUtils.FILE_URL_PREFIX.length()));
			}
		} else {
			return new JarFile(jarFileUrl);
		}
	}
}
