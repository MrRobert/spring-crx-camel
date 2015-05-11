package com.howtodoinjava.camel;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import com.howtodoinjava.services.MongoUserServices;

public class MyCxfCamelRoutes extends RouteBuilder {

	@Autowired
	MongoUserServices mongoUserServices;

	@Override
	public void configure() throws Exception {

		from("cxfrs:bean:mycxfWS?bindingStyle=SimpleConsumer").recipientList(
				simple("direct:cxf:${header.operationName}")).stopOnException();
		// .to("direct:cxf:getAllUser");

		from("direct:cxf:getAllUser").log("Request region=${header.region}")
				.choice().when(header("region").isEqualTo("vn"))
				.to("direct:cxf:getAllUser:vn")
				.when(header("region").isEqualTo("us"))
				.to("direct:cxf:getAllUser:us");

		from("direct:cxf:getAllUser:vn").process(new Processor() {
			public void process(Exchange exchange) throws Exception {
				Response response = Response.status(Status.OK)
						.type(MediaType.APPLICATION_JSON_TYPE)
						.entity("Sorry, you are not allowed!").build();
				exchange.getOut().setBody(response);
			}
		});

		from("direct:cxf:getAllUser:us").process(new Processor() {
			public void process(Exchange exchange) throws Exception {
				Response response = Response.status(Status.OK)
						.type(MediaType.APPLICATION_JSON_TYPE)
						.entity(mongoUserServices.findAllUser()).build();
				exchange.getOut().setBody(response);
			}
		});

	}

}
