package org.example.endtoendspringmvc_webapplication.Utility;

import jakarta.servlet.http.HttpServletRequest;

public class UrlUtil {

    public static String getAppUrl(HttpServletRequest request)
    {
        String schema=request.getScheme();
        String serverName=request.getServerName();
        int serverPort=request.getServerPort();

        String appUrl=schema    +"://"+serverName;

        if(serverPort !=80 && serverPort!=443)
            appUrl+=":"+serverPort;
        return appUrl;
    }
}
