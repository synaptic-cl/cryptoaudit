Documentation
=============

Installation
------------

* Install Java 1.7

`sudo apt-get install openjdk-7-jdk`

* Install Maven

`sudo apt-get install maven`



Git 
---
* Pull request
 
* [Merge changes of main repository] (http://stackoverflow.com/questions/867831/merge-changes-from-remote-github-repository-to-your-local-repository)

`git remote add synaptic-cryptoaudit https://github.com/synaptic-cl/cryptoaudit.git`  
`git pull synaptic-cryptoaudit master`  
`git push`  

IDE
---

We use [IntelliJ IDEA 14 by Jetbrains](https://www.jetbrains.com/idea/download/)

Compiling - Running Tests
--------------------------

* Using maven
 * In the root directory: `mvn test`
* Using IntelliJ
 * (Only the first time)
  * File > Project Structure > Global Libraries > Add > Scala SDK > Maven
 * Run the test files (right click on the file)

