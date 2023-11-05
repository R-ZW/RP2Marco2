import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class US1PrimeiroAcesso {

    BufferedReader buffer;
    StringBuilder json;
    String linha;
    JsonParser parser;
    JsonObject jsonObject;
    EdgeOptions options;
    WebDriver navegador;

    @BeforeEach
    public void setUp() {
        try {
            // Lê o arquivo JSON usando um BufferedReader
            buffer = new BufferedReader(new FileReader("src/main/resources/dataUS2.json"));
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
        options = new EdgeOptions();
        options.addArguments("start-maximized");

        // Inicializa o WebDriver
        WebDriverManager.edgedriver().setup();
        navegador = new EdgeDriver(options);
    }

    @Test
    @DisplayName("Teste de caso automatizado 2 - Primeiro Acesso Bem-Sucedido")
    public void TCA02() {
        // Obtendo os dados do arquivo JSON
        String emailIntegradora = "emanuelferreira.aluno@unipampa.edu.br";
        String senhaIntegradora = "teste1234";
        String emailGmail = "emanuelferreira.aluno@unipampa.edu.br";
        String senhaGmail = "Tavinho2903";


        //Abre o SolarSys
        navegador.get("https://rp2unipampa.plataformasolarsys.com/login");

        //Preenche os campos de login e submete
        for (int i = 0; i < "usuario@email.com".length(); i++) {
            navegador.findElement(By.name("login")).sendKeys(Keys.BACK_SPACE);
        }
        navegador.findElement(By.name("login")).sendKeys(emailIntegradora);
        navegador.findElement(By.name("password")).sendKeys(senhaIntegradora);

        navegador.findElement(By.className("v-btn__content")).click();

        // Opens a new tab and switches to new tab
        navegador.switchTo().newWindow(WindowType.TAB);
        navegador.get("https://mail.google.com/mail/u/2/#inbox");

        // Preenche o email e o submete
        navegador.findElement(By.id("identifierId")).sendKeys(emailGmail, Keys.ENTER);

        //espera 5 segundos até que o campo da senha apareça na tela
        espera(10);

        // Preenche a senha e a submete
        navegador.findElement(By.name("Passwd")).sendKeys(senhaGmail, Keys.ENTER);

        //espera 30 segundos até que a página do e-mail carregue e apareça na tela
        espera(30);

        // suposto id normal do e-mail enviado pela plataforma
        navegador.findElement(By.cssSelector("tr#:1v")).click();

        // vou ter que usar depois para clicar no resend
        //navegador.findElement(By.className("d-inline")).click();

        // alerta de e-mail enviado
        // navegador.findElement(By.className("alert"))


    }

    // Método para fazer com que o navegador espere um tempo (em segundos) para que algo apareça na tela
    private void espera(int tempo) throws RuntimeException {
        navegador.manage().timeouts().implicitlyWait(tempo, TimeUnit.SECONDS);
    }
}