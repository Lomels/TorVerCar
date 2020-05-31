package logic.controller.maps;

public enum ViewRouteHereParameter {

	STYLE("t", "0"),
	WIDTH("w", "512"),
	HEIGHT("h","512"),
	;
	
	private String name, value;
	
	private ViewRouteHereParameter(String name, String value){
		this.name = name;
		this.value = value;
	}
	
	public String getParameter() {
		return "&" + this.name + "=" + this.value;
	}
}
