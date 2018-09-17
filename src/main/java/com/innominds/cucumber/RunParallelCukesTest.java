package com.innominds.cucumber;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.innominds.team.frameworkengine.Constants;

import cucumber.api.cli.Main;


public class RunParallelCukesTest {
	public volatile static int thcount = 0;
	String classpath1 = "/D:/dump/repository/info/cukes/cucumber-java/1.2.0/cucumber-java-1.2.0.jar,/D:/dump/repository/info/cukes/cucumber-core/1.2.0/cucumber-core-1.2.0.jar,/D:/dump/repository/info/cukes/cucumber-html/0.2.3/cucumber-html-0.2.3.jar,/D:/dump/repository/info/cukes/cucumber-jvm-deps/1.0.3/cucumber-jvm-deps-1.0.3.jar,/D:/dump/repository/info/cukes/gherkin/2.12.2/gherkin-2.12.2.jar,/D:/dump/repository/info/cukes/cucumber-testng/1.2.0/cucumber-testng-1.2.0.jar,/D:/dump/repository/info/cukes/cucumber-junit/1.2.0/cucumber-junit-1.2.0.jar,/D:/dump/repository/junit/junit/4.11/junit-4.11.jar,/D:/dump/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar,/D:/dump/repository/xml-apis/xml-apis/1.0.b2/xml-apis-1.0.b2.jar,/D:/dump/repository/xerces/xercesImpl/2.8.0/xercesImpl-2.8.0.jar,/D:/dump/repository/org/seleniumhq/selenium/selenium-java/3.5.3/selenium-java-3.5.3.jar,/D:/dump/repository/org/seleniumhq/selenium/selenium-api/3.5.3/selenium-api-3.5.3.jar,/D:/dump/repository/org/seleniumhq/selenium/selenium-chrome-driver/3.5.3/selenium-chrome-driver-3.5.3.jar,/D:/dump/repository/org/seleniumhq/selenium/selenium-edge-driver/3.5.3/selenium-edge-driver-3.5.3.jar,/D:/dump/repository/org/seleniumhq/selenium/selenium-ie-driver/3.5.3/selenium-ie-driver-3.5.3.jar,/D:/dump/repository/org/seleniumhq/selenium/selenium-opera-driver/3.5.3/selenium-opera-driver-3.5.3.jar,/D:/dump/repository/org/seleniumhq/selenium/selenium-remote-driver/3.5.3/selenium-remote-driver-3.5.3.jar,/D:/dump/repository/org/seleniumhq/selenium/selenium-safari-driver/3.5.3/selenium-safari-driver-3.5.3.jar,/D:/dump/repository/org/seleniumhq/selenium/selenium-support/3.5.3/selenium-support-3.5.3.jar,/D:/dump/repository/cglib/cglib-nodep/3.2.4/cglib-nodep-3.2.4.jar,/D:/dump/repository/org/apache/commons/commons-exec/1.3/commons-exec-1.3.jar,/D:/dump/repository/commons-codec/commons-codec/1.10/commons-codec-1.10.jar,/D:/dump/repository/commons-logging/commons-logging/1.2/commons-logging-1.2.jar,/D:/dump/repository/org/w3c/css/sac/1.3/sac-1.3.jar,/D:/dump/repository/net/sourceforge/cssparser/cssparser/0.9.23/cssparser-0.9.23.jar,/D:/dump/repository/com/google/code/gson/gson/2.8.0/gson-2.8.0.jar,/D:/dump/repository/com/google/guava/guava/23.0/guava-23.0.jar,/D:/dump/repository/com/google/code/findbugs/jsr305/1.3.9/jsr305-1.3.9.jar,/D:/dump/repository/com/google/errorprone/error_prone_annotations/2.0.18/error_prone_annotations-2.0.18.jar,/D:/dump/repository/com/google/j2objc/j2objc-annotations/1.1/j2objc-annotations-1.1.jar,/D:/dump/repository/org/codehaus/mojo/animal-sniffer-annotations/1.14/animal-sniffer-annotations-1.14.jar,/D:/dump/repository/net/sourceforge/htmlunit/htmlunit/2.27/htmlunit-2.27.jar,/D:/dump/repository/net/sourceforge/htmlunit/htmlunit-core-js/2.27/htmlunit-core-js-2.27.jar,/D:/dump/repository/net/sourceforge/htmlunit/neko-htmlunit/2.27/neko-htmlunit-2.27.jar,/D:/dump/repository/org/apache/httpcomponents/httpcore/4.4.6/httpcore-4.4.6.jar,/D:/dump/repository/org/eclipse/jetty/jetty-client/9.4.5.v20170502/jetty-client-9.4.5.v20170502.jar,/D:/dump/repository/org/eclipse/jetty/jetty-http/9.4.5.v20170502/jetty-http-9.4.5.v20170502.jar,/D:/dump/repository/org/eclipse/jetty/jetty-io/9.4.5.v20170502/jetty-io-9.4.5.v20170502.jar,/D:/dump/repository/org/eclipse/jetty/jetty-util/9.4.5.v20170502/jetty-util-9.4.5.v20170502.jar,/D:/dump/repository/net/java/dev/jna/jna/4.1.0/jna-4.1.0.jar,/D:/dump/repository/net/java/dev/jna/jna-platform/4.1.0/jna-platform-4.1.0.jar,/D:/dump/repository/javax/servlet/javax.servlet-api/3.1.0/javax.servlet-api-3.1.0.jar,/D:/dump/repository/org/eclipse/jetty/websocket/websocket-api/9.4.5.v20170502/websocket-api-9.4.5.v20170502.jar,/D:/dump/repository/org/eclipse/jetty/websocket/websocket-client/9.4.5.v20170502/websocket-client-9.4.5.v20170502.jar,/D:/dump/repository/org/eclipse/jetty/websocket/websocket-common/9.4.5.v20170502/websocket-common-9.4.5.v20170502.jar,/D:/dump/repository/xalan/serializer/2.7.2/serializer-2.7.2.jar,/D:/dump/repository/xalan/xalan/2.7.2/xalan-2.7.2.jar,/D:/dump/repository/com/github/detro/ghostdriver/2.1.0/ghostdriver-2.1.0.jar,/D:/dump/repository/com/h2database/h2/1.4.196/h2-1.4.196.jar,/D:/dump/repository/org/seleniumhq/selenium/selenium-firefox-driver/3.5.3/selenium-firefox-driver-3.5.3.jar,/D:/dump/repository/org/seleniumhq/selenium/htmlunit-driver/2.24/htmlunit-driver-2.24.jar,/D:/dump/repository/org/seleniumhq/selenium/selenium-server/3.5.3/selenium-server-3.5.3.jar,/D:/dump/repository/com/beust/jcommander/1.48/jcommander-1.48.jar,/D:/dump/repository/net/jcip/jcip-annotations/1.0/jcip-annotations-1.0.jar,/D:/dump/repository/org/seleniumhq/selenium/jetty-repacked/9.4.5.v20170502/jetty-repacked-9.4.5.v20170502.jar,/D:/dump/repository/org/yaml/snakeyaml/1.15/snakeyaml-1.15.jar,/D:/dump/repository/com/codeborne/phantomjsdriver/1.4.1/phantomjsdriver-1.4.1.jar,/D:/dump/repository/io/appium/java-client/5.0.0-BETA9/java-client-5.0.0-BETA9.jar,/D:/dump/repository/cglib/cglib/3.2.5/cglib-3.2.5.jar,/D:/dump/repository/org/ow2/asm/asm/5.2/asm-5.2.jar,/D:/dump/repository/org/apache/ant/ant/1.9.6/ant-1.9.6.jar,/D:/dump/repository/org/apache/ant/ant-launcher/1.9.6/ant-launcher-1.9.6.jar,/D:/dump/repository/commons-validator/commons-validator/1.6/commons-validator-1.6.jar,/D:/dump/repository/commons-beanutils/commons-beanutils/1.9.2/commons-beanutils-1.9.2.jar,/D:/dump/repository/commons-digester/commons-digester/1.8.1/commons-digester-1.8.1.jar,/D:/dump/repository/commons-collections/commons-collections/3.2.2/commons-collections-3.2.2.jar,/D:/dump/repository/org/springframework/spring-context/4.3.8.RELEASE/spring-context-4.3.8.RELEASE.jar,/D:/dump/repository/org/springframework/spring-aop/4.3.8.RELEASE/spring-aop-4.3.8.RELEASE.jar,/D:/dump/repository/org/springframework/spring-beans/4.3.8.RELEASE/spring-beans-4.3.8.RELEASE.jar,/D:/dump/repository/org/springframework/spring-core/4.3.8.RELEASE/spring-core-4.3.8.RELEASE.jar,/D:/dump/repository/org/springframework/spring-expression/4.3.8.RELEASE/spring-expression-4.3.8.RELEASE.jar,/D:/dump/repository/org/aspectj/aspectjweaver/1.8.10/aspectjweaver-1.8.10.jar,/D:/dump/repository/org/openpnp/opencv/3.2.0-1/opencv-3.2.0-1.jar,/D:/dump/repository/org/apache/httpcomponents/httpclient/4.5.3/httpclient-4.5.3.jar,/D:/dump/repository/org/apache/logging/log4j/log4j-core/2.8.1/log4j-core-2.8.1.jar,/D:/dump/repository/org/apache/logging/log4j/log4j-api/2.8.1/log4j-api-2.8.1.jar,/D:/dump/repository/org/testng/testng/6.10/testng-6.10.jar,/D:/dump/repository/org/apache/poi/poi-ooxml/3.16/poi-ooxml-3.16.jar,/D:/dump/repository/org/apache/poi/poi-ooxml-schemas/3.16/poi-ooxml-schemas-3.16.jar,/D:/dump/repository/org/apache/xmlbeans/xmlbeans/2.6.0/xmlbeans-2.6.0.jar,/D:/dump/repository/stax/stax-api/1.0.1/stax-api-1.0.1.jar,/D:/dump/repository/com/github/virtuald/curvesapi/1.04/curvesapi-1.04.jar,/D:/dump/repository/org/apache/poi/poi/3.16/poi-3.16.jar,/D:/dump/repository/org/apache/commons/commons-collections4/4.1/commons-collections4-4.1.jar,/D:/dump/repository/com/sun/mail/javax.mail/1.5.6/javax.mail-1.5.6.jar,/D:/dump/repository/javax/activation/activation/1.1/activation-1.1.jar,/D:/dump/repository/org/apache/commons/commons-lang3/3.6/commons-lang3-3.6.jar,/D:/dump/repository/commons-io/commons-io/2.5/commons-io-2.5.jar,/D:/dump/repository/org/apache/httpcomponents/httpmime/4.5.3/httpmime-4.5.3.jar,/D:/dump/repository/org/jsoup/jsoup/1.10.3/jsoup-1.10.3.jar,/D:/dump/repository/com/relevantcodes/extentreports/2.41.2/extentreports-2.41.2.jar,/D:/dump/repository/org/xerial/sqlite-jdbc/3.8.11.1/sqlite-jdbc-3.8.11.1.jar,/D:/dump/repository/org/freemarker/freemarker/2.3.23/freemarker-2.3.23.jar,/D:/dump/repository/org/mongodb/mongodb-driver/3.0.4/mongodb-driver-3.0.4.jar,/D:/dump/repository/org/mongodb/bson/3.0.4/bson-3.0.4.jar,/D:/dump/repository/org/mongodb/mongodb-driver-core/3.0.4/mongodb-driver-core-3.0.4.jar,/D:/dump/repository/com/sun/jersey/jersey-bundle/1.19.1/jersey-bundle-1.19.1.jar,/D:/dump/repository/javax/ws/rs/jsr311-api/1.1.1/jsr311-api-1.1.1.jar,/D:/dump/repository/org/json/json/20170516/json-20170516.jar,/D:/dump/repository/com/sun/jersey/jersey-client/1.19.1/jersey-client-1.19.1.jar,/D:/dump/repository/com/sun/jersey/jersey-core/1.19.1/jersey-core-1.19.1.jar,/D:/dump/repository/com/googlecode/json-simple/json-simple/1.1.1/json-simple-1.1.1.jar,/D:/dump/repository/org/reflections/reflections/0.9.10/reflections-0.9.10.jar,/D:/dump/repository/org/javassist/javassist/3.19.0-GA/javassist-3.19.0-GA.jar,/D:/dump/repository/com/google/code/findbugs/annotations/2.0.1/annotations-2.0.1.jar,/D:/dump/repository/io/rest-assured/rest-assured/3.0.3/rest-assured-3.0.3.jar,/D:/dump/repository/org/codehaus/groovy/groovy/2.4.9/groovy-2.4.9.jar,/D:/dump/repository/org/codehaus/groovy/groovy-xml/2.4.9/groovy-xml-2.4.9.jar,/D:/dump/repository/org/hamcrest/hamcrest-library/1.3/hamcrest-library-1.3.jar,/D:/dump/repository/org/ccil/cowan/tagsoup/tagsoup/1.2.1/tagsoup-1.2.1.jar,/D:/dump/repository/io/rest-assured/json-schema-validator/3.0.3/json-schema-validator-3.0.3.jar,/D:/dump/repository/com/github/fge/json-schema-validator/2.2.6/json-schema-validator-2.2.6.jar,/D:/dump/repository/com/googlecode/libphonenumber/libphonenumber/6.2/libphonenumber-6.2.jar,/D:/dump/repository/com/github/fge/json-schema-core/1.2.5/json-schema-core-1.2.5.jar,/D:/dump/repository/org/mozilla/rhino/1.7R4/rhino-1.7R4.jar,/D:/dump/repository/com/github/fge/jackson-coreutils/1.8/jackson-coreutils-1.8.jar,/D:/dump/repository/com/github/fge/msg-simple/1.1/msg-simple-1.1.jar,/D:/dump/repository/com/github/fge/btf/1.2/btf-1.2.jar,/D:/dump/repository/com/fasterxml/jackson/core/jackson-databind/2.2.3/jackson-databind-2.2.3.jar,/D:/dump/repository/com/fasterxml/jackson/core/jackson-annotations/2.2.3/jackson-annotations-2.2.3.jar,/D:/dump/repository/com/fasterxml/jackson/core/jackson-core/2.2.3/jackson-core-2.2.3.jar,/D:/dump/repository/com/github/fge/uri-template/0.9/uri-template-0.9.jar,/D:/dump/repository/net/sf/jopt-simple/jopt-simple/4.6/jopt-simple-4.6.jar,/D:/dump/repository/joda-time/joda-time/2.3/joda-time-2.3.jar,/D:/dump/repository/javax/mail/mailapi/1.4.3/mailapi-1.4.3.jar,/D:/dump/repository/io/rest-assured/json-path/3.0.3/json-path-3.0.3.jar,/D:/dump/repository/org/codehaus/groovy/groovy-json/2.4.9/groovy-json-2.4.9.jar,/D:/dump/repository/io/rest-assured/rest-assured-common/3.0.3/rest-assured-common-3.0.3.jar,/D:/dump/repository/io/rest-assured/xml-path/3.0.3/xml-path-3.0.3.jar,/D:/dump/repository/org/hamcrest/java-hamcrest/2.0.0.0/java-hamcrest-2.0.0.0.jar,/D:/dump/repository/org/java-websocket/Java-WebSocket/1.3.4/Java-WebSocket-1.3.4.jar,/D:/streamlined/HarmonyCore/lib/MonteScreenRecorder.jar,/C:/app/eclipse/configuration/org.eclipse.osgi/392/0/.cp/,/C:/app/eclipse/configuration/org.eclipse.osgi/391/0/.cp/";
	
