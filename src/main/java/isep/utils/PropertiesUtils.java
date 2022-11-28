package isep.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import isep.shared.exceptions.MissingMandatoryPropertyException;
import isep.shared.Constants;

public class PropertiesUtils {
  private static void checkMandatoryProperties(Properties props) throws MissingMandatoryPropertyException {
    // if (props.getProperty(Constants.PARAMS_DATA_FOLDER_PATH) == null)
    //   throw new MissingMandatoryPropertyException("Missing mandatory property: " + Constants.PARAMS_DATA_FOLDER_PATH);
  }

  public static Properties getProperties() {
    Properties props = new Properties();

    // Read configured values
    try {
      InputStream in = new FileInputStream(Constants.PARAMS_FILENAME);
      props.load(in);
      in.close();
    } catch (IOException ex) {
      System.out.println("Error reading properties file:!");
      System.out.println(
          "Ensure that the file " + Constants.PARAMS_FILENAME + " exists and the path is correct.");
    }

    try {
      checkMandatoryProperties(props);
    } catch (MissingMandatoryPropertyException ex) {
      System.out.println(ex.getMessage());
      System.exit(1);
    }

    return props;
  }
}
