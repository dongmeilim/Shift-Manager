# Shift-Manager
Use OOP in Java to create a shift manager for a shop

# Domain Model:
To manage shifts and work staff, we have the shift manager. It contains a roster and staff
register for a shop. The roster adds shifts and opening hours for one week, and the shifts
have times, workers, and a manager. The staff register is list of workers registered at the
shop.
# Design:
The main concepts and classes in this implementation of ShiftMan are Roster, Staff, Shift,
Worker, WorkingDay, Week, and Time. Shift and Worker are needed because shifts and
workers are what is being managed by this system. To store/manage them, we need a
Roster, and a Staff registry. Since shifts must be within the working hours for the day, the
WorkingDay is needed to store the working hours. Shift and WorkingDay haves times and
days of the week, hence the need for Time and Week.
Roster is represented as a list of Shifts, an array of WorkingDays, and the name of the shop.
Staff is represented as a list of Workers.
Shift is represented by a day of the Week, start Time, end Time, minimum number of
workers, a Staff object for the workers, a boolean for if the shift has a manager, and a
Worker for the manager if one is assigned.
Worker is represented as their first name, surname, and whether they have been assigned a
shift.
WorkingDay is represented by a day of the Week, start Time, and end Time.
Week is an enumerated type containing the values for the days of the week, since the days
of the week cannot change.
Time is represented by an hour and minute integer.

# Objects created:
newRoster : A Roster object and Staff object.
setWorkingHours: A WorkingDay object is created inside Roster. Two Time objects - one
for the start of the working hours, and one for the end - and a Week object for the day of the
week are created inside WorkingDay.
addShift : a Shift object is created inside Roster. Two Time objects (one for the start of the
shift, and one for the end), a Week object for the day of the week, and a Staff object for the
workers are created inside Shift.
registerStaff : A Worker object is created inside Staff.

# Errors and Checked Exceptions:
To detect the errors, the checked exceptions InvalidWeekException, InvalidTimeException,
InvalidShiftException, InvalidWorkerException, and NoWorkingHoursException were
created. When errors were detected in any of the classes, the appropriate exception would
be thrown. The exceptions are declared in the method signatures for all classes except
ShiftManServer, where they are surrounded with a try/catch statement. When an exception is
caught, the method in ShiftManServer returns a string containing an error message instead
of an empty string.
The errors detected include:
addShift/setWorkingHours/assignStaff: The value given for the day of week is invalid, the
start time is the same as or after the end time, one or both times are invalid
addShift : the shift is not within the working hours, the shift overlaps with an already existing
shift.
assignStaff : the specified shift has not been set, the specified staff member is not
registered, there is already a staff member assigned as the manager.
getRosterForDay: the day is invalid.
getRosterForWorker/getShiftsManagedBy: the staff member is not registered
registerStaff :one or both of the names are empty, or someone with the supplied name
(ignoring case) already registered
