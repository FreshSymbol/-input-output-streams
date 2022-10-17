import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
     // Создание каталогов и файлов
        StringBuilder sb = new StringBuilder();
        File dirRoot = new File("C://Game");
        if (dirRoot.mkdir()) {
            sb.append("каталог ")
                    .append(dirRoot.getName())
                    .append(" создан")
                    .append("\n");
        }

        String[] dirs = {"src", "res", "savegames", "temp"};
        for (String s : dirs) {
            File dir = new File(dirRoot, s);
            if (dir.mkdir()) {
                sb.append("каталог ")
                        .append(dir.getName())
                        .append(" создан")
                        .append("\n");
            }
        }

        String[] srcDirs = {"main", "test"};
        for (String s : srcDirs) {
            File dir = new File(dirRoot + "/src",s);
            if (dir.mkdir()) {
                sb.append("каталог ")
                        .append(dir.getName())
                        .append(" создан")
                        .append("\n");
            }
        }

        String[] resDirs = {"drawbles", "vectors", "icons"};
        for (String s : resDirs) {
            File dir = new File(dirRoot + "/res", s);
            if (dir.mkdir()) {
                sb.append("каталог ")
                        .append(dir.getName())
                        .append(" создан")
                        .append("\n");
            }
        }

        File mainFile = new File(dirRoot+"/src/main/Main.java");
        File utilsFile = new File(dirRoot+"/src/main/Utils.java");
        File tempFile = new File(dirRoot+"/temp/temp.txt");
        File[] files = {tempFile, mainFile, utilsFile};
        try {
            for (File file : files) {
                if (file.createNewFile()) {
                    sb.append("файл ")
                            .append(file.getName())
                            .append(" создан")
                            .append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //

        String log = sb.toString();
        // Удаление файлов после архивации
        try (FileWriter fw = new FileWriter(tempFile)) {
            fw.write(log);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //

        GameProgress saves = new GameProgress(10, 3, 15, 2);
        GameProgress saves1 = new GameProgress(5, 1, 8, 5);
        GameProgress saves2 = new GameProgress(7, 2, 13, 7);

        saves.saveGame("C://Game/savegames/save0.dat", saves);
        saves1.saveGame("C://Game/savegames/save1.dat", saves1);
        saves2.saveGame("C://Game/savegames/save2.dat", saves2);

        List<String> listSaves = new ArrayList<>();
        listSaves.add("C://Game/savegames/save0.dat");
        listSaves.add("C://Game/savegames/save1.dat");
        listSaves.add("C://Game/savegames/save2.dat");

        saves.zipFiles("C://Game/savegames", listSaves);

        for (String save : listSaves) {
            File file = new File(save);
            if (file.exists()) {
                file.delete();
            }
        }

        saves.openZip("C://Game/savegames/zip0.zip", "C://Game/savegames/");
        saves.openZip("C://Game/savegames/zip1.zip", "C://Game/savegames/");
        saves.openZip("C://Game/savegames/zip2.zip", "C://Game/savegames/");
        System.out.println(saves.openProgress("C://Game/savegames/save0.dat"));
        System.out.println(saves.openProgress("C://Game/savegames/save1.dat"));
        System.out.println(saves.openProgress("C://Game/savegames/save2.dat"));
    }
}
