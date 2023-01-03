package isep.ui;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import isep.controller.LoadBasketsController;

public class ImportBasketListUI implements Runnable {
  private LoadBasketsController ctrler;

  public ImportBasketListUI() {
    this.ctrler = new LoadBasketsController();
  }

  @Override
  public void run() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("\nInsert the file path: ");
    String filePath = scanner.nextLine();
    ctrler.setFilename(filePath);

    try {
      List<Map<String,String>> data =  ctrler.readData();
      ctrler.mapBaskets(data);
    } catch (FileNotFoundException e) {
      System.out.println("File does not exist.");
    }

    scanner.close();
  }
}
