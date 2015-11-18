import com.codeborne.selenide.Selenide;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SelenideTest {

    @Test
    public void testSelenide() {
        open("http://localhost:8080/trinfinity/app/trinfinity.html");
        $(".btn-success").click();
    }
}
