package application;

import java.util.HashMap;

public abstract class SuperController {

	private HashMap<String, Object> appPaneMap;
	private HashMap<String, SuperController> appControllerMap;
	
	public HashMap<String, Object> getAppPaneMap() {
		return appPaneMap;
	}
	public void setAppPaneMap(HashMap<String, Object> appPaneMap) {
		this.appPaneMap = appPaneMap;
	}
	public HashMap<String, SuperController> getAppControllerMap() {
		return appControllerMap;
	}
	public void setAppControllerMap(HashMap<String,SuperController> controllerMap) {
		this.appControllerMap = controllerMap;
	}
}
