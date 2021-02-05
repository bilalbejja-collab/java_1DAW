/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capitulo8.Utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BILAL
 */
public class ConfigDB {

    public static void saveConfigFile(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            Properties props = new Properties();
            props.setProperty("user", "bilal");
            props.setProperty("password", "zaio2016");
            props.setProperty("host", "localhost");
            props.setProperty("port", "3306");
            props.setProperty("database", "javadb");
            props.store(writer, "host settings");

        } catch (FileNotFoundException ex) {
            System.err.println("El fichero "+fileName+ " no existe");

        } catch (IOException ex) {
            Logger.getLogger(ConfigDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Properties loadConfigFile(String fileName) {
        try (FileReader reader = new FileReader(fileName)) {
            Properties props = new Properties();
            props.load(reader);
            return props;
          
        } catch (FileNotFoundException ex) {
            System.err.println("El fichero "+fileName+ " no existe");
        } catch (IOException ex) {
            System.out.println("Error I/O");
        }
        return null;
    }
}
