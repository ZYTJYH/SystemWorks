package zytjyh.com.hellocharts;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private Button goCompare;
    private Button goOperation;
    private Button getAllResult;
    private Button getOperationData;
    private SeekBar storageSeekBar;
    private Button startTest;
    private TextView storageSeekBarTip;
    private int numOfOperationKind=32;
    private int numOfOperation=3;
    private int numOfStorage=4;
    private ShowAdapter showAdapter;
    private ListView showListView;
    private ProgressDialog progressDialog;
    private final int ProcessDialogCancel=1;
    private Handler handler=new Handler();

    private ArrayList<Integer> everyOperationNumArray=new ArrayList<Integer>(32);
    ArrayList<String> FIFOArray=new ArrayList<String>();
    ArrayList<String> LRUArray=new ArrayList<String>();
    ArrayList<String> LFUArray=new ArrayList<String>();
    ArrayList<String> OTPArray=new ArrayList<String>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getOperationData=(Button)findViewById(R.id.main_getOperationsData);
        goCompare=(Button)findViewById(R.id.main_goCompare);
        goOperation=(Button)findViewById(R.id.main_goOperation);
        storageSeekBar=(SeekBar)findViewById(R.id.main_storage);
        storageSeekBarTip=(TextView)findViewById(R.id.main_storageTip);
        startTest=(Button)findViewById(R.id.main_start);
        showListView=(ListView)findViewById(R.id.main_showListView);
        startTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(MainActivity.this);
                // 设置进度条风格，风格为圆形，旋转的
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                // 设置ProgressDialog 标题
                progressDialog.setTitle("生成数据中");
                // 设置ProgressDialog 提示信息
                progressDialog.setMessage("请稍等");
                // 设置ProgressDialog 标题图标
                // 设置ProgressDialog 的进度条是否不明确
                progressDialog.setIndeterminate(false);
                // 设置ProgressDialog 是否可以按退回按键取消
                progressDialog.setCancelable(false);
                // 让ProgressDialog显示
                progressDialog.show();
                ArrayList<ShowItem> showItems=new ArrayList<ShowItem>();
                for(int i=0;i<10;i++)
                {
                    showItems.add(new ShowItem("add",false,"1",new ArrayList<String>()));
                }
                ShowAdapter showAdapter=new ShowAdapter(MainActivity.this,R.layout.showlist_item,showItems);
                showListView.setAdapter(showAdapter);
                showAdapter.notifyDataSetChanged();
                FormBody.Builder builder = new FormBody.Builder();
                builder.add("numOfOperation","100");
                RequestBody formBody = builder.build();
                Request request = new Request.Builder()
                        .url("http://47.97.181.151:8888")
                        .post(formBody)
                        .build();
                OkHttpClient client = new OkHttpClient();
                Log.e("s","test1");
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(okhttp3.Call call, IOException e) {
                        Log.e("sss","onFailure");
                        final MyRunnable myRunnable = new MyRunnable();//定义MyRunnable的对象；
                        new Thread() {
                            @Override
                            public void run() {
                                handler.post(myRunnable);//调用Handler.post方法；
                            }
                        }.start();
                    }

                    @Override
                    public void onResponse(okhttp3.Call call, Response response) throws IOException {
                        Log.e("s","test2");
                        if(response.isSuccessful()){//回调的方法执行在子线程。
                            Log.e("kwwl","获取数据成功了");
                            Log.e("kwwl","response.code()=="+response.code());
                            Log.e("kwwl","response.body().string()=="+response.body().string());
                            final MyRunnable myRunnable = new MyRunnable();//定义MyRunnable的对象；
                            new Thread() {
                                @Override
                                public void run() {
                                    handler.post(myRunnable);//调用Handler.post方法；
                                }
                            }.start();
                        }
                    }
                });

            }
        });
        storageSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                storageSeekBarTip.setText("内存块数"+(i+4));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        for(int i=0;i<32;i++) {
            everyOperationNumArray.add(0);
        }
        getAllResult=(Button)findViewById(R.id.main_getAllResult);
        getOperationData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataAsync();
                for(int i=0;i<32;i++) {
                    everyOperationNumArray.set(i,i);
                }
            }
        });
        getAllResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FIFOArray.add("92.0");
                FIFOArray.add("15.0");
                FIFOArray.add("32.0");

                getResultAsync("ALL",4,1000);
            }
        });
        goCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,CompareActivity.class);
                intent.putExtra("numOfOperation",numOfOperation);
                intent.putStringArrayListExtra("FIFO",FIFOArray);
                intent.putStringArrayListExtra("LRU",LRUArray);
                intent.putStringArrayListExtra("LFU",LFUArray);
                intent.putStringArrayListExtra("OTP",OTPArray);
                startActivity(intent);
            }
        });
        goOperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,OperationActivity.class);
                intent.putExtra("numOfOperationKind",numOfOperationKind);
                intent.putIntegerArrayListExtra("operationArray",everyOperationNumArray);
                for(int i=0;i<32;i++)
                {
                    Log.e("ss",""+everyOperationNumArray.get(i));
                }
                startActivity(intent);
            }
        });
    }
    private void getDataAsync() {
        progressDialog = new ProgressDialog(MainActivity.this);
        // 设置进度条风格，风格为圆形，旋转的
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // 设置ProgressDialog 标题
        progressDialog.setTitle("生成数据中");
        // 设置ProgressDialog 提示信息
        progressDialog.setMessage("请稍等");
        // 设置ProgressDialog 标题图标
        // 设置ProgressDialog 的进度条是否不明确
        progressDialog.setIndeterminate(false);
        // 设置ProgressDialog 是否可以按退回按键取消
        progressDialog.setCancelable(false);
        // 让ProgressDialog显示
        progressDialog.show();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://47.97.181.151:8888")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Message msg =new Message(); //从全局池中返回一个message实例，避免多次创建message（如new Message）
                msg.obj = 1;
                msg.what=1; //标志消息的标志
                handler.sendMessage(msg);
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if(response.isSuccessful()){//回调的方法执行在子线程。
                    Log.d("kwwl","获取数据成功了");
                    Log.d("kwwl","response.code()=="+response.code());
                    Log.d("kwwl","response.body().string()=="+response.body().string());
                    Message msg =new Message(); //从全局池中返回一个message实例，避免多次创建message（如new Message）
                    msg.obj = 1;
                    msg.what=1; //标志消息的标志
                    handler.sendMessage(msg);
                }
            }
        });
    }
    private void getResultAsync(String way,int numOfStorage,int numOfOperation)
    {
        progressDialog = new ProgressDialog(MainActivity.this);
        // 设置进度条风格，风格为圆形，旋转的
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // 设置ProgressDialog 标题
        progressDialog.setTitle("算法运算中");
        // 设置ProgressDialog 提示信息
        progressDialog.setMessage("请稍等");
        // 设置ProgressDialog 标题图标
        // 设置ProgressDialog 的进度条是否不明确
        progressDialog.setIndeterminate(false);
        // 设置ProgressDialog 是否可以按退回按键取消
        progressDialog.setCancelable(false);
        // 让ProgressDialog显示
        progressDialog.show();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("numOfStorage","4");
        builder.add("algorithm","FIFO");
        RequestBody formBody = builder.build();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://47.97.181.151:8889")
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                progressDialog.cancel();
                Log.e("kwwl","获取数据失败了");
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if(response.isSuccessful()){//回调的方法执行在子线程。
                    progressDialog.cancel();
                    Log.e("kwwl","获取数据成功了");
                    Log.e("kwwl","response.code()=="+response.code());
                    Log.e("kwwl","response.body().string()=="+response.body().string());
                }
            }
        });
    }
    class MyRunnable implements Runnable {//内部类实现Runnable接口；

        @Override
        public void run() {//还是在Runnable重写的run()方法中更新界面；
            progressDialog.cancel();
        }
    }
}