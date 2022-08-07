package se.lexicon.course_manager_assignment.data.service.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.course_manager_assignment.data.dao.CourseDao;
import se.lexicon.course_manager_assignment.data.dao.StudentDao;
import se.lexicon.course_manager_assignment.data.service.converter.Converters;
import se.lexicon.course_manager_assignment.dto.forms.CreateCourseForm;
import se.lexicon.course_manager_assignment.dto.forms.UpdateCourseForm;
import se.lexicon.course_manager_assignment.dto.views.CourseView;
import se.lexicon.course_manager_assignment.model.Course;


import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
public class CourseManager implements CourseService {

    private final CourseDao courseDao;
    private final StudentDao studentDao;
    private final Converters converters;

    @Autowired
    public CourseManager(CourseDao courseDao, StudentDao studentDao, Converters converters) {
        this.courseDao = courseDao;
        this.studentDao = studentDao;
        this.converters = converters;
    }

    @Override
    public CourseView create(CreateCourseForm form) {
        Course co = this.courseDao.createCourse(form.getCourseName(), form.getStartDate(), form.getWeekDuration());
        return this.converters.courseToCourseView(co);
    }

    @Override
    public CourseView update(UpdateCourseForm form) {
        Course co = this.courseDao.update(form.getCourseName(), form.getStartDate(), form.getWeekDuration());
        return this.converters.courseToCourseView(co);
    }

    @Override
    public List<CourseView> searchByCourseName(String courseName) {
        Collection<Course> courses = this.courseDao.findByNameContains(courseName);
        return this.converters.coursesToCourseViews(courses);
    }

    @Override
    public List<CourseView> searchByDateBefore(LocalDate end) {
        Collection<Course> courses = this.courseDao.findByDateBefore(end);
        return this.converters.coursesToCourseViews(courses);
    }

    @Override
    public List<CourseView> searchByDateAfter(LocalDate start) {
        Collection<Course> courses = this.courseDao.findByDateAfter(start);
        return this.converters.coursesToCourseViews(courses);
    }

    @Override
    public boolean addStudentToCourse(int courseId, int studentId) {
        Course course = this.courseDao.findById(courseId);
        return course.enrollStudent(this.studentDao.findById(studentId));
    }

    @Override
    public boolean removeStudentFromCourse(int courseId, int studentId) {
        Course c = this.courseDao.findById(courseId);
        return c.unenrollStudent(this.studentDao.findById(studentId));
    }

    @Override
    public CourseView findById(int id) {
        Course c = this.courseDao.findById(id);
        return this.converters.courseToCourseView(c);
    }

    @Override
    public List<CourseView> findAll() {
        Collection<Course> courses = this.courseDao.findAll();
        return this.converters.coursesToCourseViews(courses);
    }

    @Override
    public List<CourseView> findByStudentId(int studentId) {
        Collection<Course> courses = this.courseDao.findByStudentId(studentId);
        return this.converters.coursesToCourseViews(courses);
    }

    @Override
    public boolean deleteCourse(int id) {
        return this.courseDao.removeCourse(this.courseDao.findById(id));
    }
}
