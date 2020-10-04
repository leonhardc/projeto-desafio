# projeto-desafio

Primeiramente, teremos que instalar algumas dependencias e fazer algumas configurações na máquina para que possamos rodar esse programa .jar. 

As dependencias que devem ser instaladas são:

1. Java e JDK 8;
2. MySQL Server Community 5.7.17;
3. MySQL Workbenth 8.0.


As opções 2 e 3 são usadas para gerenciar o banco de dados usado na aplicação

Ao instalarmos o mysql community devemos configurá-lo com as seguinter informações:

Usário: root
Senha: 1234

Para que possamos acessar o servidor da meneira adequada na aplicação

Para criármos o banco de dados usado na aplicação, basta executarmos no mysql workbenth  o script "mydbscript" localizado no diretório "dataBaseModels".

Executando o Aplicativo:

Podemos executar o arquivo de duas formas diferentes:

1. No diretório, "App-ControleDesempenho", temos um arquivo chamado "desafio-controleDeDesenpenho.jar", podemos executá-lo ao cricármos duas vezes nele.

2. Temos uma segunda maneira de executar o nosso aplicativo. No mesmo diretório descrito no item 1, ao segurármos SHIFT + Botão Direito do mouse, veremos, no menu de contexto que aparece, a opção "Abrir Janela no power shell aqui". Ao clicarmos nessa opção, podemos digitar o seguinte comando:

java -jar desafio-controleDeDesenpenho.jar  
