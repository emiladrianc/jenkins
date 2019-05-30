FROM jenkins/jenkins:lts

# USER root

COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt

# drop back to jenkins user
# USER jenkins

# install plugins
#RUN /usr/local/bin/install-plugins.sh 			/usr/share/jenkins/plugins.txt

# disable initial setup wizard
ENV JAVA_OPTS "-Djenkins.install.runSetupWizard=false"

#USER root

#USER jenkins