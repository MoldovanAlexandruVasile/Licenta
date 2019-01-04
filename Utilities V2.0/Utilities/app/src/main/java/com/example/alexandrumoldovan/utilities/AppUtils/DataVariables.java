package com.example.alexandrumoldovan.utilities.AppUtils;

public class DataVariables {
    
    //IP ADDRESS
    private static final String IP = "192.168.0.102";
    
    //ADMIN
    public static final String ADMIN_URL = "http://" + IP + ":8012/UtilitiesAPI/Admin/adminscontroller.php?view=all";
    public static final String INSERT_ADMIM_URL = "http://" + IP + ":8012/UtilitiesAPI/Admin/insertAdmin.php";
    public static final String UPDATE_ADMIN_NAME_URL = "http://" + IP + ":8012/UtilitiesAPI/Admin/updateAdminName.php";
    public static final String UPDATE_ADMIN_PASS_URL = "http://" + IP + ":8012/UtilitiesAPI/Admin/updateAdminPassword.php";

    //USER
    public static final String USER_URL = "http://" + IP + ":8012/UtilitiesAPI/User/userscontroller.php?view=all";
    public static final String INSERT_USER_URL = "http://" + IP + ":8012/UtilitiesAPI/User/insertUser.php";
    public static final String UPDATE_USER_URL = "http://" + IP + ":8012/UtilitiesAPI/User/updateUser.php";
    public static final String UPDATE_USER_NAME_URL = "http://" + IP + ":8012/UtilitiesAPI/User/updateUserName.php";
    public static final String UPDATE_USER_PASS_URL = "http://" + IP + ":8012/UtilitiesAPI/User/updateUserPassword.php";
    public static final String DELETE_USER_URL = "http://" + IP + ":8012/UtilitiesAPI/User/deleteUser.php";

    //ADDRESS
    public static final String ADDRESS_URL = "http://" + IP + ":8012/UtilitiesAPI/Address/addressescontroller.php?view=all";

    //EVENT
    public static final String EVENT_URL = "http://" + IP + ":8012/UtilitiesAPI/Event/eventscontroller.php?view=all";
    public static final String INSERT_EVENT_URL = "http://" + IP + ":8012/UtilitiesAPI/Event/insertEvent.php";
    public static final String UPDATE_EVENT_URL = "http://" + IP + ":8012/UtilitiesAPI/Event/updateEvent.php";
    public static final String DELETE_EVENT_URL = "http://" + IP + ":8012/UtilitiesAPI/Event/deleteEvent.php";

    //EVENT_USER
    public static final String EVENT_USER_URL = "http://" + IP + ":8012/UtilitiesAPI/EventUser/eventusercontroller.php?view=all";
    public static final String INSERT_EVENT_USER_URL = "http://" + IP + ":8012/UtilitiesAPI/EventUser/insertEventUser.php";
    public static final String UPDATE_EVENT_USER_URL = "http://" + IP + ":8012/UtilitiesAPI/EventUser/updateEventUser.php";
    public static final String DELETE_EVENT_USER_URL = "http://" + IP + ":8012/UtilitiesAPI/EventUser/deleteEventUser.php";
    public static final String DELETE_EVENT_USER_USER_URL = "http://" + IP + ":8012/UtilitiesAPI/EventUser/deleteEventUserByUser.php";

    //REPORT
    public static final String REPORT_URL = "http://" + IP + ":8012/UtilitiesAPI/Report/reportscontroller.php?view=all";
    public static final String INSERT_REPORT_URL = "http://" + IP + ":8012/UtilitiesAPI/Report/insertReport.php";
    public static final String UPDATE_QUANTITY_REPORT_URL = "http://" + IP + ":8012/UtilitiesAPI/Report/updateQuantityReport.php";
    public static final String DELETE_REPORT_BY_USER_URL = "http://" + IP + ":8012/UtilitiesAPI/Report/deleteReportByUser.php";

    //CONTRACT
    public static final String CONTRACT_URL = "http://" + IP + ":8012/UtilitiesAPI/Contracts/contractscontroller.php?view=all";
    public static final String INSERT_CONTRACT_URL = "http://" + IP + ":8012/UtilitiesAPI/Contracts/insertContract.php";
    public static final String UPDATE_CONTRACT_URL = "http://" + IP + ":8012/UtilitiesAPI/Contracts/updateContract.php";
    public static final String DELETE_CONTRACT_BY_USER = "http://" + IP + ":8012/UtilitiesAPI/Contracts/deleteContractByUser.php";
}
