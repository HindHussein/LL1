//Hind Hussein 1202416
package com.example.compilerfinalproject;

import java.util.ArrayList;
import java.util.Stack;

public class LL1Parser {
    private Stack<String> stackOfParser; //identify a stack for pushing the tokens into known as stackOfParser
    private LL1Table ll1Table; //identify an LL1Table as a variable

    public LL1Parser(){ //constructor for the LL1 parser
        ll1Table = new LL1Table();
        stackOfParser = new Stack<>();
        stackOfParser.push("$"); //pushes the last item which is $ into the stack
        stackOfParser.push("module-decl"); //pushes the first item that will popped from the stack
    }

    public Stack<String> getStackOfParser() { //getter for the stack
        return stackOfParser;
    }

    public void setStackOfParser(Stack<String> stackOfParser) { //setter for the stack
        this.stackOfParser = stackOfParser;
    }

    public LL1Table getLl1Table() { //getter for the table of terminals and nonterminals
        return ll1Table;
    }

    public void setLl1Table(LL1Table ll1Table) { //setter for the table terminals and nonterminals
        this.ll1Table = ll1Table;
    }

    public String parserLL1(ArrayList<CharactersAndLine> charactersFromCode){
        System.out.println("Hind Hussein 1202416"); //debugging to determine correctness
        ArrayList<CharactersAndLine> correctCharacters = new ArrayList<>(charactersFromCode); //identifying an arraylist of characters and line
        int indexOfChar = 0; //initializing an int to act as the index of the character

        String lastPushedStackItem = ""; //initializing the string for the item that is to be pushed last into the stack
        String printToUser = ""; //initializing the string that will be shown as a label to the user

        while(!stackOfParser.isEmpty()){ //a while loop that loops until the stack is empty
            System.out.println("Hind Hussein 1202416 in Loop"); //debugging to determine correctness
            CharactersAndLine character = charactersFromCode.get(indexOfChar); //get the index of the character necessary
            String characters = character.getCharacters(); //get the character that was chosen by the index and store it in the characters string

            if(characterIsRealNumber(characters) && !characterIsTerminal(characters)){ //if the string is a real number and terminal then the character is a real value
                characters = "real-value";
            } else if(characterIsName(characters) && !characterIsTerminal(characters)){ //if the string is a name  and terminal then the character is a name
                characters = "name";
            } else if(characterIsAnInteger(characters) && !characterIsTerminal(characters)){ //if the string is an integer and terminal then the character is an integer
                characters = "integer-value";
            }

            lastPushedStackItem = stackOfParser.pop(); // pop the last item in stack onto the lastPushedStackItem string
            System.out.println(lastPushedStackItem); //debugging to determine correctness

            if(characters.equals("$") && lastPushedStackItem.equals("$")){ //if both character and last popped item from the stack are both $ then its ready to go and the code was parsed correctly
                System.out.println("Hind Hussein 1202416: Ready to go"); //debugging to determine correctness
                printToUser = "The Code has been Parsed Successfully!!"; //inform user that the code has been parsed correctly
                return printToUser; //return the label
            } else if (characterIsTerminal(lastPushedStackItem)){ //if the character is terminal
                System.out.println("its terminal"); //debugging to determine correctness
                if(lastPushedStackItem.equals(characters) && indexOfChar < charactersFromCode.size()){ //if the characters and last pushed item are equal and the index is less than the size of the amount of characters
                    indexOfChar++; //increment the index
                } else { //if the characters and last pushed item are NOT equal or/and the index is NOT less than the size of the amount of characters
                    if (correctCharacters.get(indexOfChar).getCharacters().equals("$")) { //if the character equals to $ meaning it has reached the bottom, then the code is not complete
                        System.out.println("Syntax Error: Code Cannot Complete"); //debugging to determine correctness
                        printToUser="Syntax Error: Code Cannot Complete"; //inform user that the code has not been completed
                    } else {
                        System.out.println("stack  on top: " + lastPushedStackItem); //debugging to determine correctness
                        System.out.println("most recent token: " + characters); //debugging to determine correctness

                        System.out.println("Syntax error -- > at Line "+character.getLineNum()+" : There's an Unexpected Token " + correctCharacters.get(indexOfChar).getCharacters()); //inform user theres a syntax error and which line that syntax error is showing up on
                        printToUser="Syntax error -- >  at Line "+character.getLineNum()+" : There's an Unexpected Token " + correctCharacters.get(indexOfChar).getCharacters(); //debugging to determine correctness
                    }
                    return printToUser; //return the label

                }
            } else if (characterIsNonTerminal(lastPushedStackItem)) { //if the character is nonterminal
                String productionRule = getProductionRule(characters, lastPushedStackItem); //then get the production rules for the terminal and nonterminal of that specific character
                System.out.println("stack item: " + lastPushedStackItem + " " + "its rule: " + productionRule); //debugging to determine correctness
                if (productionRule != null) { //if the production rule is not null
                    if (!productionRule.equals("")) { //and not empty
                        String[] productionSymbols = productionRule.split("\\s+"); //then split the code for every white space that exists
                        for (int i = productionSymbols.length - 1; i >= 0; i--) { //and loop through the entire production rules for that specific nonterminal with its terminal
                            stackOfParser.push(productionSymbols[i]); //and store all the
                        }
                    }
                } else { //if its not null
                    if (correctCharacters.get(indexOfChar).getCharacters().equals("$")) { //if the character equals to $ meaning it has reached the bottom, then the code is not complete
                        System.out.println("Syntax Error: Code Cannot Complete"); //debugging to determine correctness
                        printToUser="Syntax Error: Code Cannot Complete"; //inform user that the code has not been completed
                    } else { //if its not equal to $
                        System.out.println("Syntax error -- > at Line "+character.getLineNum()+": There's an Unexpected Token " + correctCharacters.get(indexOfChar).getCharacters()); //debugging to determine correctness
                        printToUser="Syntax error -- > at Line "+character.getLineNum()+": There's an Unexpected Token " + correctCharacters.get(indexOfChar).getCharacters(); //inform user theres a syntax error and which line that syntax error is showing up on
                    }
                    return printToUser; //return the label
                }
            } else { //if the character is not terminal or terminal
                System.out.println("Syntax error: There's an Unexpected Symbol '" + lastPushedStackItem + "'"); //debugging to determine correctness
                printToUser="Syntax error: There's an Unexpected Symbol " + lastPushedStackItem + "'"; //inform user that the symbol given in their code is not expected
                return printToUser; //return the label
            }
        }

        return printToUser; //return the label
    }

