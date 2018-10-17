package restAPIAutomation.RestAPI;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestBase {

	static ExcelActions exAct;
	static RequestSpecification request;
	static Response response;
	static SoftAssert sa;

	ExtentHtmlReporter htmlreporter;
	ExtentReports extent;
	ExtentTest logger;

	Utility util;

	@BeforeTest
	public void startTest() {
		htmlreporter = new ExtentHtmlReporter(".//ExcelFile//MyReport.html");
		extent = new ExtentReports();
		extent.attachReporter(htmlreporter);

		extent.setSystemInfo("Testing", "System");
		extent.setSystemInfo("Type", "API");
		extent.setSystemInfo("Round", "1");

		htmlreporter.config().setDocumentTitle("Rest API Report");
		htmlreporter.config().setReportName("REST API Automation");
		htmlreporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlreporter.config().setTheme(Theme.DARK);
	}

	@Test(dataProvider = "dataProviderS", enabled = false)
	public void testone(String APIName) {
		System.out.println(APIName);
	}

	// FOR CHARGEUSER API
	@Test(dataProvider = "dataProviderS", enabled = false)
	public void ChargeUser(String APIName) throws IOException {
		// Getting EXCEL file path
		exAct = new ExcelActions("./ExcelFile/Data_Value_Final.xlsx", APIName);
		// Initializing loop for getting all scenarios based on column value
		try {
			for (int col = 2; col < exAct.lastColumnNumber(0); col++) {
				// int col1 = col - 2;
				int sl = col - 1;
				logger = extent.createTest(APIName + " Scenario" + sl, "This is " + APIName);
				request = RestAssured.given();
				// Adding header
				logger.info("url:" + exAct.readExcel(0, col));
				request.header("Content-Type", "application/json");
				logger.info("Content-Type:" + "application/json");
				request.header("Authorization", exAct.readExcel(1, col));
				logger.info("Authorization:" + exAct.readExcel(1, col));
				// Adding JSON body to Request
				request.body(exAct.makingJSONWithResponse(0, 1, col));
				// hitting request with URL
				response = request.post(exAct.readExcel(0, col));
				// Storing required values into Arrays
				// String[] data2 = { "chargeUser2_Scenario" + col1,
				// exAct.makingJSONWithResponse(0, 1, col).toString(), response.asString()};
				// Writting into another excel
				// WriteOntoExcel.writetorow("ChargeUser", col1, data2);
				// System.out.println("Scenario " + sl);
				// System.out.println("Request is" + exAct.makingJSONWithResponse(0, 1, col));
				logger.info(exAct.makingJSONWithResponse(0, 1, col));
				// System.out.println("Response is" + response.asString());
				logger.info(response.asString());
				if (JSONExctractorUtil.GetValueOf(response.asString(), "statusCode").equals("500")) {
					logger.log(Status.FAIL, MarkupHelper.createLabel("Test Case is failed", ExtentColor.RED));
				} else if (JSONExctractorUtil.GetValueOf(response.asString(), "statusCode").equals("200")) {
					logger.log(Status.PASS, MarkupHelper.createLabel("Test Case is Passed", ExtentColor.GREEN));
				} else {
					logger.log(Status.FAIL,
							MarkupHelper.createLabel("Test Case is failed due to unreachability", ExtentColor.PINK));
				}

				// System.out.println("---------------------------------------------------------------------");
				// System.out.println("---------------------------------------------------------------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
			// String[] data2 = { "chargeUser2_Scenario", String.valueOf(e) };
			// WriteOntoExcel.writetorow("ChargeUser", 0, data2);
		}
	}

	// FOR CHARGEUSER API
	@Test(dataProvider = "dataProviderS", enabled = true)
	public void apiMethod(String APIName) throws IOException {
		// Getting EXCEL file path
		exAct = new ExcelActions("./ExcelFile/Data_Value_Final.xlsx", APIName);
		// Initializing loop for getting all scenarios based on column value
		try {
			for (int col = 2; col < exAct.lastColumnNumber(0); col++) {
				// int col1 = col - 2;
				int sl = col - 1;
				logger = extent.createTest(APIName + " Scenario " + sl, "This is " + APIName + " " + sl);
				request = RestAssured.given();
				// Adding header
				logger.info("url:" + exAct.readExcel(0, col));
				request.header("Content-Type", "application/json");
				logger.info("Content-Type:" + "application/json");
				request.header("Authorization", exAct.readExcel(1, col));
				logger.info("Authorization:" + exAct.readExcel(1, col));
				// Adding JSON body to Request
				request.body(exAct.makingJSONWithResponse(0, 1, col));
				// hitting request with URL
				response = request.post(exAct.readExcel(0, col));
				logger.info(exAct.makingJSONWithResponse(0, 1, col));
				logger.info(response.asString());

				if (JSONExctractorUtil.GetValueOf(response.asString(), "statusCode").equals("500")) {
					logger.info("GTRID is "+JSONExctractorUtil.GetValueOf(response.asString(), "serverReferenceCode"));
					logger.log(Status.FAIL,
							MarkupHelper.createLabel(
									"Test Case is failed due to "
											+ JSONExctractorUtil.GetValueOf(response.asString(), "errorDescription"),
									ExtentColor.RED));
				} else if (JSONExctractorUtil.GetValueOf(response.asString(), "statusCode").equals("200")) {
					logger.info("GTRID is "+JSONExctractorUtil.GetValueOf(response.asString(), "serverReferenceCode"));
					logger.log(Status.PASS, MarkupHelper.createLabel("Test Case is Passed", ExtentColor.GREEN));
				} else {
					logger.log(Status.FAIL,
							MarkupHelper.createLabel("Test Case is failed due to unreachability", ExtentColor.PINK));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@DataProvider()
	public Object[][] dataProviderS() {
		util = new Utility("./ExcelFile/driver_script.xlsx", "Sheet1");
		Object[][] data = new Object[util.lastRowNumber()][1];

		for (int i = 0; i < util.lastRowNumber(); i++) {
			try {
				data[i][0] = util.readExcel(i, 0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return data;

	}

	@AfterTest
	public void tearDown() {
		extent.flush();
	}
}
