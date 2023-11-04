import io.github.bonigarcia.wdm.WebDriverManager;
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
    @Test
    @DisplayName("Teste automatizado 1 - Envio de email")
    public void envioEmailTest() {
        try {
            // Lê o arquivo JSON usando um BufferedReader
            BufferedReader buffer = new BufferedReader(new FileReader("src/main/resources/data.json"));
            StringBuilder json = new StringBuilder();
            String linha;
            while ((linha = buffer.readLine()) != null) {
                json.append(linha);
            }
            buffer.close();

            // Converte o conteúdo do arquivo JSON em um objeto JsonObject
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(json.toString()).getAsJsonObject();

            // Obtendo os dados do arquivo JSON
            String email = jsonObject.get("email").getAsString();
            String senha = jsonObject.get("senha").getAsString();
            String destinatario = jsonObject.get("destinatario").getAsString();
            String titulo = jsonObject.get("titulo").getAsString();
            String corpo = jsonObject.get("corpo").getAsString();

            // Define as opções do Chrome
            ChromeOptions options = new ChromeOptions();
            options.addArguments("start-maximized");

            // Inicializa o WebDriver
            WebDriverManager.chromedriver().setup();
            WebDriver navegador = new ChromeDriver(options);

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

        } catch (IOException e) {
            e.printStackTrace();
        }
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