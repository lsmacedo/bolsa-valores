package util;

import exception.InvalidValueException;
import java.util.Scanner;

public class InputController {
    
    public static String readLine(String message) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print(message + "\n>> ");
        return scanner.nextLine();
    }
    
    public static int readInteger(String message) throws InvalidValueException {
        String strValue = readLine(message);
        try { 
            return Integer.parseInt(strValue); 
        }
        catch (NumberFormatException e) { throw new InvalidValueException(); }
    }
    
    public static float readFloat(String message) throws InvalidValueException {
        String strValue = readLine(message);
        try { 
            return Float.parseFloat(strValue); 
        }
        catch (NumberFormatException e) { throw new InvalidValueException(); }
    }
    
}
