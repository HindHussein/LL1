//Hind Hussein 1202416
package com.example.compilerfinalproject;

public class LL1Table {

    //identifying all the terminals as given by the professor
    private final String[] terminals = {
            ">", "<", "<=", "|=", ">=", "*",
            ".", "-", "+", "/", ",", ";",
            ":=", "=", ":", "(", ")", "char",
            "integer", "const", "real-value", "name", "var", "real",
            "readint", "readln", "readchar", "readreal", "writechar", "writereal",
            "writeln", "writeint", "integer-value", "mod", "div", "module",
            "begin", "until", "while", "then", "if", "else",
            "do", "loop", "call", "procedure", "exit", "end"
    };

    //identifying all the non-terminals as given by the professor
    private final String[] nonTerminals = {
            "module-decl", "module-heading", "block", "declarations", "const-decl",
            "const-list", "var-decl", "var-list", "var-item", "name-list",
            "more-names", "data-type", "procedure-decl", "procedure-heading", "stmt-list",
            "statement", "ass-stmt", "exp", "exp-prime", "term",
            "term-prime", "factor", "add-oper", "mul-oper", "read-stmt",
            "write-stmt", "write-list", "more-write-value", "write-item", "if-stmt",
            "else-part", "while-stmt", "loop-stmt", "exit-stmt", "call-stmt",
            "condition", "relational-oper", "name-value", "value", "$"
    };

    //identifying a 2D array with the nonterminals for rows and terminals for coloumns
    public String[][] LL1ParsingTable = new String[nonTerminals.length][terminals.length];

