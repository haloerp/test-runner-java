package com.haloerp.script;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class MainScript {
	public static void main(String[] args) {
		ScriptEngineManager scriptManager = new ScriptEngineManager();
		ScriptEngine scriptEngine = scriptManager.getEngineByName("js");
		try {
			String script = "";
			scriptEngine.eval(script);
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}
}
