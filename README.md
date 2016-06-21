# lastminute-exercise-prj
Test project for lastminute.com
##Some TODOs
* Add logging, logging configuration ()
* Document package info and at least public method
* Make receipt list a bean so that we can insert the decorator PrettyPrinter.
* Check double handling (check whether there are too many rounding)
* Implement main so that one can test with whatever json (s)he wants. Thanks to TRAVIS, one can check that it works
* Check better for jackson typed list serialization
* Some more final for immutable object/method parameter
* Refine with sonar again
* Improve pom info
* Bear in mind that in a real project you should think about how to add configuration capability depending on the environment.

##Some thoughts
* I looked for grossPrice and netPrice definition, it was counterintuitive (at least for me) but I should have been named it right.
* thinking forward, I added groupedPrinting. Hope you appreciate it.
* thinking by test is very powerful. It leaves off some anxiety and fear in refactoring, and it leads to a dramatical improvement of the code quality. Tested on animal. Ooops, on myself :)
* LoggingWithAspect has been used in two other projects. I'd like to find a simplier way to configure it, but it worth anyway. Not sure whether it works at the moment, since logback configuration is missing and properties, as well as th context configuration, have not been tested in this project.
* In a real project, it could be more modularized with more jar. For the scope of this POC, it'd be an overdesign.
