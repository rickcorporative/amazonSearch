package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.*;
import io.qameta.allure.testng.AllureTestNg;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.Assert;
import java.util.List;

@Epic("Amazon Search")
@Feature("Search Functionality")
@Listeners({AllureTestNg.class})
public class MainTest {
    private WebDriver driver;
    private MainPage mainPage;
    private ExpectedBookPage expectedBookPage;
    private Book expectedBook;


    // настройка перед тестом
    @BeforeMethod
    @Step("Setup WebDriver and initialize pages")
    public void setup(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("profile-directory=Default");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-web-security");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.5481.178 Safari/537.36");
        options.addArguments("--disable-features=ChromeWhatsNewUI");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        mainPage = new MainPage(driver);
        expectedBookPage = new ExpectedBookPage(driver);
    }


    //початок тесту
    @Test
    @Story("User searches for a book by title")
    @Description("Проверка поиска книги 'Java' в категории 'Books'")
    @Severity(SeverityLevel.CRITICAL)
    public void testSearch() {
        openMainPage();

        setParameter();

        enterWord();

        clickOnSearchBtn();

        List<Book> books = getExpectedData();

        openExpectedPage();

        saveExpectedData();

        IsInList(books, expectedBook);
    }

    @Step("Open main page")
    public void openMainPage() {
        mainPage.open();
    }

    @Step("Set parameter")
    public void setParameter(){
        mainPage.setParameter("Books");
    }

    @Step("Enter word")
    public void enterWord(){
        mainPage.enterWord("Java");
    }


    @Step("Click on search button")
    public void clickOnSearchBtn(){
        mainPage.clickOnSearchBtn();
    }

    @Step("Getting expected data")
    public List<Book> getExpectedData(){
        List<Book> books = mainPage.parsePage();
        return books;
    }

    @Step("Opening page of expected book")
    public void openExpectedPage(){
        expectedBookPage.open();
    }

    @Step("Saving data from a page of expected book")
    public void saveExpectedData(){
        expectedBook = expectedBookPage.parseBookDetails();
    }

    @Step("Checking if the book is in list")
    public void IsInList(List<Book> books, Book book){
        Assert.assertTrue(!books.contains(expectedBook), "The expected book is not found in the list");
    }

    //кінець
    @AfterMethod
    @Step("Close WebDriver")
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }


}
