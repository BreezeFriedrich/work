package com.ys.intelligentlock;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ys.intelligentlock.AppManage.LockApplication;
import com.ys.intelligentlock.JsonData.RecordData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by admin on 2017/2/9.
 */

public class RecordDetailActivity extends Activity {

    private ImageButton backOnRecordDetail;
    private ImageButton filter;
    private TextView textView_statisticsStartTimeOnDetail;
    private TextView textView_statisticsEndTimeOnDetail;
    private PopupMenu popupMenu;
    private RecyclerView recordRecyclerView;
    private RecordRecyclerAdapter adapter;
    private RecordData recordDataTotal;
    private List<RecordData.RecordInfo> recordList;

    private List<List<RecordData.RecordInfo>> gatewayRecordList;
    private List<List<RecordData.RecordInfo>> lockRecordList;
    private List<List<RecordData.RecordInfo>> cardRecordList;

    private MyItemDecoration myItemDecoration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_record);
        backOnRecordDetail=(ImageButton)findViewById(R.id.backOnRecordDetail);
        textView_statisticsStartTimeOnDetail=(TextView)findViewById(R.id.textView_statisticsStartTimeOnDetail);
        textView_statisticsEndTimeOnDetail=(TextView)findViewById(R.id.textView_statisticsEndTimeOnDetail);
        filter=(ImageButton)findViewById(R.id.imageButton_filter);
        recordRecyclerView=(RecyclerView)findViewById(R.id.recordRecyclerView);
        myItemDecoration=new MyItemDecoration();
        recordRecyclerView.addItemDecoration(myItemDecoration);

        ((LockApplication)getApplication()).addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        backOnRecordDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        popupMenu=new PopupMenu(RecordDetailActivity.this,filter);
        popupMenu.inflate(R.menu.filter_menu);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu.show();
            }
        });
        textView_statisticsStartTimeOnDetail.setText(getIntent().getStringExtra("startTime"));
        textView_statisticsEndTimeOnDetail.setText(getIntent().getStringExtra("endTime"));
        recordDataTotal=(RecordData)getIntent().getSerializableExtra("recordDataTotal");
        recordList=recordDataTotal.recordList;
        listTimeSort();
        final LinearLayoutManager linearLayoutManager=new LinearLayoutManager(RecordDetailActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recordRecyclerView.setLayoutManager(linearLayoutManager);
        adapter=new RecordRecyclerAdapter();
        recordRecyclerView.setAdapter(adapter);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()){
                    case R.id.allRecord:
                        recordRecyclerView.setAdapter(adapter);
                        break;
                    case R.id.gatewayRecord:
                        gatewayFilter();
                        recordRecyclerView.setAdapter(new GatewayRecordRecyclerAdapter());
                        break;
                    case R.id.lockRecord:
                        lockFilter();
                        recordRecyclerView.setAdapter(new LockRecordRecyclerAdapter());
                        break;
                    case R.id.userRecord:
                        cardFilter();
                        recordRecyclerView.setAdapter(new CardRecordRecyclerAdapter());
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

    }

    class RecordRecyclerAdapter extends RecyclerView.Adapter<RecordRecyclerAdapter.MyViewHolder>{

        @Override
        public RecordRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(RecordDetailActivity.this).inflate(R.layout.record_item,parent,false);
            MyViewHolder holder=new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            RecordData.RecordInfo recordInfo=recordList.get(position);
            holder.textView_gatewayCodeRecord.setText(recordInfo.gatewayCode);
            holder.textView_lockCodeRecord.setText(recordInfo.lockCode);
            if(recordInfo==null){
                return;
            }
            if(recordInfo.openMode==1){
                holder.textView_openModeRecord.setText("开锁人姓名");
                holder.linearLayout_cardNumbRecord.setVisibility(View.VISIBLE);
                if(recordInfo.cardInfo==null){
                    return;
                }
                holder.textView_unlockNameOrPassword.setText(recordInfo.cardInfo.name);
                holder.textView_unlockCardNumb.setText(recordInfo.cardInfo.cardNumb);
            }
            else if(recordInfo.openMode==2){
                holder.textView_openModeRecord.setText("开锁密码");
                holder.linearLayout_cardNumbRecord.setVisibility(View.GONE);
                if(recordInfo.passwordInfo==null){
                    return;
                }
                holder.textView_unlockNameOrPassword.setText(recordInfo.passwordInfo.password);
            }
            holder.textView_unlockTime.setText(timeShow(recordInfo.timetag));
        }

        @Override
        public int getItemCount() {
            if(recordList!=null){
                return recordList.size();
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
            mDrawable=RecordDetailActivity.this.getResources().getDrawable(R.drawable.segmentation);
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

    public void listTimeSort(){
        Collections.sort(recordList, new Comparator<RecordData.RecordInfo>() {
            @Override
            public int compare(RecordData.RecordInfo o1, RecordData.RecordInfo o2) {
                return o2.timetag.compareTo(o1.timetag);
            }
        });
    }

    private void gatewayFilter(){
        gatewayRecordList=new ArrayList<>();
        List<String> gatewayCodeList=new ArrayList<>();
        for(int i=0;i<recordList.size();i++){
            gatewayCodeList.add(recordList.get(i).gatewayCode);
        }
        HashSet<String> set=new HashSet<>(gatewayCodeList);
        gatewayCodeList.clear();
        gatewayCodeList=new ArrayList<>(set);
        for(int i=0;i<gatewayCodeList.size();i++){
            List<RecordData.RecordInfo> sameGatewayList=new ArrayList<>();
            for(int j=0;j<recordList.size();j++){
                if(recordList.get(j).gatewayCode.equals(gatewayCodeList.get(i))){
                    sameGatewayList.add(recordList.get(j));
                }
            }
            gatewayRecordList.add(sameGatewayList);
        }
        Collections.sort(gatewayRecordList, new Comparator<List<RecordData.RecordInfo>>(){
            @Override
            public int compare(List<RecordData.RecordInfo> o1, List<RecordData.RecordInfo> o2) {
                return o1.get(0).gatewayCode.compareTo(o2.get(0).gatewayCode);
            }
        });
    }

    private void lockFilter(){
        lockRecordList=new ArrayList<>();
        List<String> lockCodeList=new ArrayList<>();
        for(int i=0;i<recordList.size();i++){
            lockCodeList.add(recordList.get(i).lockCode);
        }
        HashSet<String> set=new HashSet<>(lockCodeList);
        lockCodeList.clear();
        lockCodeList=new ArrayList<>(set);
        for(int i=0;i<lockCodeList.size();i++){
            List<RecordData.RecordInfo> sameLockList=new ArrayList<>();
            for(int j=0;j<recordList.size();j++){
                if(recordList.get(j).lockCode.equals(lockCodeList.get(i))){
                    sameLockList.add(recordList.get(j));
                }
            }
            lockRecordList.add(sameLockList);
        }
        Collections.sort(lockRecordList, new Comparator<List<RecordData.RecordInfo>>(){
            @Override
            public int compare(List<RecordData.RecordInfo> o1, List<RecordData.RecordInfo> o2) {
                return o1.get(0).lockCode.compareTo(o2.get(0).lockCode);
            }
        });
    }

    private void cardFilter(){
        cardRecordList=new ArrayList<>();
        List<String> cardList=new ArrayList<>();
        for(int i=0;i<recordList.size();i++){
            if(recordList.get(i).openMode==1){
                cardList.add(recordList.get(i).cardInfo.cardNumb);
            }
        }
        HashSet<String> set=new HashSet<>(cardList);
        cardList.clear();
        cardList=new ArrayList<>(set);
        for(int i=0;i<cardList.size();i++){
            List<RecordData.RecordInfo> sameCardList=new ArrayList<>();
            for(int j=0;j<recordList.size();j++){
                if(recordList.get(j).openMode==1){
                    if(recordList.get(j).cardInfo.cardNumb==null){
                        return ;
                    }
                    if(recordList.get(j).cardInfo.cardNumb.equals(cardList.get(i))){
                        sameCardList.add(recordList.get(j));
                    }
                }
            }
            cardRecordList.add(sameCardList);
        }
        Collections.sort(cardRecordList, new Comparator<List<RecordData.RecordInfo>>(){
            @Override
            public int compare(List<RecordData.RecordInfo> o1, List<RecordData.RecordInfo> o2) {
                return o1.get(0).lockCode.compareTo(o2.get(0).lockCode);
            }
        });
    }

    class GatewayRecordRecyclerAdapter extends RecyclerView.Adapter<GatewayRecordRecyclerAdapter.MyViewHolder>{

        @Override
        public GatewayRecordRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(RecordDetailActivity.this).inflate(R.layout.gateway_record_item,parent,false);
            MyViewHolder holder=new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.textView_gatewayCodeRecord.setText(gatewayRecordList.get(position).get(0).gatewayCode);
            holder.textView_gatewayUnlockAmount.setText(String.valueOf(gatewayRecordList.get(position).size()));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(RecordDetailActivity.this,RecordSortActivity.class);
                    intent.putExtra("type",1);
                    intent.putExtra("gatewayRecordSort",(Serializable)gatewayRecordList.get(position));
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            if(gatewayRecordList!=null){
                return gatewayRecordList.size();
            }
            return 0;
        }

        class MyViewHolder extends RecyclerView.ViewHolder{

            public TextView textView_gatewayCodeRecord;
            public TextView textView_gatewayUnlockAmount;

            public MyViewHolder(View itemView) {
                super(itemView);
                textView_gatewayCodeRecord=(TextView)itemView.findViewById(R.id.textView_gatewayCodeRecord2);
                textView_gatewayUnlockAmount=(TextView)itemView.findViewById(R.id.textView_gatewayUnlockAmount);
            }
        }
    }

    class LockRecordRecyclerAdapter extends RecyclerView.Adapter<LockRecordRecyclerAdapter.MyViewHolder>{

        @Override
        public LockRecordRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(RecordDetailActivity.this).inflate(R.layout.lock_record_item,parent,false);
            MyViewHolder holder=new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.textView_lockCodeRecord.setText(lockRecordList.get(position).get(0).lockCode);
            holder.textView_lockUnlockAmount.setText(String.valueOf(lockRecordList.get(position).size()));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(RecordDetailActivity.this,RecordSortActivity.class);
                    intent.putExtra("type",2);
                    intent.putExtra("lockRecordSort",(Serializable)lockRecordList.get(position));
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            if(lockRecordList!=null){
                return lockRecordList.size();
            }
            return 0;
        }

        class MyViewHolder extends RecyclerView.ViewHolder{

            public TextView textView_lockCodeRecord;
            public TextView textView_lockUnlockAmount;

            public MyViewHolder(View itemView) {
                super(itemView);
                textView_lockCodeRecord=(TextView)itemView.findViewById(R.id.textView_lockCodeRecord2);
                textView_lockUnlockAmount=(TextView)itemView.findViewById(R.id.textView_lockUnlockAmount);
            }
        }
    }

    class CardRecordRecyclerAdapter extends RecyclerView.Adapter<CardRecordRecyclerAdapter.MyViewHolder>{

        @Override
        public CardRecordRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(RecordDetailActivity.this).inflate(R.layout.card_record_item,parent,false);
            MyViewHolder holder=new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.textView_nameRecord.setText(cardRecordList.get(position).get(0).cardInfo.name);
            holder.textView_cardNumbRecord.setText(cardRecordList.get(position).get(0).cardInfo.cardNumb);
            holder.textView_cardUnlockAmount.setText(String.valueOf(cardRecordList.get(position).size()));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(RecordDetailActivity.this,RecordSortActivity.class);
                    intent.putExtra("type",3);
                    intent.putExtra("cardRecordSort",(Serializable)cardRecordList.get(position));
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            if(cardRecordList!=null){
                return cardRecordList.size();
            }
            return 0;
        }

        class MyViewHolder extends RecyclerView.ViewHolder{

            public TextView textView_nameRecord;
            public TextView textView_cardNumbRecord;
            public TextView textView_cardUnlockAmount;

            public MyViewHolder(View itemView) {
                super(itemView);
                textView_nameRecord=(TextView)itemView.findViewById(R.id.textView_nameRecord);
                textView_cardNumbRecord=(TextView)itemView.findViewById(R.id.textView_cardNumbRecord);
                textView_cardUnlockAmount=(TextView)itemView.findViewById(R.id.textView_cardUnlockAmount);
            }
        }
    }
}
