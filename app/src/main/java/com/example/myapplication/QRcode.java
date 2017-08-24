package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QRcode extends AppCompatActivity {
    String host = "223.194.134.161";    // 서버 컴퓨터 IP
    //String host = "121.161.183.214";
    int port = 5001;
    private Handler mHandler;
    private Handler timerHandler;
    private Handler stopHandler;
    private Bundle bundle;
    private Bundle timerbundle;
    private ConnectThread thread;
    private TimerThread thread2;
    private pauestimer thread3;
    private WebView imgv ;
    static int k = 9000;
    private long now;
    private Date date;
    private SimpleDateFormat sdfNow;
    private String formatDate;
    private String url;
    private String md5;
    private TextView dateNow;
    private Intent clockview;
    private Intent getfinger;
    private String qrurl;
    private WebView qrview;
    private TextView timev;
    private TextView timev2;
    private String[] ReturnList;
    private ImageButton ibtn;
    private Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        //스샷막아주는 코드
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);

        clockview = new Intent(getApplicationContext(), ClockView.class);
        startActivity(clockview);


        getfinger = getIntent();
        qrurl = getfinger.getStringExtra("finger");
        qrview = (WebView)findViewById(R.id.qr_qrcode_img);
        qrview.loadUrl(qrurl);


        thread = new ConnectThread();
        thread.start();


        // 현재시간을 msec 으로 구한다.
        now = System.currentTimeMillis();
        // 현재시간을 date 변수에 저장한다.
        date = new Date(now);
        // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
        sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        // nowDate 변수에 값을 저장한다.
        formatDate = sdfNow.format(date);

        dateNow = (TextView) findViewById(R.id.qr_date);
        dateNow.setText(formatDate);    // TextView 에 현재 시간 문자열 할당

        timev = (TextView)findViewById(R.id.qr_timer_t);
        timev2 = (TextView)findViewById(R.id.qr_timer_t2);
        ibtn = (ImageButton)findViewById(R.id.qr_time);
        //서버에서 받은 QR코드 url을 핸들러를 통해 웹뷰에 붙여줌
        mHandler = new Handler(){
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                bundle = msg.getData();

                imgv = (WebView)findViewById(R.id.qr_qrcode_img);
                //Uri uri = Uri.parse(s);
                String qrcode = bundle.getString("key");

                imgv.loadUrl(qrcode);

                imgv.setVisibility(View.VISIBLE);

                ReturnList = qrcode.split("%3B%3B");
                url = ReturnList[0] + "%3B%3B";
                md5 = ReturnList[1];
                Log.d("aa", url);
                Log.d("bb", md5);


                //Toast.makeText(getApplicationContext(), bundle.getString("key") , Toast.LENGTH_SHORT).show();
            }
        };

        stopHandler = new Handler(){
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                bundle = msg.getData();

                imgv = (WebView)findViewById(R.id.qr_qrcode_img);
                //Uri uri = Uri.parse(s);
                String ss = bundle.getString("key");

                imgv.setVisibility(View.INVISIBLE);


                //Toast.makeText(getApplicationContext(), bundle.getString("key") , Toast.LENGTH_SHORT).show();
            }
        };

        // 시간초 나오게하는 핸들러
        timerHandler = new Handler(){
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                timerbundle = msg.getData();
                //Uri uri = Uri.parse(s);
                int timess = timerbundle.getInt("timer");
                //Toast.makeText(getApplicationContext(), "kkk" , Toast.LENGTH_SHORT).show();
                timev.setText(Integer.toString(timess));
                timev2.setText(Integer.toString(timess));

                if(timess < 2000){
                    timev.setVisibility(View.INVISIBLE);
                    ibtn.setVisibility(View.VISIBLE);
                }
                else{
                    timev.setVisibility(View.VISIBLE);
                    ibtn.setVisibility(View.INVISIBLE);
                }

                //Toast.makeText(getApplicationContext(), timerbundle.getString("timer") , Toast.LENGTH_SHORT).show();
            }
        };


        thread3 = new pauestimer();
        thread3.setDaemon(true);
        thread3.start();
        thread2 = new TimerThread();
        thread2.setDaemon(true);
        thread2.start();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),OldFirstView.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //thread.interrupt();
        //thread2.interrupt();
        startActivity(intent);
        super.onBackPressed();
    }

    // 새로운 QR코드를 받고싶을때 버튼 이벤트
    public void onButtonClicked(View v){

        k=0;
        // 현재시간을 msec 으로 구한다.
        now = System.currentTimeMillis();
        // 현재시간을 date 변수에 저장한다.
        date = new Date(now);
        // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
        sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        // nowDate 변수에 값을 저장한다.
        formatDate = sdfNow.format(date);
        dateNow = (TextView) findViewById(R.id.qr_date);
        dateNow.setText(formatDate);    // TextView 에 현재 시간 문자열 할당


        timev = (TextView)findViewById(R.id.qr_timer_t);
        ibtn = (ImageButton)findViewById(R.id.qr_time);
        ibtn.setVisibility(View.INVISIBLE);
        timev.setVisibility(View.VISIBLE);


        thread = new ConnectThread();
        thread.start();
        thread2 = new TimerThread();
        thread2.start();
    }

    class pauestimer extends Thread{
        public void run(){
            try{
                Thread.sleep(3000);
            }
            catch (Exception e){
                    e.printStackTrace();
            }
        }
    }

    class TimerThread extends Thread{
        ProgressBar progressBar = (ProgressBar)findViewById(R.id.qr_bar);

        // Button btn = (Button)findViewById(R.id.qr_time);

        public void run(){
            // 프로그래스바 (위와 동일)

            k=9000;

            for(; k>=0; k--){
                progressBar.setProgress(k);

                Bundle tbundle = new Bundle();
                tbundle.putInt("timer", k);
                Message timermsg = new Message();
                timermsg.setData(tbundle);
                timerHandler.sendMessage(timermsg);


                try{
                    Thread.sleep(1);
                }

                catch(Exception e){
                    e.printStackTrace();
                }
            }
            Bundle stopbundle = new Bundle();
            stopbundle.putString("timer", "ee");
            Message stopmsg = new Message();
            stopmsg.setData(stopbundle);
            stopHandler.sendMessage(stopmsg);

            //ibtn.setVisibility(View.VISIBLE);
        }
    }

    // 쓰레드 (서버연결 및 프로그래스바) - 현재는 임시로 한 쓰레드에 그냥 넣어둠
    class ConnectThread extends Thread{
        //ProgressBar progressBar = (ProgressBar)findViewById(R.id.qr_bar);
        public void run(){
            String host = "172.30.1.53";
            //String host = "223.194.158.91";
            //String host = "113.198.81.69";
            //String host = "223.194.134.161";
            int port = 5002;
            //String host = "172.30.1.53";
            //int port = 8080;


            try {
                Socket socket = new Socket(host, port);
                System.out.println("서버로 연결되었습니다. : " + host + ", " + port);
                //Toast.makeText(MainActivity.this, "connect server : " + host + ", " + port , Toast.LENGTH_SHORT).show();

                //String output = m_etSendData.getText().toString();
                String output = "send";
                ObjectOutputStream outstream = new ObjectOutputStream(socket.getOutputStream());
                outstream.writeObject(output);
                outstream.flush();
                System.out.println("서버로 보낸 데이터 : " + output);
                //Toast.makeText(MainActivity.this, "서버로 보낸 데이터 : " + output , Toast.LENGTH_SHORT).show();

                String output2 = "send2";
                ObjectOutputStream outstream2 = new ObjectOutputStream(socket.getOutputStream());
                outstream2.writeObject(output);
                outstream2.flush();
                System.out.println("서버로 보낸 데이터 : " + output2);

                ObjectInputStream instream = new ObjectInputStream(socket.getInputStream());
                //Bitmap bitmap = (Bitmap)instream.readObject();
                Object input = instream.readObject();
                //System.out.println(instream.readObject());
                System.out.println("서버로부터 받은 데이터: " + input);
                //Toast.makeText(getApplicationContext(), "서버로부터 받은 데이터 : " + input , Toast.LENGTH_SHORT).show();


                // 서버에서 받은 데이터(QR코드)를 번들을 통해 핸들러 메세지로 전달
                Bundle bundle = new Bundle();
                bundle.putString("key", input.toString());
                Message msg = new Message();
                msg.setData(bundle);
                mHandler.sendMessage(msg);


                instream.close();
                outstream.close();
                socket.close();

            }
            catch (Exception e) {
                e.printStackTrace();
                System.out.println("접근실패");
            }
        }
    }


}
