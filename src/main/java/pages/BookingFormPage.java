package pages;

import com.web.core.enums.WaitStrategy;
import data_models.AddingAdultsDataModel;
import org.openqa.selenium.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookingFormPage extends BasePage {
    public BookingFormPage(WebDriver driver) {
        super(driver);
    }


    By ContinuePopupButton=By.xpath("//button/font[text()='CONTINUE']");
  By calculatedRevised=By.xpath("//p[@class='fareRevisedText']/font/b");
  By Actual_Total_Price=By.xpath("//span[text()='Total Amount']/following-sibling::span");
    By SomeInformationCheck=By.xpath("//div[@class='importantInfo-userConsent ']//input/parent::span");
   By ClickingWithoutInsurance=By.xpath(" //div[@class='radioboxContent']//span[contains(text(),'without insurance')]/parent::p/parent::div/parent::label//input/parent::span");
   By Adult_One_MiddleName=By.xpath("(//*[@id=\"wrapper_ADULT\"]//input[contains(@placeholder,'Middle Name')])[1]");

   By Adult_One_LastName=By.xpath("(//*[@id=\"wrapper_ADULT\"]//input[contains(@placeholder,'Last Name')])[1]");


    By Adult_Second_MiddleName=By.xpath("(//*[@id=\"wrapper_ADULT\"]//input[contains(@placeholder,'Middle Name')])[2]");

    By Adult_Second_LastName=By.xpath("(//*[@id=\"wrapper_ADULT\"]//input[contains(@placeholder,'Last Name')])[2]");
    By DayInput=By.xpath("(//div[@class='adultItemSelect']//div[text()='Date'])[1]/parent::div/parent::div//div[2]//input");

    By Passport_DayField=By.xpath("(//div[@class='adultItemSelect']//div[text()='Date'])[1]");
    By Passport_MonthField=By.xpath("(//div[@class='adultItemSelect']//div[text()='Month'])[1]");
    By Passport_YearField=By.xpath("(//div[@class='adultItemSelect']//div[text()='Year'])[1]");
     By DayField=By.xpath("(//div[@class='adultItemSelect']//div[text()='Date'])[1]");


    By MonthField=By.xpath("(//div[@class='adultItemSelect']//div[text()='Month'])[1]");
    By MonthInput=By.xpath("(//div[@class='adultItemSelect']//div[text()='Month'])[1]/parent::div/parent::div//div[2]//input");

    By YearField=By.xpath("(//div[@class='adultItemSelect']//div[text()='Year'])[1]");
    By YearInput=By.xpath("(//div[@class='adultItemSelect']//div[text()='Year'])[1]/parent::div/parent::div//div[2]//input");
By AddingAdults=By.xpath("//button[contains(text(),'ADD NEW ADULT')]");
By SelectState=By.xpath("//input[@value='Select State']");
By SelectFirstElement=By.xpath("//*[@id=\"BILLING_ADDRESS\"]/div/div[2]/div/ul/li[1]");
By confirmButton=By.xpath("//*[@id=\"BILLING_ADDRESS\"]/div/div[3]/div/span");

By ClickOnContinue=By.xpath("//button[text()='Continue']");

By errorMessage=By.xpath("//p[contains(@class,'errorMsg')]");
    By AddOnsPopUp = By.xpath("//*[@id=\"root\"]/div/div[1]/div/div/div[2]/div/section/span");


By Adult_One_PassportNumber=By.xpath("(//label[text()='Passport No']/parent::div//input)[1]");

By Adult_Second_PassportNumber=By.xpath("(//label[text()='Passport No']/parent::div//input)[2]");
By Adult_One_PassportIssueCountry=By.xpath("//div[text()='Passport Issuing Country']");
By MobileNumber=By.xpath("//*[@id=\"Mobile No\"]/div/input");
By Email=By.xpath("//*[@id=\"Email\"]/div/input");
public static List<AddingAdultsDataModel>addingAdultsDataModels=new ArrayList<>();
    public boolean switchToBookingFormAndValidate(String Airport) throws IOException {
        switchToChildWindow();
        boolean isFound=false;

        try{
            By  AirportElement = By.xpath("//p/span[contains(text(),'" + Airport + "')]");
            isFound=  checkTheExistingTextOfElement(AirportElement,WaitStrategy.PRESENCE,Airport,"AirportElement");

        }catch (Exception e){
            click(ContinuePopupButton, WaitStrategy.PRESENCE,"ContinuePopupButton");


        }
        return isFound;
    }

    public BookingFormPage clickAggreedOnsomeInformationAndBookingWithoutInsurance() throws InterruptedException, IOException {

        try {
         click(SomeInformationCheck,WaitStrategy.CLICKABLE,"SomeInformationCheck");
         Thread.sleep(5000);
         click(ClickingWithoutInsurance,WaitStrategy.CLICKABLE,"ClickingWithoutInsurance");
            Thread.sleep(5000);
        }catch (Exception e){

            click(ContinuePopupButton, WaitStrategy.PRESENCE,"ContinuePopupButton");


        }
        return this;
    }


    public int calculatedIfPriceIncreasedOrNot(int numberOfPassengers) throws InterruptedException, IOException {
    int total_price=0;
        try {

            total_price=(FilterFlightsResultsPage.card_price*numberOfPassengers);


        }catch (Exception e){
            String price = getTextOfElement(calculatedRevised, WaitStrategy.PRESENCE, "calculatedRevised");
            String numericValue = price.replaceAll("[^0-9]", "");
            int Revised_price = Integer.parseInt(numericValue);
            click(ContinuePopupButton, WaitStrategy.PRESENCE,"ContinuePopupButton");
            total_price=(FilterFlightsResultsPage.card_price*numberOfPassengers)+Revised_price;


        }
        return total_price;
    }

    public int getActualTotalPrice() throws InterruptedException, IOException {
        String price = getTextOfElement(Actual_Total_Price, WaitStrategy.PRESENCE, "Actual_Total_Price");
        String numericValue = price.replaceAll("[^0-9]", "");
        int Actual_price = Integer.parseInt(numericValue);
        return Actual_price;
    }

    public BookingFormPage addPassengersInfo(AddingAdultsDataModel addingAdultsDataModel) throws InterruptedException, IOException {
        clickWithJavaScriptExecutor(AddingAdults,WaitStrategy.CLICKABLE,"AddingAdults");

   return this;
    }


    public BookingFormPage fillAdultData    (AddingAdultsDataModel addingAdultsDataModel) throws InterruptedException, IOException {

        if(addingAdultsDataModel.getExpected_Results().equals("Errors")){

         sendKeys(Adult_One_MiddleName,addingAdultsDataModel.getFirstName(),WaitStrategy.VISIBLE,"Adult_One_MiddleName");
          sendKeys(Adult_One_LastName,addingAdultsDataModel.getLastName(),WaitStrategy.VISIBLE,"Adult_One_LastName");
        By Gender=By.xpath("//div[@class='selectTab ']/div/label//span[text()='"+addingAdultsDataModel.getGender()+"']");
        clickWithJavaScriptExecutor(Gender,WaitStrategy.CLICKABLE,"Gender");
        moveToElementAndClick(DayField,WaitStrategy.CLICKABLE,"DayField");

        setValueOfElement(DayInput,(Object) addingAdultsDataModel.getDOB_Day(),WaitStrategy.VISIBLE,"Adult_One_Month");
        sendKeysWithKeys(DayInput, Keys.ENTER,WaitStrategy.VISIBLE,"Day of date birth");
            click(MonthField,WaitStrategy.CLICKABLE,"DayField");
           setValueOfElement(MonthInput,(Object) addingAdultsDataModel.getDOB_Month(),WaitStrategy.VISIBLE,"Adult_One_year");
            sendKeysWithKeys(MonthInput, Keys.ENTER,WaitStrategy.VISIBLE,"Year of date birth");
            click(YearField,WaitStrategy.CLICKABLE,"DayField");

            setValueOfElement(YearInput,(Object) addingAdultsDataModel.getDOB_Year(),WaitStrategy.VISIBLE,"Adult_One_year");
            sendKeysWithKeys(YearInput, Keys.ENTER,WaitStrategy.VISIBLE,"Year of date birth");
            addingAdultsDataModel.setPassportNumber("A2544588");
            sendKeys(Adult_One_PassportNumber,addingAdultsDataModel.getPassportNumber(),WaitStrategy.VISIBLE,"Adult_One_PassportNumber");

            moveToElementAndClick(Adult_One_PassportIssueCountry,WaitStrategy.VISIBLE,"Adult_Second_PassportIssueCountry");
            By selectCountry=By.xpath("//div[text()='"+addingAdultsDataModel.getPassportCountry()+"']");
            moveToElementAndClick(selectCountry,WaitStrategy.CLICKABLE,"PassportCountry");
            addingAdultsDataModel.setPassportExpiryDate_Day("10");

            addingAdultsDataModel.setPassportExpiryDate_Month("Feb");
            addingAdultsDataModel.setPassportExpiryDate_Year("2026");
            moveToElementAndClick(Passport_DayField,WaitStrategy.CLICKABLE,"Adult_One_PassportIssueDateDay");

            By selectPassportDay=By.xpath("//div[text()='"+addingAdultsDataModel.getPassportExpiryDate_Day()+"']");
            moveToElementAndClick(selectPassportDay,WaitStrategy.CLICKABLE,"PassportDay");
            click(Passport_MonthField,WaitStrategy.CLICKABLE,"Adult_One_PassportIssueDateMonth");

            By selectPassportMonth=By.xpath("//div[text()='"+addingAdultsDataModel.getPassportExpiryDate_Month()+"']");
            moveToElementAndClick(selectPassportMonth,WaitStrategy.CLICKABLE,"PassportMonth");

            moveToElementAndClick(Passport_YearField,WaitStrategy.CLICKABLE,"Adult_One_PassportIssueDateYear");

            By selectPassportYear=By.xpath("//div[text()='"+addingAdultsDataModel.getPassportExpiryDate_Year()+"']");
            moveToElementAndClick(selectPassportYear,WaitStrategy.CLICKABLE,"PassportYear");


        }else{


            sendKeys(Adult_Second_MiddleName,addingAdultsDataModel.getFirstName(),WaitStrategy.VISIBLE,"Adult_Second_MiddleName");
            sendKeys(Adult_Second_LastName,addingAdultsDataModel.getLastName(),WaitStrategy.VISIBLE,"Adult_Second_LastName");
            By Gender=By.xpath("(//div[@class='selectTab ']/div/label//span[text()='"+addingAdultsDataModel.getGender()+"'])[2]");
            clickWithJavaScriptExecutor(Gender,WaitStrategy.CLICKABLE,"Gender");
          //  waitForOverlaysToDisappear(By.xpath("//p[@class='overlay']"));
            // scrollToElementsByJavaScriptExecutor(DayField,WaitStrategy.VISIBLE,"DayField");
           // clickWithJavaScriptExecutor(DayField,WaitStrategy.CLICKABLE,"DayField");


            removeTransparentOverlay();
            moveToElementAndClick(DayField,WaitStrategy.CLICKABLE,"DayField");
            By selectDateDay=By.xpath("//div[text()='"+addingAdultsDataModel.getDOB_Day()+"']");
            moveToElementAndClick(selectDateDay,WaitStrategy.CLICKABLE,"selectDateDay");

            moveToElementAndClick(MonthField,WaitStrategy.CLICKABLE,"MonthField");
            By selectDateMonth=By.xpath("//div[text()='"+addingAdultsDataModel.getDOB_Month()+"']");
            moveToElementAndClick(selectDateMonth,WaitStrategy.CLICKABLE,"selectDateMonth");



            moveToElementAndClick(YearField,WaitStrategy.CLICKABLE,"YearField");

            By selectDateYear=By.xpath("//div[text()='"+addingAdultsDataModel.getDOB_Year()+"']");
            moveToElementAndClick(selectDateYear,WaitStrategy.CLICKABLE,"selectDateYear");

            sendKeys(Adult_Second_PassportNumber,addingAdultsDataModel.getPassportNumber(),WaitStrategy.VISIBLE,"Adult_Second_PassportNumber");
            moveToElementAndClick(Adult_One_PassportIssueCountry,WaitStrategy.CLICKABLE,"Adult_Second_PassportIssueCountry");
            By selectCountry=By.xpath("//div[text()='"+addingAdultsDataModel.getPassportCountry()+"']");
            moveToElementAndClick(selectCountry,WaitStrategy.CLICKABLE,"PassportCountry");


            moveToElementAndClick(Passport_DayField,WaitStrategy.CLICKABLE,"Adult_One_PassportIssueDateDay");

            By selectPassportDay=By.xpath("//div[text()='"+addingAdultsDataModel.getPassportExpiryDate_Day()+"']");
            moveToElementAndClick(selectPassportDay,WaitStrategy.CLICKABLE,"PassportDay");
            click(Passport_MonthField,WaitStrategy.CLICKABLE,"Adult_One_PassportIssueDateMonth");

            By selectPassportMonth=By.xpath("//div[text()= '"+addingAdultsDataModel.getPassportExpiryDate_Month()+"']");
            moveToElementAndClick(selectPassportMonth,WaitStrategy.CLICKABLE,"PassportMonth");

            click(Passport_YearField,WaitStrategy.CLICKABLE,"Adult_One_PassportIssueDateYear");

            By selectPassportYear=By.xpath("//div[text()='"+addingAdultsDataModel.getPassportExpiryDate_Year()+"']");
            moveToElementAndClick(selectPassportYear,WaitStrategy.CLICKABLE,"PassportYear");
             moveToElementAndClick(ClickOnContinue,WaitStrategy.CLICKABLE,"Click on Continue");

        }

        addingAdultsDataModels.add(addingAdultsDataModel);


        return this;
    }


    public void clickContinue() throws InterruptedException, IOException {


        clickWithJavaScriptExecutor(ClickOnContinue,WaitStrategy.VISIBLE,"ClickOnContinue");
    }

    public boolean checkErrorMessage() throws InterruptedException, IOException {
        return checkTheExistingOfElement(errorMessage,WaitStrategy.PRESENCE,"errorMessage");
    }

    public void fillFirstAdultAgainAfterCheckingErrorMessage(AddingAdultsDataModel addingAdultsDataModel) throws InterruptedException, IOException {
        Thread.sleep(1000);
        moveToElementAndClick(SelectState,WaitStrategy.CLICKABLE,"SelectState");
        clickWithJavaScriptExecutor(SelectFirstElement,WaitStrategy.CLICKABLE,"SelectFirstElement");
        moveToElementAndClick(confirmButton,WaitStrategy.CLICKABLE,"confirmButton");
        sendKeys(MobileNumber,"9876543210",WaitStrategy.VISIBLE,"MobileNumber");
        sendKeys(Email,"lTjV0@example.com",WaitStrategy.VISIBLE,"EmailId");




    }
}

