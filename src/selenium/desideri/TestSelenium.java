package selenium.desideri;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import logic.bean.UserBean;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.model.Student;
import logic.view.mysql.MySqlDAO;
import logic.view.mysql.UniDAO;

public class TestSelenium {
	private static final String USER_ID = "0456789";
	
	@Test
	public void test() throws InvalidInputException, DatabaseException {
		System.setProperty("webdriver.chrome.driver", "Driver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("http://localhost:8081/TorVerCar/index.jsp");
		WebDriverWait wait = new WebDriverWait(driver, 3);
		
		//Open registration dialog
		driver.findElement(By.xpath("//*[@id=\"navbar\"]/a[3]")).click();

		//Insert UserID
		WebElement loginfield = driver.findElement(By.xpath("//*[@id=\"userID\"]")); 
		wait.until(ExpectedConditions.visibilityOf(loginfield));  
		loginfield.sendKeys(USER_ID);
		
		//Load UniDB infos
		driver.findElement(By.xpath("//*[@id=\"btnCheck\"]")).click();
		
		//Get Email
		WebElement email = driver.findElement(By.xpath("//*[@id=\"email\"]"));
		wait.until(ExpectedConditions.visibilityOf(email));  
				
		//Test
		UniDAO uniDb = new UniDAO();
		UserBean user = uniDb.getByUserID(USER_ID);
		assertEquals(user.getEmail(), email.getAttribute("placeholder"));
		driver.close();
	}

}
