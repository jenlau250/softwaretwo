/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jCalendar.utilities;

import java.io.IOException;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author jlau2
 */
public class Loggerutil {

//    private final Logger logger = Logger.getLogger(Loggerutil.class.getName());
//    private FileHandler fh = null;
//
//    public Loggerutil() {
//	//just to make our log file nicer :)
//	try {
//	    fh = new FileHandler("MyLog.log");
//	    System.out.println(fh);
//	} catch (Exception e) {
//	    e.printStackTrace();
//	}
//
//	fh.setFormatter(new SimpleFormatter());
//	logger.addHandler(fh);
//    }
//
//    public void doLogging() {
//	logger.info("info msg");
//	logger.severe("error message");
//	logger.fine("fine message"); //won't show because to high level of logging
//    }
//}


//	    //The following four lines write the log text to a file. Otherwise it will print only to the console. 
//	    FileHandler fh = new FileHandler("log.txt", true);
//	    SimpleFormatter sf = new SimpleFormatter();
//	    fh.setFormatter(sf);
//	    log.addHandler(fh);
	    //change the following line to change what gets logged.
	    // Here is the descending list:
//        SEVERE (highest)
//        WARNING
//        INFO
//        CONFIG
//        FINE
//        FINER
//        FINEST
//      So if you set the following line to log.setLevel(Level.INFO), only logs that have levels SEVERE, WARNING, or INFO will actually get logged.
//      Great for debugging! You could set it to FINEST, and then when you put the code into production, set it to INFO or WARNING, for instance, so that you
//      don't get the debugging log info in your text file
//	} catch (IOException ex) {
//	    Logger.getLogger(Loggerutil.class.getName()).log(Level.SEVERE, null, ex);
//	} catch (SecurityException ex) {
//	    Logger.getLogger(Loggerutil.class.getName()).log(Level.SEVERE, null, ex);
//	}
//
//	log.setLevel(Level.CONFIG); //change this line to see how the output changes!

//    }
//}


    private final static Logger logger = Logger.getLogger(Loggerutil.class.getName());
    private static FileHandler handler = null;

    
    public static void init() {
	try {
	    //The following four lines write the log text to a file. Otherwise it will print only to the console. 
	    FileHandler handler = new FileHandler("log.txt", true);
	} catch (SecurityException | IOException e) {
	    e.printStackTrace();
	}

//	Logger logger = Logger.getLogger("");
	handler.setFormatter(new SimpleFormatter());
	logger.addHandler(handler);
	logger.setLevel(Level.FINE);
    }

    //change the following line to change what gets logged.
    // Here is the descending list:
//        SEVERE (highest)
//        WARNING
//        INFO
//        CONFIG
//        FINE
//        FINER
//        FINEST
//      So if you set the following line to log.setLevel(Level.INFO), only logs that have levels SEVERE, WARNING, or INFO will actually get logged.
//      Great for debugging! You could set it to FINEST, and then when you put the code into production, set it to INFO or WARNING, for instance, so that you
//      don't get the debugging log info in your text file
}
