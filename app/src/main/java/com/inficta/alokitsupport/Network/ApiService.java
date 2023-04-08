package com.inficta.alokitsupport.Network;


import com.inficta.alokitsupport.Model.AttendanceReportModel;
import com.inficta.alokitsupport.Model.CompanyModel;
import com.inficta.alokitsupport.Model.CounterModel;
import com.inficta.alokitsupport.Model.ExpiredDateTimeModel;
import com.inficta.alokitsupport.Model.InOutStatusModel;
import com.inficta.alokitsupport.Model.InTimeModel;
import com.inficta.alokitsupport.Model.IssueModel;
import com.inficta.alokitsupport.Model.LeaveListModel;
import com.inficta.alokitsupport.Model.LeaveModel;
import com.inficta.alokitsupport.Model.LoginModel;
import com.inficta.alokitsupport.Model.OutTimeModel;
import com.inficta.alokitsupport.Model.PreviousLeaveModel;
import com.inficta.alokitsupport.Model.ProfileModel;
import com.inficta.alokitsupport.Model.SearchModel;
import com.inficta.alokitsupport.Model.StstusModel;
import com.inficta.alokitsupport.Model.SuportModel;
import com.inficta.alokitsupport.Model.UpdateModel;
import com.inficta.alokitsupport.Model.YearModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("Json_Controller/login_json")
    Call<LoginModel> login(@FieldMap Map<String, String> stringStringMap);

    @FormUrlEncoded
    @POST("Json_Controller/attendance_in_json")
    Call<InTimeModel> inTime(@FieldMap Map<String, String> stringStringMap);

    @FormUrlEncoded
    @POST("Json_Controller/attendance_out_json")
    Call<OutTimeModel> outTime(@FieldMap Map<String, String> stringStringMap);

    @FormUrlEncoded
    @POST("Json_Controller/service_issue_to_engineer_json")
    Call<IssueModel> issue(@FieldMap Map<String, String> stringStringMap);

    @FormUrlEncoded
    @POST("Json_Controller/in_out_status_response_json")
    Call<InOutStatusModel> inOutStatus(@FieldMap Map<String, String> stringStringMap);

    @FormUrlEncoded
    @POST("Json_Controller/issue_counter_json")
    Call<CounterModel> counter(@FieldMap Map<String, String> stringStringMap);

    @FormUrlEncoded
    @POST("Json_Controller/service_issue_udpate_by_engginer_json")
    Call<UpdateModel> update(@FieldMap Map<String, String> stringStringMap);

    @FormUrlEncoded
    @POST("Json_Controller/support_json")
    Call<SuportModel> support(@FieldMap Map<String, String> stringStringMap);

    @POST("Json_Controller/service_status_json")
    Call<StstusModel> getStatus();

    @POST("Json_Controller/company_master_json")
    Call<CompanyModel> getCompany();

    @POST("Json_Controller/leave_type_list_json")
    Call<LeaveListModel> getLeaveList();

    @FormUrlEncoded
    @POST("Json_Controller/engineer_profile_json")
    Call<ProfileModel> profile(@FieldMap Map<String, String> stringStringMap);

    @FormUrlEncoded
    @POST("Json_Controller/search_json")
    Call<SearchModel> search(@FieldMap Map<String, String> stringStringMap);

    @POST("Json_Controller/year_json")
    Call<YearModel> getYear();

    @FormUrlEncoded
    @POST("Json_Controller/ticket_cowndown_json")
    Call<ExpiredDateTimeModel> expiredDateTime(@FieldMap Map<String, String> stringStringMap);

    @FormUrlEncoded
    @POST("Json_Controller/create_leave_json")
    Call<LeaveModel> applyLeave(@FieldMap Map<String, String> stringStringMap);

    @FormUrlEncoded
    @POST("Json_Controller/attadance_list_json")
    Call<AttendanceReportModel> aReport(@FieldMap Map<String, String> stringStringMap);


    @FormUrlEncoded
    @POST("Json_Controller/leave_list_json")
    Call<PreviousLeaveModel> leaveList(@FieldMap Map<String, String> stringStringMap);

}

