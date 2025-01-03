package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MainPage {
    private WebDriver driver;
    private By searchBox = By.cssSelector("input#twotabsearchtextbox");
    private By searchBtn = By.cssSelector("span#nav-search-submit-text");
    private By selectBox = By.id("nav-search-dropdown-card");
    private By results = By.cssSelector("div.sg-col-inner");
    private By titles = By.cssSelector("h2.a-size-medium.a-spacing-none.a-color-base.a-text-normal");
    private By authors = By.cssSelector("a.a-size-base.a-link-normal.s-underline-text.s-underline-link-text.s-link-style");
    private By prices = By.cssSelector(".a-price span.a-offscreen");
    private By bestSellers = By.xpath("//span[text()='Best Seller']");

    public MainPage(WebDriver driver){
        this.driver = driver;
    }

    public void open(){
        driver.get("https://www.amazon.com/");
    }

    public void enterWord(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(selectBox));

        driver.findElement(searchBox).click();

        Select select = new Select(driver.findElement(By.id("searchDropdownBox")));
        select.selectByVisibleText("Books");

        driver.findElement(searchBox).sendKeys("Java");

        driver.findElement(searchBtn).click();

    }

    public List<Book> parsePage(){
        List<Book> books = new ArrayList<>();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(results));

        List<WebElement> titleElements = driver.findElements(titles);
        List<WebElement> authorElements = driver.findElements(authors);
        List<WebElement> priceElements = driver.findElements(prices);
        List<WebElement> bestSellerElements = driver.findElements(bestSellers);


        for (int i = 0; i < titleElements.size(); i++) {
            String title = titleElements.get(i).getText();
            String author = (authorElements.size() > i) ? authorElements.get(i).getText() : "Unknown Author";
            String price = (priceElements.size() > i) ? priceElements.get(i).getText() : "Price not available";
            boolean isBestSeller = false;

            for (WebElement element : bestSellerElements) {
                if (element.isDisplayed() && element.getLocation().getY() == titleElements.get(i).getLocation().getY()) {
                    isBestSeller = true;
                    break; // Если найден элемент, дальнейший поиск не требуется
                }
            }
            books.add(new Book(title, author, price, isBestSeller));
        }

        return books;
    }

    public boolean check(Book book, List<Book> books){
        for(Book b : books){
            if (b.equals(book)) {
                return true;
            }
        }
        return false;
    }

}
