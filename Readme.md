## install JDK
download [oracle java](https://www.oracle.com/java/technologies/downloads/)
```sh
sudo apt install ~/Downloads/jdk-20_linux-x64_bin.deb
sudo update-alternatives --install /usr/bin/java java /usr/lib/jvm/jdk-20/bin/java 1
sudo update-alternatives --install /usr/bin/javac javac /usr/lib/jvm/jdk-20/bin/javac 1
sudo update-alternatives --install /usr/bin/jar jar /usr/lib/jvm/jdk-20/bin/jar 1
sudo update-alternatives --install /usr/bin/jar jar /usr/lib/jvm/jdk-20/bin/jarsigner 1
sudo update-alternatives --install /usr/bin/jar jar /usr/lib/jvm/jdk-20/bin/jlink 1
sudo update-alternatives --install /usr/bin/jar jar /usr/lib/jvm/jdk-20/bin/javadoc 1
sudo update-alternatives --config java
sudo update-alternatives --config javac
sudo update-alternatives --config jar
export JAVA_HOME=/usr/lib/jvm/jdk-20
```
## add java repo to profile
```shell
nano ~/.profile
```
Add this line to the end of the file
```shell
export JAVA_HOME=/usr/lib/jvm/jdk-20
```

# Install Maven
```sh
wget https://mirrors.estointernet.in/apache/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz
tar -xvf apache-maven-3.6.3-bin.tar.gz
mv apache-maven-3.6.3 /opt/
```
## Add maven repo to profile
```shell
M2_HOME='/opt/apache-maven-3.6.3'
PATH="$M2_HOME/bin:$PATH"
export PATH
```

## Read profile
```shell
source ~/.profile
```




**Build**
```shell
mvn -B package --file pom.xml -Pdev
```
**Run**
```shell
java -jar target/buana_technical_test-0.0.1-SNAPSHOT.jar
```

@author : achmadayas@gmail.com