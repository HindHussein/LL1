//Hind Hussein 1202416
package com.example.compilerfinalproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class LL1Scanner {
    private ArrayList<CharactersAndLine> CharactersAndLineNum; //identifying a variable from the class charactersAndLine to store the token and which line its at
    private HashSet<String> wordsOfReserved; //identifying a hashset to store all the reserved words
    int lineNum = 0; //identifying an integer for the number of lines
    private File file; //identifying the file for when the user chooses one

    public LL1Scanner() { //a default constructor
    }

    public LL1Scanner(File file){ //a constructor with a file that will be called in the main when a user chooses the file
        this.file = file;
        CharactersAndLineNum = new ArrayList<>();
        System.out.println("hello: " + file);
    }

    public ArrayList<CharactersAndLine> scan() throws FileNotFoundException {
        wordsOfReserved = new HashSet<>(Set.of("then", "else", "while", "loop", "exit", "end", "const", "var",
                                               "integer", "real", "char", "procedure", "if", "readreal", "readchar", "readln",
                                               "writeint", "writereal", "writechar", "writeln", "do", "call", "until", "readint",
                                               "module", "begin")); //storing all the reserved words as a set of strings for the hashset

        System.out.println(wordsOfReserved); //debugging for confirmation on correctness

        Scanner scanner = new Scanner(file); //scan the file that user chose
        lineNum = 1; //initializing the line number to start from 1

        while(scanner.hasNext()) { //loop that continues until scanner has no next string
            String line = scanner.nextLine(); //storing every line into a string known as line
            System.out.println(lineNum + " " + line);
            charOnEachLine(line); //calling a function with each line to divide each special character and word to letter from each other and determine what character it is
            lineNum++; //after every line, increment by 1 to note that its on the next line
        }

        nextChar(CharactersAndLineNum); //calling a function that determines whether the token is a regular word or a special character

        for(int i=0; i<CharactersAndLineNum.size(); i++){ //a loop that was used for debugging and shows all the characters
            System.out.println("Here's character" + (i+1) + " " + CharactersAndLineNum.get(i));
        }

        return CharactersAndLineNum; //the function returns the characters in the correct format with the line number

    }

    private void nextChar(ArrayList<CharactersAndLine> charactersAndLineNum) { //a function that determines whether the character is a normal word or special character in a cetain format
        ArrayList<CharactersAndLine> nextChar = new ArrayList<>(); //identifying an array of list for every char known as nextChar
        for (int i = 0; i < charactersAndLineNum.size(); i++) { //a for loop that will loop through all the characters
            CharactersAndLine charactersAndLine = charactersAndLineNum.get(i); //getting every character and its line, and storing it in the variable from CharactersAndLine known as charactersAndLine
            String characters = charactersAndLine.getCharacters(); //getting every character and storing it in the variable from CharactersAndLine known as characters

            if (isFunctionName(characters)) { //checking if the character is a function name or not
                String firstOfWord = characters.substring(0, characters.length() - 1); //dividing the character into two parts, first is the function name
                String secondOfWord = characters.substring(characters.length() - 1); //second part of the token is the . after the function name
                nextChar.add(new CharactersAndLine(charactersAndLine.getLineNum(), firstOfWord)); //adding the function name into the arraylist of characters and line
                nextChar.add(new CharactersAndLine(charactersAndLine.getLineNum(), secondOfWord)); //adding the . into the arraylist of characters and line
                System.out.println("On first if: " + characters); //debugging code to determine correctness
            }
            else if (isEqual(i, characters, charactersAndLineNum)) { //checking if the character is equals or not
                nextChar.add(new CharactersAndLine(charactersAndLine.getLineNum(), ":=")); //adding the entire token that is := into the arraylist of characters and line
                System.out.println("On second if: " + nextChar.get(nextChar.size()-1)); //debugging code to determine correctness
                i++; //increment i by 1 because the equals and : were considered as one token
            }
            else if (isLessThan(i, characters, charactersAndLineNum)) { //checking if the character is less than or not
                nextChar.add(new CharactersAndLine(charactersAndLine.getLineNum(), "<=")); //adding the entire token that is <= into the arraylist of characters and line
                System.out.print("On third if: " + characters); //debugging code to determine correctness
                i++; //increment i by 1 because the equals and < were considered as one token
            }
            else if (isGreaterThan(i, characters, charactersAndLineNum)) { //checking if the character is greater than or not
                nextChar.add(new CharactersAndLine(charactersAndLine.getLineNum(), ">=")); //adding the entire token that is >= into the arraylist of characters and line
                System.out.println("On fourth if: " + characters); //debugging code to determine correctness
                System.out.println("On last char: " + nextChar.get(charactersAndLine.getLineNum())); //debugging code to determine correctness
                i++; //increment i by 1 because the equals and > were considered as one token
            }
            else {
                System.out.println("On else: " +characters); //if the character ends up not being the function name or any of the special characters mentioned then it falls in the else category
                nextChar.add(charactersAndLine); //add this singular token into the characters and line arraylist
            }
        }
        nextChar.add(new CharactersAndLine(256, "$")); //adding the $ special token to determine a last character
        charactersAndLineNum.clear(); //if theres anything in the character and line arraylist then clear it
        charactersAndLineNum.addAll(nextChar); //add into the characters and line arraylist the new char arraylist that was just done
    }

    private boolean isEqual(int indexOfChar, String characters, ArrayList<CharactersAndLine> charactersAndLineNum){ //a function that determines whether the token is equals or not
        if(characters.equals(":") && indexOfChar + 1 < charactersAndLineNum.size() && charactersAndLineNum.get(indexOfChar + 1).getCharacters().equals("=")){ //ensuring that the first part of the token is : while the second is =
            return true; //if it is in fact := then its equals and therefore true
        }
        return false; //return false if it isnt := meaning not equal
    }

    private boolean isLessThan(int indexOfChar, String characters, ArrayList<CharactersAndLine> charactersAndLineNum){ //a function that determines whether the token is less than or not
        if(characters.equals("<") && indexOfChar + 1 < charactersAndLineNum.size() && charactersAndLineNum.get(indexOfChar + 1).getCharacters().equals("=")){ //ensuring that the first part of the token is < while the second is =
            return true; //if it in fact <= then its equal and therefore true
        }
        return false; //return false if it isnt <= meaning not less than
    }

    private boolean isGreaterThan(int indexOfChar, String characters, ArrayList<CharactersAndLine> charactersAndLineNum){ //a function that determines whether the token is greater than or not
        if(characters.equals(">") && indexOfChar + 1 < charactersAndLineNum.size() && charactersAndLineNum.get(indexOfChar + 1).getCharacters().equals("=")){ //ensuring that the first part of the token is > while the second is =
            return true; //if it in fact >= then its equal and therefore true
        }
        return false; //return false if it isnt >= meaning not greater than
    }

    private void charOnEachLine(String line) { //splits the characters from each other
        line = line.replaceAll("\\s+", " "); //puts a white space for anything that has alot of space and stores back into line which is string
        String[] specialCharactersOfLine = line.split("(?=[()\\-+*/=;,:|])|\\s+|(?<=[()\\-+*/=;,:|])"); //split the string into a bunch of different tokens to ensure every token is stored in an individual place in the array of strings

        for(int i=0; i<specialCharactersOfLine.length; i++){ //a for loop for every character
            determineCharacter(specialCharactersOfLine[i]); //call the function that determines what exactly this function is
        }
    }

    private void determineCharacter(String specialCharactersOfLine) { //a function that determines what the character is exactly
        if(specialCharactersOfLine.equals("")){ //if the character does not equal anything
            return; //then return it
        }
            if (isDecimal(specialCharactersOfLine)) { //if the token is float or double
                CharactersAndLineNum.add(new CharactersAndLine(lineNum, specialCharactersOfLine)); //then add it into the arraylist alongside the line number
            } else if (isInteger(specialCharactersOfLine)) { //if the token is an integer
                CharactersAndLineNum.add(new CharactersAndLine(lineNum, specialCharactersOfLine)); //then add it into the arraylist alongside the line number
            } else if (wordsOfReserved.contains(specialCharactersOfLine)) { //if the token is a reserved word
                CharactersAndLineNum.add(new CharactersAndLine(lineNum, specialCharactersOfLine)); //then add it into the arraylist alongside the line number
            } else if (isOperator(specialCharactersOfLine)) { //if the token is an operator
                CharactersAndLineNum.add(new CharactersAndLine(lineNum, specialCharactersOfLine)); //then add it into the arraylist alongside the line number
            } else if (isSpecialCharacter(specialCharactersOfLine)) { //if the token is a special character
                CharactersAndLineNum.add(new CharactersAndLine(lineNum, specialCharactersOfLine)); //then add it into the arraylist alongside the line number
            } else if (Character.isLetter(specialCharactersOfLine.charAt(0))) { //if the character is an individual letter
                if (isModOrDiv(specialCharactersOfLine)) { //if the token is a mod or div
                    CharactersAndLineNum.add(new CharactersAndLine(lineNum, specialCharactersOfLine)); //then add it into the arraylist alongside the line number
                } else { //if the token is an individual letter
                    char[] specialCharactersArray = specialCharactersOfLine.toCharArray(); //make the string an array of characters
                    int specialPosition = checkForSpecialCharacters(specialCharactersArray); //returns either the index if its not a digit or letter or ., or returns -1 if its neither of anything
                    if (specialPosition == -1) { //if its -1 that means its a regular word
                        CharactersAndLineNum.add(new CharactersAndLine(lineNum, specialCharactersOfLine)); //and add it into the arraylist of characters and line number
                    } else { //if it returns an index
                        while (specialPosition != -1) { //then loop while the index of the token is not -1
                            char[] firstPartOfArray = Arrays.copyOfRange(specialCharactersArray, 0, specialPosition); //the first part will be between zero and special position starting from -1 for the characters of array
                            char[] secondPartOfArray = Arrays.copyOfRange(specialCharactersArray, specialPosition, specialCharactersArray.length); //the second part will be from special position -1 until the length of the entire characters of array for the characters of array

                            if (firstPartOfArray.length > 0) { //if the first part of array's length greater than 0
                                determineCharacter(String.valueOf(firstPartOfArray)); //then determine which character it is
                            }

                            if (secondPartOfArray.length > 0) { //if the second part of array's length greater than 0
                                determineCharacter(String.valueOf(secondPartOfArray)); //then determine which character it is
                            }

                            break;
                        }

                    }
                }
            }
    }

    public int checkForSpecialCharacters(char[] divideTokenToChar) { //function that determines whether the token is a letter digit or . then returns its index if not, or -1 if neither
        for (int i = 0 ; i<divideTokenToChar.length ; i++) { //a for loop to loop through the entire array of characters
            if(Character.isLetterOrDigit(divideTokenToChar[i] )|| divideTokenToChar[i] == '.') { //if the character is a letter or digit or .
                        // then return nothing
            } else
                return i; //return the index of the character if it is
        }

        return -1; //return -1 if nothing is returned from the above code
    }

    private boolean isDecimal(String token){ //a method that returns true or false dependent on if token matches the format of a float or decimal
        return token.matches("\\d+\\.\\d+");
    }

    private boolean isInteger(String token){ //a method that returns true or false dependent on if token matches the format of a integer
        return token.matches("\\d+");
    }

    private boolean isOperator(String token){ //a method that returns true or false dependent on if token matches the format of the operators
        if(token.matches(":=")) //if the token matches the equals operator then return true
            return true;

        else if(token.matches("\\+|\\-|\\*|\\/")) //if the token matches the + - * or / then it returns true
            return true;

        else if(token.matches("mod|div")) //if the token matches mod or div then it returns true
            return true;

        else if (token.matches("=|<=|<|>=|>|\\|=")) //if the token matches = <= >= > = \\ then return true
            return true;


        return false;
    }

    private boolean isSpecialCharacter(String token){ //a method that returns true or false dependent on if token matches the format of special characters
        return token.matches("\\;|\\,|\\(|\\)|\\[|\\]|:|\\.");
    }
    
    private boolean isModOrDiv(String token){ //a method that returns true or false dependent on if token matches the format of a mod or divide
        return token.equals("mod") || token.equals("div");
    }

    private boolean isFunctionName(String token){ //a method that returns true or false dependent on if token matches the format of a function name
        if(token.length() > 1 && token.endsWith(".")){
            return true;
        }
        return false;
    }
}

class CharactersAndLine { //class for the characters and their line

    private String characters; //identify a string for the character
    private int lineNum; //identify an int for lineNum for the chracter

    public CharactersAndLine(int lineNum, String characters) { //constructor for  CharactersAndLine class
        this.lineNum = lineNum;
        this.characters = characters;
    }

    public String getCharacters() { //getter for characters
        return characters;
    }

    public void setCharacters(String characters) { //setter for characters
        this.characters = characters;
    }

    public int getLineNum() { //getter for line number
        return lineNum;
    }

    public void setLineNum(int lineNum) { //setter for line number
        this.lineNum = lineNum;
    }

    @Override
    public String toString() { //a toString method for print the characters and line number
        return "CharactersAndLine{" +
                "characters='" + characters + '\'' +
                ", lineNum=" + lineNum +
                '}';
    }
}
