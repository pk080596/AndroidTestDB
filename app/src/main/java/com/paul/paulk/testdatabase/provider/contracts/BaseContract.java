package com.paul.paulk.testdatabase.provider.contracts;

/**
 * Created by paulk on 12/14/2016.
 */

public class BaseContract {
    public static final String FORCE_DOWNLOAD = "force_dl";
    public static final String YES = "1";
    public static final String NO = "0";

    public static final class MATCHER_ID {
        public static final int EMPLOYEES = 10;
    }

    public static final class PATH {
        public static final String EMPLOYEE = EmployeesContract.TABLE_NAME;
    }
}
