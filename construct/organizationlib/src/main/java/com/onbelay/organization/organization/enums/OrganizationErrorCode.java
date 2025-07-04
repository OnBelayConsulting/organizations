/*
 Copyright 2019, OnBelay Consulting Ltd.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.  
 */
package com.onbelay.organization.organization.enums;

import java.util.*;

public enum OrganizationErrorCode {

    SUCCESS                       	 ("0", "Success: transaction was successful"),
    MISSING_ORGANIZATION             ("ORG_OR-E0001", "Error: Missing organization."),
    MISSING_SHORT_NAME               ("ORG_OR-E0002", "Error: Missing short name."),
    MISSING_LEGAL_NAME               ("ORG_OR-E0003", "Error: Missing legal name."),

    MISSING_INDUSTRY_TYPE            ("ORG_OR-E0004", "Error: Missing Industry type."),
    MISSING_CREDIT_STATUS            ("ORG_OR-E0005", "Error: Missing Credit status."),

    SYSTEM_FAILURE                   ("E99999", "Error: Application error has occurred");

    private String code;
    private String description;

    private static final Map<String, OrganizationErrorCode> lookup
            = new HashMap<String, OrganizationErrorCode>();

    static {
        for (OrganizationErrorCode c : EnumSet.allOf(OrganizationErrorCode.class))
            lookup.put(c.code, c);
    }


    private OrganizationErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String toString() {
        return code + ":" + description;
    }

    public String getCode() {
        return code;
    }

    public static List<String> getTransactionCodes() {
        ArrayList<String> list = new ArrayList<String>();
        for (OrganizationErrorCode c : EnumSet.allOf(OrganizationErrorCode.class))
            list.add(c.getCode() + " : " + c.getDescription());
        return list;
    }

    public String getDescription() {
        return description;
    }

    public static OrganizationErrorCode lookUp(String code) {
        return lookup.get(code);
    }

}
