# PillFill Java Client

![PillFill](logo.png)

This library aims to make building mHealth applications easier. It serves as the base of the <a href="https://play.google.com/store/apps/details?id=com.apothesource.pillfill.android">PillFill Android Application</a>.

We're focused on three areas:

* Patient Rx Information
* Drug & Interaction Information
* Doctor & Pharmacy Information

These are fairly broad groups and will probably mean breaking them into separate modules in the future. 

Most of the data services depend on pulling information from the _developer.pillfill.com_ server, so you'll need to get an <a href="https://pillfill.3scale.net/">API Key</a>. 
Swagger documentation on the REST services is available at the <a href="https://developer.pillfill.com">PillFill Developer Site</a>.


Patient Information
-------
###<a href="https://github.com/rammic/pf-java-client/blob/master/src/main/java/com/apothesource/pillfill/service/patient/PatientService.java">PatientService</a>

Each instance of this service represents a <a href="https://github.com/rammic/pf-java-client/blob/master/src/main/java/com/apothesource/pillfill/datamodel/PatientType.java">single patient</a>. It is responsible for managing the Rx lists for an individual
and syncing to/from the server. It ensures that sensitive information about the user is encrypted before it is synced to 
the server.

###<a href="https://github.com/rammic/pf-java-client/blob/master/src/main/java/com/apothesource/pillfill/service/patient/PatientServiceLocator.java">PatientServiceLocator</a>

PatientServiceLocator helps organize and mange instances of PatientService (assuming you're managing multiple patients).
 

Drug & Interaction Information
-----
###


License
-------

The MIT License (MIT)

Copyright (c) 2015, Apothesource, Inc.

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