    // entering all the production rules for all nonterminals with their terminals
    public LL1Table() {
        // module-decl production rule
        LL1ParsingTable[0][35] = "module-heading declarations block name .";

        //module-heading production rule
        LL1ParsingTable[1][35] = "module name ;";

        //block production rule
        LL1ParsingTable[2][36] = "begin stmt-list end";

        //declarations production rules
        LL1ParsingTable[3][19] = "const-decl var-decl procedure-decl";
        LL1ParsingTable[3][22] = "const-decl var-decl procedure-decl";
        LL1ParsingTable[3][36] = "const-decl var-decl procedure-decl";
        LL1ParsingTable[3][45] = "const-decl var-decl procedure-decl";

        //const-decl production rules
        LL1ParsingTable[4][19] = "const const-list";
        LL1ParsingTable[4][22] = "";
        LL1ParsingTable[4][36] = "";
        LL1ParsingTable[4][45] = "";

        //const-list production rules
        LL1ParsingTable[5][21] = "name = value ; const-list";
        LL1ParsingTable[5][22] = "";
        LL1ParsingTable[5][36] = "";
        LL1ParsingTable[5][45] = "";

        //var-decl production rules
        LL1ParsingTable[6][22] = "var var-list";
        LL1ParsingTable[6][36] = "";
        LL1ParsingTable[6][45] = "";

        //var-list production rules
        LL1ParsingTable[7][21] = "var-item ; var-list";
        LL1ParsingTable[7][36] = "";
        LL1ParsingTable[7][45] = "";

        //var-item production rule
        LL1ParsingTable[8][21] = "name-list : data-type";

        //name-list production rule
        LL1ParsingTable[9][21] = "name more-names";

        //more-names production rules
        LL1ParsingTable[10][10] = ", name-list";
        LL1ParsingTable[10][14] = "";
        LL1ParsingTable[10][16] = "";

        //data-type production rules
        LL1ParsingTable[11][17] = "char";
        LL1ParsingTable[11][18] = "integer";
        LL1ParsingTable[11][23] = "real";

        //procedure-decl production rules
        LL1ParsingTable[12][36] = "";
        LL1ParsingTable[12][45] = "procedure-heading declarations block name ; procedure-decl";

        //procedure-heading production rules
        LL1ParsingTable[13][45] = "procedure name ;";

        //stmt-list production rules
        LL1ParsingTable[14][11] = "statement ; stmt-list";
        LL1ParsingTable[14][21] = "statement ; stmt-list";
        LL1ParsingTable[14][24] = "statement ; stmt-list";
        LL1ParsingTable[14][25] = "statement ; stmt-list";
        LL1ParsingTable[14][26] = "statement ; stmt-list";
        LL1ParsingTable[14][27] = "statement ; stmt-list";
        LL1ParsingTable[14][28] = "statement ; stmt-list";
        LL1ParsingTable[14][29] = "statement ; stmt-list";
        LL1ParsingTable[14][30] = "statement ; stmt-list";
        LL1ParsingTable[14][31] = "statement ; stmt-list";
        LL1ParsingTable[14][36] = "statement ; stmt-list";
        LL1ParsingTable[14][37] = "";
        LL1ParsingTable[14][38] = "statement ; stmt-list";
        LL1ParsingTable[14][40] = "statement ; stmt-list";
        LL1ParsingTable[14][41] = "";
        LL1ParsingTable[14][43] = "statement ; stmt-list";
        LL1ParsingTable[14][44] = "statement ; stmt-list";
        LL1ParsingTable[14][46] = "statement ; stmt-list";
        LL1ParsingTable[14][47] = "";

        //statement production rules
        LL1ParsingTable[15][11] = "";
        LL1ParsingTable[15][21] = "ass-stmt";
        LL1ParsingTable[15][24] = "read-stmt";
        LL1ParsingTable[15][25] = "read-stmt";
        LL1ParsingTable[15][26] = "read-stmt";
        LL1ParsingTable[15][27] = "read-stmt";
        LL1ParsingTable[15][28] = "write-stmt";
        LL1ParsingTable[15][29] = "write-stmt";
        LL1ParsingTable[15][30] = "write-stmt";
        LL1ParsingTable[15][31] = "write-stmt";
        LL1ParsingTable[15][36] = "block";
        LL1ParsingTable[15][38] = "while-stmt";
        LL1ParsingTable[15][40] = "if-stmt";
        LL1ParsingTable[15][43] = "loop-stmt";
        LL1ParsingTable[15][44] = "call-stmt";
        LL1ParsingTable[15][46] = "exit-stmt";

        //ass-stat production rule
        LL1ParsingTable[16][21] = "name := exp";

        //exp production rules
        LL1ParsingTable[17][15] = "term exp-prime";
        LL1ParsingTable[17][20] = "term exp-prime";
        LL1ParsingTable[17][21] = "term exp-prime";
        LL1ParsingTable[17][32] = "term exp-prime";

        //exp-prime production rules
        LL1ParsingTable[18][7] = "add-oper term exp-prime";
        LL1ParsingTable[18][8] = "add-oper term exp-prime";
        LL1ParsingTable[18][11] = "";
        LL1ParsingTable[18][16] = "";

        //term production rules
        LL1ParsingTable[19][15] = "factor term-prime";
        LL1ParsingTable[19][20] = "factor term-prime";
        LL1ParsingTable[19][21] = "factor term-prime";
        LL1ParsingTable[19][32] = "factor term-prime";

        //term-prime production rules
        LL1ParsingTable[20][5] = "mul-oper factor term-prime";
        LL1ParsingTable[20][7] = "";
        LL1ParsingTable[20][8] = "";
        LL1ParsingTable[20][9] = "mul-oper factor term-prime";
        LL1ParsingTable[20][11] = "";
        LL1ParsingTable[20][16] = "";
        LL1ParsingTable[20][33] = "mul-oper factor term-prime";
        LL1ParsingTable[20][34] = "mul-oper factor term-prime";

        //factor production rules
        LL1ParsingTable[21][15] = "( exp )";
        LL1ParsingTable[21][20] = "name-value";
        LL1ParsingTable[21][21] = "name-value";
        LL1ParsingTable[21][32] = "name-value";

        //add-oper production rules
        LL1ParsingTable[22][7] = "-";
        LL1ParsingTable[22][8] = "+";

        //mull-oper production rules
        LL1ParsingTable[23][5] = "*";
        LL1ParsingTable[23][9] = "/";
        LL1ParsingTable[23][33] = "mod";
        LL1ParsingTable[23][34] = "div";

        //read-stmt production rules
        LL1ParsingTable[24][24] = "readint ( name-list )";
        LL1ParsingTable[24][25] = "readln";
        LL1ParsingTable[24][26] = "readchar ( name-list )";
        LL1ParsingTable[24][27] = "readreal ( name-list )";

        //write-stmt production rules
        LL1ParsingTable[25][28] = "writechar ( write-list )";
        LL1ParsingTable[25][29] = "writereal ( write-list )";
        LL1ParsingTable[25][30] = "writeln";
        LL1ParsingTable[25][31] = "writeint ( write-list )";

        //write-list production rules
        LL1ParsingTable[26][20] = "write-item more-write-value";
        LL1ParsingTable[26][21] = "write-item more-write-value";
        LL1ParsingTable[26][32] = "write-item more-write-value";

        //more-write-value production rules
        LL1ParsingTable[27][10] = ", write-list";
        LL1ParsingTable[27][16] = "";

        //write-item production rules
        LL1ParsingTable[28][20] = "value";
        LL1ParsingTable[28][21] = "name";
        LL1ParsingTable[28][32] = "value";

        //if-stmt production rule
        LL1ParsingTable[29][40] = "if condition then stmt-list else-part end";

        //else-part production rules
        LL1ParsingTable[30][41] = "else stmt-list";
        LL1ParsingTable[30][47] = "";

        //while-stmt production rule
        LL1ParsingTable[31][38] = "while condition do stmt-list end";

        //loop-stmt production rule
        LL1ParsingTable[32][43] = "loop stmt-list until condition";

        //exit-stmt production rule
        LL1ParsingTable[33][46] = "exit";

        //call-stmt production rule
        LL1ParsingTable[34][44] = "call name";

        //condition production rules
        LL1ParsingTable[35][20] = "name-value relational-oper name-value";
        LL1ParsingTable[35][21] = "name-value relational-oper name-value";
        LL1ParsingTable[35][32] = "name-value relational-oper name-value";

        //relational-oper production rules
        LL1ParsingTable[36][0] = ">";
        LL1ParsingTable[36][1] = "<";
        LL1ParsingTable[36][2] = "<=";
        LL1ParsingTable[36][3] = "|=";
        LL1ParsingTable[36][4] = ">=";
        LL1ParsingTable[36][13] = "=";

        //name-value production rules
        LL1ParsingTable[37][20] = "value";
        LL1ParsingTable[37][21] = "name";
        LL1ParsingTable[37][32] = "value";

        //value production rules
        LL1ParsingTable[38][20] = "real-value";
        LL1ParsingTable[38][32] = "integer-value";

    }

    public String[] getTerminals() { //getter for terminals
        return terminals;
    }

    public String[] getNonTerminals() { //getter for nonterminals
        return nonTerminals;
    }
}
