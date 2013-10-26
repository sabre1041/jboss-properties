package com.andyserver.jboss;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet demonstrating how to load and utilize classpath and System Properties within an application 
 * 
 * @author Andrew Block
 *
 */
@WebServlet("/")
public class PropertiesServlet extends GenericServlet {

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesServlet.class);
	
	private static final String PROPERTIES_FILE_NAME = "jboss-properties.properties";

	@Override
	public void service(ServletRequest req, ServletResponse res)
			throws ServletException, IOException {
		
		res.setContentType("text/html");
		
		PrintWriter writer =  res.getWriter();
		writer.print("<title>JBoss Properties</title>");
		
				
		Properties properties = getProperties();
		
		if(properties != null && properties.keySet().size() > 0) {
			
			writer.print("<table><tr><td><b>Key</b></td><td><b>Value<b></td><td><b>Type</b></td></tr>");
			
			for(Object key : properties.keySet()) {
				
				String keyStr = String.valueOf(key);
				
				String envProp = System.getProperty(keyStr);
				String localProp = properties.getProperty(keyStr);
				
				if(localProp != null) {
					writer.print("<tr><td>"+keyStr+"</td><td>"+ localProp + "</td><td>Local</td></tr>");
				}
				
				if(envProp != null) {
					writer.print("<tr><td>"+keyStr+"</td><td>"+ envProp + "</td><td>System</td></tr>");
				}

				
			}
			
			writer.print("</tr></table>");

			
		}
		else {
			writer.print("No Properties on Classpath");
		}
		
		
		
		
	}
	
	
	private Properties getProperties() {
					
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME);
		
		Properties properties = new Properties();
		
		try {
			properties.load(is);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		finally {
			try {
				is.close();
			}
			catch(Exception e) {
				
			}
		}
		
		return properties;
	}
	
}
