package org.tinygroup.flowplugin;

import org.tinygroup.event.Parameter;
import org.tinygroup.event.ServiceInfo;
import org.tinygroup.flow.config.Flow;

import java.util.List;

/**
 * Created by luoguo on 14-4-9.
 */
public class FlowServiceInfo implements ServiceInfo {
    private String serviceId;
    private List<Parameter> parameters;
    private List<Parameter> results;

    public FlowServiceInfo(Flow flow) {
        serviceId = flow.getId();
        parameters = flow.getParameters();
        results = flow.getOutputParameters();
    }

    public String getServiceId() {
        return serviceId;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public List<Parameter> getResults() {
        return results;
    }

    public int compareTo(ServiceInfo o) {
        return o.getServiceId().compareTo(serviceId);
    }

}
