package com.trivadis.camel.security.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.trivadis.camel.security.user.UserData;

/**
 * Special processor to return the processed parts of the Camel response.
 * 
 * @author Dominik Schadow, Trivadis GmbH
 * @version 1.0.0
 */
public class AccessDeniedProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        UserData body = exchange.getIn().getBody(UserData.class);
        
        exchange.getIn().setBody(body);
    }
}
