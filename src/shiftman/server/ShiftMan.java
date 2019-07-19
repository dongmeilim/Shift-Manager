package shiftman.server;

import java.util.List;

/**
 * Manage the assignment of staff for a shop to shifts for a working week.
 * The shop is open for a single continuous period each day, the "working hours". Each
 * day is divided into a number of shifts.
 * A shift is a continuous period of time that has staff assigned to it for the
 * entire period.
 * Each shift has a manager,
 * and zero or more workers. There is a minimum number of workers required for
 * each shift (possibly zero). Shifts with fewer than this number are considered understaffed. 
 * Shifts with more than this number are considered overstaffed. Only registered staff
 * can be assigned to a shift.
 * 
 * <p><b>Notes</b>
 * <ol>
 * <li> A working week is the days Monday, Tuesday, Wednesday, Thursday, Friday, Saturday,
 * and Sunday (in that order, with exactly those values). 
 * <li> All methods return a string or a list of strings. Some of the strings will 
 * indicate the status of the request made to the interface.
 * <li> If a string is
 * returned and there are no problems, then the string will be empty.
 * Otherwise, the string will begin with "ERROR" followed by a description of what
 * the problem is.
 * <li>
 * If a list of strings is returned, and there is an error, then the list 
 * should contain exactly one string. That string will begin with "ERROR" 
 * followed by a description of the problem. For example, if a day of week
 * needs to be provided, but what is provided is "monDay", then the message
 * might be: "ERROR: Day given (monDay) is invalid".
 * <li>
 * Various methods require shifts to be listed. Except where noted,
 * the format of the shift description is <b><tt>dayofweek</tt></b>"["<b><tt>starttime</tt></b>"-"<b><tt>endtime</tt></b>"]"
 * The day of the week will be included even when there are multiple shifts on the
 * same day in the list. 
 * For example, if the worker is assigned the shifts 09:00-12:00 and 14:00-17:00 on Monday 
 * and 09:00-12:00 on Friday, then the list will contain the 3 elements
 * <b><tt>Monday[09:00-12:00]</tt></b>, <b><tt>Monday[14:00-17:00]</tt></b>, <b><tt>Friday[09:00-12:00]</tt></b>
 * <li>
 * All times are use the format hh:mm (zero-padded, no seconds).
 * </ol>
 * @author Ewan Tempero, Semester 1 2019, Assignment 2
 */
public interface ShiftMan {

	/**
	 * <b>(0 Marks)</b> Request that a new roster be started for the shop with the supplied name.
	 * @param shopName The name of the shop the roster is for.
	 * @return The status of the request as described in the notes.
	 * Possible problems include: The supplied name is null or empty.
	 * 
	 * <p><em>There are no marks specific to this method. If you do 
	 * not implement this method, you will be limited as to what else you can do.</em>
	 */
	public String newRoster(String shopName);
	
	/**
	 * <b>(1 Mark)</b> Request that the hours for the specified day be as specified by the times supplied, 1 Mark. 
	 * @param dayOfWeek The day the request applies to.
	 * @param startTime The start of the working day.
	 * @param endTime The end of the working day.
	 * @return The status of the request as described in the notes.
	 * Possible problems include: the value given for the day of week is invalid (must be
	 * exactly the same as one of the 7 strings), the start time is after the end time, one or both times are invalid.
	 */
	public String setWorkingHours(String dayOfWeek, String startTime, String endTime);
	
	/**
	 * <b>(1 Mark)</b> Request that the shift specified by the supplied times be added as a shift that 
	 * staff can be assigned to for the specified day. 
	 * @param dayOfWeek The day the request applies to.
	 * @param startTime The start of the shift.
	 * @param endTime The end of the shift.
	 * @param minimumWorkers The number of workers needed for the shift.
	 * @return The status of the request as described in the notes.
	 * Possible problems include: the value given for the day of week is invalid (not one of the 7 strings 
	 * given above), the start time is after the end time, one or both times are invalid, the shift is
	 * not within the working hours, the shift overlaps with an already existing shift.
	 */
	public String addShift(String dayOfWeek, String startTime, String endTime, String minimumWorkers);
	
