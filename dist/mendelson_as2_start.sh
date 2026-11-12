#! /bin/sh
###############################################################################
#
#  Copyright (c) 2000-2026, mendelson-e-commerce GmbH  All Rights Reserved.
#
###############################################################################
#

CLASSPATH=as2.jar:jetty10/start.jar
export CLASSPATH

addToClasspath(){
    if [ -d $1 ]; then
	_DIR=$1
	export _DIR
	for jar in `ls $_DIR/*.jar $_DIR/*.zip 2>/dev/null`
	do
            CLASSPATH=$CLASSPATH:$jar
	done
    fi
}

addToClasspath "jlib"
addToClasspath "jlib/bc"
addToClasspath "jlib/caffeine"
addToClasspath "jlib/dark"
addToClasspath "jlib/db"
addToClasspath "jlib/help"
addToClasspath "jlib/httpclient"
addToClasspath "jlib/jackson"
addToClasspath "jlib/jpod"
addToClasspath "jlib/ldap"
addToClasspath "jlib/lucene"
addToClasspath "jlib/mina"
addToClasspath "jlib/oshi"
addToClasspath "jlib/svg"
addToClasspath "jetty10/lib"

######################################################################################
# 
# This finally starts the Java VM.
#
# Standard parameter
# -Xmx defines the max heap memory of this process, e.g. -Xmx4096M
# -Xms defines the startup allocated heap memory, e.g. -Xms92M
#
# To pass more than 9 parameter to the program please add additional parameter at the
# end of the list - the syntax depends on your used shell. Might be "${10} ${11} ..."
# or just "$10 $11 ..."
#
######################################################################################

#Define the path to the used java executable
#JAVAEXEC="/home/mendelson/as2/jdk-11.0.20+7/bin/java"
JAVAEXEC="java"
export JAVA_EXEC

#Setup the max heap for the process
MAX_HEAP="6144M"
export MAX_HEAP

$JAVAEXEC -Xmx$MAX_HEAP -Xms92M -classpath $CLASSPATH de.mendelson.comm.as2.AS2 $1 $2 $3 $3 $4 $5 $6 $7 $8 $9


