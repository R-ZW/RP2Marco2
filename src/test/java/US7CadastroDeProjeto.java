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

import javax.swing.plaf.TableHeaderUI;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class US7CadastroDeProjeto {

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
            buffer = new BufferedReader(new FileReader("src/main/resources/dataUS7.json"));
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
    @DisplayName("Teste cadastro projeto")
    public void TC07() throws InterruptedException {
        String email = jsonObject.get("email").getAsString();
        String senha = jsonObject.get("senha").getAsString();

        //Abre o SolarSys
        navegador.get("https://rp2unipampa.plataformasolarsys.com/login");

        //Faz o login na Plataforma SolarSys
        login(email, senha);

        abrirMenuDeLeads();


        navegador.findElement(By.xpath("/html/body/div[1]/div/div/div/main/div/div/div/div/div/div/div[2]/header/div/div[1]/a/span")).click();

        navegador.findElement(By.xpath("/html/body/div[1]/div/div/div[1]/main/div/div/div/div/div/div[1]/div[2]/div[2]/div[1]/div/div/form/div[2]/div/div[1]/div/div/div[1]/div[2]/div[1]/input")).click();

        Thread.sleep(5000);

        String potencialInput = "/html/body/div[1]/div/div/div[2]/div/div[8]";
        espera.until(d -> navegador.findElement(By.xpath(potencialInput)));
        navegador.findElement(By.xpath(potencialInput)).click();

        Thread.sleep(5000);

        String botaoAvancar = "/html/body/div[1]/div/div/div[1]/main/div/div/div/div/div/div[1]/div[2]/div[2]/div[1]/div/div/div/div[2]/button";
        espera.until(d -> navegador.findElement(By.xpath(botaoAvancar)));
        navegador.findElement(By.xpath(botaoAvancar)).click();

        Thread.sleep(5000);

        String botaoProjeto = "/html/body/div[1]/div/div/div[1]/main/div/div/div/div/div/div[1]/div[2]/div[2]/div[2]/div/div/button";
        espera.until(d -> navegador.findElement(By.xpath(botaoProjeto)));
        navegador.findElement(By.xpath(botaoProjeto)).click();

        String dadosCliente = "/html/body/div[1]/div/div/div[1]/main/div/div/div/div/div/div[1]/div/div[2]/div[2]/div[1]/div/div/form/div[1]/div/button";
        espera.until(d -> navegador.findElement(By.xpath(dadosCliente)));
        navegador.findElement(By.xpath(dadosCliente)).click();

        String orientacao = "/html/body/div[1]/div/div/div[1]/main/div/div/div/div/div/div[1]/div/div[2]/div[2]/div[1]/div/div/form/div[4]/div[1]/div/div/div[1]/div[1]/div[1]/input";
        espera.until(d -> navegador.findElement(By.xpath(orientacao)));
        navegador.findElement(By.xpath(orientacao)).click();
        String orientacaoInput = "/html/body/div[1]/div/div/div[3]/div/div[1]";
        espera.until(d -> navegador.findElement(By.xpath(orientacaoInput)));
        navegador.findElement(By.xpath(orientacaoInput)).click();

        String graus ="/html/body/div[1]/div/div/div[1]/main/div/div/div/div/div/div[1]/div/div[2]/div[2]/div[1]/div/div/form/div[4]/div[2]/div/div/div[1]/div/input";
        espera.until(d -> navegador.findElement(By.xpath(graus)));
        navegador.findElement(By.xpath(graus)).sendKeys("30");

        String modelo ="/html/body/div[1]/div/div/div[1]/main/div/div/div/div/div/div[1]/div/div[2]/div[2]/div[1]/div/div/form/div[4]/div[3]/div/div/div[1]/div[1]/div[1]/input";
        espera.until(d -> navegador.findElement(By.xpath(modelo)));
        navegador.findElement(By.xpath(modelo)).click();
        String modeloInput = "/html/body/div[1]/div/div/div[4]/div/div[1]";
        espera.until(d -> navegador.findElement(By.xpath(modeloInput)));
        navegador.findElement(By.xpath(modeloInput)).click();

        Thread.sleep(2000);

        String avancarDados = "/html/body/div[1]/div/div/div[1]/main/div/div/div/div/div/div[1]/div/div[2]/div[2]/div[1]/div/div/form/div[5]/div[2]/button";
        espera.until(d -> navegador.findElement(By.xpath(avancarDados)));
        navegador.findElement(By.xpath(avancarDados)).click();

        Thread.sleep(2000);

        String classe = "/html/body/div[1]/div/div/div[1]/main/div/div/div/div/div/div[1]/div/div[2]/div[2]/div[2]/div/div/form/div/div[3]/div/div[1]/div/div/div[1]/div[1]/div[1]/input";
        espera.until(d -> navegador.findElement(By.xpath(classe)));
        navegador.findElement(By.xpath(classe)).click();
        String classeInput = "/html/body/div[1]/div/div/div[5]/div/div[1]/div/div";
        espera.until(d -> navegador.findElement(By.xpath(classeInput)));
        navegador.findElement(By.xpath(classeInput)).click();

        String modeloFiscal = "/html/body/div[1]/div/div/div[1]/main/div/div/div/div/div/div[1]/div/div[2]/div[2]/div[2]/div/div/form/div/div[3]/div/div[2]/div/div/div[1]/div[1]/div[1]/input";
        espera.until(d -> navegador.findElement(By.xpath(modeloFiscal)));
        navegador.findElement(By.xpath(modeloFiscal)).click();
        String modeloFiscalInput = "/html/body/div[1]/div/div/div[6]/div/div[1]/div/div";
        espera.until(d -> navegador.findElement(By.xpath(modeloFiscalInput)));
        navegador.findElement(By.xpath(modeloFiscalInput)).click();

        String ponta ="/html/body/div[1]/div/div/div[1]/main/div/div/div/div/div/div[1]/div/div[2]/div[2]/div[2]/div/div/form/div/div[1]/div/div[1]/div/div/div[1]/div/input";
        espera.until(d -> navegador.findElement(By.xpath(ponta)));
        navegador.findElement(By.xpath(ponta)).sendKeys("10");

        String pontaTUSD ="/html/body/div[1]/div/div/div[1]/main/div/div/div/div/div/div[1]/div/div[2]/div[2]/div[2]/div/div/form/div/div[2]/div/div[1]/div/div/div[1]/div/input";
        espera.until(d -> navegador.findElement(By.xpath(pontaTUSD)));
        navegador.findElement(By.xpath(pontaTUSD)).sendKeys("10");

        String foraPonta ="/html/body/div[1]/div/div/div[1]/main/div/div/div/div/div/div[1]/div/div[2]/div[2]/div[2]/div/div/form/div/div[1]/div/div[2]/div/div/div[1]/div/input";
        espera.until(d -> navegador.findElement(By.xpath(foraPonta)));
        navegador.findElement(By.xpath(foraPonta)).sendKeys("10");

        String foraPontaTUSD ="/html/body/div[1]/div/div/div[1]/main/div/div/div/div/div/div[1]/div/div[2]/div[2]/div[2]/div/div/form/div/div[2]/div/div[2]/div/div/div[1]/div/input";
        espera.until(d -> navegador.findElement(By.xpath(foraPontaTUSD)));
        navegador.findElement(By.xpath(foraPontaTUSD)).sendKeys("10");

        String reservado ="/html/body/div[1]/div/div/div[1]/main/div/div/div/div/div/div[1]/div/div[2]/div[2]/div[2]/div/div/form/div/div[1]/div/div[3]/div/div/div[1]/div/input";
        espera.until(d -> navegador.findElement(By.xpath(reservado)));
        navegador.findElement(By.xpath(reservado)).sendKeys("10");

        String reservadoTUSD ="/html/body/div[1]/div/div/div[1]/main/div/div/div/div/div/div[1]/div/div[2]/div[2]/div[2]/div/div/form/div/div[2]/div/div[3]/div/div/div[1]/div/input";
        espera.until(d -> navegador.findElement(By.xpath(reservadoTUSD)));
        navegador.findElement(By.xpath(reservadoTUSD)).sendKeys("10");

        String ICSM ="/html/body/div[1]/div/div/div[1]/main/div/div/div/div/div/div[1]/div/div[2]/div[2]/div[2]/div/div/form/div/div[4]/div/div[1]/div/div/div[1]/div/input";
        espera.until(d -> navegador.findElement(By.xpath(ICSM)));
        navegador.findElement(By.xpath(ICSM)).sendKeys("10");

        String PIS ="/html/body/div[1]/div/div/div[1]/main/div/div/div/div/div/div[1]/div/div[2]/div[2]/div[2]/div/div/form/div/div[4]/div/div[2]/div/div/div[1]/div/input";
        espera.until(d -> navegador.findElement(By.xpath(PIS)));
        navegador.findElement(By.xpath(PIS)).sendKeys("10");

        String COFINS ="/html/body/div[1]/div/div/div[1]/main/div/div/div/div/div/div[1]/div/div[2]/div[2]/div[2]/div/div/form/div/div[4]/div/div[3]/div/div/div[1]/div/input";
        espera.until(d -> navegador.findElement(By.xpath(COFINS)));
        navegador.findElement(By.xpath(COFINS)).sendKeys("10");

        String botaoAvancarTarifa ="/html/body/div[1]/div/div/div[1]/main/div/div/div/div/div/div[1]/div/div[2]/div[2]/div[2]/div/div/div/div[2]/button/span";
        espera.until(d -> navegador.findElement(By.xpath(botaoAvancarTarifa)));
        navegador.findElement(By.xpath(botaoAvancarTarifa)).click();

        String preecherMediaMensal ="/html/body/div[1]/div/div/div[1]/main/div/div/div/div/div/div[1]/div/div[2]/div[2]/div[3]/div/div/form/div[1]/div/button/span";
        espera.until(d -> navegador.findElement(By.xpath(preecherMediaMensal)));
        navegador.findElement(By.xpath(preecherMediaMensal)).click();

        Thread.sleep(2000);

        String pontaMedia ="/html/body/div[1]/div/div/div[3]/div/form/div/div[2]/div/div[1]/div/div[1]/label";
        espera.until(d -> navegador.findElement(By.xpath(pontaMedia)));
        navegador.findElement(By.xpath(pontaMedia)).click();

        String preencherPonta ="/html/body/div[1]/div/div/div[3]/div/form/div/div[3]/div/div/div[1]/div/input";
        espera.until(d -> navegador.findElement(By.xpath(preencherPonta)));
        navegador.findElement(By.xpath(preencherPonta)).sendKeys("500");

        String botaoPreencher="/html/body/div[1]/div/div/div[3]/div/form/div/div[4]/button[2]/span";
        espera.until(d -> navegador.findElement(By.xpath(botaoPreencher)));
        navegador.findElement(By.xpath(botaoPreencher)).click();

        espera.until(d -> navegador.findElement(By.xpath(preecherMediaMensal)));
        navegador.findElement(By.xpath(preecherMediaMensal)).click();

        Thread.sleep(2000);

        String foraPontaMedia ="/html/body/div[1]/div/div/div[3]/div/form/div/div[2]/div/div[1]/div/div[2]/label";
        espera.until(d -> navegador.findElement(By.xpath(foraPontaMedia)));
        navegador.findElement(By.xpath(foraPontaMedia)).click();

        String preencherForaPonta ="/html/body/div[1]/div/div/div[3]/div/form/div/div[3]/div/div/div[1]/div/input";
        espera.until(d -> navegador.findElement(By.xpath(preencherForaPonta)));
        navegador.findElement(By.xpath(preencherForaPonta)).sendKeys("500");

        espera.until(d -> navegador.findElement(By.xpath(botaoPreencher)));
        navegador.findElement(By.xpath(botaoPreencher)).click();

        Thread.sleep(2000);

        String botaoAvancarMedia="/html/body/div[1]/div/div/div[1]/main/div/div/div/div/div/div[1]/div/div[2]/div[2]/div[3]/div/div/div/div[2]/button/span";
        espera.until(d -> navegador.findElement(By.xpath(botaoAvancarMedia)));
        navegador.findElement(By.xpath(botaoAvancarMedia)).click();

        Thread.sleep(2000);

        String botaoAvancarProjeto ="/html/body/div[1]/div/div/div[1]/main/div/div/div/div/div/div[1]/div/div[2]/div[2]/div[4]/div/div/div/div[2]/button";
        espera.until(d -> navegador.findElement(By.xpath(botaoAvancarProjeto)));
        navegador.findElement(By.xpath(botaoAvancarProjeto)).click();

        Thread.sleep(2000);

        String botaoAvancarProjetoConfirmar ="/html/body/div[1]/div/div/div[8]/div/div/div[2]/div[3]/div/div[2]/button/span";
        espera.until(d -> navegador.findElement(By.xpath(botaoAvancarProjetoConfirmar)));
        navegador.findElement(By.xpath(botaoAvancarProjetoConfirmar)).click();

        Thread.sleep(2000);

        String botaoAvancarFinal ="/html/body/div[1]/div/div/div[2]/main/div/div/div/div/div/div[1]/div[2]/div[2]/div[2]/div/div/div[2]/div[2]/button/span";
        espera.until(d -> navegador.findElement(By.xpath(botaoAvancarFinal)));
        navegador.findElement(By.xpath(botaoAvancarFinal)).click();

        Thread.sleep(2000);

        String botaoConfirmarFinal ="/html/body/div[1]/div/div/div[2]/main/div/div/div/div/div/div[1]/div[2]/div[2]/div[3]/div/div/div[2]/div/div[2]/button/span";
        espera.until(d -> navegador.findElement(By.xpath(botaoConfirmarFinal)));
        navegador.findElement(By.xpath(botaoConfirmarFinal)).click();

        Thread.sleep(2000);

        String confirmarOperacao = "/html/body/div[1]/div/div/div[4]/div/div/div[2]/div[3]/div/div[2]/button/span";
        espera.until(d -> navegador.findElement(By.xpath(confirmarOperacao)));
        navegador.findElement(By.xpath(confirmarOperacao)).click();

    }

    private static void login(String email, String senha) {

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

    private static void abrirMenuDeLeads(){

        //abre o menu de leads
        espera.until(d -> navegador.findElement(By.id("step5")));
        navegador.findElement(By.id("step5")).click();
    }
}