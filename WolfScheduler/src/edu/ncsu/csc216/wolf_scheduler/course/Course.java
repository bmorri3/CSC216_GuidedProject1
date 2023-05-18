/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * Class to create, edit, and return a Course's title, section, credit hours, instructor ID,
 * meeting days, start time, and end time
 * 
 * @author Ben Morris
 */
public class Course {

	/** Minimum name length */
	private static final int MIN_NAME_LENGTH = 5;
	/** Maximum name length */
	private static final int MAX_NAME_LENGTH = 8;
	/** Minimum number of letters in a course name */
	private static final int MIN_LETTER_COUNT = 1;
	/** Maximum number of letters in a course name */
	private static final int MAX_LETTER_COUNT = 4;
	/** Number of digits in a course name */
	private static final int DIGIT_COUNT = 3;
	/** Number of digits in a course's section */
	private static final int SECTION_LENGTH = 3;
	/** Maximum number of credits */
	private static final int MAX_CREDITS = 5;
	/** Minimum number of credits */
	private static final int MIN_CREDITS = 1;
	/** Upper bound on time in hours */
	private static final int UPPER_HOUR = 24;
	/** Upper bound on time in minutes */
	private static final int UPPER_MINUTE = 60;
	
	/** Course's name. */
	private String name;
	/** Course's title. */
	private String title;
	/** Course's section. */
	private String section;
	/** Course's credit hours */
	private int credits;
	/** Course's instructor */
	private String instructorId;
	/** Course's meeting days */
	private String meetingDays;
	/** Course's starting time */
	private int startTime;
	/** Course's ending time */
	private int endTime;

	/**
	 * Constructs a course object with values for all fields
	 * @param name name of the course
	 * @param title title of the course
	 * @param section course's section
	 * @param credits # of credits the course is worth
	 * @param instructorId instructor's ID
	 * @param meetingDays days the class meets
	 * @param startTime time the class starts
	 * @param endTime time the class ends
	 */
	public Course(String name, String title, String section, int credits, String instructorId, String meetingDays,
			int startTime, int endTime) {
		setName(name);
		setTitle(title);
		setSection(section);
		setCredits(credits);
		setInstructorId(instructorId);
		setMeetingDaysAndTime(meetingDays, startTime, endTime);
	}

	/**
	 * Creates a Course with the given name, title, section, credits, instructorId, and meetingDays for 
	 * courses that are arranged. Start and end times are initialized to zero.
	 * @param name name of the course
	 * @param title title of the course
	 * @param section course's section
	 * @param credits # of credits the course is worth
	 * @param instructorId instructor's ID
	 * @param meetingDays days the class meets
	 */
	public Course(String name, String title, String section, int credits, String instructorId, String meetingDays) {
		this(name, title, section, credits, instructorId, meetingDays, 0, 0);
	}

	/**
	 * Calls the method to begin the program
	 * @param args command line arguments (not used)
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Returns the course's name
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the course's name
	 * @param name the name to set
	 * @throws IllegalArgumentException "Invalid course name" if the name is null or blank
	 * @throws IllegalArgumentException "Invalid course name" if the name length is out of range
	 * @throws IllegalArgumentException "Invalid course name" if the name no space is found
	 * @throws IllegalArgumentException "Invalid course name" if the name is the character after the space is not a digit
	 * @throws IllegalArgumentException "Invalid course name" if the name the number of characters in the course name is out of range
	 * @throws IllegalArgumentException "Invalid course name" if the name the number of digits in the course name is out of range
	 */
	private void setName(String name) {
		//Throw exception if name is null
		if (name == null || "".equals(name))
			throw new IllegalArgumentException("Invalid course name.");

		//Throw exception if class name has too many or too few characters 
		if(name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH)
			throw new IllegalArgumentException("Invalid course name.");

		//Check for pattern of L[LLL] NNN
		int numLetters = 0;
		int numDigits = 0;
		boolean spaceFound = false;
		for(int i = 0; i < name.length(); i++) {
			if(!spaceFound) {
				if(Character.isLetter(name.charAt(i)))
					numLetters++;
				else if(name.charAt(i) == ' ')
					spaceFound = true;
				else
					throw new IllegalArgumentException("Invalid course name.");
			}
			else if(Character.isDigit(name.charAt(i)))
					numDigits++;
			else
				throw new IllegalArgumentException("Invalid course name.");
		}
		
		//Check that the number of letters is correct
		if(numLetters < MIN_LETTER_COUNT || numLetters > MAX_LETTER_COUNT)
			throw new IllegalArgumentException("Invalid course name.");
		
		//Check that the number of digits is correct
		if(numDigits != DIGIT_COUNT)
			throw new IllegalArgumentException("Invalid course name.");
		
		this.name = name;
	}

