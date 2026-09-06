package com.example.spacemanpredictor;
import android.app.*;import android.os.*;import android.content.*;import android.widget.*;import java.util.*;
public class MainActivity extends Activity{
 ArrayList<Double>a=new ArrayList<>(); EditText input;TextView pred,info; SharedPreferences p;
 public void onCreate(Bundle b){super.onCreate(b);setContentView(R.layout.activity_main);input=findViewById(R.id.input);pred=findViewById(R.id.pred);info=findViewById(R.id.info);p=getSharedPreferences("d",0);load();
 findViewById(R.id.add).setOnClickListener(v->add());findViewById(R.id.go).setOnClickListener(v->predict());findViewById(R.id.clear).setOnClickListener(v->{a.clear();save();render();});render();}
 void add(){try{double x=Double.parseDouble(input.getText().toString());if(x<1)throw new Exception();a.add(x);if(a.size()>100)a.remove(0);save();input.setText("");render();}catch(Exception e){Toast.makeText(this,"Masukkan angka >= 1.00x",Toast.LENGTH_SHORT).show();}}
 void predict(){if(a.size()<3){Toast.makeText(this,"Masukkan minimal 3 ronde dulu",Toast.LENGTH_SHORT).show();return;} int n=Math.min(20,a.size());double s=0;for(int i=a.size()-n;i<a.size();i++)s+=a.get(i);double x=s/n; x=Math.max(1.01,Math.min(20,x)); double rounded=Math.round(x*100.0)/100.0;pred.setText(String.format(Locale.US,"%.2fx",rounded));}
 void render(){info.setText("Ronde: "+a.size()+"\n20 terakhir: "+last()+"\n\nTekan PREDIKSI BERIKUTNYA untuk estimasi berbasis rata-rata 20 ronde terakhir.");}
 String last(){if(a.isEmpty())return "-";StringBuilder s=new StringBuilder();for(int i=Math.max(0,a.size()-20);i<a.size();i++)s.append(String.format(Locale.US,"%.2fx",a.get(i))).append(i<a.size()-1?" • ":"");return s.toString();}
 void save(){StringBuilder s=new StringBuilder();for(double x:a)s.append(x).append(",");p.edit().putString("a",s.toString()).apply();}
 void load(){String s=p.getString("a","");if(!s.isEmpty())for(String x:s.split(","))try{a.add(Double.parseDouble(x));}catch(Exception e){}}
}