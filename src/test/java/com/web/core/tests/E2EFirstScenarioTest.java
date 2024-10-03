package com.web.core.tests;

import com.web.core.annotations.FrameworkAnnotation;
import com.web.core.enums.AuthorType;
import com.web.core.enums.CategoryType;
import com.web.core.reports.ExtentReport;
import com.web.core.utils.ConfigLoader;
import data_models.AddingAdultsDataModel;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;
import testshelpers.AddingAdultInfoDataProviderMethods;

import java.io.IOException;
import java.util.List;

public class E2EFirstScenarioTest extends BaseTest {

    String AirPortName="Air India";
    int numberOfPassengers=2;

    SoftAssert softAssert;
    @FrameworkAnnotation(author = { AuthorType.Ahmed }, category = { CategoryType.SANITY,
            CategoryType.BVT,CategoryType.REGRESSION })
    @Test(groups = { "SANITY" },description = "Search for Flights Test" ,priority = 1)
    public void searchFlightsTest() throws InterruptedException, IOException {
ExtentReport.createTest("Search for Flights Test");
        FilterFlightsResultsPage filterFlightsResultsPage = new SearchFlightsPage(getDriver())
                .loadUrl(ConfigLoader.getInstance().getBaseUrl()).
                handleAddOnsPopUp().selectDepartureCity("Riyadh")
                .selectDirectionCity("Mumbai").selectFromDate(10)
                       .selectToDate(13).selectTheNumberOfPassengers(numberOfPassengers)
                .clickSearch();


    }



    @FrameworkAnnotation(author = { AuthorType.Ahmed }, category = { CategoryType.SANITY,
            CategoryType.BVT,CategoryType.REGRESSION })
    @Test(groups = { "SANITY" },description = "filter the flights" ,priority = 2,dependsOnMethods = "searchFlightsTest")
    public void filterFlightsTest() throws InterruptedException, IOException {
   ExtentReport.createTest("filter the flights Test");
    int flight_price_per_adult =   new FilterFlightsResultsPage(getDriver()).filterFlights("Non Stop",AirPortName).checkFlightAfterFilter(AirPortName).getPrice();
     System.out.println(flight_price_per_adult);
   int flight_price_summary =  new FilterFlightsResultsPage(getDriver()).viewPriceSummary().getPriceSummaryPerAdult();
        Assert.assertEquals(flight_price_summary,flight_price_per_adult,"the price on filteration page results is not equal to price summary");
    }

    @FrameworkAnnotation(author = { AuthorType.Ahmed }, category = { CategoryType.SANITY,
            CategoryType.BVT,CategoryType.REGRESSION })
    @Test(groups = { "SANITY" },description = "Check the flight booking info" ,priority = 3,dependsOnMethods = {"searchFlightsTest","filterFlightsTest"})
    public void checkFlightBookingInfoTest() throws InterruptedException, IOException {
       ExtentReport.createTest("Check the flight booking info");

        BookingFormPage bookingFormPage=new ViewPriceSummaryPage(getDriver()).clickContinue().clickBookingNow();
    boolean isFound =     bookingFormPage.switchToBookingFormAndValidate(AirPortName);
    Assert.assertTrue(isFound,"the Airport name is not found");
    Assert.assertEquals(bookingFormPage.calculatedIfPriceIncreasedOrNot(numberOfPassengers),bookingFormPage.getActualTotalPrice(),"the flight price is not equal to flight price summary");
    bookingFormPage.clickAggreedOnsomeInformationAndBookingWithoutInsurance();
    }


    @FrameworkAnnotation(author = { AuthorType.Ahmed }, category = { CategoryType.SANITY,
            CategoryType.BVT,CategoryType.REGRESSION })
    @Test(groups = { "SANITY" } ,priority = 3,dependsOnMethods = {"searchFlightsTest","filterFlightsTest","checkFlightBookingInfoTest"},dataProviderClass = AddingAdultInfoDataProviderMethods.class ,dataProvider = "getDataAddingAdultSheet")
    public void addingPassengersInfoTest(AddingAdultsDataModel addingAdultsDataModel) throws InterruptedException, IOException {
    ExtentReport.createTest("Adding passengers info with " +addingAdultsDataModel.getExpected_Results()+" Data");
     BookingFormPage bookingFormPage=  new BookingFormPage(getDriver()).addPassengersInfo(addingAdultsDataModel).fillAdultData(addingAdultsDataModel);
       if(addingAdultsDataModel.getExpected_Results().equals("Errors")){
           bookingFormPage.clickContinue();
           Assert.assertTrue(bookingFormPage.checkErrorMessage(),"the error message is not found");
           bookingFormPage.fillFirstAdultAgainAfterCheckingErrorMessage(addingAdultsDataModel);
       }

    }

    @FrameworkAnnotation(author = { AuthorType.Ahmed }, category = { CategoryType.SANITY,
            CategoryType.BVT,CategoryType.REGRESSION })
    @Test(groups = { "SANITY" },description = "Check the Flight Review Confirmation Details" ,priority = 4,dependsOnMethods = {"searchFlightsTest","filterFlightsTest","checkFlightBookingInfoTest","addingPassengersInfoTest"})
    public void checkFlightReviewConfirmationDetails() throws InterruptedException, IOException {
     // validate the FlightReviewConfirmationDetails

        ExtentReport.createTest("Check the Flight Review Confirmation Details Test Data");
        List<String>values= new FlightReviewDetailsPage(getDriver()).checkFlightReviewDetails();
        values.forEach(System.out::println);
        softAssert=new SoftAssert();
        for (AddingAdultsDataModel addingAdultsDataModel : BookingFormPage.addingAdultsDataModels) {
            System.out.println(addingAdultsDataModel.getFirstName());
            softAssert.assertTrue(values.contains(addingAdultsDataModel.getFirstName().trim().trim()),"The value of first name are not found in  flight review details page");
            System.out.println(addingAdultsDataModel.getLastName());
            softAssert.assertTrue(values.contains(addingAdultsDataModel.getLastName().trim().trim()),"The value of last name are not found in  flight review details page");
            softAssert.assertTrue(values.contains(addingAdultsDataModel.getPassportNumber().trim()),"The value of passport number are not found in  flight review details page");
            softAssert.assertTrue(values.contains(addingAdultsDataModel.getGender().trim().trim()),"The value of gender are not found in  flight review details page");
        }
     softAssert.assertAll();


    }


}
