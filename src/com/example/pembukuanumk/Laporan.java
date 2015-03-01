package com.example.pembukuanumk;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Laporan extends Activity implements View.OnClickListener,OnItemSelectedListener {
	
	DatabaseHelper db;
	private String umk_email=new String();
	private Spinner jenisLaporan;
	LinearLayout layoutLaporan;
	SharedPreferences prefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_laporan);
		setHeader(getString(R.string.Laporan), false, true);
		this.db = new DatabaseHelper(this);
		prefs = getSharedPreferences("MyPref", MODE_PRIVATE); 
		umk_email= prefs.getString("email", null);
		
		layoutLaporan = (LinearLayout)findViewById(R.id.layoutLaporan);
		
		jenisLaporan = (Spinner)findViewById(R.id.spinnerJenisTransaksiLain);
		jenisLaporan.setOnItemSelectedListener(this);
	}
	
	public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
	
	public void setHeader(String title, boolean btnHomeVisible, boolean btnFeedbackVisible)
    {
    		ViewStub stub = (ViewStub) findViewById(R.id.vsHeader);
    		View inflated = stub.inflate();
          
    		TextView txtTitle = (TextView) inflated.findViewById(R.id.txtHeading);
    		txtTitle.setText(title);
    		
    		Button btnHome = (Button) inflated.findViewById(R.id.btnHome);
    		btnHome.setOnClickListener(this);

    		
    }
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.btnHome){
		Intent intent = new Intent(getApplicationContext(), Main_menu_v2.class);
		/*Bundle bundleEmail = new Bundle();
		bundleEmail.putString("email", umk_email);
		intent.putExtras(bundleEmail);*/
    	startActivity(intent);
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		if(parent.getItemAtPosition(position).toString().equals("Daftar Transaksi")){
			TableLayout tl = (TableLayout)findViewById(R.id.daftarTransaksi);
			tl.setVisibility(View.VISIBLE);
			
			List<Model_Transaksi_Penjualan> listTransaksiPenjualan = db.getAllTransaksi_penjualan(db.getUMK(umk_email).getId());
			for(int i=0;i<listTransaksiPenjualan.size();i++){
				TextView tv_id = new TextView(this);
				tv_id.setText(""+listTransaksiPenjualan.get(i).getId());
				TextView tv_tanggal = new TextView(this);
				tv_tanggal.setText(listTransaksiPenjualan.get(i).getTanggal_transaksi_penjualan());
				TextView tv_namaProduk = new TextView(this);
				tv_namaProduk.setText(db.getProduct(listTransaksiPenjualan.get(i).getId()).getNama_produk());
				TextView tv_uraian = new TextView(this);
				tv_uraian.setText(listTransaksiPenjualan.get(i).getUraian_transaksi());
				TextView tv_nilaiRupiah = new TextView(this);
				tv_nilaiRupiah.setText(Long.toString((listTransaksiPenjualan.get(i).getNilai_rupiah())));
				
				TableRow tr1 = new TableRow(this);
				tr1.addView(tv_id);
				tr1.addView(tv_tanggal);
				tr1.addView(tv_namaProduk);
				tr1.addView(tv_uraian);
				tr1.addView(tv_nilaiRupiah);
				tl.addView(tr1);
			}
			
		}
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}

}
