## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

Modified to allow the server to accept 2 client to play game together but with the same card deck

## run code - class file
1. convert to class file
    ## for server
    ```
    javac --source-path src -d classes src/sg/edu/nus/iss/baccarat/server/* 
    ```
    ## for client 
    ```
    javac --source-path src -d classes src/sg/edu/nus/iss/baccarat/client/* 
    ```
    ## for test    	
    ```
    javac -cp lib/junit-4.13.2.jar;src -d classes src/sg/edu/nus/iss/baccarat/AppTest.java
    ```

2. run class file
    ## for server
    ```
    java -cp classes sg.edu.nus.iss.baccarat.server.serverApp 12345 4
    ```
    ## for client (as it use newFixedThreadPool = 2, there is a max of 2 client can connect but both still end up same user even login differently)
    ```
    java -cp classes sg.edu.nus.iss.baccarat.client.clientApp localhost:12345
    ```
    ## for test
    ```
    java -cp classes;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar org.junit.runner.JUnitCore sg.edu.nus.iss.baccarat.AppTest
    ```

4. valid command in client side:
    - login|username|fund(in int)
    - bet|amount(in int)
    - deal|P or deal|B 
    - end to disconeect from server
    - adjust|amount (in int) 

## jar file (in classes folder)
1. compile to jar file 
    ``` 
    jar -c -v -f myapp.jar -e sg.edu.nus.iss.baccarat . 
    ```
2. run server
   ``` 
   java -cp myapp.jar sg.edu.nus.iss.baccarat.server.serverApp 12345 4 
   ```
3. run client
    ``` 
    java -cp myapp.jar sg.edu.nus.iss.baccarat.client.clientApp localhost:12345 
    ```

## jar file (at same level as src)
1. compile to jar file     
    ```
    jar -c -v -f classes/myapp.jar -e sg.edu.nus.iss.baccarat -C classes .
    ```
2. run server
    ```
    java -cp classes/myapp.jar sg.edu.nus.iss.baccarat.server.serverApp 12345 4
    ```
3. run client
    ```
    java -cp classes/myapp.jar sg.edu.nus.iss.baccarat.client.clientApp localhost:12345
    ```

## how to use the .sh file
1. make sure the two file (junit-4.12.jar & hamcrest-core-1.3.jar) are exist in lib 
2. run ./compile in git bash to convert all java file to classes file (./compile.sh doesnt work if test file is included) 
3. update the run server and client .sh file and run by ./filename.sh also
4. 

## local server to check the javascript importing CSV
1. upload to vercel
or 
1. use: py -m http:server (at the index.html directory level)
2. open browser and go to : http://localhost:8000/index.html
3. browser might cache the file, use CTRL + F5 (for Window) to hard reload the file  

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies
- `classes`: the folder contain all classes file

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).
