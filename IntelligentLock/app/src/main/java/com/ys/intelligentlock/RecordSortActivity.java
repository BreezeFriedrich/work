package com.ys.intelligentlock;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ys.intelligentlock.AppManage.LockApplication;
import com.ys.intelligentlock.JsonData.RecordData;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by admin on 2017/2/20.
 */

public class RecordSortActivity extends Activity {

    private ImageButton back;
    private LinearLayout linearLayout_gatewayRecord;
    private LinearLayout linearLayout_lockRecord;
    private LinearLayout linearLayout_cardRecord;
    private RecyclerView sortRecordRecyclerView;
    private int type;
    private List<RecordData.RecordInfo> gatewayRecordSortList;
    private List<RecordData.RecordInfo> lockRecordSortList;
    private List<RecordData.RecordInfo> cardRecordSortList;

    private TextView textView_gatewayCodeSort;
    private TextView textView_gatewayUnlockAmount;
    private TextView textView_lockCodeSort;
    private TextView textView_lockUnlockAmount;
    private TextView textView_nameRecord;
    private TextView textView_cardNumbRecord;
    private TextView textView_cardUnlockAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sort_record);
        back=(ImageButton)findViewById(R.id.backOnSortRecord);
        linearLayout_gatewayRecord=(LinearLayout)findViewById(R.id.linearLayout_gatewayRecord);
        linearLayout_lockRecord=(LinearLayout)findViewById(R.id.linearLayout_lockRecord);
        linearLayout_cardRecord=(LinearLayout)findViewById(R.id.linearLayout_cardRecord);
        sortRecordRecyclerView=(RecyclerView)findViewById(R.id.sortRecordRecyclerView);

        textView_gatewayCodeSort=(TextView)findViewById(R.id.textView_gatewayCodeRecord2);
        textView_gatewayUnlockAmount=(TextView)findViewById(R.id.textView_gatewayUnlockAmount);
        textView_lockCodeSort=(TextView)findViewById(R.id.textView_lockCodeRecord2);
        textView_lockUnlockAmount=(TextView)findViewById(R.id.textView_lockUnlockAmount);
        textView_nameRecord=(TextView)findViewById(R.id.textView_nameRecord);
        textView_cardNumbRecord=(TextView)findViewById(R.id.textView_cardNumbRecord);
        textView_cardUnlockAmount=(TextView)findViewById(R.id.textView_cardUnlockAmount);

        ((LockApplication)getApplication()).addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent=getIntent();
        type=intent.getIntExtra("type",0);
        if(type==1){
            linearLayout_gatewayRecord.setVisibility(View.VISIBLE);
            linearLayout_lockRecord.setVisibility(View.GONE);
            linearLayout_cardRecord.setVisibility(View.GONE);
            gatewayRecordSortList=(List<RecordData.RecordInfo>)intent.getSerializableExtra("gatewayRecordSort");
            Collections.sort(gatewayRecordSortList, new Comparator<RecordData.RecordInfo>() {
                @Override
                public int compare(RecordData.RecordInfo o1, RecordData.RecordInfo o2) {
                    return o2.timetag.compareTo(o1.timetag);
                }
            });
            textView_gatewayCodeSort.setText(gatewayRecordSortList.get(0).gatewayCode);
            textView_gatewayUnlockAmount.setText(String.valueOf(gatewayRecordSortList.size()));
        }
        else if(type==2){
            linearLayout_gatewayRecord.setVisibility(View.GONE);
            linearLayout_lockRecord.setVisibility(View.VISIBLE);
            linearLayout_cardRecord.setVisibility(View.GONE);
            lockRecordSortList=(List<RecordData.RecordInfo>)intent.getSerializableExtra("lockRecordSort");
            Collections.sort(lockRecordSortList, new Comparator<RecordData.RecordInfo>() {
                @Override
                public int compare(RecordData.RecordInfo o1, RecordData.RecordInfo o2) {
                    return o2.timetag.compareTo(o1.timetag);
                }
            });
            textView_lockCodeSort.setText(lockRecordSortList.get(0).lockCode);
            textView_lockUnlockAmount.setText(String.valueOf(lockRecordSortList.size()));
        }
        else if(type==3){
            linearLayout_gatewayRecord.setVisibility(View.GONE);
            linearLayout_lockRecord.setVisibility(View.GONE);
            linearLayout_cardRecord.setVisibility(View.VISIBLE);
            cardRecordSortList=(List<RecordData.RecordInfo>)intent.getSerializableExtra("cardRecordSort");
            Collections.sort(cardRecordSortList, new Comparator<RecordData.RecordInfo>() {
                @Override
                public int compare(RecordData.RecordInfo o1, RecordData.RecordInfo o2) {
                    return o2.timetag.compareTo(o1.timetag);
                }
            });
            textView_nameRecord.setText(cardRecordSortList.get(0).cardInfo.name);
            textView_cardNumbRecord.setText(cardRecordSortList.get(0).cardInfo.cardNumb);
            textView_cardUnlockAmount.setText(String.valueOf(cardRecordSortList.size()));
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(RecordSortActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        sortRecordRecyclerView.setLayoutManager(linearLayoutManager);
        sortRecordRecyclerView.setAdapter(new RecordRecyclerAdapter());
        sortRecordRecyclerView.addItemDecoration(new MyItemDecoration());
    }


    class RecordRecyclerAdapter extends RecyclerView.Adapter<RecordRecyclerAdapter.MyViewHolder>{

        @Override
        public RecordRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(RecordSortActivity.this).inflate(R.layout.record_item,parent,false);
            MyViewHolder holder=new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            RecordData.RecordInfo recordInfo=null;
            if(type==1){
                recordInfo=gatewayRecordSortList.get(position);
            }
            else if(type==2){
                recordInfo=lockRecordSortList.get(position);
            }
            else if(type==3){
                recordInfo=cardRecordSortList.get(position);
            }

            holder.textView_gatewayCodeRecord.setText(recordInfo.gatewayCode);
            holder.textView_lockCodeRecord.setText(recordInfo.lockCode);
            if(recordInfo.openMode==1){
                holder.textView_openModeRecord.setText("开锁人姓名");
                holder.linearLayout_cardNumbRecord.setVisibility(View.VISIBLE);
                holder.textView_unlockNameOrPassword.setText(recordInfo.cardInfo.name);
                holder.textView_unlockCardNumb.setText(recordInfo.cardInfo.cardNumb);
            }
            else if(recordInfo.openMode==2){
                holder.textView_openModeRecord.setText("开锁密码");
                holder.linearLayout_cardNumbRecord.setVisibility(View.GONE);
                if(recordInfo.passwordInfo!=null){
                    holder.textView_unlockNameOrPassword.setText(recordInfo.passwordInfo.password);
                }
            }
            holder.textView_unlockTime.setText(timeShow(recordInfo.timetag));
        }

        @Override
        public int getItemCount() {
            if(type==1 && gatewayRecordSortList!=null){
                return gatewayRecordSortList.size();
            }
            else if(type==2 && lockRecordSortList!=null){
                return lockRecordSortList.size();
            }
            else if(type==3 && cardRecordSortList!=null){
                return cardRecordSortList.size();
            }
            return 0;
        }

        class MyViewHolder extends RecyclerView.ViewHolder{

            public TextView textView_gatewayCodeRecord;
            public TextView textView_lockCodeRecord;
            public TextView textView_openModeRecord;
            public TextView textView_unlockNameOrPassword;
            public LinearLayout linearLayout_cardNumbRecord;
            public TextView textView_unlockCardNumb;
            public TextView textView_unlockTime;

            public MyViewHolder(View itemView) {
                super(itemView);
                textView_gatewayCodeRecord=(TextView)itemView.findViewById(R.id.textView_gatewayCodeRecord);
                textView_lockCodeRecord=(TextView)itemView.findViewById(R.id.textView_lockCodeRecord);
                textView_openModeRecord=(TextView)itemView.findViewById(R.id.textView_openModeRecord);
                textView_unlockNameOrPassword=(TextView)itemView.findViewById(R.id.textView_unlockNameOrPassword);
                textView_unlockCardNumb=(TextView)itemView.findViewById(R.id.textView_unlockCardNumb);
                textView_unlockTime=(TextView)itemView.findViewById(R.id.textView_unlockTime);
                linearLayout_cardNumbRecord=(LinearLayout)itemView.findViewById(R.id.linearLayout_cardNumbRecord);
            }
        }
    }


    class MyItemDecoration extends RecyclerView.ItemDecoration {
        private Drawable mDrawable;
        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
            mDrawable=RecordSortActivity.this.getResources().getDrawable(R.drawable.segmentation);
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
            outRect.set(0,0,0,0);
        }

    }

    public String timeShow(String original){
        String consequence=original.substring(0,4)+"年"+original.substring(4,6)+"月"+original.substring(6,8)+"日"
                +original.substring(8,10)+"时"+original.substring(10,12)+"分"+original.substring(12,14)+"秒";
        return consequence;
    }

    public void sort(List<RecordData.RecordInfo> list){
        Collections.sort(list, new Comparator<RecordData.RecordInfo>() {
            @Override
            public int compare(RecordData.RecordInfo o1, RecordData.RecordInfo o2) {
                return o1.gatewayCode.compareTo(o2.gatewayCode);
            }
        });
    }
}