	/**
	 * <b>(1 Mark)</b> Request that a staff member with the supplied name be registered. 
	 * To simplify the assignment, the (very) unrealistic assumption will 
	 * be made that both the given and family names are not empty, and 
	 * that every name is unique (case insensitive).
	 * @param givenname The given name of the staff member
	 * @param familyName The family name of the staff member
	 * @return The status of the request as described in the notes.
	 * <p>Possible problems include: one or both of the names are empty, or 
	 * that there is someone with the supplied name (ignoring case) already registered. 
	 */
	public String registerStaff(String givenname, String familyName);
	
	/**
	 * <b>(2 Marks)</b> Request that the staff member specified by the supplied names be assigned to the shift specified by the supplied start and end times 
	 * @param dayOfWeek The day the request applies to.
	 * @param startTime The start of the shift.
	 * @param endTime The end of the shift.
	 * @param givenName The given name of the staff member
	 * @param familyName The family name of the staff member
	 * @param isManager true if the staff member is to be the manager for the shift (and not a worker).
	 * @return The status of the request as described in the notes.
	 * <p>Possible problems include: the value given for the day of week is invalid (not one of the 7 strings 
	 * given above), the start time is after the end time, one or both times are invalid, the specified
	 * shift has not been set, the specified
	 * staff member is not registered, there is already a staff member assigned as the manager.
	 */
	public String assignStaff(String dayOfWeek, String startTime, String endTime, String givenName, String familyName, boolean isManager);
	
	/**
	 * <b>(1 Mark)</b> Request a list of the names of all registered staff. 
	 * 
	 * @return A list of the names of all registered staff. The format of the names is: 
	 * <b><tt>given name</tt></b>" "<b><tt>family name</tt></b> (there is a single space between the two).
	 * The list should be sorted by family name.
	 */
	public List<String> getRegisteredStaff();

	/**
	 * <b>(1 Mark)</b> Request a list of the names of all staff that have not be assigned as either
	 * a shift manager or as a worker on any shift.
	 * 
	 * @return A list of the names of all unassigned staff. The format of the names is: 
	 * <b><tt>given name</tt></b>" "<b><tt>family name</tt></b> (there is a single space between the two).
	 * The list should be sorted by family name.
	 */
	public List<String> getUnassignedStaff();

	/**
	 * <b>(1 Mark)</b> Request the shifts that do not have a manager assigned.
	 * @return A list of strings describing shifts that do not have a manager assigned,
	 * one string describing one shift.
	 * The format of the shift description is as described in the notes above.
	 * The order of the shifts is in the order of day of the week, and then by start time.
	 * If there are no such shifts, return an empty list.
	 */
	public List<String> shiftsWithoutManagers();

	/**
	 * <b>(1 Mark)</b> Request the shifts that do not have enough workers assigned.
	 * @return A list of strings describing shifts that have fewer workers than the minimum required,
	 * one string describing one shift.
	 * The format of the shift description is as described in the notes above.
	 * The order of the shifts is in the order of day of the week, and then by start time.
	 * If there are no such shifts, return an empty list.
	 */
	public List<String> understaffedShifts();

	/**
	 * <b>(1 Mark)</b> Request the shifts that have more workers than are needed.
	 * @return A list of strings describing shifts that have more workers than the minimum required,
	 * one string describing one shift.
	 * The format of the shift description is as described in the notes above.
	 * The order of the shifts is in the order of day of the week, and then by start time.
	 * If there are no such shifts, return an empty list.
	 */
	public List<String> overstaffedShifts();

