import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Scanner;

public class US16EnvioDeMensagem {

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
            buffer = new BufferedReader(new FileReader("src/main/resources/dataUS16.json"));
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
    @DisplayName("Teste de caso automatizado 4 - Envio de Mensagem Bem-Sucedido")
    public void TC04() {
        // Obtendo os dados do arquivo JSON
        String email = jsonObject.get("email").getAsString();
        String senha = jsonObject.get("senha").getAsString();
        String mensagem = jsonObject.get("mensagem").getAsString();

        //Abre o SolarSys
        navegador.get("https://rp2unipampa.plataformasolarsys.com/login");

        //Faz o login na Plataforma SolarSys
        loginSolarSys(email, senha);

        espera.until(d -> navegador.findElement(By.cssSelector(".iconify--majesticons")));
        navegador.findElement(By.cssSelector(".iconify--majesticons")).click();

        espera.until(d -> navegador.findElement(By.cssSelector(".v-btn__content > .iconify--fluent")));
        navegador.findElement(By.cssSelector(".v-btn__content > .iconify--fluent")).click();

        mandarMensagem(mensagem);

    }


    private static void loginSolarSys(String email, String senha) {
        espera.until(d -> navegador.findElement(By.name("login")));

        //Preenche os campos de login e senha
        for (int i = 0; i < "usuario@email.com".length(); i++) {
            navegador.findElement(By.name("login")).sendKeys(Keys.BACK_SPACE);
        }
        navegador.findElement(By.name("login")).sendKeys(email);
        navegador.findElement(By.name("password")).sendKeys(senha);

        // Clica no botão de login
        navegador.findElement(By.className("v-btn__content")).click();
    }

    private static void mandarMensagem(String mensagem) {
        // espera pela caixa de texto aparecer e digita a mensagem nela

        // clica no botão de enviar a mensagem
    }
}