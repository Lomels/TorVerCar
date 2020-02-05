package Entity;

public class Report {
	private String message;
	private StudentCar target;
	private Boolean resolved;
	private Admin handler;
	
	public Report(String message, StudentCar target, Admin handler) {
		this.message = message;
		this.target = target;
		this.resolved = false;
		this.handler = handler;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		if(message.length()<1) {
			return;
		}
		this.message = message;
	}

	public StudentCar getTarget() {
		return target;
	}

	public void setTarget(StudentCar target) {
		if(target == null) {
			return;
		}
		this.target = target;
	}

	public Boolean getResolved() {
		return resolved;
	}

	public void setResolved(Boolean resolved) {
		this.resolved = resolved;
	}

	public Admin getHandler() {
		return handler;
	}

	public void setHandler(Admin handler) {
		if(handler == null) {
			return;
		}
		this.handler = handler;
	}
	
}
