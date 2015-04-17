import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Remove track numbers from music file names
 * @author Keripo
 */
public class RemoveMP3TrackNumber {

	private static String DEFAULT_FOLDER_PATH = "Z:\\Temp\\_Next\\_Add";
	private static Pattern TRACK_NUMBER_PATTERN = Pattern.compile("\\d\\d[ .-]*\\w", Pattern.UNICODE_CHARACTER_CLASS);
	private static String[] MUSIC_FILE_EXTENSIONS = {".mp3", ".m4a", ".flac"};
	
	/**
	 * Main
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		String startFolderPath;
		if (args.length > 0) {
			startFolderPath = args[0];
		} else {
			startFolderPath = DEFAULT_FOLDER_PATH;
		}
		checkDirectory(new File(startFolderPath), TRACK_NUMBER_PATTERN, -1, MUSIC_FILE_EXTENSIONS);
	}
	
	private static void checkDirectory(File parentDir, Pattern pattern, int patternOffset, String[] supportedExtensions) {
		for (File f : parentDir.listFiles()) {
			if (f.isDirectory()) {
				checkDirectory(f, pattern, patternOffset, supportedExtensions);
			} else {
				renameFile(f, pattern, patternOffset, supportedExtensions);
			}
		}
	}

	private static void renameFile(File file, Pattern pattern, int patternOffset, String[] supportedExtensions) {
		String oldPath = file.getAbsolutePath();
		String oldName = file.getName();
		int extensionIndex = oldName.lastIndexOf('.');
		if (extensionIndex > 0) {
			String extension = oldName.substring(extensionIndex);
			oldName = oldName.substring(0, extensionIndex);
			if (isSupportedFormat(extension, supportedExtensions)) {
				Matcher trackNameMatcher = pattern.matcher(oldName);
				if (trackNameMatcher.find()) {
					String newName = oldName.substring(trackNameMatcher.group().length() + patternOffset);
					String newPath = oldPath.replace(oldName, newName);
					System.out.println("Renaming file:");
					System.out.println("  " + oldPath);
					System.out.println("  " + newPath);
					if (!file.renameTo(new File(newPath))) {
						System.err.println("Failed to rename: " + oldPath);
					}
					return;
				}
				System.err.println("Unable to match: " + oldPath);
			}
		}
	}

	private static boolean isSupportedFormat(String extension, String[] supportedExtensions) {
		for (String supportedExtension : supportedExtensions) {
			if (supportedExtension.equalsIgnoreCase(extension)) {
				return true;
			}
		}
		return false;
	}

}
