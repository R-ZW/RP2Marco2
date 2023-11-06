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

public class US1PrimeiroAcesso {

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
            buffer = new BufferedReader(new FileReader("src/main/resources/dataUS1.json"));
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
    public void TCA03() {
        // Obtendo os dados do arquivo JSON
        String email = jsonObject.get("email").getAsString();
        String senha = jsonObject.get("senha").getAsString();
        String novaSenha = jsonObject.get("novaSenha").getAsString();


        //Abre o SolarSys
        navegador.get("https://rp2unipampa.plataformasolarsys.com/login");

        //Faz o login na Plataforma SolarSys
        loginSolarSys(email, senha);

        // espera o menu de usuário aparecer e clica nele
        espera.until(d -> navegador.findElement(By.cssSelector(".v-badge > .v-avatar")));
        navegador.findElement(By.cssSelector(".v-badge > .v-avatar")).click();

        // espera a opção de "meus dados" aparecer e clica nela
        espera.until(d -> navegador.findElement(By.id("list-item-158")));
        navegador.findElement(By.id("list-item-158")).click();

        // espera a opção de "alterar senha" aparecer e clica nela
        espera.until(d -> navegador.findElement(By.cssSelector(".mr-15 > .v-btn__content")));
        navegador.findElement(By.cssSelector(".mr-15 > .v-btn__content")).click();

        alterarSenha(senha, novaSenha);

        logout();

        loginSolarSys(email, novaSenha);
    }


    private static void loginSolarSys(String email, String senha) {

        espera.until(d -> navegador.findElement(By.name("login")));

        //Preenche os campos de login e senha
        for(int i = 0; i < "usuario@email.com".length(); i++){
            navegador.findElement(By.name("login")).sendKeys(Keys.BACK_SPACE);
        }
        navegador.findElement(By.name("login")).sendKeys(email);
        navegador.findElement(By.name("password")).sendKeys(senha);

        // Clica no botão de login
        navegador.findElement(By.className("v-btn__content")).click();
    }

    private static void logout() {
        espera.until(d -> navegador.findElement(By.className("v-badge__wrapper")));

        //Abre o menu de usuário
        navegador.findElement(By.cssSelector(".v-badge > .v-avatar")).click();

        //Seleciona a opção de fazer logout
        navegador.findElement(By.id("list-item-160")).click();
    }

    private static void alterarSenha(String senhaAntiga, String novaSenha) {

        // espera os campos de senha aparecerem na tela
        espera.until(d -> navegador.findElement(By.cssSelector(".mr-15 > .v-btn__content")));

        // preenche os campos e submete
        navegador.findElement(By.id("input-231")).sendKeys(senhaAntiga);
        navegador.findElement(By.id("input-235")).sendKeys(novaSenha);
        navegador.findElement(By.id("input-239")).sendKeys(novaSenha);

        navegador.findElement(By.cssSelector(".rounded:nth-child(3)")).click();
    }

}