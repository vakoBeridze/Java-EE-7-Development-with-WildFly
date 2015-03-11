package vako.wildflydevelopment.chapter4.util;

import org.jboss.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

/**
 * Created by vako on 3/11/15.
 */
public class LoggerProducer {

    @Produces
    public Logger produceLogger(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }
}
