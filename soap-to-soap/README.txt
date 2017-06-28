
============================================

You will need to compile this example:
  mvn clean install

To run the example type
  mvn exec:java

--------------------------------------------------


SoapUI request: (to http://localhost:8060/services/DemoServiceProxy)
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://www.talend.org/service/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:RouteResourceDemoOperationRequest>
         <in>hello32</in>
      </ser:RouteResourceDemoOperationRequest>
   </soapenv:Body>
</soapenv:Envelope>


with: 
        <camel.version>2.13.2</camel.version>
        <cxf.version>2.7.12</cxf.version>

it works as expected, after 60 sec, return HTTP 500 and Fault:
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <soap:Fault>
         <faultcode>soap:Server</faultcode>
         <faultstring>Read timed out</faultstring>
      </soap:Fault>
   </soap:Body>
</soap:Envelope>

with:
        <camel.version>2.17.6</camel.version>
        <cxf.version>3.1.11</cxf.version>

after 30 sec, return HTTP 200 and Body:
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <typ:DemoServiceOperationRequest xmlns:typ="http://www.talend.org/service/">
         <in>hello</in>
      </typ:DemoServiceOperationRequest>
   </soap:Body>
</soap:Envelope>
