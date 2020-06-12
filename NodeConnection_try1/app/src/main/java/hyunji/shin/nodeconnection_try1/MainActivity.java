package hyunji.shin.nodeconnection_try1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView tvData;
    private  static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.context = getApplicationContext();



        tvData = (TextView) findViewById(R.id.textview);

        Button btn_all = (Button) findViewById(R.id.btn_all);
        Button btn_post = (Button) findViewById(R.id.btn_post);
        Button btn_insert = (Button) findViewById(R.id.btn_insert);
        Button btn_update = (Button) findViewById(R.id.btn_update);

        btn_all.setOnClickListener(new View.OnClickListener() { //GET ALL버튼을 누르면
                                       @Override
                                       public void onClick(View view) {
                                           new Server.JSONTask1().execute("http://192.168.35.25:3306/person");
                                       }
                                   }
        );
        btn_post.setOnClickListener(new View.OnClickListener() { //POST버튼을 누르면
            @Override
            public void onClick(View view) {
                new Server.Show_alarmList("b").execute("http://192.168.35.25:3306/post" ); //(1)이벤트 발생후 호출
            }
        });

        btn_insert.setOnClickListener(new View.OnClickListener() { //insert DB 버튼을 누르면
            @Override
            public void onClick(View view) {
                new Server.Update_alarmList("b","time","day").execute("http://192.168.35.25:3306/insert"); //(1)이벤트 발생후 호출
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() { //insert DB 버튼을 누르면
            @Override
            public void onClick(View view) {
                new Server.JSONTask4().execute("http://192.168.35.25:3306/update" ); //(1)이벤트 발생후 호출
            }
        });

    }

    public static Context getAppContext() {
        return MainActivity.context;
    }


//    public class JSONTask1 extends AsyncTask<String, String, String> {
//
//        public JSONTask1() {
//            super();
//        }
//
//        @Override
//        protected String doInBackground(String... urls) {
//            try {
//                JSONObject jsonObject = new JSONObject();
//                HttpURLConnection con = null;
//                BufferedReader reader = null;
//
//                try {
//                    URL url = new URL(urls[0]);
//                    con = (HttpURLConnection) url.openConnection();
//                    con.connect();
//
//                    //입력 스트림 생성
//                    InputStream stream = con.getInputStream();
//
//                    //속도를 향상시키고 부하를 줄이기 위한 버퍼를 선언한다.
//                    reader = new BufferedReader(new InputStreamReader(stream));
//
//                    //실제 데이터를 받는곳
//                    StringBuffer buffer = new StringBuffer();
//
//                    //line별 스트링을 받기 위한 temp 변수
//                    String line = "";
//
//                    //아래라인은 실제 reader에서 데이터를 가져오는 부분이다. 즉 node.js서버로부터 데이터를 가져온다.
//                    while ((line = reader.readLine()) != null) {
//                        Log.e("line", line);
//                        buffer.append(line);
//                    }
//
//                    //다 가져오면 String 형변환을 수행한다. 이유는 protected String doInBackground(String... urls) 니까
//                    return buffer.toString();
//
//                    //아래는 예외처리 부분이다.
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    //종료가 되면 disconnect메소드를 호출한다.
//                    if (con != null) {
//                        con.disconnect();
//                    }
//                    try {
//                        //버퍼를 닫아준다.
//                        if (reader != null) {
//                            reader.close();
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }//finally 부분
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            Log.e("null", "This is null");
//            return null;
//        }
//
//        //doInBackground메소드가 끝나면 여기로 와서 텍스트뷰의 값을 바꿔준다.
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            tvData.setText(result);
//        }
//
//    }
///*
////원본
//    public class JSONTask2 extends AsyncTask<String, String, String>{
//
//        @Override
//        protected String doInBackground(String... urls) {
//            try {
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.accumulate("name", "b");
//
//                HttpURLConnection con = null;
//                BufferedReader reader = null;
//
//                try{
//                    //URL url = new URL("http://192.168.25.16:3000/users");
//                    URL url = new URL(urls[0]);
//                    con = (HttpURLConnection) url.openConnection();
//                    con.setRequestMethod("POST");
//                    con.setRequestProperty("Cache-Control", "no-cache");
//                    con.setRequestProperty("Content-Type", "application/json");
//                    con.setRequestProperty("Accept", "text/html");
//                    con.setDoOutput(true);
//                    con.setDoInput(true);
//                    con.connect();
//
//                    OutputStream outStream = con.getOutputStream();
//                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
//                    writer.write(jsonObject.toString());
//                    writer.flush();
//                    writer.close();
//
//                    InputStream stream = con.getInputStream();
//
//                    reader = new BufferedReader(new InputStreamReader(stream));
//
//                    StringBuffer buffer = new StringBuffer();
//
//                    String line = "";
//                    while((line = reader.readLine()) != null){
//                        buffer.append(line);
//                    }
//
//                    return buffer.toString();
//
//                } catch (MalformedURLException e){
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    if(con != null){
//                        con.disconnect();
//                    }
//                    try {
//                        if(reader != null){
//                            reader.close();
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            tvData.setText(result);
//        }
//    }
//*/
//    public class JSONTask2 extends AsyncTask<String, String, String>{
//
//        @Override
//        protected String doInBackground(String... urls) {
//            try {
//                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
//                JSONObject jsonObject = new JSONObject();
//
//                jsonObject.accumulate("name", "c"); //(2)찾으려는 요소 넣기
//
//
//                HttpURLConnection con = null;
//                BufferedReader reader = null;
//
//                try{
//                    URL url = new URL(urls[0]);
//                    //연결을 함
//                    con = (HttpURLConnection) url.openConnection();
//
//                    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//application JSON 형식으로 전송
//                    con.setRequestMethod("POST");//POST방식으로 보냄
//                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
//
//                    con.setRequestProperty("Accept", "application/x-www-form-urlencoded");//서버에 response 데이터를 html로 받음
//                    con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
//                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
//                    con.connect();
//
//                    //서버로 보내기위해서 스트림 만듬
//                    OutputStream outStream = con.getOutputStream();
//                    //버퍼를 생성하고 넣음
//                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
//                    Log.e("Json?",jsonObject.toString());
//                    writer.write(jsonObject.toString());
//                    writer.flush();
//                    writer.close();//버퍼를 받아줌
//
//                    //서버로 부터 데이터를 받음
//                    InputStream stream = con.getInputStream();
//
//                    reader = new BufferedReader(new InputStreamReader(stream));
//
//                    StringBuffer buffer = new StringBuffer();
//
//                    String line = "";
//                    while((line = reader.readLine()) != null){
//                        buffer.append(line);
//                    }
//
//                    return buffer.toString();//서버로 부터 받은 값을 리턴해줌 아마 OK!!가 들어올것임
//
//                } catch (MalformedURLException e){
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    if(con != null){
//                        con.disconnect();
//                    }
//                    try {
//                        if(reader != null){
//                            reader.close();//버퍼를 닫아줌
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            tvData.setText(result);//서버로 부터 받은 값을 출력해주는 부
//        }
//    }
//
//    public class JSONTask3 extends AsyncTask<String, String, String>{
//
//        @Override
//        protected String doInBackground(String... urls) {
//            try {
//                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
//                JSONObject jsonObject = new JSONObject();
//
//                jsonObject.accumulate("name", "bnsert_name"); //(2)찾으려는 요소 넣기
//                jsonObject.accumulate("address", "bnsert_address");
//                jsonObject.accumulate("email", "bnsert_email");
//                jsonObject.accumulate("phone", "binsert_phone");
//
//
//
//                HttpURLConnection con = null;
//                BufferedReader reader = null;
//
//                try{
//                    URL url = new URL(urls[0]);
//                    //연결을 함
//                    con = (HttpURLConnection) url.openConnection();
//
//                    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//application JSON 형식으로 전송
//                    con.setRequestMethod("POST");//POST방식으로 보냄
//                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
//
//                    con.setRequestProperty("Accept", "application/x-www-form-urlencoded");//서버에 response 데이터를 html로 받음
//                    con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
//                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
//                    con.connect();
//
//                    //서버로 보내기위해서 스트림 만듬
//                    OutputStream outStream = con.getOutputStream();
//                    //버퍼를 생성하고 넣음
//                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
//                    Log.e("Json?",jsonObject.toString());
//                    writer.write(jsonObject.toString());
//                    writer.flush();
//                    writer.close();//버퍼를 받아줌
//
//                    //서버로 부터 데이터를 받음
//                    InputStream stream = con.getInputStream();
//
//                    reader = new BufferedReader(new InputStreamReader(stream));
//
//                    StringBuffer buffer = new StringBuffer();
//
//                    String line = "";
//                    while((line = reader.readLine()) != null){
//                        buffer.append(line);
//                    }
//
//                    return buffer.toString();//서버로 부터 받은 값을 리턴해줌 아마 OK!!가 들어올것임
//
//                } catch (MalformedURLException e){
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    if(con != null){
//                        con.disconnect();
//                    }
//                    try {
//                        if(reader != null){
//                            reader.close();//버퍼를 닫아줌
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            tvData.setText(result);//서버로 부터 받은 값을 출력해주는 부
//        }
//    }
//
//    public class JSONTask4 extends AsyncTask<String, String, String>{
//
//        @Override
//        protected String doInBackground(String... urls) {
//            try {
//                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
//                JSONObject jsonObject = new JSONObject();
//
//                jsonObject.accumulate("email", "changed-Email"); //(2)찾으려는 요소 넣기
//                jsonObject.accumulate("name", "f");
//
//
//                HttpURLConnection con = null;
//                BufferedReader reader = null;
//
//                try{
//                    URL url = new URL(urls[0]);
//                    //연결을 함
//                    con = (HttpURLConnection) url.openConnection();
//
//                    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//application JSON 형식으로 전송
//                    con.setRequestMethod("POST");//POST방식으로 보냄
//                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
//
//                    con.setRequestProperty("Accept", "application/x-www-form-urlencoded");//서버에 response 데이터를 html로 받음
//                    con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
//                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
//                    con.connect();
//
//                    //서버로 보내기위해서 스트림 만듬
//                    OutputStream outStream = con.getOutputStream();
//                    //버퍼를 생성하고 넣음
//                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
//                    Log.e("Json?",jsonObject.toString());
//                    writer.write(jsonObject.toString());
//                    writer.flush();
//                    writer.close();//버퍼를 받아줌
//
//                    //서버로 부터 데이터를 받음
//                    InputStream stream = con.getInputStream();
//
//                    reader = new BufferedReader(new InputStreamReader(stream));
//
//                    StringBuffer buffer = new StringBuffer();
//
//                    String line = "";
//                    while((line = reader.readLine()) != null){
//                        buffer.append(line);
//                    }
//
//                    return buffer.toString();//서버로 부터 받은 값을 리턴해줌 아마 OK!!가 들어올것임
//
//                } catch (MalformedURLException e){
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    if(con != null){
//                        con.disconnect();
//                    }
//                    try {
//                        if(reader != null){
//                            reader.close();//버퍼를 닫아줌
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            tvData.setText(result);//서버로 부터 받은 값을 출력해주는 부
//        }
//    }

}
