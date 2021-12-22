import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static java.lang.System.exit;

public class ScheduleParser {
    static String icsLine;
    static BufferedReader icsReader;

    static final String TABLE_NSU_URL = "https://table.nsu.ru/ics/group/";
    static final String LESSON_BLOCK_START = "BEGIN:VEVENT";
    static final String LESSON_BLOCK_END = "END:VEVENT";
    static final String DIVIDER = ":";

    public static Multimap<String, Lesson> parseGroupSchedule(String groupNumber) {
        String groupScheduleURL = TABLE_NSU_URL + groupNumber;

        try {
            icsReader = new BufferedReader(new InputStreamReader(new URL(groupScheduleURL).openStream(), StandardCharsets.UTF_8));
            List<Lesson> lessons = new ArrayList<>();

            while ((icsLine = icsReader.readLine()) != null) {
                if (icsLine.equals(LESSON_BLOCK_START)) {
                    lessons.add(parseLessonBlock(icsReader));
                }
            }

            Multimap<String, Lesson> schedule = ArrayListMultimap.create();

            lessons.forEach(lesson -> {
                schedule.put(lesson.getDay(), lesson);
            });
            return schedule;

        }
        catch (IOException e) {
            return null;
        }
    }

    private static Lesson parseLessonBlock(BufferedReader icsReader) throws IOException {
        HashMap<String, String> icsFields = new HashMap<>();

        String icsLine;
        while (!Objects.equals(icsLine = icsReader.readLine(), LESSON_BLOCK_END)) {
            int dividerIndex = icsLine.indexOf(DIVIDER);

            String fieldName = icsLine.substring(0, dividerIndex);
            String fieldValue = icsLine.substring(dividerIndex + 1);
            icsFields.put(fieldName, fieldValue);
        }

        return new Lesson(icsFields);
    }
}
