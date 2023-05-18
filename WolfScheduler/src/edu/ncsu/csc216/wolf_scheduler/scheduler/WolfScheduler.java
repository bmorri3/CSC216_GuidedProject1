package edu.ncsu.csc216.wolf_scheduler.scheduler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import edu.ncsu.csc216.wolf_scheduler.course.Course;
import edu.ncsu.csc216.wolf_scheduler.io.CourseRecordIO;

/**
 * Allows a user to build and edit a schedule of Courses
 * 
 * @author Ben Morris
 */
public class WolfScheduler {
	/** Width of the limited Catalog and Schedule arrays */
	private static final int CATALOG_ARRAY_WIDTH = 3;
	/** Width of the full array containing all of the fields */
	private static final int FULL_ARRAY_WIDTH = 6;
	
	/** Catalog of Courses */
	ArrayList<Course> catalog;
	/** Schedule of courses */
	ArrayList<Course> schedule;
	/** Schedule title */
	String title;
	
	/**
	 * Constructor
	 * @param file filename for the course records that should be read in and stored
	 * @throws IllegalArgumentException "Cannot find file." if the file to read from is not found
	 */
	public WolfScheduler(String file) {
		catalog = new ArrayList<Course>(); //Create an empty ArrayList of Course objects for the catalog
		schedule = new ArrayList<Course>(); //Create an empty ArrayList of Course objects for the schedule
		title = "My Schedule";
		
		//Read Courses into catalog. Throw IAE if the file isn't found.
		try {
			catalog = CourseRecordIO.readCourseRecords(file);	
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Cannot find file.");
		}
	}
	
	/**
	 * Returns a 2D String array of the catalog. There is a row for each 
	 * Course and three columns for name, section, and title. If there are no Courses in 
	 * the catalog, an empty 2D String array is returned.
	 * @return 2D String array of the catalog
	 */
	public String[][] getCourseCatalog() {
		String[][] catalogArray = new String[catalog.size()][CATALOG_ARRAY_WIDTH];
		
		if (catalog.size() != 0)	
			for(int i = 0; i < catalog.size(); i++) {
				//Get Course[i]
				Course current = catalog.get(i);
				//Add name, section, and title to catalogArray[i]
				catalogArray[i][0] = current.getName();
				catalogArray[i][1] = current.getSection();
				catalogArray[i][2] = current.getTitle();
			}
		return catalogArray;
	}

	/**
	 * Returns a 2D String array of the schedule. There is a row for each 
	 * Course and three columns for name, section, and title. If there are no Courses in 
	 * the catalog, an empty 2D String array is returned.
	 * @return 2D String array of the catalog
	 */
	public String[][] getScheduledCourses() {
		String[][] scheduleArray = new String[schedule.size()][CATALOG_ARRAY_WIDTH];
		
		if (catalog.size() != 0)	
			for(int i = 0; i < schedule.size(); i++) {
				//Get Course[i]
				Course current = schedule.get(i);
				//Add name, section, and title to scheduleArray[i]
				scheduleArray[i][0] = current.getName();
				scheduleArray[i][1] = current.getSection();
				scheduleArray[i][2] = current.getTitle();
			}
		return scheduleArray;
	}

	/** 
	 * Returns a 2D String array of the schedule with all information. 
	 * This array is used in the GUI to create the table of course catalog information. There is 
	 * a row for each Course and six columns for name, section, title, credits, instructorId, and 
	 * the meeting days string (e.g., getMeetingString(). If there are no Courses in the schedule, 
	 * an empty 2D String array is returned.
	 * @return 2D String array of the schedule with all information.
	 */
	public String[][] getFullScheduledCourses() {
		String[][] scheduleArray = new String[schedule.size()][FULL_ARRAY_WIDTH];
		
		if (catalog.size() != 0)	
			for(int i = 0; i < schedule.size(); i++) {
				//Get Course[i]
				Course current = schedule.get(i);
				//Add name, section, title, credits (as a String), instructorId, and meeting days String to scheduleArray[i]
				scheduleArray[i][0] = current.getName();
				scheduleArray[i][1] = current.getSection();
				scheduleArray[i][2] = current.getTitle();
				scheduleArray[i][3] = "" + current.getCredits();
				scheduleArray[i][4] = current.getInstructorId();
				scheduleArray[i][5] = current.getMeetingString();
			}
		
		return scheduleArray;
	}

