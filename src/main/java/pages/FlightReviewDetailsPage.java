package pages;

import com.web.core.enums.WaitStrategy;
import com.web.core.factories.ExplicitWaitFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.web.core.manager.driver.DriverManager.getDriver;

public class FlightReviewDetailsPage extends BasePage {
    public FlightReviewDetailsPage(WebDriver driver) {
        super(driver);
    }


    By pop=By.xpath("//h3[text()='Review Details']");

    public void  validateTheFlightReviewDetailsPopExist() throws InterruptedException, IOException {


    }

    public List<String> checkFlightReviewDetails() throws InterruptedException, IOException {

        By information = By.xpath("//div[@class='reviewDtlsOverlayContent customScroll']/div//span[2]");
        ExplicitWaitFactory.performExplicitWait(WaitStrategy.VISIBLE, information);

        List<WebElement> elements = driver.findElements(information);
        List<String> list = new ArrayList<>();
        for(WebElement el: elements){

            String text = el.getText();
            System.out.println(text);
            list.add(text);


        }
      return list;
    }


}
