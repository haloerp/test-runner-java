package com.haloerp.selenium;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Halo_Chen
 *
 */
public class Scenario {

	private String name;
	private List<String> beforeMessage = new ArrayList<String>();
	private List<Action> actions = new ArrayList<Action>();
	private List<String> afterMessage = new ArrayList<String>();

	public Scenario(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void add(Action action) {
		actions.add(action);
	}

	public List<Action> getActions() {
		return actions;
	}

	public void clear() {
		actions.clear();
	}

	public void addBeforeMessage(String message) {
		beforeMessage.add(message);
	}

	public void addAfterMessage(String message) {
		afterMessage.add(message);
	}

}
