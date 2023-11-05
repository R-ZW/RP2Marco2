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
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class US2TrocaDeSenha {

    BufferedReader buffer;
    StringBuilder json;
    String linha;
    JsonParser parser;
    JsonObject jsonObject;
    ChromeOptions options;
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
        options = new ChromeOptions();
        options.addArguments("start-maximized");

        // Inicializa o WebDriver
        WebDriverManager.chromedriver().setup();
        navegador = new ChromeDriver(options);
    }

    @AfterEach
    public void tearDown() {
        // Fecha o navegador
        //navegador.close();
    }

    @Test
    @DisplayName("Teste de caso automatizado 1 - Modificação de senha bem sucedida")
    public void TCA01() {
        // Obtendo os dados do arquivo JSON
        String email = jsonObject.get("email").getAsString();
        String senha = jsonObject.get("senha").getAsString();
        String destinatario = jsonObject.get("destinatario").getAsString();
        String titulo = jsonObject.get("titulo").getAsString();
        String corpo = jsonObject.get("corpo").getAsString();

        //Abre o SolarSys
        navegador.get("https://rp2unipampa.plataformasolarsys.com/login");

        //Preenche os campos de login e submete
        //navegador.findElement(By.name("login")).clear();
        for(int i=0; i<"usuario@email.com".length(); i++){
            navegador.findElement(By.name("login")).sendKeys(Keys.BACK_SPACE);
        }
        navegador.findElement(By.name("login")).sendKeys(email);
        navegador.findElement(By.name("password")).sendKeys(senha);

        navegador.findElement(By.className("v-btn__content")).click();


        //Acessando a página de configurações
        navegador.findElement(By.className("v-avatar")).click();
        navegador.findElement(By.className("list-item-328")).click();
        navegador.findElement(By.className("v-btn__content")).click();
        /*
        // Abre o Gmail
        navegador.get("https://gmail.com");

        // Preenche o email e o submete
        navegador.findElement(By.id("identifierId")).sendKeys(email, Keys.ENTER);

        // Espera de 5s
        sleep(5000);

        // Preenche a senha e a submete
        navegador.findElement(By.name("Passwd")).sendKeys(senha, Keys.ENTER);

        // Espera de 10s
        sleep(10000);

        // Abre a opção de escrever um novo email
        navegador.findElement(By.className("z0")).click();

        // Preenche os campos do email
        navegador.findElement(By.id(":cs")).sendKeys(destinatario);
        navegador.findElement(By.id(":97")).sendKeys(titulo);
        navegador.findElement(By.id(":ag")).sendKeys(corpo);

        //Envia o email
        navegador.findElement(By.id(":8k")).click();
        */
    }

    // Método para fazer uma pausa (espera)
    private static void sleep(int milliseconds) throws RuntimeException{
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}