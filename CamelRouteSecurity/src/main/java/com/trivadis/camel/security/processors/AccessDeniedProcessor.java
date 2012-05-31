package com.trivadis.camel.security.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.trivadis.camel.security.user.UserData;

/**
 * Special processor to return the processed parts of the Camel response before the exception occurred. This processor
 * is configured to only catch <b>AccessDeniedException</b>, but can principally be used to catch other exceptions as
 * well.
 * 
 * @author Dominik Schadow, Trivadis GmbH
 * @version 1.0.0
 */
public class AccessDeniedProcessor implements Processor {
    Logger logger = LoggerFactory.getLogger(AccessDeniedProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        UserData body = exchange.getIn().getBody(UserData.class);
        Exception ex = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);

        logger.warn("AccessDeniedException caught", ex);
        logger.warn("Original body " + body);

        exchange.getIn().setBody(body);
    }
}