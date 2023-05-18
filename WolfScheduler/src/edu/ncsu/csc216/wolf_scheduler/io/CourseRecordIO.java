/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.wolf_scheduler.course.Course;

/**
 * Reads Course records from text files.  Writes a set of CourseRecords to a file.
 * 
 * @author Sarah Heckman
 * @author Ben Morris
 */
public class CourseRecordIO {

	/**
    * Reads course records from a file and generates a list of valid Courses.  Any invalid
    * Courses are ignored.  If the file to read cannot be found or the permissions are incorrect
    * a File NotFoundException is thrown.
    * @param fileName file to read Course records from
    * @return a list of valid Courses
    * @throws FileNotFoundException if the file cannot be found or read
    */
	public static ArrayList<Course> readCourseRecords(String fileName) throws FileNotFoundException {
	    Scanner fileReader = new Scanner(new FileInputStream(fileName));  //Create a file scanner to read the file
	    ArrayList<Course> courses = new ArrayList<Course>(); //Create an empty array of Course objects
	    while (fileReader.hasNextLine()) { //While we have more lines in the file
	        try { //Attempt to do the following
	            //Read the line, process it in readCourse, and get the object
	            //If trying to construct a Course in readCourse() results in an exception, flow of control will transfer to the catch block, below
	            Course course = readCourse(fileReader.nextLine()); 

	            //Create a flag to see if the newly created Course is a duplicate of something already in the list  
	            boolean duplicate = false;
	            //Look at all the courses in our list
	            for (int i = 0; i < courses.size(); i++) {
	                //Get the course at index i
	                Course current = courses.get(i);
	                //Check if the name and section are the same
	                if (course.getName().equals(current.getName()) &&
	                        course.getSection().equals(current.getSection())) {
	                    //It's a duplicate!
	                    duplicate = true;
	                    break; //We can break out of the loop, no need to continue searching
	                }
	            }
	            //If the course is NOT a duplicate
	            if (!duplicate) {
	                courses.add(course); //Add to the ArrayList!
	            } //Otherwise ignore
	        } catch (IllegalArgumentException e) {
	            //The line is invalid b/c we couldn't create a course, skip it!
	        }
	    }
	    //Close the Scanner b/c we're responsible with our file handles
	    fileReader.close();
	    //Return the ArrayList with all the courses we read!
	    return courses;
	}

	/**
	 * Helper method to parse a line into Course fields
	 * @param nextLine line of text file to parse
	 * @return the information as a Course
	 * @throws IllegalArgumentException "Invalid course entry" if there are more fields than expected
	 * @throws IllegalArgumentException "Invalid course entry" if an exception is caught from the IO
	 */
	private static Course readCourse(String nextLine) {
		Scanner lineScanner = new Scanner(nextLine);
		lineScanner.useDelimiter(",");
		
		try {
			// Declare and initialize local variables
			String name = lineScanner.next();
			String title = lineScanner.next();
			String section = lineScanner.next();
			int credits = lineScanner.nextInt();
			String instructorId = lineScanner.next();
			String meetingDays = lineScanner.next();	
			
			//If meetingDays is arranged and there are no more tokens, return the Course with the appropriate fields
			if ("A".equals(meetingDays)) {
				//If there are more fields, throw IAE
				if(lineScanner.hasNext()) {
					lineScanner.close();
					throw new IllegalArgumentException("Invalid course entry");
				}
				//Otherwise create and return the Course
				else {
					lineScanner.close();
					Course course;
					course = new Course(name, title, section, credits, instructorId, meetingDays);
					return course;
				}
			}
			//Otherwise, get the start and end times and return the appropriate Course
			else {
				int startTime = lineScanner.nextInt();
				int endTime = lineScanner.nextInt();
				//If there are more fields after reading the times, throw IAE
				if(lineScanner.hasNext()) {
					lineScanner.close();
					throw new IllegalArgumentException("Invalid course entry");
				}
				//Otherwise, create and return the Course
				else {
					lineScanner.close();
					Course course;
					course = new Course(name, title, section, credits, instructorId, meetingDays, startTime, endTime);
					return course;
				}
			}
		} 
		catch (IllegalArgumentException e){
			lineScanner.close();
			throw new IllegalArgumentException("Invalid course entry");				
		}
		catch (InputMismatchException e) {
			lineScanner.close();
			throw new IllegalArgumentException("Invalid course entry");
		}
		catch (NoSuchElementException e) {
			lineScanner.close();
			throw new IllegalArgumentException("Invalid course entry");
		}

	}

	/**
     * Writes the given list of Courses to 
     * @param fileName file to write schedule of Courses to
     * @param courses list of Courses to write
     * @throws IOException if cannot write to file
     */
	public static void writeCourseRecords(String fileName, ArrayList<Course> courses) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));

		for (int i = 0; i < courses.size(); i++) {
		    fileWriter.println(courses.get(i).toString());
		}

		fileWriter.close();
	}

}
