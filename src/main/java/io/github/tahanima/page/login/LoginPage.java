package io.github.tahanima.page.login;

import com.microsoft.playwright.Locator;
import io.github.tahanima.page.BasePage;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

import static io.github.tahanima.config.ConfigurationManager.configuration;

/**
 * @author tahanima
 */
public class LoginPage extends BasePage {
    public LoginPage goTo() {
        page.navigate(configuration().baseUrl());
        return this;
    }

    public LoginPage enterUsername(final String username) {
        page.click("id=nav-user-login");
        page.fill("id=email", username);
        return this;
    }

    public LoginPage enterPassword(final String password) {
        page.fill("id=password", password);
        return this;
    }

    public LoginPage clickLogin() {
        page.click("//*[@id=\"login-form\"]/button");
        return this;
    }
    public void checkHomePageHeader() {
        page.isVisible("//img[@alt='Instahyre']");
    }
    public String getOpportunities() {
        return page.locator("//*[@id=\"nav-candidates-opportunities\"]/span").textContent().trim();
    }

    public void goToProfilePage() throws InterruptedException {
        page.click("id=nav-candidates-profile");
//        page.click("id=candidate-resume-modal");
//        page.setInputFiles("id=candidate-resume-modal", Path.of("src/test/resources/testData/Resume_Sumanth_Reddy_SDET_-_II_4.4_Years.pdf"));
        page.click("id=nav-candidates-opportunities");

        int oppNum = 10;
        Locator linkNums = page.locator("//button[@id='interested-btn']");
        int clickCount = 0;
        if (isBetween(oppNum, 0, 20) && linkNums.count() > 20) {
            page.click("(//button[@id='interested-btn'])[1]");
            boolean doesProfileMatch = stringContainsItemFromList(page.locator("h1[class='ng-binding']").textContent(),profileHeading);
            boolean doesLocationMatch = stringContainsItemFromList(page.locator("//div[contains(@class,'job-locations')]//span[1]").textContent(),locationMatch);
            System.out.println(doesProfileMatch);
            System.out.println(doesLocationMatch);

        } else if (isBetween(oppNum, 20, 40)) {
            System.out.println("testing case 20 to 40");
        }
    }


    String[] profileHeading = {"Testing","QA","Lead","Automation","Test","SDET","Test"};
    String[] locationMatch = {"Bangalore","Bengaluru","Remote","Work From Home","Home"};

    public static boolean stringContainsItemFromList(String inputStr, String[] items) {
        Arrays.stream(items).map(s -> s.toLowerCase()).collect(Collectors.toList());
        return Arrays.stream(items).anyMatch(inputStr::contains);
    }
    public static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }


}
