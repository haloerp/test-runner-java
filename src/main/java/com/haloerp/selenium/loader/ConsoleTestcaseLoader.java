package com.haloerp.selenium.loader;

import java.util.ArrayList;
import java.util.List;

import com.haloerp.selenium.Testcase;

public class ConsoleTestcaseLoader extends AbstractTestcaseLoader {

	public List<Testcase> getTestcases() {
		return testcases == null ? new ArrayList<Testcase>() : testcases;
	}

}
