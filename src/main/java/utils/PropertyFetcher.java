package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by hfathelb on 9/6/15.
 */
public class PropertyFetcher {

  private String propertyfilePath;
  private Properties props;

  public PropertyFetcher(String propertyFilePath) {
    this.propertyfilePath = propertyFilePath;
    this.props = new Properties();
  }

  public boolean loadProperties() {
    InputStream input = null;
    try {
      input = new FileInputStream("config.properties");
      // load a properties file
      props.load(input);
      return true;

    } catch (IOException ex) {
      ex.printStackTrace();
    } finally {
      if (input != null) {
        try {
          input.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    return false;
  }

  public String getPropertyValue(String propertyName) {
    return props.getProperty(propertyName);
  }

}
