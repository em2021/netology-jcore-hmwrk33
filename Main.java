import java.io.*;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {

    public static void openZip(String pathName, String zipOutPath) {
        try (ZipInputStream zin = new ZipInputStream(
                new FileInputStream(pathName)
        )) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fout = new FileOutputStream(zipOutPath + name);
                for (int i = zin.read(); i != -1; i = zin.read()) {
                    fout.write(i);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static GameProgress openProgress(String pathName) {
        GameProgress gameProgress = null;
        try (FileInputStream fis = new FileInputStream(pathName);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return gameProgress;
    }

    public static void main(String[] args) {
        openZip("/Users/egor_m/Games/savegames/zip.zip", "/Users/egor_m/Games/savegames/");
        System.out.println(openProgress("/Users/egor_m/Games/savegames/save1.dat").toString());
        System.out.println(openProgress("/Users/egor_m/Games/savegames/save2.dat").toString());
        System.out.println(openProgress("/Users/egor_m/Games/savegames/save3.dat").toString());
    }
}