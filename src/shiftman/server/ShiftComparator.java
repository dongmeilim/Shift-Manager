package shiftman.server;

import java.util.Comparator;

/**
 * A comparator for sorting Shifts by day and then time
 */
public class ShiftComparator implements Comparator<Shift>{
	@Override
	public int compare(Shift o1, Shift o2) {

         int Comp = o1.getDay().compareTo(o2.getDay());

         if (Comp != 0) {
            return Comp;
         } 

         Integer x1 =o1.getStart().asMinutes();
         Integer x2 =o2.getStart().asMinutes();
         return x1.compareTo(x2);
 }


}
