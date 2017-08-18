package com.ys.intelligentlock;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ys.intelligentlock.AppManage.LockApplication;
import com.ys.intelligentlock.JsonData.AbnormalDeviceData;

import java.util.List;
/**
 * Created by Administrator on 2016/12/1.
 */
public class AlertActivity extends Activity{
    private ImageView backOnAlert;
    private RecyclerView alertRecyclerView;

    public AbnormalDeviceData abnormalDeviceData;
    public List<AbnormalDeviceData.AbnormalDeviceInfo> abnormalDeviceLists;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert);

        backOnAlert=(ImageView)findViewById(R.id.backOnAlert);
        alertRecyclerView=(RecyclerView)findViewById(R.id.alertRecyclerView);
        ((LockApplication)getApplication()).addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        abnormalDeviceData=(AbnormalDeviceData)getIntent().getSerializableExtra("abnormalDeviceData");
        if(abnormalDeviceData==null){
            Toast.makeText(this,"当前无异常设备！",Toast.LENGTH_SHORT).show();
        }
        abnormalDeviceLists=abnormalDeviceData.abnormalDeviceLists;
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        alertRecyclerView.setLayoutManager(linearLayoutManager);

        backOnAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RecyclerView.Adapter adapter=new AlertActivity.abnormalDeviceRecyclerAdapter();
        alertRecyclerView.setAdapter(adapter);
        alertRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            private Drawable mDrawable;
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);
                mDrawable=AlertActivity.this.getResources().getDrawable(R.drawable.segmentation);
                final int left=parent.getLeft();
                final int right=parent.getRight();
                int childCount=parent.getChildCount();
                for(int i=0;i<childCount-1;i++){
                    View child=parent.getChildAt(i);
                    int top=child.getBottom();
                    int bottom=top+mDrawable.getIntrinsicHeight();
                    mDrawable.setBounds(left,top,right,bottom);
                    mDrawable.draw(c);
                }
            }
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                mDrawable=AlertActivity.this.getResources().getDrawable(R.drawable.segmentation);
                outRect.set(0,0,0,0);
            }
        });

    }

    class abnormalDeviceRecyclerAdapter extends RecyclerView.Adapter<AlertActivity.abnormalDeviceRecyclerAdapter.MyAbnormalDeviceViewHolder>{

        @Override
        public AlertActivity.abnormalDeviceRecyclerAdapter.MyAbnormalDeviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(AlertActivity.this).inflate(R.layout.alert_item,parent,false);
            AlertActivity.abnormalDeviceRecyclerAdapter.MyAbnormalDeviceViewHolder holder=new AlertActivity.abnormalDeviceRecyclerAdapter.MyAbnormalDeviceViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(AlertActivity.abnormalDeviceRecyclerAdapter.MyAbnormalDeviceViewHolder holder, int position) {
            holder.imageView_noPower.setVisibility(View.GONE);
            if(abnormalDeviceLists.get(position).type==1){
                holder.textView_abnormalDeviceType.setText("网关");
            }
            else if(abnormalDeviceLists.get(position).type==2){
                holder.textView_abnormalDeviceType.setText("门锁");
            }
            holder.textView_abnormalDeviceName.setText(abnormalDeviceLists.get(position).abnormalDeviceName);
            holder.textView_abnormalDeviceLocation.setText(abnormalDeviceLists.get(position).abnormalDeviceLocation);
            holder.textView_abnormalDeviceComment.setText(abnormalDeviceLists.get(position).abnormalDeviceComment);
            int status=abnormalDeviceLists.get(position).abnormalDeviceStatus;
            if(status==2){
                holder.textView_abnormalDeviceState.setText("门锁工作异常");
            }
            else if(status==3){
                holder.textView_abnormalDeviceState.setText("门锁失联");
            }
            else if(status==5){
                holder.textView_abnormalDeviceState.setText("网关工作异常");
            }
            else if(status==6){
                holder.textView_abnormalDeviceState.setText("网关失联");
            }
            holder.textView_abnormalDeviceState.setTextColor(Color.RED);
            if(abnormalDeviceLists.get(position).lockPower==1){
                holder.textView_abnormalDeviceState.setText("门锁电量不足,请立即更换门锁电池！");
                holder.imageView_noPower.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            if(abnormalDeviceLists!=null){
                return abnormalDeviceLists.size();
            }
            return 0;
        }

        class MyAbnormalDeviceViewHolder extends RecyclerView.ViewHolder{

            public ImageView abnormalDevice_image;
            public TextView textView_abnormalDeviceType;
            public TextView textView_abnormalDeviceName;
            public TextView textView_abnormalDeviceLocation;
            public TextView textView_abnormalDeviceComment;
            public TextView textView_abnormalDeviceState;
            public ImageView imageView_noPower;
            public LinearLayout linearLayout_abnormalDeviceItem;
            public MyAbnormalDeviceViewHolder(View itemView) {
                super(itemView);
                abnormalDevice_image=(ImageView)itemView.findViewById(R.id.imageView_abnormalDevice);
                textView_abnormalDeviceType=(TextView)itemView.findViewById(R.id.textView_abnormalDeviceType);
                textView_abnormalDeviceName=(TextView)itemView.findViewById(R.id.textView_abnormalDeviceName);
                textView_abnormalDeviceLocation=(TextView)itemView.findViewById(R.id.textView_abnormalDeviceLocation);
                textView_abnormalDeviceComment=(TextView)itemView.findViewById(R.id.textView_abnormalDeviceComment);
                textView_abnormalDeviceState=(TextView)itemView.findViewById(R.id.textView_abnormalDeviceState);
                imageView_noPower=(ImageView)itemView.findViewById(R.id.imageView_noPower);
                linearLayout_abnormalDeviceItem=(LinearLayout)itemView.findViewById(R.id.linearLayout_abnormalDeviceItem);
            }
        }
    }

}
