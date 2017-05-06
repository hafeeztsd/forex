                                            # forex
                          Forex App to load the forex data into Database.
Application uses mongoDB. Make sure instance of mongoDB is up and running using the command <br/>
mongod.exe --nojournal <br/>
Don't foreget to provide --nojournal switch as it impacts upload performence. <br/>
Application will by default pick the properties available in src/main/resources directory but you can change the location by
providing spring.config.location parameter. Please makse sure application properties are configured properly before following
the below steps. <br/>

mvn clean install -Dspring.config.location=C:\Users\hafeeztsd\Desktop\forex\application.properties <br/>
java -jar target/forex-1.0.jar -Dspring.config.location=C:\Users\hafeeztsd\Desktop\forex\application.properties <br/>

After issuing the above commands application will be up and running on 8080, in case the server.port is not changed. <br/>

Following links can be accessed to upload and view the uploaded file enteries respectively <br/>

http://localhost:8080/ <br/>

http://localhost:8080/list?fileName=134071062038624.csv <br/>

fileName is a required parameter which is always the name of file uploaded in first step <br/>

Two data files with 100K records are provided in resources folder however these data files are also created when you execute the unit tests.<br/>

