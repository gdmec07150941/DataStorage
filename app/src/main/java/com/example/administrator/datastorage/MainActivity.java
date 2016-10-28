package com.example.administrator.datastorage;

import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText editText1, editText2;
    private TextView textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText1 = (EditText) findViewById(R.id.editText1);
        editText2 = (EditText) findViewById(R.id.editText2);
        textView1 = (TextView) findViewById(R.id.textView1);
    }
    public void spWrite(View view){
        SharedPreferences user = getSharedPreferences("user",MODE_APPEND);
        SharedPreferences.Editor editor = user.edit();
        editor.putString("account",editText1.getText().toString());
        editor.putString("pass",editText2.getText().toString());
        editor.commit();
        Toast.makeText(this,"SharedPreferences保存成功",Toast.LENGTH_SHORT).show();
    }
    public void spRead(View view){
        SharedPreferences user = getSharedPreferences("user",MODE_PRIVATE);
        String acount = user.getString("account","没有此键值");
        String pass = user.getString("pass","没有此键值");
        textView1.setText("帐号："+acount+"\n"+"密码："+pass);
        Toast.makeText(this,"SharedPreferences读取成功",Toast.LENGTH_SHORT).show();
    }
    public void ROMWrite(View view){
        String account = editText1.getText().toString();
        String pass = editText2.getText().toString();
        try {
            FileOutputStream fileOutputStream = openFileOutput("user.txt",MODE_APPEND);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(account+"===>"+pass);
            bufferedWriter.flush();
            fileOutputStream.close();
            Toast.makeText(this,"ROW写入成功",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void ROMRead(View view){
        String acount = editText1.getText().toString();
        String pass = editText2.getText().toString();
        try {
            FileInputStream fileInputStream = openFileInput("user.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String str;
            while((str = bufferedReader.readLine()) != null){
                stringBuffer.append(str+"\n");
            }
            fileInputStream.close();
            textView1.setText(stringBuffer);
            Toast.makeText(this,"ROM读取成功",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void SDWrite(View view){
        String str = editText1.getText().toString()+"==>"+editText2.getText().toString();     // 获取输入框的值
        String sdCardRoot = Environment.getExternalStorageDirectory().getAbsolutePath();    // 动态返回SDCard路径名称
        String path = sdCardRoot+"/test.txt";
        File file = new File(path);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(str.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
            Toast.makeText(this,"sd卡写入成功",Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,"我竟然异常了！？",Toast.LENGTH_LONG).show();
        }
    }
    public void SDRead(View view){
        String sdCardRoot = Environment.getExternalStorageDirectory().getAbsolutePath();
        String path = sdCardRoot+"/test.txt";
        File file = new File(path);
        int length = (int) file.length();
        byte[] bytes = new byte[length];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytes,0,length);
            fileInputStream.close();
            textView1.setText(new String(bytes));
            Toast.makeText(this,"SD卡读取成功",Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,1,0,"退出");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 1:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
