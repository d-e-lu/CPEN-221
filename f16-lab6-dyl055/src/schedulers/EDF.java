package schedulers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EDF implements Scheduler{
	private int ticks = 0;
	private List<Task> tasks = new ArrayList<Task>();
	private static Comparator<Task> c = new Comparator<Task>(){
		@Override
		public int compare(Task t1, Task t2) {
			try {
				return t1.getAbsDeadline() - t2.getAbsDeadline();
			} catch (TaskNotReadyException e) {
				e.printStackTrace();
			}
			return -1;
		}
		
	};
	/**
	 * Return the time -- measured in clock ticks -- since this scheduler was
	 * created.
	 * 
	 * @return number of ticks since Scheduler creation
	 */
	public int getTime(){
		return ticks;
	}

	/**
	 * Add a new task for the scheduler
	 * 
	 * @param t
	 *            the task to be added
	 * @return true if successful, false otherwise
	 */
	public boolean addTask(Task t){
		
		if(tasks.contains(t)){
			t.release(ticks);
			tasks.add(t);
			tick();
			return true;
		}
		else{
			return false;
		}
		
	}

	/**
	 * Remove a task from the scheduler
	 * 
	 * @param t
	 *            the task to be removed
	 * @return true if successful, false otherwise
	 */
	public boolean delTask(Task t){
		if(tasks.contains(t)){
			tasks.remove(t);
			tick();
			return true;
		}
		else{
			return false;
		}
		
	}

	/**
	 * Get the task id of the current highest priority task
	 * 
	 * @return the task id of the current highest priority task. If the
	 *         scheduler has nothing to do (idle) then return the 0 to indicate
	 *         idle task.
	 */
	public int currentTaskID(){
		if(tasks.isEmpty()){
			return 0;
		}
		else{
			return tasks.get(0).getTaskID();
		}
	}

	/**
	 * Respond to a clock tick.
	 * 
	 * This method will update the remaining execution time for the current task
	 * and then update task priorities (if new tasks were added or if the
	 * current task is done).
	 * 
	 * This method also updates the scheduler's time.
	 */
	public void tick(){
		tasks.get(0).oneTick();
		ticks++;
		Collections.sort(tasks, c);
	}

}
