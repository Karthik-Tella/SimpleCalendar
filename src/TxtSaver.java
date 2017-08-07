/**
 * Class used to return the .txt extension
 * Helper class for Strategy Pattern
 * Implements Saver interface
 * @author Karthik Tella
 *
 */
public class TxtSaver implements Saver{
	
	/**
	 * Returns the .txt extension used in CalendarSaver
	 * @return .txt tag
	 */
	@Override
	public String getExtension() {
		return ".txt";
	}
}