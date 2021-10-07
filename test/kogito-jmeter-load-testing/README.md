## General

JMeter should be run in GUI mode for defining and debugging test cases.

When running the real load tests the test case should be run in CLI mode with as little listeners defined as needed.

### Documentation

[JMeter Site](https://jmeter.apache.org/)
<br/>[JMeter Plugins](https://jmeter-plugins.org/)

## Installation

[Download JMeter](https://dlcdn.apache.org//jmeter/binaries/apache-jmeter-5.4.1.tgz)
- unzip in the location you want JMeter to be installed

[Download Plugin manager](https://jmeter-plugins.org/get/) for easier management of plugins
- copy the downloaded file "jmeter-plugins-manager-1.6.jar" (or latest version) to <JMETER_HOME>/lib/ext
- to be able to use the Plugin Manager as well as the plugins themselves also from the command line, the "cmdrunner-x.x.jar" is needed.

[Download Plugin CLI Runner](https://jmeter-plugins.org/get/) as bundle with shell script "JMeterPluginsCMD.sh" for convenient plugin execution
- unzip onto JMeter installation directory - will add files under bin, lib and lib/ext

At first opening of the JMeter GUI a PluginsManagerCMD.bat/.sh is created in <JMETER_HOME>/bin allowing for easy use of Plugin Manager from the command line.
If the JMeter GUI is not used, create the script by running: 
```shell script
java -cp <JMETER_HOME>/lib/ext/jmeter-plugins-manager-x.x.jar org.jmeterplugins.repository.PluginManagerCMDInstaller
```

### Notes
When using plugin AggregateReport from CLI, one needs to also install the plugin "Synthesis Report" or else there will be an exception on execution: 
- Open JMeter GUI
```shell script
<JMETER_HOME>/bin/jmeter.sh
```
- Go to Menu Options -> Plugins Manager
- Add "Synthesis Report" Plugin from "Available Plugins" tab
- Choose "Apply Changes and Restart JMeter"

## Running JMeter GUI
```shell script
<JMETER_HOME>/bin/jmeter.sh
```

## Running JMeter test from command line
- cd into the <benchmark-home>/test/kogito-jmeter-load-testing
- copy one of the provided example properties files (envLocal, envOCPlab) and change to your needs
- run below script with your properties file
```shell script
runTest.sh envOCPlab
```
