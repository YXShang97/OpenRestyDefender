package org.example;

import java.util.List;

public class GeoIPRule implements Rule {
    List<String> countries;

    public GeoIPRule(List<String> countries) {
        this.countries = countries;
    }

    @Override
    public boolean check(HttpRequest httpRequest) {
        // binary search to identify the country of the IP address
        String ip = httpRequest.getIp();
        Long ipLong = ipToLong(ip);

        int left = 0;
        int right = GeoIPDatabase.ipRanges.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (ipLong >= GeoIPDatabase.ipRanges[mid][0] && ipLong <= GeoIPDatabase.ipRanges[mid][GeoIpDAatabase.ipRanges[mid].length - 1]) {
                return true;
            } else if (ipLong < GeoIPDatabase.ipRanges[mid][0]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return false;
    }

    private Long ipToLong(String ip) {
        String[] parts = ip.split("\\.");
        long result = 0;
        for (int i = 0; i < 4; i++) {
            result += Long.parseLong(parts[i]) << (24 - (8 * i));
        }
        return result;
    }
}
