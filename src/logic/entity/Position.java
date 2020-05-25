package logic.entity;

public class Position {

	private Double lat;
	private Double lon;
	private Double score;
	private String address;

	public Position(Double lat, Double lon, Double score, String address) {
		super();
		this.setLat(lat);
		this.setLon(lon);
		this.score = score;
		this.address = address;
	}

	public Double getLat() {
		return lat;
	}

	public Double getLon() {
		return lon;
	}

	public Double getScore() {
		return score;
	}

	public String getAddress() {
		return address;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Position [lat=" + lat + ", lon=" + lon + ", (lat,lon)=" + lat + "," + lon + ",\nscore=" + score
				+ ",\nlongAddress=" + address + "]";
	}

}
