package TGBotMain;

import java.io.*;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class DataBase {
    private static final int TIMER_TASK_PERIOD_MILLIS = 15000;

    private final HashMap<Long, User> usersMap;
    private volatile static DataBase dataBase = null;

    private DataBase(String dataBaseBackup) throws IOException, ClassNotFoundException {
        String dataBaseBackupPath = System.getProperty("user.dir") + "\\" + dataBaseBackup;

        if (new File(dataBaseBackupPath).isFile()) {
            ObjectInputStream savedDataBaseIn = new ObjectInputStream(new FileInputStream(dataBaseBackup));
            usersMap = (HashMap<Long, User>) savedDataBaseIn.readObject();
        }
        else {
            usersMap = new HashMap<>();
        }

        Timer backupTimeTask = new Timer();
        backupTimeTask.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    File oldBackup = new File(dataBaseBackupPath);
                    oldBackup.delete();

                    ObjectOutputStream savedDataBaseOut = new ObjectOutputStream(new FileOutputStream(dataBaseBackup));
                    savedDataBaseOut.writeObject(usersMap);
                    savedDataBaseOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, TIMER_TASK_PERIOD_MILLIS, TIMER_TASK_PERIOD_MILLIS);
    }

    public static DataBase getDataBase() throws IOException, ClassNotFoundException {
        if (dataBase == null) {
            synchronized (DataBase.class) {
                if (dataBase == null) {
                    dataBase = new DataBase("DATABASE.txt");
                }
            }
        }

        return dataBase;
    }

    public Schedule getUserSchedule(Long chatId) {
        User user = usersMap.get(chatId);
        if (user == null) {
            return null;
        }

        return user.getSchedule();
    }

    public String getGroupNumber(Long chatId) {
        User user = usersMap.get(chatId);
        if (user == null) {
            return null;
        }

        return user.getGroupNumber();
    }

    public void addUser(Long chatId, String groupNumber, Schedule schedule) {
        usersMap.put(chatId, new User(groupNumber, schedule));
    }
}