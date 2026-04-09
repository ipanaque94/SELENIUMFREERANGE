package pages;

import org.junit.jupiter.api.Test;

import driver.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class FreeRangeTest {

    @Before
    public void setUp() {
        DriverManager.initDriver();
    }

    @Test
    public void navegamosaFreerangetesters() {
        DriverManager.getDriver().get("https://www.freerangetesters.com");
    }

    @After
    public void tearDown() {
        DriverManager.quitDriver();
    }

}
