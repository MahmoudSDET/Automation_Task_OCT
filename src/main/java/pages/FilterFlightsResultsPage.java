package pages;

import com.web.core.enums.WaitStrategy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

import static com.web.core.manager.driver.DriverManager.getDriver;

public class FilterFlightsResultsPage extends BasePage {
    public FilterFlightsResultsPage(WebDriver driver) {
        super(driver);
    }


    By popUp = By.xpath("//div[@class='commonOverlay ']/span");

    static By F_price = By.xpath("(//div[contains(@class,'priceSection')])[1]");

    By searchGridResults = By.xpath("//div[@class='listingCard  appendBottom5']/div[2]/div[@class='airlineListingInfo rt']//p");
    By clickViewSummary=By.xpath("//div[contains(@class,'priceSection')]//button");
    public FilterFlightsResultsPage filterFlights(String flightType, String AirPort) throws InterruptedException, IOException {
       try {
           click(popUp, WaitStrategy.VISIBLE, "popUp");
       } catch (Exception e) {
           //do nothing
       }
        By flight_Type = By.xpath("(//p[contains(text(),'" + flightType + "')]/parent::div/parent::label//input)[1]");

        moveToElementAndClick(flight_Type, WaitStrategy.PRESENCE, "flightType");

        By Airport = By.xpath("(//p[contains(text(),'" + AirPort + "')]/parent::div/parent::label//input)[1]");

        moveToElementAndClick(Airport, WaitStrategy.PRESENCE, "Airport");


        return this;
    }

    public FilterFlightsResultsPage checkFlightAfterFilter(String AirPort) throws InterruptedException, IOException {
        checkTheExistingTextOfElement(searchGridResults, WaitStrategy.PRESENCE, AirPort, "searchGridResults");
        return this;
    }

    public static int card_price;
    public  int getPrice() throws InterruptedException, IOException {
   String flight_price = getTextOfElement(F_price, WaitStrategy.PRESENCE, "price");
    String numericValue = flight_price.replaceAll("[^0-9]", "");

    // Convert the cleaned string to an integer (or long if the value is large)
        card_price= Integer.parseInt(numericValue);
    return card_price;
}
    public ViewPriceSummaryPage viewPriceSummary() throws InterruptedException, IOException {
    click(clickViewSummary,WaitStrategy.CLICKABLE,"clickViewSummary");

        return new ViewPriceSummaryPage(getDriver());
    }
}