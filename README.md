Grails23BeanAliasIssue
======================

an application and 3 plugins that show a usage of addAlias that worked in 2.2.4 but no longer works in 2.3.0

Summary:
The application uses a plugin architecture, where one of the plugins provides a base set of capability but the other plugins could extend the capability and alias the bean so the the base capability is seamlessly and invisibly updated if the another feature plugin is installed.  For example, the base plugin might provide a base set of capabilities, but a client specific plugin might override some of that behavior - but we want to leverage the base plugin service everywhere else.

In Grails 2.2.4 we were able to use springConfig.addAlias to alias a service in a client plugin to override the behavior of the base capability plugin.  In Grails 2.3 this does not work the same way and in particular the bean alias request seems to be ignored.  We can no longer get our client specific version aliased into the base plugin.

In the client specific plugin, in the 'doWithSpring' closure we would have code like:

springConfig.addAlias 'baseService', 'clientVersionBaseService'

The base plugin capability would still use baseService everywhere but the client was able to inject a custom version.  This springConfig.addAlias in the client plugin is no longer honored.


The project provided here runs either with Grails version 2.2.4 or Grails 2.3

When running running the example with grails 2.2.4 perform the following:

********  Grails 2.2.4 Setup and expected output

1) Go to:  MainApp/grails-app/conf/BuildConfig.groovy and make sure the section for 2.2.4 settings are active
2) Build the application with Grails 2.2.4
3) Go to the console application and execute the following:

import com.bls.*
  
  def welcomeService = ctx.getBean('welcomeService')
                                   
  welcomeService.sayGoodbye()

you should see:   See you later from LaterService

This is correct - the feature3plugin created a specialized version of the GoodbyeService that was ultimately injected into the WelcomeService.

********  Grails 2.3 Setup and expected output
1) Go to:  MainApp/grails-app/conf/BuildConfig.groovy and make sure the section for 2.3 settings are active
2) Build the application with Grails 2.3
3) Go to the console application and execute the following:

import com.bls.*
  
  def welcomeService = ctx.getBean('welcomeService')
                                   
  welcomeService.sayGoodbye()

you will see:   Goodbye from GoodbyeService

which is not what we would have expected.

We have run this example inside Intellij and outside Intellij on two different setups with the same results.
After upgrading to 2.3 we experienced 'odd' behavior and we created this small sample project to isolate the behavior so while not a perfect integration test - it is a very representative sample of our setup and the difference we are seeing with Grails 2.3



