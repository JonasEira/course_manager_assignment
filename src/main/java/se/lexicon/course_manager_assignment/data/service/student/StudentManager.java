package se.lexicon.course_manager_assignment.data.service.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.course_manager_assignment.data.dao.CourseDao;
import se.lexicon.course_manager_assignment.data.dao.StudentDao;
import se.lexicon.course_manager_assignment.data.sequencers.StudentSequencer;
import se.lexicon.course_manager_assignment.data.service.converter.Converters;
import se.lexicon.course_manager_assignment.dto.forms.CreateStudentForm;
import se.lexicon.course_manager_assignment.dto.forms.UpdateStudentForm;
import se.lexicon.course_manager_assignment.dto.views.StudentView;
import se.lexicon.course_manager_assignment.model.Student;


import java.util.Collection;
import java.util.List;

@Service
public class StudentManager implements StudentService {

    private final StudentDao studentDao;
    private final CourseDao courseDao;
    private final Converters converters;

    @Autowired
    public StudentManager(StudentDao studentDao, CourseDao courseDao, Converters converters) {
        this.studentDao = studentDao;
        this.courseDao = courseDao;
        this.converters = converters;
    }

    @Override
    public StudentView create(CreateStudentForm form) {
        Student tmp = this.studentDao.createStudent(form.getName(), form.getEmail(), form.getAddress());
        return converters.studentToStudentView(tmp);
    }

    @Override
    public StudentView update(UpdateStudentForm form) {
        Student tmp = this.studentDao.update(form.getName(), form.getEmail(), form.getAddress());
        return converters.studentToStudentView(tmp);

    }

    @Override
    public StudentView findById(int id) {
        Student tmp = this.studentDao.findById(id);
        return converters.studentToStudentView(tmp);
    }

    @Override
    public StudentView searchByEmail(String email) {
        if(email.equals("")){
            throw new IllegalArgumentException("Empty string");
        }
        Student tmp = this.studentDao.findByEmailIgnoreCase(email);
        return converters.studentToStudentView(tmp);
    }

    @Override
    public List<StudentView> searchByName(String name) {
        if(name.equals("")){
            throw new IllegalArgumentException("Empty string");
        }
        Collection<Student> tmp = this.studentDao.findByNameContains(name);
        return converters.studentsToStudentViews(tmp);
    }

    @Override
    public List<StudentView> findAll() {
        Collection<Student> tmp = this.studentDao.findAll();
        return converters.studentsToStudentViews(tmp);
    }

    @Override
    public boolean deleteStudent(int id) {
        Student tmp = this.studentDao.findById(id);
        if(tmp != null){
            this.studentDao.removeStudent(tmp);
            return true;
        }
        return false;
    }
}
