package K.Launcher;

public class AutoOSDetect {
	   private static String OS = System.getProperty("os.name").toLowerCase();
           
	        public static void main(String[] args) {

	        System.out.println("os.name: " + OS); // Vincent's note: I Copy pasted this code off the Internet LMAO-

	        if (isWindows()) {
		    	System.out.println("NT Version: " + System.getProperty("os.version"));
	        	System.out.println("Windows Found! Launching GUI!");
	               WindowsTM bin = new WindowsTM();
	               bin.show();
	        } else if (isMac()) {
	            System.out.println("macOS/OSX Found! Launching GUI!");
	            System.out.println("Darwin Version: " + System.getProperty("os.version"));
	               MacTM tiger = new MacTM();
	               tiger.show();
	        } /**else if (isUnix()) {
	            System.out.println("Unix or Linux Found! Launching GUI!");
	        } else if (isSolaris()) {
	            System.out.println("Solaris Found! Launching GUI!");
	        } **/else {
	            System.out.println("Un-supported OS Found!!");
	            System.exit(0);
	            /** OSerror OSerr = new OSerror();
	               OSerr.show();**/
	        }
	    }

	    public static boolean isWindows() {
	        return (OS.indexOf("win") >= 0);
	    }

	    public static boolean isMac() {
	        return (OS.indexOf("mac") >= 0);
	    }

	    public static boolean isUnix() {
	        return (OS.indexOf("nix") >= 0
	                || OS.indexOf("nux") >= 0
	                || OS.indexOf("aix") > 0);
	    }

	    public static boolean isSolaris() {
	        return (OS.indexOf("sunos") >= 0);
	    }

	}