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

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Defines industry type..
 * 
 * @author lefeuvrem
 *
 */
public enum CreditStatusCode {
	PENDING 		("Pending"),
	GRANTED			("Granted"),
	DENIED   		("Denied"),
	UNDER_REVIEW 	("Under Review"),;

	private final String code;

    private static final Map<String, CreditStatusCode> lookup
    	= new HashMap<String, CreditStatusCode>();

    static {
    	for(CreditStatusCode c : EnumSet.allOf(CreditStatusCode.class))
         lookup.put(c.code, c);
    }

	private CreditStatusCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}

	public static CreditStatusCode lookUp(String code) {
		return lookup.get(code);
	}

}
