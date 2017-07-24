package f3.nsu.com.habit.ui;

/**
 * Created by zhy on 2017/7/14.
 */

/**
 * 积分数据类型
 */
public class IntegralList {
    private String integralName;
    private String integralReason;
    private int integral;

    public IntegralList(){};
    public IntegralList(String integralName,String integralReason,int integral){
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

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }
}
