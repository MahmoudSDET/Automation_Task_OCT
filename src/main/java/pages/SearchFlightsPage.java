package pages;

import com.web.core.enums.WaitStrategy;
import com.web.core.utils.DateUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

import static com.web.core.manager.driver.DriverManager.getDriver;

public class SearchFlightsPage extends BasePage {


    public SearchFlightsPage(WebDriver driver) {
        super(driver);
    }

    By AddOnsPopUp = By.xpath("//div[@id='SW']/div/div[2]/div[2]/div/section/span");
    By clickFrom = By.id("fromCity");
    By Departure_Input = By.xpath("//input[@type = 'text' and @placeholder = 'From']");

    By clickTo = By.id("toCity");
    By Destination_Input = By.xpath("//input[@type = 'text' and @placeholder = 'To']");
By clickReturnDate=By.xpath("//span[text()='Return']");
By  clickNumberOfPassengers=By.xpath("//div[@data-cy='flightTraveller']");
By ApplyButton=By.xpath("//button[text()='APPLY']");
By clickSearch=By.xpath("//p/a[text()='Search']");
    @Step
    public SearchFlightsPage loadUrl(String url) throws IOException {
        load(url);
        return this;
    }
    public SearchFlightsPage handleAddOnsPopUp() throws InterruptedException, IOException {

        click(AddOnsPopUp,WaitStrategy.CLICKABLE,"AddOnsPopUp");
        return this;
    }

    public SearchFlightsPage selectDepartureCity(String city) throws IOException {

        click(clickFrom,WaitStrategy.VISIBLE,"clickFrom");
        sendKeys(Departure_Input,city,WaitStrategy.CLICKABLE,"Departure_Input");
        By cityElement = By.xpath("//li[@role='option']//div//p[contains(text(),'" + city + "')]");
        click(cityElement,WaitStrategy.CLICKABLE,"cityElement");
        return this;
    }



    public SearchFlightsPage selectDirectionCity(String city) throws IOException {

        click(clickTo,WaitStrategy.CLICKABLE,"clickTo");
        sendKeys(Destination_Input,city,WaitStrategy.CLICKABLE,"Destination_Input");
        By cityElement = By.xpath("//li[@role='option']//div//p[contains(text(),'" + city + "')]");
        click(cityElement,WaitStrategy.CLICKABLE,"cityElement");
        return this;
    }

    public SearchFlightsPage selectFromDate(int number_of_days) throws IOException {
    int currentDays=DateUtils.addingDaysToCurrentDate(String.valueOf(number_of_days));
     By Date=By.xpath("//p[text()= '" + currentDays + "']");
     click(Date,WaitStrategy.CLICKABLE,"FromDate");
        return this;
    }


    public SearchFlightsPage selectToDate(int number_of_days) throws IOException, InterruptedException {
     click(clickReturnDate,WaitStrategy.CLICKABLE,"clickReturnDate");

       int number_days= DateUtils.addingDaysToCurrentDate(String.valueOf(number_of_days));
        By Date=By.xpath("//p[text()= '" + number_days + "']");

        moveToElementAndClick(Date,WaitStrategy.CLICKABLE,"ToDate");
        return this;
    }

    public SearchFlightsPage selectTheNumberOfPassengers(int passengers) throws IOException, InterruptedException {
     moveToElementAndClick(clickNumberOfPassengers,WaitStrategy.CLICKABLE,"clickNumberOfPassengers");
     By NumberOfPassengers=By.xpath("//li[@data-cy='adults-" + passengers +"']");
 click(NumberOfPassengers,WaitStrategy.CLICKABLE,"NumberOfPassengers");
moveToElementAndClick(ApplyButton,WaitStrategy.CLICKABLE,"ApplyButton");
        return this;
    }

public FilterFlightsResultsPage clickSearch() throws IOException {
click(clickSearch,WaitStrategy.CLICKABLE,"clickSearch");

        return new FilterFlightsResultsPage(getDriver());
}
}
