package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.util.List;

public class MainTest {
    private WebDriver driver;
    private MainPage mainPage;
    private ExpectedBookPage expectedBookPage;
    private Book expectedBook;


    // настройка перед тестом
    @BeforeMethod
    public void setup(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-web-security");
        options.addArguments("--remote-allow-origins=*");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        mainPage = new MainPage(driver);
        expectedBookPage = new ExpectedBookPage(driver);
    }


    //початок тесту
    @Test
    public void testSearch() {
        mainPage.open();

        expectedBookPage.open();

        expectedBook = expectedBookPage.parseBookDetails();

        mainPage.enterWord();

        List<Book> books = mainPage.parsePage();

        Assert.assertTrue(books.contains(expectedBook), "The expected book is not found in the list");


    }
    //кінець
    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }


}
