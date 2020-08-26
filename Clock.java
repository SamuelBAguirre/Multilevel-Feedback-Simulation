
/**
 * The Clock class represents the system clock of during the The Multi-Level Feed Back Simulation
 *
 * @author Samuel Aguirre; ID: 011137110
 * @version 10/18/2019
 */
public class Clock
{
    private int systemClock;

    /**
     * Constructor for the Clock class
     * 
     * @author Samuel Aguirre; ID: 011137110
     */

    Clock() {

        systemClock = 0;
    }

    /**
     * The getSystemClock() returns the system time during the simulation
     *
     * @return the current system time 
     * @author Samuel Aguirre; ID: 011137110
     */

    public int getSystemClock() {

        return systemClock;
    }

    /**
     * The setSystemClock() sets the system time by incrementing it 
     * 
     * @author Samuel Aguirre; ID: 011137110
     */

    public void setSystemClock() {

        systemClock += 1;
    }
}
