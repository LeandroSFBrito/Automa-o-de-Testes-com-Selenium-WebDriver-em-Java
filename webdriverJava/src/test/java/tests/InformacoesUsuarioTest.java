package tests;

import static org.junit.Assert.*; // PARA IMPORTAR TODOS OS METODOS ASSERT

import org.easetech.easytest.annotation.DataLoader;
import org.easetech.easytest.annotation.Param;
import org.easetech.easytest.runner.DataDrivenTestRunner;
import org.junit.rules.TestName;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

//import suporte.Generator;
import suporte.Screenshot;

import java.util.concurrent.TimeUnit;

// NOTAÇÕES PARA UTILIZAÇÃO DE ARQUIVO .CSV
@RunWith(DataDrivenTestRunner.class)
@DataLoader(filePaths = "InformacoesUsuarioTestData.csv")


public class InformacoesUsuarioTest {

    private WebDriver navegador;

    @Rule
    public TestName test = new TestName();

    @Before
    public void setup(){

        // CONEXÃO COM O DRIVE DO GOOGLE CHROME
        System.setProperty("webdriver.chrome.driver","C:\\Drivers\\chromedriver.exe");

        // CRIAÇÃO DO O OBJETO NAVEGADOR QUE POSSUI A FUNÇÃO DE ABRIR O CHROME ATRAVES DA VARIAVEL NAVEGADOR
        navegador = new ChromeDriver(); // OBJETO NAVEGADOR
        navegador.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS); // DEFINIR A QUANTIDADE DE SEGUNDOS DE EXECUÇÃO, PARA EVITAR ERROS DE TIMEOUT

        // NAVEGAR DENTRO DO NAVEGADOR ATÉ UMA PAGINA
        navegador.get("http://www.juliodelima.com.br/taskit");

        // CLICAR NO LINK QUE POSSUI O TEXTO "SIGN IN
        navegador.findElement(By.linkText("Sign in")).click();

        // IDENTIFICANDO O FORMULARIO DE LOGIN
        WebElement formularioSignInBox = navegador.findElement(By.id("signinbox")); // CRIAR OBJETO WEB, PARA UTILIZAR EM OUTROS ELEMENTOS DENTRO DO FORMULARIO "siginbox"

        // DIGITAR NO CAMPO NAME "LOGIN" QUE ESTA DENTRO DO FORMULARIO DE ID "SIGNBOX" O TEXTO "julio0001"
        formularioSignInBox.findElement(By.name("login")).sendKeys("julio0001"); // SEND KEYS - UTILIZADO PARA DIGITAR DENTRO DO TEXT BOX DE "login"

        // DIGITAR NO CAMPO NAME "PASSWORD" QUE ESTA DENTRO DO FORMULARIO DE ID "SIGNBOX" O TEXTO "123456"
        formularioSignInBox.findElement(By.name("password")).sendKeys("123456"); // SEND KEYS - UTILIZADO PARA DIGITAR DENTRO DO TEXT BOX DE "password"

        // CLICAR NO LINK COM O TEXTO "SIGN IN"
        navegador.findElement(By.linkText("SIGN IN")).click(); // BUSCA NO NAVEGADOR PELO TEXTO DENTRO DO LINK/

        // CLICAR EM UM LINK QUE POSSUI A CLASS "me", ONDE FICA O HI, JULIO
        navegador.findElement(By.className("me")).click(); // BUSCA NO NAVEGADOR PELO NOME DA CLASSE

