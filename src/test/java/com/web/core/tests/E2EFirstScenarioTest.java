package com.web.core.tests;

import com.web.core.annotations.FrameworkAnnotation;
import com.web.core.enums.AuthorType;
import com.web.core.enums.CategoryType;
import com.web.core.reports.ExtentReport;
import data_models.AddingAdultsDataModel;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import testshelpers.AddingAdultInfoDataProviderMethods;

import java.io.IOException;
import java.util.List;

public class E2EFirstScenarioTest extends BaseTest {

    String AirPortName="Air India";
    int numberOfPassengers=2;
    @FrameworkAnnotation(author = { AuthorType.Ahmed }, category = { CategoryType.SANITY,
            CategoryType.BVT,CategoryType.REGRESSION })
    @Test(groups = { "SANITY" },description = "Search for Flights Test" ,priority = 1)
    public void searchFlightsTest() throws InterruptedException, IOException {

        FilterFlightsResultsPage filterFlightsResultsPage = new SearchFlightsPage(getDriver())
                .loadUrl("https://www.makemytrip.com/").
                handleAddOnsPopUp().selectDepartureCity("Riyadh")
                .selectDirectionCity("Mumbai").selectFromDate(10)
                       .selectToDate(13).selectTheNumberOfPassengers(numberOfPassengers)
                .clickSearch();


    }



    @FrameworkAnnotation(author = { AuthorType.Ahmed }, category = { CategoryType.SANITY,
            CategoryType.BVT,CategoryType.REGRESSION })
    @Test(groups = { "SANITY" },description = "filter the flights" ,priority = 2,dependsOnMethods = "searchFlightsTest")
    public void filterFlightsTest() throws InterruptedException, IOException {

    int flight_price_per_adult =   new FilterFlightsResultsPage(getDriver()).filterFlights("Non Stop",AirPortName).checkFlightAfterFilter(AirPortName).getPrice();
     System.out.println(flight_price_per_adult);
   int flight_price_summary =  new FilterFlightsResultsPage(getDriver()).viewPriceSummary().getPriceSummaryPerAdult();
        Assert.assertEquals(flight_price_summary,flight_price_per_adult,"the price on filteration page results is not equal to price summary");
    }

    @FrameworkAnnotation(author = { AuthorType.Ahmed }, category = { CategoryType.SANITY,
            CategoryType.BVT,CategoryType.REGRESSION })
    @Test(groups = { "SANITY" },description = "Check the flight booking info" ,priority = 3,dependsOnMethods = {"searchFlightsTest","filterFlightsTest"})
    public void checkFlightBookingInfoTest() throws InterruptedException, IOException {
        BookingFormPage bookingFormPage=new ViewPriceSummaryPage(getDriver()).clickContinue().clickBookingNow();
    boolean isFound =     bookingFormPage.switchToBookingFormAndValidate(AirPortName);
    Assert.assertTrue(isFound,"the Airport name is not found");
    Assert.assertEquals(bookingFormPage.calculatedIfPriceIncreasedOrNot(numberOfPassengers),bookingFormPage.getActualTotalPrice(),"the flight price is not equal to flight price summary");
    bookingFormPage.clickAggreedOnsomeInformationAndBookingWithoutInsurance();
    }


    @FrameworkAnnotation(author = { AuthorType.Ahmed }, category = { CategoryType.SANITY,
            CategoryType.BVT,CategoryType.REGRESSION })
    @Test(groups = { "SANITY" },description = "Adding passengers info" ,priority = 3,dependsOnMethods = {"searchFlightsTest","filterFlightsTest","checkFlightBookingInfoTest"},dataProviderClass = AddingAdultInfoDataProviderMethods.class ,dataProvider = "getDataAddingAdultSheet")
    public void addingPassengersInfoTest(AddingAdultsDataModel addingAdultsDataModel) throws InterruptedException, IOException {

     BookingFormPage bookingFormPage=  new BookingFormPage(getDriver()).addPassengersInfo(addingAdultsDataModel).fillAdultData(addingAdultsDataModel);
       if(addingAdultsDataModel.getExpected_Results().equals("Errors")){
           bookingFormPage.clickContinue();
           Assert.assertTrue(bookingFormPage.checkErrorMessage(),"the error message is not found");
           bookingFormPage.fillFirstAdultAgainAfterCheckingErrorMessage(addingAdultsDataModel);
       }



    }

    @FrameworkAnnotation(author = { AuthorType.Ahmed }, category = { CategoryType.SANITY,
            CategoryType.BVT,CategoryType.REGRESSION })
    @Test(groups = { "SANITY" },description = "Check the FlightReviewConfirmationDetails" ,priority = 4,dependsOnMethods = {"searchFlightsTest","filterFlightsTest","checkFlightBookingInfoTest","addingPassengersInfoTest"})
    public void checkFlightReviewConfirmationDetails() throws InterruptedException, IOException {
     // validate the FlightReviewConfirmationDetails
        List<String>values= new FlightReviewDetailsPage(getDriver()).checkFlightReviewDetails();
        values.forEach(System.out::println);
        for (AddingAdultsDataModel addingAdultsDataModel : BookingFormPage.addingAdultsDataModels) {
            System.out.println(addingAdultsDataModel.getFirstName());
            Assert.assertTrue(values.contains(addingAdultsDataModel.getFirstName().trim().trim()),"the values are not found");
            System.out.println(addingAdultsDataModel.getLastName());
            Assert.assertTrue(values.contains(addingAdultsDataModel.getLastName().trim().trim()),"the values are not found");
            Assert.assertTrue(values.contains(addingAdultsDataModel.getPassportNumber().trim()),"the values are not found");
            Assert.assertTrue(values.contains(addingAdultsDataModel.getGender().trim().trim()),"the values are not found");
        }



    }


}
