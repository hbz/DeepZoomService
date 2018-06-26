    sudo apt-get install libvips-tools
    cd /tmp
    git clone https://github.com/hbz/DeepZoomService
    wget http://ftp.fau.de/apache/tomcat/tomcat-9/v9.0.8/bin/apache-tomcat-9.0.8.tar.gz
    tar -xzf apache-tomcat-9.0.8.tar.gz
    mv apache-tomcat-9.0.8 ~/tomcat
    editor ~/tomcat/conf/server.xml
    ~/tomcat/bin/startup.sh
    cd /tmp/DeepZoomService
    editor src/main/resources/conf/deepzoomer.cfg
    mvn clean test war:war;rm -rf ~/tomcat/webapps/deepzoom*;cp target/deepzoom.war ~/tomcat/webapps/