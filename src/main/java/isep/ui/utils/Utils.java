package isep.ui.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Utils {

  static public String readLineFromConsole(String prompt) {
    try {
      System.out.print(prompt);

      InputStreamReader converter = new InputStreamReader(System.in);
      BufferedReader in = new BufferedReader(converter);

      return in.readLine();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  static public String readLineFromConsoleWithValidation(String prompt) {
    boolean valid = false;
    String answer = "";

    do {
      try {
        System.out.println("\n" + prompt);

        InputStreamReader converter = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(converter);

        answer = in.readLine();
      } catch (Exception e) {
        e.printStackTrace();
      }

      if (answer.length() > 0)
        valid = true;
      else
        System.out.println("Invalid input! Please try again.\n");
    } while (!valid);

    return answer;
  }

  static public int readIntegerFromConsole(String prompt) {
    do {
      try {
        String input = readLineFromConsole(prompt);

        int value = Integer.parseInt(input);

        return value;
      } catch (NumberFormatException ex) {
        System.out.println("Invalid input! Please try again.\n");
        // Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
      }
    } while (true);
  }

  static public int readPositiveIntegerFromConsole(String prompt) {
    while (true) {
      int n = readIntegerFromConsole(prompt);

      if (n > 0)
        return n;
      else
        System.out.println("\nThe value must be positive!");
    }
  }

  static public int readNonNegativeIntegerFromConsole(String prompt) {
    while (true) {
      int n = readIntegerFromConsole(prompt);

      if (n >= 0)
        return n;
      else
        System.out.println("\nThe value cannot be negative!");
    }
  }

  static public double readDoubleFromConsole(String prompt) {
    do {
      try {
        String input = readLineFromConsole(prompt);

        double value = Double.parseDouble(input);

        return value;
      } catch (NumberFormatException ex) {
        System.out.println("Invalid input! Please try again.\n");
        // Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
      }
    } while (true);
  }

  static public Date readDateFromConsole(String prompt) {
    do {
      try {
        String strDate = readLineFromConsole(prompt);

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        Date date = df.parse(strDate);

        return date;
      } catch (ParseException ex) {
        System.out.println("Invalid input! Please try again.\n");
        // Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
      }
    } while (true);
  }

  static public Date readHourDateFromConsole(String prompt) {
    do {
      try {
        String strDate = readLineFromConsole(prompt);

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Date date = df.parse(strDate);

        return date;
      } catch (ParseException ex) {
        System.out.println("Invalid input! Please try again.\n");
      }
    } while (true);
  }

  static public Date readDateInFutureFromConsole(String prompt) {
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    do {
      Date date = readDateFromConsole(prompt);

      try {
        if (date.after(df.parse(df.format(new Date())))
            || date.equals(df.parse(df.format(new Date()))))
          return date;
        System.out.println("The date must be in the future!\n");
      } catch (ParseException e) {
        System.out.println("Invalid input! Please try again.\n");
      }
    } while (true);
  }

  static public boolean confirm(String message) {
    String input;
    do {
      input = Utils.readLineFromConsole("\n" + message + "\n");
    } while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n"));

    return input.equalsIgnoreCase("y");
  }

  static public <E> Object showAndSelectOne(List<E> list, String header) {
    showList(list, header);
    return selectsObject(list);
  }

  static public <E> int showAndSelectIndex(List<E> list, String header) {
    showList(list, header);
    return selectsIndex(list);
  }

  static public Object showAndSelectOneEnum(Enum[] list, String header) {
    showEnumItems(list, header);
    return selectEnumObject(list);
  }

  static public <E> void showList(List<E> list, String header) {
    System.out.println(header);

    int index = 0;

    for (Object o : list) {
      index++;

      System.out.println(index + ". " + o.toString());
    }

    System.out.println("");
    System.out.println("0 - Cancel");
  }

  static public void showEnumItems(Enum[] items, String header) {
    System.out.println(header);

    int index = 0;
    for (Enum o : items) {
      index++;

      System.out.println(index + ". " + o.toString());
    }
    System.out.println("");
    System.out.println("0 - Cancel");
  }

  static public <E> Object selectsObject(List<E> list) {
    int value = 0;

    do {
      value = Utils.readIntegerFromConsole("Type your option: ");
    } while (value < 0 || value > list.size());

    if (value == 0)
      return null;
    else
      return list.get(value - 1);
  }

  static public Object selectEnumObject(Enum[] list) {
    int value = 0;

    do {
      value = Utils.readIntegerFromConsole("Type your option: ");
    } while (value < 0 || value > list.length);

    if (value == 0)
      return null;
    else
      return list[value - 1];
  }

  static public <E> int selectsIndex(List<E> list) {
    int value = -1;
    do {
      value = Utils.readIntegerFromConsole("Type your option: ");
    } while (value < 0 || value > list.size());

    return value - 1;
  }

  static public void showRightToLeftText(String title, String text) {
    final int MAX_WIDTH = 120;

    int nOfSpaces = MAX_WIDTH - title.length() - text.length() - 2;

    for (int i = 0; i < nOfSpaces; i++)
      System.out.print(" ");

    System.out.printf("%s: %s%n", title, text);
  }
}
