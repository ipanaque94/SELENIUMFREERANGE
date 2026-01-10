package pages;

public class PaginaCursos extends BasePage {

    private String introduccionTestingLink = "//a[contains(normalize-space(),'Introducción al Testing de Software') and @href]";

    public PaginaCursos() {
        super();
    }

    public void clickIntroduccionTestinglink() {
        clickElement(introduccionTestingLink);
    }
}
