package mk.finki.ukim.mk.lab.selenium;

import mk.finki.ukim.mk.lab.model.Manufacturer;
import mk.finki.ukim.mk.lab.model.ShoppingCart;
import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.model.enumerations.BalloonType;
import mk.finki.ukim.mk.lab.model.enumerations.Role;
import mk.finki.ukim.mk.lab.repository.jpa.ShoppingCartRepository;
import mk.finki.ukim.mk.lab.service.BalloonService;
import mk.finki.ukim.mk.lab.service.ManufacturerService;
import mk.finki.ukim.mk.lab.service.ShoppingCartService;
import mk.finki.ukim.mk.lab.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SeleniumScenarioTest {

    private static final String user = "user";
    private static final String admin = "admin";
    private static Manufacturer m1;
    private static User adminUser;
    private static User regularUser;
    private static boolean dataInitialized = false;
    @Autowired
    UserService userService;
    @Autowired
    BalloonService balloonService;
    @Autowired
    ManufacturerService manufacturerService;
    @Autowired
    ShoppingCartRepository shoppingCartRepository;
    @Autowired
    ShoppingCartService shoppingCartService;

    private WebDriver driver;

    @BeforeEach
    private void setup() {
        this.driver = new HtmlUnitDriver(false);
//        System.setProperty("webdriver.chrome.driver", "D:\\Downloads\\chromedriver_win32 (1)\\chromedriver.exe");
//        driver = new ChromeDriver();
        initData();
    }

    @AfterEach
    public void destroy() {
        if (this.driver != null) {
            this.driver.close();
        }

        if (!balloonService.listAll().isEmpty()) {
            if (!shoppingCartRepository.findAll().isEmpty()) {
                ShoppingCart shoppingCart = shoppingCartService.getActiveShoppingCart(regularUser.getUsername());
                shoppingCartRepository.delete(shoppingCart);
            }

            balloonService.deleteById(balloonService.listAll().get(0).getId());
        }
    }


    private void initData() {
        if (!dataInitialized) {
            m1 = manufacturerService.save("Name1", "Country1", "Address1", null).get();

            regularUser = userService.register(user, user, user, user, user, Role.ROLE_USER);
            adminUser = userService.register(admin, admin, admin, admin, admin, Role.ROLE_ADMIN);

            dataInitialized = true;
        }
    }

    @Test
    public void testScenarioBalloonsPageNoUser() {
        BalloonsPage balloonsPage = BalloonsPage.to(this.driver);
        balloonsPage.assertElements(0, 0, 0, 0);
    }

    @Test
    public void testScenarioBalloonsPageAdminUser() {
        LoginPage loginPage = LoginPage.openLogin(this.driver);

        BalloonsPage balloonsPage = LoginPage.doLogin(this.driver, loginPage, adminUser.getUsername(), admin);
        balloonsPage.assertElements(0, 0, 0, 0);

        balloonsPage = AddBalloonPage.addBalloon(this.driver, "test", "test");
        balloonsPage.assertElements(1, 1, 1, 1);
    }

    @Test
    public void testScenarioBalloonsPageRegularUser() {
        LoginPage loginPage = LoginPage.openLogin(this.driver);
        BalloonsPage balloonsPage = LoginPage.doLogin(this.driver, loginPage, regularUser.getUsername(), user);
        balloonsPage.assertElements(0, 0, 0, 0);

        balloonService.save("test", "test", m1.getId(), BalloonType.HEART, null);

        balloonsPage = BalloonsPage.to(this.driver);
        balloonsPage.assertElements(1, 0, 0, 1);
    }

    @Test
    public void testEmptyShoppingCartPageRegularUser() {
        LoginPage loginPage = LoginPage.openLogin(this.driver);
        BalloonsPage balloonsPage = LoginPage.doLogin(this.driver, loginPage, regularUser.getUsername(), user);
        ShoppingCartPage shoppingCartPage = ShoppingCartPage.to(this.driver);

        shoppingCartPage.assertElements(0, 0, 0, 1);
    }

    @Test
    public void testNotEmptyShoppingCartPageRegularUser() {
        balloonService.save("test", "test", m1.getId(), BalloonType.HEART, null);
        LoginPage loginPage = LoginPage.openLogin(this.driver);
        BalloonsPage balloonsPage = LoginPage.doLogin(this.driver, loginPage, regularUser.getUsername(), user);
        balloonsPage.assertElements(1, 0, 0, 1);
        balloonsPage.clickAddToCartButton(0);

        ShoppingCartPage shoppingCartPage = ShoppingCartPage.to(this.driver);
        shoppingCartPage.assertElements(1, 1, 1, 0);

    }
}