    private final Callable<Byte> runCuke = new Callable<Byte>() {
        @Override
        public Byte call() throws Exception {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            
            String reportname = System.getProperty("rpname"); //"custom-out11.txt";
            
            String tg = System.getProperty("user.dir")+File.separator + "target"+ File.separator;
            String[] args = {
                "--glue", "step_definitions",
                "--glue", "parallel",
                "--glue", "pageobjects",
                "--glue", "helpers",
                "--glue", "main",
                "--glue", "modules",
                "--glue", System.getProperty("user.dir")+File.separator + "target/classes"+ File.separator,
                "--glue", System.getProperty("user.dir")+File.separator + "target/test-classes"+ File.separator,
                Constants.PROJ_RESOURCE_PATH+"features/ifusion.feature",
                "--plugin", "step_definitions.CucumberCustomReporter:target/"+reportname,
                "--strict"
            };
            System.out.println("Inside callable before Main");
            return Main.run(args, classLoader);
        }
    };
    
//   
    
//    private final Callable<Byte> runCuke1 = new Callable<Byte>() {
//    	
//    	
//        @Override
//        public Byte call() throws Exception {
//            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//            
//            String tg = System.getProperty("user.dir")+File.separator + "target"+ File.separator;
//            String[] args = {
//                "--glue", "step_definitions",
//                "--glue", "parallel",
//                "--glue", "pageobjects",
//                "--glue", "helpers",
//                "--glue", "main",
//                "--glue", "modules",
//                "--glue", System.getProperty("user.dir")+File.separator + "target/classes"+ File.separator,
//                "--glue", System.getProperty("user.dir")+File.separator + "target/test-classes"+ File.separator,
//                Constants.PROJ_RESOURCE_PATH+"features/ifusion.feature",
//                "--plugin", "junit:target/cucumber1.xml",
//                "--strict"
//            };
//            System.out.println("Inside callable before Main");
//            return Main.run(args, classLoader);
//        }
//    };
    
