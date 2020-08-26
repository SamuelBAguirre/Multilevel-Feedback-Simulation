import java.util.Scanner;
import java.io.*;
/**
 * The Mfq class implements and runs the Multi-Level Feed Back Simulation. 
 *
 * @author Samuel Aguirre, ID: 011137110
 * @version 10/18/2019
 */
public class Mfq
{
    private ObjectQueue inputQueue;
    private ObjectQueue queue1;
    private ObjectQueue queue2;
    private ObjectQueue queue3;
    private ObjectQueue queue4;
    private Scanner fileScan;
    private PrintWriter pw;
    private Clock systemClock;
    
    private int responseTime;
    private int totalJobs;
    private int sumTotalTime;
    private int totalCpuTime;
    private double avgResponseTime;
    private boolean preemption;
    
    

    /**
     * Constructor for objects of class Mfq
     * 
     * @param pw, PrintWriter used to write to the output file; sc, passes the job information
     * @author Samuel Aguirre, ID: 011137110
     */
    public Mfq(Scanner sc, PrintWriter pw)
    {
        // initialise instance variables
        this.pw = pw;
        this.fileScan = sc;
        this.inputQueue = new ObjectQueue();
        this.queue1 = new ObjectQueue();
        this.queue2 = new ObjectQueue();
        this.queue3 = new ObjectQueue();
        this.queue4 = new ObjectQueue();
        this.systemClock = new Clock();
        totalJobs = 0;

    }

