package mk.finki.ukim.mk.lab.selenium;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class BalloonsPage extends AbstractPage {

    @FindBy(css = "tr[class=balloon-row]")
    private List<WebElement> balloonsRows;

    @FindBy(className = "edit-balloon")
    private List<WebElement> editBalloonButtons;

    @FindBy(className = "delete-balloon")
    private List<WebElement> deleteBalloonButtons;

    @FindBy(className = "add-to-cart-balloon")
    private List<WebElement> addBalloonToCartButton;

    public BalloonsPage(WebDriver driver) {
        super(driver);
    }

    public static BalloonsPage to(WebDriver driver) {
        get(driver, "/balloons");
        return PageFactory.initElements(driver, BalloonsPage.class);
    }

    public void assertElements(int balloonsNumber, int editButtonsNumber, int deleteButtonsNumber, int addBalloonToCartButtons) {
        Assert.assertEquals("The number of balloons doesn't match", balloonsNumber, balloonsRows.size());
        Assert.assertEquals("The number of edit balloon buttons doesn't match", editButtonsNumber, editBalloonButtons.size());
        Assert.assertEquals("The number of delete balloon buttons doesn't match", deleteButtonsNumber, deleteBalloonButtons.size());
        Assert.assertEquals("The number of add balloon to cart buttons doesn't match", addBalloonToCartButtons, addBalloonToCartButton.size());

    }
}
