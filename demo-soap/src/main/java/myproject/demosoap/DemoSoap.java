package myproject.demosoap;

import org.apache.camel.CamelContext;
import org.apache.camel.component.cxf.CxfEndpoint;
import org.apache.camel.main.Main;

public class DemoSoap extends org.apache.camel.builder.RouteBuilder {

    @Override
    public void configure() throws java.lang.Exception {
        final CamelContext camelContext = getContext();
        final org.apache.camel.impl.SimpleRegistry registry = new org.apache.camel.impl.SimpleRegistry();
        final org.apache.camel.impl.CompositeRegistry compositeRegistry = new org.apache.camel.impl.CompositeRegistry();
        compositeRegistry.addRegistry(camelContext.getRegistry());
        compositeRegistry.addRegistry(registry);
        ((org.apache.camel.impl.DefaultCamelContext) camelContext).setRegistry(compositeRegistry);

        org.apache.camel.component.cxf.CxfEndpoint endpoint_cCXF_1 = (CxfEndpoint) endpoint(
                "cxf://" + "http://localhost:8050/services/DemoService" + "?dataFormat=PAYLOAD" + "&loggingFeatureEnabled=true" + "&wsdlURL="
                        + "classpath:/config/DemoService_0.1.wsdl");

		from(endpoint_cCXF_1)
				.routeId("ResourceAsServiceCXF_cCXF_1")
				.delay(120000)
				.setBody()
				.simple("<ser:DemoServiceOperationResponse xmlns:ser=\"http://www.talend.org/service/\"><out>hi</out></ser:DemoServiceOperationResponse>");
    }

    public void run() throws java.lang.Exception {
        Main main = new org.apache.camel.main.Main();
        main.addRouteBuilder(this);
        main.run();
    }
    
    public static void main(String[] args) {
        try {
            new DemoSoap().run();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
