package io.github.tahanima.login;

import com.microsoft.playwright.Locator;
import io.github.tahanima.BaseTest;
import io.github.tahanima.data.login.LoginData;
import io.github.tahanima.page.homePage.HomePage;
import io.github.tahanima.page.login.LoginPage;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

import static io.github.tahanima.util.DataProviderUtil.processCsv;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author tahanima
 */
public class LoginTest extends BaseTest {
    private LoginPage loginPage;
    private HomePage homePage;
    private static final String FILE_PATH = "login/login.csv";

    @Override
    public void initialize() {
        loginPage = createInstance(LoginPage.class);
    }

    @AfterMethod
    public void captureScreenshot(final ITestResult result) {
        ITestNGMethod method = result.getMethod();

        if (ITestResult.FAILURE == result.getStatus()) {
            loginPage.captureScreenshot(method.getMethodName());
        }
    }

    @DataProvider(name = "loginData")
    public static Object[][] getLoginData(final Method testMethod) {
        String testCaseId = testMethod.getAnnotation(Test.class).testName();

        return processCsv(LoginData.class, FILE_PATH, testCaseId);
    }

    @Test(testName = "TC-1", dataProvider = "loginData")
    public void testCorrectUserNameAndCorrectPassword(final LoginData loginDto) throws InterruptedException {
        loginPage.goTo()
                .enterUsername(loginDto.getUserName())
                .enterPassword(loginDto.getPassword())
                .clickLogin()
                .checkHomePageHeader();
        shareSteps.put("opportunities",loginPage.getOpportunities());
        loginPage.goToProfilePage();
    }

}
