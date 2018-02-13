package src.main.java.admin.utils;

import java.util.Scanner;

public class NumberToLetterConverterImplementacion {

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    
    Scanner keyboard = new Scanner(System.in);
    System.out.println("enter an integer");
   int myint = keyboard.nextInt();
    //String myString = keyboard.next();
    
    NumberToLetterConverter nTL = new NumberToLetterConverter();
    
    String result = nTL.convertNumberToLetter(myint).toLowerCase();
    
   // String result = nTL.convertNumber(myString);
    
    
    
    System.out.println(result);

  }

}
