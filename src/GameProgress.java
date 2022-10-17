import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class GameProgress implements Serializable {

    private int health;
    private int weapons;
    private int lvl;
    private double distance;

    public GameProgress(int health, int weapons, int lvl, double distance) {
        this.health = health;
        this.weapons = weapons;
        this.lvl = lvl;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "GameProgress{" +
                "health=" + health +
                ", weapons=" + weapons +
                ", lvl=" + lvl +
                ", distance=" + distance +
                '}';
    }

    public void saveGame(String path, GameProgress progress) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oss = new ObjectOutputStream(fos)) {
            oss.writeObject(progress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void zipFiles(String path, List<String> listSaves) {
        for (int i = 0; i < listSaves.size(); i++) {
            String nameZip = "/zip" + i + ".zip";
            try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path + nameZip));
                 FileInputStream fis = new FileInputStream(listSaves.get(i))) {
                ZipEntry entry = new ZipEntry("save" + i + ".dat");
                zout.putNextEntry(entry);
                byte[] bytes = new byte[fis.available()];
                fis.read(bytes);
                zout.write(bytes);
                zout.closeEntry();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public  void openZip(String pathZip, String path) {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(pathZip));) {
            ZipEntry entry;
            String name;
            while ((entry = zis.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fos = new FileOutputStream(path+name);
                for (int i = zis.read(); i != -1; i = zis.read()) {
                    fos.write(i);
                }
                fos.flush();
                zis.closeEntry();
                fos.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public GameProgress openProgress(String path) {
        try (FileInputStream fis = new FileInputStream(path);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return  (GameProgress) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}