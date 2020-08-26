
/**
 * The CPU class processes the job object in the Multi-Level Feed Back Simulation. 
 *
 * @author Samuel Aguirre; ID: 011137110
 * @version 10/18/2019
 */
public class CPU
{
    private boolean busyFlag;
    private int cpuTime;
    private int quantumClock;
    private int currentQ;
    private int responseTime;
    private Job runningJob;
    
    /**
     * Constructor for objects of class CPU
     * 
     * @author Samuel Aguirre; ID: 011137110
     */
    public CPU(){
        busyFlag = false;

    }

    /**
     * The setBusyFlag() sets the busy flag to determine if the cpu is busy
     * 
     * @param flag, the current status of the cpu 
     * @author Samuel Aguirre; ID: 011137110
     */
    public void setBusyFlag(boolean flag){

        busyFlag = flag;
    }

    /**
     * The getBusyFlag() returns the current status of the cpu
     * 
     * @return current status of the cpu
     * @author Samuel Aguirre; ID: 011137110
     */
    public boolean getBusyFlag(){

        return busyFlag;
    }

    /**
     * The getQuantumClock() returns the amount of time of the quantum clock
     * 
     * @return the amount of time on the quantum clock 
     * @author Samuel Aguirre; ID: 011137110
     */
    public int getQuantumClock(){

        return quantumClock;
    }

    /**
     * The getCpuTime() retuns the amount of cpu time of the job
     * 
     * @return the amount of cpu time of the job 
     * @author Samuel Aguirre; ID: 011137110
     */
    public int getCpuTime(){

        return cpuTime;
    }

    /**
     * The setCpuTime() sets the time needed in the cpu 
     * 
     * @author Samuel Aguirre; ID: 011137110
     */
    public void setCpuTime(){

        cpuTime = runningJob.getCpuTimeRemaining();
    }
    
    /**
     * The getJob() returns the job object
     * 
     * @return the job object 
     * @author Samuel Aguirre; ID: 011137110
     */
    public Job getJob(){

        return runningJob;   
    }
    
    /**
     * The getQueueLevel() returns the current level the job is in. 
     * 
     * @return the current queue level of the job
     * @author Samuel Aguirre; ID: 011137110
     */
    public int getQueueLevel(){

        return currentQ;
    }

    /**
     * The jobToProcess() takes in a job and assigns it the appropriate quantum time depending on the 
     * current queue that it is in. 
     * 
     * @param newJob, the job that will be processed in the cpu; queue, the current queue the job is in
     * @author Samuel Aguirre; ID: 011137110
     */
    public void jobToProcess(Job newJob, int queue){

        runningJob = newJob;
        currentQ = queue;

        if(currentQ == 1){
            quantumClock = 2;
            cpuTime = runningJob.getCpuTimeRequired();
        }
        else if(currentQ == 2){
            quantumClock = 4;
            cpuTime = runningJob.getCpuTimeRemaining();
        }
        else if(currentQ == 3){
            quantumClock = 8;
            cpuTime = runningJob.getCpuTimeRemaining();
        }
        else if(currentQ == 4){
            quantumClock = 16;
            cpuTime = runningJob.getCpuTimeRemaining();
        }

    }

    /**
     * The processJob() decrements the quantumClock and cpuTime of the current Job that is being processed. 
     * And also sets the remaining time for the job. 
     * 
     * @author Samuel Aguirre; ID: 011137110
     */
    public void processJob(){

        if(quantumClock > 0){

            --quantumClock;
            --cpuTime;
            runningJob.setTimeRemaining(cpuTime);

        }

    }
}
