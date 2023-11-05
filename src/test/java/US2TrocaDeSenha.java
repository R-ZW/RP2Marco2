import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Scanner;

public class US2TrocaDeSenha {

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
        input = new Scanner(System.in);
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
        options = new ChromeOptions();
        options.addArguments("start-maximized");

        // Inicializa o WebDriver
        WebDriverManager.chromedriver().setup();
        navegador = new ChromeDriver(options);

        espera = new WebDriverWait(navegador, Duration.ofSeconds(50));
    }

    @AfterEach
    public void tearDown() {
        input.close();
        // Fecha o navegador
        //navegador.close();
    }

    @Test
    @DisplayName("Teste de caso automatizado 1 - Modificação de senha bem sucedida")
    public void TCA01() {
        // Obtendo os dados do arquivo JSON
        String email = jsonObject.get("email").getAsString();
        String senha = jsonObject.get("senha").getAsString();
        String novaSenha = jsonObject.get("novaSenha").getAsString();

        //Abre o SolarSys
        navegador.get("https://rp2unipampa.plataformasolarsys.com/login");

        //PARTE 1 -> REDEFININDO A SENHA
        login(email, senha);
        espera.until(d -> navegador.findElement(By.className("v-badge__wrapper")));
        alterarSenha(senha, novaSenha);
        logout();

        //PARTE 2 -> LOGANDO COM A NOVA SENHA E REDEFININDO-A
        espera.until(d -> navegador.findElement(By.name("login")));
        login(email, novaSenha);
        espera.until(d -> navegador.findElement(By.className("v-badge__wrapper")));
        alterarSenha(novaSenha, senha);

    }

    @Test
    @DisplayName("Teste de caso automatizado 2 - Recuperação de senha idêntica (1)")
    public void TCA02_1() {
        // Obtendo os dados do arquivo JSON
        String email = jsonObject.get("email").getAsString();
        String senha = jsonObject.get("senha").getAsString();

        //Abre o SolarSys
        navegador.get("https://rp2unipampa.plataformasolarsys.com/login");

        navegador.findElement(By.cssSelector(".v-btn--text > .v-btn__content")).click();

        //Preenche o campo de email
        for(int i=0; i<"usuario@email.com".length(); i++){
            navegador.findElement(By.name("login")).sendKeys(Keys.BACK_SPACE);
        }
        navegador.findElement(By.name("login")).sendKeys(email);
        navegador.findElement(By.cssSelector(".rounded > .v-btn__content")).click();

        // Abre o Gmail
        navegador.get("https://gmail.com");

        // Preenche o email e o submete
        navegador.findElement(By.id("identifierId")).sendKeys(email, Keys.ENTER);

        espera.until(d -> navegador.findElement(By.name("Passwd")));

        // Preenche a senha e a submete
        navegador.findElement(By.name("Passwd")).sendKeys(senha, Keys.ENTER);

    }

    @Test
    @DisplayName("Teste de caso automatizado 2 - Recuperação de senha idêntica (2)")
    public void TCA02_2() {
        String link = jsonObject.get("linkRecuperacaoDeSenha").getAsString();
        String senha = jsonObject.get("senha").getAsString();

        navegador.get(link);

        navegador.findElement(By.id("password")).sendKeys(senha, Keys.TAB, Keys.TAB, senha);
        navegador.findElement(By.cssSelector(".rounded > .v-btn__content")).click();
    }

    private static void login(String email, String senha) {
        //Preenche os campos de login e senha
        for(int i=0; i<"usuario@email.com".length(); i++){
            navegador.findElement(By.name("login")).sendKeys(Keys.BACK_SPACE);
        }
        navegador.findElement(By.name("login")).sendKeys(email);
        navegador.findElement(By.name("password")).sendKeys(senha);

        //Clica no botão de login
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
        //Abre o menu de usuário
        navegador.findElement(By.cssSelector(".v-badge > .v-avatar")).click();

        //Seleciona a opção de dados de usuário
        navegador.findElement(By.id("list-item-158")).click();

        espera.until(d -> navegador.findElement(By.cssSelector(".mr-15 > .v-btn__content")));

        //Preenche os campos de senha e submete
        navegador.findElement(By.cssSelector(".mr-15 > .v-btn__content")).click();

        navegador.findElement(By.id("input-231")).sendKeys(senhaAntiga);
        navegador.findElement(By.id("input-235")).sendKeys(novaSenha);
        navegador.findElement(By.id("input-239")).sendKeys(novaSenha);

        navegador.findElement(By.cssSelector(".rounded:nth-child(3)")).click();
    }

    private static void abrirGmail(String email, String senha){
        // Abre o Gmail
        navegador.get("https://gmail.com");

        // Preenche o email e o submete
        navegador.findElement(By.id("identifierId")).sendKeys(email, Keys.ENTER);

        espera.until(d -> navegador.findElement(By.name("Passwd")));

        // Preenche a senha e a submete
        navegador.findElement(By.name("Passwd")).sendKeys(senha, Keys.ENTER);
    }
}