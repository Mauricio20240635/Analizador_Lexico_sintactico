
import Pila.Pila;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Test_analizador {

    public static void main(String[] args) throws IOException {

        Pila pd = new Pila();
        Analizador_sintactico ro = new Analizador_sintactico();
        Estructuras es = new Estructuras();

        es.Leer();
        es.comparaTerminales();
        //es.Imprimir();
//        String a=es.calcularInicio();
//        System.out.println(a); 

        //es.Imprimir();
        // Definir la gramática, la tabla de predicción y el scanner
        String[] simbolosnoterminales = es.simbolosnoterminales;
        String[] terminales = es.simbolosterminales;
        int[][] MatrizPredictiva = {
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 2, 0, 0, 0, 0, 2, 0, 0, 2, 0, 0, 0, 2, 2, 0, 0, 0},
            {0, 3, 0, 4, 0, 0, 3, 0, 0, 3, 0, 0, 0, 3, 3, 0, 0, 0},
            {0, 6, 0, 0, 0, 0, 7, 0, 0, 8, 0, 0, 0, 5, 5, 0, 0, 0},
            {0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 11, 0, 0, 0, 11, 0, 10, 0, 0, 0, 0, 0, 0, 0},
            {0, 12, 0, 0, 0, 0, 0, 12, 0, 0, 0, 12, 12, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 13, 0, 0, 0, 14, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 15, 0, 0, 0, 0, 0, 15, 0, 0, 0, 15, 15, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 17, 0, 0, 0, 17, 0, 0, 0, 0, 0, 0, 16, 16, 16},
            {0, 19, 0, 0, 0, 0, 0, 18, 0, 0, 0, 20, 21, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 22, 23, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 24, 25, 26},
            {27, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,}
        };
        String[] productionRules = {
            "programa → class id { lista_sent }",
            "lista_sent → sentencia sent_final",
            "sent_final → sentencia sent_final",
            "sent_final → ε",
            "sentencia → tipo lista_id ;",
            "sentencia → id = expresion ;",
            "sentencia → read ( lista_id ) ;",
            "sentencia → write ( lista_expr ) ;",
            "lista_Id → id id_final",
            "id_final → , id id_final",
            "id_final → ε",
            "lista_expr → expresion lista_exprfinal",
            "lista_exprfinal → , expresion lista_exprfinal",
            "lista_exprfinal → ε",
            "expresion → expr_arit expr_final",
            "expr_final → operador expr_arit expr_final",
            "expr_final → ε",
            "expr_arit → ( expresion )",
            "expr_arit → id",
            "expr_arit → enteros",
            "expr_arit → reales",
            "tipo → int",
            "tipo → float",
            "operador → +",
            "operador → -",
            "operador → *",
            "inicio → programa"
        };

        String filePath = "Programa.txt";
        Analizador_Lexico ObjAnalizador = new Analizador_Lexico();
        ObjAnalizador.analizadorLexicoFromFile(filePath);
        // Realizar el análisis léxico desde el archivo
//        String[] inputTokens = ObjAnalizador.simbolos.toArray(new String[ObjAnalizador.simbolos.size()]);
        //String[] inputTokens = ObjAnalizador.simbol;
        //ArrayList<String> algo=ObjAnalizador.simbolos;
        String[] inputTokens = ObjAnalizador.simbolos.toArray(new String[ObjAnalizador.simbolos.size()]);
        // Mostrar los tokens resultantes
        // Definir la entrada corregida
        //  String[]  = tokens;
        // Inicializar la pila y agregar el símbolo inicial

        //pd.push("$");
        pd.push(es.calcularInicio());
        //System.out.println("");

        // Inicializar las variables x y a
        String x = pd.peek();
        String a = inputTokens[0];
        pd.imprimirPila();
        int i = 0;
        while (!pd.empty()) {
            if (x.equals(a)) {
                pd.pop();
                i++;
                if (i < inputTokens.length) {
                    a = inputTokens[i];
                } else {
                    a = "$";
                }
            } else if (ro.isNonTerminal(x) || ro.isNonTerminal(a)) {
                // Obtener el índice de producción de la tabla de análisis predictivo
                int ntermix = ro.getNonTerminalIndex(x);
                int termix = ro.getTerminalIndex(a);
                int productionIndex = MatrizPredictiva[ntermix][termix];
                if (productionIndex != 0) {
                    String production = productionRules[productionIndex - 1];
                    // Dividir la regla de producción en símbolos individuales
                    String[] symbols = production.split(" → ")[1].split(" ");
                    pd.pop();
                    // Empilar los símbolos en orden inverso
                    for (int j = symbols.length - 1; j >= 0; j--) {
                        if (!symbols[j].equals("ε")) {
                            pd.push(symbols[j]);
                        }
                    }
                } else {
                    System.out.println("Error de sintaxis en la entrada");
                    break;
                }
            } else {
                System.out.println("Error de sintaxis en la entrada");
                break;
            }
            if(!pd.empty()){
            x = pd.peek();}
             pd.imprimirPila();
             System.out.println("token: "+a);
             System.out.println("---------");
        }
 //Verificar si la pila está vacía y se ha procesado toda la entrada
        if (pd.empty() && i == inputTokens.length) {
            System.out.println("La entrada es válida");
        } else {
            System.out.println("La entrada es inválida");
        }
       
    }

}
