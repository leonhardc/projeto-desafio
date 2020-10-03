/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexaodb;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Leonardo
 */
public class ConexaoDBTest {
    
    public ConexaoDBTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getConexaoMySQL method, of class ConexaoDB.
     * @throws java.sql.SQLException
     */
    @Test
    public void testGetConexaoMySQL() throws SQLException {       

        Connection result = ConexaoDB.getConexaoMySQL();
        assertNotNull(result);
        
    }

    /**
     * Test of statusConection method, of class ConexaoDB.
     */
    @Test
    public void testStatusConection() {
       
        Connection conn = ConexaoDB.getConexaoMySQL();
        String status = ConexaoDB.statusConection();
        
        if (conn != null){
            assertEquals(status, "STATUS--->Conectado com sucesso!");
        }
        else {
            assertEquals(status, "STATUS--->Não foi possivel realizar conexão");
        }
       
    }

    /**
     * Test of FecharConexao method, of class ConexaoDB.
     */
    @Test
    public void testFecharConexao() {
        
        System.out.println("FecharConexao");
        boolean expResult = true;
        boolean result = ConexaoDB.FecharConexao();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of ReiniciarConexao method, of class ConexaoDB.
     */
    @Test
    public void testReiniciarConexao() {
        
    }
    
}
