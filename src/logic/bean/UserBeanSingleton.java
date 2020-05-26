package logic.bean;

public class UserBeanSingleton {
	private static UserBeanSingleton instance = null;
	private UserBean userBean;
	private String code;
	
	private UserBeanSingleton() {}
	
	public static UserBeanSingleton getInstance() {
		if(instance == null)
			instance = new UserBeanSingleton();
		return instance;
	}
	
	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}
	
	public UserBean getUserBean() {
		return this.userBean;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
