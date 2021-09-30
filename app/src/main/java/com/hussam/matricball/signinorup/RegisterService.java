package com.hussam.matricball.signinorup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.hussam.matricball.R;
import com.theartofdev.edmodo.cropper.CropImage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterService extends AppCompatActivity {

    private Button CreateAccountButton;
    private EditText InputName, InputPhoneNumber, InputPassword, InputCompanyName, InputServiceProvided, InputBusinessAddress ,InputTelephoneNumber ;
    private ProgressDialog loadingBar;
    private TextView AdminLink, NotAdminLink;
    private String downloadImageUrl,saveCurrentDate, saveCurrentTime, productRandomKey;
    private ImageView InputAdminImage;
    private CircleImageView profileImageView;
    private Uri ImageUri;
    private static final int GalleryPick = 1;


    private StorageTask uploadTask;
    private StorageReference storageProfilePictureRef;
    private Uri imageUri;
    private String myUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_service);


        storageProfilePictureRef = FirebaseStorage.getInstance().getReference().child("Profile Pictures Admins");

        CreateAccountButton = findViewById(R.id.register_btn);
        InputName = findViewById(R.id.register_username_input);
        InputPassword = findViewById(R.id.register_password_input);
        InputPhoneNumber = findViewById(R.id.register_phone_number_input);
        InputCompanyName = findViewById(R.id.company_name_input);
        InputServiceProvided = findViewById(R.id.service_provided_input);
        InputBusinessAddress = findViewById(R.id.business_address_input);
        //InputTelephoneNumber = findViewById(R.id.telephone_input);
        profileImageView =findViewById(R.id.admin_profile_image);

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());


        productRandomKey = saveCurrentDate +" "+ saveCurrentTime;



        loadingBar = new ProgressDialog(this);


        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity(imageUri)
                        .setAspectRatio(1, 1)
                        .start(RegisterService.this);

            }
        });

        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount();// استدعاء ميثود
            }
        });

    }



    private void CreateAccount() {

        String name = InputName.getText().toString();
        String phone = InputPhoneNumber.getText().toString();
        String password = InputPassword.getText().toString();
        String companyName = InputCompanyName.getText().toString();
        String serviceProvided = InputServiceProvided.getText().toString();
        String businessAddress = InputBusinessAddress.getText().toString();
        //String telephoneNumber = InputTelephoneNumber.getText().toString();


        if (TextUtils.isEmpty(name)) {

            Toast.makeText(this, "Please write your Name...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(phone)) {

            Toast.makeText(this, "Please write your Phone Number...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {

            Toast.makeText(this, "Please write your Password...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(companyName)) {

            Toast.makeText(this, "Please write your Company Name...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(serviceProvided)) {

            Toast.makeText(this, "Please write your Service Provided...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(businessAddress)) {

            Toast.makeText(this, "Please write your Business Address...", Toast.LENGTH_SHORT).show();
        } /*else if (imageUri == null) {

            Toast.makeText(this, "Please choose your Image...", Toast.LENGTH_SHORT).show();
        }*/ else  {

            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait , while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            ValidatePhoneNumber(name, phone, password,companyName,serviceProvided,businessAddress);
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode== CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            profileImageView.setImageURI(imageUri);
        }
        else
        {
            Toast.makeText(this, "Error, Try Again.", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(RegisterService.this, RegisterService.class));
            finish();
        }
    }


    private void uploadImage()
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Update Profile");
        progressDialog.setMessage("Please wait, while we are updating your account information");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        if (imageUri != null)
        {
            final StorageReference fileRef = storageProfilePictureRef
                    .child(".jpg");

            uploadTask = fileRef.putFile(imageUri);

            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception
                {
                    if (!task.isSuccessful())
                    {
                        throw task.getException();
                    }

                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task)
                {
                    if (task.isSuccessful())
                    {
                        Uri downloadUrl = task.getResult();
                        myUrl = downloadUrl.toString();



                        progressDialog.dismiss();

                        Toast.makeText(RegisterService.this, "Profile Info update successfully.", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterService.this, "Error.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else
        {
            Toast.makeText(this, "image is not selected.", Toast.LENGTH_SHORT).show();
        }
    }

    private void ValidatePhoneNumber(final String name, final String phone, final String password,
                                     final String companyName, final String serviceProvided,
                                     final String businessAddress) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        FirebaseApp.initializeApp(getBaseContext());
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("Admins").child(phone).exists())) {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("phone", phone);
                    userdataMap.put("password", password);
                    userdataMap.put("name", name);
                    userdataMap.put("companyName", companyName);
                    userdataMap.put("serviceProvided", serviceProvided);
                    userdataMap.put("businessAddress", businessAddress);
                    //userdataMap.put("telephoneNumber", telephoneNumber);
                    userdataMap.put("pid", productRandomKey);
                    userdataMap.put("subscribe","Not subscribed");


                    RootRef.child("Admins").child(phone).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {


                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterService.this, "Congratulations , your account has been created.", Toast.LENGTH_SHORT).show();

                                loadingBar.dismiss();

                                Intent intent = new Intent(RegisterService.this, Login_Fragment.class);

                                startActivity(intent);
                                finish();
                            } else {
                                loadingBar.dismiss();
                                Toast.makeText(RegisterService.this, "Network Error : Please try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                } else {

                    Toast.makeText(RegisterService.this, "This" + phone + "Already exists", Toast.LENGTH_SHORT).show();

                    loadingBar.dismiss();
                    Toast.makeText(RegisterService.this, "Please try agin using another phone number", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegisterService.this, MainActivity.class);
finish();
                    startActivity(intent);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        if (imageUri != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Update Profile");
            progressDialog.setMessage("Please wait, while we are updating your account information");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();


            final StorageReference fileRef = storageProfilePictureRef
                    .child(phone + ".jpg");
            uploadTask = fileRef.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception
                {
                    if (!task.isSuccessful())
                    {
                        throw task.getException();
                    }

                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task)
                {
                    if (task.isSuccessful())
                    {
                        Uri downloadUrl = task.getResult();
                        myUrl = downloadUrl.toString();

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Admins");

                        HashMap<String, Object> userMap = new HashMap<>();

                        userMap. put("image", myUrl);
                        ref.child(phone).updateChildren(userMap);


                        progressDialog.dismiss();

                        Toast.makeText(RegisterService.this, "Profile Info update successfully.", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterService.this, "Error.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else
        {
            Toast.makeText(this, "image is not selected.", Toast.LENGTH_SHORT).show();
        }

    }




}
