// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 12/22/2015 6:22:08 PM
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   USSDResponse.java

package org.smslib;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Referenced classes of package org.smslib:
//            USSDDatagram, InvalidMessageException, USSDSessionStatus, USSDDcs

public class USSDResponse extends USSDDatagram
{

    public USSDResponse()
    {
        rawResponse = null;
        sessionStatus = null;
    }

    public USSDResponse(String rawResp, String gtwId)
        throws InvalidMessageException
    {
        Matcher matcher = MSG_PATTERN.matcher(rawResp);
        if(!matcher.matches())
            throw new InvalidMessageException((new StringBuilder()).append("Not a well-formed +CUSD response: |").append(rawResp).append("|").toString());
        try
        {
            setGatewayId(gtwId);
            rawResponse = rawResp;
            sessionStatus = USSDSessionStatus.getByNumeric(Integer.valueOf(matcher.group(1)).intValue());
            if(matcher.groupCount() >= 2 && matcher.group(2) != null)
                setContent(matcher.group(2));
            if(matcher.groupCount() >= 3 && matcher.group(3) != null)
                setDcs(USSDDcs.getByNumeric(Integer.valueOf(matcher.group(3)).intValue()));
        }
        catch(Exception e)
        {
            throw new InvalidMessageException((new StringBuilder()).append("Session status: ").append(matcher.group(1)).append("; DCS: ").append(matcher.group(3)).toString());
        }
    }

    public String getRawResponse()
    {
        return rawResponse;
    }

    public void setRawResponse(String aRawResponse)
    {
        rawResponse = aRawResponse;
    }

    public USSDSessionStatus getSessionStatus()
    {
        return sessionStatus;
    }

    public void setUSSDSessionStatus(USSDSessionStatus aUSSDSessionStatus)
    {
        sessionStatus = aUSSDSessionStatus;
    }

    public String toString()
    {
        StringBuffer buf = new StringBuffer("Gateway: ");
        buf.append(getGatewayId());
        buf.append("\n");
        buf.append("Session status: ");
        buf.append(sessionStatus);
        buf.append("\n");
        buf.append("Data coding scheme: ");
        buf.append(getDcs() == null ? "Unspecified" : ((Object) (getDcs())));
        buf.append("\n");
        buf.append("Content: ");
        buf.append(getContent() == null ? "(EMPTY)" : getContent());
        return buf.toString();
    }

    private static final long serialVersionUID = 1L;
    private static final Pattern MSG_PATTERN = Pattern.compile("^\\+CUSD:\\s+(\\d)(?:,\\s*\"([^\"]*))?(?:\",\\s*(\\d+)\\s*)?\"?\r?$");
    private static final int STATUS_INDEX = 1;
    private static final int CONTENT_INDEX = 2;
    private static final int ENCODING_INDEX = 3;
    private String rawResponse;
    private USSDSessionStatus sessionStatus;

}