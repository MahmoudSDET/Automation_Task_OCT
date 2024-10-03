package data_models;


import com.creditdatamw.zerocell.annotation.Column;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class AddingAdultsDataModel {

    @Column(name = "FirstName", index=0)
    private String FirstName;


    @Column(name = "LastName", index=1)
    private String LastName;

    @Column(name = "Gender", index=2)
    private String Gender;



    @Column(name = "DOB_Day", index=3)
    private String DOB_Day;


    @Column(name = "DOB_Month", index=4)
    private String DOB_Month;


    @Column(name = "DOB_Year", index=5)
    private String DOB_Year;

    @Column(name = "PassportNumber", index=6)
    private String PassportNumber;


    @Column(name = "PassportCountry", index=7)
    private String PassportCountry;



    @Column(name = "PassportExpiryDate_Day", index=8)
    private String PassportExpiryDate_Day;


    @Column(name = "PassportExpiryDate_Month", index=9)
    private String PassportExpiryDate_Month;


    @Column(name = "PassportExpiryDate_Year", index=10)
    private String PassportExpiryDate_Year;
    @Column(name = "Expected_Results", index=11)
    private String Expected_Results;

}