    /**
     * The getJobs() reads in a string from mfq.txt and creates a job object by using the split method. Each job object 
     * is then inserted into the inputQueue. 
     * 
     * @author Samuel Aguirre, ID: 011137110
     */
    public void getJobs() {
        while(fileScan.hasNext()) {

            String buf = fileScan.nextLine();

            String delims = "[ ]+";
            String[] tokens = buf.split(delims);

            Job job = new Job(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
            inputQueue.insert(job);

        }

    }

    /**
     * The outputHeader() displays the header for the job information. 
     * 
     * @author Samuel Aguirre, ID: 011137110
     */
    public void outputHeader(){

        String header1 = "Event";
        String header2 = "System Time";
        String header3 = "PID";
        String header4 = "CPU Time Needed";
        String header5 = "Total Time in System";
        String header6 = "Lowest Level Queue";
        String header7 = "Response Time";
        System.out.printf("%s %20s %13s %25s %27s %28s %23s" ,header1, header2, header3 , header4, header5, header6, header7 + "\n");
        pw.printf("%s %20s %13s %25s %27s %28s %23s" ,header1, header2, header3 , header4, header5, header6, header7 + "\n");

    }

    /**
     * The runSimulation() conducts the Multi-Level Feed Back Simulation, uses the algorithm as seen 
     * during lecture. Will also display when a job enters the system and when a job is done processing.  
     * 
     * @author Samuel Aguirre, ID: 011137110
     */
    public void runSimulation(){

        CPU cpu = new CPU();
        preemption = false;

        while (!inputQueue.isEmpty() || !queue1.isEmpty() || !queue2.isEmpty() || !queue3.isEmpty() || !queue4.isEmpty() || cpu.getBusyFlag() == true){


            if(!inputQueue.isEmpty() && ( (Job) inputQueue.query()).getArrivalTime() == systemClock.getSystemClock()){
                
                while (!inputQueue.isEmpty() && ( (Job) inputQueue.query()).getArrivalTime() == systemClock.getSystemClock()){
                    ++totalJobs;
                    Job jobTemp = (Job) inputQueue.query();
                    System.out.printf("Arrival: %17s %13s %25s\n" ,jobTemp.getArrivalTime(), jobTemp.getPid(), jobTemp.getCpuTimeRequired());
                    pw.printf("Arrival: %17s %13s %25s\n" ,jobTemp.getArrivalTime(), jobTemp.getPid(), jobTemp.getCpuTimeRequired());
                    queue1.insert((Job) inputQueue.remove());

                    
                }

            }

            if(cpu.getBusyFlag() == true){
                
                cpu.processJob();
                
                if(cpu.getCpuTime() == 0){
                    
                    int totalTimeInSystem = systemClock.getSystemClock() - (cpu.getJob()).getArrivalTime();
                    sumTotalTime += totalTimeInSystem;
                    totalCpuTime += (cpu.getJob()).getCpuTimeRequired();
                    cpu.setBusyFlag(false);
                    System.out.printf("Depart:%19s %13s %53s %28s %22s\n" ,systemClock.getSystemClock(), (cpu.getJob()).getPid(), totalTimeInSystem,  cpu.getQueueLevel(), (cpu.getJob()).getResponseTime());
                    pw.printf("Depart:%19s %13s %53s %28s %22s\n" ,systemClock.getSystemClock(), (cpu.getJob()).getPid(), totalTimeInSystem,  cpu.getQueueLevel(), (cpu.getJob()).getResponseTime());
                    cpu = new CPU();

                }
                else if (cpu.getCpuTime() > 0){
                    
                    if (!queue1.isEmpty()){

                        preemption = true;
                    }
                    if(cpu.getQuantumClock() == 0){

                        preemption = true;
                    }
                }
            }

            if (preemption == true){

                Job moveJob = cpu.getJob();
                int level = cpu.getQueueLevel();

                moveNextLevel(moveJob, level);

                cpu = new CPU();
                cpu.setBusyFlag(false);
                preemption = false;
            }

            if(cpu.getBusyFlag() == false && !queue1.isEmpty()){
                int queueLevelOne = 1;
                cpu.setBusyFlag(true);

                ((Job) queue1.query()).setResponseTime(systemClock.getSystemClock() - ((Job) queue1.query()).getArrivalTime());
                avgResponseTime += systemClock.getSystemClock() - ((Job) queue1.query()).getArrivalTime();
                
                
                cpu.jobToProcess((Job) queue1.remove(),queueLevelOne);

                
            }
            else if(cpu.getBusyFlag() == false && !queue2.isEmpty() ){

                int queueLevelTwo = 2;
                cpu.setBusyFlag(true);
                cpu.jobToProcess((Job) queue2.remove(), queueLevelTwo);

            }
            else if(cpu.getBusyFlag() == false && !queue3.isEmpty() ){

                int queueLevelThree = 3;
                cpu.setBusyFlag(true);
                cpu.jobToProcess((Job) queue3.remove(), queueLevelThree);

            }
            else if(cpu.getBusyFlag() == false && !queue4.isEmpty() ){

                int queueLevelFour = 4;
                cpu.setBusyFlag(true);
                cpu.jobToProcess((Job) queue4.remove(), queueLevelFour);

            }

            systemClock.setSystemClock();
        }

    }

    /**
     * The moveNextLevel() is used when a job is preempted, will move a job to the next lower queue. 
     * This method will be called when the quantum clock reaches 0 or if queue1 !isEmpty(). 
     * 
     * @param movedJob, the job that is being preempted; level, indicates the current queue the job is in 
     * @author Samuel Aguirre, ID: 011137110
     */
    private void moveNextLevel(Job movedJob, int level){

        if(level == 1){
            queue2.insert(movedJob);

        }
        else if(level == 2){
            queue3.insert(movedJob);

        }
        else if(level == 3){
            queue4.insert(movedJob);

        }
    }
    
    /**
     * The outStats() displays the different statistics from the simulation. 
     * 
     * @author Samuel Aguirre, ID: 011137110
     */
    public void outStats(){
        double avgTurnAround = (double) sumTotalTime/totalJobs;
        double avgWaitTime = (double) (sumTotalTime - totalCpuTime)/totalJobs;
        double avgThroughSystem = (double) totalJobs/sumTotalTime;
        System.out.println("\n");
        System.out.println("Total Number Of Jobs: " + totalJobs);
        System.out.println("Total Time Of All Jobs In System: " + sumTotalTime);
        System.out.println("Average Response Time: " + avgResponseTime/totalJobs);
        System.out.println("Average Turnaround Time For The Jobs: " + (double) avgTurnAround);
        System.out.println("Average Waiting Time: " + (double) avgWaitTime);
        System.out.printf("Average Throughout For The System As A Whole: %.2f\n " ,(double) avgThroughSystem);
        
        pw.println("\n");
        pw.println("Total Number Of Jobs: " + totalJobs);
        pw.println("Total Time Of All Jobs In System: " + sumTotalTime);
        pw.println("Average Response Time: " + avgResponseTime/totalJobs);
        pw.println("Average Turnaround Time For The Jobs: " + (double) avgTurnAround);
        pw.println("Average Waiting Time: " + (double) avgWaitTime);
        pw.printf("Average Throughout For The System As A Whole: %.2f\n " ,(double) avgThroughSystem);
        
        
    }
}