        // CLICAR EM UM LINK QUE POSSUI O TEXTO "MORE DATA ABOUT YOU"
        navegador.findElement(By.linkText("MORE DATA ABOUT YOU")).click(); // BUSCA NO NAVEGADOR PELO TEXTO DENTRO DO LINK/

    }
    @Test
    public void testAdicionarUmaInformacaoAdicionalDoUsuario(@Param(name = "tipo") String tipo, @Param(name = "contato")String contato, @Param(name= "mensagem") String mensagemEsperada ){

        // CLICAR EM UM BOTÃO ATRAVÉS DO SEU XPATH "//button[@data-target='addmoredata']"
        navegador.findElement(By.xpath("//button[@data-target=\'addmoredata']")).click(); // REALIZANDO A BUSCA DO BOTÃO PELO XPATH

        // IDENTIFICAR A POPUP ONDE ESTA O FORMULARIO DE ID "addmoredata"
        WebElement popupAddMoreData = navegador.findElement(By.id("addmoredata")); // ADICIONANDO UM BLOCO POPUP A UMA VARIAVEL

        // NA COMBO DE NAME "type" ESCOLHE A OPÇÃO "Phone"
        WebElement campoType = popupAddMoreData.findElement(By.name("type")); // IDENTIFICANDO O CAMPO DE SELEÇÃO DENTRO DA POPUP
        new Select(campoType).selectByVisibleText(tipo); // SELEÇÃO DA OPÇÃO "Phone" DENTRO DO COMBOBOX

        // NO CAMPO DE NAME "contact" digitar "+5511999991212"
        popupAddMoreData.findElement(By.name("contact")).sendKeys(contato); // INSERINDO TEXTO DENTRO DE UM TEXT-BOX QUE ESTA DENTRO DA POPUP

        // CLICAR NO LINK DE TEXT "save" QUE ESTA NA POPUP
        popupAddMoreData.findElement(By.linkText("SAVE")).click(); // CLICAR NO ATRAVES DO TESXTO DENTRO DO LINK

        // NA MENSAGEM DE ID "toast-container" VALIDAR QUE O TEXTO É "Your contact has been added!"
        WebElement mensagemPop = navegador.findElement(By.id("toast-container")); // CRIANDO O ELEMENTO mensagemPop PARA ARMAZENAR O CONTEUDO DO "toast-container"
        String mensagem = mensagemPop.getText(); // ARMAZENAR O VALOR DO ELEMENTO mensagemPop DENTRO DA VARIAVEL mensagem
        assertEquals(mensagemEsperada, mensagem); // REALIZAR COMPARAÇÃO ENTRE OS VALORES

        Screenshot.tirar(navegador, "C:\\Users\\Inmetrics\\Documents\\Screenshot\\18.12\\" + " AdicionarUmaInformacaoAdicionalDoUsuario.png"); // METODO DA CLASSE SCREENSHOT PARA SALVAR UM PRINT DA TELA

    }


        @Test
        public void removerUmContatoDeUmUsuario(){

            // CLICAR NO ELEMENTO XPATH //span[text()='+551196418-3798']/following-sibling::a
            navegador.findElement(By.xpath("//span[text()='+5511999991212']/following-sibling::a")).click(); // REALIZAR BUSCA ATRAVES DO XPATH

            // CONFIRMAR A JANELA JAVASCRIPT
            navegador.switchTo().alert().accept(); // ACEITAR A MENSAGEM DO ALERT DE JAVA SCRIPT

            // VALIDAR QUE A MENSAGEM APRESENTADA FOI Rest in peace, dear phone!
            WebElement mensagemPop = navegador.findElement(By.id("toast-container")); // CRIANDO O ELEMENTO mensagemPop PARA ARMAZENAR O CONTEUDO DO "toast-container"
            String mensagem = mensagemPop.getText(); // ARMAZENAR O VALOR DO ELEMENTO mensagemPop DENTRO DA VARIAVEL mensagem
            assertEquals("Rest in peace, dear phone!", mensagem); // REALIZAR COMPARAÇÃO ENTRE OS VALORES

            Screenshot.tirar(navegador, "C:\\Users\\Inmetrics\\Documents\\Screenshot\\" + " removerUmContatoDeUmUsuario.png"); // METODO DA CLASSE SCREENSHOT PARA SALVAR UM PRINT DA TELA

            // AGUARDAR ATÉ 10 SEGUNDOS PARA QUE A JANELA DESAPAREÇA
            WebDriverWait aguardar = new WebDriverWait(navegador, 10); // O TESTE SERA EXECUTADO APOS 10 SEGUNDOS
            aguardar.until(ExpectedConditions.stalenessOf(mensagemPop)); // AGUARDAR A QUANTIDADE DE SEGUNDOS PARA REALIZAR A VALIDAÇÃO

            // CLICAR NO LINK COM O TEXTO Logout
            navegador.findElement(By.linkText("Logout")).click(); // CLICAR EM LOGOT ATRAVES DO TEXTO DENTRO DO LINK

        }


    @After
    public void tearDown(){

        // FECHAR O NAVEGADOR
        navegador.quit();
    }
}
