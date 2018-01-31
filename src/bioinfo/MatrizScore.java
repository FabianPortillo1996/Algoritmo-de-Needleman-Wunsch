/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bioinfo;

/**
 *
 * @author Fabian
 */
public class MatrizScore {

    private int[][] matriz;
    private int tam = 4;

    public MatrizScore() {
        matriz = new int[tam][tam];
    }

    public int MatrizScore(int a, int b) {
        int valor = 0;
        matriz[0][0] = 10;
        matriz[0][1] = -1;
        matriz[0][2] = -3;
        matriz[0][3] = -4;
        matriz[1][0] = -1;
        matriz[1][1] = 7;
        matriz[1][2] = -5;
        matriz[1][3] = -3;
        matriz[2][0] = -3;
        matriz[2][1] = -5;
        matriz[2][2] = 9;
        matriz[2][3] = 0;
        matriz[3][0] = -4;
        matriz[3][1] = -3;
        matriz[3][2] = 0;
        matriz[3][3] = 8;
        valor = matriz[a][b];
        return valor;
    }
}
