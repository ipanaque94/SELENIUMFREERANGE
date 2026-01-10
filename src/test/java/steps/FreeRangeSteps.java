package steps;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import driver.DriverManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.PaginaCursos;
import pages.PaginaIntroducciónalTestingdeSoftware;
import pages.PaginaPrincipal;
import pages.PaginaRegistro;

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

    @When("^(I|The user|The client) (selects?) Elegir Plan$")
    public void selectElegirPlanRegex(String actor, String action) {
        landingPage.clickOnElegirPlanButton();
    }

    @And("^(I|The user|The client) (select|selects) Introduction to testing course$")
    public void navigateToIntroRegex(String actor, String action) {
        cursosPage.clickIntroduccionTestinglink();
        new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("introduccion-al-testing-de-software"));
    }

    @Then("I can validate the options in the checkout page")
    public void validateCheckoutPlans() {

        List<String> lista = registro.returnPlanDropdownValues();
        List<String> listaEsperada = Arrays.asList("Academia: $16.99 / mes • 11 productos",
                "Academia: $176 / año • 11 productos", "Free: Gratis • 1 producto");

        Assert.assertEquals(listaEsperada, lista);

    }

}
