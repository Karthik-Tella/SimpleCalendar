/**
 * Class used to return the .xml extension
 * Helper class for Strategy Pattern
 * Implements Saver interface
 * @author Karthik Tella
 *
 */
public class XmlSaver implements Saver{
	/**
	 * Returns the .xml extension used in CalendarSaver
	 * @return .xml tag
	 */
	@Override
	public String getExtension() {
		return ".xml";
	}
}