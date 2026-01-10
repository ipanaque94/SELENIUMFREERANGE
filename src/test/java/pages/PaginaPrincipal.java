package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PaginaPrincipal extends BasePage {

    // private String searchButton = "//input[@id='search-form-autocomplete--776']";
    private String elegirUnPlanButton = "//a[contains(normalize-space(),'Compra ahora') and @href]";
    // Botón de compra en página del curso. Texto visible: "Compra ahora"
    // private String elegirUnPlanButton = "//a[contains(@href,'/buy') and
    // contains(text(),'Compra ahora')]";
    private String sectionLink = "//a[contains(normalize-space(),'%s') and @href]";

    public PaginaPrincipal() {
        super();
    }
    /*
     * public void navigateToFreeRangeTesters(){
     * navigateTo("https://www.freerangetesters.com");
     * clickElement(searchButton);
     * }
     */

    public void navigateToFreeRangeTesters() {
        navigateTo("https://www.freerangetesters.com");

    }

    // public void clickOnSectionNavigationBar(String section) {
    // String xpathSection = String.format(sectionLink, section);
    // clickElement(xpathSection);
    // }

    public void clickOnSectionNavigationBar(String section) {
        // Limpieza defensiva: elimina comillas y espacios extra
        section = section.replace("\"", "").trim();

        String xpathSection = String.format(sectionLink, section);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath(xpathSection)));
        element.click();
    }

    public void clickOnElegirPlanButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        try {
            WebElement element = wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath(elegirUnPlanButton)));
            element.click();
        } catch (Exception e) {
            // Fallback: click vía JavaScript si el click estándar falla
            WebElement element = driver.findElement(By.xpath(elegirUnPlanButton));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }
}