    static {
    	System.out.println("Inside static");
    }

    @SuppressWarnings("deprecation")
	@Test
    public void tester() throws InterruptedException, ExecutionException {
    	URL[] urls = null;
    	try {
			InitBrowserOS.init();
			ClassLoader cl = ClassLoader.getSystemClassLoader();

	       urls = ((URLClassLoader)cl).getURLs();

	        for(URL url: urls){
	        	//System.out.println(url.getFile());
	        }
	        
	        String classpath = "";
			for (URL url: urls) {
				classpath = classpath + url.getFile()+";";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(2));
        //System.setProperty("browser1", "")
    	ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        //ScheduledExecutorService executorService1 = Executors.newScheduledThreadPool(1);
//        
//        
//    	//System.setProperty("browsersBDD", "chrome, firefox");
//    	Future<Byte> result1 = executorService.schedule(runCuke, 2, TimeUnit.SECONDS);
		//ListenableFuture<Byte> lf = service.submit(runCuke);
		// listenableFutures is a collection
		//ArrayList<ListenableFuture<Byte>> listenableFutures = new ArrayList<ListenableFuture<Byte>>();
//		listenableFutures.add(lf);
		thcount++;
        //Thread.sleep(120000);
//        ListenableFuture<Byte> lf1 = service.submit(runCuke);
//        listenableFutures.add(lf1);
//    	
//		ListenableFuture<List<Byte>> lfas = Futures.successfulAsList(listenableFutures);
//    	
//		Futures.addCallback(lfas, new FutureCallback<List<Byte>>() {
//	        @Override
//	        public void onSuccess(List<Byte> result) {
//	            System.out.println("@@ finished processing {} elements"+ Iterables.size(result));
//	            // do something with all the results
//	        }
//
//	        @Override
//	        public void onFailure(Throwable t) {
//	        	System.out.println("@@ failed because of :: {}"+ t);
//	        }
//	    });
		
		 // URL[] urls = ((URLClassLoader() Thread.currentThread().getContextClassLoader()).getURL();
//		String classpath = "";
//		for (URL url: urls) {
//			classpath = classpath + url.getFile()+";";
//		}
//        ProcessBuilder pb = new ProcessBuilder("C:/app/Java/jdk180131/bin/java.exe", "-cp", classpath, "cucumber.api.cli.Main", "D:/streamlined/HarmonyCore/src/test/resources/features/ifusion.feature --glue parallel --glue main --glue step_definitions --glue pageobjects --glue modules --glue helpers --plugin junit:target/cucumber11.xml");
//        
//      
		
		Future<Byte> result1 = executorService.schedule(runCuke, 2, TimeUnit.SECONDS);
		
        //Future<Byte> result2 = executorService1.schedule(runCuke1, 2, TimeUnit.SECONDS);
        assertEquals(result1.get().byteValue(), 0x0);
        //assertEquals(result2.get().byteValue(), 0x0);
    }
}