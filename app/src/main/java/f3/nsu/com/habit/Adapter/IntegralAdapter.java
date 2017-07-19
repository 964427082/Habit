package f3.nsu.com.habit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.LinkedList;

import f3.nsu.com.habit.R;
import f3.nsu.com.habit.ui.IntegralList;

/**
 * Created by zhy on 2017/7/14.
 */

public class IntegralAdapter extends BaseAdapter {
    private static final String TAG = "IntegralAdapter";
    private LinkedList<IntegralList> integralData;
    private Context context;

    public IntegralAdapter(LinkedList<IntegralList> integralData,Context context){
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.integral_listitem, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.integralName = (TextView) convertView.findViewById(R.id.IntegralName);
            viewHolder.integralReason = (TextView) convertView.findViewById(R.id.IntegralReason);
            viewHolder.integral = (TextView) convertView.findViewById(R.id.Integral);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.integralName.setText(integralData.get(position).getIntegralName());
        viewHolder.integralReason.setText(integralData.get(position).getIntegralReason());
        viewHolder.integral.setText(integralData.get(position).getIntegral());
        return convertView;
    }
    class ViewHolder{
        private TextView integralName;
        private TextView integralReason;
        private TextView integral;
    }
}
