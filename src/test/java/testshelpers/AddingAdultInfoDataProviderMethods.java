package testshelpers;

import data_models.AddingAdultsDataModel;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static utils.DataReaders.getDataFromCSV;
import static utils.DataReaders.getDataFromExcel;

public class AddingAdultInfoDataProviderMethods {

    @DataProvider()
    private Iterator<Object[]> getDataAddingAdultSheet() throws IOException {
        List<AddingAdultsDataModel> testData = getDataFromExcel(System.getProperty("user.dir") + "/src/test/resources/TestData_Files/TestData.xlsx","Adding Adults" ,AddingAdultsDataModel.class);
        Collection<Object[]> dp = new ArrayList<Object[]>();
        for (AddingAdultsDataModel testData1 : testData) {
            dp.add(new Object[]{testData1});
        }
        return dp.iterator();

    }
}

