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
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

    /*
    * emanuelferreira.aluno@unipampa.edu.br
    * senhaPraSerAlterada0
    */
    @Test
    @DisplayName("Teste de caso automatizado 2 - Primeiro Acesso Bem-Sucedido")
    public void TCA02() {
        // Obtendo os dados do arquivo JSON
        // String email = jsonObject.get("email").getAsString();
        String email = "matheusciocca.aluno@unipampa.edu.br";
        String senha = "fazAsDocumentacaoDoBernas2";
        // String senha = jsonObject.get("senha").getAsString();
        // String destinatario = jsonObject.get("destinatario").getAsString();
        // String titulo = jsonObject.get("titulo").getAsString();
        // String corpo = jsonObject.get("corpo").getAsString();

        //Abre o SolarSys
        navegador.get("https://rp2unipampa.plataformasolarsys.com/login");

        //Preenche os campos de login e submete
        for(int i=0; i<"usuario@email.com".length(); i++){
            navegador.findElement(By.name("login")).sendKeys(Keys.BACK_SPACE);
        }
        navegador.findElement(By.name("login")).sendKeys(email);
        navegador.findElement(By.name("password")).sendKeys(senha);

        navegador.findElement(By.className("v-btn__content")).click();


    }
}