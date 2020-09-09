package selenium.marseglia;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import logic.controller.exception.DatabaseException;
import logic.model.StudentCar;
import logic.view.mysql.MySqlDAO;


class TestSelenium {
	private static final String USER_ID = "0252379";

	@Test
	void test() throws DatabaseException {
		System.setProperty("webdriver.chrome.driver", "Driver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("http://localhost:8081/TorVerCar/index.jsp");
		WebDriverWait wait = new WebDriverWait(driver, 3);

		// Open login dialog
		driver.findElement(By.xpath("//*[@id=\"navbar\"]/a[4]")).click();

		// UserID
		WebElement loginfield = driver.findElement(By.xpath("//*[@id=\"loginDialog\"]/form/div/input[1]"));
		wait.until(ExpectedConditions.visibilityOf(loginfield));
		loginfield.sendKeys(USER_ID);

		// Password
		WebElement pswfield = driver.findElement(By.xpath("//*[@id=\"myPass\"]"));
		pswfield.sendKeys("aaaAAA123@");

		// Login
		driver.findElement(By.xpath("//*[@id=\"loginDialog\"]/form/div/button[2]")).click();

		// Open myCar
		WebElement myCar = driver.findElement(By.xpath("//*[@id=\"navbar\"]/a[5]"));
		wait.until(ExpectedConditions.visibilityOf(myCar));
		myCar.click();
		
		// Get plate
		WebElement plate = driver.findElement(By.xpath("//*[@id=\"myPlate\"]"));
		
		// Test
		MySqlDAO ourDb = new MySqlDAO();
		StudentCar user = ourDb.loadStudentCarByUserID(USER_ID);
		assertEquals(user.getCarInfo().getPlate(), plate.getAttribute("value"));
		driver.close();
	}

}
