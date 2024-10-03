package pages;

import com.web.core.enums.WaitStrategy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

import static com.web.core.manager.driver.DriverManager.getDriver;

public class ViewPriceSummaryPage extends BasePage {
    public ViewPriceSummaryPage(WebDriver driver) {
        super(driver);
    }


  By priceSummaryPerAdult = By.xpath ("//div[@class='ffOuterFooter']//span");
   By ContinueButton=By.xpath("//button[text()='CONTINUE']");

    By BookingNow=By.xpath("//button[text()='BOOK NOW']");
    public int getPriceSummaryPerAdult() throws InterruptedException, IOException {
        String price = getTextOfElement(priceSummaryPerAdult, WaitStrategy.PRESENCE, "priceSummaryPerAdult");
        String numericValue = price.replaceAll("[^0-9]", "");
        int card_price = Integer.parseInt(numericValue);
        return card_price;
    }


    public ViewPriceSummaryPage clickContinue() throws InterruptedException, IOException {


        moveToElementAndClick(ContinueButton,WaitStrategy.CLICKABLE,"ContinueButton");
        return this;
    }


    public BookingFormPage clickBookingNow() throws InterruptedException, IOException {


        click(BookingNow,WaitStrategy.CLICKABLE,"BookingNow");
        return new BookingFormPage(getDriver());
    }
}
