package pages;

import java.util.List;

import org.openqa.selenium.By;

public class PaginaRegistro extends BasePage {

    private By planOptions = By.xpath("//input[@name='cart[cart_items_attributes][0][offer_id]']");

    public PaginaRegistro() {
        super();
    }

    public List<String> returnPlanDropdownValues() {
        return getRadioButtonValues(planOptions);
    }

}
