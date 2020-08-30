package logic.controller.maps;

public enum RoutingHereParameter {

	TRANSPORT("transportMode", "car"), RETURN("return", "summary");

	private String name;
	private String value;

	private RoutingHereParameter(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	public String getParameter() {
		return "&" + this.name + "=" + this.value;
	}

}
