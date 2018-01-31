/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bioinfo;

import bioinfo.FXMLDocumentController;

/**
 *
 * @author Fabian
 */
public class Matriz {

    private int fila;
    private int columna;
    private int[][] matrixScoring;
    private int[][] matrixFinal;
    private int GAP = -5;
    private String AlineamientoA = "";
    private String AlineamientoB = "";
    Secuencia sq = new Secuencia();
    MatrizScore ms = new MatrizScore();

    public Matriz() {
    }

    public Matriz(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        matrixScoring = new int[fila][columna];
        matrixFinal = new int[fila][columna];
    }

    public int[] Conversion(String secuencia) {
        String[] arreglo;
        int tam = secuencia.length();
        String m2 = "el vector en numeros";
        int[] conversion = new int[tam];
        for (int i = 0; i < tam; i++) {
            arreglo = secuencia.split("");
            if (arreglo[i].equals(sq.getA())) {
                conversion[i] = 0;
            } else if (arreglo[i].equals(sq.getG())) {
                conversion[i] = 1;
            } else if (arreglo[i].equals(sq.getC())) {
                conversion[i] = 2;
            } else if (arreglo[i].equals(sq.getT())) {
                conversion[i] = 3;
            }
            m2 = m2 + "{" + conversion[i] + "]";
        }
        // System.out.println(m2);
        return conversion;
    }

    public void MatrizScoring() {
        int p1 = 1, p = 1;
        matrixScoring[0][0] = 0;

        for (int i = 1; i < fila; i++) {
            matrixScoring[i][0] = p1 * GAP;
            p1++;
        }
        for (int i = 1; i < columna; i++) {
            matrixScoring[0][i] = p * GAP;
            p++;
        }

    }

    public void MatrizFinal(String sq, String sq2) {
        int k = 0, l = 0;
        int tam = sq.length();
        int tam1 = sq2.length();
        int[] secuencia1 = new int[tam];
        int[] secuencia2 = new int[tam1];
        secuencia1 = Conversion(sq);
        secuencia2 = Conversion(sq2);

        matrixFinal[0][0] = 0;

        for (int i = 1; i < fila; i++) {
            matrixFinal[i][0] = secuencia1[k];
            k++;
        }
        for (int i = 1; i < columna; i++) {
            matrixFinal[0][i] = secuencia2[l];
            l++;
        }

    }

    public int consultaSocre(int i, int j) {
        int consultaTabla = ms.MatrizScore(matrixFinal[i][0], matrixFinal[0][j]);
        return consultaTabla;
    }

    public int MAX(int i, int j) {
        int consultaTabla = consultaSocre(i, j);
        int diagonal = matrixScoring[i - 1][j - 1] + consultaTabla;
        int arriba = matrixScoring[i - 1][j] + GAP;
        int izquierda = matrixScoring[i][j - 1] + GAP;
        int resultado = Mayor(diagonal, izquierda, arriba);
        return resultado;
    }

    public int Mayor(int diagonal, int izquierda, int arriba) {

        if (diagonal >= izquierda) {
            if (diagonal >= arriba) {
                return diagonal;
            } else {

                return arriba;
            }
        } else if (izquierda >= arriba) {

            return izquierda;
        } else {

            return arriba;
        }
    }

    public void valoresMatrizScoring() {
        for (int i = 1; i < fila; i++) {
            for (int j = 1; j < columna; j++) {
                matrixScoring[i][j] = MAX(i, j);
            }
        }

    }

    public void alineamiento(String sq, String sq2) {
        int tam = sq.length();
        int tam1 = sq2.length();
        int[] secuencia1 = new int[tam];
        int[] secuencia2 = new int[tam1];
        secuencia1 = Conversion(sq);
        secuencia2 = Conversion(sq2);
        int i, j;
        i = sq.length() ;
        j = sq2.length() ;
        while (i > 0 && j > 0) {
            int consulta = consultaSocre(i, j);
            //System.out.println("consulta" + ":" + consulta);
            int score = matrixScoring[i][j];
            int diagonal = matrixScoring[i - 1][j - 1];
            int arriba = matrixScoring[i][j-1] + GAP;
            int izquierda = matrixScoring[i - 1][j] + GAP;

            if (score == diagonal + consulta) {
                AlineamientoA = secuencia1[i - 1] + AlineamientoA;
                AlineamientoB = secuencia2[j - 1] + AlineamientoB;
                i--;
                j--;
            } else if (score == izquierda) {
                AlineamientoA = secuencia1[i - 1] + AlineamientoA;
                AlineamientoB = "-" + AlineamientoB;
                i--;
            } else if (score == arriba) {
                AlineamientoA = "-" + AlineamientoA;
                AlineamientoB = secuencia2[j - 1] + AlineamientoB;
                j--;
            }

        }
        while (i > 0) {
            AlineamientoA = secuencia1[i - 1] + AlineamientoA;
            AlineamientoB = "-" + AlineamientoB;
            i--;
        }
        while (j > 0) {
            AlineamientoA = "-" + AlineamientoA;
            AlineamientoB = secuencia2[j - 1] + AlineamientoB;
            j--;
        }
        System.out.println("Alineamiento A" + ":" + AlineamientoA);
        System.out.println("Alineamiento B" + ":" + AlineamientoB);

    }

    public void mostrarMatriz() {
        String m = "la matriz es \n";

        for (int k = 0; k < fila; k++) {
            for (int l = 0; l < columna; l++) {
                m = m + "[" + matrixScoring[k][l] + "]";
            }
            m = m + "\n";
        }
        //ob.mostrarDatos(m);
        // System.out.println(m);
    }

    public void mostrarMatrizFinal() {
        String m = "la matriz es \n";

        for (int k = 0; k < fila; k++) {
            for (int l = 0; l < columna; l++) {
                m = m + "[" + matrixFinal[k][l] + "]";
            }
            m = m + "\n";
        }
        //ob.mostrarDatos(m);
        // System.out.println(m);
    }

    public int[][] getMatrix() {

        return matrixScoring;
    }

    public String getSecuenciaA() {
        String Alinieamiento1 = "";
        String[] aux = AlineamientoA.split("");
        for (int i = 0; i < AlineamientoA.length(); i++) {
            if (aux[i].equals("0")) {
                Alinieamiento1 += "A";
            }
            if (aux[i].equals("1")) {
                Alinieamiento1 += "G";
            }
            if (aux[i].equals("2")) {
                Alinieamiento1 += "C";
            }
            if (aux[i].equals("3")) {
                Alinieamiento1 += "T";
            }
            if (aux[i].equals("-")) {
                Alinieamiento1 += "-";
            }
        }
        return Alinieamiento1;
    }

    public String getSecuenciaB() {
        String Alinieamiento2 = "";
        String[] aux = AlineamientoB.split("");
        for (int i = 0; i < AlineamientoB.length(); i++) {
            if (aux[i].equals("0")) {
                Alinieamiento2 += "A";
            }
            if (aux[i].equals("1")) {
                Alinieamiento2 += "G";
            }
            if (aux[i].equals("2")) {
                Alinieamiento2 += "C";
            }
            if (aux[i].equals("3")) {
                Alinieamiento2 += "T";
            }
            if (aux[i].equals("-")) {
                Alinieamiento2 += "-";
            }
        }
        return Alinieamiento2;
    }
}
