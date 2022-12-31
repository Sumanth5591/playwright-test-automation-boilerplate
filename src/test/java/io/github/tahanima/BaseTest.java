package io.github.tahanima;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import io.github.tahanima.browser.BrowserManager;
import io.github.tahanima.page.BasePage;
import io.github.tahanima.page.BasePageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

import java.util.HashMap;

/**
 * @author tahanima
 */
@Listeners(TestListener.class)
public abstract class BaseTest {
    private final Playwright playwright = Playwright.create();
    private final Browser browser = BrowserManager.browser(playwright);
    protected final Page page = browser.newPage();
    public HashMap<String,String> shareSteps = new HashMap<>();

    public abstract void initialize();

    protected <T extends BasePage> T createInstance(final Class<T> basePage) {
        return BasePageFactory.createInstance(page, basePage);
    }

    @BeforeClass
    public void setup() {
        initialize();
    }

    @AfterClass
    public void teardown() {
        playwright.close();
    }

    protected int convertStringToInt(String strValue) {
        Integer number = null;
        try {
            number = Integer.valueOf(strValue);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return number;
    }


}
