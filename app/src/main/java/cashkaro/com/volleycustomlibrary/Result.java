
package cashkaro.com.volleycustomlibrary;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result implements Serializable
{

    @SerializedName("timetable_data")
    @Expose
    private List<TimetableDatum> timetableData = null;
    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("user_types")
    @Expose
    private String userTypes;
    private final static long serialVersionUID = -5232957133159533716L;

    public List<TimetableDatum> getTimetableData() {
        return timetableData;
    }

    public void setTimetableData(List<TimetableDatum> timetableData) {
        this.timetableData = timetableData;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserTypes() {
        return userTypes;
    }

    public void setUserTypes(String userTypes) {
        this.userTypes = userTypes;
    }

}
