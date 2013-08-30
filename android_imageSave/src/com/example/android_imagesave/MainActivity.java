package com.example.android_imagesave;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {
	private MySQLiteOpenHelper mySQLiteOpenHelpe;
	private SQLiteDatabase mydb;
	private Button bt1,bt11,bt3,bt33;
	private ImageView iv1,iv2;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mySQLiteOpenHelpe=new MySQLiteOpenHelper(this, "saveimage.db", null, 1);
		mydb=mySQLiteOpenHelpe.getWritableDatabase();
		
		bt1=(Button)this.findViewById(R.id.button1);
		bt11=(Button)this.findViewById(R.id.button3);
		bt3=(Button)this.findViewById(R.id.button2);
		bt33=(Button)this.findViewById(R.id.button4);
		iv1=(ImageView)this.findViewById(R.id.imageView1);
		iv2=(ImageView)this.findViewById(R.id.imageView2);
		
		bt1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Bitmap bitmap1=BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
				int size=bitmap1.getWidth()*bitmap1.getHeight()*4;
				ByteArrayOutputStream baos=new ByteArrayOutputStream(size);
				bitmap1.compress(Bitmap.CompressFormat.PNG, 100, baos);
				byte[] imagedata1=baos.toByteArray();
				
				ContentValues cv=new ContentValues();
				cv.put("_id", 1);
				cv.put("image", imagedata1);
				mydb.insert("imagetable", null, cv);
				iv1.setImageDrawable(null);
				try {
					baos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		bt11.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Cursor cur=mydb.query("imagetable", new String[] {"_id","image"} , null, null, null, null, null);
				byte[] imagequery=null;
				if(cur.moveToNext()){
					imagequery=cur.getBlob(cur.getColumnIndex("image"));
				}
				Bitmap imagebitmap=BitmapFactory.decodeByteArray(imagequery, 0, imagequery.length);
				iv1.setImageBitmap(imagebitmap);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
