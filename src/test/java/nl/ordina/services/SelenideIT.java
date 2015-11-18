package nl.ordina.services;

import org.junit.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SelenideIT {

    @Test
    public void testSelenide() {
        open("http://localhost:8080/trinfinity/app/trinfinity.html");
        $(".btn-success").click();
    }
}
