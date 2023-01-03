package isep.ui;

import java.util.Scanner;
import isep.controller.LoadBasketsController;

public class ImportBasketListUI implements Runnable {
  private LoadBasketsController controller;

  public ImportBasketListUI() {
    this.controller = new LoadBasketsController();
  }

  @Override
  public void run() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("\nInsert the file path: ");
    String filePath = scanner.nextLine();

    controller.setFilename(filePath);

    scanner.close();
  }
}
