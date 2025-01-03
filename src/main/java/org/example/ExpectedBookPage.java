package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class ExpectedBookPage {
    private WebDriver driver;
    private By title = By.cssSelector("span#productTitle");
    private By authors = By.cssSelector("span.author.notFaded a.a-link-normal");
    private By price = By.cssSelector("span.a-color-price.a-color-price");
    private By isBestSeller = By.cssSelector("i.a-icon.a-icon-addon.p13n-best-seller-badge");

    public ExpectedBookPage(WebDriver driver){
        this.driver = driver;
    }

    public void open(){
        driver.get("https://www.amazon.com/Head-First-Java-Brain-Friendly-Guide/dp/1491910771/ref=sr_1_2?crid=1W90UX4KJXNDR&dib=eyJ2IjoiMSJ9.r8jwNpp75JChtx2yhpjorrBDZT_170M-_n-LUPf85K1L_8GhqIliQGsIy4_B78dpu1MZNBo45YFywH3YMtTDdF7Hwd8iGGH0J8y2Z2C-mkpPi44WZZVifruonUSt5rHZ7WsKaQFV0wkZD-Jd-IHi8UDrGjCynO8G4dYjCSnxNN7i0cBtZhUoL58IJSAN0ZdtUPVPdFoayjMkBRvxx3rwS6C9SA4bz1jC_PT8jgPEmGE.St1GeNDs4zqRXjzM4xksfcJsOdCRNbGTLRsymU-pe_Q&dib_tag=se&keywords=Java&qid=1735853169&s=books&sprefix=java%2Cstripbooks-intl-ship%2C233&sr=1-2");
    }

    public Book parseBookDetails(){
        String expectedTitle = driver.findElement(title).getText();
        List<WebElement> authorElements = driver.findElements(authors);
        String expectedAuthors = authorElements.stream()
                .map(WebElement::getText)
                .collect(Collectors.joining(", "));
        String expectedPrice = driver.findElement(price).getText();
        boolean expectedIsBestSeller = false;
        try {
            if (driver.findElement(isBestSeller).isDisplayed()) {
                expectedIsBestSeller = true;
            }
        } catch (NoSuchElementException e) {
            expectedIsBestSeller = false; // Элемент не найден
        }
        return new Book(expectedTitle, expectedAuthors, expectedPrice, expectedIsBestSeller);
    }
}
