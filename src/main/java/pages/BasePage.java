
package pages;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.google.common.util.concurrent.Uninterruptibles;
import com.web.core.constants.FrameworkConstants;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import com.web.core.manager.driver.DriverManager;
import com.web.core.enums.WaitStrategy;
import com.web.core.factories.ExplicitWaitFactory;
import com.web.core.reports.ExtentLogger;
import com.web.core.reports.ExtentManager;
import com.web.core.utils.ConfigLoader;
import com.web.core.utils.ScreenshotUtils;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.web.core.constants.FrameworkConstants.ICON_Navigate_Right;
import static com.web.core.constants.FrameworkConstants.WAIT;

public class BasePage {

	/* Class level -> Not Thread safe */
	/*
	 * No need to use ThreadLocal, because when we are creating the Object of Page,
	 * those objects are local to test methods.
	 */
	protected WebDriver driver;
	protected WebDriverWait wait;
	private final String DEFAULT_ELEMENT_TIME_OUT_MESSAGE = "Time out... Element is not found...";
	private final String DEFAULT_ALERT_TIME_OUT_MESSAGE = "Time out... Alert is not found...";
	static String currentWindow = "";
	/*
	 * Many waits can also be used in case you want to different time wait for
	 * different web elements
	 */
//	protected WebDriverWait waitLong;
//	protected WebDriverWait waitShort;

	public BasePage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(FrameworkConstants.getExplicitWait()));
//		waitLong = new WebDriverWait(driver, 25);
//		waitShort = new WebDriverWait(driver, 5);

	}

	public void load(String URL) throws IOException {
		// driver.get("https://askomdch.com/" + endPoint);
		driver.get(URL);
		ExtentLogger.info(ICON_Navigate_Right + "  Navigating to : <b>"
				+ URL + "</b>");
	}



	public void waitForOverlaysToDisappear(By overlay) {
		List<WebElement> overlays = driver.findElements(overlay);
		System.out.println("OVERLAY SIZE" + overlays.size());
		if (overlays.size() > 0) {
			wait.until(ExpectedConditions.invisibilityOfAllElements(overlays));
			System.out.println("OVERLAY INVISIBLE NOW");
		} else {
			System.out.println("OVERLAY NOT FOUND");
		}
	}

	protected void click(By by, WaitStrategy waitStrategy, String elementName) throws IOException {
		// DriverManager.getDriver().findElement(by).click();
		ExplicitWaitFactory.performExplicitWait(waitStrategy, by).click();
		ExtentLogger.pass("<b>" + elementName + "</b> is clicked", true);
		// log(PASS,elementName+" is clicked");
	}



	protected void clickWithJavaScriptExecutor(By by, WaitStrategy waitStrategy, String elementName) throws IOException {
		// DriverManager.getDriver().findElement(by).click();
		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("arguments[0].click();", ExplicitWaitFactory.performExplicitWait(waitStrategy, by));
		ExtentLogger.pass("<b>" + elementName + "</b> is clicked", true);
		// log(PASS,elementName+" is clicked");
	}
	protected void removeTransparentOverlay() throws IOException {
		// DriverManager.getDriver().findElement(by).click();
		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("document.querySelector('.transparentOverlay').style.display='none';");

		// log(PASS,elementName+" is clicked");
	}

	protected void sendKeys(By by, String value, WaitStrategy waitStrategy, String elementName) throws IOException {
		ExplicitWaitFactory.performExplicitWait(waitStrategy, by).sendKeys(value);
		ExtentLogger.pass("<b>" + value + "</b> is entered successfully in <b>" + elementName + "</b>", true);
		// log(PASS,value +" is entered successfully in "+elementName);
	}

	protected void sendKeysWithKeys(By by, Keys value, WaitStrategy waitStrategy, String elementName) throws IOException {
		ExplicitWaitFactory.performExplicitWait(waitStrategy, by).sendKeys(value);
		ExtentLogger.pass("<b>" + value + "</b> is entered successfully in <b>" + elementName + "</b>", true);
		// log(PASS,value +" is entered successfully in "+elementName);
	}

	protected void clear(By by, WaitStrategy waitStrategy, String elementName) {
		ExplicitWaitFactory.performExplicitWait(waitStrategy, by).clear();
		ExtentLogger.info("Clearing the field  <b>" + elementName + "</b>");
		// log(PASS,value +" is entered successfully in "+elementName);
	}

	protected void clearAndSendKeys(By by, String value, WaitStrategy waitStrategy, String elementName) throws IOException {
		WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
		element.clear();
		element.sendKeys(value);
		ExtentLogger.pass("<b>" + value + "</b> is entered successfully in <b>" + elementName + "</b>", true);
		// log(PASS,value +" is entered successfully in "+elementName);
	}
