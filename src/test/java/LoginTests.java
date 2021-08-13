import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.isChrome;

public class LoginTests {

    @DataProvider(name = "alert-login")
    public Object[][] loginProvider() {
        return new Object[][]{
                {"404@gmail.com", "missio", "Usuário e/ou senha inválidos"},
                {"rtmissio@gmail.com", "1234", "Usuário e/ou senha inválidos"},
                {"", "1234", "Opps. Cadê o email?"},
                {"rtmissio@gmail.com", "", "Opps. Cadê a senha?"}

        };

    }

    @Test
    public void shouldSeeLoggedUser() {
        isChrome();
        open("http://ninjaplus-web:5000");

        $("input[name=email]").setValue("rtmissio@gmail.com");
        $("#passId").setValue("missio");
        $(byText("Entrar")).click();

        $(".user .info span").shouldHave(text("missio"));

    }


    @Test(dataProvider = "alert-login")
    public void passowordNotFound(String email, String password, String expectAlert) {
        isChrome();


        open("http://ninjaplus-web:5000");
        executeJavaScript("localStorage.clear();");

        $("input[name=email").setValue(email);
        $("input[name=password]").setValue(password);

        $("#login").click();
        $(".alert span ").shouldHave(text(expectAlert));
    }
}
