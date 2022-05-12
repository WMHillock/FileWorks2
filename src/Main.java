import java.io.*;
import java.util.zip.*;

public class Main {

    public static void main(String[] args) {
        GameProgress[] mySave = new GameProgress[]{
                new GameProgress(100, 2, 1, 12.5),
                new GameProgress(85, 3, 3, 29.5),
                new GameProgress(46, 5, 7, 47.5)
        };

        String gamePathSave = "C:/Games/savegames/save";
        String gamePathSaveZIP = "C:/Games/savegames/save.zip";

        saveGame(gamePathSave, mySave);
        File directory = new File("C:/Games/savegames/");

        File[] fileList = null;

        if (directory.isDirectory()) {
            fileList = directory.listFiles();
        }
        zipFiles(gamePathSaveZIP, fileList);
    }

    public static void saveGame(String path, GameProgress[] mySave) {
        for (int i = 0; i < mySave.length; i++ ) {
            try (FileOutputStream fileSave = new FileOutputStream(path + i + ".dat");
                 ObjectOutputStream obj = new ObjectOutputStream(fileSave);) {

                obj.writeObject(mySave[i]);

            } catch (IOException e) {
                System.out.println(e.getMessage(
                ));
            }
        }

    }
    public static void zipFiles(String zipSavePath, File[] fileList) {

        try(ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipSavePath))) {
            for (File file : fileList) {
                FileInputStream fileInputStream = new FileInputStream(file.getPath());
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zipOutputStream.putNextEntry(zipEntry);

                byte[] buffer = new byte[fileInputStream.available()];
                fileInputStream.read(buffer);
                zipOutputStream.write(buffer);
                zipOutputStream.closeEntry();

                fileInputStream.close();
            }
        } catch (IOException e) {
            e.getMessage();
        }

        for (File path : fileList) {
            path.delete();
        }
    }
}