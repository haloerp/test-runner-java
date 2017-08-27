package com.haloerp.selenium.resource;

import java.io.IOException;
import java.util.Set;

public interface ResourceRelativeLoader {

	Set<Resource> getResources(Resource rootDirResource, String subPattern) throws IOException;

}
