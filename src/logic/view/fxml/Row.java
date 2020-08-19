package logic.view.fxml;

public class Row {

	private String address;
	private String URL;

	public Row(String address, String uRL) {
		super();
		this.address = address;
		this.URL = uRL;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

}
