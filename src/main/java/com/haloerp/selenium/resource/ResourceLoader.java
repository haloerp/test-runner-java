package com.haloerp.selenium.resource;

import java.io.IOException;
import java.util.Set;

public interface ResourceLoader {

	Set<Resource> getResources(String packageName, String locationPattern) throws IOException;

}
