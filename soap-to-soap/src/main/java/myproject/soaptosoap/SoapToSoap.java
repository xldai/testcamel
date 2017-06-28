package myproject.soaptosoap;

import org.apache.camel.CamelContext;
import org.apache.camel.component.cxf.CxfEndpoint;
import org.apache.camel.component.cxf.CxfEndpointConfigurer;
import org.apache.camel.main.Main;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.frontend.AbstractWSDLBasedEndpointFactory;
import org.apache.cxf.transport.http.HTTPConduit;

public class SoapToSoap extends org.apache.camel.builder.RouteBuilder {

    @Override
    public void configure() throws java.lang.Exception {
        final CamelContext camelContext = getContext();
        final org.apache.camel.impl.SimpleRegistry registry = new org.apache.camel.impl.SimpleRegistry();
        final org.apache.camel.impl.CompositeRegistry compositeRegistry = new org.apache.camel.impl.CompositeRegistry();
        compositeRegistry.addRegistry(camelContext.getRegistry());
        compositeRegistry.addRegistry(registry);
        ((org.apache.camel.impl.DefaultCamelContext) camelContext).setRegistry(compositeRegistry);

        org.apache.camel.component.cxf.CxfEndpoint endpoint_cCXF_1 = (CxfEndpoint) endpoint(
                "cxf://" + "http://localhost:8060/services/DemoServiceProxy" 
                + "?dataFormat=PAYLOAD" + "&loggingFeatureEnabled=true" 
                + "&wsdlURL=" + "classpath:/config/DemoWsdl_0.1.wsdl");

        CxfEndpoint endpoint_cCXF_2 = (CxfEndpoint) endpoint("cxf://" + "http://localhost:8050/services/DemoService"
                + "?dataFormat=PAYLOAD" + "&wsdlURL=" + "classpath:/config/DemoService_0.1.wsdl"
                + "&serviceNameString={http://www.talend.org/service/}DemoService"
				+ "&endpointNameString={http://www.talend.org/service/}DemoServicePort"
                + "&defaultOperationNamespace=http://www.talend.org/service/"
                + "&defaultOperationName=DemoServiceOperation"
                + "&continuationTimeout=80000");

        endpoint_cCXF_2.setCxfEndpointConfigurer(new CxfEndpointConfigurer() {

            @Override
            public void configure(AbstractWSDLBasedEndpointFactory arg0) {

            }

            @Override
            public void configureClient(Client client) {
                HTTPConduit httpConduit = (HTTPConduit)client.getConduit();
                httpConduit.getClient().setConnectionTimeout(60000);
                httpConduit.getClient().setReceiveTimeout(60000);
                httpConduit.getClient().setAsyncExecuteTimeout(60000);
                //httpConduit.getClient().setAsyncExecuteTimeoutRejection(true);
            }

            @Override
            public void configureServer(Server server) {

            }

        });

		from(endpoint_cCXF_1)
				.routeId("cCXF_1")
				.removeHeaders("*")
				.setBody()
				.simple("<typ:DemoServiceOperationRequest xmlns:typ=\"http://www.talend.org/service/\"><in>hello</in></typ:DemoServiceOperationRequest>")
				.to(endpoint_cCXF_2);
    }

    public void run() throws java.lang.Exception {
        Main main = new org.apache.camel.main.Main();
        main.addRouteBuilder(this);
        main.run();
    }
    
    public static void main(String[] args) {
        try {
            new SoapToSoap().run();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
