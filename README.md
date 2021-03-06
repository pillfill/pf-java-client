# PillFill Java Client
[![Build Status](https://travis-ci.org/pillfill/pf-java-client.svg?branch=master)](https://travis-ci.org/rammic/pf-java-client)

This library aims to make building mHealth applications easier. It serves as the base of the <a href="https://play.google.com/store/apps/details?id=com.apothesource.pillfill.android">PillFill Android Application</a>.

![PillFill](logo.png)

We're focused on three areas:

* Medication & Interaction Information
* Doctor & Pharmacy Information
* Patient Rx Information (<a href="https://github.com/rammic/pf-java-client/wiki/Aggregating-Prescription-Information">Automatic Prescription Import</a>)

These are fairly broad groups and will probably mean breaking each into their separate module in the future.

Most of the data services depend on pulling information from the _developer.pillfill.com_ server, so you'll need to get an <a href="https://pillfill.3scale.net/">API Key</a>. 
Swagger documentation on the REST services is available at the <a href="https://developer.pillfill.com">PillFill Developer Site</a>.


Integration and Use
-------

The easiest way to get started is to simply add a dependency. If you're using maven:
```XML
<dependency>
  <groupId>com.apothesource.pillfill</groupId>
  <artifactId>pf-java-client</artifactId>
  <version>1.0.1</version>
</dependency>
```

Or if you're using gradle (e.g. w/ Android Studio):
```Groovy
dependency{
    compile 'com.apothesource.pillfill:pf-java-client:1.0.1'
}
```

Primary Features & Services
-------

Some of the most useful functions in this library are the following services (or proxies for the PF backend services) to provide information about drugs, providers, and patients. Most
will reach back to the PillFill Server as needed to retrieve relevant information. Be sure to set your apikey as either a system property (e.g. `export pfApiKey=[YOUR_API_KEY]`) or in
the _<a href="https://github.com/rammic/pf-java-client/blob/master/src/main/resources/credentials.properties">credentials.properties</a>_ file.

#### Medication & Interaction Services
##### <a href="https://github.com/rammic/pf-java-client/blob/master/src/main/java/com/apothesource/pillfill/service/drug/DrugService.java">DrugService</a>

DrugService provides methods to access information about medications, broken into Generic Drug
_<a href="https://github.com/rammic/pf-java-client/blob/master/src/main/java/com/apothesource/pillfill/datamodel/ndfrt/FullConcept.java">concepts</a>_
(e.g. Ibuprofen 200mg Pills) and _<a href="https://github.com/rammic/pf-java-client/blob/master/src/main/java/com/apothesource/pillfill/datamodel/spl/SplEntry.java">products</a>_
(e.g. Advil 200mg Pill 100 Pill Bottle).

##### <a href="https://github.com/rammic/pf-java-client/blob/master/src/main/java/com/apothesource/pillfill/service/drug/DrugService.java">DrugAlertService</a>

DrugAlertService offers methods to check for FDA Alerts (Drug <a href="http://www.fda.gov/Drugs/DrugSafety/DrugRecalls/default.htm">Recalls</a> and <a href="http://www.accessdata.fda.gov/scripts/drugshortages/default.cfm">Shortages</a>), <a href="http://www.epa.gov/ncct/dsstox/sdf_fdamdd.html">Maximum Recommended Therapeutic Dose</a> (MRTD- Overdose Monitoring), and
<a href="http://rxnav.nlm.nih.gov/InteractionAPIs.html">Drug/Drug Interaction Checking</a> (via NIH/RxNav).

#### Prescriber & Pharmacy Services
##### <a href="https://github.com/rammic/pf-java-client/blob/master/src/main/java/com/apothesource/pillfill/service/prescriber/PrescriberService.java">PrescriberService</a>

Use the prescriber service to search for <a href="https://nppes.cms.hhs.gov/NPPES/">prescriber by NPI</a> or by name and location. You can also
use this method to get detailed background information about a doctor including <a href="https://www.cms.gov/Medicare/Prescription-Drug-Coverage/PrescriptionDrugCovGenIn/PartDData.html">Medicare prescribing history</a> if available.

##### <a href="https://github.com/rammic/pf-java-client/blob/master/src/main/java/com/apothesource/pillfill/service/pharmacy/PharmacyService.java">PharmacyService</a>

Use the pharmacy service to retrieve detailed operational information (e.g. hours of operation, phone number) about a pharmacies included in a prescription's `pharmacyStoreId` field.

#### Patient Services
##### <a href="https://github.com/rammic/pf-java-client/blob/master/src/main/java/com/apothesource/pillfill/service/prescription/PrescriptionService.java">PrescriptionService</a>

Prescription service coordinates the task of extracting and managing prescriptions from pharmacy and insurance accounts. <a href="https://github.com/rammic/pf-java-client/wiki/Prescriptions-and-Drug-Identifiers">More information about data included in the prescription</a>.

##### <a href="https://github.com/rammic/pf-java-client/blob/master/src/main/java/com/apothesource/pillfill/service/patient/PatientService.java">PatientService</a>

Each instance of this service represents a <a href="https://github.com/rammic/pf-java-client/blob/master/src/main/java/com/apothesource/pillfill/datamodel/PatientType.java">single patient</a>. It is responsible for managing the Rx lists for an individual
and syncing to/from the server. It ensures that sensitive information about the user is encrypted before it is synced to the server.


Example Command Line Tool
-------

You can invoke a CLI to test the API by running the library as an executable Jar. First build a fatJar to avoid classpath issues:

`./gradlew fatJar`

This should build a Jar with the path of `build/libs/pf-java-client.jar`. Switch to directory (`cd build/libs`) to run commands from the command line. You can try any of the following:

Example: **Get product information about drug with NDC '00555078802'**:

  `java -jar pf-java-client.jar info apikey=[YOUR_API_KEY] ids=00555078802`

Example: **Get concept information about *National Drug File* ID 'N022111124'**:

  `java -jar pf-java-client.jar info apikey=[YOUR_API_KEY] ids=N022111124`

Example: **Export a prescription information for a given user**:

  `java -jar pf-java-client.jar extract apikey=[YOUR_API_KEY] username=testuser@example.com password=testPassword type=test outfile=rx-export.json`

  This will retrieve a set of example prescriptions. You can also use this to extract live information:

  `java -jar pf-java-client.jar extract apikey=[YOUR_API_KEY] username=[YOUR_USERNAME] password=[YOUR_PASSWORD] type=[ACCOUNT_TYPE] outfile=rx-export.json`

  The export process is described in more detail <a href="https://github.com/rammic/pf-java-client/blob/master/src/main/java/com/apothesource/pillfill/service/prescription/PrescriptionService.java">in the PrescriptionService documentation.</a>. A current list of supported account types is [available on the developer site](https://developer.pillfill.com/apis/?url=/service/api-docs?group=rx-aggregation-api-v1#!/rx-aggregation-service/getSupportedAccountTypes).


Build
-------

Be sure to set `JAVA7_HOME` & `JAVA8_HOME` environment variables (or in gradle.properties) if you want to build from scratch since this library depends on <a href="https://github.com/orfjackal/retrolambda">retrolambda</a> to
handle the conversion of lambda functions to Android-compatible Java 7 baseline.



License
-------

The MIT License (MIT)

Copyright (c) 2015, Apothesource, Inc.

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
