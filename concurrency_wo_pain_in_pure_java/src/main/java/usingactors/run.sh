mkdir classes
javac -d classes -classpath $AKKA_JARS ActorRun.java 
java -classpath $AKKA_JARS:classes ActorRun

