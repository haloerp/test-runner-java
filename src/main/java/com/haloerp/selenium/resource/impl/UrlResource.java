package com.haloerp.selenium.resource.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import com.haloerp.selenium.resource.Resource;
import com.haloerp.selenium.resource.ResourceUtils;

public class UrlResource extends AbstractResource {
	protected URL url;

	public UrlResource(String resourceName, URL url, ClassLoader classLoader) {
		super(resourceName, classLoader);
		this.url = url;
	}

	public UrlResource(String resourceName, String path, ClassLoader classLoader) throws MalformedURLException {
		super(resourceName, classLoader);
		this.url = new URL(path);
	}

	public Resource createRelative(String relativePath) throws MalformedURLException {
		if (relativePath.startsWith("/")) {
			relativePath = relativePath.substring(1);
		}

		String relativeName = relativePath.replace(ResourceUtils.PATH_SEPARATOR, ResourceUtils.PACKAGE_SEPARATOR);
		if (relativeName.endsWith(".class")) {
			relativeName = relativeName.replace(".class", "");
		}

		return new UrlResource(getResourceName() + ResourceUtils.PACKAGE_SEPARATOR + relativeName,
				new URL(this.url, relativePath), getResourceClassLoader());
	}

	public File getFile() throws IOException {
		return getFile(url, getDescription());
	}

	public InputStream getInputStream() throws IOException {
		URLConnection con = this.url.openConnection();
		ResourceUtils.useCachesIfNecessary(con);
		try {
			return con.getInputStream();
		} catch (IOException ex) {
			// Close the HTTP connection (if applicable).
			if (con instanceof HttpURLConnection) {
				((HttpURLConnection) con).disconnect();
			}
			throw ex;
		}
	}

	public boolean exists() {
		try {
			URL url = getURL();
			if (ResourceUtils.isFileURL(url)) {
				return getFile().exists();
			} else {
				URLConnection con = url.openConnection();
				ResourceUtils.customizeConnection(con);
				HttpURLConnection httpCon = (con instanceof HttpURLConnection ? (HttpURLConnection) con : null);
				if (httpCon != null) {
					int code = httpCon.getResponseCode();
					if (code == HttpURLConnection.HTTP_OK) {
						return true;
					} else if (code == HttpURLConnection.HTTP_NOT_FOUND) {
						return false;
					}
				}
				if (con.getContentLength() >= 0) {
					return true;
				}
				if (httpCon != null) {
					httpCon.disconnect();
					return false;
				} else {
					InputStream is = getInputStream();
					is.close();
					return true;
				}
			}
		} catch (IOException ex) {
			return false;
		}
	}

	protected String getDescription() {
		return "URL [" + this.url + "]";
	}

	protected File getFile(URL resourceUrl, String description) throws FileNotFoundException {
		if (!ResourceUtils.URL_PROTOCOL_FILE.equals(resourceUrl.getProtocol())) {
			throw new FileNotFoundException(description + " cannot be resolved to absolute file path "
					+ "because it does not reside in the file system: " + resourceUrl);
		}
		try {
			return new File(ResourceUtils.toURI(resourceUrl.toString()).getSchemeSpecificPart());
		} catch (URISyntaxException ex) {
			return new File(resourceUrl.getFile());
		}
	}

	public URL getURL() throws IOException {
		return url;
	}

}