	/**
	 * Searches for a Course with the given name and section. If it isn't found, return null.
	 * @param name Course's name
	 * @param section Course's section
	 * @return Course with the given name and section
	 */
	public Course getCourseFromCatalog(String name, String section) {
		
		//If the catalog isn't empty, search for a Course that matches name and section
		if (catalog.size() != 0)
			for(int i = 0; i < catalog.size(); i++) {
				//Get Course Catalog[i]
				Course current = catalog.get(i);
				//Check for matching name and section
				if (current.getName().equals(name) && current.getSection().equals(section))
					return current;
			}
		//Return null if no matching Course was found
		return null;
	}
	
	/**
	 * Add a Course to a student's schedule
	 * Returns true if the given Course (represented by the name and section)
	 * meets the following criteria: 1) the course exists in the catalog and 2) 
	 * the course is successfully added to the student’s schedule.
	 * @param name name of the Course to add to the schedule
	 * @param section section of the Course to add to the schedule
	 * @return true if the Course exists and was added to the student's schedule
	 * @throws IllegalArgumentException "You are already enrolled in [course]" if the student is already enrolled
	 * 		   in a course with the same name.
	 */
	public boolean addCourseToSchedule(String name, String section) {
		Course addedCourse = getCourseFromCatalog(name, section);
		
		//If the course isn't null
		if (addedCourse != null) {		
			//Search all Courses in schedule
			for(int i = 0; i < schedule.size(); i++) {
				Course current = schedule.get(i);
				//If the Course is already scheduled, throw IAE
				if (current.getName().equals(name)) {
					throw new IllegalArgumentException("You are already enrolled in " + name);
				}
			}
			//Otherwise, add the course
			return schedule.add(addedCourse);
		} 
		// The course is null and can't be added.
		else
			return false;
	}
	
	/**
	 * Removes a course from a student's schedule.
	 * Returns true if the given Course (represented by the name and section) can be removed from the student’s schedule. 
	 * The method returns false if the Course isn’t in the schedule.
	 * @param name name of the course to remove
	 * @param section section number of the course to remove
	 * @return true if the Course is removed from the schedule, false otherwise
	 */
	public boolean removeCourseFromSchedule(String name, String section) {
		//Get the student's schedule
		String[][] scheduleArray = getScheduledCourses();
		//Get the course to remove from the catalog
		Course courseToRemove = getCourseFromCatalog(name, section);
		
		//If the schedule isn't empty
		if (scheduleArray.length != 0) {		
			for(int i = 0; i < scheduleArray.length; i++)
				if (scheduleArray[i][0].equals(name) && scheduleArray[i][1].equals(section))
					return schedule.remove(courseToRemove);
		}
			
		return false;
	}

	/**
	 * creates a empty ArrayList for the schedule
	 */
	public void resetSchedule() {
		schedule.clear();
	}
	
	/**
	 * Sets the schedule's title
	 * @param title new schedule title
	 * @throws IllegalArgumentException "Title cannot be null" if the title is null.
	 */
	public void setScheduleTitle(String title) {
		if(title == null)
			throw new IllegalArgumentException("Title cannot be null");
		else
			this.title = title;
	}
	
	/**
	 * Returns the schedule title. 
	 * Throws an IllegalArgumentException if the title is null with an error message of “Title cannot be null.”
	 * @return Title of the class
	 */
	public String getScheduleTitle() {
		return title;
	}

	/**
	 * Exports the schedule to file with the passed name.
	 * 
	 * If CourseRecordIO.writeCourseRecords() throws an IOException, throw new IAE, “The file cannot be saved.”
	 * @param fileName file to export to
	 * @throws IllegalArgumentException "The file cannot be saved." if there is an IOException when trying to save the file.
	 */
	public void exportSchedule(String fileName) {
		try {
			CourseRecordIO.writeCourseRecords(fileName, schedule);
		}
		catch (IOException e){
			throw new IllegalArgumentException("The file cannot be saved.");
		}
	}
}
