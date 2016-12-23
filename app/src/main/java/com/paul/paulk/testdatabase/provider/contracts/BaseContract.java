package com.paul.paulk.testdatabase.provider.contracts;

/**
 * Created by paulk on 12/14/2016.
 */

public class BaseContract {
    public static final String FORCE_DOWNLOAD = "force_dl";
    public static final String RESET = "reset";
    public static final String YES = "1";
    public static final String NO = "0";

    public static final class MATCHER_ID {
        public static final int EMPLOYEES = 10;
        public static final int EMPLOYEES_DETAIL = 11;
        public static final int SALARIES = 20;
        public static final int TITLES = 30;
        public static final int SCHEMA = 40;
        public static final int WIPEALL = 99;
    }

    public static final class PATH {
        public static final String EMPLOYEE = EmployeesContract.TABLE_NAME;
        public static final String SALARY = SalariesContract.TABLE_NAME;
        public static final String TITLE = TitlesContract.TABLE_NAME;
        public static final String SCHEMA = "schema";
    }
}
