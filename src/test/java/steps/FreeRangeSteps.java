package steps;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import driver.DriverManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.PaginaCursos;
import pages.PaginaIntroducciónalTestingdeSoftware;
import pages.PaginaPrincipal;
import pages.PaginaRegistro;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FreeRangeSteps {

    PaginaPrincipal landingPage = new PaginaPrincipal();
    PaginaCursos cursosPage = new PaginaCursos();
    PaginaIntroducciónalTestingdeSoftware introducciónSofware = new PaginaIntroducciónalTestingdeSoftware();
    PaginaRegistro registro = new PaginaRegistro();

    @Given("I navigate to www.freerangetesters.com")
    public void iNavigateToFRT() {
        landingPage.navigateToFreeRangeTesters();
    }

    @When("I go to {string} using the navigation bar")
    public void navigationBarUse(String section) {
        landingPage.clickOnSectionNavigationBar(section);
    }

    @When("The user selects Comprar ahora")
    public void selectComprarAhora() {
        landingPage.clickOnElegirPlanButton();
    }

    @And("The user selects Introduction to testing course")
    public void navigateToIntroduction() {
        cursosPage.clickIntroduccionTestinglink();
        new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(15))
                .until(ExpectedConditions.urlContains("introduccion-al-testing-de-software"));
    }

    @Then("I can validate the options in the checkout page")
    public void validateCheckoutPlans() {
        List<String> options = registro.returnPlanDropdownValues();
        for (String value : options) {
            System.out.println("Plan disponible: " + value);
        }
    }

    @Then("I should be on the {string} page")
    public void verifyCurrentPage(String section) {
        // "Mentorías" → "mentoria-1-1-con-pato", no "mentorías"
        String expectedFragment = landingPage.getExpectedUrlFragment(section);

        new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(15))
                .until(ExpectedConditions.urlContains(expectedFragment));

        String currentUrl = landingPage.getCurrentUrl();
        assertTrue(currentUrl.contains(expectedFragment),
                "URL esperada contiene: '" + expectedFragment
                        + "' pero la URL actual fue: " + currentUrl);
    }

}
