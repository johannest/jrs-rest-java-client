/*
* Copyright (C) 2005 - 2014 Jaspersoft Corporation. All rights  reserved.
* http://www.jaspersoft.com.
*
* Unless you have purchased  a commercial license agreement from Jaspersoft,
* the following license terms  apply:
*
* This program is free software: you can redistribute it and/or  modify
* it under the terms of the GNU Affero General Public License  as
* published by the Free Software Foundation, either version 3 of  the
* License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU Affero  General Public License for more details.
*
* You should have received a copy of the GNU Affero General Public  License
* along with this program.&nbsp; If not, see <http://www.gnu.org/licenses/>.
*/

package com.jaspersoft.jasperserver.jaxrs.client.builder.reporting;

import com.jaspersoft.jasperserver.dto.reports.ReportExecutionStatusEntity;
import com.jaspersoft.jasperserver.jaxrs.client.core.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ExportExecutionDescriptor;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ExportExecutionOptions;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ReportExecutionDescriptor;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequestBuilder.buildRequest;

public class ReportExecutionRequestBuilder {

    private final SessionStorage sessionStorage;
    private final String requestId;

    public ReportExecutionRequestBuilder(SessionStorage sessionStorage, String requestId) {
        this.requestId = requestId;
        this.sessionStorage = sessionStorage;
    }

    public OperationResult<ReportExecutionStatusEntity> status() {
        return buildRequest(sessionStorage, ReportExecutionStatusEntity.class,
                new String[]{"/reportExecutions", requestId, "/status"}).get();
    }

    public OperationResult<ReportExecutionDescriptor> executionDetails() {
        return buildRequest(sessionStorage, ReportExecutionDescriptor.class, new String[]{"/reportExecutions", requestId})
                .get();
    }

    public OperationResult<ReportExecutionStatusEntity> cancelExecution() {
        ReportExecutionStatusEntity statusEntity = new ReportExecutionStatusEntity();
        statusEntity.setValue("cancelled");
        return buildRequest(sessionStorage, ReportExecutionStatusEntity.class, new String[]{"/reportExecutions", requestId, "/status"})
                .put(statusEntity);
    }

    public ExportExecutionRequestBuilder export(String exportOutput) {
        return new ExportExecutionRequestBuilder(sessionStorage, requestId, exportOutput);
    }

    public OperationResult<ExportExecutionDescriptor> runExport(ExportExecutionOptions exportExecutionOptions) {
        return buildRequest(sessionStorage, ExportExecutionDescriptor.class, new String[]{"/reportExecutions", requestId, "/exports"})
                .post(exportExecutionOptions);
    }

}
