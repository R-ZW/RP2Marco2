import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class US1_1PrimeiroAcessoUsuario {

    BufferedReader buffer;
    StringBuilder json;
    String linha;
    JsonParser parser;
    JsonObject jsonObject;
    ChromeOptions options;
    Scanner input;
    static WebDriver navegador;
    static Wait<WebDriver> espera;

    @BeforeEach
    public void setUp() {
        try {
            // Lê o arquivo JSON usando um BufferedReader
            buffer = new BufferedReader(new FileReader("src/main/resources/dataUS1_1.json"));
            json = new StringBuilder();
            while ((linha = buffer.readLine()) != null) {
                json.append(linha);
            }
            buffer.close();

            // Converte o conteúdo do arquivo JSON em um objeto JsonObject
            parser = new JsonParser();
            jsonObject = parser.parse(json.toString()).getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Define as opções do Chrome
        options = new ChromeOptions();
        options.addArguments("start-maximized");

        // Inicializa o WebDriver
        WebDriverManager.chromedriver().setup();
        navegador = new ChromeDriver(options);

        espera = new WebDriverWait(navegador, Duration.ofSeconds(50));
    }

    @Test
    @DisplayName("Teste de caso automatizado 2 - Primeiro Acesso Bem-Sucedido")
    public void TCA02() {
        // Obtendo os dados do arquivo JSON
        String emailIntegradora = jsonObject.get("emailIntegradora").getAsString();
        String senhaIntegradora = jsonObject.get("senhaIntegradora").getAsString();
        String emailGmail = jsonObject.get("emailGmail").getAsString();
        String senhaGmail = jsonObject.get("senhaGmail").getAsString();


        //Abre o SolarSys
        navegador.get("https://rp2unipampa.plataformasolarsys.com/login");

        //Faz o login na Plataforma SolarSys
        loginSolarSys(emailIntegradora, senhaIntegradora);

        // clicar no resend para de fato enviar o e-mail
        //navegador.findElement(By.className("d-inline")).click();
        //espera.until(d -> navegador.findElement(By.cssSelector(".btn.btn-link.p-0.m-0.align-baseline")));
        //navegador.findElement(By.cssSelector(".btn.btn-link.p-0.m-0.align-baseline")).click();

        // Opens a new tab and switches to new tab
        navegador.switchTo().newWindow(WindowType.TAB);
        navegador.get("https://mail.google.com/mail/u/2/#inbox");

        loginEmail(emailGmail, senhaGmail);
        //String a = navegador.getWindowHandle();
        //navegador.switchTo().window(a);

        //espera um máximo de 15 segundos até que a página do e-mail carregue e apareça na tela
        espera(15);

        try {
            // clica no e-mail enviado pela plataforma
            navegador.findElement(By.id(":5z")).click();
        } catch (NoSuchElementException e) {
            // clica no resend para de fato enviar o e-mail
            espera.until(d -> navegador.findElement(By.cssSelector(".btn.btn-link.p-0.m-0.align-baseline")));
            navegador.findElement(By.cssSelector(".btn.btn-link.p-0.m-0.align-baseline")).click();
        }

        // clica no botão do e-mail enviado pela plataforma para validar o acesso
        navegador.findElement(By.linkText("Verificar email")).click();

    }

    private static void loginEmail(String email, String senha) {
        // Preenche o email e o submete
        navegador.findElement(By.id("identifierId")).sendKeys(email, Keys.ENTER);

        // Espera até que o campo da senha carregue na tela
        espera.until(d -> navegador.findElement(By.name("Passwd")));

        // Preenche a senha e a submete
        navegador.findElement(By.name("Passwd")).sendKeys(senha, Keys.ENTER);
    }

    private static void loginSolarSys(String email, String senha) {
        // apaga o que tem no campo e preenche todos existentes
        for (int i = 0; i < "usuario@email.com".length(); i++) {
            navegador.findElement(By.name("login")).sendKeys(Keys.BACK_SPACE);
        }
        navegador.findElement(By.name("login")).sendKeys(email);
        navegador.findElement(By.name("password")).sendKeys(senha);

        // clica no botão de login
        navegador.findElement(By.className("v-btn__content")).click();
    }

    // Método para fazer com que o navegador espere um tempo (em segundos) para que algo apareça na tela
    private void espera(int tempo) {
        navegador.manage().timeouts().implicitlyWait(tempo, TimeUnit.SECONDS);
    }

}
