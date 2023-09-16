import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение:");
        String stringInput = scanner.nextLine();
        String result = calc(stringInput);
        System.out.println(result);
    }

    public static String calc(String input) throws Exception {
        input = input.replaceAll(" ", "");
        Character[] operations = new Character[]{'+', '-', '/', '*'};
        String operation = null;
        int countOperation = 0;
        for (int i = 0; i < input.length(); i++) {
            for (int j = 0; j < operations.length; j++) {
                if (input.charAt(i) == operations[j]) {
                    operation = String.valueOf(operations[j]);
                    countOperation++;
                }
            }
        }
        if (countOperation > 1) {
            throw new  Exception ("Формат матиматической операции не удолетворяет заданию");
        }
        if (operation == null) {
            throw new Exception("Не поддерживаемая операция между числами");
        }
        String[] numbers = input.split("[" + operation + "]");
        String numberOne = numbers[0];
        String numberTwo = numbers[1];
        Roman roman = new Roman();
        boolean isRomanNumber = roman.isRoman(numberOne) && roman.isRoman(numberTwo);
        boolean isArabNumber = !roman.isRoman(numberOne) && !roman.isRoman(numberTwo);
        if (!isRomanNumber && !isArabNumber) {
            throw new Exception("Используются разные системы счисления");
        }
        Integer one;
        Integer two;
        if (isRomanNumber) {
            one = roman.romanToArab(numberOne);
            two = roman.romanToArab(numberTwo);
        } else {
            one = Integer.parseInt(numberOne);
            two = Integer.parseInt(numberTwo);
        }
        if ((one == null || one > 10 || one < 1) || (two == null || two > 10 || two < 1)) {
            throw new Exception("Вводимые значенич должны быть от 1 до 10");
        }

        int result = 0;
        switch (operation) {
            case "+": {
                result = one + two;
                break;
            }
            case "-": {
                result = one - two;
                break;
            }
            case "*": {
                result = one * two;
                break;
            }
            case "/": {
                result = one / two;
            }
        }

        if (isRomanNumber) {
            if (result < 1) {
                throw new Exception("В римкой системе счисление значение не может быть меньше 1");
            }
            return roman.arabicToRoman(result);
        }
        return String.valueOf(result);
    }
}

class Roman {
    Map<String, Integer> romanToArab = new HashMap<>(Map.of("I", 1, "II", 2, "III", 3,
            "IV", 4, "V", 5, "VI", 6, "VII", 7, "VIII", 8, "IX", 9,
            "X", 10));
    String[] romanChar = new String[]{"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
    int[] romanNum = new int[]{100, 90, 50, 40, 10, 9, 5, 4, 1};

    public boolean isRoman(String number) {
        char oneSymbol = number.charAt(0);
        return Arrays.asList('M', 'C', 'D', 'X', 'L', 'V', 'I').contains(oneSymbol);
    }
    public Integer romanToArab(String number) {
        return romanToArab.get(number);
    }
    public String arabicToRoman(int number) {
        StringBuilder ret = new StringBuilder();
        int ind = 0;
        while (ind < romanChar.length) {
            while (number >= romanNum[ind]) {
                var d = number / romanNum[ind];
                number = number % romanNum[ind];
                for (int i = 0; i < d; i++)
                    ret.append(romanChar[ind]);
            }
            ind++;
        }
        return ret.toString();
    }
}
