import com.google.common.collect.Multimap;

public class Schedule {
    private static final String DASH = "========================================================";

    public static String getFullSchedule(Multimap<String, Lesson> schedule, String day) {
        StringBuffer stringBuffer = new StringBuffer("");

        if (schedule != null) {
            for (Lesson lesson : schedule.get(day)) {
                stringBuffer.append(lesson.getTeacher()).append("\n");
                stringBuffer.append(lesson.getSubject()).append("\n");
                stringBuffer.append(lesson.getClassroom()).append("\n");
                stringBuffer.append(lesson.getStartTime().toString()).append("\n");
                stringBuffer.append(lesson.getEndTime().toString()).append("\n");

                stringBuffer.append(DASH).append("\n");
            }
        } else {
            stringBuffer.append("Sorry we got some problems").append("\n");
        }
        return stringBuffer.toString();
    }
}
