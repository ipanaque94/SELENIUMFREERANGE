package pages;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import driver.DriverManager;

public class FreeRangeTest {

    @BeforeMethod
    public void setUp() {
        DriverManager.initDriver();
    }

    @Test
    public void navegamosaFreerangetesters() {
        DriverManager.getDriver().get("https://www.freerangetesters.com");
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }

}
