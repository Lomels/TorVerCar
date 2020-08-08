package logic.controller.maps;

public enum ViewMapHereParameter {

	STYLE("t", "0"),
	MAPTYPE("vt", "0"),
	WIDTH("w", "512"), 
	HEIGHT("h", "512"),
	PICTURE_IN_PICTURE("pip","13")
	;

	private String name, value;

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