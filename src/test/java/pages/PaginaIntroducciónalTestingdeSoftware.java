package pages;

public class PaginaIntroducciónalTestingdeSoftware extends BasePage {

    private String introduccionTestingLink = "//a[contains(normalize-space(),'Introducción al Testing de Software') and @href]";

    public PaginaIntroducciónalTestingdeSoftware() {
        super();
    }

    public void clickIntroduccionTestinglink() {
        clickElement(introduccionTestingLink);
    }

}
