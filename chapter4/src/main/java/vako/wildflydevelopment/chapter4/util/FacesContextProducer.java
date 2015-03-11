package vako.wildflydevelopment.chapter4.util;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

/**
 * Created by vako on 3/11/15.
 */
public class FacesContextProducer {

    @Produces
    @RequestScoped
    public FacesContext produceFacesContext() {
        return FacesContext.getCurrentInstance();
    }

}
