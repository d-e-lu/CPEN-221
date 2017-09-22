package schedulers;

public class Task {
	
	private static final int NOT_RELEASED = -1;
	
	private final int procTime; // the total processing time needed for this
								// task
	private final int relDeadline; // the deadline for this task
	private int remProcTime; // the remaining processing time for this task
	private int releaseTime; // the time at which the task is added to a
								// scheduler
	private int absDeadline; // the absolute deadline (releaseTime+relDeadline)
	private final int taskID; // the task identifier
	private static int taskCounter; // counts the # of tasks created

	/**
	 * Get task ID
	 * @return the task's ID
	 */
	public int getTaskID() {
		return taskID;
	}
	
	/**
	 * Create a new task given processing time and relative deadline
	 * 
	 * @param procTime
	 *            > 0, and also < relDeadline
	 * @param relDeadline
	 *            > 0, and also > procTime
	 */
	public Task(int procTime, int relDeadline) {
		this.procTime = procTime;
		this.relDeadline = relDeadline;
		remProcTime = procTime;
		releaseTime = NOT_RELEASED; // to indicate that the task has not been added to the
							// ready queue yet
		taskID = ++taskCounter;
	}

	/**
	 * Release the task at the current time. This method sets the release time
	 * and absolute deadline for this task.
	 * 
	 * @param currentTime
	 *            the time at which the job was released/added to the task queue
	 * 
	 */
	public void release(int currentTime) {
		releaseTime = currentTime;
		absDeadline = releaseTime + relDeadline;
	}

	/**
	 * Get the remaining processing time
	 * 
	 * @return remaining processing time
	 */
	public int getRemProcTime() {
		return remProcTime;
	}

	/**
	 * Get the absolute deadline for this task
	 * 
	 * @return the absolute deadline
	 * @throws TaskNotReadyException
	 */
	public int getAbsDeadline() throws TaskNotReadyException {
		if (releaseTime == NOT_RELEASED) {
			throw new TaskNotReadyException();
		} else {
			return absDeadline;
		}
	}

	/**
	 * Simulate execution by one time tick
	 * 
	 * Requires: getRemProcessingTime( ) > 0 Modifies: this Task Effects: Reduce
	 * remaining processing time
	 */
	public void oneTick() {
		remProcTime--;
	}
}
