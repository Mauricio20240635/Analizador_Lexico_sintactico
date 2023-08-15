/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mauri
 */
public class Analizador_sintactico {

    Estructuras es = new Estructuras();

    public boolean isNonTerminal(String symbol) {
        String[] nonTerminals = {"programa","lista_sent","sent_final","sentencia","lista_id","id_final","lista_expr","lista_exprfinal","expresion","expr_final","expr_arit","tipo","operador","inicio"};
        for (String nonTerminal : nonTerminals) {
            if (nonTerminal.equals(symbol)) {
                return true;
            }
        }
        return false;
    }

    public static int getNonTerminalIndex(String symbol) {
        String[] nonTerminals = {"programa", "lista_sent", "sent_final", "sentencia", "lista_id", "id_final", "lista_expr", "lista_exprfinal", "expresion", "expr_final", "expr_arit", "tipo", "operador", "inicio"};
        for (int i = 0; i < nonTerminals.length; i++) {
            if (nonTerminals[i].equals(symbol)) {
                return i;
            }
        }
        return -1;
    }

    public static int getTerminalIndex(String symbol) {
        String[] terminals = {"class", "id", "{", "}", ";", "=", "read", "(", ")", "write", ",", "enteros", "reales", "int", "float", "+", "-", "*"};
        for (int i = 0; i < terminals.length; i++) {
            if (terminals[i].equals(symbol)) {
                return i;
            }
        }
        return -1;
    }
}
