jboss-properties
================

Demonstration of how classpath and system properties can be used within an application deployed on JBoss EAP 6.1+


###Build the Base Project

Using Maven, build the project with the following command:

    mvn clean install
    
This will produce an artifact called jboss-properties.war in the target folder. After deploying to JBoss, the application can be accessed at [http://localhost:8080/jboss-properties] (http://localhost:8080/jboss-properties)

###Use a Deployment Overlay

Create a Deployment Overlay and link the deployment using the JBoss CLI


    /deployment-overlay=propertiesoverlay:add

    /deployment-overlay=propertiesoverlay/content=WEB-INF\/classes\/jboss-properties.properties:add(content={url=file://<APPROOT>/support/deployment-overlay-jboss-properties.properties})

    /deployment-overlay=propertiesoverlay/deployment=jboss-properties.war:add
   
Redeploy the application if it is already deployed

*Resources*: [Deployment Overlay](https://docs.jboss.org/author/display/AS72/Deployment+Overlays)

###Utilize a Core Module
An example core module is provided in the *support/modules* folder. Copy to the *<JBOSS_HOME>/modules* folder

####Implement Using a jboss-deployment-structure.xml File

A sample file is included in *support/build/jboss-deployment-structure.xml*

To utilize within the project, run the following command to build the project

    mvn clean install -P jboss-module

Redeploy the application if it is already deployed

####Implement Using a Global Module

Two versions of the com.andyserver.jboss module are provided. The previous second utilized the default *main* version. This will utilize the **alt** version. Global modules can be added using the JBoss Management Console or by running the following command in the JBoss CLI

    /subsystem=ee:add(global-modules=[name=com.andyserver.jboss,slot=alt])

Redeploy the application if it is already deployed

####Order of Precedence 

1. Core module configured as a global module
2. Core module included as a dependency of an application
3. Deployment overlay
4. Resource packaged within an application

###JVM System Properties
The following sections describe how to declare JVM System Properties for use by the JBoss server its applications

*Note*: Each of the following examples will specify a key value of *prop1* and value of *jboss*

####Arguments During Server Startup
Can be specified in -Dproperty syntax when initializing the JBoss Server as follows:
    
    ./standalone.sh -Dprop1=jboss

####Load Properties From a File(s)
Properties file can be used as system properties by passing the *-P* flag at server startup. A sample file is provided at *support/standalone-jboss-properties.properties*

Startup the JBoss server loading the values in the properties file by executing the following command:

    ./standalone.sh –P <project_root>/support/ standalone-jboss-properties.properties

####Specify in JBoss Server Configuration

System properties can be specified within the server configuration. This can be accomplished through the Management Console or by running the following command using the JBoss CLI:

    /system-property=prop1:add(value=jboss) 

####Order of Precedence 

1. Values specified within the JBoss Configuration
2. Included from a declared Properties File
3. Passed as Command Line Arguments