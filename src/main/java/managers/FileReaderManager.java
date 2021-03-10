package managers;

import dataprovider.ConfigFileReader;

/*The duty of the class is to limit the number of object creation of the ConfigFileReader class */
public class FileReaderManager {
    private static final FileReaderManager fileReaderManager = new FileReaderManager();
    private static ConfigFileReader configFileReader;

    /*Constructor private to restrict instantiation of the class from other classes*/
    private FileReaderManager() {

    }

    public static FileReaderManager getInstance() {
        return fileReaderManager;
    }

    /*This method returns the instance of the ConfigFileReader but this method is not static. So that client has to use the getInstance()
    method to access other public methods of the FileReaderManager*/
    public ConfigFileReader getConfigReader() {
        return (configFileReader == null) ? new ConfigFileReader() : configFileReader;
    }
}
