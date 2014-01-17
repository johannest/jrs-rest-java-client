package com.jaspersoft.jasperserver.jaxrs.client.builder.reporting.inputcontrols;

import com.jaspersoft.jasperserver.dto.reports.inputcontrols.InputControlStateListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.SessionStorage;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

public class InputControlValuesAdapter {

    protected final SessionStorage sessionStorage;
    private final String reportUnitUri;
    protected MultivaluedMap<String, String> params;
    private String idsPathSegment;

    public InputControlValuesAdapter(SessionStorage sessionStorage, String reportUnitUri){
        params = new MultivaluedHashMap<String, String>();
        this.sessionStorage = sessionStorage;
        this.reportUnitUri = reportUnitUri;
    }

    public InputControlValuesAdapter(SessionStorage sessionStorage, String reportUnitUri, String idsPathSegment) {
        this(sessionStorage, reportUnitUri);
        this.idsPathSegment = idsPathSegment;
    }

    public InputControlValuesAdapter parameter(String name, String value){
        params.putSingle(name, value);
        return this;
    }

    public OperationResult<InputControlStateListWrapper> get(){
        JerseyRequestBuilder<InputControlStateListWrapper> builder =
                new JerseyRequestBuilder<InputControlStateListWrapper>(
                        sessionStorage, InputControlStateListWrapper.class);
        builder
                .setPath("reports")
                .setPath(reportUnitUri)
                .setPath("inputControls");
        if (idsPathSegment != null){
            builder.setPath(idsPathSegment);
        }
        builder.setPath("values");
        builder.addParams(params);
        return builder.get();
    }
    public OperationResult<InputControlStateListWrapper> update(){
        JerseyRequestBuilder<InputControlStateListWrapper> builder =
                new JerseyRequestBuilder<InputControlStateListWrapper>(
                        sessionStorage, InputControlStateListWrapper.class);
        builder
                .setPath("reports")
                .setPath(reportUnitUri)
                .setPath("inputControls");
        if (idsPathSegment != null){
            builder.setPath(idsPathSegment);
        }
        builder.setPath("values");
        builder.setContentType(MediaType.APPLICATION_XML);
        return builder.post(InputControlsUtils.toReportParameters(params));
    }


}
