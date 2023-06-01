package com.fyp.vishmi.skinlab.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RequestInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer eyJ4NXQiOiJOVEF4Wm1NeE5ETXlaRGczTVRVMVpHTTBNekV6T0RKaFpXSTRORE5sWkRVMU9HRmtOakZpTVEiLCJraWQiOiJOVEF4Wm1NeE5ETXlaRGczTVRVMVpHTTBNekV6T0RKaFpXSTRORE5sWkRVMU9HRmtOakZpTVEiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImF1ZCI6IjU2R3Jwem1wTzNLWnpyQTV4ZjN3ZkZDeklmQWEiLCJuYmYiOjE1ODEwNTk1NTUsImF6cCI6IjU2R3Jwem1wTzNLWnpyQTV4ZjN3ZkZDeklmQWEiLCJzY29wZSI6Im9wZW5pZCIsImlzcyI6Imh0dHBzOlwvXC9sb2NhbGhvc3Q6OTQ0M1wvb2F1dGgyXC90b2tlbiIsImV4cCI6MTU4MTA2MzE1NSwiaWF0IjoxNTgxMDU5NTU1LCJqdGkiOiJmODg4ZTQyOS1iMDA4LTQ5N2UtYjFhNS1mZTEyNjE1ODAyNzQifQ.GrRSkBBsN9olbWRf9eREF4jTOhsNIj6iFQucnhYK2c-mZ2vCKtCtYBtl8Aq1Jr--gXYihHEOGeuBmuE1xZ1K5NpzTNnkd1z7U4H6EZIjSH_KBqR3t7s7p3bpzp9RqAGARCtd9O9g17OAlA6E6xtOv6fcS-nWwxuL7wEbfxD40hmABDC3RFe-E85lA1WIpJ1iEG_EW97zzNBGleA7Q7p8zmdC7ehIB_BSHdmrujLclvW9e0njrX85Ywukwd_LQ8WIpbSILRjHNOxLyQzqNvOP8Zb2YGOwbKU3xKFegmQdply8PHMusggxCL5jxiTXxqALuuNTQJbr3Y3we75ZYwiACQ")
                .build();
        Response response = chain.proceed(request);
        return response;
    }
}
