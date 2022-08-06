package se.lexicon.course_manager_assignment.data.service.converter;

import org.springframework.stereotype.Component;
import se.lexicon.course_manager_assignment.dto.views.CourseView;
import se.lexicon.course_manager_assignment.dto.views.StudentView;
import se.lexicon.course_manager_assignment.model.Course;
import se.lexicon.course_manager_assignment.model.Student;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class ModelToDto implements Converters {
    @Override
    public StudentView studentToStudentView(Student student) {
        StudentView stuView = new StudentView(student.getId(), student.getName(), student.getEmail(), student.getAddress());
        return stuView;
    }

    @Override
    public CourseView courseToCourseView(Course course) {
        CourseView csView = new CourseView(course.getId(), course.getCourseName(), course.getStartDate(), course.getWeekDuration(), studentsToStudentViews(course.getStudents()));
        return csView;
    }

    @Override
    public List<CourseView> coursesToCourseViews(Collection<Course> courses) {
        List<CourseView> csViews = new ArrayList<CourseView>();
        for(Course course : courses){
            csViews.add(courseToCourseView(course));
        }
        return csViews;
    }

    @Override
    public List<StudentView> studentsToStudentViews(Collection<Student> students) {
        List<StudentView> stuViews = new ArrayList<StudentView>();
        for (Student stud : students) {
            stuViews.add(new StudentView(stud.getId(), stud.getName(), stud.getEmail(), stud.getAddress()));
        }
        return stuViews;
    }
}
