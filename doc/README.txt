/*
 * Copyright 2009-14 Fraunhofer ISE
 *
 * This file is part of jSML.
 * For more information visit http://www.openmuc.org
 *
 * jSML is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * jSML is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with jSML.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

Authors: Stefan Feuerhahn & Manuel Buehrer

jSML is Java library implementing the Smart Message Language (SML).

SML is communication protocol for transmitting meter data for so
called smart meters.  SML is similar to XML with regard to its ability
to nest information tags within other tags.  But the standard way of
coding SML packets is in a more efficient binary format that is
similar but not equal to BER encoding. Also the specification comes in
a form that is similar to ASN.1 but not completely standards
compliant.

The jSML library can be used to easily construct SML messages, encode
them, and then send them. Just as easily received messages can be
decoded. jSML implements the SML Transport Layers necessary to
communicate over TCP/IP and serial connections. View the sample files
for more details.

-The library and its dependencies are found in the folders
 "build/libsdeps/" and "dependencies"
-Javadocs can be found in the build/javadoc folder

For the latest release of this software visit http://www.openmuc.org .

Using the examples:
-------------------
For an example on how to use jSML see the folder src/sample/java/ .
You can create Eclipse project files as explained here:
http://www.openmuc.org/index.php?id=28 and run the sample from within
Eclipse or you can compile and execute it in a terminal. To compile
and execute the sample on Linux use something like this:
go to the folder src/sample/java/
>javac *.java -cp "../../../build/libsdeps/jsml-<version>.jar:../../../dependencies/rxtxcomm_api-2.2pre2-11_bundle.jar"
>java -cp "../../../build/libsdeps/jsml-<version>.jar:../../../dependencies/rxtxcomm_api-2.2pre2-11_bundle.jar:./" SampleSerialRead
or run SampleSMLServer or SampleSMLClient instead of SampleSerialRead

To execute the server and client in ssl mode:
java -Djavax.net.ssl.keyStore=/path/to/serverKeystore -Djavax.net.ssl.keyStorePassword="storepass" -cp "../../../build/libsdeps/jsml-<version>.jar:../../../dependencies/rxtxcomm_api-2.2pre2-11_bundle.jar:./" SampleSMLServer <port> -s
java -cp "../../../build/libsdeps/jsml-<version>.jar:../../../dependencies/rxtxcomm_api-2.2pre2-11_bundle.jar:./" SampleSMLClient <port> -s


RXTX - Java library for serial communication
--------------------------------------------

If you use jSML over serial then it depends on the Java library RXTX
(http://rxtx.qbang.org). A binary version of this library can be found
in the dependencies folder. This library in turn depends on native
libraries that you have to install first. Many Linux distributions
offer the package librxtx-java to install these native libraries. For
instructions on how to install these on Windows visit
http://rxtx.qbang.org/wiki/index.php/Installation_for_Windows

RXTX (Copyright 1997-2004 by Trent Jarvi) is licensed under LGPL(v2 or
later). Beware there exist several different versions of
rxtxcomm-2.2pre2. The version of RXTX in the depencies folder was
taken from Debian (version 2.2pre2-11) and has many bug fixes compared
to the latest version on the website. This version was converted to a
bundle using bnd: "java -jar bnd/bnd-1.50.0.jar wrap
RXTXcomm-2.2pre2.jar". The source code of this version can be obtained
from the Debian rxtx src package. E.g. from here:
http://packages.ubuntu.com/source/saucy/rxtx

Develop jSML:
---------------

We use the Gradle build automation tool. The distribution contains a
fully functional gradle build file ("build.gradle"). Thus if you
changed code and want to rebuild a library you can do it easily with
Gradle. Also if you want to import our software into Eclipse you can
easily create Eclipse project files using Gradle. Just follow these
instructions (for the most up to date version of these instructions
visit http://www.openmuc.org/index.php?id=28):

Install Gradle: 

* Download Gradle from the website: www.gradle.org

* Set the PATH variable: e.g. in Linux add to your .bashrc: export
  PATH=$PATH:/home/user/path/to/gradle-version/bin

* Gradle will automatically download some dependencies from Maven
  Central. Thererfore if you are behind a proxy you should set the
  proxy options in the gradle.properties file as explained here:
  http://www.gradle.org/docs/current/userguide/build_environment.html.

* We use OpenJDK 6 to compile our projects. If you have several JDKs
  installed you may want to set the org.gradle.java.home property in
  the gradle.properties file.

* Setting "org.gradle.daemon=true" in gradle.properties will speed up
  Gradle

Create Eclipse project files using Gradle:

* with the command "gradle eclipse" you can generate Eclipse project
  files

* It is important to add the GRADLE_USER_HOME variable in Eclipse:
  Window->Preferences->Java->Build Path->Classpath Variable. Set it to
  the path of the .gradle folder in your home directory
  (e.g. /home/someuser/.gradle (Unix) or C:/Documents and
  Settings/someuser/.gradle (Windows))

Rebuild a library:

* After you have modified the code you can completely rebuild the code
  using the command "gradle clean build" This will also execute the
  junit tests.

* You can also assemble a new distribution tar file: the command
  "gradle clean tar" will build everything and put a new distribution
  file in the folder "build/distribution".