/*
making scroll to element
 */
protected void scrollToElementsByJavaScriptExecutor(By by, WaitStrategy waitStrategy, String elementName) throws IOException {
	WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
	JavascriptExecutor js = (JavascriptExecutor) driver;
	js.executeScript("arguments[0].scrollIntoView(true);", element);
	ExtentLogger.pass("The Page is scrolled successfully into <b>" + elementName + "</b>", true);

}

	protected void selectElementByValue(By by, String value, WaitStrategy waitStrategy, String elementName) throws IOException {
		WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
		Select select=new Select(element);
		select.selectByValue(value);
		ExtentLogger.pass("<b>" + value + "</b> is selected in <b>" + elementName + "</b>", true);

	}
	public List<WebElement> getDropDownOptionsList(By by,WaitStrategy waitStrategy,String elementName) {
		WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
		Select select = new Select(element);
		ExtentLogger.info("The elements from dropdown list"+elementName+ "are returned");
		return select.getOptions();

	}

	protected void  moveToElementToSetText(By by, WaitStrategy waitStrategy,String value) throws InterruptedException, IOException {
		WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
		Actions act = new Actions(driver);
		act.moveToElement(element).click().sendKeys(value).perform();
		ExtentLogger.pass("<b>" + value + "</b> is entered successfully in <b>" + "input field" + "</b>", true);

	}
	protected void  moveToElementAndClickOnInsideElement(By parentMenuLocator, WaitStrategy waitStrategy,By SubMenuLocator) throws InterruptedException, IOException {
		WebElement parent_menu = ExplicitWaitFactory.performExplicitWait(waitStrategy, parentMenuLocator);
		Actions act = new Actions(driver);
		act.moveToElement(parent_menu).perform();
		ExtentLogger.pass("<b>" + parent_menu.getText()+" is moved" + "</b>", true);
		WebElement sub_menu=ExplicitWaitFactory.performExplicitWait(waitStrategy, SubMenuLocator);
		sub_menu.click();
		ExtentLogger.pass("<b>" + sub_menu.getText()+" is clicked" + "</b>", true);
	}


	protected void  moveToElementAndClick(By parentMenuLocator, WaitStrategy waitStrategy,String elementName) throws InterruptedException, IOException {
		WebElement parent_menu = ExplicitWaitFactory.performExplicitWait(waitStrategy, parentMenuLocator);
		Actions act = new Actions(driver);
		act.moveToElement(parent_menu).perform();
		parent_menu.click();

		ExtentLogger.pass("<b>" + elementName +" is moved" + "</b>", true);

	}
	protected static String getTextOfElement(By by, WaitStrategy waitStrategy, String elementName) throws IOException {

		WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
		String text=element.getText();
		ExtentLogger.pass("<b>" + "The text value of element "+elementName+" is "+text + "</b> is returned", true);
		return text;
	}


	protected void setValueOfElement(By by, Object newvalue, WaitStrategy waitStrategy, String elementName) throws IOException, InterruptedException {
		WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, by);

		JavascriptExecutor js = (JavascriptExecutor) driver;
		Thread.sleep(1000);
		js.executeScript("arguments[0].setAttribute('value', '"+newvalue+"');", element);
		ExtentLogger.pass("<b>" + newvalue + "</b> is entered successfully in <b>" + elementName + "</b>", true);


	}
	protected  int getElementCount(By by,WaitStrategy waitStrategy,String elementName) throws IOException {
		ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
		List<WebElement> elements = driver.findElements(by);
		int elements_count= (int) elements.stream().count();
		ExtentLogger.pass("<b>" + "The count  of element "+elementName+" is "+elements_count + "</b> is returned", true);
		return elements_count;
	}

	protected void searchForTextInListOfElementsAndClick(By by,WaitStrategy waitStrategy,String text,String elementName) throws IOException {
	try {
		ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
		List<WebElement> elements = driver.findElements(by);
	    Optional<WebElement> optional=elements.stream().filter(e -> e.getText().equals(text)).findFirst();
	     optional.get().click();
		ExtentLogger.pass("<b>" + "The element " + elementName + " is Found within the List of elements and clicked"+"</b>", true);
	}catch (NoSuchElementException | IOException ex){
		ExtentLogger.fail("<b>" + "The element " + elementName + " is  not Found within the List of elements and not clicked"+"</b>", true);
	}
	}

	protected  boolean checkTheExistingTextOfElement(By by,WaitStrategy waitStrategy,String text,String elementName) throws IOException {
		boolean isFound=false;

	try {
			ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
			List<WebElement> elements = driver.findElements(by);
	     isFound=elements.stream().anyMatch(e->e.getText().equals(text));
			ExtentLogger.pass("<b>" + "The element " + elementName + " is Found within the List of elements and clicked"+"</b>", true);
		}catch (NoSuchElementException | IOException ex){
			ExtentLogger.fail("<b>" + "The element " + elementName + " is  not Found within the List of elements and not clicked"+"</b>", true);
		}
	return isFound;
	}



	protected Alert waitForJSAlertWithFluentWait(int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(NoAlertPresentException.class)
				.withMessage(DEFAULT_ALERT_TIME_OUT_MESSAGE);
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	protected Alert waitForJSAlert(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	protected String getAlertText(int timeOut) {
		return waitForJSAlert(timeOut).getText();
	}

	protected void acceptAlert(int timeOut) {
		waitForJSAlert(timeOut).accept();
	}

	protected void dismissAlert(int timeOut) {
		waitForJSAlert(timeOut).dismiss();
	}

	protected void alertSendKeys(int timeOut, String value) {
		waitForJSAlert(timeOut).sendKeys(value);
	}

	protected boolean waitForWindow(int totalNumberOfWindowsToBe, int timeOut) {
		 wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.numberOfWindowsToBe(totalNumberOfWindowsToBe));
	}

	protected void waitForFrameAndSwitchToIt(By frameLocator, int timeOut) {
	     wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}

	protected void waitForFrameAndSwitchToIt(int frameIndex, int timeOut) {
	    wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
	}



	protected void  doDragAndDrop(By source,By destination ,WaitStrategy waitStrategy,String elementName) throws InterruptedException, IOException {
		WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, source);
		WebElement target = ExplicitWaitFactory.performExplicitWait(waitStrategy, destination);
		Actions act = new Actions(driver);
		act.dragAndDrop(element,target).perform();
		ExtentLogger.pass("<b>" + elementName + "</b> is dropped successfully", true);
	}
	protected void  doRightClick(By by, WaitStrategy waitStrategy,String elementName) throws InterruptedException, IOException {
		WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
		Actions act = new Actions(driver);
		act.contextClick(element).perform();
		ExtentLogger.pass("<b>" + elementName + "</b> is right clicked", true);
	}

	protected void drawSignature(By by,WaitStrategy waitStrategy,String elementName) throws IOException {
		WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
		Actions act = new Actions(driver);

		Action signature = act.click(element)
				.moveToElement(element, 30, 10)
				.clickAndHold(element)
				.moveToElement(element, -200, -50)
				.moveByOffset(50, -50)
				.moveByOffset(50, -50)
				.moveByOffset(3, 3)
				.release(element)
				.build();

		signature.perform();

		ExtentLogger.pass("<b>" + elementName + "</b> is signed successfully", true);
	}

	protected void handleTheSlidersByActions(By by,WaitStrategy waitStrategy,int targetPosition,String elementName) throws IOException {
		WebElement slider = ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
		Actions act = new Actions(driver);
		int sliderWidth = slider.getSize().getWidth();
		int offset = (int) (sliderWidth * (targetPosition / 100.0)); // Calculate X offset
		act.dragAndDropBy(slider, offset, 0).build().perform();
		ExtentLogger.pass("<b>" + elementName + "</b> is moved successfully to target position", true);
	}

	protected void handleTheSlidersByJavaScript(By by,WaitStrategy waitStrategy,double targetPosition,String elementName) throws IOException {
		WebElement slider = ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		long sliderWidth = (long) js.executeScript("return arguments[0].clientWidth", slider);
		double valueRatio = targetPosition/ 100.0; // Assuming min=0 and max=100
		long handlePosition = Math.round(sliderWidth * valueRatio);
        // Move handle using JavaScript
		js.executeScript("arguments[0].style.left = '" + handlePosition + "px'", slider);
		ExtentLogger.pass("<b>" + elementName + "</b> is moved successfully to target position", true);
	}

	protected void ScrollUp() throws InterruptedException {
		JavascriptExecutor js = ((JavascriptExecutor)driver);
        waitForSomeTime();
        js.executeScript("scroll(0,-250);");
		waitForSomeTime();
		ExtentLogger.info("The window is scrolled up successfully");
	}
	protected void scrollToBottomOfPage() {
		try {
			long Height = Long.parseLong(
					((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight").toString());

			while (true) {
				((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
				waitForGivenTime(5);

				long newHeight = Long.parseLong(
						((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight").toString());
				if (newHeight == Height) {
					break;
				}
				Height = newHeight;
			}
			ExtentLogger.info("The window is scrolled to bottom of page successfully");
		} catch (Exception e) {
			e.printStackTrace();
			ExtentLogger.info("The window is not scrolled to bottom of page successfully");
		}
	}
	protected void getDynamicValue(String searchTerm, By by,WaitStrategy waitStrategy, String valueXpath,String elementName) {
		try {
			WebElement Fieldelement = ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
			Fieldelement.sendKeys(searchTerm);
			driver.findElement((getDynamicXpath(valueXpath, searchTerm))).click();
			ExtentLogger.pass("<b> The Text of " + elementName + "</b> is got", true);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("failed to select the option, check the element");
		}
	}

	  protected void SelectDynamicValue(String optionToSelect, String valueXpath,String elementName) throws IOException {
		try {

			driver.findElement((getDynamicXpath(valueXpath, optionToSelect))).click();
			ExtentLogger.pass("<b>" + elementName + "</b> is clicked", true);
		} catch (Exception e) {
			e.printStackTrace();
			ExtentLogger.pass("<b>" + elementName + "</b> is not clicked", true);
		}
	}
	protected  By getDynamicXpath(String xpath, String data) {

		String rawXapth = xpath.replaceAll("%replaceable%", data);
		return By.xpath(rawXapth);
	}
	  boolean switchToChildWindow() {

		boolean flag = false;

		currentWindow = driver.getWindowHandle();
		try {

			Set<String> allWindows = driver.getWindowHandles();

			for (String actual : allWindows) {

				if (!actual.equalsIgnoreCase(currentWindow)) {

					driver.switchTo().window(actual);
//					test.get().info("Switched to the SignEasy Window");
					flag = true;
				}
			}
			ExtentLogger.info("The child window is switched successfully");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ExtentLogger.info("The child window is not switched successfully");
//
		}
		return flag;
	}
	// Switching back to parent Window
	protected void switchToParentWindow() {
		try {
			driver.switchTo().window(currentWindow);
			ExtentLogger.info("The parent window is switched successfully");
		} catch (Exception e) {
			e.printStackTrace();
			ExtentLogger.info("The parent window is not switched successfully");
		}
	}

	protected void switchToFrameByName(String frameName) {
		try {
			driver.switchTo().frame(frameName);
			ExtentLogger.info("The frame by name is switched successfully");
		} catch (Exception e) {
			e.printStackTrace();
			ExtentLogger.info("The frame by "+frameName+ "not switched successfully");
		}
	}
	protected void switchToFrameByWebElement(By by,WaitStrategy waitStrategy,String elementName) {
		try {
			WebElement frameElement = ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
			driver.switchTo().frame(frameElement);
			ExtentLogger.info("The "+elementName +" frame is switched successfully");
		} catch (Exception e) {
			e.printStackTrace();
			ExtentLogger.info("The "+elementName +" frame is not switched successfully");
		}
	}


	protected void switchToFrameByIndex(int index) {
		try {
			driver.switchTo().frame(index);
			ExtentLogger.info("The frame by index is switched successfully");
		} catch (Exception e) {
			e.printStackTrace();
			ExtentLogger.info("The frame by index is not switched successfully");
		}
	}

	protected void uploadFileWithSendKeys(By by,WaitStrategy waitStrategy,String filePath,String elementName) throws IOException {
		WebElement upload_file = ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
		upload_file.sendKeys(filePath);
		ExtentLogger.pass("<b> The File of " + elementName + "</b> is uploaded successfully", true);
	}


	protected void uploadFileWithJavaScript(By by,WaitStrategy waitStrategy,String filePath,String elementName) throws IOException {
		WebElement upload_file = ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('value', arguments[1])", upload_file, filePath);
		ExtentLogger.pass("<b> The File of " + elementName + "</b> is uploaded successfully", true);
	}

	protected String getElementAttribute(By by,WaitStrategy waitStrategy,String attributeName,String elementName) throws IOException {
		WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
		String attribute_value=element.getAttribute(attributeName);
		ExtentLogger.pass("<b> The value of " + elementName + "</b> is returned successfully with +"+ attribute_value, true);
		return attribute_value;
	}
	protected void searchForAttributeInListOfElementsAndClick(By by,WaitStrategy waitStrategy,String text,String attributeName,String elementName) throws IOException {
		try {
			ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
			List<WebElement> elements = driver.findElements(by);
			Optional<WebElement> optional=elements.stream().filter(e -> e.getAttribute(attributeName).equals(text)).findFirst();
			optional.get().click();
			ExtentLogger.pass("<b>" + "The attribute value of element " + elementName + " is Found within the List of elements and clicked"+"</b>", true);
		}catch (NoSuchElementException | IOException ex){
			ExtentLogger.fail("<b>" + "The attribute value of element " + elementName + " is  not Found within the List of elements and not clicked"+"</b>", true);
		}
	}

	protected  boolean checkTheExistingValueOfElement(By by,WaitStrategy waitStrategy,String attributeName,String text,String elementName) throws IOException {
		boolean isFound=false;

		try {
			ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
			List<WebElement> elements = driver.findElements(by);
			isFound=elements.stream().anyMatch(e->e.getAttribute(attributeName).equals(text));
			ExtentLogger.pass("<b>" + "The attribute value of element " + elementName + " is Found within the List of elements and clicked"+"</b>", true);
		}catch (NoSuchElementException ex){
			ExtentLogger.fail("<b>" + "The attribute value of element " + elementName + " is  not Found within the List of elements and not clicked"+"</b>", true);
		}
		return isFound;
	}

	protected void clickWithShadowRootElements(By by,By shadow,WaitStrategy waitStrategy,String elementName) throws IOException {
		WebElement element=	ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
         SearchContext searchContext=element.getShadowRoot();
		 searchContext.findElement(shadow).click();
		ExtentLogger.pass("<b>" + elementName + "</b> is clicked", true);
	}


	void setTexWithShadowRootElements(By by, By shadow, WaitStrategy waitStrategy, String text, String elementName) throws IOException {
		WebElement element=	ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
		SearchContext searchContext=element.getShadowRoot();
		searchContext.findElement(shadow).sendKeys(text);
		ExtentLogger.pass("<b>" + text + "</b> is entered successfully in <b>" + elementName + "</b>", true);
	}
     protected SearchContext getShadowRootElement(By by,WaitStrategy waitStrategy){
		 WebElement element=	ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
		 SearchContext searchContext=element.getShadowRoot();
		 return searchContext;

	  }


	protected void pressEnter(WebElement we,WaitStrategy waitStrategy,String elementName) throws IOException {
	//	WebElement element=	ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
		wait.until(ExpectedConditions.elementToBeClickable(we));
        we.sendKeys(Keys.RETURN);
		ExtentLogger.pass("<b>" + elementName + "</b> is clicked", true);
	}


	protected WebElement getWebElement(By by,WaitStrategy waitStrategy){
		WebElement element=	ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
		return  element;

	}

	protected String getCurrentURL() {
		return driver.getCurrentUrl();
	}
	protected String getPageTitle() {
		return DriverManager.getDriver().getTitle();
	}



	protected void captureScreenshot() {
		ExtentManager.getExtentTest().info("Capturing Screenshot",
				MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtils.getBase64Image()).build());
	}

	protected void waitForSomeTime() {
		Uninterruptibles.sleepUninterruptibly(WAIT, TimeUnit.SECONDS);
	}

	protected void waitForGivenTime(long time) {
		Uninterruptibles.sleepUninterruptibly(time, TimeUnit.SECONDS);
	}
protected boolean checkTheExistingOfElement(By by,WaitStrategy waitStrategy,String elementName) throws IOException {
return ExplicitWaitFactory.performExplicitWait(waitStrategy, by).isDisplayed();

}}