	/**
	 * Returns the course's title
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the course's title
	 * @param title the title to set
	 * @throws IllegalArgumentException "Invalid title." if the title passed is null or empty.
	 */
	public void setTitle(String title) {
		if(title == null || "".equals(title))
			throw new IllegalArgumentException("Invalid title.");
		this.title = title;
	}

	/**
	 * Returns the section number
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * Sets the course's section number
	 * @param section the section to set
	 * @throws IllegalArgumentException "Invalid section." if the passed section is null or != SECTION_LENGTH
	 * @throws IllegalArgumentException "Invalid section." if the section isn't made of all digits.
	 */
	public void setSection(String section) {
		
		//Section must be exactly 3 digits
		if(section == null || section.length() != SECTION_LENGTH)
			throw new IllegalArgumentException("Invalid section.");
		else
			for(int i = 0; i < section.length(); i++)
				if(!Character.isDigit(section.charAt(i)))
					throw new IllegalArgumentException("Invalid section.");
		
		this.section = section;
	}

	/**
	 * Returns the number of credits
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * Sets the number of credits
	 * @param credits the credits to set
	 * @throws IllegalArgumentException "Invalid credits." if the number of credits is out of range
	 */
	public void setCredits(int credits) {
		
		if(credits < MIN_CREDITS || credits > MAX_CREDITS)
			throw new IllegalArgumentException("Invalid credits.");
		
		this.credits = credits;
	}

	/**
	 * Returns the instructor ID for a course and section
	 * @return the instructorId
	 */
	public String getInstructorId() {
		return instructorId;
	}

	/**
	 * Sets the instructor id for a course and section
	 * @param instructorId the instructorId to set
	 * @throws IllegalArgumentException "Invalid instructor id." if the Id is null or blank.
	 */
	public void setInstructorId(String instructorId) {
		if(instructorId == null || "".equals(instructorId))
			throw new IllegalArgumentException("Invalid instructor id.");
		
		this.instructorId = instructorId;
	}

	/**
	 * Returns the days the class meets
	 * @return the meetingDays
	 */
	public String getMeetingDays() {
		return meetingDays;
	}


	/**
	 * Returns the start time of the class
	 * @return the startTime
	 */
	public int getStartTime() {
		return startTime;
	}


	/**
	 * Returns the end time of the class
	 * @return the endTime
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * Set the meeting days and start and end times
	 * @param meetingDays days the class will meet
	 * @param startTime time the class will start
	 * @param endTime time the class will end
	 * @throws IllegalArgumentException "Invalid meeting days and times." if meetingDays is null or blank
	 * @throws IllegalArgumentException "Invalid meeting days and times." if meetingDays is "A" and startTime or EndTime != 0
	 * @throws IllegalArgumentException "Invalid meeting days and times." if meetingDays isn't an accepted character
	 * @throws IllegalArgumentException "Invalid meeting days and times." if a day is repeated
	 * @throws IllegalArgumentException "Invalid meeting days and times." if endTime is before startTime
	 * @throws IllegalArgumentException "Invalid meeting days and times." if times are out of range
	 */
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		
		//Check for null or empty meetingDays
		if(meetingDays == null || "".equals(meetingDays))
			throw new IllegalArgumentException("Invalid meeting days and times.");
		
		//If meetingDays is "A", set startTime and endTime to 0
		if("A".equals(meetingDays))
			if(startTime != 0 || endTime != 0)
				throw new IllegalArgumentException("Invalid meeting days and times.");
			else {
				this.meetingDays = meetingDays;
				this.startTime = 0;
				this.endTime = 0;
				return;
			}
		
