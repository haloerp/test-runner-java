package com.haloerp.selenium.resource.protocol;

import java.net.MalformedURLException;

import com.haloerp.selenium.resource.ProtocolResolver;
import com.haloerp.selenium.resource.Resource;
import com.haloerp.selenium.resource.ResourceUtils;
import com.haloerp.selenium.resource.impl.ClassPathContextResource;
import com.haloerp.selenium.resource.impl.ClassPathResource;
import com.haloerp.selenium.resource.impl.UrlResource;

public class DefaultProtocolResolver implements ProtocolResolver {

	public Resource resolve(String resourceName, String location, ClassLoader resourceClassLoader) {
		if (location.startsWith("/")) {
			return new ClassPathContextResource(resourceName, location, resourceClassLoader);
		} else if (location.startsWith(ResourceUtils.CLASSPATH_URL_PREFIX)) {
			return new ClassPathResource(resourceName, location.substring(ResourceUtils.CLASSPATH_URL_PREFIX.length()),
					resourceClassLoader);
		} else {
			try {
				return new UrlResource(resourceName, location, null);
			} catch (MalformedURLException ex) {
				return new ClassPathContextResource(resourceName, location, resourceClassLoader);
			}
		}
	}

}
