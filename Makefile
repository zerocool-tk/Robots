clean:
	mvn clean

compile: clean
	mvn compile

test:
	mvn test -Dtest="!*AcceptanceTest" -DfailIfNoTests=false

reference-test:
	java -jar libs/reference-server-0.2.2.jar & mvn test -Dtest="*AcceptanceTest#*1x1" -DfailIfNoTests=false
	pkill java
	sleep 3
	java -jar libs/reference-server-0.2.2.jar -s 2 & mvn test -Dtest="*AcceptanceTest#*2x2Empty" -DfailIfNoTests=false
	pkill java
	sleep 3
	java -jar libs/reference-server-0.2.2.jar -s 2 -o 1,1 & mvn test -Dtest="*AcceptanceTest#*2x2" -DfailIfNoTests=false
	pkill java

base-test:
	cd robot-worlds-server && mvn exec:java &
	mvn test -Dtest="*AcceptanceTest#*1x1" -DfailIfNoTests=false
	pkill java
	sleep 3
	cd robot-worlds-server && mvn exec:java -Dexec.args="-s 2" &
	mvn -Dtest="*AcceptanceTest#*2x2Empty" -DfailIfNoTests=false
	pkill java
	cd robot-worlds-server && mvn exec:java -Dexec.args="-s 2 -o 1,1" &
	mvn -Dtest="*AcceptanceTest#*Obstacle*" -DfailIfNoTest=false
	pkill java

package: clean compile

release: compile reference-test base-test
	cd robot-worlds-server && mvn validate -DaddMinor
	cd robot-worlds-server && mvn package && ./.release.sh

snapshot: compile reference-test base-test	
	cd robot-worlds-server && mvn validate -DaddPatch
	cd robot-worlds-server && mvn package && ./.snapshot.sh




