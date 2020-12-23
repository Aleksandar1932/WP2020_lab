package mk.finki.ukim.mk.lab.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class AddBalloonPage extends AbstractPage {
    private WebElement name;
    private WebElement description;
    private WebElement manufacturerId;
    private WebElement type;
    private WebElement submit;

    public AddBalloonPage(WebDriver driver) {
        super(driver);
    }

    public static BalloonsPage addBalloon(WebDriver driver, String name, String description) {
        get(driver, "/balloons/add-form");
        AddBalloonPage addBalloon = PageFactory.initElements(driver, AddBalloonPage.class);

        addBalloon.name.sendKeys(name);
        addBalloon.description.sendKeys(description);


        addBalloon.submit.click();
        return PageFactory.initElements(driver, BalloonsPage.class);

    }

}
