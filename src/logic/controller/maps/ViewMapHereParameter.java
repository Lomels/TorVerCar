	package logic.controller.maps;

public enum ViewMapHereParameter {

	STYLE("t", "0"),
	MAPTYPE("vt", "0"),
	WIDTH("w", "256"), 
	HEIGHT("h", "180"),
	PICTURE_IN_PICTURE("pip","13")
	;

	private String name;
	private String value;

	private ViewMapHereParameter(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public String getParameter() {
		return "&" + this.name + "=" + this.value;
	}

}
