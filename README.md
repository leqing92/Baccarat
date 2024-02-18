## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## run code
1. convert to class file
    ## for server
    javac --source-path src -d classes src/sg/edu/nus/iss/baccarat/server/* 
    ## for client 
    javac --source-path src -d classes src/sg/edu/nus/iss/baccarat/client/* 

2. run class file
    ## for server
    java -cp classes sg.edu.nus.iss.baccarat.server.serverApp 12345 4
    ## for client
    java -cp classes sg.edu.nus.iss.baccarat.client.clientApp localhost:12345

3. valid command in client side:
    - login|username|fund(in int)
    - bet|amount(in int)
    - deal|P or deal|B 
    - end to disconeect from server
    - adjust|amount (in int) 

## how to use the .sh file
1. make sure the two file (junit-4.12.jar & hamcrest-core-1.3.jar) are exist in lib
2. run ./compile in git bash to convert all java file to classes file
3. update the run server and client .sh file and run by ./filename.sh also
4. ./runTest.sh to run test file but cannot lerh 

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies
- `classes`: the folder contain all classes file

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).
