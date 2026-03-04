package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {

	@DataProvider(name="LoginData")
	public String[][]getData() throws IOException{
		String path=".\\testData\\Open_cart_login_test_data.xlsx";
		Utilities ut=new Utilities(path);
		
		int totalrows=ut.getRowCount("Sheet1");
		int totalcol=ut.getCellCount("Sheet1", 1);
		
		String loginData[][]=new String[totalrows][totalcol];//created 2D array of excel size
		
		for(int i=1;i<=totalrows;i++) {//i=1 bcz first row of excel sheet contains headers
			for(int j=0;j<totalcol;j++) { // j=0 bcz first column contains the value
				loginData[i-1][j]=ut.getCellData("Sheet1", i, j);
				//here we took [i-1] bcz our i value is 1 and array indexing starts from zero so it is [i-1]
			}
		}	
		return loginData;
	}
}

