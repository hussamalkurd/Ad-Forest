package com.hussam.matricball;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.interfaces.HttpResponseCallback;
import com.braintreepayments.api.internal.HttpClient;
import com.braintreepayments.api.models.PaymentMethodNonce;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hussam.matricball.Model.Users;
import com.hussam.matricball.Prevalent.Prevalent;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class BaintreeActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1234;
    final String API_GET_TOKEN = "https://baintree.000webhostapp.com/main.php";
    final String API_CHECK_OUT = "https://baintree.000webhostapp.com/checkout.php";
    private RadioGroup radioSexGroup;
    private RadioButton radioButton;
    String token,amount;
    HashMap<String,String> paramsHash;
    Button btn_pay; Users usersData;
    EditText edit_amount;
    LinearLayout group_payment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baintree);

        radioSexGroup = (RadioGroup) findViewById(R.id.radio);

        int selectedId = radioSexGroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        radioButton = (RadioButton) findViewById(selectedId);

        TextView userNameTextView = findViewById(R.id.user_profile_name_admin_baintree);
        //to display the username
        userNameTextView.setText(Prevalent.currentOnlineUsers.getName());

        //edit_amount=(EditText)findViewById(R.id.edt_amount);
        btn_pay=(Button) findViewById(R.id.btn_pay);
        group_payment=(LinearLayout)findViewById(R.id.payment_group);

        new BaintreeActivity.getToken().execute();

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitPayment();
            }
        });

    }

    private void submitPayment(){
        String payValue=radioButton.getText().toString();
        if(!payValue.isEmpty())
        {
            DropInRequest dropInRequest=new DropInRequest().clientToken(token);
            startActivityForResult(dropInRequest.getIntent(this),REQUEST_CODE);
        }
        else
            Toast.makeText(this, "Enter a valid amount for payment", Toast.LENGTH_SHORT).show();

    }

    private void sendPayments(){
        RequestQueue queue= Volley.newRequestQueue(BaintreeActivity.this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, API_CHECK_OUT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.toString().contains("Successful")){

                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Admins");

                            HashMap<String, Object> userMap = new HashMap<>();
                            userMap. put("subscribe","subscribed");
                            ref.child(Prevalent.currentOnlineUsers.getPhone()).updateChildren(userMap);


                            Toast.makeText(BaintreeActivity.this, "Payment Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(BaintreeActivity.this, AdminCategoryActivity.class);
                            startActivity(intent);
                            finish();

                        }
                        else {
                            Toast.makeText(BaintreeActivity.this, "Payment Failed", Toast.LENGTH_SHORT).show();
                        }
                        Log.d("Response",response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Err",error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if(paramsHash==null)
                    return null;
                Map<String,String> params=new HashMap<>();
                for(String key:paramsHash.keySet())
                {
                    params.put(key,paramsHash.get(key));
                }
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("Content-type","application/x-www-form-urlencoded");
                return params;
            }
        };
        RetryPolicy mRetryPolicy=new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(mRetryPolicy);
        queue.add(stringRequest);
    }

    private class getToken extends AsyncTask {
        ProgressDialog mDailog;

        @Override
        protected Object doInBackground(Object[] objects) {
            HttpClient client=new HttpClient();
            client.get(API_GET_TOKEN, new HttpResponseCallback() {
                @Override
                public void success(final String responseBody) {
                    mDailog.dismiss();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            group_payment.setVisibility(View.VISIBLE);
                            token=responseBody;
                        }
                    });
                }

                @Override
                public void failure(Exception exception) {
                    mDailog.dismiss();
                    Log.d("Err",exception.toString());
                }
            });
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDailog=new ProgressDialog(BaintreeActivity.this,android.R.style.Theme_DeviceDefault_Light_Dialog);
            mDailog.setCancelable(false);
            mDailog.setMessage("We are trying to contact Server! Please wait...");
            mDailog.show();

        }

        @Override
        protected void onPostExecute(Object o){
            super.onPostExecute(o);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                PaymentMethodNonce nonce = result.getPaymentMethodNonce();
                String strNounce = nonce.getNonce();
                if (!radioButton.getText().toString().isEmpty()) {
                    amount = radioButton.getText().toString();
                    paramsHash = new HashMap<>();
                    paramsHash.put("amount", amount);
                    paramsHash.put("nonce", strNounce);

                    sendPayments();
                } else {
                    Toast.makeText(this, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "User canceled", Toast.LENGTH_SHORT).show();
            } else {
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Log.d("Err", error.toString());
            }
        }
    }

}