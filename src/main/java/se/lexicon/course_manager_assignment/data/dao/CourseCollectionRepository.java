package se.lexicon.course_manager_assignment.data.dao;



import se.lexicon.course_manager_assignment.data.sequencers.CourseSequencer;
import se.lexicon.course_manager_assignment.model.Course;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;


public class CourseCollectionRepository implements CourseDao{

    private Collection<Course> courses;


    public CourseCollectionRepository(Collection<Course> courses) {
        this.courses = courses;
    }

    @Override
    public Course createCourse(String courseName, LocalDate startDate, int weekDuration) {
        Course tmp = new Course();
        tmp.setId(CourseSequencer.nextCourseId());
        tmp.setCourseName(courseName);
        tmp.setStartDate(startDate);
        tmp.setWeekDuration(weekDuration);
        courses.add(tmp);
        return tmp;
    }

    @Override
    public Course findById(int id) {
        for(Course course : courses){
            if(course.getId() == id){
                return course;
            }
        }
        return null;
    }

    @Override
    public Collection<Course> findByNameContains(String name) {
        Collection<Course> coursesToAdd = new ArrayList<Course>();
        for(Course theCourse : courses){
            if(theCourse.getCourseName().contains(name)){
                coursesToAdd.add(theCourse);
            }
        }
        return coursesToAdd;
    }

    @Override
    public Collection<Course> findByDateBefore(LocalDate end) {
        Collection<Course> coursesToAdd = new ArrayList<Course>();
        for(Course theCourse : courses){
            if(theCourse.getStartDate().isBefore(end)){
                coursesToAdd.add(theCourse);
            }
        }
        return coursesToAdd;
    }

    @Override
    public Collection<Course> findByDateAfter(LocalDate start) {
        Collection<Course> coursesToAdd = new ArrayList<Course>();
        for(Course theCourse : courses){
            if(theCourse.getStartDate().isAfter(start)){
                coursesToAdd.add(theCourse);
            }
        }
        return coursesToAdd;
    }

    @Override
    public Collection<Course> findAll() {
        return courses;
    }

    @Override
    public Collection<Course> findByStudentId(int studentId) {
        Collection<Course> coursesToAdd = new ArrayList<Course>();
        for(Course theCourse : courses){
            if(theCourse.getId() == studentId){
                coursesToAdd.add(theCourse);
            }
        }
        return coursesToAdd;
    }

    @Override
    public boolean removeCourse(Course course) {
        Iterator courseIterator = courses.iterator();
        while(courseIterator.hasNext()){
            if(courseIterator.next().equals(course)){
                courseIterator.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public void clear() {
        this.courses = new HashSet<>();
    }

    @Override
    public Course update(String courseName, LocalDate startDate, Integer weekDuration) {
        Collection<Course> tmp = findByNameContains(courseName);
        Course chosenCourse = null;
        if(tmp.size() > 1){
            chosenCourse = new Course();
            for(Course c : tmp){
                if(c.getStartDate().isBefore(chosenCourse.getStartDate())){
                    chosenCourse = c;
                }
            }
        }
        if(chosenCourse != null){
            chosenCourse.setCourseName(courseName);
            chosenCourse.setStartDate(startDate);
            chosenCourse.setWeekDuration(weekDuration);
        }
        return chosenCourse;
    }
}
