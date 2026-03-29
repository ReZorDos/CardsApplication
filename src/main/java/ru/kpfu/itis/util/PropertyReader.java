package ru.kpfu.itis.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PropertyReader {

    public static String getProperties(String key) {
        String value = System.getenv(key);

        if (value == null || value.isEmpty()) {
            switch (key) {
                case "DB_URL":
                    value = "jdbc:postgresql://postgres:5432/card-application-oris";
                    break;
                case "DB_USER":
                    value = "card-application";
                    break;
                case "DB_PASSWORD":
                    value = "123456";
                    break;
                case "API.DOCUMENTS":
                    value = "http://185.221.160.131/api/documents";
                    break;
                case "API.USERS":
                    value = "http://104.128.137.40/api/v1/users/";
                    break;
                case "API.TRANSFERS":
                    value = "http://26.122.215.84:8083/api/v1/transfers/";
                    break;
            }
        }

        return value;
    }
}