		//Check for invalid or repeating days
		char[] dayArray = new char[meetingDays.length()];
		for(int i = 0; i < meetingDays.length(); i++) {
			//Check for invalid days
			if(!(meetingDays.charAt(i) == 'M' || meetingDays.charAt(i) == 'T' || meetingDays.charAt(i) == 'W' ||
					meetingDays.charAt(i) == 'H' || meetingDays.charAt(i) == 'F'))
				throw new IllegalArgumentException("Invalid meeting days and times.");
			
			//Check for repeating days
			dayArray[i] = meetingDays.charAt(i);
			if(i > 0) //do this only for more than the first day
				for(int j = 0; j < i; j++)
					if(dayArray[j] == meetingDays.charAt(i))
						throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		
		if (endTime < startTime)
			throw new IllegalArgumentException("Invalid meeting days and times.");

		
		//Check times to make sure they are between 000 and 2359
        int startHour = startTime / 100;
        int startMin = startTime % 100;
        int endHour = endTime / 100;
        int endMin = endTime % 100;
        
        if (startHour < 0 || startHour >= UPPER_HOUR)
        	throw new IllegalArgumentException("Invalid meeting days and times.");
        
        if (startMin < 0 || startMin >= UPPER_MINUTE)
        	throw new IllegalArgumentException("Invalid meeting days and times.");
        
        if (endHour < 0 || endHour >= UPPER_HOUR)
        	throw new IllegalArgumentException("Invalid meeting days and times.");
        
        if (endMin < 0 || endMin >= UPPER_MINUTE)
        	throw new IllegalArgumentException("Invalid meeting days and times.");
        
        this.meetingDays = meetingDays;
        this.startTime = startTime;
        this.endTime = endTime;
	}

	/**
	 * Generates a hashCode for Course using all fields
	 * @return hashCode for Course
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + credits;
		result = prime * result + endTime;
		result = prime * result + ((instructorId == null) ? 0 : instructorId.hashCode());
		result = prime * result + ((meetingDays == null) ? 0 : meetingDays.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((section == null) ? 0 : section.hashCode());
		result = prime * result + startTime;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/**
	 * Compares a given object to this object for equality on all fields.
	 * @param obj the Object to compare
	 * @return true if the objects are the same on all fields
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (credits != other.credits)
			return false;
		if (endTime != other.endTime)
			return false;
		if (instructorId == null) {
			if (other.instructorId != null)
				return false;
		} else if (!instructorId.equals(other.instructorId))
			return false;
		if (meetingDays == null) {
			if (other.meetingDays != null)
				return false;
		} else if (!meetingDays.equals(other.meetingDays))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (section == null) {
			if (other.section != null)
				return false;
		} else if (!section.equals(other.section))
			return false;
		if (startTime != other.startTime)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	/**
	 * Helper method to convert military time as an int to standard time as a String
	 * @param time military time
	 * @return Time in standard form as a String
	 */
	private String getTimeString(int time) {
		int hours = time / 100;
		int minutes = time % 100;
		
		if(hours > 12) {
			hours = hours - 12;
			if (minutes < 10)
				return "" + hours + ":" + "0" + minutes + "PM";
			else
				return	"" + hours + ":" + minutes + "PM";
		}
		else
			if (minutes < 10)
				return "" + hours + ":" + "0" + minutes + "AM";
			else
				return	"" + hours + ":" + minutes + "AM";
	}
	
	/**
	 * Return the schedule line as either "Arranged" or
	 * DDDDD HH:MM[AM or PM]-HH:MM[AM or PM]
	 * @return schedule line as a string
	 */
	public String getMeetingString() {	
		String daysString = null;
		if("A".equals(getMeetingDays()))
			return "Arranged"; //Arranged has no start or end times
		else
			daysString = getMeetingDays();
		
		return daysString + " " + getTimeString(getStartTime()) + "-" + getTimeString(getEndTime());
	}

	/**
	 * Returns a comma separated value String of all Course fields
	 * @return String representation of Course
	 */
	@Override
	public String toString() {
		if ("A".equals(meetingDays)) {
			return name + "," + title + "," + section + "," + credits + "," + instructorId + "," + meetingDays;
		}
		return name + "," + title + "," + section + "," + credits + "," + instructorId + "," + meetingDays + "," + startTime + "," + endTime;
	}

}
