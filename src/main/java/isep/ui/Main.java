package isep.ui;

import java.util.Properties;
import isep.controller.App;
import isep.ui.MainMenuUI;

public class Main {
  public static void main(String[] args) {
    try {
      MainMenuUI menu = new MainMenuUI();
      menu.run();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
