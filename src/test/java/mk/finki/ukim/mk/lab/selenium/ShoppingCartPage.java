package mk.finki.ukim.mk.lab.selenium;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ShoppingCartPage extends AbstractPage {

    @FindBy(css = "tr[class=balloon-row]")
    List<WebElement> balloonRows;
    @FindBy(className = "delete-balloon")
    List<WebElement> balloonDeleteButtons;
    @FindBy(className = "place-order-button")
    List<WebElement> placeOrderButtons;
    @FindBy(className = "shop-for-balloons-button")
    List<WebElement> shopForBalloonsButtons;

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
    }

    public static ShoppingCartPage to(WebDriver driver) {
        get(driver, "/shopping-cart");
        return PageFactory.initElements(driver, ShoppingCartPage.class);
    }

    public void assertElements(int balloonsNumber, int deleteButtonsNumber, int placeOrderButtonsNumber, int shopForBalloonsButtonsNumber) {
        Assert.assertEquals("The number of balloons doesn't match", balloonsNumber, balloonRows.size());
        Assert.assertEquals("The number of delete buttons doesn't match", deleteButtonsNumber, balloonDeleteButtons.size());
        Assert.assertEquals("The number of place order buttons doesn't match", placeOrderButtonsNumber, placeOrderButtons.size());
        Assert.assertEquals("The number of shop for balloons buttons doesn't match", shopForBalloonsButtonsNumber, shopForBalloonsButtons.size());
    }

}
