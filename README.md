# PillFill Java Client

![PillFill](logo.png)

[![Build Status](https://travis-ci.org/rammic/pf-java-client.svg?branch=master)](https://travis-ci.org/rammic/pf-java-client)

This library aims to make building mHealth applications easier. It serves as the base of the <a href="https://play.google.com/store/apps/details?id=com.apothesource.pillfill.android">PillFill Android Application</a>.

We're focused on three areas:

* Drug & Interaction Information
* Doctor & Pharmacy Information
* Patient Rx Information

These are fairly broad groups and will probably mean breaking each into their separate module in the future.

Most of the data services depend on pulling information from the _developer.pillfill.com_ server, so you'll need to get an <a href="https://pillfill.3scale.net/">API Key</a>. 
Swagger documentation on the REST services is available at the <a href="https://developer.pillfill.com">PillFill Developer Site</a>.


Command Line Tool
-------

You can invoke the CommandLine interface by running the library as an executable Jar:

Example: _Get product information about drug with NDC '00555078802'_:

  `java -jar pf-java-client-* info apikey=[YOUR_API_KEY] ids=00555078802`

Example: _Get concept information about *National Drug File* ID 'N022111124'_:

  `java -jar pf-java-client-* info apikey=[YOUR_API_KEY] ids=N022111124`

Example: _Export your prescription information from your pharmacy_:

  `java -jar pf-java-client-* extract apikey=[YOUR_API_KEY] username=[YOUR_CVS_LOGIN] password=[YOUR_CVS_PASSWORD] type=CVS outfile=rx-export.json`

(You can also invoke `com.apothesource.pillfill.CommandLine` directly if you'd prefer it that way)

Primary Features & Services
-------

Some of the most useful functions in this library are the following services (or proxies for the PF backend services) to provide information about drugs, providers, and patients. Most
will reach back to the PillFill Server as needed to retrieve relevant information. Be sure to set your apikey as either a system property (e.g. `export pfApiKey=[YOUR_API_KEY]`) or in
the _<a href="https://github.com/rammic/pf-java-client/blob/master/src/main/resources/credentials.properties">credentials.properties</a>_ file.

#### Drug & Interaction Services
##### <a href="https://github.com/rammic/pf-java-client/blob/master/src/main/java/com/apothesource/pillfill/service/drug/DrugService.java">DrugService</a>

DrugService provides methods to access information about medications, broken into Generic Drug
_<a href="https://github.com/rammic/pf-java-client/blob/master/src/main/java/com/apothesource/pillfill/datamodel/ndfrt/FullConcept.java">concepts</a>_
(e.g. Ibuprofen 200mg Pills) and _<a href="https://github.com/rammic/pf-java-client/blob/master/src/main/java/com/apothesource/pillfill/datamodel/spl/SplEntry.java">products</a>_
(e.g. Advil 200mg Pill 100 Pill Bottle).

##### <a href="https://github.com/rammic/pf-java-client/blob/master/src/main/java/com/apothesource/pillfill/service/drug/DrugService.java">DrugAlertService</a>

DrugAlertService offers methods to check for FDA Alerts (Drug <a href="http://www.fda.gov/Drugs/DrugSafety/DrugRecalls/default.htm">Recalls</a> and <a href="http://www.accessdata.fda.gov/scripts/drugshortages/default.cfm">Shortages</a>), <a href="http://www.epa.gov/ncct/dsstox/sdf_fdamdd.html">Maximum Recommended Therapeutic Dose</a> (MRTD- Overdose Monitoring), and
<a href="http://rxnav.nlm.nih.gov/InteractionAPIs.html">Drug/Drug Interaction Checking</a> (via NIH/RxNav).

#### Patient Services
##### <a href="https://github.com/rammic/pf-java-client/blob/master/src/main/java/com/apothesource/pillfill/service/patient/PatientService.java">PatientService</a>

Each instance of this service represents a <a href="https://github.com/rammic/pf-java-client/blob/master/src/main/java/com/apothesource/pillfill/datamodel/PatientType.java">single patient</a>. It is responsible for managing the Rx lists for an individual
and syncing to/from the server. It ensures that sensitive information about the user is encrypted before it is synced to
the server.

##### <a href="https://github.com/rammic/pf-java-client/blob/master/src/main/java/com/apothesource/pillfill/service/patient/PatientServiceLocator.java">PatientServiceLocator</a>

PatientServiceLocator helps organize and mange instances of PatientService (assuming you're managing multiple patients).


License
-------

The MIT License (MIT)

Copyright (c) 2015, Apothesource, Inc.

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
