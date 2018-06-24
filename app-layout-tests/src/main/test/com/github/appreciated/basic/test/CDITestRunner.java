package com.github.appreciated.basic.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.github.appreciated.basic.test.ui.annotated.CdiUI;
import com.github.appreciated.basic.test.view.annotated.cdi.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {CdiUI.class, View1.class, View2.class, View3.class, View4.class, View5.class, View6.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class CDITestRunner {


    @LocalServerPort
    int randomServerPort;

    private static WebDriver driver;

    @BeforeClass
    public static void init() {
        System.setProperty("webdriver.chrome.driver", "selenium/bin/windows/googlechrome/64bit/chromedriver.exe");
        driver = new ChromeDriver();
        WebDriverRunner.setWebDriver(driver);
    }

    @Test
    public void testMenu() {
        openWebsite();

        $(withText(View1.class.getName())).should(Condition.visible);

        clickElement($(byText("My Submenu")));
        clickElement($(byText("View 2")));
        $(withText(View2.class.getName())).should(Condition.visible);

        clickElement($(byText("View 3")));
        $(withText(View3.class.getName())).should(Condition.visible);

        clickElement($(byText("View 4")));
        $(withText(View4.class.getName())).should(Condition.visible);

        clickElement($(byText("View 5")));
        $(withText(View5.class.getName())).should(Condition.visible);

        clickElement($(byText("View 6")));
        $(withText(View6.class.getName())).should(Condition.visible);
    }

    private void clickElement(SelenideElement element) {
        Actions actions = new Actions(driver);
        actions.click(element).perform();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void openWebsite() {
        open("http://localhost:" + randomServerPort);
    }

    @AfterClass
    public static void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}