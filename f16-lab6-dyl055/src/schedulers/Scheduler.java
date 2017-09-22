package schedulers;

public interface Scheduler {

	/**
	 * Return the time -- measured in clock ticks -- since this scheduler was
	 * created.
	 * 
	 * @return number of ticks since Scheduler creation
	 */
	public int getTime();

	/**
	 * Add a new task for the scheduler
	 * 
	 * @param t
	 *            the task to be added
	 * @return true if successful, false otherwise
	 */
	public boolean addTask(Task t);

	/**
	 * Remove a task from the scheduler
	 * 
	 * @param t
	 *            the task to be removed
	 * @return true if successful, false otherwise
	 */
	public boolean delTask(Task t);

	/**
	 * Get the task id of the current highest priority task
	 * 
	 * @return the task id of the current highest priority task. If the
	 *         scheduler has nothing to do (idle) then return the 0 to indicate
	 *         idle task.
	 */
	public int currentTaskID();

	/**
	 * Respond to a clock tick.
	 * 
	 * This method will update the remaining execution time for the current task
	 * and then update task priorities (if new tasks were added or if the
	 * current task is done).
	 * 
	 * This method also updates the scheduler's time.
	 */
	public void tick();

}
