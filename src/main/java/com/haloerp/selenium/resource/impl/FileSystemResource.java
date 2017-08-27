package com.haloerp.selenium.resource.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.haloerp.selenium.resource.Resource;
import com.haloerp.selenium.resource.ResourceUtils;

public class FileSystemResource extends AbstractResource {

	private final File file;
	private final String path;

	public FileSystemResource(String resourceName, File file, ClassLoader resourceClassLoader) {
		super(resourceName, resourceClassLoader);
		this.file = file;
		this.path = ResourceUtils.cleanPath(file.getPath());
	}

	public FileSystemResource(String resourceName, String path, ClassLoader resourceClassLoader) {
		super(resourceName, resourceClassLoader);
		this.file = new File(path);
		this.path = ResourceUtils.cleanPath(path);
	}

	public URL getURL() throws IOException {
		return this.file.toURI().toURL();
	}

	public Resource createRelative(String relativePath) {
		String pathToUse = ResourceUtils.applyRelativePath(this.path, relativePath);
		String relativeName = relativePath.replace(ResourceUtils.PATH_SEPARATOR, ResourceUtils.PACKAGE_SEPARATOR);
		if (relativeName.endsWith(".class")) {
			relativeName = relativeName.replace(".class", "");
		}
		return new FileSystemResource(getResourceName() + ResourceUtils.PACKAGE_SEPARATOR + relativeName, pathToUse,
				getResourceClassLoader());
	}

	public File getFile() {
		return this.file;
	}

	public InputStream getInputStream() throws IOException {
		return new FileInputStream(this.file);
	}

}
