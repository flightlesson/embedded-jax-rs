package com.strongblackcoffee.api;

import java.io.File;
import java.net.URI;
import javax.ws.rs.core.UriBuilder;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Replace this with an overview of the application that answers the questions
 * "what is this?" and "why would I want to use it?".
 */
public class JerseyDemo implements Runnable {
    static final Logger LOGGER = Logger.getLogger("JerseyDemo");
    
    static private final String USAGE = "replace this with stuff that will appear after 'usage:'";
    static private final String HEADER = "Replace this with a brief summary describing what the application does.\nOptions are:";
    static private final String FOOTER = "\n"
            +"Replace this with a longer description of the application "
            +"that answers 'what does this do?' and 'why should I use it?'.\n"
            +"\n"
            +"I like to use a blank line to separate paragraphs.\n"
            +"\n";
    static private final Options OPTIONS;
    static {
        OPTIONS = new Options();
        OPTIONS.addOption("h","help",false,"Print this message.");
	OPTIONS.addOption("v","verbose",false,"Turn on verbose output.");
        OPTIONS.addOption(null,"debug",false,"set fallback log4j configurationlevel to DEBUG");
        OPTIONS.addOption(null,"l4jconfig",true,"Path to the log4j configuration file [./l4j.lcf]");
        // Add application specific options here.
    }
    
    static public void main(String[] args) {
        try {
            CommandLine cmdline = (new DefaultParser()).parse(OPTIONS,args);
            if (cmdline.hasOption("help")) {
                (new HelpFormatter()).printHelp(USAGE,HEADER,OPTIONS,FOOTER,false);
                System.exit(1);
            }
            configureLog4j(cmdline.getOptionValue("l4jconfig","l4j.lcf"),cmdline.hasOption("debug"));
        
            JerseyDemo application = new JerseyDemo(cmdline.hasOption("verbose"), cmdline.getArgs());
            application.run();
        } catch (ParseException ex) {
            // can't use logger; it's not configured
            System.err.println(ex.getMessage());
            (new HelpFormatter()).printHelp(USAGE,HEADER,OPTIONS,FOOTER,false);
        }
    }
    
    static void configureLog4j(String l4jconfig,boolean debug) {
        if ((new File(l4jconfig)).canRead()) {
            if (l4jconfig.matches(".*\\.xml$")) {
                DOMConfigurator.configureAndWatch(l4jconfig);
            } else {
                PropertyConfigurator.configureAndWatch(l4jconfig);
            }
        } else {
            BasicConfigurator.configure();
            LOGGER.setLevel(debug?Level.DEBUG:Level.INFO);
        }
    }

    private final boolean verbose;
    private final String[] args;
    
    public JerseyDemo(boolean verbose, String[] args) {
        this.verbose = verbose;
        this.args = args;
    }

    @Override
    public void run() {
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(8088).build();
        LOGGER.info("configuring ...");
        ResourceConfig config = new ResourceConfig(MyResource.class,OtherResource.class);
        LOGGER.info("... config="+config+" ... starting server ...");
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(baseUri, config);
        LOGGER.info("... server="+server);
    }
}
