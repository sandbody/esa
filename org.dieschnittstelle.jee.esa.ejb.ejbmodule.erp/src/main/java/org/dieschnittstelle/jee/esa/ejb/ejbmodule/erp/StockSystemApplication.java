package org.dieschnittstelle.jee.esa.ejb.ejbmodule.erp;


import org.dieschnittstelle.jee.esa.ejb.ejbmodule.erp.crud.StockSystemRestBean;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by pentax on 29.01.17.
 */
//@ApplicationPath("/stocks")
public class StockSystemApplication {//{extends Application {

    public Set<Class<?>> getClasses() {
        return new HashSet(Arrays.asList(new Class[]{StockSystemRestBean.class}));
    }
}
