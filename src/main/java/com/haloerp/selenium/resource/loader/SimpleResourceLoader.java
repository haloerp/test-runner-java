package com.haloerp.selenium.resource.loader;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import com.haloerp.selenium.resource.ProtocolResolver;
import com.haloerp.selenium.resource.Resource;
import com.haloerp.selenium.resource.ResourceLoader;
import com.haloerp.selenium.resource.protocol.DefaultProtocolResolver;

public class SimpleResourceLoader implements ResourceLoader {

	private ClassLoader resourceClassLoader;
	private final Set<ProtocolResolver> protocolResolvers = new LinkedHashSet<ProtocolResolver>(4);

	public SimpleResourceLoader() {
		protocolResolvers.add(new DefaultProtocolResolver());
	}

	public void setResourceClassLoader(ClassLoader resourceClassLoader) {
		this.resourceClassLoader = resourceClassLoader;
	}

	public Set<Resource> getResources(String resourceName, String locationPattern) {

		Set<Resource> result = new HashSet<Resource>();

		for (ProtocolResolver protocolResolver : this.protocolResolvers) {
			Resource resource = protocolResolver.resolve(resourceName, locationPattern, resourceClassLoader);
			if (resource != null) {
				result.add(resource);
				break;
			}
		}

		return result;
	}

}
