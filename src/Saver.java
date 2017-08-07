/**
 * Saver interface used for Strategy pattern  and saving files in different format
 * @author Karthik Tella
 *
 */
public interface Saver {
	/**
	 * Changes the extension type based on calendar state
	 * @return
	 */
	public String getExtension();
}


