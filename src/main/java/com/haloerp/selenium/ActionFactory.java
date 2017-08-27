package com.haloerp.selenium;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.haloerp.selenium.action.Unknown;

/**
 * 
 * @author Halo_Chen
 *
 */
public class ActionFactory {

	private static final Map<String, Class<? extends Action>> factory = new HashMap<String, Class<? extends Action>>();
	private static final Set<String> actionPackages = new HashSet<String>();
	private static final ActionDefinitionScanner actionScanner = new ActionDefinitionScanner(factory);

	static {
		addActionPackage("com.haloerp.selenium.action");
	}

	public static void addActionPackage(String... basePackages) {
		for (String basePackage : basePackages) {
			if (!actionPackages.contains(basePackage)) {
				if (actionScanner.doScan(basePackage)) {
					actionPackages.add(basePackage);
				}
			}
		}
	}

	public static Map<String, Class<? extends Action>> getActionAll() {
		return factory;
	}

	public static Action get(int id, String action, String key, String string) {
		return get(id, action, factory.get(action.toLowerCase()), key, string);
	}

	public static Action get(int id, String name, Class<? extends Action> clazz, String key, String args) {
		Action action = null;
		if (clazz == null) {
			clazz = Unknown.class;
		}
		try {
			action = clazz.newInstance();

			// FIX: split ","
			action.setParameter(name, id, key, args == null ? null : args.split(","));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return action;
	}

}