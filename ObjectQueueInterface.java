
/**
 * The ObjectQueueInterface holds the ObjectQueue signatures 
 *
 * @author Samuel Aguirre 
 * @version 10/18/2019
 */
public interface ObjectQueueInterface
{
    public boolean isEmpty();
    public boolean isFull();
    public void clear();
    public void insert(Object o);
    public Object remove();
    public Object query();
    
}
