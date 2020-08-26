
/**
 * The Job class is a represents a single job in the simulation during processing. 
 *
 * @author Samuel Aguirre; ID: 011137110
 * @version 10/18/2019
 */
public class Job
{
    // instance variables 
    private int arrivalTime;
    private int pid;
    private int cpuTimeRequired;
    private int cpuTimeRemaining;
    private int currentQueue;
    private int responseTime;

    /**
     * Constructor for objects of class Job
     * 
     * @param arrivalTime, when a job enters the system; pid, process identification of the job; cpuTimeRequired, time needed in the cpu
     * @author Samuel Aguirre, ID: 011137110
     */
    public Job(int arrivalTime, int pid, int cpuTimeRequired)
    {
        // initialise instance variables
        this.arrivalTime = arrivalTime;
        this.pid = pid;
        this.cpuTimeRequired = cpuTimeRequired;
        this.currentQueue = currentQueue;
    }

    /**
     * The getPid() returns the process identification number of the job
     * 
     * @return process identification number
     * @author Samuel Aguirre, ID: 011137110
     */
    public int getPid(){

        return pid;   
    }

    /**
     * The getArrivalTime() returns the arrival time of the job
     * 
     * @return arrival time
     * @author Samuel Aguirre, ID: 011137110
     */
    public int getArrivalTime(){

        return arrivalTime;
    }

    /**
     * The getCpuTimeRequired() returns the cpu time required for the job.
     * 
     * @return cpu time required
     * @author Samuel Aguirre, ID: 011137110
     */
    public int getCpuTimeRequired(){

        return cpuTimeRequired;
    }

    /**
     * The setTimeRemaining() sets the cpu time remaining for the job.
     * 
     * @param n, time remaining for the job
     * @author Samuel Aguirre, ID: 011137110
     */
    public void setTimeRemaining(int n){

        cpuTimeRemaining = n;   
    }

    /**
     * The getCpuTimeRemaining() returns the cpu time remaining for the job. 
     * 
     * @return cpu time remaining
     * @author Samuel Aguirre, ID: 011137110
     */
    public int getCpuTimeRemaining(){

        return cpuTimeRemaining;
    }

    /**
     * The getResponseTime() returns the response time for the job. 
     * 
     * @return response time
     * @author Samuel Aguirre, ID: 011137110
     */
    public int getResponseTime(){

        return responseTime;
    }

    /**
     * The setResponseTime() sets the response time for the job. 
     * 
     * @param time, the response time of the job
     * @author Samuel Aguirre, ID: 011137110
     */
    public void setResponseTime(int time){

        responseTime = time;
    }
}
