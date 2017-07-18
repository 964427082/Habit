package f3.nsu.com.habit.ui;

/**
 * Created by zhy on 2017/7/14.
 */

public class IntegralList {
    private String integralName;
    private String integralReason;
    private String integral;

    public IntegralList(){};
    public IntegralList(String integralName,String integralReason,String integral){
        this.integralName = integralName;
        this.integralReason = integralReason;
        this.integral = integral;
    }

    public String getIntegralName() {
        return integralName;
    }

    public void setIntegralName(String integralName) {
        this.integralName = integralName;
    }

    public String getIntegralReason() {
        return integralReason;
    }

    public void setIntegralReason(String integralReason) {
        this.integralReason = integralReason;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }
}
