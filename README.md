# UniversalConverter

# Description
HTTP conversion service for all units.
# API
Server has following endpoints:
POST localhost:8080/convert with JSON in request body:
```bash
{
 "from": "<expression in original units>",
 "to": "<expression in units of measurement to be obtained>"
}
```
# Build
Building the service using Apache Maven using the mvn package command.
The service is launched using the java -jar universal-converter-1.0.0.jar /path/to/file.csv command, where /path/to/file.csv is the path to the file with the conversion rules.
