package dc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
    - className (nazwa klasy) : String
    - pupilsList (lista uczniów) : List<Pupil>
    - teachersList (lista nauczycieli) : List<Teacher>
    - subjectList (lista przedmiotów) : List<String>
 */
public class Klasa {
    private String className;
    private List<Pupil> pupilList;
    private List<Teacher> teachersList;
    private List<Subject> subjectList;

    private static Klasa instance;

    private Klasa(String className) {
        this.className = className;
    }

    public static Klasa getInstance(String className) {
        if (instance == null)
            instance = new Klasa(className);
        return instance;
    }

    public void addPupil(Pupil p) {
        pupilList.add(p);
    }
    public void addSubject(String subjectName) {
        subjectList.add(new Subject(subjectName));
    }
    public void addTeacher(Teacher t) {
        teachersList.add(t);
    }
    public void addTeacherToSubjects() throws ClassException {
        for(Subject subject : subjectList) {
            List<Teacher> kandydaci = new ArrayList<Teacher>();
            for(Teacher t : teachersList) {
                boolean pasuje = false;
                for(String sub : t.getSubjects())
                    if (sub.equals(subject)) {
                        pasuje = true;
                        break;
                    }
                if (pasuje == true)
                    kandydaci.add(t);
            }
            if (kandydaci.size() == 0)
                throw new ClassException("nie ma nauczyciela dla " + subject);
            Random los = new Random();
            int numer = los.nextInt(kandydaci.size());
            Teacher teacher = kandydaci.get(numer);
            subject.setTeacher(teacher);
        }
    }
    public double calculateAverageNote(Pupil pupil) {
        return pupil.countAverageNote();
    }
    public double calculateAverageNote(String subjectName) throws ClassException {
        double suma = 0;
        double count = 0;
        for(Pupil pupil : pupilList) {
            for(Note note : pupil.noteList)
                if (note.getSubjectName().equals(subjectName)) {
                    suma += note.getNote();
                    count++;
                }
        }
        if (count == 0)
            throw new ClassException("nie ma ocen z przedmiotu " + subjectName);
        double average = suma / count;
        return average;
    }
    public double calculateAverageNote() throws ClassException {
        double suma = 0;
        double count = 0;
        for(Pupil pupil : pupilList) {
            for(Note note : pupil.noteList) {
                    suma += note.getNote();
                    count++;
                }
        }
        if (count == 0)
            throw new ClassException("nie ma żadnych ocen!");
        double average = suma / count;
        return average;
    }
    public void showAbsentNotes(Pupil pupil) {
        List<String> przedmioty = new ArrayList<String>();
        for(Subject sub : subjectList)
            przedmioty.add(sub.getName());
        for(Note note : pupil.noteList)
            if (przedmioty.contains(note.getSubjectName()))
                przedmioty.remove(note.getSubjectName());
        // wyświetlenie przedmiotów, które pozostały na liście 'przedmioty'
        System.out.println("=== SUBJECTS WITHOUT NOTES ===");
        for(String s : przedmioty)
            System.out.println(s);
    }
}
