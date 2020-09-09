package selenium.lomele;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.model.Student;
import logic.view.mysql.MySqlDAO;


public class TestSelenium {
	private static final String USER_ID = "0241118";
	
	@Test
	public void test() throws DatabaseException, InvalidInputException{
		System.setProperty("webdriver.chrome.driver", "Driver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("http://localhost:8081/TorVerCar/index.jsp");
		WebDriverWait wait = new WebDriverWait(driver, 3); 
		
		//Open login dialog
		driver.findElement(By.xpath("//*[@id=\"navbar\"]/a[4]")).click();
		
		//UserID
		WebElement userid = driver.findElement(By.xpath("//*[@id=\"loginDialog\"]/form/div/input[1]"));
		wait.until(ExpectedConditions.visibilityOf(userid));  
		userid.sendKeys(USER_ID);
		//password
		driver.findElement(By.xpath("//*[@id=\"myPass\"]")).sendKeys("aaaAAA123@");
				
		//Login
		driver.findElement(By.xpath("//*[@id=\"loginDialog\"]/form/div/button[2]")).click();
		
		//Open profile
		WebElement profile = driver.findElement(By.xpath("//*[@id=\"navbar\"]/a[7]"));
		wait.until(ExpectedConditions.visibilityOf(profile));
		wait.until(ExpectedConditions.elementToBeClickable(profile)); 
		profile.click();
		
		//Get phone
		WebElement phoneField = driver.findElement(By.xpath("//*[@id=\"myPhoneNum\"]"));
		
		//Test the result
		MySqlDAO ourDb = new MySqlDAO();
		Student user = ourDb.loadStudentByUserID(USER_ID);
		assertEquals(user.getPhone(), phoneField.getAttribute("value"));
		driver.close();

	}
}
