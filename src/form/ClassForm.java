package form;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

/* 
Automate following Test Steps:
1.	Open http://demoqa.com/automation-practice-form
2.	Enter FistName, LastName, Email
3.	Verify the number of radio buttons  is equal to 3 
4.	Verify that by default none of radio button is selected
5.	Select Female as gender ZADACA
6.	Enter phone Number ZADACA 
7.	Enter Date Of Birth ZADACA
8.	Fill in Subject Zadava
9.	Verify that number of checkboxes is 3 ZADACA
10.	Verify that all checkbox are unchecked ZADAVA
11.	Check Sport and Music ZADACA
12.	Upload file
13.	Enter Current Address 
14.	Verify that select city is disabled
15.	Verify  the content of State dropdown
16.	Select the state NCR and Gurgaon
17.	Click on Submit button
18.	Get student name from modal
19.	Close modal

*/
public class ClassForm {

	private WebDriver driver;

	public static void main(String[] args) {

		ClassForm formy = new ClassForm();
		formy.SetUp();
		formy.fillInField("firstName", "Arijana");
		formy.fillInField("lastName", "Arijana");
		formy.fillInField("userEmail", "arijana@gmail.com");
		int number = formy.getAllRadioBtn("radio");
		Assert.assertEquals(number, 3, "Number of radiobuttons is not 3");
		boolean sel = formy.isRadioSelected("radio");
		Assert.assertFalse(sel, "Radio button is selected by default but it should not be");
		formy.clickRadioBtn("Female");
		formy.fillInField("userNumber", "1234567890");
		formy.selectDOB("22 May 1992");
		String selcDate = formy.getDOB();
		System.out.println("Selected date is: " + selcDate);
		formy.selectSubject("subjectsInput", "Computer Science");
		int number1 = formy.getAllCheckBox("checkbox");
		Assert.assertEquals(number1, 3, "Number of Check Boxes is not 3");
		boolean sel1 = formy.isCheckBoxSelected("checkbox");
		Assert.assertFalse(sel1, "CheckBox is selected by default but it should not be");
		formy.clickCheckBox("Sports");
		formy.clickCheckBox("Music");
		formy.uploadFile("pit.jpg");
		formy.fillInField("currentAddress", "Adresa1");
		boolean city = formy.isCityDisabled("city");
		Assert.assertTrue(city, "City element is enabled but is should not be");
		formy.stateDropdown("state");

		// formy.clickSubmitBtn("submit");

	}

	public void SetUp() {

		String dir = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", dir + "\\executable\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("http://demoqa.com/automation-practice-form");

	}

	public void fillInField(String locator, String value) {

		driver.findElement(By.id(locator)).sendKeys(value);

	}

	public int getAllRadioBtn(String attValue) {

		List<WebElement> radioList = driver.findElements(By.cssSelector("[type=\"" + attValue + "\"]"));

		System.out.println("Number of radio buttons is:" + radioList.size());

		return radioList.size();
	}

	public boolean isRadioSelected(String attValue) {

		List<WebElement> radioList = driver.findElements(By.cssSelector("[type=\"" + attValue + "\"]"));

		for (int i = 0; i < radioList.size(); i++) {

			if (radioList.get(i).isSelected()) {

				return true;
			}
		}
		return false;

	}

	public void clickRadioBtn(String gender) {
		driver.findElement(By.xpath("//label[contains(.,\"" + gender + "\")]")).click();
	}

	public void selectDOB(String dateValue) {

		driver.findElement(By.id("dateOfBirthInput")).click();

		String[] date = dateValue.split(" ");
		String day = date[0];
		String month = date[1];
		String year = date[2];

		System.out.println("dan" + day);

		Select selMonth = new Select(driver.findElement(By.className("react-datepicker__month-select")));

		selMonth.selectByVisibleText(month);

		Select selYear = new Select(driver.findElement(By.className("react-datepicker__year-select")));

		selYear.selectByValue(year);

		List<WebElement> activeDay = driver.findElements(By.xpath(
				"//div[starts-with(@class,\"react-datepicker__day react-datepicker__day\")][not(contains(@class, \"react-datepicker__day--outside-month\"))]"));

		for (int i = 0; i < activeDay.size(); i++) {
			if (activeDay.get(i).getText().contentEquals(day)) {
				activeDay.get(i).click();
				break;
			}
		}

	}

	public String getDOB() {
		return driver.findElement(By.id("dateOfBirthInput")).getAttribute("value");
	}

	public void selectSubject(String elementId, String subject) {
		driver.findElement(By.id(elementId)).sendKeys(subject);

		driver.findElement(By.id(elementId)).sendKeys(Keys.ENTER);

	}

	public int getAllCheckBox(String attValue) {

		List<WebElement> checkList = driver.findElements(By.cssSelector("[type=\"" + attValue + "\"]"));

		System.out.println("Number of check boxes is:" + checkList.size());

		return checkList.size();

	}

	public boolean isCheckBoxSelected(String attValue) {

		List<WebElement> checkList = driver.findElements(By.cssSelector("[type=\"" + attValue + "\"]"));

		for (int i = 0; i < checkList.size(); i++) {

			if (checkList.get(i).isSelected()) {

				return true;
			}
		}
		return false;

	}

	public void clickCheckBox(String checkbox) {
		driver.findElement(By.xpath("//label[contains(.,\"" + checkbox + "\")]")).click();
	}

	public void uploadFile(String fileName) {
		driver.findElement(By.id("uploadPicture")).sendKeys(System.getProperty("user.dir") + "\\file\\" + fileName);
	}

	
	 public boolean isCityDisabled(String elementID) { return
	 driver.findElement(By.id(elementID)).isEnabled();
	  
	  }
	 

	public void stateDropdown(String elementID) {

	 driver.findElement(By.id(elementID)).click();
		
		/*Select select = new Select();
		List<WebElement> elements = select.getOptions();
		
		boolean found = false;
		for (WebElement ele:elements)
		{
			if (strSearch.equals(ele.getText()))
			{
				found=true;
				break;
			}*/
		}
		

	
	 public void clickSubmitBtn(String elementId) {
	

	 WebElement element = driver.findElement(By.id("elementID"));
	 ((JavascriptExecutor)
	 driver).executeScript("arguments[0].scrollIntoView(true);", element);
	 
	 element.click(); }
	 
}
