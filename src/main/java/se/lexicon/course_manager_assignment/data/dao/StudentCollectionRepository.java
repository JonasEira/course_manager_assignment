package se.lexicon.course_manager_assignment.data.dao;



import se.lexicon.course_manager_assignment.model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;


public class StudentCollectionRepository implements StudentDao {

    private Collection<Student> students;

    public StudentCollectionRepository(Collection<Student> students) {
        this.students = students;
    }

    @Override
    public Student createStudent(String name, String email, String address) {
        Student tmp = new Student();
        tmp.setAddress(address);
        tmp.setEmail(email);
        tmp.setName(name);
        students.add(tmp);
        return tmp;
    }

    @Override
    public Student findByEmailIgnoreCase(String email) {
        for(Student s : students){
            if(s.getEmail().equalsIgnoreCase(email)){
                return s;
            }
        }
        return null;
    }

    @Override
    public Collection<Student> findByNameContains(String name) {
        Collection<Student> tmpStudents = new ArrayList<Student>();
        for(Student s : students){
            if(s.getName().equalsIgnoreCase(name)){
                tmpStudents.add(s);
            }
        }
        return tmpStudents;
    }

    @Override
    public Student findById(int id) {
        for(Student s : students){
            if(s.getId() == id){
                return s;
            }
        }
        return null;
    }

    @Override
    public Collection<Student> findAll() {
        return students;
    }

    @Override
    public boolean removeStudent(Student student) {
        Iterator studIterator = this.students.iterator();
        while(studIterator.hasNext()){
            if(studIterator.next().equals(student)){
                studIterator.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public void clear() {
        this.students = new HashSet<>();
    }

    @Override
    public void createStudent(Student tmp) {
        students.add(tmp);
    }

    @Override
    public Student update(String name, String email, String address) {
        Student student = findByEmailIgnoreCase(email);

        if (student != null){
            student.setName(name);
            student.setEmail(email);
            student.setAddress(address);
        }

        return student;
    }
}