	/**
	 * <b>(2 Marks)</b> Request the roster (who is working when and in what role) for the specfied day.
	 * @param dayOfWeek The day of the week to provide the roster for.
	 * @return A list of strings that describe who is working on what shifts for
	 * the specified day and other relevant information.
	 * <ul>
	 * <li>The first entry should have the name of the shop the roster is for.</li> 
	 * <li>The second entry should have the format: <b><tt>day of week</tt></b>" "<b><tt>working hours</tt></b>
	 * (there is a single space between the two elements).</li> 
	 * <li>The remaining strings should give the relevant details of each shift.</li> 
	 * <li>The format for a shift is <em>shift description</em>" "<em>manager description</em>" "<em>worker list</em></li>  
	 * <li>The format of the shift description is: <b><tt>dayofweek</tt></b>"["<b><tt>starttime</tt></b>"-"<b><tt>endtime</tt></b>"]"
	 * (that is, as described in the notes above).</li> 
	 * <li>The format of the manager description is: " Manager: <b><tt>family name</tt></b>", "<b><tt>given name</tt></b> (i.e. family
	 * name first) </li> 
	 * <li>The format of the worker list is "["<em>list of worker names separated by ", "</em>"]" where the
	 * worker name format is <b><tt>given name</tt></b>" "<b><tt>family name</tt></b></li> 
	 * </ul> 
	 * The order of the shifts is in the order of day of the week, and then by start time.
	 * If there are no such shifts, return an empty list.
	 * <p>Possible problems include: the day is invalid, no shop name has been given.
	 */
	public List<String> getRosterForDay(String dayOfWeek);

	/**
	 * <b>(2 Marks)</b> Request the shifts that the staff member with the supplied name is assigned to (i.e. not as manager).
	 * @param workerName The name of the staff member in format: <b><tt>given name</tt></b>" "<b><tt>family name</tt></b>
	 * @return a list of strings describing the shifts the staff member is a worker for.
	 * <ul>
	 * <li>
	 * The first entry should have the name of the worker with the format <b><tt>family name</tt></b>", "<b><tt>given name</tt></b> (i.e. family
	 * name first)
	 * <li>
	 * The remaining strings are the shifts, one string per shift, using the format described in the notes above.
	 * </ul>
	 * The order of the shifts is in the order of day of the week, and then by start time.
	 * If there are no such shifts, return an empty list.
	 * <p>Possible problems include: the staff member is not registered.
	 */
	public List<String> getRosterForWorker(String workerName);
	
	/**
	 * <b>(1 Mark)</b> Request the shifts that the staff member with the supplied name is the manager for.
	 * @param managerName The name of the staff member in format: <b><tt>given name</tt></b>" "<b><tt>family name</tt></b>
	 * @return a list of strings describing the shifts the staff member is the manager for.
	 * <ul>
	 * <li>
	 * The first entry should have the name of the manager with the format <b><tt>family name</tt></b>", "<b><tt>given name</tt></b> (i.e. family
	 * name first)
	 * <li>
	 * The remaining strings are the shifts, one string per shift, using the format described in the notes above.
	 * </ul>
	 * The order of the shifts is in the order of day of the week, and then by start time.
	 * If there are no such shifts, return an empty list.
	 * <P>Possible problems include: the staff member is not registered.
	 */
	public List<String> getShiftsManagedBy(String managerName);
	
	/**
	 * <b>(0 Marks)</b> Request the current issues with the current roster in a suitably formatted form.
	 * @return A string describing the issues.
	 * 
	 * <p><em>NOT MARKED</em> This will not be marked. It is provided as an example of 
	 * how a client might get information from the server, and may be useful for development.
	 */
	public String reportRosterIssues();
	
	/**
	 * <b>(0 Marks)</b> Request the current roster in a suitably formatted form.
	 * @return A string describing the roster.
	 * 
	 * <em>NOT MARKED</em> This will not be marked. It is provided as an example of 
	 * how a client might get information from the server, and may be useful for development.
	 */
	public String displayRoster();
}