    private boolean characterIsTerminal(String character){ //function that determines whether the token is a terminal or not
        for(int i=0; i<ll1Table.getTerminals().length; i++){
            if(ll1Table.getTerminals()[i].equals(character)){
                return true;
            }
        }
        return false;
    }

    private boolean characterIsNonTerminal(String character){ //function that determines whether the token is a nonterminal or not
        for(int i=0; i<ll1Table.getNonTerminals().length; i++){
            if(ll1Table.getNonTerminals()[i].equals(character)){
                return true;
            }
        }
        return false;
    }

    private boolean characterIsName(String character){ //function that determines whether the token matches the format of a name or not
        if(character.matches("[a-zA-Z][a-zA-Z0-9]*")){
            return true;
        }
        return false;
    }

    public boolean characterIsRealNumber(String character) { //function that determines whether the token matches the format of a real number or not
        if(character.matches("\\d+\\.\\d*|\\d*\\.\\d+")){
            return true;
        }
        return false;
    }

    private boolean characterIsAnInteger(String character){ //function that determines whether the token matches the format of an integer or not
        if(character.matches("\\d+")){
            return true;
        }

        return false;
    }

    private String getProductionRule(String terminal, String nonTerminal){ //method that gets the production rule based on which terminal and nonterminal is needed
        int terminalPosition = -1, nonTerminalPosition = -1; //start with terminal and nonterminal at -1
        for(int i=0; i<ll1Table.getTerminals().length; i++){ //a loop through terminals in the table to determine whether the terminal given is equal to the terminals in the table
            if(ll1Table.getTerminals()[i].equals(terminal)){
                terminalPosition = i; //if they are equal then terminal becomes equal to that index
                break;
            }
        }
        for(int i=0; i<ll1Table.getNonTerminals().length; i++){ //a loop through nonterminals in the table to determine whether the nonterminals given is equal to the nonterminals in the table
            if(ll1Table.getNonTerminals()[i].equals(nonTerminal)){
                nonTerminalPosition = i; //if they are equal then nonterminals becomes equal to that index
                break;
            }
        }

        if (terminalPosition != -1 && nonTerminalPosition != -1) { //if both terminal and nonterminal are -1 then there is no production rule for it
            System.out.println(ll1Table.LL1ParsingTable[nonTerminalPosition][terminalPosition]);
            return ll1Table.LL1ParsingTable[nonTerminalPosition][terminalPosition];
        }
        return null;

    }
}
