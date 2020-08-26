import java.io.*;
import java.util.Scanner;
/**
 * The Driver class runs the whole program, The Multi-Level Feed Back Simulation
 *
 * @author Richard Stegman 
 * @version 10/18/2019
 */
public class Driver
{
   public static void main (String [] args) throws IOException{
       
       PrintWriter pw = new PrintWriter(new FileWriter("csis.txt"));
       Scanner fileScan = new Scanner(new File("mfq5.txt"));
       
       Mfq mfq = new Mfq(fileScan, pw);
       mfq.getJobs();
       mfq.outputHeader();
       mfq.runSimulation();
       mfq.outStats();
       
       pw.close();
       fileScan.close();
       
    }
}
