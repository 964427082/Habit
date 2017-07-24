package f3.nsu.com.habit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import f3.nsu.com.habit.R;
import f3.nsu.com.habit.RealmDataBase.DBControl;
import f3.nsu.com.habit.RealmDataBase.TaskData.RewardList;

import static f3.nsu.com.habit.RealmDataBase.DBControl.createRealm;

/**
 * Created by zhy on 2017/7/14.
 */

/**
 * 积分兑换列表的适配器
 */
public class IntegralAdapter extends BaseAdapter {
    private static final String TAG = "IntegralAdapter";
    private LinkedList<RewardList> integralData;
    private Context context;

    public IntegralAdapter(LinkedList<RewardList> integralData,Context context){
        this.integralData = integralData;
        this.context =context;
    }
    @Override
    public int getCount() {
        return integralData.size();
    }

    @Override
    public Object getItem(int position) {
        return integralData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.integral_listitem, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.integralName = (TextView) convertView.findViewById(R.id.IntegralName);
            viewHolder.integralReason = (TextView) convertView.findViewById(R.id.IntegralReason);
            viewHolder.integral = (TextView) convertView.findViewById(R.id.Integral);
            viewHolder.redeemBtn = (RelativeLayout) convertView.findViewById(R.id.redeem_btn);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.integralName.setText(integralData.get(position).getName());
        viewHolder.integralReason.setText(integralData.get(position).getWhy());
        viewHolder.integral.setText(integralData.get(position).getIntegral() + "");
        viewHolder.redeemBtn.setOnClickListener(new ChangeRewardList(integralData.get(position).getName()
                ,position,integralData.get(position).getIntegral()));
        return convertView;
    }
    class ViewHolder{
        private TextView integralName;
        private TextView integralReason;
        private TextView integral;
        private RelativeLayout redeemBtn;   //  积分按钮    可以兑换
    }


    public class ChangeRewardList implements View.OnClickListener{
        private String name;
        private int mPosition;
        private int integral;   //  需要多少积分

        public ChangeRewardList(String name,int mPosition,int integral){
            this.mPosition = mPosition;
            this.integral = integral;
            this.name = name;
        }
        @Override
        public void onClick(View v) {
            //累计每天积分汇总  总分数
            int integralAll = createRealm(context).showTotalModify();
            //积分兑换在册的所有分数
            int convertIntegral = DBControl.createRealm(context).showConvertIntegralAll();
            if((integralAll - convertIntegral) > integral){
                createRealm(context).convertIntegral(name,integral);//先记录下来
                createRealm(context).deleteRewardOneList(name);     //再删除
                List<RewardList> rewardLists = createRealm(context).showRewardList();
                integralData.clear();
                integralData.addAll(rewardLists);
                notifyDataSetChanged();
            }else
                Toast.makeText(context,"所需积分不够，无法兑换！",Toast.LENGTH_SHORT).show();
        }
    }
}
