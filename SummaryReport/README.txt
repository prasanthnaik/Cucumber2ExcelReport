Commandline Implementation:

Details:
exec:java ==> java/exec
-Dexec.classpathScope ==> test/main
-Dexec.mainClass ==> Path of Main Method (Example: com.project.Reporter.Reporting.Main)
-Dexec.args ==> External Arguments (Environment, FIle location e.t.c as String)

Psudo Syntax:
mvn exec:java -Dexec.classpathScope=test -Dexec.mainClass="com.project.Reporter.Reporting.Main" -Dexec.args="Your Report Path till overview-features.html"

Example:
mvn exec:java -Dexec.classpathScope=test -Dexec.mainClass="com.project.Reporter.Reporting.Main" -Dexec.args="C:\Report_Analyzer\Choice_API_Test_Results\cucumber-html-reports"