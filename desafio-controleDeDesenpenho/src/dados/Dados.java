/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dados;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author Leonardo
 */
public class Dados {
    
    private final int jogo;
    private final int placar;
    private final int mintemp;
    private final int maxtemp;
    private final int quebramin;
    private final int quebramax;

    public Dados(int jogo, int placar, int mintemp, int maxtemp, int quebramin, int quebramax) {
        this.jogo = jogo;
        this.placar = placar;
        this.mintemp = mintemp;
        this.maxtemp = maxtemp;
        this.quebramin = quebramin;
        this.quebramax = quebramax;
    }

    public int getJogo() {
        return jogo;
    }

    public int getPlacar() {
        return placar;
    }

    public int getMintemp() {
        return mintemp;
    }

    public int getMaxtemp() {
        return maxtemp;
    }

    public int getQuebramin() {
        return quebramin;
    }

    public int getQuebramax() {
        return quebramax;
    }

    // metodos seters


    
}
