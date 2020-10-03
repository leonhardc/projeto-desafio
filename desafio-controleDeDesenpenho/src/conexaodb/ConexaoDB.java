/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package conexaodb;

//Classes necessárias para uso de Banco de dados //

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



/**
 * Classe que Auxilia na conexão com o banco de dados utilizado na aplicação
 * @author Leonardo
 */
public class ConexaoDB {
    

    public static String status = "";
    
    
    //Método Construtor da Classe//

    public ConexaoDB() {

    }


    
    /**
     * Método que cria a conexão com o banco de dados. 
     * Seu retorno é um objeto do tipo Connection se a conexão for bem sucedida ou null se não for.
     * @see getConexaoMySQL()
     */
    public static java.sql.Connection getConexaoMySQL() {
        
        //Método de Conexão//

        Connection connection = null;    //atributo do tipo Connection

        try {

        // Carregando o JDBC Driver padrão

            String driverName = "com.mysql.jdbc.Driver";

            Class.forName(driverName);

        // Configurando a nossa conexão com um banco de dados//

                String serverName = "localhost";    //caminho do servidor do BD
                String mydatabase = "mydb";        //nome do banco de dados
                String url = "jdbc:mysql://" + serverName + "/" + mydatabase + "?verifyServerCertificate=false&useSSL=true";
                String username = "root";        //nome de um usuário
                String password = "1234";      //senha de acesso
                connection = DriverManager.getConnection(url, username, password);

                //Testa sua conexão//

                if (connection != null) {

                    status = ("STATUS--->Conectado com sucesso!");

                } else {

                    status = ("STATUS--->Não foi possivel realizar conexão");

                }            

                return connection;

                } catch (ClassNotFoundException e) {  //Driver não encontrado

                    System.out.println("O driver expecificado nao foi encontrado.");

                    return null;

                } catch (SQLException e) {

            //Não conseguindo se conectar ao banco

                    System.out.println("Nao foi possivel conectar ao Banco de Dados.");

                    return null;

                }


            }
    
    /**
     * Método que retorna String status, que é a string que contem status da conexão. 
     *
     * @see  statusConection()
     */

    public static String statusConection() {
        //Método que retorna o status da sua conexão//
        return status;

    }

    /**
     * Método que fecha a conexão com o banco de dados previamente aberta. 
     * Retorna true se a conexão foi fechada com sucesso  ou false se a conexão não foi fechada com sucesso
     * @see  FecharConexao()
     */

    public static boolean FecharConexao() {
        //Método que fecha sua conexão//
        try {

            ConexaoDB.getConexaoMySQL().close();

            return true;

        } catch (SQLException e) {

            return false;

        }

    }


    /**
     * Método que reinicia a conexão com o banco de dados, previamente aberta, porem, fechada inesperadamente
     * Retorna um objeto do tipo Conection
     * @see ReiniciarConexao()
     */

    public static java.sql.Connection ReiniciarConexao() {
            //Método que reinicia sua conexão//
            
            FecharConexao();

            return ConexaoDB.getConexaoMySQL();

        }


}
